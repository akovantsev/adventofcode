(ns adventofcode.2016.day05
  (:require [adventofcode.utils :as u]
            [clojure.string :as str]))


(def DOOR-ID "reyedfim")
(def PASSWORD-LEN 8)


(defn f1 [door-id]
  (loop [idx      0
         password ""]
    (if (-> password count (= PASSWORD-LEN))
      password
      (let [md5 (u/md5 (str door-id idx))]
        (if-not (str/starts-with? md5 "00000")
          (recur (inc idx) password)
          (recur (inc idx) (str password (nth md5 5))))))))


(defn f2 [door-id]
  (loop [idx      0
         password (zipmap
                    (map #(Character/forDigit % 10) (range PASSWORD-LEN))
                    (repeat nil))]
    (if (->> password vals (not-any? nil?))
      (->> password (sort-by key) (map val) (str/join))
      (let [md5 (u/md5 (str door-id idx))
            idx (inc idx)]
        (if-not (str/starts-with? md5 "00000")
          (recur idx password)
          (let [pos      (nth md5 5)
                newv     (nth md5 6)
                [k oldv] (find password pos)]
            (if (and (contains? password pos) (nil? oldv))
              (recur idx (assoc password pos newv))
              (recur idx password))))))))


#_#_
(assert (= "f97c354d" (f1 DOOR-ID)))
(assert (= "863dde27" (f2 DOOR-ID)))