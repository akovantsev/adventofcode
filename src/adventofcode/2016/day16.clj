(ns adventofcode.2016.day16
  (:require [clojure.string :as str]))




(def input "01000100010010111")
(def DISC 272)
(set! *print-length* 30)


(defn check-digits [chs]
  (->> chs
    (partition 2)
    (map {[\1 \1] \1 [\0 \0] \1
          [\1 \0] \0 [\0 \1] \0})))

;(def mem-check-digs (memoize check-digit))

(defn checksum [len digits]
  (let [n (->> len
            (iterate #(/ % 2))
            (take-while even?)
            (count))]
    (->> digits
      (iterate check-digits)
      (drop n)
      (first)
      (str/join))))


(defn rev [digits]
  (->> digits (reverse) (map {\1 \0, \0 \1})))

(defn naive-step [digits]
  (concat (seq digits) [\0] (rev digits)))



(defn gen-seq [input len]
  (let [token-len (count input)
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
    (take len (mapcat conj tokens seps))))


(defn p2 [input len]
  (checksum len (gen-seq input len)))

(time (assert (= "10010010110011010" (p2 input 272))))
(time (assert (= "01010100101011100" (p2 input 35651584))))

"Elapsed time: 0.758346 msecs"
"Elapsed time: 37930.696068 msecs"