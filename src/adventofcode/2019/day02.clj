(ns adventofcode.2019.day02
  (:require
   [clojure.string :as str]
   [adventofcode.utils :as u]))

(def input "1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,10,1,19,1,19,9,23,1,23,6,27,1,9,27,31,1,31,10,35,2,13,35,39,1,39,10,43,1,43,9,47,1,47,13,51,1,51,13,55,2,55,6,59,1,59,5,63,2,10,63,67,1,67,9,71,1,71,13,75,1,6,75,79,1,10,79,83,2,9,83,87,1,87,5,91,2,91,9,95,1,6,95,99,1,99,5,103,2,103,10,107,1,107,6,111,2,9,111,115,2,9,115,119,2,13,119,123,1,123,9,127,1,5,127,131,1,131,2,135,1,135,6,0,99,2,0,14,0")

(defn parse [s]
  (mapv u/to-int (str/split s #",")))

(defn f [init x y]
  (loop [idx   0
         state (assoc init 1 x 2 y)]
    (if (= 99 (state idx))
      (first state)
      (let [idx* (+ idx 4)
            [op aidx bidx cidx] (subvec state idx idx*)
            a (state aidx)
            b (state bidx)]
        (case op
          1  (recur idx* (assoc state cidx (+ a b)))
          2  (recur idx* (assoc state cidx (* a b))))))))

(defn f1 [input] (-> input parse (f 12 2)))

(assert (= 4138658 (f1 input)))


(defn f2 [input]
  (let [init (parse input)]
    (reduce
      (fn rf [_ [x y]]
        (when (= 19690720 (f init x y))
          (reduced (+ y (* 100 x)))))
      nil
      (for [x (range 100)
            y (range 100)]
        [x y]))))

(assert (= 7264 (f2 input)))