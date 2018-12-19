(ns adventofcode.2018.day19
  (:require [clojure.string :as str]
            ;[adventofcode.2018.day16 :refer [apply-named-op]]
            [clojure.edn :as edn]
            [adventofcode.utils :as u]))


(def input "#ip 5\naddi 5 16 5\nseti 1 5 3\nseti 1 4 2\nmulr 3 2 4\neqrr 4 1 4\naddr 4 5 5\naddi 5 1 5\naddr 3 0 0\naddi 2 1 2\ngtrr 2 1 4\naddr 5 4 5\nseti 2 2 5\naddi 3 1 3\ngtrr 3 1 4\naddr 4 5 5\nseti 1 0 5\nmulr 5 5 5\naddi 1 2 1\nmulr 1 1 1\nmulr 5 1 1\nmuli 1 11 1\naddi 4 1 4\nmulr 4 5 4\naddi 4 9 4\naddr 1 4 1\naddr 5 0 5\nseti 0 5 5\nsetr 5 6 4\nmulr 4 5 4\naddr 5 4 4\nmulr 5 4 4\nmuli 4 14 4\nmulr 4 5 4\naddr 1 4 1\nseti 0 3 0\nseti 0 1 5")
;(def input "#ip 0\nseti 5 0 1\nseti 6 0 2\naddi 0 1 0\naddr 1 2 3\nsetr 1 0 0\nseti 8 0 4\nseti 9 0 5")


(defn parse-op [s]
  (let [tokens (str/split s #"\s+")]
    (into
      [(-> tokens first keyword)]
      (->> tokens rest (mapv edn/read-string)))))

(defn apply-named-op! [!regs [op A B C]]
  (let [RA (get !regs A)
        RB (get !regs B)
        RC (case op
            :addr (+ RA RB)
            :addi (+ RA B)
            :mulr (* RA RB)
            :muli (* RA B)
            :banr (bit-and RA RB)
            :bani (bit-and RA B)
            :borr (bit-or RA RB)
            :bori (bit-or RA B)
            :setr RA
            :seti A
            :gtir (if (> A RB) 1 0)
            :gtri (if (> RA B) 1 0)
            :gtrr (if (> RA RB) 1 0)
            :eqir (if (= A RB) 1 0)
            :eqri (if (= RA B) 1 0)
            :eqrr (if (= RA RB) 1 0))]
    (assoc! !regs C RC)))


(defn simulation1 [regs]
  (time
    (let [lines     (str/split-lines input)
          ip-reg    (-> lines first (str/split #"\s+") second edn/read-string)
          ops       (->> lines rest (mapv parse-op))
          complete? #(-> ops count (<= %))]
      (loop [!regs (transient regs)
             idx   (get !regs ip-reg)]
        (prn [idx (!regs 0) (!regs 1) (!regs 2) (!regs 3) (!regs 4) (!regs 5)])
        (if (complete? idx)
          (persistent! !regs)
          (let [op    (get ops idx)
                !regs (-> !regs
                        (assoc! ip-reg idx)
                        (apply-named-op! op))
                idx'  (inc (get !regs ip-reg))]
            (recur !regs idx')))))))


(defn sum-of-factors [x]
  ;; https://pastebin.com/6azHfChG thanks reddit, Kappa
  (->> (range 1 (inc x))
    (filter #(zero? (rem x %)))
    (reduce + 0)))


(defn simulation2 [regs ancor-reg-idx]
  (time
    (let [lines         (str/split-lines input)
          ip-reg-idx    (-> lines first (str/split #"\s+") second edn/read-string)
          ops           (->> lines rest (mapv parse-op))]
      (loop [!regs (transient regs)
             idx   (get !regs ip-reg-idx)
             seen  {}]
        ;(prn [idx (!regs 0) (!regs 1) (!regs 2) (!regs 3) (!regs 4) (!regs 5)])
        (if (= (get !regs ancor-reg-idx)
              (get seen ip-reg-idx))
          (persistent! !regs)
          (let [op     (get ops idx)
                !regs  (-> !regs
                         (assoc! ip-reg-idx idx)
                         (apply-named-op! op))
                idx'   (inc (get !regs ip-reg-idx))
                anchor (get !regs ancor-reg-idx)]
            (recur !regs idx' (assoc seen idx' anchor))))))))



(defn f1 []
  (-> [1 0 0 0 0 0]
    (simulation1)
    (get 0)))

(def ANHCOR-REG-IDX 1);; <- magic (it is the reg that stabilizes fast and never changes since.)

(defn f2 []
  (-> [1 0 0 0 0 0]
    (simulation2 ANHCOR-REG-IDX)
    (get ANHCOR-REG-IDX)
    (sum-of-factors)))

(assert (= 1228 (f1)))
(assert (= 15285504 (f2)))
