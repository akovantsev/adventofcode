(ns adventofcode.2019.day04)

(def MIN 382345)
(def MAX 843167)
(def PASSWORDS (map str (range MIN (inc MAX))))

;; faster predicates stolen from:
;; https://gist.github.com/roman01la/c607849e71e6de11549976428cd3d6a6
(defn ascending? [s] (re-matches #"1*2*3*4*5*6*7*8*9*" s))
(defn has-xwins? [s] (->> s (frequencies) (vals) (some #(< 1 %))))
(defn has-twins? [s] (->> s (frequencies) (vals) (some #{2})))

(defn f1 [passwords] (->> passwords (filter ascending?) (filter has-xwins?) count))
(defn f2 [passwords] (->> passwords (filter ascending?) (filter has-twins?) count))

(assert (= 460 (f1 PASSWORDS)))
(assert (= 290 (f2 PASSWORDS)))