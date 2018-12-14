(ns adventofcode.utils
  (:import [java.security MessageDigest]))


(def letters "abcdefghijklmnopqrstuvwxyz")

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