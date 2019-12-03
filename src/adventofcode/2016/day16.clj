(ns adventofcode.2016.day16
  (:require [clojure.string :as str]))




(def input "01000100010010111")
(def DISC 272)
(set! *print-length* DISC)

(defn check-digit [[x y]]
  (if (= x y) \1 \0))

(def mem-check-dig (memoize check-digit))


(defn check-digits [chs]
  (->> chs
    (partition 2 2)
    (map mem-check-dig)))

(defn checksum [digits]
  (->> digits
    (iterate check-digits)
    (drop-while #(-> % count even?))
    (first)
    (str/join)))


(defn rev [digits]
  (->> digits (reverse) (map {\1 \0, \0 \1})))

(defn naive-step [digits]
  (concat (seq digits) [\0] (rev digits)))


(def LEN1 272)
(def LEN2 35651584)

(def sek
  (let [len       LEN2
        token-len (count input)
        too-short #(let [csep (count %)
                         ctok (inc csep)]
                     (-> token-len (* ctok) (+ csep) (< len)))
        seps      (->> "0"
                    (iterate naive-step)
                    (drop-while too-short)
                    (first)
                    (vec))
        seps      (conj seps "")
        odd-token (vec input)
        evn-token (vec (rev input))
        tokens    (cycle [odd-token evn-token])]
    (doall (take len (mapcat conj tokens seps)))))

;(assert (= (checksum input 272) "10010010110011010"))

(set! *print-length* 30)

(time (doall (check-digits sek)))