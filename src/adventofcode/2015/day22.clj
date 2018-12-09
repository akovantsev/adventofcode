(ns adventofcode.2015.day22)


(def STATE
  (merge
    {:boss/damage       9
     :boss/hp           51
     :player/hp         50
     :player/mana-left  500
     :player/mana-spent 0
     :game/lost?        false
     :game/won?         false
     :game/scheduled    ()
     :game/applied      []
     :game/turn         0}
    #_
    {:boss/damage      8
     :boss/hp          14
     :player/hp        10
     :player/mana-left 250}
    #_
    {:boss/hp 13}))


(def SPELLS
  [{:id :spell.id/arrow    :cost 53}
   {:id :spell.id/drain    :cost 73}
   {:id :spell.id/shield   :cost 113 :duration 6}
   {:id :spell.id/poison   :cost 173 :duration 6}
   {:id :spell.id/recharge :cost 229 :duration 5}])

(defn cast-spell [state spell-id]
  (let [state (case spell-id
                :spell.id/shield   (-> state (update :boss/damage - 7))
                :spell.id/poison   (-> state (update :boss/hp - 3))
                :spell.id/arrow    (-> state (update :boss/hp - 4))
                :spell.id/drain    (-> state (update :boss/hp - 2) (update :player/hp + 2))
                :spell.id/recharge (-> state (update :player/mana-left + 101)))]
    (update state :game/applied conj spell-id)))

(defn schedule-spell [state {:as spell :keys [:id :duration]}]
  (update state :game/scheduled conj (map vector
                                       (repeat id)
                                       (reverse (range duration)))))

(defn reset-state [{:as state :keys [:game/hard-fn]}]
  (cond-> state
    :always (assoc :boss/damage (:boss/damage STATE))
    hard-fn (hard-fn)))


(defn banned-spell-ids [state]
  (->> state
    :game/scheduled
    (map first)
    (remove #(-> % second zero?))
    (map first)
    (set)))


(defn buy-spell [state {:as spell :keys [id cost duration]}]
  (let [state (-> state
                (update :player/mana-left - cost)
                (update :player/mana-spent + cost))]
    (if duration
      (schedule-spell state spell)
      (cast-spell state id))))


(defn apply-scheduled-spells [state]
  (if (or (:game/lost? state) (:game/won? state))
    state
    (let [spell-ids (->> state :game/scheduled (map ffirst))]
      (as-> state $
        (update $ :game/scheduled #(->> % (map next) (remove nil?)))
        (reduce cast-spell $ spell-ids)))))

(defn health-check [state]
  (cond
    (-> state :boss/hp (< 1))
    (assoc state :game/won? true)

    (-> state :player/hp (< 1))
    (assoc state :game/lost? true)

    :else state))


(defn boss-turn [{:as state :keys [:boss/damage]}]
  (update state :player/hp - damage))

(defn available-spells [{:as state :keys [:player/mana-left]}]
  (let [banned (banned-spell-ids state)]
    (->> SPELLS
      (remove #(-> % :id banned))
      (remove #(-> % :cost (> mana-left))))))

(defn player-turn [{:as state :keys [:player/mana-left]}]
  (if-let [spells (seq (available-spells state))]
    (mapv (partial buy-spell state) spells)
    [(assoc state :game/lost? true)]))

(defn -f [init-state]
  (time
    (loop [[state & todo] [init-state]
           done     []
           seen-min nil]
      ;(println [(count todo) (count done) seen-min])
      (let [state (some-> state health-check)
            {:keys [:player/mana-spent :game/won? :game/lost?]} state]
        (cond
          (nil? state)
          (->> done (filter :game/won?) (sort-by :player/mana-spent <) (first))

          lost?
          (recur todo (conj done state) seen-min)

          won?
          (recur todo (conj done state) (if seen-min (min mana-spent seen-min) mana-spent))

          (some-> seen-min (< mana-spent))
          (recur todo (conj done state) seen-min)

          :else
          (let [state  (-> state
                         (update :game/turn inc)
                         (reset-state)
                         (health-check)
                         (apply-scheduled-spells)
                         (health-check))]
            (cond
              (or (:game/won? state) (:game/lost? state))
              (recur todo (conj done state) seen-min)

              (-> state :game/turn odd?)
              (recur (into todo (player-turn state)) done seen-min)

              (-> state :game/turn even?)
              (recur (conj todo (boss-turn state)) done seen-min))))))))

(defn hardmode [state]
  (if (-> state :game/turn odd?)
    (update state :player/hp - 1)
    state))


(def r1 (-f STATE))
(def r2 (-f (assoc STATE :game/hard-fn hardmode)))


(assert (-> r1 :player/mana-spent (= 900)))
(assert (-> r2 :player/mana-spent (= 1216)))