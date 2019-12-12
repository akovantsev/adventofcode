(ns adventofcode.2019.day12
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]
            [adventofcode.utils :as u]))


(def input "<x=4, y=12, z=13>\n<x=-9, y=14, z=-3>\n<x=-7, y=-1, z=2>\n<x=-11, y=17, z=-1>")

(defn parse-line [s] (->> s (re-seq #"[-]?\d+") (mapv u/to-int)))

(defn parse [s] (->> s (str/split-lines) (mapv parse-line)))


(def positions (parse input))


(defn init-velocities [positions]
  (->> [0 0 0]
    (repeat (count positions))
    (into [])))

(def idx-pairs (-> positions count range (combo/combinations 2)))
(def empty-diffs (-> positions count (repeat []) (vec)))

(defn energy [xyz]
  (->> xyz (map #(Math/abs ^int %)) (reduce +)))

(defn total-moon-energy [pxyz vxyz]
  (* (energy pxyz) (energy vxyz)))

(defn total-system-energy [pos vel]
  (->>
    (map total-moon-energy pos vel)
    (reduce +)))

(defn gravity-axis-diff [x y]
  (cond
    (= x y) [identity identity]
    (< x y) [inc dec]
    (> x y) [dec inc]))

(defn gravity-pair-diffs [v1 v2]
  (->>
    (mapv gravity-axis-diff v1 v2)
    (apply map vector)))

(defn gravity-diffs [positions]
  (->> idx-pairs
    (reduce
      (fn f1 [diffs [idx1 idx2 :as idx-pair]]
        (let [[m1 m2] (map positions idx-pair)
              [d1 d2] (gravity-pair-diffs m1 m2)]
          (-> diffs
            (update idx1 conj d1)
            (update idx2 conj d2))))
      empty-diffs)
    (reduce
      (fn f2 [diffs triplets]
        (->> triplets
          (apply map comp)
          (conj diffs)))
      [])))

(defn apply-diff [xyz diff]
  (mapv #(%1 %2) diff xyz))

(defn apply-gravity [positions velocities]
  (mapv apply-diff velocities (gravity-diffs positions)))

(defn apply-velocity [positions velocities]
  (mapv #(mapv + %1 %2) positions velocities))

(defn tick [[pos vel]]
  (let [vel (apply-gravity pos vel)
        pos (apply-velocity pos vel)]
    [pos vel]))

(defn f1 [steps input]
  (let [pos (parse input)
        vel (init-velocities pos)]
    (->> [pos vel]
      (iterate tick)
      (drop steps)
      (first)
      (apply total-system-energy))))

(def sample1 "<x=-1, y=0, z=2>\n<x=2, y=-10, z=-7>\n<x=4, y=-8, z=8>\n<x=3, y=5, z=-1>")
(def sample2 "<x=-8, y=-10, z=0>\n<x=5, y=5, z=10>\n<x=2, y=-7, z=3>\n<x=9, y=-8, z=-3>")
(assert (= 179 (f1 10 sample1)))
(assert (= 1940 (f1 100 sample2)))
(assert (= 5350 (time (f1 1000 input))))


(defn by-axis [pos vel]
  (apply mapv vector (concat pos vel)))


(defn GCD [x y]
  (loop [x (Math/abs ^long x)
         y (Math/abs ^long y)]
    (if (zero? y)
      x
      (recur y (mod x y)))))

(defn LCM [x y]
  (Math/abs ^long (/ (* x y) (GCD x y))))

(defn conj-or-count [seen v]
  (if (int? seen)
    seen
    (let [seen* (conj seen v)
          c     (count seen)]
      (if (= c (count seen*))
        c
        seen*))))


(defn f2 [input]
  (loop [pos  (parse input)
         vel  (init-velocities pos)
         seen [#{} #{} #{}]]
    (if (every? int? seen)
      (reduce LCM seen)
      (let [vel  (apply-gravity pos vel)
            pos  (apply-velocity pos vel)
            axes (by-axis pos vel)
            seen (mapv conj-or-count seen axes)]
        (recur pos vel seen)))))

;[167624 231614 96236]
;467034091553512

(assert (= (time (f2 sample1)) 2772))
(assert (= (time (f2 sample2)) 4686774924))
(assert (= (time (f2 input)) 467034091553512))

;; positions and velocities per axis are independent,
;; so everything above can be refactored to run 3 separate steps per axis
;; for each part instead of running one step for all axes.