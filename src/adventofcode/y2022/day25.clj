(ns adventofcode.y2022.day25
  (:require [clojure.math :as math]
            [clojure.string :as str]
            [com.akovantsev.blet.core :refer [blet blet!]]))

(def sample "1=-0-2\n12111\n2=0=\n21\n2=01\n111\n20012\n112\n1=-1=\n1-12\n12\n1=\n122")
(def input "2=200=-\n1=1-021\n2-0=-=----10-\n12=-20=2-=2\n12=-2--20=12=-0\n102-1212-=1\n1-211-1-102\n1=00---\n12=-0-102-2\n1=210\n1021111-0020-2-2\n1=12-1-=12=202-22\n11-12\n200=0-\n2202=-2002-\n21=20=1=21=00=-==\n1002201\n1-==-1022\n11-202-00=-=2=201\n102110-1011=2===11\n1=-=1-12-2100\n10-0\n1--2---2110-00\n11--1102=0=21212\n1-1-\n100\n1=--=1=-\n11---2--1-2==--0=\n2211-11-2211-01\n11-2-1=222-=\n1==21001\n1==-11=0-2\n221-2-211=\n12022212-=12--02\n2\n1=-=0\n1==2-1-0000=10\n101201-02-=--0\n2-0\n2-=\n1=2122201011-221120\n2000022=1=0-1\n1=2-2=1101-2\n1--1112=2==220-1\n10-0221-1-0=01-1-\n20110210201000=1=-\n1-1=2--=102-=1---\n1222-0--12=-2\n2==1-0\n1=2120-\n1-210-210\n1-0212-=-=-\n10-2-1120-12=11\n1-020=1-0--21\n1-10-221-1=\n111=2=12----\n212-1--=002\n1=0011202=-1=\n1-22=2\n2==-1=2\n10=\n12-1=211\n2=2\n2-200=111=\n11==221-11101--\n1=-1=0-=2=22-11\n11-101-222-100\n1-12=0-220=2=\n1020-0210-2-01=22\n1=2-002-12\n12202=-0=--2=00-\n1=111=-=-\n1=--0-1==01=1\n1-11=10-=2==-1\n12-=0===0==1-1\n2=20-=21==-\n1=-2=\n1--02-2==20-2=\n12==11==-\n1-==-0--=0--01=021\n1-=10\n11--\n101=12-2==\n1==-\n1-=10-02\n221=-=-=0-\n1=01-120=12111--\n210=-0-2\n1-==2--=-=\n1-1010\n2-\n10=-200121--2-211=20\n2=1=10-\n112\n2=02\n11211=-02==20-1=--\n2-=210-1=\n1==020-11-10-=-=\n112-=1=-==2-0020=\n121=01=\n202-1==2-=00=22\n2021=1-0-1-====\n1-\n112-=2-0--021=-\n1-002\n12--01-0=02102\n20-02\n12210-1-1\n1==2=200-1-1001\n2-01\n21-22221220\n2==10=-0-121-01\n21-=0----=-00-\n100=-2112\n1=-2--\n121-21--0=-=-0--=20\n1=-1-2\n110==-2012=2-01\n20020-0211--20\n22=12\n1=20-==-2-0100-=\n21-20\n201201=0=222")


(defn nafu->int [s]
  (->> s
    reverse
    (map-indexed (fn [idx c] (* (math/pow 5 idx) (case c \= -2 \- -1 \0 0 \1 1 \2 2))))
    (reduce +)))


(defn int->nafu [N]
  (let [upperbound (loop [idxs (range)
                          nafu ()
                          res  0]
                     (let [idxs- (rest idxs)
                           idx   (first idxs)
                           res+  (* 2 (math/pow 5 idx))
                           nafu+ (conj nafu \2)]
                       (if (< res N)
                         (recur idxs- nafu+ res+)
                         nafu+)))
        minus #(case % \2 \1, \1 \0, \0 \-, \- \=, nil)]
    (if (-> upperbound nafu->int (= N))
      upperbound
      (loop [suffix upperbound
             prefix []]
        (blet [suffix-  (rest suffix)
               s        (first suffix)
               s-       (minus s)
               suffix+  (conj suffix- s-)
               prefix+  (conj prefix s)
               res-nafu (concat prefix [s-] suffix-)
               res-int  (nafu->int res-nafu)]

          (cond
            (nil? s-)     (recur suffix- prefix+)
            (= res-int N) (->> res-nafu (drop-while #{\0}) str/join)
            (< res-int N) (recur suffix- prefix+)
            (> res-int N) (recur suffix+ prefix)))))))


(defn p1 [ss] (->> ss str/split-lines (map nafu->int) (reduce +) int->nafu))

(assert (= "2=10---0===-1--01-20" (p1 input)))


(defn pad [a b]
  (let [an  (count a)
        bn  (count b)
        n   (abs (- bn an))]
    (cond
      (= an bn) [a b]
      (< an bn) [(concat a (repeat n \0)) b]
      (> an bn) [a (concat b (repeat n \0))])))

(def SUM
  ; a  b    res carry
  {[\2 \2] [\- \1]
   [\2 \1] [\= \1]
   [\2 \0] [\2 \0]
   [\2 \-] [\1 \0]
   [\2 \=] [\0 \0]
   [\1 \1] [\2 \0]
   [\1 \0] [\1 \0]
   [\1 \-] [\0 \0]
   [\1 \=] [\- \0]
   [\0 \0] [\0 \0]
   [\0 \-] [\- \0]
   [\0 \=] [\= \0]
   [\- \-] [\= \0]
   [\- \=] [\2 \-]
   [\= \=] [\1 \-]})

(def DESC (zipmap [\2 \1 \0 \- \=] (range)))

(defn sum1 [a b] (SUM (sort-by DESC [a b])))

(defn sum [ra rb]
  (let [[pra prb]   (pad ra rb)
        [res carry] (apply map vector (map sum1 pra prb))]
    (if (every? #{\0} carry)
      res
      (recur res (cons \0 carry)))))

(defn p2 [ss]
  (->> ss
    (str/split-lines)
    (map reverse)
    (reduce sum)
    (reverse)
    (drop-while #{\0})
    (str/join)))


(assert (= "2=10---0===-1--01-20" (p1 input)))
(assert (= "2=10---0===-1--01-20" (p2 input)))
