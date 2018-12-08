(ns adventofcode.2015.day24
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            [clojure.math.combinatorics :as combo]))



(def input
  (->> "1\n3\n5\n11\n13\n17\n19\n23\n29\n31\n41\n43\n47\n53\n59\n61\n67\n71\n73\n79\n83\n89\n97\n101\n103\n107\n109\n113"
    (str/split-lines)
    (mapv edn/read-string)))

;(def input [1 2 3 4 5  7 8 9 10 11])

(set! *print-length* (* 2 (count input)))


(defn max-bag-capacity [items sum-limit]
  (->> items
    (sort <)
    (reductions +)
    (take-while #(<= % sum-limit))
    (count)))

(defn min-bag-capacity [items sum-limit]
  (->> items
    (sort >)
    (reductions +)
    (take-while #(< % sum-limit))
    (count)
    (inc)))


(defn any-intersection?
  ([[s1 & sets]]
   (any-intersection? s1 sets))

  ([s1 & [s2 & sets]]
   (when s2
     (or (some s1 s2)
         (recur (into s1 s2) sets)))))


(defn composable? [all-items weight-limit bags-count bag]
  (let [side-items (remove (set bag) all-items)
        maxcount   (max-bag-capacity side-items weight-limit)
        mincount   (min-bag-capacity side-items weight-limit)
        balanced?   #(->> % (reduce +) (= weight-limit))]
    (when (<= mincount maxcount)
      (let [lengths       (range mincount (inc maxcount))
            all-side-bags (reduce
                            (fn rf [all len]
                              (->>
                                (combo/combinations side-items len)
                                (filter balanced?)
                                (into all)))
                            [] lengths)]
        (->> all-side-bags
          (map set)
          (repeat bags-count)
          (apply combo/cartesian-product)
          (remove any-intersection?)
          (seq)
          (boolean))))))



(defn score [bag] (reduce * 1 bag))

(defn faster-partitions [items bags-count]
  (assert (= (count items) (count (set items)))) ;; assumptions
  (let [total        (reduce + items)
        weight-limit (/ total bags-count)
        maxcount     (max-bag-capacity input weight-limit)
        mincount     (min-bag-capacity input weight-limit)
        balanced?    #(->> % (reduce +) (= weight-limit))
        lengths      (range mincount (inc maxcount))
        shortest     (reduce
                       (fn rf [_ len]
                         (some->>
                           (combo/combinations items len)
                           (filter balanced?)
                           (seq)
                           (reduced)))
                       nil lengths)
        solution?     (partial composable? items weight-limit (dec bags-count))
        front-bag    (->> shortest
                       (sort-by score)
                       (filter solution?)
                       (first)
                       (score))]
    front-bag))

(defn f1 [input] (time (faster-partitions input 3)))
(defn f2 [input] (time (faster-partitions input 4)))

(assert (= (f1 input) 11266889531))
(assert (= (f2 input) 77387711))
