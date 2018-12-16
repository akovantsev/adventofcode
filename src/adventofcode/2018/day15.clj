(ns adventofcode.2018.day15
  (:import [clojure.lang PersistentQueue])
  (:require [clojure.set :as set]
            [clojure.string :as str]
            [adventofcode.utils :as u]))

(set! *print-length* 100)

(def input "################################\n#######################.########\n#######################.########\n########..#############.########\n#######.....#########....#..####\n#######.....##########......####\n######....#..########.......#..#\n#######.G...########...........#\n####..GG....G######..........###\n########....G..###..E.......#.E#\n########...G..#....G..G.....E..#\n########...G...G.G...........E.#\n####....G.....#####..E......#E.#\n####.####.#..#######....G.....##\n####.G#####.#########..........#\n####G#####..#########..........#\n####.####..E#########..........#\n####...#..#.#########.G........#\n####.....G..#########.........##\n####..G....E.#######........####\n####G.........#####...##....####\n#####G................###..E####\n#####..####...............######\n####..#####.............########\n#####.#######...........########\n#####.########.........#########\n#####.########.....E..##########\n#.....#########...#.############\n#..#############....############\n################....############\n##################.#############\n################################")

(def DAMAGE 3)
(def HP 200)





(defn reading-order [[x1 y1] [x2 y2]]
  (compare [y1 x1] [y2 x2]))

(defn attack-order [[[x1 y1] p1]  [[x2 y2] p2]]
  (compare [(:hp p1) y1 x1] [(:hp p2) y2 x2]))

(def empty-players (sorted-map-by reading-order))

(defn parse-idxed-line
  "=> [room players]"
  [y s]
  (->> s
    (map-indexed vector)
    (reduce
      (fn rf [[room players] [x sym]]
        (let [xy [x y]]
          (case sym
            \# [room players]
            \. [(conj room xy) players]
            \E [(conj room xy) (assoc players xy {:t sym :hp HP})]
            \G [(conj room xy) (assoc players xy {:t sym :hp HP})])))
      [#{} empty-players])))


(def adjacent-xys
  (memoize
    (fn -adjacent-xys [room [x1 y1]]
      (let [x0 (dec x1) x2 (inc x1)
            y0 (dec y1) y2 (inc y1)
            adjacent #{,,,,,,, [x1 y0] ,,,,,,,
                       [x0 y1] ,,,,,,, [x2 y1]
                       ,,,,,,, [x1 y2] ,,,,,,,}]
        (set/intersection adjacent room)))))

(defn would-die? [player damage]
  (-> player :hp (< (inc damage))))

(defn enemy? [p1 p2]
  (not= (:t p1) (:t p2)))

(defn all-enemies [p players]
  (filter #(-> % val (enemy? p)) players))

(defn weakest-in-range [room players xy p]
  (let [adj-points (adjacent-xys room xy)]
    (->> (select-keys players adj-points)
      (all-enemies p)
      (sort attack-order)
      (first))))


(def free-xys
  (memoize
    (fn -free-xys [room players xy]
      (set/difference
        (adjacent-xys room xy)
        (-> players keys set)))))


(defn shortest-paths [room players limit start-xy destination-xy]
  (loop [shortest limit
         done     []
         todo     (->> start-xy
                    (free-xys room players)
                    (mapv (fn mf1 [xy] [(transient #{xy}) [xy]])) ;; [visited path]
                    (into (PersistentQueue/EMPTY)))]
    (if (empty? todo)
      done
      (let [[visited p] (peek todo)
            todo        (pop todo)]
        (cond
          (-> p peek (= destination-xy))
          (recur (min shortest (count p)) (conj done p) todo)

          (-> p count (>= shortest))
          (recur shortest done todo)

          :else
          (let [next-paths (->> (peek p)
                             (free-xys room players)
                             (remove visited)
                             (map (fn mf2 [xy]
                                    [(conj! visited xy) (conj p xy)])))]
            (recur shortest done (into todo next-paths))))))))

(set! *unchecked-math* true)
(def city-distance (memoize u/city-distance))


(defn get-next-xy [room players pxy target-xys]
  (->> target-xys
    (map (partial city-distance pxy))
    (map vector target-xys)
    (sort-by second)
    (reduce
      (fn rf [[shortest xy] [target-xy distance]]
        (if (< shortest distance)
          [shortest xy]
          (let [paths   (shortest-paths room players shortest pxy target-xy)
                len     (-> paths first count)
                next-xy (->> paths (map first) (sort reading-order) (first))]
            (cond
              (nil? next-xy)   [shortest xy]
              (< len shortest) [len next-xy]
              (= len shortest) [len (->> [xy next-xy] (sort reading-order) (first))]))))
      [##Inf nil])
    (second)))


(defn simulate [input stop-on-elf-death? elf-dmg]
  ;(time
    (let [INPUT   (->> input
                    (str/split-lines)
                    (map-indexed parse-idxed-line)
                    (apply map vector)
                    (map (partial reduce into)))
          ROOM    (apply sorted-set-by reading-order (first INPUT))
          PLAYERS (second INPUT)]
      (loop [round   0
             moved?  false
             todo    (keys PLAYERS)
             players PLAYERS]
        (if (empty? todo)
          ;; next round:
          (recur (inc round) false (keys players) players)
          (let [[pxy & todo]  todo
                player        (get players pxy)
                t             (:t player)
                damage        (case t
                                \E elf-dmg
                                \G DAMAGE)]
            ;; find adjacent enemy:
            (if-let [[exy enemy] (weakest-in-range ROOM players pxy player)]
              (if (would-die? enemy damage)
                ;; kill:
                (if (and stop-on-elf-death? (= t \G))
                  ::failed
                  (recur round false (remove #{exy} todo) (dissoc players exy)))
                ;; damage:
                (recur round false todo (update-in players [exy :hp] - damage)))

              (if moved?
                ;; avoid double moves in 1 turn
                (recur round false todo players)
                ;; find all enemies in the room:
                (if-let [enemies (seq (all-enemies player players))]
                  ;; all open attack positions:
                  (if-let [open-xys (->> enemies
                                      (sequence
                                        (comp
                                          (map key)
                                          (mapcat (partial free-xys ROOM players))
                                          (distinct)))
                                      (seq))]
                    ;; find next possible step:
                    (if-let [next-xy (get-next-xy ROOM players pxy open-xys)]
                      ;; move:
                      (do ;(prn [:move pxy next-xy open-xys])
                        (recur round true
                          (conj todo next-xy)
                          (-> players (dissoc pxy) (assoc next-xy player))))
                      ;; do nothing:
                      (recur round false todo players))
                    ;; do nothing:
                    (recur round false todo players))
                  ;; exit:
                  (->> players (vals) (map :hp) (reduce + 0) (* round))))))))))


(defn f1 [input]
  (simulate input false 3))



(assert (= 27730 (f1 "#######\n#.G...#\n#...EG#\n#.#.#G#\n#..G#E#\n#.....#\n#######")))
(assert (= 36334 (f1 "#######\n#G..#E#\n#E#E.E#\n#G.##.#\n#...#E#\n#...E.#\n#######")))
(assert (= 39514 (f1 "#######\n#E..EG#\n#.#G.E#\n#E.##E#\n#G..#.#\n#..E#.#\n#######")))
(assert (= 27755 (f1 "#######\n#E.G#.#\n#.#G..#\n#G.#.G#\n#G..#.#\n#...E.#\n#######")))
(assert (= 28944 (f1 "#######\n#.E...#\n#.#..G#\n#.###.#\n#E#G#G#\n#...#G#\n#######")))
(assert (= 18740 (f1 "#########\n#G......#\n#.E.#...#\n#..##..G#\n#...##..#\n#...#...#\n#.G...G.#\n#.....G.#\n#########")))

"Elapsed time: 9.785227 msecs"
"Elapsed time: 8.535596 msecs"
"Elapsed time: 9.521708 msecs"
"Elapsed time: 4.023284 msecs"
"Elapsed time: 8.365766 msecs"
"Elapsed time: 12.756658 msecs"

(assert (= (f1 input) 196200))
"Elapsed time: 4399.123038 msecs"


(defn f2 [input]
  (time
    (loop [damage 4]
      (let [score (simulate input true damage)]
        (if (= score ::failed)
          (recur (inc damage))
          score)))))

;(f2 input)


(assert (= 4988 (f2 "#######\n#.G...#\n#...EG#\n#.#.#G#\n#..G#E#\n#.....#\n#######")))
(assert (= 31284 (f2 "#######\n#E..EG#\n#.#G.E#\n#E.##E#\n#G..#.#\n#..E#.#\n#######")))
(assert (= 3478 (f2 "#######\n#E.G#.#\n#.#G..#\n#G.#.G#\n#G..#.#\n#...E.#\n#######")))
(assert (= 6474 (f2 "#######\n#.E...#\n#.#..G#\n#.###.#\n#E#G#G#\n#...#G#\n#######")))
(assert (= 1140 (f2 "#########\n#G......#\n#.E.#...#\n#..##..G#\n#...##..#\n#...#...#\n#.G...G.#\n#.....G.#\n#########")))

"Elapsed time: 80.792273 msecs"
"Elapsed time: 9.331685 msecs"
"Elapsed time: 118.772361 msecs"
"Elapsed time: 139.921578 msecs"
"Elapsed time: 1887.330786 msecs"