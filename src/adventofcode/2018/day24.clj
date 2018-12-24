(ns adventofcode.2018.day24
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))


(defn parse-modifier [s]
  ;;"immune to slashing, bludgeoning, cold"
  (let [[k vs] (str/split s #" to ")]
    {(keyword (str *ns*) k) (->> (str/split vs #", ") (set))}))

(parse-modifier "immune to slashing, bludgeoning, cold")

(defn set-modifiers [{:as m ::keys [modifiers]}]
  ;; "(immune to slashing, bludgeoning, cold; weak to fire)"
  (-> modifiers
    (str/trim)
    (str/replace "(" "")
    (str/replace ")" "")
    (str/split #"; ")
    (as-> $
      (remove str/blank? $)
      (map parse-modifier $)
      (apply merge m $))
    (dissoc ::modifiers)
    (update ::damage edn/read-string)
    (update ::units edn/read-string)
    (update ::hp edn/read-string)
    (update ::initiative edn/read-string)))

(defn parse-line [s]
  ;; "2758 units each with 5199 hit points (immune to slashing, bludgeoning, cold; weak to fire) with an attack that does 18 bludgeoning damage at initiative 15"
  (->> s
    (re-matches #"(\d+) units each with (\d+) hit points( \(.+\)|) with an attack that does (\d+) (\w+?) damage at initiative (\d+)")
    (rest)
    (zipmap [::units ::hp ::modifiers ::damage ::attack ::initiative])
    (set-modifiers)))

(defn parse-input [s]
  (let [[immune infect] (->> (str/split s #"\n\n")
                             (map str/split-lines)
                             (map rest))]
    (->> (concat
           (->> immune (map parse-line) (map-indexed #(assoc %2 ::id (str \i %1) ::team :team/immune)))
           (->> infect (map parse-line) (map-indexed #(assoc %2 ::id (str \d %1) ::team :team/desease))))
      (reduce
        (fn rf [by-id player]
          (assoc by-id (::id player) player))
        {}))))




(defn power [{::keys [units damage]}]  (* units damage))

(defn set-power [m] (when m (assoc m ::power (power m))))
(defn set-power-all [m]
  (reduce
    (fn rf [m k]
      (update m k set-power))
    m (keys m)))

(defn by-target-selection-priority [m1 m2]
  (compare
    ;; [higher higher] first
    [(::power m2) (::initiative m2)]
    [(::power m1) (::initiative m1)]))

(defn same-team? [m1 m2]
  (= (::team m1) (::team m2)))

(defn immune? [ma md]
  (contains? (::immune md) (::attack ma)))

(defn weak? [ma md]
  (contains? (::weak md) (::attack ma)))

(defn damage [ma md]
  (if (weak? ma md)
    (* (::power ma) 2)
    (::power ma)))

(defn by-damage-received [ma md1 md2]
  (compare
    ;; [higher higher higher] first
    [(damage ma md2) (::power md2) (::initiative md2)]
    [(damage ma md1) (::power md1) (::initiative md1)]))


(defn pick-target [m ms]
  (->> ms
    (remove (partial same-team? m))
    (remove (partial immune? m))
    (sort (partial by-damage-received m))
    (first)))


(defn receive-damage [target attacker]
  (let [units (- (::units target)
                (quot (damage attacker target) (::hp target)))]
    (when (pos? units)
      (assoc target ::units units))))


(defn one-team-left? [by-id]
  (->> by-id vals (map ::team) set count (= 1)))

(defn units-left [by-id]
  (->> by-id vals (map ::units) (reduce +)))


(defn select-targets [players-by-id]
  (loop [attackers (->> players-by-id vals (sort by-target-selection-priority))
         targets   (->> players-by-id vals set)
         selected  {}]
    (if (empty? attackers)
      selected
      (let [att (first attackers)
            tar (pick-target att targets)]
        (recur
          (rest attackers)
          (disj targets tar)
          (assoc selected (::id att) (::id tar)))))))


(defn select-attackers [players-by-id]
  (->> players-by-id vals (sort-by ::initiative >) (map ::id)))

(defn targets-snapshot [targets players]
  (->> targets vals (remove nil?) (map players)))

(defn simulate [players]
  (loop [targets      {}
         attackers    ()
         snaphot      nil
         players      (set-power-all players)]
    (cond
      (one-team-left? players)
      {::winner (-> players first val ::team)
       ::units  (units-left players)}

      (empty? attackers)
      (let [targets'  (select-targets players)
            snapshot' (targets-snapshot targets' players)]
        (if (= snapshot' snaphot)
          {::winner :team/tie
           ::units  (units-left players)}
          (recur targets' (select-attackers players) snapshot' players)))

      :else
      (let [[aid & attackers] attackers
            att (players aid)
            tid (targets aid)]
        (if (nil? att)
          (recur targets attackers snaphot players)
          (recur targets attackers snaphot (if-let [target (some-> tid players (receive-damage att) set-power)]
                                             (assoc  players tid target)
                                             (dissoc players tid))))))))

(defn f1 [input]
  (time
    (->> input parse-input simulate ::units)))


(defn boost [dmg players]
  (reduce-kv
    (fn rf [m k v]
      (if (-> v ::team (= :team/immune))
        (update-in m [k ::damage] + dmg)
        m))
    players
    players))

(defn f2 [input]
  (time
    (->> input
      (parse-input)
      (repeat)
      (map-indexed boost)
      (map simulate)
      (drop-while #(-> % ::winner #{:team/desease :team/tie}))
      (first)
      (::units))))



(def input "Immune System:\n84 units each with 9798 hit points (immune to bludgeoning) with an attack that does 1151 fire damage at initiative 9\n255 units each with 9756 hit points (weak to cold, radiation) with an attack that does 382 slashing damage at initiative 17\n4943 units each with 6022 hit points (weak to bludgeoning) with an attack that does 11 bludgeoning damage at initiative 4\n305 units each with 3683 hit points (weak to bludgeoning, slashing) with an attack that does 107 cold damage at initiative 5\n1724 units each with 6584 hit points (weak to radiation) with an attack that does 30 cold damage at initiative 6\n2758 units each with 5199 hit points (immune to slashing, bludgeoning, cold; weak to fire) with an attack that does 18 bludgeoning damage at initiative 15\n643 units each with 9928 hit points (immune to fire; weak to slashing, bludgeoning) with an attack that does 149 fire damage at initiative 14\n219 units each with 8810 hit points with an attack that does 368 cold damage at initiative 3\n9826 units each with 10288 hit points (weak to bludgeoning; immune to cold) with an attack that does 8 cold damage at initiative 18\n2417 units each with 9613 hit points (weak to fire, cold) with an attack that does 36 cold damage at initiative 19\n\nInfection:\n1379 units each with 46709 hit points with an attack that does 66 slashing damage at initiative 16\n1766 units each with 15378 hit points (weak to bludgeoning) with an attack that does 12 radiation damage at initiative 10\n7691 units each with 33066 hit points (weak to bludgeoning) with an attack that does 7 slashing damage at initiative 12\n6941 units each with 43373 hit points (weak to cold) with an attack that does 12 fire damage at initiative 7\n5526 units each with 28081 hit points (weak to fire, slashing) with an attack that does 7 bludgeoning damage at initiative 11\n5844 units each with 41829 hit points with an attack that does 11 bludgeoning damage at initiative 20\n370 units each with 25050 hit points (immune to radiation; weak to fire) with an attack that does 120 radiation damage at initiative 2\n164 units each with 42669 hit points with an attack that does 481 fire damage at initiative 13\n3956 units each with 30426 hit points (weak to radiation) with an attack that does 13 cold damage at initiative 8\n2816 units each with 35467 hit points (immune to slashing, radiation, fire; weak to cold) with an attack that does 24 slashing damage at initiative 1")

(assert (= 33551 (f1 input)))
(assert (= 760 (f2 input)))

"Elapsed time: 212.742613 msecs"
"Elapsed time: 14182.029512 msecs"
