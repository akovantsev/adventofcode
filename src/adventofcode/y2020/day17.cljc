(ns adventofcode.y2020.day17
  (:require
   [clojure.string :as str]
   [clojure.math.combinatorics :as combo]))


(def t ".#.\n..#\n###")
(def i "#...#.#.\n..#.#.##\n..#..#..\n.....###\n...#.#.#\n#.#.##..\n#####...\n.#.#.##.")

(defn parse [tail s]
  (let [lines (str/split-lines s)]
    (into #{}
      (for [x (range 0 (count (first lines)))
            y (range 0 (count lines))
            :when (= \# (get-in lines [y x]))]
        (into [x y] tail)))))


(defn neighbours2 [coords]
  (let [f      (juxt dec identity inc)
        ranges (map f coords)]
    (apply combo/cartesian-product ranges)))

(def membours (memoize neighbours2))

(defn active? [state xyz]
  (let [actives (->> xyz membours (filter state) (remove #{xyz}) count)]
    (case [(contains? state xyz) actives]
      [true 2] xyz
      [true 3] xyz
      [false 3] xyz
      nil)))

(defn dimension [coll]
  (let [xs (->> coll (sort <) vec)]
    (range (dec (first xs))  (+ 2 (peek xs)))))

(defn xyzs [actives]
  (->> actives
    (apply map vector)
    (map dimension)
    (apply combo/cartesian-product)))

(defn step [actives]
  (->> actives
    (xyzs)
    (keep (partial active? actives))
    (set)))

(defn solve [input extra-dimensions]
  (->> input
    (parse extra-dimensions)
    (iterate step)
    (drop 6)
    (first)
    (count)))

(solve i [0])
;401
(solve i [0 0])
;2224