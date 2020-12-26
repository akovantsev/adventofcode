(ns adventofcode.2017.day25
  (:require [clojure.string :as str]))

(def t "Begin in state A.\nPerform a diagnostic checksum after 6 steps.\n\nIn state A:\n  If the current value is 0:\n    - Write the value 1.\n    - Move one slot to the right.\n    - Continue with state B.\n  If the current value is 1:\n    - Write the value 0.\n    - Move one slot to the left.\n    - Continue with state B.\n\nIn state B:\n  If the current value is 0:\n    - Write the value 1.\n    - Move one slot to the left.\n    - Continue with state A.\n  If the current value is 1:\n    - Write the value 1.\n    - Move one slot to the right.\n    - Continue with state A.")
(def i "Begin in state A.\nPerform a diagnostic checksum after 12386363 steps.\n\nIn state A:\n  If the current value is 0:\n    - Write the value 1.\n    - Move one slot to the right.\n    - Continue with state B.\n  If the current value is 1:\n    - Write the value 0.\n    - Move one slot to the left.\n    - Continue with state E.\n\nIn state B:\n  If the current value is 0:\n    - Write the value 1.\n    - Move one slot to the left.\n    - Continue with state C.\n  If the current value is 1:\n    - Write the value 0.\n    - Move one slot to the right.\n    - Continue with state A.\n\nIn state C:\n  If the current value is 0:\n    - Write the value 1.\n    - Move one slot to the left.\n    - Continue with state D.\n  If the current value is 1:\n    - Write the value 0.\n    - Move one slot to the right.\n    - Continue with state C.\n\nIn state D:\n  If the current value is 0:\n    - Write the value 1.\n    - Move one slot to the left.\n    - Continue with state E.\n  If the current value is 1:\n    - Write the value 0.\n    - Move one slot to the left.\n    - Continue with state F.\n\nIn state E:\n  If the current value is 0:\n    - Write the value 1.\n    - Move one slot to the left.\n    - Continue with state A.\n  If the current value is 1:\n    - Write the value 1.\n    - Move one slot to the left.\n    - Continue with state C.\n\nIn state F:\n  If the current value is 0:\n    - Write the value 1.\n    - Move one slot to the left.\n    - Continue with state E.\n  If the current value is 1:\n    - Write the value 1.\n    - Move one slot to the right.\n    - Continue with state A.")

(defn re [s r]
  (second (re-find r s)))

;In state A:
;  If the current value is 0:
;    - Write the value 1.
;    - Move one slot to the right.
;    - Continue with state B.
;  If the current value is 1:
;    - Write the value 0.
;    - Move one slot to the left.
;    - Continue with state E.
(defn parse-value [lines]
  {:write (-> lines (nth 0) (re #"(\d)") (read-string))
   :move  (-> lines (nth 1) (re #"(left|right)") {"left" dec "right" inc})
   :st    (-> lines (nth 2) (re #"state (\w)"))})

(defn parse-state [text]
  (let [lines (str/split-lines text)
        [if0 if1] (->> lines rest (partition 4) (map rest) (map parse-value))]
    {:id (re text #"In state (\w):")
     0 if0
     1 if1}))

(defn parse [input]
  (let [db {:idx   0
            :st    (->> (re input #"Begin in state (\w)"))
            :steps (->> (re input #"Perform a diagnostic checksum after (\d+) steps.") read-string)}]
    (-> input
      (str/split #"\n\n")
      (rest)
      (->>
        (map parse-state)
        (reduce
          (fn [db m]
            (-> db
              (assoc [(:id m) 0] (m 0))
              (assoc [(:id m) 1] (m 1))))
          db)))))

(parse t)

(defn step [state]
  (let [{:keys [idx st]} state
        v (get state idx 0)
        {:keys [write move st]} (get state [st v])]
    (assoc! state
      idx write
      :idx (move idx)
      :st st)))

(let [state (parse i)]
  (->> state
    transient
    ;step))
    (iterate step)
    (drop (:steps state))
    first
    persistent!
    (vals)
    (filter #{1})
    (count)))
