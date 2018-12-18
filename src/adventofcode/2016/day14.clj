(ns adventofcode.2016.day14
  (:require [adventofcode.utils :as u]))

(set! *print-length* 64)
(def SALT "ngcjuoqr")
;(def SALT "abc")

(defn stats [s]
  (let [parts (->> s
                (partition-by identity))]
    {:3 (->> parts (filter #(-> % count (>= 3))) ffirst)
     :5 (->> parts (filter #(-> % count (>= 5))) ffirst);(map first) set
     :s s}))

(def memo-stats (memoize stats))

(defn idx-stats [idx s]
  (-> s memo-stats (assoc :i idx)))


(defn password [[st & stats]]
  (when-let [ch (:3 st)]
    (when-let [pair (some #(when (-> % :5 (= ch)) %) stats)]
      [st pair])))


(defn generate [hashing-times]
    (->> (range)
      (map str (repeat SALT))
      (map #(->> % (iterate u/md5) (drop hashing-times) (first)))
      (map-indexed idx-stats)
      (partition 1001 1)
      (keep password)
      (drop (dec 64))
      (ffirst)
      (:i)))

(def r1 (generate 1))

(assert (< r1 22695))
(assert (< r1 21105))
(assert (= r1 18626))

#_
(assert (= 20092 (generate 2017))) ;; few minutes
