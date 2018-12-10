(ns adventofcode.2015.day08
  (:require [clojure.string :as str]))

(def lines (str/split-lines (slurp "./resources/2015day08.txt")))


(defn mem-len [s]
  (loop [len 0
         [a b :as all] (seq s)]
    (if (empty? all)
      (- len 2)  ;; ""
      (let [x (case [a b]
                [\\ \"] 2
                [\\ \\] 2
                [\\ \x] 4
                1)]
        (recur (inc len) (drop x all))))))

(assert (= 0  (mem-len (apply str [\" \"]))))
(assert (= 3  (mem-len (apply str [\" \a \b \c \"]))))
(assert (= 7  (mem-len (apply str [\" \a \a \a \\ \" \a \a \a \"]))))
(assert (= 1  (mem-len (apply str [\" \\ \x \2 \7 \"]))))
(assert (= 18 (mem-len (apply str [\" \v \\ \x \f \b \\ \" \l \g \s \\ \" \k \v \j \f \y \w \m \u \t \\ \x \9 \c \r \"]))))


(defn encoded-len [s]
  (loop [len 0
         [x & tail] (seq s)]
    (if (nil? x)
      (+ len 2)  ;; ""
      (if (#{\\ \"} x)
        (recur (+ len 2) tail)
        (recur (+ len 1) tail)))))

(assert (= 6  (encoded-len (apply str [\" \"]))))
(assert (= 9  (encoded-len (apply str [\" \a \b \c \"]))))
(assert (= 16 (encoded-len (apply str [\" \a \a \a \\ \" \a \a \a \"]))))
(assert (= 11 (encoded-len (apply str [\" \\ \x \2 \7 \"]))))

(defn f1 [lines]
  (->> lines
    (map #(- (count %) (mem-len %)))
    (reduce +)))

(defn f2 [lines]
  (->> lines
    (map #(- (encoded-len %) (count %)))
    (reduce +)))


(assert (= 1342 (f1 lines)))
(assert (= 2074 (f2 lines)))