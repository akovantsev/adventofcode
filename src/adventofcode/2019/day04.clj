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

(assert (= 460 (time (f1 PASSWORDS))))
(assert (= 290 (time (f2 PASSWORDS))))
"Elapsed time: 140.608874 msecs"
"Elapsed time: 159.905614 msecs"



(time ;;"Elapsed time: 14.843969 msecs" Kappa
  (let [MIN      382345
        MAX      843167
        int-vec  (fn [n] (->> n str (map str) (mapv #(Integer/parseInt % 10))))
        low      (int-vec MIN)
        high     (int-vec MAX)
        too-low  #(neg? (compare % low))
        in-range #(neg? (compare % high))
        pwds     (for [a (range 0 10)
                       b (range a 10)
                       c (range b 10)
                       d (range c 10)
                       e (range d 10)
                       f (range e 10)
                       :let [n [a b c d e f]]
                       :when (->> n frequencies vals (some #{2}))]
                   n)]
       (->> pwds
         (drop-while too-low)
         (take-while in-range)
         (count))))
