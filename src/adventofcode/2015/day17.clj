(ns adventofcode.2015.day17
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            [clojure.math.combinatorics :as combo]))


(def input "50\n44\n11\n49\n42\n46\n18\n32\n26\n40\n21\n7\n18\n43\n10\n47\n36\n24\n22\n40")
(def test-input "20\n15\n10\n5\n5")

(defn combinations [total input]
  (->> input
    (str/split-lines)
    (map edn/read-string)
    (map vector (repeat 0))
    (apply combo/cartesian-product)
    (filter #(->> % (reduce +) (= total)))))

(defn f1 [total input]
  (->> input
    (combinations total)
    (count)))

(defn f2 [total input]
  (->> input
    (combinations total)
    (map (partial remove #{0}))
    (sort-by count)
    (partition-by count)
    (first)
    (count)))



(let [one (f1 150 input)]
  (assert (not= one 402))
  (assert (not= one 554))
  (assert (= one 654)))

(assert (= 57 (f2 150 input)))
