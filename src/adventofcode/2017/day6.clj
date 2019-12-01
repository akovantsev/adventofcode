(ns adventofcode.2017.day6
  (:require [clojure.string :as str]
            [adventofcode.utils :as u]))

(def input "10\t3\t15\t10\t5\t15\t5\t15\t9\t2\t5\t8\t5\t2\t3\t6")

(defn parse [s]
  (mapv #(Integer/parseInt ^String % 10) (str/split s #"\s")))

(parse input)

(defn max-idx [v]
  (->> v
    (map-indexed vector)
    (sort-by second >)
    (ffirst)))

(max-idx (parse input))

(defn redistribute [v]
  (let [size    (count v)
        idx     (max-idx v)
        len     (get v idx)
        pad     (repeat (inc idx) 0)
        tail    (repeat len 1)
        patch   (concat pad tail)
        times   (inc (quot (count patch) size))
        patches (->> (concat patch (repeat 0))
                  (partition-all size)
                  (take times))
        res (apply mapv + (assoc v idx 0) patches)]
    res))

(assert (= (redistribute [0 2 7 0]) [2 4 1 2]))

(defn f [input]
  (let [init input]
    (loop [steps 0
           state init
           seen?  #{}]
      (if (seen? state)
        {::steps steps ::state state}
        (recur (inc steps) (redistribute state) (conj seen? state))))))

(defn f1 [input] (-> input parse f ::steps))
(defn f2 [input] (-> input parse f ::state f ::steps))
(assert (= 14029 (f1 input)))

(f2 input)
