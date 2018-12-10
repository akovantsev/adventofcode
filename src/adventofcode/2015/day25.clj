(ns adventofcode.2015.day25
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))

(def GRID-STR "   |    1         2         3         4         5         6\n---+---------+---------+---------+---------+---------+---------+\n 1 | 20151125  18749137  17289845  30943339  10071777  33511524\n 2 | 31916031  21629792  16929656   7726640  15514188   4041754\n 3 | 16080970   8057251   1601130   7981243  11661866  16474243\n 4 | 24592653  32451966  21345942   9380097  10600672  31527494\n 5 |    77061  17552253  28094349   6899651   9250759  31663883\n 6 | 33071741   6796745  25397450  24659492   1534922  27995004")

(def ROW (dec 3010))
(def COL (dec 3019))

(defn parse-line [s]
  (-> s
    (str/split #"\|")
    (second)
    (str/split #"\s+")
    (as-> $
      (remove str/blank? $)
      (mapv edn/read-string $))))

(defn parse-grid [s]
  (->> s
    str/split-lines
    (drop 2)
    (mapv parse-line)))


(defn f1 [grid-str col row]
  (time
    (loop [x 0  y 0  grid (parse-grid grid-str)]
      (let [nextv (-> grid
                    (get-in [x y])
                    (* 252533)
                    (rem 33554393))
            [x y] (if (= 0 y)
                    [      0 (inc x)]
                    [(inc x) (dec y)])]
        (if (and (= x col) (= y row))
          nextv
          (recur x y (assoc-in grid [x y] nextv)))))))


(assert (= 8997277 (f1 GRID-STR COL ROW)))