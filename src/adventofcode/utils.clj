(ns adventofcode.utils
  (:require [clojure.string :as str])
  (:import [java.security MessageDigest]
           [clojure.lang PersistentQueue]))

(set! *print-length* 2000)

(def letters "abcdefghijklmnopqrstuvwxyz")

(defn spy [x] (prn x) x)
(defn spyf>  [x f] (prn (f x)) x)
(defn spyf>> [f x] (prn (f x)) x)

(def sconj (fnil conj #{}))

(defn queue [init] (into PersistentQueue/EMPTY init))

(defn index-by
  ([kf    coll] (persistent! (reduce #(assoc! %1 (kf %2)     %2)  (transient {}) coll)))
  ([kf vf coll] (persistent! (reduce #(assoc! %1 (kf %2) (vf %2)) (transient {}) coll))))


(defn to-int [^String s]
  (Long/parseLong s 10))

(defn str->ints [s] (->> s (re-seq #"-?\d+") (map to-int)))

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
        woffset (- (reduce min xs))
        hoffset (- (reduce min ys))
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
(def turn-back  {:up :down :down :up :left :right :right :left})

(defn next-xy [dir [x y]]
  (case dir
    :up    [x (dec y)]
    :down  [x (inc y)]
    :left  [(dec x) y]
    :right [(inc x) y]))

(defn neighbours [[x y]]
  [[x (dec y)]
   [x (inc y)]
   [(dec x) y]
   [(inc x) y]])

(defn neighbours8 [[x y]]
  (let [dx (dec x) ix (inc x)
        dy (dec y) iy (inc y)]
    [[dx dy] [x dy] [ix dy]
     [dx  y] ,,,,,, [ix  y]
     [dx iy] [x iy] [ix iy]]))

(defn fixed-point [xs] (reduce #(if (= %1 %2) (reduced %1) %2) xs))

(defn shortest-distance [floor next-tiles start finish]
  (loop [shortest Integer/MAX_VALUE
         seen     #{start}
         todo     (-> (PersistentQueue/EMPTY) (conj [0 start]))]
    (if (empty? todo)
      (when-not (= shortest Integer/MAX_VALUE)
        shortest)
      (let [[len xy] (peek todo)
            todo (pop todo)]
        (cond
          (< shortest len) (recur shortest seen todo)
          (= xy finish)    (recur (min shortest len) seen todo)
          :else            (let [xys    (->> xy next-tiles (filter floor) (remove seen))
                                 seen   (into seen xys)
                                 clones (map vector (repeat (inc len)) xys)]
                             (recur shortest seen (into todo clones))))))))


(defn shortest-path [floor next-tiles start finish]
  (loop [shortest Integer/MAX_VALUE
         path     nil ;;excluding finish
         seen     #{start}
         todo     (-> (PersistentQueue/EMPTY) (conj [start]))]
    (if (empty? todo)
      path
      (let [p    (peek todo)
            todo (pop todo)
            xy   (peek p)
            len  (count p)]
        (if (= xy finish)
          (if (< len shortest)
            (recur len p seen todo)
            (recur shortest path seen todo))
          (let [xys  (->> xy next-tiles (filter floor) (remove seen))
                seen (into seen xys)]
            (recur shortest path seen (->> xys (map #(conj p %)) (into todo)))))))))
