(ns adventofcode.2017.day14
  (:require [adventofcode.2017.day10 :as d10]
            [clojure.string :as str]
            [adventofcode.utils :as u]
            [clojure.set :as set]))


(def t "flqrgnkx")
(def i "amgozmfv")

(def knot-hash d10/f2)

(defn hex-to-bin [s]
  (->> s
    (map str)
    (map #(-> % (Integer/parseInt 16) (Integer/toBinaryString)))
    (map #(format "%4s" %))
    (str/join)))

(defn grid [input]
  (->>
    (map #(str input "-" %) (range 128))
    (map knot-hash)
    (mapv hex-to-bin)))

(assert (= 8222 (-> i grid str/join frequencies (get \1))))


(def gg (grid i))
gg
(count (first gg))

(def neighbours (memoize u/neighbours))

(let [w (count (first gg))
      h (count gg)
      db (->>
           (for [x (range w) y (range h)] (when (= \1 (get-in gg [y x])) [x y]))
           (remove nil?)
           (into #{}))]
  (loop [total  0
         region #{}
         db     db]
    (cond
      (empty? db)
      (cond-> total
        (seq region) inc)

      (empty? region)
      (let [xy (first db)
            db (disj db xy)]
        (recur total #{xy} db))

      :else
      (if-let [xys (->> region (mapcat neighbours) (remove region) (set) (set/intersection db) seq)]
        (recur total (into region xys) (reduce disj db xys))
        (recur (inc total) #{} db)))))

< 1085
1086