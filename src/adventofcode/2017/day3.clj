(ns adventofcode.2017.day3
  (:require [adventofcode.utils :as u]))


(def input 277678)

(defn f [input]
  (let [a      (-> input Math/sqrt Math/floor int)
        a      (if (odd? a) a (dec a))
        b      (+ 2 a)
        aa     (* a a)
        bb     (* b b)
        ei     (-> bb (- aa) (/ 8))
        [rb bm bl lm tl tm tr rm] (take 8 (iterate #(- % ei) bb))]
    (cond
      (= input aa) (dec a)
      (= input bb) (dec b)
      :else
      (+ (dec b)
        (cond
          (<  input rm) (- input rm)
          (<= input tr) (- input tr)
          (<  input tm) (- input tm)
          (<= input tl) (- input tl)
          (<  input lm) (- input lm)
          (<= input bl) (- input bl)
          (<  input bm) (- input bm)
          (<= input rb) (- input rb))))))


(assert (= 0 (f 1)))
(assert (= 3 (f 12)))
(assert (= 2 (f 23)))
(assert (= 31 (f 1024)))
(let [r (time (f input))]
  (assert (> 739 r))
  (assert (> 738 r))
  (assert (> 737 r))
  (assert (= 475 r)))

(reduce
  (fn rf [m]))