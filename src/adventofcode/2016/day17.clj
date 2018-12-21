(ns adventofcode.2016.day17
  (:require [clojure.string :as str]
            [adventofcode.utils :as u]))

(defn U [[x y]] [x (dec y)])
(defn D [[x y]] [x (inc y)])
(defn L [[x y]] [(dec x) y])
(defn R [[x y]] [(inc x) y])
(def neighbours (memoize (juxt U D L R)))

(def OPS ["U" "D" "L" "R"])

(def open? #{\b \c \d \e \f})

(defn wall? [[x y]]
  (or (< x 0) (< y 0) (< 3 x) (< 3 y)))


(defn move [pw xy md5]
  (->> xy
    (neighbours)
    (map-indexed vector)
    (filter #(->> % first (get md5) open?))
    (remove #(->> % second wall?))
    (map (fn mf [[idx xy]]
           [(str pw (OPS idx)) xy]))))


(defn all-paths [input]
  (time
    (loop [[[pw xy] & todo] [[input [0 0]]]
           done []]
      (case xy
        nil   done
        [3 3] (recur todo (conj done pw))
        (let [md5 (u/md5 pw)
              xys (move pw xy md5)]
          (recur (into todo xys) done))))))


(defn f1 [input]
  (str/replace-first
    (->> input all-paths (sort-by count <) (first))
    input ""))


(defn f2 [input]
  (count
    (str/replace-first
      (->> input all-paths (sort-by count >) (first))
      input "")))


(def input "udskfozm")

(assert (= "DDRLRRUDDR" (f1 input)))
(assert (= 556 (f2 input)))