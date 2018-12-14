(ns adventofcode.2018.day14
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))


(def input 157901)

(defn next-scores [score1 score2]
  (map edn/read-string (-> (+ score1 score2)
                         (str)
                         (str/split #""))))

(defn adjust-idx [len idx]
  (->>
    (reductions - idx (repeat len))
    (drop-while #(>= % len))
    (first)))


(defn next-idx [len cur-idx score]
  (assert (pos? len))
  (adjust-idx len (+ cur-idx 1 score)))


(set! *print-length* 30)

(defn tick [idx1 idx2 scores]
  (let [score1     (get scores idx1)
        score2     (get scores idx2)
        new-scores (next-scores score1 score2)
        scores'    (into scores new-scores)
        len        (count scores')
        idx1'      (next-idx len idx1 score1)
        idx2'      (next-idx len idx2 score2)]
    [idx1' idx2' scores']))


(defn f1 [input]
  (let [limit (+ input 10)]
    (loop [[idx1 idx2 scores] [0 1 [3 7]]]
      (if (-> scores count (>= limit))
        (-> scores (subvec input limit) str/join)
        (recur (tick idx1 idx2 scores))))))

(assert (= (f1 9) "5158916779"))
(assert (= (f1 5) "0124515891"))
(assert (= (f1 18) "9251071085"))
(assert (= (f1 2018) "5941429882"))
(assert (= (f1 input) "9411137133"))



(defn solution [match scores right-offset]
  (let [scores-len (count scores)
        match-len  (count match)
        tail-to    (- scores-len right-offset)
        tail-from  (- tail-to match-len)]
    (when (and (<= match-len scores-len)
               (<= 0 tail-from))
      (let [tail (subvec scores tail-from tail-to)]
        (when (= match tail)
          tail-from)))))

(defn f2 [input]
  (time
    (let [match     (->> input (map str) (mapv edn/read-string))
          solution' (partial solution match)]
      (loop [[idx1 idx2 scores] [0 1 [3 7]]]
        (or
          (solution' scores 1)
          (solution' scores 0)
          (recur (tick idx1 idx2 scores)))))))


(assert (= (f2 "51589") 9))
(assert (= (f2 "01245") 5))
(assert (= (f2 "92510") 18))
(assert (= (f2 "59414") 2018))

(assert (= (f2 "157901") 20317612))
"Elapsed time: 36315.651048 msecs"
