(ns adventofcode.2015.day15
  (:require [clojure.edn :as edn]
            [clojure.string :as str]
            [clojure.math.combinatorics :as combo]))

(def input "Sprinkles: capacity 5, durability -1, flavor 0, texture 0, calories 5\nPeanutButter: capacity -1, durability 3, flavor 0, texture 0, calories 1\nFrosting: capacity 0, durability -1, flavor 4, texture 0, calories 6\nSugar: capacity -1, durability 0, flavor 0, texture 2, calories 8")
(def test-input "Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8\nCinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3")

(defn parse-line [line]
  (->> line
    (re-matches #"\w+: capacity (-?\d+), durability (-?\d+), flavor (-?\d+), texture (-?\d+), calories (-?\d+)")
    (rest)
    (map edn/read-string)
    (zipmap [:capacity :durability :flavor :texture :calories])))


(def SPOONS 100)


(defn recipes [n-ingridients n-slots]
  (->> (range (inc n-slots))
    (repeat n-ingridients)
    (apply combo/cartesian-product)
    (filter #(->> % (reduce +) (= n-slots)))))

(defn multiply [ing n]
  (reduce-kv
    (fn [m k v]
      (assoc m k (* n v)))
    {} ing))

(defn cookie [ingredients coefs]
  (apply merge-with +
    (map multiply ingredients coefs)))



(defn -f [input & [cookie-filter-f]]
  (let [ff          (or cookie-filter-f identity)
        ingredients (->> input (str/split-lines) (map parse-line))
        all-coefs   (recipes (count ingredients) SPOONS)
        cookies     (map (partial cookie ingredients) all-coefs)
        get-score   (fn gs [m]
                      (->> (dissoc m :calories) (vals) (filter pos?) (apply *)))]
    (->> cookies
      (filter ff)
      (map get-score)
      (reduce max))))

(defn f1 [input] (-f input))
(defn f2 [input] (-f input #(-> % :calories (= 500))))


(assert (= (f1 test-input) 62842880))
(assert (= (f2 test-input) 57600000))
#_(assert (= (f1 input) 13882464)) ;;slow
#_(assert (= (f2 input) 11171160)) ;;slow
