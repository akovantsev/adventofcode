(ns adventofcode.2018.day09
  (:import [java.util Deque ArrayDeque]))


(set! *print-length* 100)

(defn clockwise! [^Deque q]
  (->> (.removeFirst q) (.addLast q))
  q)

(defn counterclockwise! [^Deque q]
  (->> (.removeLast q) (.addFirst q))
  q)


(defn f2 [total-players total-marbles]
  (time
    (let [!q             (doto (new ArrayDeque) (.add 0))
          insert-marble! (fn [marble]
                           (doto !q
                             (clockwise!)
                             (.addLast marble)))
          eject-marble!  (fn []
                           (doto !q
                             (counterclockwise!)
                             (counterclockwise!)
                             (counterclockwise!)
                             (counterclockwise!)
                             (counterclockwise!)
                             (counterclockwise!)
                             (counterclockwise!))
                           (let [el (.removeLast !q)]
                             (clockwise! !q)
                             el))]
      (loop [turns  (cycle (range total-players))
             marble 1
             scores {}]
        (if (> marble total-marbles)
          (->> scores vals (reduce max))
          (let [[player & turns] turns]
            (if (= 0 (rem marble 23))
              (let [ejected (eject-marble!)
                    scores  (update scores player (fnil + 0) marble ejected)]
                (recur turns (inc marble) scores))
              (do
                (insert-marble! marble)
                (recur turns (inc marble) scores)))))))))


(assert (= (f2 9 25) 32))
(assert (= (f2 10 1618) 8317))
(assert (= (f2 10 1618) 8317))
(assert (= (f2 13 7999) 146373))
(assert (= (f2 17 1104) 2764))
(assert (= (f2 21 6111) 54718))
(assert (= (f2 30 5807) 37305))
(assert (= (f2 479 71035) 367634))
(assert (= (f2 479 7103500) 3020072891))