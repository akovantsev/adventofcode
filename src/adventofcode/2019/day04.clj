(ns adventofcode.2019.day04)

(def MIN 382345)
(def MAX 843167)
(def PASSWORDS (map str (range MIN (inc MAX))))

(defn ascending? [s] (= (seq s) (sort-by identity compare s)))
(defn has-xwins? [s] (->> s (partition-by identity) (map count) (some #(< 1 %))))
(defn has-twins? [s] (->> s (partition-by identity) (map count) (some #{2})))

(defn f1 [passwords] (->> passwords (filter ascending?) (filter has-xwins?) count))
(defn f2 [passwords] (->> passwords (filter ascending?) (filter has-twins?) count))

(assert (= 460 (f1 PASSWORDS)))
(assert (= 290 (f2 PASSWORDS)))