(ns adventofcode.2015.day20)

(set! *print-length* 10)

(def presents-limit 34000000)


(defn -f [presents-limit multiplier & [houses-limit]]
  (let [limit        (/ presents-limit multiplier)
        houses-limit (or houses-limit limit)
        set-presents (fn rf1 [!houses elf]
                       (assert (> elf 0))
                       (->> (repeat elf)
                         (reductions +)
                         (take houses-limit)
                         ;; presents count is never > than house #:
                         (take-while #(< % limit))
                         (reduce
                           (fn rf2 [!houses house]
                             (let [presents (get !houses house 0)]
                               (assoc! !houses house (+ presents elf))))
                           !houses)))
        elves        (rest (range limit))
        !houses      (transient (vec (repeat (inc limit) 0)))
        table        (reduce set-presents !houses elves)]
    (->> table
      (persistent!)
      (map-indexed vector)
      (drop-while #(-> % second (< limit)))
      (ffirst))))

(defn f1 [presents-limit] (time (-f presents-limit 10)))
(defn f2 [presents-limit] (time (-f presents-limit 11 50)))

#_
(assert (= (f1 presents-limit) 786240))
(assert (= (f2 presents-limit) 831600))
