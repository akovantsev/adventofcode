(ns adventofcode.2016.day21
  (:require [clojure.string :as str]))

(set! *print-length* 100)

(def t "")
(def i "move position 2 to position 1\nmove position 2 to position 5\nmove position 2 to position 4\nswap position 0 with position 2\nmove position 6 to position 5\nswap position 0 with position 4\nreverse positions 1 through 6\nmove position 7 to position 2\nrotate right 4 steps\nrotate left 6 steps\nrotate based on position of letter a\nrotate based on position of letter c\nmove position 2 to position 0\nswap letter d with letter a\nswap letter g with letter a\nrotate left 6 steps\nreverse positions 4 through 7\nswap position 6 with position 5\nswap letter b with letter a\nrotate based on position of letter d\nrotate right 6 steps\nmove position 3 to position 1\nswap letter g with letter a\nswap position 3 with position 6\nrotate left 7 steps\nswap letter b with letter c\nswap position 3 with position 7\nmove position 2 to position 6\nswap letter b with letter a\nrotate based on position of letter d\nswap letter f with letter b\nmove position 3 to position 4\nrotate left 3 steps\nrotate left 6 steps\nrotate based on position of letter c\nmove position 1 to position 3\nswap letter e with letter a\nswap letter a with letter c\nrotate left 2 steps\nmove position 6 to position 5\nswap letter a with letter g\nrotate left 5 steps\nreverse positions 3 through 6\nmove position 7 to position 2\nswap position 6 with position 5\nswap letter e with letter c\nreverse positions 2 through 7\nrotate based on position of letter e\nswap position 3 with position 5\nswap letter e with letter d\nrotate left 3 steps\nrotate based on position of letter c\nmove position 4 to position 7\nrotate based on position of letter e\nreverse positions 3 through 5\nrotate based on position of letter h\nswap position 3 with position 0\nswap position 3 with position 4\nmove position 7 to position 4\nrotate based on position of letter a\nreverse positions 6 through 7\nrotate based on position of letter g\nswap letter d with letter h\nreverse positions 0 through 3\nrotate right 2 steps\nrotate right 6 steps\nswap letter a with letter g\nreverse positions 2 through 4\nrotate based on position of letter e\nmove position 6 to position 0\nreverse positions 0 through 6\nmove position 5 to position 1\nswap position 5 with position 2\nrotate right 3 steps\nmove position 3 to position 1\nrotate left 1 step\nreverse positions 1 through 3\nrotate left 4 steps\nreverse positions 5 through 6\nrotate right 7 steps\nreverse positions 0 through 2\nmove position 0 to position 2\nswap letter b with letter c\nrotate based on position of letter d\nrotate left 1 step\nswap position 2 with position 1\nswap position 6 with position 5\nswap position 5 with position 0\nswap letter a with letter c\nmove position 7 to position 3\nmove position 6 to position 7\nrotate based on position of letter h\nmove position 3 to position 0\nmove position 4 to position 5\nrotate left 4 steps\nswap letter h with letter c\nswap letter f with letter e\nswap position 1 with position 3\nswap letter e with letter b\nrotate based on position of letter e")

(defn parse-op [s]
  (or
    (some->> s (re-matches #"move position (\d+) to position (\d+)") next (map read-string) (cons :mov-pos))
    (some->> s (re-matches #"reverse positions (\d+) through (\d+)") next (map read-string) (cons :rev-pos))
    (some->> s (re-matches #"swap position (\d+) with position (\d+)") next (map read-string) (cons :swap-pos))
    (some->> s (re-matches #"swap letter (\w) with letter (\w)") next (cons :swap-let))
    (some->> s (re-matches #"rotate based on position of letter (\w)") next (cons :rot-let-pos))
    (some->> s (re-matches #"rotate right (\d+) steps?") next (map read-string) (cons :rot-rigth))
    (some->> s (re-matches #"rotate left (\d+) steps?") next (map read-string) (cons :rot-left))))

(defn index-of [state x]
  (->> state
    (map-indexed vector)
    (filter #(-> % second (= x)))
    (first)
    (first)))

(index-of [:a :b :c] :c)
(defn rot-left [v n]
  (into (subvec v n) (subvec v 0 n)))

(defn rot-right [v n]
  (rot-left v (- (count v) n)))





(defn step [v [op x y]]
  ;(prn (str/join v) op x y)
  (case op
    :swap-pos     (assoc v y (v x) x (v y))
    :swap-let     (let [ix (index-of v x) iy (index-of v y)] (assoc v iy x ix y))
    :rot-left     (rot-left v x)
    :rot-rigth    (rot-right v x)
    :rot-let-pos  (let [n (index-of v x)] (-> v (rot-right 1) (rot-right n) (cond-> (<= 4 n) (rot-right 1))))
    :rev-pos      (let [n (min (inc y) (count v))] (vec (concat (subvec v 0 x) (reverse (subvec v x n)) (subvec v n))))
    :mov-pos      (let [a (v x)
                        v (into (subvec v 0 x) (subvec v (inc x)))]
                    (vec (concat (subvec v 0 y) [a] (subvec v y))))
    :rot-let-left (let [n (count v)]
                    (->> v
                      (cycle)
                      (partition n 1)
                      (take n)
                      (map vec)
                      (filter #(= v (step % [:rot-let-pos x])))
                      (first)))))

(defn undo [[op x y]]
  (case op
    :swap-pos [op y x]
    :swap-let [op y x]
    :rot-left [:rot-rigth x]
    :rot-rigth [:rot-left x]
    :rev-pos [op x y]
    :mov-pos [op y x]
    :rot-let-pos [:rot-let-left x]))

(-> [0 1 2 3 4 5 6 7]
  (step [:rot-let-pos 3])
  (step (undo [:rot-let-pos 3]))
  (str/join)
  prn)


(->> i str/split-lines (map parse-op) (reduce step (mapv str "abcdefgh")) (str/join) prn)
;bfheacgd

(->> i str/split-lines (map parse-op) reverse (map undo) (reduce step (mapv str "fbgdceah")) (str/join) prn)
;gcehdbfa
