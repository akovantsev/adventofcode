(ns adventofcode.2015.day23
  (:require [clojure.edn :as edn]
            [clojure.string :as str]))


(def input "jio a, +19\ninc a\ntpl a\ninc a\ntpl a\ninc a\ntpl a\ntpl a\ninc a\ninc a\ntpl a\ntpl a\ninc a\ninc a\ntpl a\ninc a\ninc a\ntpl a\njmp +23\ntpl a\ntpl a\ninc a\ninc a\ntpl a\ninc a\ninc a\ntpl a\ninc a\ntpl a\ninc a\ntpl a\ninc a\ntpl a\ninc a\ninc a\ntpl a\ninc a\ninc a\ntpl a\ntpl a\ninc a\njio a, +8\ninc b\njie a, +4\ntpl a\ninc a\njmp +2\nhlf a\njmp -7")
;(def input "inc a\njio a, +2\ntpl a\ninc a")

(defn parse-line [s]
  (let [m (->> s
            (re-matches #"(\w+) (a|b)?,? ?([\+\-][0-9]+)?")
            (rest)
            (zipmap [:op :reg :val]))]
    (-> m
      (update :op keyword)
      (update :reg keyword)
      (update :val edn/read-string))))


(def instructions (->> input str/split-lines (mapv parse-line)))


(defn jump [state offset]
  (update state :idx + offset))

(defn apply-step [state {:as step :keys [:op :reg :val]}]
  (case op
    :tpl (-> state (update reg * 3))
    :hlf (-> state (update reg / 2))
    :inc (-> state (update reg + 1))
    :jmp (-> state (jump (dec val)))
    :jie (-> state (jump (if (-> reg state even?) (dec val) 0)))
    :jio (-> state (jump (if (-> reg state (= 1)) (dec val) 0)))))


(defn -f [a]
  (loop [state {:a   a
                :b   0
                :idx 0}]
    (if-let [step (get instructions (:idx state))]
      (recur (-> state (apply-step step) (jump 1) (assoc :took step)))
      (:b state))))

(def r1 (-f 0))
(def r2 (-f 1))

(assert (= r1 184))
(assert (= r2 231))

