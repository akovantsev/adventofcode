(ns adventofcode.utils
  (:require [clojure.string :as str])
  (:import [java.security MessageDigest]))

(set! *print-length* 2000)

(def letters "abcdefghijklmnopqrstuvwxyz")

(defn spy [x] (prn x) x)

(defn to-int [^String s]
  (Long/parseLong s 10))

(def char-to-int {\0 0 \1 1 \2 2 \3 3 \4 4 \5 5 \6 6 \7 7 \8 8 \9 9})

(defmacro locals-map
  ;; https://gist.github.com/noisesmith/3490f2d3ed98e294e033b002bc2de178
  []
  (into {}
    (for [[sym val] &env]
      [(keyword (name sym)) sym])))

(defmacro print-locals-map []
  `(clojure.pprint/pprint (locals-map)))

(defmacro print-locals-including [& ks]
  `(clojure.pprint/pprint (select-keys (locals-map) ~ks)))

(defmacro print-locals-excluding [& ks]
  `(clojure.pprint/pprint (dissoc (locals-map) ~@ks)))


(defn md5
  ;; https://gist.github.com/jizhang/4325757#gistcomment-1993162
  [^String s]
  (->> s
    .getBytes
    (.digest (MessageDigest/getInstance "MD5"))
    (BigInteger. 1)
    (format "%032x")))

(defn city-distance
  [[^int x1 ^int y1]
   [^int x2 ^int y2]]
  (+ (Math/abs (- x2 x1))
    (Math/abs (- y1 y2))))


(defn draw [brushes canvas & [default]]
  (let [xs      (->> canvas keys (map first))
        ys      (->> canvas keys (map second))
        woffset (- (reduce min 0 xs))
        hoffset (- (reduce min 0 ys))
        w       (->> xs (reduce max) inc (+ woffset))
        h       (->> ys (reduce max) inc (+ hoffset))
        default (or default "0")
        blank   (->> (repeat w default) (vec) (repeat h) (vec))]
    (->> canvas
      (reduce-kv
        (fn [v [x y] c]
          (assoc-in v [(+ y hoffset) (+ x woffset)] (brushes c)))
        blank)
      (map str/join)
      (str/join "\n"))))

(def turn-left  {:up :left :left :down :down :right :right :up})
(def turn-right {:up :right :right :down :down :left :left :up})

(defn next-xy [dir [x y]]
  (case dir
    :up    [x (dec y)]
    :down  [x (inc y)]
    :left  [(dec x) y]
    :right [(inc x) y]))
