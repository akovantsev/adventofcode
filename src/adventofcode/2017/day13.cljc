(ns adventofcode.2017.day13
  (:require [clojure.string :as str]))


(def ti "0: 3\n1: 2\n4: 4\n6: 4")
(def i "0: 3\n1: 2\n2: 4\n4: 6\n6: 5\n8: 8\n10: 6\n12: 4\n14: 8\n16: 6\n18: 8\n20: 8\n22: 6\n24: 8\n26: 9\n28: 12\n30: 8\n32: 14\n34: 10\n36: 12\n38: 12\n40: 10\n42: 12\n44: 12\n46: 12\n48: 12\n50: 14\n52: 12\n54: 14\n56: 12\n60: 14\n62: 12\n64: 14\n66: 14\n68: 14\n70: 14\n72: 14\n74: 14\n78: 26\n80: 18\n82: 17\n86: 18\n88: 14\n96: 18")

(defn makedb [i]
  (->> i
    str/split-lines
    (mapv #(str/split % #": "))
    (map #(mapv read-string %))
    (into {})))

(defn caught? [delay [idx depth]]
  (let [r (* 2 (dec depth))]
    (= 0 (mod (+ idx delay) r))))

(defn score [delay [idx depth]]
   (if (caught? delay [idx depth])
     (* idx depth)
     0))

(defn damage [delay db]
  (->> db (map #(score delay %)) (reduce +)))


(assert (= 24 (damage 0 (makedb ti))))
(assert (= 748 (damage 0 (makedb i))))

(let [db (makedb i)]
  (->> (range)
    (map (fn [delay]
           [(caught? delay (first db)) (damage delay db)]))
    (take-while (fn [[fc dmg]]
                  (or fc (pos? dmg))))
    (count)))


3873662

