(ns adventofcode.2016.day12
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))


(def input "cpy 1 a\ncpy 1 b\ncpy 26 d\njnz c 2\njnz 1 5\ncpy 7 c\ninc d\ndec c\njnz c -2\ncpy a c\ninc a\ndec b\njnz b -2\ncpy c b\ndec d\njnz d -6\ncpy 19 c\ncpy 11 d\ninc a\ndec d\njnz d -2\ndec c\njnz c -5")
;(def input "cpy 41 a\ninc a\ninc a\ndec a\njnz a 2\ndec a")


(defn parse-instruction [s]
  (->> (str/split s #"\s")
    (map edn/read-string)
    (zipmap [::op ::x ::y])))


(defn simulation [input initial-state]
  (let [ops   (->> input str/split-lines (mapv parse-instruction))
        limit (count ops)]
    (loop [idx   0
           state initial-state]
      (if (<= limit idx)
        state
        (let [{::keys [op x y]} (get ops idx)
              xv (if (symbol? x) (get state x) x)]
          ;(prn [op x y])
          (case op
            cpy (recur (inc idx) (assoc  state y xv))
            inc (recur (inc idx) (update state x inc))
            dec (recur (inc idx) (update state x dec))
            jnz (if (zero? xv)
                  (recur (inc idx) state)
                  (recur (+ idx y) state))))))))


(assert (-> input (simulation {'a 0  'b 0  'c 0  'd 0}) (get 'a) (= 318020)))
(assert (-> input (simulation {'a 0  'b 0  'c 1  'd 0}) (get 'a) (= 9227674)))

