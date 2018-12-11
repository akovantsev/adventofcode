(ns adventofcode.2015.day04
  (:require [clojure.string :as str]
            [adventofcode.utils :as u]))


(defn -f [secret n-of-zeroes]
  (let [yes (str/join (repeat n-of-zeroes "0"))
        no  (str yes "0")]
    (loop [x 1]
      (when (< x Integer/MAX_VALUE)
        (let [cs (u/md5 (str secret x))]
          (if (and
                (-> cs (str/starts-with? yes))
                (-> cs (str/starts-with? no) not))
            x
            (recur (inc x))))))))

(defn f1 [secret] (-f secret 5))
(defn f2 [secret] (-f secret 6))


(assert (= 609043 (f1 "abcdef")))
(assert (= 1048970 (f1 "pqrstuv")))



(def input "yzbqklnj")

(assert (= (f1 input) 282749))
(assert (= (f2 input) 9962624))
