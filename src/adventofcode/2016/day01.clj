(ns adventofcode.2016.day01
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))


(def input "R4, R4, L1, R3, L5, R2, R5, R1, L4, R3, L5, R2, L3, L4, L3, R1, R5, R1, L3, L1, R3, L1, R2, R2, L2, R5, L3, L4, R4, R4, R2, L4, L1, R5, L1, L4, R4, L1, R1, L2, R5, L2, L3, R2, R1, L194, R2, L4, R49, R1, R3, L5, L4, L1, R4, R2, R1, L5, R3, L5, L4, R4, R4, L2, L3, R78, L5, R4, R191, R4, R3, R1, L2, R1, R3, L1, R3, R4, R2, L2, R1, R4, L5, R2, L2, L4, L2, R1, R2, L3, R5, R2, L3, L3, R3, L1, L1, R5, L4, L4, L2, R5, R1, R4, L3, L5, L4, R5, L4, R5, R4, L3, L2, L5, R4, R3, L3, R1, L5, R5, R1, L3, R2, L5, R5, L3, R1, R4, L5, R4, R2, R3, L4, L5, R3, R4, L5, L5, R4, L4, L4, R1, R5, R3, L1, L4, L3, L4, R1, L5, L1, R2, R2, R4, R4, L5, R4, R1, L1, L1, L3, L5, L2, R4, L3, L5, L4, L1, R3")

(defn parse-input [s]
  (->> (str/split s #", ")
    (map (fn token [[turn & tail]]
           [turn (-> tail str/join edn/read-string)]))))

(defn expand-instruction [[turn len]]
  (into [[turn 1]] (repeat (dec len) [\= 1])))

(defn move [[facing x y] [turn len]]
  (case [facing turn]
    [::N \R] [::E (+ x len) y]
    [::S \L] [::E (+ x len) y]
    [::S \R] [::W (- x len) y]
    [::N \L] [::W (- x len) y]
    [::E \R] [::S x (+ y len)]
    [::W \L] [::S x (+ y len)]
    [::W \R] [::N x (- y len)]
    [::E \L] [::N x (- y len)]
    [::N \=] [::N x (dec y)]
    [::S \=] [::S x (inc y)]
    [::W \=] [::W (dec x) y]
    [::E \=] [::E (inc x) y]))


(defn city-distance [[x y]]
  (+ (Math/abs ^int x)
     (Math/abs ^int y)))


(defn f1 [s]
  (->> s
    (parse-input)
    (reduce move [::N 0 0])
    (rest)
    (city-distance)))


(defn f2 [s]
  (->> s
    (parse-input)
    (mapcat expand-instruction)
    (reductions move [::N 0 0])
    (reduce
      (fn [seen [_ x y]]
        (if (contains? seen [x y])
          (reduced [x y])
          (conj seen [x y])))
      #{})
    (city-distance)))


(assert (not= (f1 input) 121))
(assert (not= (f2 input) 184))
(assert (= (f1 input) 146))
(assert (= (f2 input) 131))
