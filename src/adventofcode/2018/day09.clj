(ns adventofcode.2018.day09)


(defn insert-at [v ^long idx x]
  (into
    (conj (subvec v 0 idx) x)
    (subvec v idx)))

(defn remove-at [v ^long idx]
  (into
    (subvec v 0 idx)
    (subvec v (inc idx))))

(defn next-pos ^long [v ^long curr-idx ^long dif]
  (let [total    (count v)
        next-idx (+ curr-idx dif)]
    (cond
      (> next-idx total)
      (- next-idx total)

      (neg? next-idx)
      (+ total next-idx)

      :else next-idx)))

(set! *print-length* 200)
(set! *warn-on-reflection* true)
(set! *unchecked-math* true)

(defn -f [total-players total-marbles]
  (time
    (loop [turns   (->> (range total-players) (cycle) (take total-marbles))
           marble  1
           field   [0]
           cur-idx 0
           scores  (zipmap (range total-players) (repeat 0))
           score-idx 1]
      (when (zero? (rem marble 10000)) (prn marble))
      (prn field)
      (if (empty? turns)
        (->> scores vals (reduce max))
        (let [[player & turns] turns]
          (if (= 23 score-idx)
            (let [eject-idx    (next-pos field cur-idx -7)
                  eject-marble (get field eject-idx)]
              (recur turns
                (inc marble)
                (remove-at field eject-idx)
                eject-idx
                (update scores player + marble eject-marble)
                1))
            (let [insert-idx  (next-pos field cur-idx 2)]
              (recur turns
                (inc marble)
                (insert-at field insert-idx marble)
                insert-idx
                scores
                (inc score-idx)))))))))


;(assert (= (-f 9 25) 32))
;(assert (= (-f 10 1618) 8317))
;(assert (= (-f 13 7999) 146373))
;(assert (= (-f 17 1104) 2764))
;(assert (= (-f 21 6111) 54718))
;(assert (= (-f 30 5807) 37305))

;(assert (= (-f 479 71035) 367634))

#_(-f 479 7103500)