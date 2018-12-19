(ns adventofcode.2016.day15
  (:require [clojure.edn :as edn]
            [clojure.string :as str]))

(set! *print-length* 30)

(def input "Disc #1 has 5 positions; at time=0, it is at position 2.\nDisc #2 has 13 positions; at time=0, it is at position 7.\nDisc #3 has 17 positions; at time=0, it is at position 10.\nDisc #4 has 3 positions; at time=0, it is at position 2.\nDisc #5 has 19 positions; at time=0, it is at position 9.\nDisc #6 has 7 positions; at time=0, it is at position 0.")
;(def input "Disc #1 has 5 positions; at time=0, it is at position 4.\nDisc #2 has 2 positions; at time=0, it is at position 1.")

(defn parse-line [s]
  (->> s (re-seq #"\d+")
    (map edn/read-string)
    (zipmap [::disc ::amount ::start-time ::start-pos])))

(->> input str/split-lines (map parse-line))

(defn disc-timeline [m]
  (->> (range 0 (::amount m))
    (cycle)
    (drop (::start-pos m))
    (drop (::disc m))))

(def DISCS
  (->> input
    str/split-lines
    (map parse-line)
    (sort-by ::disc)
    (vec)))

(def EXTRA-DISC
  {::disc       (inc (count DISCS))
   ::start-time 0
   ::start-pos  0
   ::amount     11})


(defn snapshots [timeline]
  (apply map vector timeline))


(defn solve [discs]
  (time
    (->> discs
      (map disc-timeline)
      (snapshots)
      (map-indexed vector)
      (drop-while #(->> % second (every? zero?) not))
      (ffirst))))

(assert (= 148737  (solve DISCS)))
(assert (= 2353212 (solve (conj DISCS EXTRA-DISC))))

"Elapsed time: 468.116405 msecs"
"Elapsed time: 8170.572882 msecs"