(ns adventofcode.2019.day24
  (:require
   [clojure.string :as str]
   [adventofcode.utils :as u]))


(def input "###.#\n..#..\n#..#.\n#....\n.#.#.")
(def sample1 "....#\n#..#.\n#..##\n..#..\n#....")

(def xys (for [x (range 5) y (range 5)] [x y]))

(defn parse [s]
  (->> s
    str/split-lines
    (mapcat
      (fn [y line]
        (map-indexed
          (fn [x c]
            [[x y] (= \# c)])
          line))
      (range))
    (into {})))

(parse input)


(defn bug? [neighbors is-bug]
  (let [bugs (->> neighbors (filter true?) (count))]
    (if is-bug
      (if (#{1} bugs)   true false)
      (if (#{1 2} bugs) true false))))


(defn tick [floor xys]
  (reduce
    (fn r [m xy]
      (let [is-bug    (floor xy)
            neighbors (->> xy u/neighbours (map floor))]
        (cond-> m
          (bug? neighbors is-bug) (assoc xy true))))
    {} xys))


(defn draw [floor]
  (println (u/draw {true \# false \. nil \.} floor \.))
  (println))


(defn left-to-right--top-to-bottom [[x1 y1] [x2 y2]]
  (compare [y1 x1] [y2 x2]))

(def points
  (->> xys
    (sort left-to-right--top-to-bottom)
    (map-indexed (fn [idx xy] [xy (Math/pow 2 idx)]))
    (into {})))

(defn score [floor]
  (->> floor (filter #(-> % val true?)) (map key) (map points) (reduce +) (int)))

xys
points


(defn f1 [input]
  (loop [floor (parse input)
         seen #{floor}]
    ;(draw floor)
    (let [floor (tick floor xys)]
      (if (seen floor)
        (score floor)
        (recur floor (conj seen floor))))))

(assert (= 2129920 (f1 sample1)))
(assert (= 1113073 (f1 input)))







(defn except-center [xys]
  (remove #{[2 2]} xys))


(def adjacent-in
  (memoize
    (fn [[x y]]
      (case [x y]
        ;; inner edges:
        [2 1] (for [x (range 5)] [x 0]) ;;top
        [3 2] (for [y (range 5)] [4 y]) ;;right
        [2 3] (for [x (range 5)] [x 4]) ;;bottom
        [1 2] (for [y (range 5)] [0 y]) ;;left
        []))))

(def adjacent-out
  (memoize
    (fn [[x y]]
      (case [x y]
        ;;corners
        [0 0] [[2 1] [1 2]]
        [4 0] [[2 1] [3 2]]
        [4 4] [[2 3] [3 2]]
        [0 4] [[2 3] [1 2]]
        ;;outer edges w/o corners
        (case x
          0 [[1 2]]
          4 [[3 2]]
          (case y
            0 [[2 1]]
            4 [[2 3]]
            []))))))



(defn tick-level [space lvl]
  (letfn [(get-neighbors [xy]
            (except-center
              (concat
                (->> xy adjacent-out (map (get space (dec lvl) {})))
                (->> xy u/neighbours (map (get space      lvl  {})))
                (->> xy adjacent-in  (map (get space (inc lvl) {}))))))
          (tick-one [m xy]
            (let [is-bug    (get-in space [lvl xy])
                  neighbors (get-neighbors xy)]
              (cond-> m
                (bug? neighbors is-bug) (assoc xy true))))]
    (->> xys
      (except-center)
      (reduce tick-one {}))))

(draw (tick-level {0 (parse input)} 0))


(def empty-floor
  (into {}
    (map vector xys (repeat false))))


(defn draw-space [space]
  (doseq [[lvl floor] (sort-by key < space)]
    (println lvl)
    (if-not (empty? floor)
      (draw (merge empty-floor floor))
      (println "empty"))))

(draw-space {-1 nil 0 (parse input)})

(defn f2 [input steps]
  (let [space {0 (parse input)}]
    (loop [steps steps
           space space]
      (if (zero? steps)
        #_(draw-space space)
        (->> space vals (mapcat vals) (filter true?) count)
        (let [levels  (->> space keys)
              lowest  (dec (reduce min levels))
              highest (inc (reduce max levels))
              levels  (range lowest (inc highest))
              tick    (partial tick-level space)
              space   (zipmap levels (map tick levels))]
          (recur (dec steps) space))))))

(time (assert (= 99 (f2 sample1 10))))
(time (assert (= 1928 (f2 input 200))))
