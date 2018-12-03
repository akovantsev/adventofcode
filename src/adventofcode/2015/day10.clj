(ns adventofcode.2015.day10)

(def input "3113322113")


(defn xform [s]
  (->> s
    (partition-by identity)
    (map #(str (count %) (first %)))
    (apply str)))

(assert (= (xform "1") "11"))
(assert (= (xform "11") "21"))
(assert (= (xform "21") "1211"))
(assert (= (xform "1211") "111221"))
(assert (= (xform "111221") "312211"))

(defn -f [s n]
  (->> s
    (iterate xform)
    (take (inc n))
    (last)
    (count)))

(defn f1 [s] (-f s 40))
(defn f2 [s] (-f s 50))


(assert (not= (f1 input) 252594))
(assert (= (f1 input) 329356))
(assert (= (f2 input) 4666278))
