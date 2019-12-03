(ns adventofcode.2017.day10
  (:require
   [clojure.string :as str]
   [adventofcode.utils :as u]))


(def input "197,97,204,108,1,29,5,71,0,50,2,255,248,78,254,63")

(defn parse [s] (mapv u/to-int (str/split s #",")))

(defn reverse-subvec [v start len]
  (if (< len 2)
    v
    (let [to      (+ start len)
          size    (count v)
          wrap    (- to size)
          tail    (- len wrap)
          segment (->> v cycle (drop start) (take len) (reverse))]
      (vec
        (if (pos? wrap)
          (concat
            (drop tail segment)
            (subvec v wrap start)
            (take tail segment))
          (concat
            (subvec v 0 start)
            segment
            (subvec v to)))))))


(assert (= [4 3 0 1 2] (reverse-subvec [2 1 0 3 4] 3 4)))

(defn next-idx [idx len skip size]
  (loop [i (+ idx len skip)]
    (if (< i size)
      i
      (recur (- i size)))))

(defn f [init lens]
  (loop [[len & todo] lens
         state (vec init)
         idx   0
         skip  0]
    (if (nil? len)
      state
      (recur todo
        (reverse-subvec state idx len)
        (next-idx idx len skip (count state))
        (inc skip)))))

(defn score [state]
  (->> state (take 2) (apply *)))

(defn f1 [input]
  (->> input parse (f (range 256)) score))


(assert (= 12 (score (f [0, 1, 2, 3, 4] [3, 4, 1, 5]))))
(assert (= 40132 (f1 input)))

(defn to-char-codes [s] (mapv int s))

(assert (= [49 44 50 44 51] (to-char-codes "1,2,3")))

(def magic-tail [17 31 73 47 23])

(defn dense-hash [coll]
  (->> coll (partition-all 16) (mapv (partial apply bit-xor))))

(defn to-hexadec-str [coll]
  (let [s (->> coll (map (partial format "%2h")) (str/join))]
    (str/replace s " " "0")))

(assert (= "4007ff" (to-hexadec-str [64 7 255])))

(defn f2 [input]
  (as-> input x
    (to-char-codes x)
    (into x magic-tail)
    (repeat 64 x)
    (reduce into [] x)
    (f (range 256) x)
    (dense-hash x)
    (to-hexadec-str x)))

(f2 input)

(let [r (f2 input)]
  (assert (not= r "35b028fe2c958793f7d5a61d7a08c8"))
  (assert (= r "35b028fe2c958793f7d5a61d07a008c8")))