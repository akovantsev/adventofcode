(ns adventofcode.y2020.day10
  (:require [clojure.string :as str]))

(def t "28\n33\n18\n42\n31\n14\n46\n20\n48\n47\n24\n23\n49\n45\n19\n38\n39\n11\n1\n32\n25\n35\n8\n17\n7\n9\n4\n2\n34\n10\n3")
(def i "66\n7\n73\n162\n62\n165\n157\n158\n137\n125\n138\n59\n36\n40\n94\n95\n13\n35\n136\n96\n156\n155\n24\n84\n42\n171\n142\n3\n104\n149\n83\n129\n19\n122\n68\n103\n74\n118\n20\n110\n54\n127\n88\n31\n135\n26\n126\n2\n51\n91\n16\n65\n128\n119\n67\n48\n111\n29\n49\n12\n132\n17\n41\n166\n75\n146\n50\n30\n1\n164\n112\n34\n18\n72\n97\n145\n11\n117\n58\n78\n152\n90\n172\n163\n89\n107\n45\n37\n79\n159\n141\n105\n10\n115\n69\n170\n25\n100\n80\n4\n85\n169\n106\n57\n116\n23")

;;p1
(let [xs (->> i str/split-lines (map read-string) (sort >) vec)
      xs (cons (+ 3 (first xs)) (conj xs 0))
      diffs (->> xs (partition 2 1) (map (partial apply -)))]
  (reduce * 1 (-> diffs frequencies (select-keys [1 3]) vals)))

2482


(def minus (fnil - ##Inf ##Inf))

(defn combos [xs]
  (loop [n 1
         todo [[(first xs) (rest xs)]]]
    (if (empty? todo)
      n
      (let [[a [x & xs]] (first todo)
            todo (rest todo)
            b    (first xs)]
        (if (nil? b)
          (recur n todo)
          (if (<= (minus a b) 3)
            (recur (+ n 1) (conj todo [x xs] [a xs]))
            (recur n (conj todo [x xs]))))))))


(defn split [xs]
  (let [threes (fn [[a b c]] (= 3 (minus a b) (minus b c)))]
    (->> xs
      (partition-all 3 1)
      (partition-by threes)
      (map (partial map first))
      (partition-all 2)
      (map (partial apply concat)))))

;;p2
(time
  (let [adapters (->> i str/split-lines (map read-string) (sort >) vec)
        xs       (cons (+ 3 (first adapters)) (conj adapters 0))]
    (->> xs split
      (map combos)
      (reduce * 1))))

= 19208
96717311574016
