(ns adventofcode.2016.day11
  (:require [clojure.math.combinatorics :as combo])
  (:import [clojure.lang PersistentQueue]))

(defn done? [floors]
   (->> (range 3)
     (map floors)
     (map count)
     (every? zero?)))

(def chip? (memoize (fn -chip? [x] (-> x name (= "chi")))))
(def gen?  (memoize (fn -gen?  [x] (-> x name (= "gen")))))
(def safe? (memoize (fn -safe? [gens chip] (or (empty? gens) (contains? gens chip)))))

(def disj-many (partial apply disj))

(defn valid? [floor]
  (let [chips (->> floor (filter chip?) (map namespace))
        gens  (->> floor (filter gen?)  (map namespace) set)
        safe? (partial safe? gens)]
    (every? safe? chips)))

(def memo-valid? (memoize valid?))

(defn -move [{:as state ::keys [idx steps floors]} items floor-idx-pred next-floor-fn]
  (when (floor-idx-pred idx)
    (let [next-idx    (next-floor-fn idx)
          next-floors (-> floors
                        (update idx disj-many items)
                        (update next-idx into items))]
      (when (every? memo-valid? next-floors)
        {::idx    next-idx
         ::steps  (inc steps)
         ::floors next-floors}))))

(defn move-up [state items] (-move state items #(< % 3) inc))
(defn move-dn [state items] (-move state items #(< 0 %) dec))


(defn get-payloads [floor]
  (let [xs          (seq floor)
        all-singles (map vector xs)
        all-pairs   (->> (combo/combinations xs 2) (map set) (set))]
    (into all-singles all-pairs)))

(def memo-payloads (memoize get-payloads))


(defn next-states [{:as state ::keys [floors idx]}]
  (let [floor    (get floors idx)
        payloads (memo-payloads floor)]
    (remove nil?
      (into
        (map (partial move-up state) payloads)
        (map (partial move-dn state) payloads)))))


(defn floor-hash [floor]
  (->> floor
    (group-by namespace)
    (vals)
    (map #(->> % (map name) (set)))
    (frequencies)))

(defn state-hash [{:as state ::keys [floors idx]}]
  [idx (mapv floor-hash floors)])


(defn solve [floors]
  (time
    (loop [min-steps ##Inf
           todo      (conj (PersistentQueue/EMPTY)
                       {::steps  0
                        ::idx    0
                        ::log    []
                        ::floors floors})
           !seen     (transient #{})
           solution  nil]
      (if (empty? todo)
        solution
        (let [state (peek todo)
              todo  (pop todo)
              {::keys [floors steps]} state
              shash (state-hash state)]
          (cond
            (nil? state)        (recur min-steps todo !seen solution)
            (< min-steps steps) (recur min-steps todo !seen solution)
            (done? floors)      (recur (min min-steps steps) todo !seen state)
            (!seen shash)       (recur min-steps todo !seen solution)
            :else               (recur min-steps
                                  (into todo (next-states state))
                                  (conj! !seen shash)
                                  solution)))))))


;The first floor contains a promethium generator and a promethium-compatible microchip.
;The second floor contains a cobalt generator, a curium generator, a ruthenium generator, and a plutonium generator.\n
;The third floor contains a cobalt-compatible microchip, a curium-compatible microchip, a ruthenium-compatible microchip, and a plutonium-compatible microchip.\n
;The fourth floor contains nothing relevant."

(defn f1 []
  (solve
    [#{:pro/gen :pro/chi}
     #{:cob/gen :cur/gen :rut/gen :plu/gen}
     #{:cob/chi :cur/chi :rut/chi :plu/chi}
     #{}]))


(defn f2 []
  (solve
    [#{:pro/gen :pro/chi :el/gen :el/chi :del/gen :del/chi}
     #{:cob/gen :cur/gen :rut/gen :plu/gen}
     #{:cob/chi :cur/chi :rut/chi :plu/chi}
     #{}]))


(assert (= 33 (::steps (f1))))
"Elapsed time: 756.429605 msecs"

(assert (= 57 (::steps (f2))))
"Elapsed time: 3473.872745 msecs"

