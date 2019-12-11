(ns adventofcode.2019.day10
  (:require [clojure.string :as str])
  (:import [clojure.lang PersistentQueue]))

(def input ".#....#.###.........#..##.###.#.....##...\n...........##.......#.#...#...#..#....#..\n...#....##..##.......#..........###..#...\n....#....####......#..#.#........#.......\n...............##..#....#...##..#...#..#.\n..#....#....#..#.....#.#......#..#...#...\n.....#.#....#.#...##.........#...#.......\n#...##.#.#...#.......#....#........#.....\n....##........#....#..........#.......#..\n..##..........##.....#....#.........#....\n...#..##......#..#.#.#...#...............\n..#.##.........#...#.#.....#........#....\n#.#.#.#......#.#...##...#.........##....#\n.#....#..#.....#.#......##.##...#.......#\n..#..##.....#..#.........#...##.....#..#.\n##.#...#.#.#.#.#.#.........#..#...#.##...\n.#.....#......##..#.#..#....#....#####...\n........#...##...#.....#.......#....#.#.#\n#......#..#..#.#.#....##..#......###.....\n............#..#.#.#....#.....##..#......\n...#.#.....#..#.......#..#.#............#\n.#.#.....#..##.....#..#..............#...\n.#.#....##.....#......##..#...#......#...\n.......#..........#.###....#.#...##.#....\n.....##.#..#.....#.#.#......#...##..#.#..\n.#....#...#.#.#.......##.#.........#.#...\n##.........#............#.#......#....#..\n.#......#.............#.#......#.........\n.......#...##........#...##......#....#..\n#..#.....#.#...##.#.#......##...#.#..#...\n#....##...#.#........#..........##.......\n..#.#.....#.....###.#..#.........#......#\n......##.#...#.#..#..#.##..............#.\n.......##.#..#.#.............#..#.#......\n...#....##.##..#..#..#.....#...##.#......\n#....#..#.#....#...###...#.#.......#.....\n.#..#...#......##.#..#..#........#....#..\n..#.##.#...#......###.....#.#........##..\n#.##.###.........#...##.....#..#....#.#..\n..........#...#..##..#..##....#.........#\n..#..#....###..........##..#...#...#..#..")

(defn parse [s]
  (let [Z (->> s str/split-lines (mapv vec))
        W (-> Z first count)
        H (-> Z count)]
    (set
      (for [x (range W)
            y (range H)
            :when (not= \. (get-in Z [y x]))]
        [x y]))))


(defn distance [xy1 xy2]
  (->>
    (map
      (fn [a b] (Math/pow (- a b) 2))
      xy1 xy2)
    (reduce +)))

(assert (= 5.0 (distance [1 2] [3 3])))

(defn rotate [deg]
  (let [deg (+ deg 90)]
    (cond-> deg (neg? deg) (+ 360))))

(defn angle [[x1 y1] [x2 y2]]
  (rotate
   (Math/toDegrees (Math/atan2 (- y2 y1) (- x2 x1)))))

(defn angles [xys xy]
  (group-by (partial angle xy) (disj xys xy)))

(defn best-of [s]
  (let [xys (parse s)]
    (->> xys
      (map (fn [xy] {::xy xy ::score (->> xy (angles xys) count)}))
      (apply max-key ::score))))


(assert (= {::xy [3 4] ::score 8} (best-of ".#..#\n.....\n#####\n....#\n...##")))
(assert (= {::xy [5 8] ::score 33} (best-of "......#.#.\n#..#.#....\n..#######.\n.#.#.###..\n.#..#.....\n..#....#.#\n#..#....#.\n.##.#..###\n##...#..#.\n.#....####")))
(assert (= {::xy [1 2] ::score 35} (best-of "#.#...#.#.\n.###....#.\n.#....#...\n##.#.#.#.#\n....#.#.#.\n.##..###.#\n..#...##..\n..##....##\n......#...\n.####.###.")))
(assert (= {::xy [11 13] ::score 210} (best-of ".#..##.###...#######\n##.############..##.\n.#.######.########.#\n.###.#######.####.#.\n#####.##.#.##.###.##\n..#####..#.#########\n####################\n#.####....###.#.#.##\n##.#################\n#####.##.###..####..\n..######..##.#######\n####.##.####...##..#\n.#####..#.######.###\n##...#.##########...\n#.##########.#######\n.####.#.###.###.#.##\n....##.##.###..#####\n.#.#.###########.###\n#.#.#.#####.####.###\n###.##.####.##.#..##")))

(let [r (best-of input)]
  (assert (not= (::score r) 364))
  (assert (= r {::xy [28 29] ::score 340})))


(defn score2 [[x y]]
  (-> x (* 100) (+ y)))

(defn f2 [input xy n]
  (let [
        ;xys      (parse ".#..##.###...#######\n##.############..##.\n.#.######.########.#\n.###.#######.####.#.\n#####.##.#.##.###.##\n..#####..#.#########\n####################\n#.####....###.#.#.##\n##.#################\n#####.##.###..####..\n..######..##.#######\n####.##.####...##..#\n.#####..#.######.###\n##...#.##########...\n#.##########.#######\n.####.#.###.###.#.##\n....##.##.###..#####\n.#.#.###########.###\n#.#.#.#####.####.###\n###.##.####.##.#..##")
        ;center  [11 13]
        ;xys      (parse ".#....#####...#..\n##...##.#####..##\n##...#...#.#####.\n..#.....X...###..\n..#.#.....#....##");
        ;center   [8 3]
        xys      (parse input)
        center   xy
        db       (angles xys center)
        distance (partial distance center)
        q        (->> db
                   (sort-by key <)
                   (map val)
                   (map #(sort-by distance < %))
                   (into (PersistentQueue/EMPTY)))
        targets  (loop [!done (transient [])
                        q     q]
                   (if (empty? q)
                     (persistent! !done)
                     (let [[xy & todo] (peek q)]
                       (recur
                         (conj! !done xy)
                         (cond-> (pop q)
                           todo (conj todo))))))]
    (->> targets (drop (dec n)) (first) score2)))


(assert (= 2628 (f2 input [28 29] 200)))