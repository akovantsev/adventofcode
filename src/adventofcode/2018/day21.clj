(ns adventofcode.2018.day21
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            [adventofcode.2018.day19 :as d19]))

(def input "#ip 5\nseti 123 0 4\nbani 4 456 4\neqri 4 72 4\naddr 4 5 5\nseti 0 0 5\nseti 0 0 4\nbori 4 65536 3\nseti 4332021 4 4\nbani 3 255 2\naddr 4 2 4\nbani 4 16777215 4\nmuli 4 65899 4\nbani 4 16777215 4\ngtir 256 3 2\naddr 2 5 5\naddi 5 1 5\nseti 27 5 5\nseti 0 2 2\naddi 2 1 1\nmuli 1 256 1\ngtrr 1 3 1\naddr 1 5 5\naddi 5 1 5\nseti 25 2 5\naddi 2 1 2\nseti 17 3 5\nsetr 2 7 3\nseti 7 1 5\neqrr 4 0 2\naddr 2 5 5\nseti 5 6 5")

(def lines     (str/split-lines input))
(def ip-reg  (-> lines first (str/split #"\s+") second edn/read-string))
(def ops (->> input str/split-lines rest (mapv d19/parse-op)))

(map-indexed vector ops)

(def magic-ins 28)
(def magic-reg 4)


(defn simulation1 [regs]
  (time
    (loop [!regs (transient regs)
           idx   (get !regs ip-reg)]
      ;(prn [idx (!regs 0) (!regs 1) (!regs 2) (!regs 3) (!regs 4) (!regs 5)])
      ;; idx of the only instruction where reg0 is used (compared with reg4 in my case)
      (if (= 28 idx)
        (!regs 4)
        (let [op    (get ops idx)
              !regs (-> !regs
                      (assoc! ip-reg idx)
                      (d19/apply-named-op! op))
              idx'  (inc (get !regs ip-reg))]
          (recur !regs idx'))))))


(defn simulation2 [regs]
  (time
    (loop [!regs (transient regs)
           idx   (get !regs ip-reg)
           len   0
           !seen (transient {})] ;;{magic-val instructions-count}
      (let [op    (get ops idx)
            !regs (-> !regs
                    (assoc! ip-reg idx)
                    (d19/apply-named-op! op))
            idx'  (inc (get !regs ip-reg))
            len'  (inc len)
            v     (!regs magic-reg)]
        (if (= magic-ins idx)
          (if (!seen v)
            (->> !seen (persistent!) (sort-by val >) (ffirst))
            (recur !regs idx' len' (assoc! !seen (!regs 4) len')))
          (recur !regs idx' len' !seen))))))


(assert (= (simulation1 [0 0 0 0 0 0]) 9566170))
"Elapsed time: 6.854874 msecs"
;(assert (= (simulation2 [0 0 0 0 0 0]) 13192622))
"Elapsed time: 848641.379538 msecs"
