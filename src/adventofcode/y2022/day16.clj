(ns adventofcode.y2022.day16
  (:require [clojure.string :as str]
            [com.akovantsev.blet.core :refer [blet blet!]]
            [adventofcode.utils :as u]
            [clojure.data.priority-map :as pm]))

(def START "AA")
(def TIMELIMIT1 30)
(def TIMELIMIT2 26)
(def sample "Valve AA has flow rate=0; tunnels lead to valves DD, II, BB\nValve BB has flow rate=13; tunnels lead to valves CC, AA\nValve CC has flow rate=2; tunnels lead to valves DD, BB\nValve DD has flow rate=20; tunnels lead to valves CC, AA, EE\nValve EE has flow rate=3; tunnels lead to valves FF, DD\nValve FF has flow rate=0; tunnels lead to valves EE, GG\nValve GG has flow rate=0; tunnels lead to valves FF, HH\nValve HH has flow rate=22; tunnel leads to valve GG\nValve II has flow rate=0; tunnels lead to valves AA, JJ\nValve JJ has flow rate=21; tunnel leads to valve II")
(def input "Valve RT has flow rate=0; tunnels lead to valves EN, LZ\nValve VB has flow rate=0; tunnels lead to valves SZ, BF\nValve AD has flow rate=0; tunnels lead to valves EB, JF\nValve RE has flow rate=4; tunnels lead to valves QB, IF, XT, WF, KW\nValve RL has flow rate=0; tunnels lead to valves DQ, LZ\nValve OK has flow rate=0; tunnels lead to valves QH, BF\nValve RV has flow rate=0; tunnels lead to valves IU, JF\nValve TE has flow rate=0; tunnels lead to valves HE, XF\nValve WW has flow rate=0; tunnels lead to valves QH, YZ\nValve HB has flow rate=15; tunnel leads to valve OM\nValve IY has flow rate=14; tunnels lead to valves UH, KW, BN, LW, UY\nValve QF has flow rate=0; tunnels lead to valves JF, PL\nValve YZ has flow rate=0; tunnels lead to valves JG, WW\nValve QB has flow rate=0; tunnels lead to valves SP, RE\nValve SO has flow rate=0; tunnels lead to valves QH, SZ\nValve EB has flow rate=7; tunnels lead to valves IF, NH, AD, VI, DQ\nValve VL has flow rate=0; tunnels lead to valves JF, YV\nValve BF has flow rate=18; tunnels lead to valves OK, VB, OH, SX\nValve UC has flow rate=0; tunnels lead to valves SC, YV\nValve OQ has flow rate=0; tunnels lead to valves XT, AA\nValve YV has flow rate=6; tunnels lead to valves YX, TT, VL, UC, NH\nValve KJ has flow rate=0; tunnels lead to valves OH, JG\nValve QH has flow rate=20; tunnels lead to valves SO, OK, WW\nValve KW has flow rate=0; tunnels lead to valves RE, IY\nValve PL has flow rate=0; tunnels lead to valves JG, QF\nValve DQ has flow rate=0; tunnels lead to valves EB, RL\nValve AA has flow rate=0; tunnels lead to valves YI, EN, UK, OQ, VI\nValve XT has flow rate=0; tunnels lead to valves OQ, RE\nValve SZ has flow rate=24; tunnels lead to valves VB, SO\nValve IU has flow rate=25; tunnels lead to valves RV, HE, HQ\nValve OM has flow rate=0; tunnels lead to valves NY, HB\nValve YX has flow rate=0; tunnels lead to valves YV, SI\nValve SX has flow rate=0; tunnels lead to valves ZB, BF\nValve KD has flow rate=0; tunnels lead to valves XF, LW\nValve SP has flow rate=0; tunnels lead to valves XF, QB\nValve UY has flow rate=0; tunnels lead to valves UK, IY\nValve XF has flow rate=22; tunnels lead to valves SP, TE, KD, NY\nValve SC has flow rate=0; tunnels lead to valves LZ, UC\nValve UK has flow rate=0; tunnels lead to valves UY, AA\nValve LW has flow rate=0; tunnels lead to valves KD, IY\nValve FL has flow rate=0; tunnels lead to valves BN, LZ\nValve VI has flow rate=0; tunnels lead to valves AA, EB\nValve HW has flow rate=0; tunnels lead to valves JF, CY\nValve YI has flow rate=0; tunnels lead to valves AA, TT\nValve HE has flow rate=0; tunnels lead to valves IU, TE\nValve JG has flow rate=10; tunnels lead to valves PL, YZ, SI, KJ\nValve BN has flow rate=0; tunnels lead to valves IY, FL\nValve IF has flow rate=0; tunnels lead to valves EB, RE\nValve JF has flow rate=19; tunnels lead to valves HW, QF, VL, RV, AD\nValve SI has flow rate=0; tunnels lead to valves JG, YX\nValve WF has flow rate=0; tunnels lead to valves LZ, RE\nValve HQ has flow rate=0; tunnels lead to valves IU, UH\nValve LZ has flow rate=5; tunnels lead to valves SC, FL, WF, RL, RT\nValve UH has flow rate=0; tunnels lead to valves IY, HQ\nValve CY has flow rate=21; tunnel leads to valve HW\nValve NH has flow rate=0; tunnels lead to valves EB, YV\nValve TT has flow rate=0; tunnels lead to valves YV, YI\nValve OH has flow rate=0; tunnels lead to valves KJ, BF\nValve EN has flow rate=0; tunnels lead to valves RT, AA\nValve NY has flow rate=0; tunnels lead to valves OM, XF\nValve ZB has flow rate=8; tunnel leads to valve SX")

(defn parse1 [s]
  (let [ids (re-seq #"[A-Z]{2}" s)]
    {(first ids) {::rate (-> s u/str->ints first)
                  ::ids  (rest ids)}}))

(defn p1 [ss]
  (let [DB    (->> ss str/split-lines (map parse1) (reduce merge))
        OPEN  (->> DB (filter #(-> % val ::rate zero?)) (map key) set)
        START {[START OPEN] 0}

        step  (fn step [[[at opened] score]]
                (let [ids     (-> at DB ::ids)
                      opened+ (conj opened at)
                      score+  (->> opened (map DB) (map ::rate) (reduce + score))
                      state+  (fn m1 [id] {[id opened] score+})
                      moves   (map state+ ids)
                      open    {[at opened+] score+}]
                  (conj moves open)))

        tick  (fn tick [best]
                (->> best (mapcat step) (reduce (partial merge-with max))))]

    (->> START (iterate tick) (drop TIMELIMIT1) first vals (reduce max))))


(assert (= 1651 (p1 sample)))
#_(assert (= 1947 (p1 input)))





(defn key-to-key [DB from to]
  (loop [paths    [[0 from #{}]]
         shortest {}]
    (blet [paths-    (rest paths)
           path      (first paths)
           [steps at been] path
           done?     (empty? paths)
           end?      (= to at)
           been+     (conj been at)
           steps+    (inc steps)
           next-xy   (fn [xy]
                       (when-not (been+ xy)
                         [steps+ xy been+]))
           path+     (->> at DB ::ids (keep next-xy))
           paths+    (into paths- path+)
           slow?     (<= (shortest at ##Inf) steps)
           shortest+ (update shortest at (fnil min ##Inf) steps)]
      (cond
        done? (get shortest to)
        slow? (recur paths- shortest)
        end?, (recur paths- shortest+)
        :else (recur paths+ shortest+)))))


(defn valves [DB] (->> DB (remove #(-> % val ::rate zero?)) (map key) set))

(defn graph  [DB]
  (let [to-ids   (valves DB)
        from-ids (cons START to-ids)
        rf       (fn rf [m from]
                   (assoc m from (into {}
                                   (for [to to-ids :when (not= to from)]
                                     (let [dist (or (get-in m [to from])
                                                  (key-to-key DB from to))]
                                       [to dist])))))]
    (reduce rf {} from-ids)))


(defn p2 [ss]
  (let [DB    (->> ss str/split-lines (map parse1) (reduce merge))
        G     (graph DB)
        ;;AA rate = 0
        half  (fn [todo best]
                (loop [todo todo
                       best best]
                  (blet [todo-    (rest todo)
                         [at t open score] (first todo)
                         k [open t]
                         best+    (assoc best k score)
                         too-slow (< score (best k -1))
                         state+   (fn [[id cost]]
                                    (when (<= cost t)
                                      (when-not (open id)
                                        (blet [t- (- t 1 cost)
                                               o+ (conj open id)
                                               s+ (-> id DB ::rate (* t-) (+ score))]
                                          [id t- o+ s+]))))
                         todo+    (->> at G (keep state+) (into todo-))]
                    (cond
                      (empty? todo) best
                      too-slow,,,,, (recur todo- best)
                      :else,,,,,,,, (recur todo+ best+)))))
        TODO1 [[START TIMELIMIT2 #{} 0]]
        BEST1 (half TODO1 (pm/priority-map-by >))

        TODO2 (->> BEST1  ;;already sorted by score desc
                (map (fn [[[open _] score]]
                       [START TIMELIMIT2 open score])))

        BEST2 (half TODO2 BEST1)]
    ;(prn BEST1)
    ;(u/spy TODO2)
    ;(prn BEST2)
    (->> BEST2 vals (reduce max))))



(time (assert (= 1707 (p2 sample))))
(time (assert (= 2556 (p2 input))))
