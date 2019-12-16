(ns adventofcode.2019.day16
  (:require [adventofcode.utils :as u]
            [clojure.string :as str]))

(def input "59750939545604170490448806904053996019334767199634549908834775721405739596861952646254979483184471162036292390420794027064363954885147560867913605882489622487048479055396272724159301464058399346811328233322326527416513041769256881220146486963575598109803656565965629866620042497176335792972212552985666620566167342140228123108131419565738662203188342087202064894410035696740418174710212851654722274533332525489527010152875822730659946962403568074408253218880547715921491803133272403027533886903982268040703808320401476923037465500423410637688454817997420944672193747192363987753459196311580461975618629750912028908140713295213305315022251918307904937")


(defn parse [s] (map u/char-to-int s))

(defn last-digit [^long i] (mod (Math/abs i) 10))

(assert (= 2 (last-digit -12)))

(defn pattern [idx]
  (->> [0 1 0 -1]
    (mapcat (partial repeat (inc idx)))
    (cycle)
    (rest)))

(def patterns (map pattern (range)))

(set! *print-length* 50)

(defn calc-one [xs pattern]
  (->>
    (map * xs pattern)
    (reduce +)
    (last-digit)))

(defn step1 [xs]
  (->> patterns
    (map (partial calc-one xs))
    (take (count xs))))


(defn f [xs step]
  (->> xs
    (iterate step)
    (drop 100)
    (first)
    (take 8)
    (str/join)))


(defn f1 [input]
  (-> input parse (f step1)))

#_(time (assert (= "59281788" (f1 input))))
"Elapsed time: 7665.422389 msecs"


;; https://raw.githubusercontent.com/drrelyea/advent_of_code_2019/master/day16.py
;inparray = np.array([int(ele) for ele in inpstring]*10000)
;theoffset = int(inpstring[0:7])
;
;# at that distance, it's literally *just* a sum and nothing else
;
;Niters = 100
;newinparray = copy(inparray)
;newinparray = newinparray[theoffset:]
;for iteration in range(Niters):
;    newpattern = np.cumsum(newinparray[::-1]) % 10
;    newinparray = newpattern[::-1]
;
;print(newinparray[0:8])

(defn f2 [input]
  (let [message-offset  (->> input (take 7) (str/join) (u/to-int))
        real-input      (->> input parse (repeat 10000) (apply concat))
        real-real-input (->> real-input (drop message-offset))
        step2           (fn [in] (->> in reverse (reductions +) (map last-digit) reverse))]
    (assert (-> real-input count (/ 2) (<= message-offset)))
    (f real-real-input step2)))

#_(time (assert (= "96062868" (f2 input))))
"Elapsed time: 25621.558881 msecs"

