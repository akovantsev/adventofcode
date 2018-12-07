(ns adventofcode.2015.day21
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]
            [clojure.edn :as edn]))


(defn parse-item [line]
  (->> (str/split line #"\s{2,}")
       (rest)
       (map edn/read-string)
       (zipmap [:cost :damage :armor])))

(defn parse-items [s]
  (->> s (str/split-lines) (map parse-item)))



(def BOSS   {:hp 103 :damage 9 :armor 2})
(def PLAYER {:hp 100 :damage 0 :armor 0 :cost 0})

(def WEAPONS (parse-items "Dagger        8     4       0\nShortsword   10     5       0\nWarhammer    25     6       0\nLongsword    40     7       0\nGreataxe     74     8       0"))
(def ARMOR   (parse-items "Leather      13     0       1\nChainmail    31     0       2\nSplintmail   53     0       3\nBandedmail   75     0       4\nPlatemail   102     0       5"))
(def RINGS   (parse-items "Damage +1    25     1       0\nDamage +2    50     2       0\nDamage +3   100     3       0\nDefense +1   20     0       1\nDefense +2   40     0       2\nDefense +3   80     0       3"))


(defn purchase-options [items MIN MAX]
  (mapcat
    (partial combo/combinations items)
    (range MIN (inc MAX))))

(def outfits
  (map
    (partial apply concat)
    (combo/cartesian-product
      (purchase-options WEAPONS 1 1)
      (purchase-options ARMOR 0 1)
      (purchase-options RINGS 0 2))))


(defn gear-up [player items]
  (apply merge-with + player items))

(defn calc-dmg [{:keys [damage]} {:keys [armor]}]
  (let [dmg (- damage armor)]
    (if (pos? dmg) dmg 1)))

(defn timeline [init-hp damage]
  (->> damage
    (repeat)
    (reductions - init-hp)
    (take-while pos?)))

(defn fight [boss player items]
  (let [player       (gear-up player items)
        player-dmg   (calc-dmg player boss)
        boss-dmg     (calc-dmg boss player)
        player-turns (timeline (:hp player) boss-dmg)
        boss-turns   (timeline (:hp boss) player-dmg)]
    {:cost    (:cost player)
     :player  player
     :items   items
     :fight   (mapv vector player-turns boss-turns)
     :outcome (if (< (count player-turns) (count boss-turns))
                :loss
                :win)}))


(defn -f [outcome cost-rf]
  (->> outfits
    (map (partial fight BOSS PLAYER))
    (filter #(-> % :outcome (= outcome)))
    (map :cost)
    (reduce cost-rf)))



(def r1 (-f :win min))
(def r2 (-f :loss max))


(assert (= 121 r1))
(assert (not= 282 r2))
(assert (= 201 r2))
