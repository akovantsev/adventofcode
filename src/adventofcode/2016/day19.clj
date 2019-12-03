(ns adventofcode.2016.day19)

(set! *print-length* 25)
(def N 3005290)


(defn round [elves]
  (->> elves
    (sequence
      (comp
        (map-indexed vector)
        (remove #(-> % first odd?))
        (map second)
        (map-indexed vector)
        (remove #(-> % first even?))
        (map second)))))


(->> 3005290
  (iterate #(- % (quot % 2)))
  (take-while pos?))


(not= 2796203)

2796203

(prn
  (time
    (let [limit 3005290]
      (loop [[x & todo] (cycle (range 1 (inc limit)))
             stays?     (cycle [true false])
             banned?    (transient #{})
             togo       (dec 3005290)]
        ;(prn togo)
        (cond
          (banned? x)    (recur todo stays? banned? togo)
          (zero? togo)   x
          (first stays?) (recur todo (rest stays?) banned? togo)
          :else          (recur todo (rest stays?) (conj! banned? x) (dec togo)))))))