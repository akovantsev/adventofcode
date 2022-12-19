(ns adventofcode.y2022.day19
  (:require [clojure.string :as str]
            [com.akovantsev.blet.core :refer [blet blet!]]
            [adventofcode.utils :as u]
            [clojure.data.priority-map :as pm]
            [clojure.math.combinatorics :as combo]))


(def sample "Blueprint 1: Each ore robot costs 4 ore.  Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.\nBlueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.")
(def input "Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 2 ore and 7 clay. Each geode robot costs 4 ore and 13 obsidian.\nBlueprint 2: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 15 clay. Each geode robot costs 3 ore and 8 obsidian.\nBlueprint 3: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 18 clay. Each geode robot costs 4 ore and 9 obsidian.\nBlueprint 4: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 20 clay. Each geode robot costs 2 ore and 12 obsidian.\nBlueprint 5: Each ore robot costs 4 ore. Each clay robot costs 3 ore. Each obsidian robot costs 4 ore and 8 clay. Each geode robot costs 3 ore and 7 obsidian.\nBlueprint 6: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 4 ore and 15 obsidian.\nBlueprint 7: Each ore robot costs 4 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 11 clay. Each geode robot costs 4 ore and 7 obsidian.\nBlueprint 8: Each ore robot costs 2 ore. Each clay robot costs 4 ore. Each obsidian robot costs 2 ore and 15 clay. Each geode robot costs 3 ore and 16 obsidian.\nBlueprint 9: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 17 clay. Each geode robot costs 3 ore and 10 obsidian.\nBlueprint 10: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 2 ore and 8 clay. Each geode robot costs 3 ore and 9 obsidian.\nBlueprint 11: Each ore robot costs 4 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 18 clay. Each geode robot costs 4 ore and 8 obsidian.\nBlueprint 12: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 12 clay. Each geode robot costs 4 ore and 19 obsidian.\nBlueprint 13: Each ore robot costs 4 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 17 clay. Each geode robot costs 3 ore and 13 obsidian.\nBlueprint 14: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 2 ore and 10 clay. Each geode robot costs 3 ore and 14 obsidian.\nBlueprint 15: Each ore robot costs 3 ore. Each clay robot costs 3 ore. Each obsidian robot costs 2 ore and 16 clay. Each geode robot costs 2 ore and 18 obsidian.\nBlueprint 16: Each ore robot costs 2 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 17 clay. Each geode robot costs 3 ore and 11 obsidian.\nBlueprint 17: Each ore robot costs 3 ore. Each clay robot costs 4 ore. Each obsidian robot costs 3 ore and 18 clay. Each geode robot costs 4 ore and 16 obsidian.\nBlueprint 18: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 9 clay. Each geode robot costs 3 ore and 9 obsidian.\nBlueprint 19: Each ore robot costs 3 ore. Each clay robot costs 4 ore. Each obsidian robot costs 3 ore and 18 clay. Each geode robot costs 4 ore and 19 obsidian.\nBlueprint 20: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 2 ore and 17 clay. Each geode robot costs 3 ore and 19 obsidian.\nBlueprint 21: Each ore robot costs 3 ore. Each clay robot costs 3 ore. Each obsidian robot costs 4 ore and 19 clay. Each geode robot costs 4 ore and 7 obsidian.\nBlueprint 22: Each ore robot costs 4 ore. Each clay robot costs 3 ore. Each obsidian robot costs 2 ore and 13 clay. Each geode robot costs 2 ore and 9 obsidian.\nBlueprint 23: Each ore robot costs 3 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 5 clay. Each geode robot costs 3 ore and 12 obsidian.\nBlueprint 24: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 11 clay. Each geode robot costs 4 ore and 12 obsidian.\nBlueprint 25: Each ore robot costs 2 ore. Each clay robot costs 2 ore. Each obsidian robot costs 2 ore and 7 clay. Each geode robot costs 2 ore and 14 obsidian.\nBlueprint 26: Each ore robot costs 4 ore. Each clay robot costs 3 ore. Each obsidian robot costs 2 ore and 19 clay. Each geode robot costs 3 ore and 13 obsidian.\nBlueprint 27: Each ore robot costs 3 ore. Each clay robot costs 3 ore. Each obsidian robot costs 2 ore and 12 clay. Each geode robot costs 2 ore and 10 obsidian.\nBlueprint 28: Each ore robot costs 4 ore. Each clay robot costs 3 ore. Each obsidian robot costs 4 ore and 5 clay. Each geode robot costs 3 ore and 10 obsidian.\nBlueprint 29: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 2 ore and 11 clay. Each geode robot costs 4 ore and 8 obsidian.\nBlueprint 30: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 8 clay. Each geode robot costs 4 ore and 14 obsidian.")
(defn parse1 [line]
  (let [rep   #"\d+|ore|clay|obsidian|geode"
        [head tail] (str/split line #":")
        id    (->> head (re-seq rep) first u/to-int)
        each  (str/split tail #"\.")
        p1    (fn [line]
                (let [ys   (->> line (re-seq rep) (map read-string))
                      unit (->> ys first)
                      cost (->> ys rest reverse (partition 2) (map vec) (into {}))]
                  {unit cost}))
        costs (->> each (map p1) (reduce merge))]
    {::id id ::costs costs}))


(->> sample str/split-lines (map parse1))





(def TIMELIMIT1 24)
(def TIMELIMIT2 32)

(def plus (fnil + 0))
(def minus (fnil - 0))
(def extra (memoize (fn [timeleft] (reduce + (range timeleft)))))
(defn third [x] (first (next (next x))))

(defn estimate [state]
  (let [{:keys [::t-left ::income ::money ::factory]} state
        now  (get money 'geode 0)
        bot  (get income 'geode 0)
        soon (get factory 'geode 0)]
    [0 (+ now (* t-left bot) (extra t-left) (dec (* t-left soon))) (- t-left)]))

(mapv estimate
  [{::t-left 5 ::income {'geode 1} ::money {'geode 1} ::factory {'geode 1}}
   {::t-left 5 ::income {'geode 1} ::money {'geode 1} ::factory {'geode 0}}
   {::t-left 5 ::income {'geode 1} ::money {'geode 0} ::factory {'geode 0}}
   {::t-left 5 ::income {'geode 0} ::money {'geode 0} ::factory {'geode 0}}
   {::t-left 4 ::income {'geode 1} ::money {'geode 1} ::factory {'geode 1}}
   {::t-left 4 ::income {'geode 1} ::money {'geode 1} ::factory {'geode 0}}
   {::t-left 4 ::income {'geode 1} ::money {'geode 0} ::factory {'geode 0}}
   {::t-left 4 ::income {'geode 0} ::money {'geode 0} ::factory {'geode 0}}])

(mapv estimate
  [{::t-left 5 ::income {'geode 1} ::money {'geode 0} ::factory {'geode 0}}
   {::t-left 5 ::income {'geode 0} ::money {'geode 0} ::factory {'geode 1}}
   {::t-left 5 ::income {'geode 0} ::money {'geode 0} ::factory {'geode 0}}])

(defn DESC [a b] (compare b a))


(let [a {::t-left 5 ::income {'ore 1} ::money {'ore 2} ::factory {}}
      b {::t-left 5 ::income {'ore 1} ::money {'ore 3} ::factory {}}
      c {::t-left 4 ::income {'ore 1} ::money {'ore 3} ::factory {}}]
  (->
    (pm/priority-map-by DESC)
    (assoc a (estimate a))
    (assoc b (estimate b))
    (assoc c (estimate c))))
    ;(first)))

(defn tick [state]
  (let [{:keys [::t-left ::income ::money ::factory]} state
        state+ {::income  (merge-with plus income factory)
                ::money   (merge-with plus money income)
                ::factory {}
                ::t-left  (dec t-left)}]
    (assoc state+ ::estimate (estimate state+))))

(defn could-buy? [state [unit price]]
  ;(prn 'could-buy? unit price state)
  (every?
    (-> state ::income keys set)
    (-> price keys)))


(could-buy? {::income {'ore 1}} [:foo {'ore 2}])
(could-buy? {::income {'ore 1}} [:foo {'bar 3}])
(could-buy? {::income {'ore 1}} [:foo {'ore2 2}])

(defn buy-one [state [unit price]]
  ;(prn 'buyone? unit price state)
  (let [state+ (tick state)
        left   (merge-with - (::money state+) price)]
    (if (->> left vals (some neg?))
      (recur state+ [unit price])
      (-> state+
        (update ::factory update unit plus 1)
        (assoc ::money left)))))

(buy-one {::t-left 5 ::money {'ore 0} ::income {'ore 1}} [:foo {'ore 2}])
;=> #:adventofcode.y2022.day19{:income {ore 1}, :money {ore 0}, :factory {:foo 1}, :t-left 3, :estimate [0 2 -3]}



(defn score [timelimit recipe]
  (time
    (let [{:keys [::id ::costs]} recipe
          max-demand (->> costs vals (apply merge-with (fnil max 0)))
          start      {::t-left  timelimit
                      ::income  {'ore 1}
                      ::money   {'ore 0 'clay 0 'obsidian 0 'geode 0}  ;(merge-with - {} {:a 6}) => {:a 6} kek
                      ::factory {}}
          noneed?    (fn [state [unit price]]
                       (<= (max-demand unit ##Inf) (-> state ::income (get unit 0))))
          geodes     #(-> % ::money (get 'geode 0))
          EMPTY      (pm/priority-map-by DESC)
          enq        (fn [q x] (assoc q x (::estimate x)))
          best-t nil
          best-t+ nil]
      (loop [todo       (enq EMPTY (assoc start ::estimate (estimate start)))
             best-t     {}
             best-geo   0]
        (if (empty? todo)
          best-geo
          (blet [todo-     (-> todo pop)
                 state     (-> todo peek key)
                 t-left    (-> state ::t-left)
                 hope      (-> state ::estimate second)
                 state+    (->> costs
                             (remove #(noneed? state %))
                             (filter #(could-buy? state %))
                             (map #(buy-one state %)))
                 todo+     (reduce enq todo- state+)
                 geo       (geodes state)]
                 ;best-hope (get best-t t-left 0)
                 ;best-t+   (assoc best-t t-left hope)]
            (cond
              (neg? t-left),,,,, (recur todo- best-t best-geo)
              (< hope best-geo), (recur todo- best-t best-geo)
              ;(< hope best-hope) (recur todo- best-t best-geo)
              (< best-geo geo),, (do ;(prn geo state best-t+)
                                     (recur todo+ best-t+ geo))
              :else,,,,,,,,,,,,, (recur todo+ best-t+ best-geo))))))))

(defn p1 [ss]
  (->> ss
    str/split-lines
    (map parse1)
    (map (partial score TIMELIMIT1))
    (map (partial apply *) (rest (range)))
    (reduce + 0)
    time))

(->> sample
  str/split-lines
  (map parse1)
  ;(take 1)
  (map (partial score TIMELIMIT2))
  time)

(defn p2 [ss]
  (->> ss
    str/split-lines
    (map parse1)
    (take 3)
    (map (partial score TIMELIMIT2))
    u/spy
    (reduce * 1)
    time))

;(assert (= 33 (p1 sample)))
;(assert (= 1565 (p1 input)))
"Elapsed time: 19290.007506 msecs"

;(assert (= 10672 (p2 input)))
"Elapsed time: 23966.599927 msecs" ;; with commented out best-t heuristic

;; not gonna clean this up


