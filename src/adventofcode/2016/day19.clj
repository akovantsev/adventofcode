(ns adventofcode.2016.day19
  (:import [clojure.lang PersistentQueue]))

(set! *print-length* 25)
(def N 3005290)


(defn p1 [input]
  (loop [q (into PersistentQueue/EMPTY (range 1 (inc input)))]
    (let [x (peek q) q (pop q)
          y (peek q) q (pop q)]
      (if (nil? y)
        x
        (recur (conj q x))))))

(assert (= (p1 5) 3))
(assert (= (p1 N) 1816277))


(defn p2 [input]
  (let [half (-> input (/ 2) Math/floor int)]
    (loop [q1 (into PersistentQueue/EMPTY (range 1 (inc half)))
           q2 (into PersistentQueue/EMPTY (range (inc half) (inc input)))]
      ;(prn (mapv vec [q1 q2]))
      (let [x (peek q1) q1 (pop q1)
            y (peek q2) q2 (pop q2)]
            ;z (peek q2) q2 (pop q2)]
        (cond
          (nil? x) y
          (nil? y) x
          :else    (if (<= (count q2) (count q1))
                     (recur q1 (conj q2 x))
                     (let [z (peek q2) q2 (pop q2)]
                       (recur (conj q1 z) (conj q2 x)))))))))

(assert (= (p2 5) 2))
(assert (= (p2 N) 1410967))