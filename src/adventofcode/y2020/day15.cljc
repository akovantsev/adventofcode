(ns adventofcode.y2020.day15)


(def t [0,3,6])
(def i [14,1,17,0,3,20])

(defn f [input n]
  (loop [turn  (count input)
         hist  (zipmap input (range 1 ##Inf))
         prev  (peek input)]
    (if (= n turn)
      prev
      (recur
        (inc turn)
        (assoc hist prev turn)
        (if-let [last (get hist prev)]
          (- turn last)
          0)))))

(assert (= 436 (f t 2020)))
(assert (= 387 (f i 2020)))
(time (assert (= 6428 (f i 30000000))))

;;387
;;6428