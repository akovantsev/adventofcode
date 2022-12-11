(ns adventofcode.y2022.day11
  (:require
   [clojure.math :as math]
   [clojure.string :as str]
   [com.akovantsev.blet.core :refer [blet blet!]]
   [adventofcode.utils :as u]))

(set! *print-namespace-maps* false)

(def sample "Monkey 0:\n  Starting items: 79, 98\n  Operation: new = old * 19\n  Test: divisible by 23\n    If true: throw to monkey 2\n    If false: throw to monkey 3\n\nMonkey 1:\n  Starting items: 54, 65, 75, 74\n  Operation: new = old + 6\n  Test: divisible by 19\n    If true: throw to monkey 2\n    If false: throw to monkey 0\n\nMonkey 2:\n  Starting items: 79, 60, 97\n  Operation: new = old * old\n  Test: divisible by 13\n    If true: throw to monkey 1\n    If false: throw to monkey 3\n\nMonkey 3:\n  Starting items: 74\n  Operation: new = old + 3\n  Test: divisible by 17\n    If true: throw to monkey 0\n    If false: throw to monkey 1")
(def input "Monkey 0:\n  Starting items: 85, 77, 77\n  Operation: new = old * 7\n  Test: divisible by 19\n    If true: throw to monkey 6\n    If false: throw to monkey 7\n\nMonkey 1:\n  Starting items: 80, 99\n  Operation: new = old * 11\n  Test: divisible by 3\n    If true: throw to monkey 3\n    If false: throw to monkey 5\n\nMonkey 2:\n  Starting items: 74, 60, 74, 63, 86, 92, 80\n  Operation: new = old + 8\n  Test: divisible by 13\n    If true: throw to monkey 0\n    If false: throw to monkey 6\n\nMonkey 3:\n  Starting items: 71, 58, 93, 65, 80, 68, 54, 71\n  Operation: new = old + 7\n  Test: divisible by 7\n    If true: throw to monkey 2\n    If false: throw to monkey 4\n\nMonkey 4:\n  Starting items: 97, 56, 79, 65, 58\n  Operation: new = old + 5\n  Test: divisible by 5\n    If true: throw to monkey 2\n    If false: throw to monkey 0\n\nMonkey 5:\n  Starting items: 77\n  Operation: new = old + 4\n  Test: divisible by 11\n    If true: throw to monkey 4\n    If false: throw to monkey 3\n\nMonkey 6:\n  Starting items: 99, 90, 84, 50\n  Operation: new = old * old\n  Test: divisible by 17\n    If true: throw to monkey 7\n    If false: throw to monkey 1\n\nMonkey 7:\n  Starting items: 50, 66, 61, 92, 64, 78\n  Operation: new = old + 3\n  Test: divisible by 2\n    If true: throw to monkey 5\n    If false: throw to monkey 1")


(defn find-ints [s] (->> s (re-seq #"-?\d+") (mapv u/to-int)))
(defn find-int  [s] (->> s find-ints first))

(defn parse1 [monkey]
  (let [[idx-line
         items-line
         op-line
         pred-line
         then-line
         else-line] monkey]
    {::idx   (-> idx-line find-int)
     ::items (-> items-line find-ints)
     ::total 0
     ::pred  (-> pred-line find-int)
     ::then  (-> then-line find-int)
     ::else  (-> else-line find-int)
     ::op    (let [expr (second (str/split op-line #" = "))
                   [x op y] (str/split expr #"\s+")]
               (eval (read-string (str "(fn [old] (" op " " x " " y "))"))))
     ::lines monkey}))

(defn parse [ss]
  (-> ss
    (str/split #"\n\n")
    (->>
      (map str/split-lines)
      (mapv parse1))))


(defn items-distrib1 [monkey]
  (let [{:keys [::items ::op ::pred ::then ::else]} monkey
        f (fn [i]
            (let [w   (-> i op (/ 3) math/floor int)
                  idx (if (-> w (mod pred) zero?) then else)]
              [idx w]))]
    (mapv f items)))

(defn items-distrib2 [lcd monkey]
  (let [{:keys [::items ::op ::pred ::then ::else]} monkey
        f (fn [i]
            ;; https://clojurians.slack.com/archives/C0GLTDB2T/p1670747371451889?thread_ts=1670739044.277379&cid=C0GLTDB2T
            (let [w   (-> i (rem lcd) op)
                  idx (if (-> w (mod pred) zero?) then else)]
              [idx w]))]
    (mapv f items)))


(defn round [monkeys distrib-f]
  (let [accept (fn accept [monkeys [idx item]]
                 (-> monkeys
                   (update-in [idx ::items] conj item)))
        send   (fn send [monkeys idx]
                 (let [monkey (get monkeys idx)
                       items  (distrib-f monkey)]
                   (-> monkeys
                     (update-in [idx ::total] + (count items))
                     (assoc-in [idx ::items] [])
                     (as-> $
                       (reduce accept $ items)))))]
    (reduce send monkeys (range (count monkeys)))))




(defn solve [ss roundf rounds]
  (->> ss parse (sort-by ::idx <) vec (iterate roundf) (drop rounds) first (map ::total) (sort >) (take 2) (apply *)))

(defn p1 [ss]
  (let [roundf #(round % items-distrib1)]
    (solve ss roundf 20)))

(defn p2 [ss]
  (let [lcd    (->> ss parse (map ::pred) (reduce *))
        roundf #(round % (partial items-distrib2 lcd))]
    (solve ss roundf 10000)))

(assert (= 10605 (p1 sample)))
(assert (= 54752 (p1 input)))

(assert (= 2713310158 (p2 sample)))
(assert (= 13606755504 (p2 input)))