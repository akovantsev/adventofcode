(ns adventofcode.2015.day11
  (:require [clojure.string :as str]))

(def input "cqjxjnds")

(def banned-chars #{\i \l \o})
(def all-chars "abcdefghijklmnopqrstuvwxyz")
(def next-char (zipmap
                 all-chars
                 (->> all-chars cycle rest)))
(def allowed-triplets (->> all-chars
                        (partition 3 1)
                        (remove #(some banned-chars %))
                        (set)))
(def allowed-pairs (->> all-chars
                     (remove banned-chars)
                     (map #(list % %))
                     (set)))

(defn valid? [v]
  (and
    (not (some banned-chars v))
    (->> v (partition 3 1) (some allowed-triplets))
    (->> v
      (partition-by identity)
      (map #(-> % count (quot 2)))
      (reduce +)
      (< 1))))

(assert (not (valid? "hijklmmn")))
(assert (not (valid? "abbceffg")))
(assert (not (valid? "abbcegjk")))
(assert (valid? "abcdffaa"))
(assert (valid? "ghjaabcc"))

(defn next-password [v]
  (loop [pos (-> v count dec)
         cur v]
    (when (neg? pos)
      (throw (ex-info "Out of passwords" {})))
    (let [nxt (update cur pos next-char)]
      (if (= \z (nth cur pos))
        (recur (dec pos) nxt)
        nxt))))


(defn f1 [s]
  (let [pw (-> s vec next-password)]
    (if (valid? pw)
      (apply str pw)
      (recur (next-password pw)))))

(defn f2 [s]
  (-> s f1 f1))


(assert (= (f1 "abcdefgh") "abcdffaa"))
(assert (= (f1 "ghijklmn") "ghjaabcc"))
(assert (= (f1 input) "cqjxxyzz"))
(assert (= (f2 input) "cqkaabcc"))