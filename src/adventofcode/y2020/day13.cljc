(ns adventofcode.y2020.day13
  (:require [clojure.string :as str]
            [clojure.string :as str]))

(def t "939\n7,13,x,x,59,x,31,19")
(def i "1013728\n23,x,x,x,x,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,x,x,x,733,x,x,x,x,x,x,x,x,x,x,x,x,13,17,x,x,x,x,19,x,x,x,x,x,x,x,x,x,29,x,449,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,37")

;;p1
#_
(prn
  (let [[x & buses] (map read-string (re-seq #"\d+" i))
        search      (fn [n] (->> buses (some #(when (zero? (mod n %)) [n %]))))
        score       (fn [[t b]] (-> t (- x) (* b)))]
    (->> (range x ##Inf) (map search) (some identity) (score))))

;;8063

;p2
(defn parse [input]
  (-> input
    (str/split-lines)
    second
    (str/split #",")
    (->>
      (map read-string)
      (map-indexed vector)
      (remove #(-> % second #{'x})))))
      ;(sort-by second >)))

[[-4 7] [-3 13] [0 59] [2 31] [3 19]]
[[0 7] [1 13] [4 59] [6 31] [7 19]]

;;https://rosettacode.org/wiki/Chinese_remainder_theorem#Clojure
(defn extended-gcd
  "The extended Euclidean algorithm
  Returns a list containing the GCD and the Bézout coefficients
  corresponding to the inputs. "
  [a b]
  (cond (zero? a) [(Math/abs b) 0 1]
        (zero? b) [(Math/abs a) 1 0]
        :else (loop [s 0
                     s0 1
                     t 1
                     t0 0
                     r (Math/abs b)
                     r0 (Math/abs a)]
                (if (zero? r)
                  [r0 s0 t0]
                  (let [q (quot r0 r)]
                    (recur (- s0 (* q s)) s
                      (- t0 (* q t)) t
                      (- r0 (* q r)) r))))))

(defn chinese_remainder
  " Main routine to return the chinese remainder "
  [n a]
  (let [prod (apply * n)
        reducer (fn [sum [n_i a_i]]
                  (let [p (quot prod n_i)           ; p = prod / n_i
                        egcd (extended-gcd p n_i)   ; Extended gcd
                        inv_p (second egcd)]        ; Second item is the inverse
                    (+ sum (* a_i inv_p p))))
        sum-prod (reduce reducer 0 (map vector n a))] ; Replaces the Python for loop to sum
    ; (map vector n a) is same as
    ;                                             ; Python's version Zip (n, a)
    (mod sum-prod prod)))                             ; Result line

(let [;pairs '([0 23] [13 41] [23 733] [36 13] [37 17] [42 19] [52 29] [54 449] [91 37])
      ;pairs [[0 7] [1 13] [4 59] [6 31] [7 19]]
      pairs (parse
              i)
              ;"\n1789,37,47,1889")
              ;"\n67,7,x,59,61")
              ;"\n67,x,7,59,61")
              ;"\n67,7,59,61")
              ;"\n17,x,13,19")
      nn (map second pairs)
      aa (map - nn (map first pairs))]
  (prn [nn aa])
  (chinese_remainder nn aa))
1095815875017851 >
1095815875017851
1095815875017851


;test results are correct, personal input result is wrong. lel.

;;https://redpenguin101.github.io/posts/2020_12_13_mod_mult.html
(defn- linear-congruence-solve [remainders modulos]
  (let [prod (apply * modulos)
        pp (map #(/ prod %) modulos)
        inv (map #(.modInverse (biginteger %1) (biginteger %2)) pp modulos)]
    (mod (apply + (map * remainders pp inv)) prod)))

(let [pairs (parse
              i)
      nn (map second pairs)
      aa (map - nn (map first pairs))]
  (linear-congruence-solve aa nn))

775230782877242