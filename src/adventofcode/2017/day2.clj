(ns adventofcode.2017.day2
  (:require [clojure.edn :as edn]
            [clojure.string :as str]
            [clojure.math.combinatorics :as combo]))


(def input "1364\t461\t1438\t1456\t818\t999\t105\t1065\t314\t99\t1353\t148\t837\t590\t404\t123\n204\t99\t235\t2281\t2848\t3307\t1447\t3848\t3681\t963\t3525\t525\t288\t278\t3059\t821\n280\t311\t100\t287\t265\t383\t204\t380\t90\t377\t398\t99\t194\t297\t399\t87\n7698\t2334\t7693\t218\t7344\t3887\t3423\t7287\t7700\t2447\t7412\t6147\t231\t1066\t248\t208\n3740\t837\t4144\t123\t155\t2494\t1706\t4150\t183\t4198\t1221\t4061\t95\t148\t3460\t550\n1376\t1462\t73\t968\t95\t1721\t544\t982\t829\t1868\t1683\t618\t82\t1660\t83\t1778\n197\t2295\t5475\t2886\t2646\t186\t5925\t237\t3034\t5897\t1477\t196\t1778\t3496\t5041\t3314\n179\t2949\t3197\t2745\t1341\t3128\t1580\t184\t1026\t147\t2692\t212\t2487\t2947\t3547\t1120\n460\t73\t52\t373\t41\t133\t671\t61\t634\t62\t715\t644\t182\t524\t648\t320\n169\t207\t5529\t4820\t248\t6210\t255\t6342\t4366\t5775\t5472\t3954\t3791\t1311\t7074\t5729\n5965\t7445\t2317\t196\t1886\t3638\t266\t6068\t6179\t6333\t229\t230\t1791\t6900\t3108\t5827\n212\t249\t226\t129\t196\t245\t187\t332\t111\t126\t184\t99\t276\t93\t222\t56\n51\t592\t426\t66\t594\t406\t577\t25\t265\t578\t522\t57\t547\t65\t564\t622\n215\t2092\t1603\t1001\t940\t2054\t245\t2685\t206\t1043\t2808\t208\t194\t2339\t2028\t2580\n378\t171\t155\t1100\t184\t937\t792\t1436\t1734\t179\t1611\t1349\t647\t1778\t1723\t1709\n4463\t4757\t201\t186\t3812\t2413\t2085\t4685\t5294\t5755\t2898\t200\t5536\t5226\t1028\t180")

(defn parse-line [s]
  (->> (str/split s #"\s+")
    (map edn/read-string)))

(defn parse [s]
  (->> s (str/split-lines) (map parse-line)))

(defn line-difference [xs]
  (let [sorted (sort xs)]
    (- (last sorted) (first sorted))))

(defn evenly-divisible? [[x y]]
  (and (not= x y) (zero? (mod x y))))

(defn line-division [xs]
  (->> (combo/cartesian-product xs xs)
    (filter evenly-divisible?)
    (first)
    (apply /)))

(defn checksum [s line-f]
  (->> s parse (map line-f) (reduce + 0)))

(defn f1 [s] (checksum s line-difference))
(defn f2 [s] (checksum s line-division))

(assert (= 18 (f1 "5 1 9 5\n7 5 3\n2 4 6 8")))
(assert (= 9  (f2 "5 9 2 8\n9 4 7 3\n3 8 6 5")))

(assert (= (f1 input) 53460))
(assert (= (f2 input) 282))