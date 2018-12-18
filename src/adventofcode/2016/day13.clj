(ns adventofcode.2016.day13
  (:require [clojure.pprint :refer [cl-format]]))

(defn int-to-binary-repr [^long x]
  ;; https://stackoverflow.com/a/21450431
  (cl-format nil "~6,'0',B" x))

(def INPUT 1358)
(set! *unchecked-math* true)
(set! *print-length* 50)

(def wall?
  (memoize
    (fn -wall? [[^long x ^long y]]
      (or (neg? x)
          (neg? y)
          (->> (+ (* x x) (* 3 x) (* 2 x y) y (* y y))
            (+ INPUT)
            (int-to-binary-repr)
            (filter #{\1})
            (count)
            (odd?))))))

(defn next-xys [[x y]]
  [,,,,,,,,,,, [x (dec y)] ,,,,,,,,,,,
   [(dec x) y] ,,,,,,,,,,, [(inc x) y]
   ,,,,,,,,,,, [x (inc y)] ,,,,,,,,,,,])



(def r1
  (time
    (loop [todo [[#{} [1 1]]]
           len  ##Inf]
      (if (empty? todo)
        (dec len)
        (let [[visited xy] (peek todo)
              todo         (pop todo)
              visited      (conj visited xy)
              branches     (->> xy
                             (next-xys)
                             (remove wall?)
                             (remove visited)
                             (map vector (repeat visited)))]
          (cond
            (< len (count visited)) (recur todo len)
            (empty? branches)       (recur todo len)
            (= [31 39] xy)          (recur todo (min len (count visited)))
            :else                   (recur (into todo branches) len)))))))


(def r2
  (time
    (loop [todo   [[#{} [1 1]]]
           places #{}]
      (if (empty? todo)
        (count places)
        (let [[visited xy] (peek todo)
              todo     (pop todo)
              visited  (conj visited xy)
              branches (->> xy
                         (next-xys)
                         (remove wall?)
                         (remove visited)
                         (map vector (repeat visited)))]
          (cond
            (-> visited count (= 51)) (recur todo (into places visited))
            (empty? branches)         (recur todo (into places visited))
            :else                     (recur (into todo branches) places)))))))


(assert (= r1 96))
(assert (not= r2 15))
(assert (not= r2 50))
(assert (not= r2 51))
(assert (not= r2 136))
(assert (= r2 141))