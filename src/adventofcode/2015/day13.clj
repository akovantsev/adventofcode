(ns adventofcode.2015.day13
  (:require [clojure.math.combinatorics :as combo]
            [clojure.string :as str]))


(def input "Alice would lose 57 happiness units by sitting next to Bob.\nAlice would lose 62 happiness units by sitting next to Carol.\nAlice would lose 75 happiness units by sitting next to David.\nAlice would gain 71 happiness units by sitting next to Eric.\nAlice would lose 22 happiness units by sitting next to Frank.\nAlice would lose 23 happiness units by sitting next to George.\nAlice would lose 76 happiness units by sitting next to Mallory.\nBob would lose 14 happiness units by sitting next to Alice.\nBob would gain 48 happiness units by sitting next to Carol.\nBob would gain 89 happiness units by sitting next to David.\nBob would gain 86 happiness units by sitting next to Eric.\nBob would lose 2 happiness units by sitting next to Frank.\nBob would gain 27 happiness units by sitting next to George.\nBob would gain 19 happiness units by sitting next to Mallory.\nCarol would gain 37 happiness units by sitting next to Alice.\nCarol would gain 45 happiness units by sitting next to Bob.\nCarol would gain 24 happiness units by sitting next to David.\nCarol would gain 5 happiness units by sitting next to Eric.\nCarol would lose 68 happiness units by sitting next to Frank.\nCarol would lose 25 happiness units by sitting next to George.\nCarol would gain 30 happiness units by sitting next to Mallory.\nDavid would lose 51 happiness units by sitting next to Alice.\nDavid would gain 34 happiness units by sitting next to Bob.\nDavid would gain 99 happiness units by sitting next to Carol.\nDavid would gain 91 happiness units by sitting next to Eric.\nDavid would lose 38 happiness units by sitting next to Frank.\nDavid would gain 60 happiness units by sitting next to George.\nDavid would lose 63 happiness units by sitting next to Mallory.\nEric would gain 23 happiness units by sitting next to Alice.\nEric would lose 69 happiness units by sitting next to Bob.\nEric would lose 33 happiness units by sitting next to Carol.\nEric would lose 47 happiness units by sitting next to David.\nEric would gain 75 happiness units by sitting next to Frank.\nEric would gain 82 happiness units by sitting next to George.\nEric would gain 13 happiness units by sitting next to Mallory.\nFrank would gain 77 happiness units by sitting next to Alice.\nFrank would gain 27 happiness units by sitting next to Bob.\nFrank would lose 87 happiness units by sitting next to Carol.\nFrank would gain 74 happiness units by sitting next to David.\nFrank would lose 41 happiness units by sitting next to Eric.\nFrank would lose 99 happiness units by sitting next to George.\nFrank would gain 26 happiness units by sitting next to Mallory.\nGeorge would lose 63 happiness units by sitting next to Alice.\nGeorge would lose 51 happiness units by sitting next to Bob.\nGeorge would lose 60 happiness units by sitting next to Carol.\nGeorge would gain 30 happiness units by sitting next to David.\nGeorge would lose 100 happiness units by sitting next to Eric.\nGeorge would lose 63 happiness units by sitting next to Frank.\nGeorge would gain 57 happiness units by sitting next to Mallory.\nMallory would lose 71 happiness units by sitting next to Alice.\nMallory would lose 28 happiness units by sitting next to Bob.\nMallory would lose 10 happiness units by sitting next to Carol.\nMallory would gain 44 happiness units by sitting next to David.\nMallory would gain 22 happiness units by sitting next to Eric.\nMallory would gain 79 happiness units by sitting next to Frank.\nMallory would lose 16 happiness units by sitting next to George.")
(def testinput "Alice would gain 54 happiness units by sitting next to Bob.\nAlice would lose 79 happiness units by sitting next to Carol.\nAlice would lose 2 happiness units by sitting next to David.\nBob would gain 83 happiness units by sitting next to Alice.\nBob would lose 7 happiness units by sitting next to Carol.\nBob would lose 63 happiness units by sitting next to David.\nCarol would lose 62 happiness units by sitting next to Alice.\nCarol would gain 60 happiness units by sitting next to Bob.\nCarol would gain 55 happiness units by sitting next to David.\nDavid would gain 46 happiness units by sitting next to Alice.\nDavid would lose 7 happiness units by sitting next to Bob.\nDavid would gain 41 happiness units by sitting next to Carol.")


(def ops {"lose" - "gain" +})

(defn parse-line
  "[(Alice Bob) +40]"
  [line]
  (let [[obj f x subj] (rest
                         (re-matches #"(\w+) would (lose|gain) (\d+) happiness units by sitting next to (\w+)\."
                           line))]
    [(list obj subj)
     ((ops f) (Integer/parseInt x 10))]))


(defn make-costs
  "{(Alice Bob) +40
    (Bob Alice) -10}"
  [input]
  (->> input
    (str/split-lines)
    (map parse-line)
    (into {})))


(defn subtotal [costs people]
  (->> people
    (cycle)
    (take (-> people count inc))
    (partition 2 1)
    (map costs)
    (reduce +)))


(defn total [costs people]
  (+ (subtotal costs people)
     (subtotal costs (reverse people))))

(defn optimal [costs people]
  (->> people
    (combo/permutations)
    (map (partial total costs))
    (reduce max)))


(defn f1 [input]
  (let [costs  (make-costs input)
        people (into #{} (mapcat key) costs)]
    (optimal costs people)))


(defn insert-self [costs people self]
  (reduce
    (fn [c p]
      (-> c
        (assoc (list self p) 0)
        (assoc (list p self) 0)))
    costs
    people))


(defn f2 [input]
  (let [costs  (make-costs input)
        people (into #{} (mapcat key) costs)
        costs  (insert-self costs people :self)
        people (conj people :self)]
    (optimal costs people)))



(assert (= (f1 input) 618))
(assert (= (f2 input) 601))