(ns adventofcode.2017.day24
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [adventofcode.utils :as u]))


(def t "0/2\n2/2\n2/3\n3/4\n3/5\n0/1\n10/1\n9/10")
(def i "48/5\n25/10\n35/49\n34/41\n35/35\n47/35\n34/46\n47/23\n28/8\n27/21\n40/11\n22/50\n48/42\n38/17\n50/33\n13/13\n22/33\n17/29\n50/0\n20/47\n28/0\n42/4\n46/22\n19/35\n17/22\n33/37\n47/7\n35/20\n8/36\n24/34\n6/7\n7/43\n45/37\n21/31\n37/26\n16/5\n11/14\n7/23\n2/23\n3/25\n20/20\n18/20\n19/34\n25/46\n41/24\n0/33\n3/7\n49/38\n47/22\n44/15\n24/21\n10/35\n6/21\n14/50")

(def sconj (fnil conj #{}))

(defn make-db [input]
  (->> input
    (re-seq #"\d+")
    (map read-string)
    (partition 2)
    (u/spy)
    (reduce
      (fn [m [a b :as ab]]
        (-> m
          (update :all sconj ab)
          (update a sconj ab)
          (update b sconj ab)))
      {})))

(make-db t)

(defn bridges [input]
  (let [db (make-db input)]
    (loop [done []
           [[n used] & todo] [[0 #{}]]]
      ;(prn done  [n used] todo)
      (if (nil? n)
        done
        (let [opts (->> n db (remove used))]
          (if (empty? opts)
            (recur (conj done used) todo)
            (recur done (into todo
                          (map (fn [[a b :as opt]]
                                 [(if (= n a) b a)
                                  (conj used opt)])
                            opts)))))))))

(->> i bridges
  (map #(->> % (reduce into []) (reduce +)))
  (reduce max))

> 1601
1656

(->> i bridges
  (sort-by count >)
  (partition-by count)
  (first)
  (map #(->> % (reduce into []) (reduce +)))
  (reduce max))

1642