(ns adventofcode.2016.day18
  (:require [clojure.string :as str]))


(def tile
  (fn -tile [triple]
    (case triple
      [\^ \^ \.] \^
      [\. \^ \^] \^
      [\. \. \^] \^
      [\^ \. \.] \^
      \.)))

(def tick
  (memoize
    (fn -tick [row]
      (->> (range (count row))
           (mapv #(tile [(get row (dec %) \.)
                         (get row %)
                         (get row (inc %) \.)]))))))

(defn f1 [row rows-count]
  (time
    (->> (vec row)
      (iterate tick)
      (take rows-count)
      (map str/join)
      (str/join)
      (filter #{\.})
      (count))))


(def input "......^.^^.....^^^^^^^^^...^.^..^^.^^^..^.^..^.^^^.^^^^..^^.^.^.....^^^^^..^..^^^..^^.^.^..^^..^^^..")

(assert (= 1963 (f1 input 40)))
(assert (= 20009568 (f1 input 400000)))
"Elapsed time: 21552.701718 msecs" ;; did not bother detecting pattern. zzz
