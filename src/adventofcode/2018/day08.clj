(ns adventofcode.2018.day08
  (:require [clojure.edn :as edn]
            [adventofcode.utils :as u]))


(def input
  (->>
    "8 11 6 2 5 4 3 6 1 6 0 10 9 3 3 1 9 1 1 3 4 6 3 1 3 2 3 2 1 6 0 9 3 8 8 1 1 1 8 9 2 1 2 1 1 1 3 1 8 0 8 2 3 3 8 9 4 1 7 2 1 2 3 3 2 2 1 2 1 2 4 3 2 3 7 1 9 0 6 1 5 7 3 7 8 3 1 3 2 3 1 3 2 1 1 8 0 10 1 5 5 1 6 7 8 1 8 9 3 1 1 3 1 3 3 1 1 6 0 11 2 6 1 4 1 7 5 7 9 1 4 3 2 1 3 1 2 4 2 1 2 1 3 5 3 6 1 5 0 9 1 9 1 9 1 5 5 1 9 1 1 1 1 3 1 8 0 11 3 8 3 4 3 1 3 9 6 4 9 3 3 3 1 2 2 1 1 1 6 0 8 9 2 1 8 7 1 7 1 3 1 3 2 1 1 1 5 2 2 1 5 3 4 1 8 0 10 5 1 9 7 6 3 5 3 6 1 3 1 3 3 1 2 3 1 1 7 0 10 8 5 9 9 7 1 7 2 6 3 1 3 2 2 1 3 1 1 7 0 8 6 1 2 1 3 8 1 2 2 1 1 1 3 1 3 1 2 1 1 3 6 1 8 0 8 2 8 7 6 7 1 7 5 1 3 2 2 1 2 3 3 1 7 0 9 1 1 1 5 5 9 8 5 3 1 3 1 1 1 3 1 1 6 0 10 3 7 1 5 5 8 3 8 8 1 3 1 3 3 1 2 2 1 4 2 3 1 6 6 3 1 4 5 3 6 1 7 0 9 3 1 1 1 1 8 8 2 2 3 3 1 2 3 2 3 1 6 0 11 5 6 9 5 1 1 7 1 1 3 1 3 3 1 3 2 2 1 6 0 7 3 9 7 4 8 4 1 1 1 3 2 1 1 4 2 5 3 3 3 3 4 1 6 0 9 8 5 1 9 4 2 8 3 6 3 1 2 2 2 3 1 8 0 9 4 3 7 7 3 6 2 6 1 2 2 3 1 3 3 1 2 1 8 0 7 9 2 1 8 2 4 3 3 1 2 1 1 1 2 1 3 5 1 3 3 4 1 8 0 8 2 6 9 1 5 4 6 7 2 3 1 3 1 2 2 1 1 6 0 11 7 6 5 1 8 3 1 5 4 1 5 3 3 3 1 2 3 1 9 0 6 7 7 6 1 9 7 2 2 3 2 1 2 1 2 1 5 3 2 4 3 4 1 6 0 6 8 9 2 9 7 1 1 2 3 3 1 1 1 9 0 9 7 3 2 5 9 1 5 7 8 1 3 3 3 1 3 3 3 1 1 8 0 7 3 5 1 8 6 5 1 1 2 1 1 1 2 2 1 1 2 4 3 2 1 2 1 6 5 5 3 5 1 7 0 7 7 4 5 5 9 1 9 1 2 3 3 1 1 2 1 8 0 7 5 9 1 1 2 9 5 1 2 1 2 1 1 2 3 1 5 0 9 6 5 1 8 7 3 2 4 7 2 1 1 2 1 3 3 2 1 3 3 5 1 8 0 11 1 8 1 7 9 1 2 7 3 8 7 3 2 3 1 3 2 1 2 1 7 0 8 6 7 1 3 4 4 9 9 1 1 3 2 1 2 1 1 7 0 8 1 5 9 5 9 7 9 8 3 1 3 3 1 1 3 3 4 3 1 5 3 4 1 8 0 8 9 9 5 9 7 4 1 2 3 2 3 1 1 3 3 1 1 5 0 11 5 1 6 3 4 6 7 7 8 6 6 2 2 3 1 2 1 6 0 11 4 8 1 6 7 5 1 6 9 1 6 1 3 1 2 3 2 3 3 5 5 3 5 1 6 0 6 3 9 1 6 6 1 1 2 1 3 3 2 1 8 0 11 4 3 8 1 3 1 7 8 7 6 1 2 3 2 1 1 1 2 1 1 9 0 9 3 1 9 9 3 7 1 2 2 1 1 1 1 2 2 1 1 3 1 4 4 3 1 3 5 1 8 0 9 5 3 8 1 2 2 8 1 2 2 1 3 2 1 3 1 3 1 8 0 7 1 3 4 9 4 4 9 1 3 2 2 1 1 1 3 1 9 0 8 2 1 7 8 9 5 4 9 3 1 3 1 1 3 3 3 1 4 5 4 2 2 4 4 4 2 7 5 4 3 7 1 7 0 11 6 1 9 1 1 2 7 6 3 9 1 3 1 2 1 3 1 1 1 8 0 8 1 1 9 3 6 9 3 7 2 1 1 3 3 2 1 2 1 7 0 7 4 8 9 1 8 2 1 2 3 1 1 1 1 2 5 3 2 5 3 2 4 3 7 1 6 0 7 6 2 1 9 9 8 3 3 3 1 2 2 1 1 7 0 10 7 5 4 1 3 8 6 7 8 6 3 2 2 3 1 1 2 1 5 0 8 2 7 1 7 6 5 6 6 3 2 1 3 1 3 5 3 4 3 4 1 3 5 1 5 0 11 2 4 6 5 1 2 4 2 8 3 1 1 1 1 2 1 1 9 0 10 2 1 6 2 7 1 5 7 9 1 1 3 3 2 2 1 1 3 3 1 5 0 6 2 1 7 2 9 5 1 2 3 3 1 2 1 1 5 1 3 5 1 7 0 6 8 4 1 3 8 3 1 1 3 3 2 1 2 1 7 0 10 6 5 8 3 1 3 1 6 1 7 1 2 3 2 3 2 1 1 6 0 10 3 3 3 5 2 6 1 6 1 1 1 3 1 3 2 3 1 5 1 3 3 3 7 1 9 0 10 6 2 7 2 4 3 5 8 1 8 1 2 3 1 3 1 1 2 3 1 5 0 11 1 3 5 9 2 5 4 7 6 1 9 3 3 1 1 3 1 9 0 10 1 3 7 7 6 2 7 8 1 9 1 1 2 3 3 2 3 3 2 1 4 1 5 5 2 3 5 7 1 1 4 5 3 6 1 9 0 6 1 1 9 6 4 2 2 1 1 1 3 2 1 3 3 1 6 0 10 1 5 2 1 1 8 1 2 6 8 1 1 3 2 3 1 1 6 0 8 9 8 1 2 3 9 7 4 3 2 2 1 2 2 3 1 5 1 2 1 3 5 1 6 0 10 6 4 9 7 4 3 5 1 7 3 1 3 3 3 1 1 1 5 0 8 1 9 3 5 3 2 3 5 2 3 1 2 1 1 7 0 6 5 4 7 1 8 1 1 1 2 2 1 1 3 5 5 4 2 2 3 5 1 8 0 8 9 1 4 8 8 1 3 4 1 2 1 3 1 3 2 1 1 6 0 9 4 1 1 9 2 1 1 1 2 1 1 2 1 2 1 1 8 0 6 1 7 4 9 6 3 3 1 1 1 1 3 3 2 2 3 4 4 5 3 5 1 7 0 11 9 1 9 7 1 7 7 8 8 1 2 2 2 1 3 2 2 3 1 7 0 6 9 5 6 6 3 1 3 1 3 1 3 1 3 1 7 0 10 3 3 6 6 5 7 1 3 8 1 3 2 2 1 3 3 2 1 2 3 3 3 4 3 1 3 4 4 3 3 4 1 8 0 9 1 7 1 3 4 1 6 9 3 2 1 1 2 3 1 1 1 1 8 0 9 6 1 1 3 1 4 8 6 7 3 3 1 1 1 2 1 1 1 9 0 9 9 5 1 1 2 7 2 1 2 3 1 2 1 3 1 1 1 2 3 2 3 2 3 4 1 7 0 7 9 6 9 1 8 5 2 3 1 2 3 3 3 2 1 9 0 11 8 2 1 6 6 7 5 1 5 2 9 3 1 1 3 1 2 3 2 1 1 8 0 6 1 1 1 1 5 7 1 1 1 3 2 2 2 2 3 2 2 1 3 6 1 8 0 10 1 7 5 8 4 9 2 1 9 2 1 3 1 1 2 3 1 2 1 9 0 9 4 1 8 3 9 1 1 8 4 3 1 3 2 1 1 1 1 1 1 8 0 6 7 1 7 1 7 2 2 2 1 3 2 1 1 2 4 1 2 1 3 3 3 6 1 5 0 10 1 1 7 2 6 8 9 6 1 5 2 3 1 3 3 1 9 0 7 7 1 7 4 2 2 8 2 1 1 1 2 2 1 1 3 1 9 0 6 5 2 1 6 5 9 3 3 1 2 1 2 3 2 2 4 5 3 5 3 2 4 4 2 4 1 6 3 4 3 3 7 1 5 0 9 3 4 9 1 4 9 8 6 3 2 1 3 2 1 1 8 0 6 3 3 1 2 2 1 3 2 1 1 1 1 3 3 1 7 0 11 1 3 1 4 1 7 7 6 1 5 3 3 3 1 2 1 2 1 5 3 3 4 3 2 3 3 6 1 9 0 6 7 8 6 7 1 8 2 2 1 1 3 2 3 2 3 1 5 0 6 5 5 1 8 3 2 1 1 1 2 2 1 9 0 9 5 8 4 9 1 7 5 9 2 2 3 1 3 1 1 1 3 2 4 2 3 4 4 2 3 4 1 7 0 6 1 2 1 5 5 8 1 2 2 1 3 3 3 1 7 0 9 4 7 3 3 6 7 2 1 1 3 1 1 2 1 3 3 1 7 0 10 9 2 3 1 1 3 9 4 8 9 1 1 1 2 2 3 2 1 2 3 5 3 7 1 8 0 10 1 6 1 1 1 7 6 8 2 5 1 3 1 3 1 2 1 1 1 9 0 9 1 1 6 1 5 8 5 5 2 1 2 2 1 1 1 2 1 1 1 9 0 8 6 5 1 5 8 1 7 8 3 2 1 2 2 1 1 1 1 2 5 3 2 2 3 2 2 4 3 4 4 3 6 1 7 0 6 1 6 3 1 5 5 1 1 1 2 2 1 1 1 5 0 8 9 4 8 4 6 7 1 3 2 3 1 1 3 1 5 0 8 8 6 8 4 6 1 6 2 1 1 2 1 3 2 4 1 1 2 1 3 6 1 7 0 10 1 5 8 2 9 6 7 2 7 1 2 2 1 3 1 3 3 1 6 0 11 9 2 9 2 1 4 1 9 6 7 8 2 1 2 1 1 2 1 5 0 7 1 7 2 9 7 4 8 3 3 2 1 2 1 3 4 2 5 3 3 4 1 7 0 7 4 1 1 7 5 5 6 1 2 1 1 1 2 2 1 6 0 10 1 9 9 7 1 6 2 4 2 7 1 3 3 1 1 1 1 6 0 8 5 2 7 3 2 1 8 6 3 2 3 1 1 1 5 2 5 2 3 5 1 8 0 8 6 1 2 5 1 4 8 1 3 1 2 3 1 3 1 1 1 8 0 9 5 8 9 3 4 9 8 2 1 1 3 1 3 1 1 1 2 1 8 0 9 8 1 8 7 5 3 7 5 1 2 1 1 2 3 1 1 2 1 4 5 2 2 1 1 6 3 5 4 3 5 1 8 0 11 1 1 2 9 4 4 2 1 7 1 9 2 1 1 1 1 2 3 3 1 9 0 7 2 3 6 9 1 9 6 3 3 1 1 1 2 3 2 2 1 8 0 10 7 8 8 8 1 3 8 5 6 3 1 2 2 1 3 3 1 1 2 1 1 1 5 3 7 1 7 0 8 2 2 7 9 1 6 3 5 2 3 2 3 3 1 3 1 9 0 11 5 9 4 3 1 5 5 5 7 1 1 3 1 2 1 3 2 2 2 1 1 5 0 10 3 5 2 1 1 5 9 2 1 9 2 3 1 2 3 3 3 3 3 3 4 5 3 6 1 6 0 9 2 2 5 1 6 4 9 1 6 3 2 1 1 3 3 1 8 0 8 8 2 3 1 3 1 5 5 1 2 3 3 3 2 2 2 1 6 0 11 4 7 7 4 1 7 1 3 9 4 2 3 1 2 3 3 2 2 1 3 2 4 2 3 4 1 5 0 7 1 1 9 4 3 5 8 1 3 1 1 3 1 8 0 7 1 1 3 6 2 8 6 1 1 3 3 1 1 3 2 1 5 0 10 4 7 1 4 9 8 9 7 7 1 1 3 3 3 3 3 1 1 2 3 5 1 8 0 11 9 2 3 1 6 5 8 4 1 6 5 2 3 1 3 1 2 2 2 1 6 0 9 8 8 6 1 1 6 5 1 8 3 2 2 1 3 2 1 6 0 11 5 1 4 2 3 6 1 1 2 8 8 2 1 3 3 1 3 2 3 4 2 5 5 7 3 4 5 4 3 5 1 6 0 10 2 1 8 3 5 1 2 9 2 6 3 1 3 2 1 1 1 5 0 11 1 2 3 1 5 2 3 3 4 6 6 1 1 2 2 1 1 9 0 10 6 3 4 7 3 2 4 9 4 1 1 1 2 1 2 2 3 1 1 1 3 2 5 3 3 5 1 8 0 8 1 3 8 5 1 7 6 3 3 3 1 3 1 3 1 3 1 5 0 9 6 3 9 9 7 1 9 2 4 1 1 2 2 3 1 9 0 8 7 1 2 1 9 6 8 6 1 2 1 3 3 3 1 1 1 3 3 5 1 3 3 6 1 5 0 10 7 9 1 3 4 9 2 1 1 6 2 1 1 1 1 1 7 0 7 2 9 1 6 1 7 5 3 3 2 1 1 2 2 1 9 0 6 7 7 7 4 3 1 2 1 3 1 3 1 1 3 3 3 3 4 1 5 2 3 6 1 7 0 11 1 2 4 7 5 5 4 3 6 7 9 2 1 1 3 1 1 2 1 8 0 7 4 7 2 8 5 1 2 1 3 1 1 3 2 1 1 1 6 0 7 6 1 8 9 9 9 4 3 2 1 3 3 1 3 5 2 3 1 5 3 4 1 8 0 10 5 4 8 1 3 6 8 9 1 8 1 3 1 3 3 1 1 2 1 6 0 10 4 7 2 6 1 6 1 8 8 9 1 2 1 3 1 1 1 7 0 10 5 8 1 8 6 3 1 7 7 2 1 2 2 2 1 1 1 2 1 1 3 4 5 5 6 5 5 3 5 1 9 0 11 3 3 1 8 5 7 9 9 4 1 1 3 1 2 2 3 1 2 1 1 1 7 0 8 1 5 1 2 6 9 3 4 3 1 2 2 1 2 2 1 5 0 11 4 2 2 5 3 1 9 3 8 6 2 1 3 1 1 2 1 4 3 1 1 3 7 1 7 0 7 5 8 1 2 9 4 6 2 2 3 1 1 2 2 1 7 0 11 7 4 4 6 1 6 7 9 7 1 4 3 2 1 3 1 1 1 1 6 0 11 1 3 6 2 8 3 3 1 3 4 4 2 1 1 3 3 2 4 2 1 5 4 3 4 3 6 1 6 0 8 1 7 2 6 3 8 3 5 2 1 2 1 2 1 1 5 0 11 2 6 1 6 5 1 6 1 3 9 2 3 1 1 1 1 1 8 0 10 3 1 4 8 3 6 5 3 9 1 2 2 2 2 1 1 3 1 1 3 3 2 4 3 3 5 1 5 0 9 1 5 3 6 8 8 8 5 7 1 2 1 3 3 1 8 0 8 5 5 8 7 3 5 1 6 2 2 2 2 2 1 3 2 1 9 0 8 9 3 8 6 2 9 9 1 3 3 2 1 2 3 2 1 3 2 3 3 3 3 3 7 1 9 0 7 7 6 1 9 3 4 2 2 3 2 1 3 1 1 2 1 1 9 0 7 1 1 7 2 8 5 4 2 3 3 3 3 1 2 1 2 1 7 0 11 1 6 9 3 7 4 9 4 8 4 8 1 2 1 3 1 1 3 5 1 1 4 1 1 2 5 3 7 7 1 5 5 3 5 1 7 0 8 4 3 1 3 6 8 5 4 3 2 3 1 3 2 2 1 8 0 10 4 1 6 7 9 3 2 9 2 6 3 1 2 3 2 2 3 2 1 9 0 7 4 4 4 2 7 1 9 2 2 1 3 3 3 1 1 2 1 2 2 2 4 3 6 1 7 0 9 8 6 2 1 8 2 6 3 3 2 2 2 1 2 1 2 1 6 0 9 7 2 1 7 7 9 1 1 6 2 1 1 2 3 3 1 9 0 10 2 2 3 5 5 8 8 6 2 1 1 1 3 2 2 3 3 2 1 1 5 3 4 1 2 3 7 1 9 0 7 3 1 5 1 7 4 7 2 3 1 2 1 1 3 3 2 1 6 0 9 2 7 3 3 3 8 6 7 1 1 1 2 2 1 1 1 7 0 11 1 5 5 4 4 6 4 4 5 5 6 3 1 1 3 3 2 2 5 2 2 5 2 5 3 3 7 1 7 0 8 1 6 8 3 8 1 3 3 2 3 1 1 3 1 2 1 7 0 10 1 8 2 8 5 6 6 3 6 5 1 2 2 2 2 2 1 1 6 0 8 4 5 1 4 1 2 5 9 1 1 2 2 3 1 5 2 2 2 5 5 4 3 5 1 6 0 9 4 5 2 6 2 1 5 8 4 2 2 2 1 1 1 1 8 0 9 8 1 1 4 9 6 3 2 8 3 3 1 2 1 2 1 3 1 8 0 11 7 1 4 7 2 5 2 2 6 6 6 1 3 2 3 3 2 3 1 4 3 1 4 4 6 1 5 6 3 4 2 2 6 2 5 4 3 5 1 8 0 6 1 2 9 5 1 5 3 3 3 3 1 2 1 3 1 5 0 10 7 8 1 4 1 6 9 7 1 2 1 3 1 1 1 1 6 0 6 1 2 8 1 5 8 1 3 1 1 3 2 5 3 4 2 5 3 6 1 7 0 8 9 1 7 5 7 2 9 2 2 3 2 1 2 1 1 1 6 0 9 3 1 7 9 5 5 2 8 6 3 1 1 1 2 1 1 9 0 11 8 1 2 9 6 1 8 2 3 6 2 3 3 2 1 3 1 1 1 2 5 5 3 3 5 4 3 6 1 9 0 11 2 3 2 6 1 4 2 1 6 5 1 2 1 3 1 2 1 2 2 1 1 5 0 7 9 9 1 6 5 3 8 1 1 3 1 2 1 6 0 8 3 1 4 4 3 1 3 7 1 1 3 1 2 1 1 1 4 3 5 1 3 4 1 8 0 9 5 5 7 4 1 9 2 7 8 1 1 3 1 2 3 3 2 1 9 0 8 5 4 1 7 4 8 8 3 1 2 2 1 1 3 1 2 3 1 7 0 11 7 8 6 9 7 4 2 1 8 3 4 2 1 2 2 1 2 2 2 5 2 5 3 4 1 9 0 10 4 1 8 7 6 2 1 5 7 3 1 3 3 1 2 3 1 2 2 1 6 0 7 8 5 6 1 9 4 8 3 2 2 2 1 1 1 5 0 7 5 5 6 6 5 5 1 1 3 3 1 2 3 3 2 1 3 3 6 7 4 3 3 4 1 7 0 8 7 4 1 2 5 5 6 5 1 3 2 1 1 1 2 1 8 0 10 5 7 2 4 9 7 1 4 2 6 2 1 2 3 3 1 1 3 1 6 0 11 8 9 5 1 9 8 4 4 8 2 2 2 1 2 3 3 2 4 3 1 3 3 4 1 6 0 7 3 7 3 4 5 3 1 3 1 1 3 3 1 1 8 0 7 1 2 9 2 5 8 5 1 2 1 1 2 3 1 3 1 9 0 8 4 6 6 1 6 3 9 6 2 3 2 1 2 3 1 1 3 5 3 1 4 3 6 1 7 0 10 1 1 4 2 1 3 2 1 1 2 1 1 1 2 1 3 1 1 7 0 9 5 8 5 9 4 9 4 1 9 1 1 3 1 3 3 1 1 6 0 6 1 4 6 7 8 6 2 3 1 3 1 2 5 2 1 3 4 2 3 4 1 8 0 10 1 3 4 9 2 4 8 1 9 4 2 2 2 2 3 1 2 1 1 7 0 8 3 1 8 5 6 4 1 1 2 1 1 1 2 2 1 1 9 0 6 6 5 5 1 5 2 1 1 3 1 1 2 3 3 3 1 1 4 5 3 2 5 4 4 3 5 1 8 0 10 5 6 2 1 1 5 1 2 4 4 2 3 2 2 1 2 1 3 1 6 0 10 2 4 1 9 7 5 3 8 6 6 2 1 2 3 2 1 1 9 0 7 1 3 2 6 8 1 5 1 2 3 3 1 1 1 1 2 3 1 3 1 5 3 5 1 7 0 8 7 7 4 5 2 5 1 4 1 2 3 3 1 3 3 1 8 0 8 1 7 1 5 5 1 1 2 1 1 1 1 2 2 2 3 1 8 0 7 2 7 9 1 8 2 2 1 3 3 2 2 1 1 3 3 4 3 1 5 3 7 1 9 0 9 1 6 3 5 9 6 7 3 9 3 3 2 2 2 1 1 2 1 1 7 0 11 7 3 8 2 3 6 1 7 1 9 1 2 1 2 1 1 1 2 1 9 0 11 5 6 9 7 1 6 3 1 9 8 4 3 1 1 2 2 2 1 3 2 1 4 3 1 4 2 1 3 4 1 7 0 10 5 9 5 3 1 1 7 4 8 7 1 2 1 3 1 3 2 1 6 0 8 1 1 1 7 6 4 4 4 2 2 1 2 1 2 1 8 0 10 6 6 1 3 6 9 7 1 8 3 2 1 3 1 2 2 2 2 1 4 2 2 3 2 2 3 4 3 3 6 1 7 0 9 5 3 1 3 9 7 4 1 8 1 3 2 2 2 1 1 1 8 0 6 8 1 1 1 5 2 1 3 2 2 2 2 1 3 1 6 0 9 2 2 3 7 1 9 4 5 4 2 1 1 1 1 1 1 3 1 5 4 1 3 6 1 6 0 6 1 1 3 5 6 4 1 3 3 3 2 1 1 7 0 8 5 6 7 1 3 1 4 6 3 3 1 1 3 3 3 1 8 0 6 9 2 4 1 3 8 3 1 2 1 1 3 1 2 1 2 1 1 2 5 3 7 1 5 0 6 5 6 8 1 1 1 3 2 1 1 2 1 5 0 6 6 5 5 5 5 1 1 2 3 2 1 1 9 0 6 1 9 9 1 6 1 3 2 3 1 1 2 1 2 1 1 1 5 4 1 5 2 3 4 1 5 0 9 4 7 2 9 3 7 1 6 5 2 1 3 1 1 1 8 0 9 1 5 3 4 9 6 6 7 7 1 3 3 3 2 1 3 3 1 5 0 9 6 7 2 4 9 7 9 7 1 1 2 3 1 1 4 4 2 5 2 5 5 5 4 3 5 1 7 0 6 7 3 4 9 7 1 1 1 1 3 2 3 3 1 5 0 8 7 5 1 9 8 4 1 1 2 2 1 3 3 1 6 0 9 3 5 7 7 5 9 1 2 3 2 2 2 1 1 2 2 2 5 2 3 3 6 1 5 0 7 6 1 1 4 6 1 7 2 1 2 1 1 1 7 0 9 8 8 4 3 4 8 5 1 1 3 2 1 3 1 1 2 1 6 0 10 4 3 3 9 1 6 4 4 5 6 2 1 2 3 2 2 1 2 1 4 1 2 3 5 1 8 0 10 8 3 2 1 7 1 5 4 8 8 1 3 1 2 1 3 1 3 1 5 0 8 4 2 1 6 6 7 1 2 1 3 1 1 3 1 5 0 8 8 1 9 8 8 1 9 9 1 1 2 1 2 4 3 2 1 4 3 4 1 9 0 10 4 8 6 4 4 1 3 1 2 2 1 1 3 2 2 1 2 2 1 1 6 0 10 2 7 5 8 7 7 5 1 4 2 2 2 2 2 1 1 1 6 0 6 4 5 7 5 1 1 3 2 3 3 3 1 5 5 2 1 3 5 1 7 0 9 2 7 2 3 1 8 9 8 1 1 1 3 1 1 1 1 1 6 0 10 4 1 8 1 6 5 9 4 3 2 1 3 1 1 1 3 1 5 0 9 3 2 1 1 5 6 8 9 1 1 2 2 1 2 5 3 1 4 1 4 5 6 5 5 3 3 5 1 6 0 10 5 9 2 9 2 8 3 7 7 1 2 1 1 1 1 1 1 9 0 8 3 5 1 1 8 1 5 3 3 1 1 1 1 3 3 1 2 1 6 0 8 8 4 2 9 9 4 1 4 1 1 1 1 2 3 3 5 4 3 5 3 4 1 8 0 8 1 7 3 4 2 5 3 7 3 2 3 2 1 2 1 1 1 6 0 9 7 7 6 9 4 1 6 5 1 3 3 1 2 2 3 1 9 0 7 2 1 2 9 9 9 3 1 1 1 1 2 1 1 2 3 5 3 3 3 3 6 1 7 0 10 1 4 5 3 1 8 1 1 9 6 1 1 3 1 1 2 1 1 9 0 9 2 1 6 8 1 4 9 6 1 1 1 2 2 2 2 1 3 2 1 9 0 9 6 5 3 3 1 8 8 1 1 3 1 2 1 2 1 2 1 3 4 3 2 2 2 5 3 5 1 9 0 6 6 1 3 3 3 3 1 3 2 1 1 1 2 1 1 1 8 0 8 6 5 1 7 2 9 4 3 2 3 1 2 1 3 1 2 1 6 0 10 2 1 1 8 9 2 2 8 7 1 1 2 2 1 1 1 4 2 1 2 4 3 4 1 5 0 11 5 3 8 8 8 1 5 2 4 1 7 2 1 3 1 1 1 7 0 6 4 2 7 1 3 6 1 2 1 3 1 1 1 1 7 0 8 2 2 1 4 1 2 5 5 1 1 1 1 3 1 1 4 3 4 3 5 2 7 7 4 6 2 4 3 3 7 1 6 0 6 6 8 7 4 4 1 1 1 1 3 1 3 1 9 0 6 1 3 6 9 1 7 2 2 3 3 1 1 3 1 2 1 9 0 10 4 1 3 7 8 5 3 7 1 7 1 1 2 2 1 3 3 1 2 4 4 1 3 4 5 1 3 5 1 5 0 7 6 4 5 9 1 7 6 3 1 3 1 3 1 6 0 9 8 9 9 6 1 3 4 6 5 1 3 1 1 2 3 1 6 0 10 1 9 9 3 9 1 7 6 4 2 1 1 3 2 1 1 2 2 2 3 3 3 6 1 9 0 9 8 6 8 4 1 5 3 1 6 2 1 1 3 3 3 1 3 1 1 7 0 6 1 3 2 1 3 7 1 1 1 3 1 3 3 1 7 0 11 2 1 6 1 1 3 5 6 7 7 7 2 2 1 2 2 2 1 1 1 1 1 3 1 3 7 1 8 0 11 9 6 5 8 6 1 7 9 3 1 4 2 1 3 2 3 1 2 3 1 5 0 6 4 2 5 9 1 2 1 2 2 3 2 1 6 0 7 1 5 8 4 2 9 5 1 2 1 2 3 1 5 3 5 3 4 3 3 1 3 5 4 3 3 6 1 8 0 9 2 3 1 4 4 2 1 9 2 1 1 3 3 3 1 2 1 1 8 0 6 9 9 5 1 9 1 1 1 1 1 1 1 1 3 1 8 0 7 6 1 7 9 2 5 6 2 1 2 3 1 3 2 1 5 5 4 3 2 2 3 6 1 5 0 8 3 1 9 9 5 1 6 1 2 2 2 1 2 1 8 0 8 8 1 1 7 6 4 6 4 3 2 1 2 3 2 1 1 1 6 0 9 5 8 5 1 4 1 6 9 6 1 2 3 2 1 1 2 5 4 4 4 5 3 7 1 9 0 6 1 4 2 1 6 7 1 2 2 1 3 1 1 2 2 1 5 0 10 7 4 1 8 6 2 1 6 7 3 1 3 2 2 2 1 6 0 9 9 4 1 5 8 2 3 4 9 1 1 1 3 2 2 1 5 5 5 1 5 1 3 4 1 6 0 8 6 4 7 2 1 4 7 5 1 3 3 1 1 2 1 8 0 10 3 8 7 1 1 7 1 3 7 1 3 2 3 1 1 1 2 3 1 7 0 6 6 8 5 1 5 5 2 1 3 3 1 1 1 1 5 3 2 6 4 5 4 3 3 6 1 7 0 9 4 1 3 1 9 2 4 9 8 1 3 2 2 1 1 2 1 8 0 11 8 3 4 7 3 1 4 3 9 4 9 1 3 3 3 1 2 3 2 1 5 0 11 8 5 6 5 2 7 7 7 5 8 1 2 3 3 1 1 1 3 3 3 1 1 3 6 1 7 0 9 8 3 1 8 4 6 3 6 7 2 1 2 1 3 2 2 1 6 0 7 6 1 4 8 5 1 1 1 2 1 2 3 3 1 9 0 11 7 1 5 8 7 3 3 7 1 2 8 2 3 3 3 1 2 1 1 1 5 1 3 4 4 5 3 4 1 8 0 10 2 5 4 9 7 1 3 9 9 3 2 2 3 1 3 1 1 1 1 9 0 9 8 4 2 1 7 3 9 9 8 3 3 2 3 1 2 2 1 3 1 7 0 10 8 1 6 5 4 2 7 1 2 4 2 3 1 1 1 1 1 1 5 3 1 3 5 1 9 0 9 5 4 1 1 6 2 1 8 8 2 3 2 1 2 3 1 2 3 1 6 0 6 7 2 4 1 7 8 1 2 1 3 1 2 1 6 0 8 3 5 4 6 1 4 9 6 1 3 2 2 1 2 4 3 2 3 3 6 2 5 5 4 3 4 1 7 0 10 2 5 5 1 2 6 7 7 2 7 1 1 1 2 2 3 1 1 7 0 7 3 7 4 1 9 2 7 3 1 2 2 1 1 1 1 5 0 6 9 1 7 9 8 4 1 1 2 3 1 3 4 3 4 3 7 1 5 0 6 3 1 2 3 4 4 1 1 2 2 1 1 8 0 9 5 5 5 9 4 7 8 1 1 3 1 3 2 1 3 1 1 1 9 0 9 2 1 4 9 5 2 7 5 7 1 3 3 1 1 3 2 1 3 2 1 5 1 5 2 5 3 7 1 6 0 10 8 1 9 5 4 4 9 5 6 2 3 1 3 3 1 1 1 8 0 9 8 4 4 1 9 1 7 9 2 3 1 1 2 2 2 3 2 1 5 0 8 1 2 6 6 6 6 8 3 3 3 1 1 1 1 3 3 5 2 2 5 3 7 1 8 0 7 6 2 6 1 1 5 7 1 1 1 3 1 2 2 1 1 7 0 10 3 7 5 5 1 5 6 2 2 1 3 1 1 2 2 1 2 1 7 0 7 1 8 7 7 1 8 6 1 3 2 3 3 3 2 1 3 2 3 1 2 3 3 4 1 8 0 8 7 6 3 9 4 1 8 7 1 1 2 2 1 3 1 2 1 7 0 9 9 1 8 7 1 4 2 8 1 3 3 1 3 3 1 2 1 7 0 11 1 9 7 6 6 3 1 8 8 4 4 1 3 1 1 1 3 2 1 3 3 4 4 4 2 7 4 5 3 6 1 8 0 6 8 9 8 9 1 1 1 3 1 3 1 1 3 1 1 8 0 7 6 3 3 6 1 1 3 1 3 3 1 1 1 1 2 1 7 0 6 1 5 3 1 1 3 3 2 1 1 3 3 3 3 2 5 2 2 5 3 5 1 6 0 6 3 5 2 1 1 2 1 2 1 1 2 1 1 8 0 6 7 5 3 7 3 1 1 3 3 3 2 2 1 1 1 6 0 8 1 8 3 9 2 3 3 6 1 1 1 3 1 2 5 4 2 2 1 3 5 1 6 0 6 6 2 7 1 4 2 2 3 3 2 1 3 1 5 0 7 6 2 5 1 3 3 8 2 3 2 1 3 1 9 0 9 8 4 9 7 1 1 7 3 9 1 1 3 3 3 1 2 1 3 3 2 4 1 5 3 5 1 9 0 6 2 9 1 6 1 7 1 1 3 1 1 1 3 1 3 1 9 0 6 1 3 2 4 1 1 2 3 1 3 1 3 3 1 3 1 9 0 9 6 1 3 8 9 7 9 4 3 1 3 1 3 3 3 1 2 3 5 4 1 1 3 1 4 5 4 3 4 4 3 6 1 9 0 11 1 5 5 7 8 6 2 1 7 9 9 2 2 3 1 2 3 1 1 3 1 9 0 8 9 4 1 7 7 5 6 9 1 1 3 3 3 1 3 3 2 1 6 0 10 3 7 9 7 3 9 9 1 8 8 2 3 2 3 2 1 1 2 3 1 1 3 3 4 1 6 0 6 9 4 1 1 8 1 2 3 1 1 2 1 1 9 0 6 1 8 8 3 2 9 2 3 1 3 3 2 2 2 3 1 8 0 6 7 1 2 7 2 9 2 2 2 1 2 1 2 2 1 1 1 2 3 6 1 9 0 10 7 5 1 4 1 3 5 4 2 5 2 1 1 3 2 1 2 1 3 1 7 0 9 2 6 1 1 9 3 8 1 7 1 1 2 2 3 3 1 1 9 0 8 1 5 1 3 7 2 8 3 3 3 2 1 1 1 1 1 3 5 1 3 1 5 2 3 6 1 6 0 6 7 4 2 8 1 6 3 3 2 1 1 1 1 9 0 7 4 4 1 2 1 2 6 2 2 1 2 1 1 3 1 2 1 8 0 6 1 6 5 4 1 2 1 3 3 3 2 3 3 2 3 2 5 4 2 3 3 5 4 2 2 4 7 3 4 4 3 4 1 9 0 9 1 6 2 8 9 6 6 8 5 1 1 1 1 2 2 3 1 1 1 6 0 10 1 1 4 8 6 6 2 6 6 8 1 2 2 3 2 1 1 5 0 10 5 2 1 5 1 3 4 7 4 1 1 1 3 3 3 2 1 1 1 3 4 1 5 0 11 6 2 1 6 6 6 5 3 9 2 1 1 3 1 2 1 1 7 0 6 3 5 7 1 1 7 1 3 2 1 3 2 1 1 6 0 7 5 3 5 5 2 1 1 1 1 1 3 3 1 5 4 1 2 3 6 1 5 0 9 7 9 9 1 1 4 6 6 7 2 3 1 1 1 1 7 0 7 1 2 5 1 5 1 6 2 2 1 1 3 3 3 1 9 0 10 3 3 7 1 1 1 5 9 3 4 1 2 2 3 1 1 1 1 3 3 5 3 3 3 3 3 5 1 9 0 10 1 8 4 1 7 2 2 1 3 4 1 1 1 2 1 2 1 2 1 1 9 0 9 7 7 9 3 2 8 6 7 1 1 1 3 1 2 3 3 1 3 1 8 0 9 6 2 4 1 1 1 4 3 3 2 1 3 1 3 3 1 1 2 4 3 2 1 1 1 4 2 5 3 3 4 1 9 0 11 7 2 5 1 2 4 6 7 5 5 3 2 3 2 2 3 2 3 3 1 1 5 0 6 1 3 3 9 1 5 3 3 3 1 1 1 7 0 8 9 1 3 8 6 7 1 8 2 2 1 3 1 2 2 5 1 3 5 3 7 1 5 0 7 4 1 7 8 3 4 3 2 1 1 1 3 1 6 0 11 6 5 5 6 3 8 1 6 4 1 1 1 1 1 2 3 2 1 6 0 8 6 7 1 3 7 3 1 7 2 2 3 1 1 1 1 5 2 2 1 4 2 3 5 1 9 0 8 3 7 3 7 2 1 7 1 1 3 2 2 1 2 3 2 1 1 9 0 11 1 8 9 6 6 8 9 9 1 5 8 3 3 2 2 2 3 2 1 1 1 8 0 8 1 3 2 3 8 7 1 9 1 2 3 3 3 2 2 3 3 3 2 5 1 3 4 1 5 0 9 7 2 1 3 5 4 1 6 1 1 3 1 1 3 1 6 0 9 4 3 9 7 1 2 8 5 5 3 1 2 1 1 3 1 5 0 8 5 8 4 1 2 9 1 1 3 1 3 3 1 1 1 2 4 3 6 1 6 0 11 1 8 7 3 8 5 2 6 8 1 8 1 1 2 1 2 1 1 7 0 9 8 2 1 9 4 7 4 1 1 2 1 3 2 2 1 2 1 5 0 8 5 2 3 7 3 7 1 9 2 1 2 2 3 3 2 4 3 1 1 2 4 4 4 3 3 6 1 6 0 10 1 1 1 6 4 3 3 1 9 7 1 1 3 3 3 3 1 7 0 6 8 6 4 1 8 1 3 3 1 1 3 1 1 1 6 0 8 5 1 3 5 5 4 6 5 3 1 1 1 3 1 1 4 2 4 4 1 3 4 1 7 0 11 3 9 2 7 2 2 7 7 7 1 6 1 2 2 1 2 1 2 1 5 0 6 2 3 5 2 1 3 1 1 3 1 2 1 8 0 10 8 4 4 3 8 2 3 8 1 5 3 1 1 2 3 1 1 2 3 3 2 1 3 5 1 5 0 10 9 6 2 4 4 3 4 3 1 6 2 2 1 3 1 1 5 0 7 8 8 1 9 3 4 9 1 1 3 3 2 1 5 0 6 7 6 8 3 1 3 1 3 2 3 2 1 4 4 5 2 3 5 1 9 0 10 1 1 1 4 7 2 6 1 6 2 1 1 2 1 1 3 1 3 2 1 7 0 11 9 2 4 7 9 1 6 5 3 6 2 1 2 1 2 2 1 1 1 9 0 11 6 3 4 6 4 8 1 7 3 4 1 1 2 2 1 1 3 1 2 2 5 3 2 3 1 3 2 6 5 3 3 6 1 6 0 8 7 9 9 1 5 1 2 2 3 2 1 1 2 1 1 6 0 11 4 6 1 7 9 2 1 6 5 3 2 1 2 2 1 2 3 1 5 0 10 7 1 3 9 4 8 5 8 1 4 2 3 1 3 1 1 1 2 3 1 2 3 4 1 7 0 6 8 7 6 2 4 1 3 1 1 3 1 1 2 1 7 0 7 5 6 5 8 1 8 8 1 1 2 3 2 1 2 1 6 0 11 9 8 6 9 9 9 2 6 4 1 7 1 1 1 1 3 1 4 3 1 5 3 6 1 9 0 7 8 1 3 5 4 7 1 1 1 1 1 1 1 2 1 2 1 6 0 9 4 9 9 2 1 9 2 9 3 3 1 2 2 3 3 1 6 0 8 4 1 3 9 5 3 1 8 3 2 1 2 1 1 1 2 1 2 1 3 3 5 1 8 0 7 1 2 8 7 7 3 8 1 3 2 3 3 1 3 1 1 7 0 10 9 3 1 6 1 4 6 1 7 4 2 3 3 1 1 1 3 1 5 0 6 5 3 7 5 9 1 1 1 1 1 2 2 1 1 2 2 3 4 1 7 0 7 5 5 1 2 8 6 4 2 3 1 2 1 3 3 1 8 0 11 2 6 1 1 5 6 9 7 1 4 1 1 1 1 2 3 3 2 3 1 6 0 7 2 9 1 1 1 1 3 1 2 1 2 2 1 3 2 3 1 3 6 3 4 3 3 5 1 5 0 11 1 1 9 7 6 1 3 9 5 1 5 1 2 2 3 1 1 8 0 10 3 3 5 2 2 3 2 5 1 2 1 3 1 2 2 1 1 3 1 5 0 7 8 3 1 3 5 4 7 1 3 2 3 1 3 3 3 1 2 3 4 1 9 0 7 2 8 1 3 7 5 4 2 1 3 3 2 1 3 2 3 1 5 0 9 8 1 8 5 8 8 4 1 5 1 2 2 1 2 1 6 0 7 3 2 7 7 4 4 1 2 1 1 1 3 3 2 2 1 1 3 7 1 5 0 11 9 1 8 4 2 2 1 8 9 8 5 1 1 1 2 2 1 8 0 8 3 4 3 1 9 3 1 1 2 3 2 1 3 3 1 3 1 9 0 8 6 9 2 3 1 9 3 4 2 3 3 1 1 3 1 2 1 1 3 2 3 4 4 4 3 4 1 5 0 6 9 6 8 1 4 1 2 1 3 1 1 1 9 0 9 7 1 9 9 4 4 2 5 9 3 1 1 1 2 1 2 1 2 1 5 0 8 1 6 4 7 7 5 7 4 3 1 3 2 3 3 1 4 3 3 3 5 5 5 3 4 1 9 0 7 7 8 5 1 8 4 2 2 3 2 3 1 1 3 1 3 1 6 0 10 1 3 9 6 2 7 5 3 1 4 1 1 1 2 3 1 1 7 0 8 8 5 6 1 9 2 7 4 2 1 3 2 3 1 1 2 1 5 4 3 4 1 8 0 9 2 3 4 7 1 3 7 3 6 1 3 3 3 2 2 2 1 1 6 0 10 7 1 6 3 1 3 1 4 9 1 1 3 3 1 2 3 1 9 0 10 2 8 5 3 2 1 3 4 7 2 2 2 1 3 1 3 3 2 3 3 4 2 3 3 5 1 7 0 9 1 9 7 3 2 1 4 5 2 3 3 1 1 2 1 3 1 5 0 10 1 7 3 8 4 2 4 9 6 4 3 1 2 3 1 1 7 0 9 9 1 5 4 1 4 4 7 1 2 1 2 2 1 3 2 2 5 2 2 1 3 4 1 8 0 9 4 8 1 8 9 3 5 1 1 2 2 3 1 1 3 1 2 1 8 0 6 8 4 6 1 1 2 1 2 3 1 2 3 3 1 1 5 0 9 1 6 6 4 1 2 5 5 4 3 2 1 2 2 5 2 1 3 3 6 1 7 0 9 2 7 7 1 5 1 4 1 2 3 2 3 1 1 2 3 1 6 0 6 8 1 4 3 5 3 1 1 2 1 1 1 1 9 0 11 5 1 2 1 9 1 1 6 8 2 1 3 1 3 2 3 1 1 3 2 1 5 5 2 3 5 1 5 2 2 6 5 4 3 6 1 9 0 7 4 4 4 5 7 1 8 1 1 2 1 3 2 1 1 1 1 8 0 6 1 5 9 9 2 6 3 2 1 2 1 1 1 1 1 9 0 9 5 8 1 5 3 9 1 6 7 1 3 1 2 1 1 1 1 2 4 2 2 1 3 4 3 4 1 7 0 8 9 3 8 1 2 1 7 9 3 1 3 3 3 1 1 1 7 0 8 4 1 8 3 3 3 7 9 1 1 3 3 2 1 2 1 9 0 11 3 6 1 2 8 2 6 9 5 5 2 1 3 3 3 2 3 3 3 3 3 1 2 5 3 6 1 9 0 6 1 6 5 7 3 5 1 2 3 1 3 1 2 1 1 1 7 0 7 8 5 1 4 5 8 7 3 1 3 2 3 3 2 1 9 0 10 9 2 5 4 2 1 9 6 1 2 1 2 1 1 1 1 1 1 3 5 5 4 1 2 2 3 7 1 5 0 10 3 9 4 1 9 2 9 2 9 1 3 2 1 1 1 1 5 0 10 6 4 8 5 1 4 5 6 7 1 2 3 1 1 3 1 5 0 11 7 6 1 8 9 8 5 8 8 4 6 2 3 1 2 1 3 1 1 2 2 1 2 3 6 1 8 0 10 3 2 4 1 1 8 7 9 5 9 3 2 2 1 3 2 3 1 1 9 0 10 6 8 1 4 2 1 7 4 1 1 1 3 2 1 2 3 1 3 1 1 8 0 7 5 9 6 8 8 2 1 1 1 1 1 2 1 2 3 4 1 4 5 2 4 3 7 5 3 4 3 5 7 3 5 4 3 7 1 5 0 9 6 1 1 2 6 5 5 9 9 3 1 3 2 1 1 8 0 10 5 1 3 1 1 3 8 4 4 4 3 2 2 1 3 1 1 2 1 7 0 9 5 9 2 4 2 1 4 6 9 2 3 1 1 3 3 1 2 1 1 1 1 5 2 3 5 1 9 0 7 2 4 1 7 5 8 4 3 1 2 3 3 3 1 3 3 1 9 0 7 6 7 2 1 4 5 9 1 2 1 3 2 3 1 1 1 1 7 0 10 4 7 1 4 7 5 6 7 5 6 1 2 3 2 2 1 2 5 3 1 2 1 3 6 1 7 0 11 6 9 6 6 7 6 4 4 4 1 4 3 1 2 1 1 1 1 1 7 0 10 6 1 1 4 8 5 8 8 8 8 3 1 1 3 3 1 3 1 7 0 8 7 3 1 1 8 2 2 1 3 3 3 3 2 1 1 2 3 2 3 5 3 3 5 1 9 0 9 7 8 1 9 8 2 5 5 3 1 2 2 2 1 2 1 1 2 1 8 0 8 7 2 1 2 9 7 1 4 2 3 2 3 1 2 2 3 1 8 0 9 1 1 3 5 5 5 2 9 1 3 1 1 3 3 1 2 1 3 1 5 5 4 3 5 1 7 0 8 4 1 3 4 3 9 2 1 2 1 3 3 1 3 1 1 5 0 6 7 7 1 7 2 1 1 3 3 1 3 1 8 0 6 3 3 1 1 9 2 3 3 1 3 3 1 1 1 3 3 3 5 4 5 4 2 7 5 4 3 7 1 9 0 8 5 1 5 5 5 7 1 7 3 2 1 3 1 3 3 3 3 1 9 0 9 2 1 5 4 2 8 4 9 1 1 1 2 1 2 2 1 2 3 1 5 0 6 1 6 4 8 1 2 2 3 1 3 1 5 1 4 5 3 2 5 3 5 1 6 0 7 3 5 8 5 1 7 7 2 3 1 2 1 1 1 8 0 7 8 2 5 3 4 1 2 2 3 1 1 3 1 3 3 1 6 0 9 1 2 4 9 3 2 5 3 1 2 3 2 1 3 1 1 3 5 4 2 3 7 1 9 0 10 8 4 1 2 8 9 4 3 3 2 3 3 3 1 1 3 2 3 3 1 8 0 6 2 1 7 1 1 3 3 1 2 1 1 2 1 1 1 7 0 9 4 1 2 1 6 9 2 1 2 3 1 2 1 1 1 3 3 2 2 5 2 1 5 3 4 1 5 0 6 5 5 7 9 1 9 1 1 1 1 3 1 6 0 11 2 5 5 6 9 2 8 4 8 1 8 1 1 2 2 3 3 1 9 0 8 4 1 8 6 6 9 3 9 1 3 2 2 3 3 1 2 2 1 2 3 3 3 6 1 8 0 9 5 1 6 7 2 9 1 3 6 2 3 2 3 3 3 1 1 1 9 0 10 2 1 1 9 4 3 3 1 3 5 1 1 2 1 1 2 2 2 1 1 8 0 6 4 1 2 9 3 6 2 1 1 3 3 3 1 3 1 4 5 5 1 1 4 5 1 6 5 3 3 7 1 5 0 10 7 9 5 1 1 6 2 7 8 3 1 3 1 1 1 1 7 0 11 4 7 6 9 2 1 1 5 8 4 2 2 1 1 1 3 3 1 1 6 0 6 5 3 7 8 2 1 1 2 1 1 1 3 3 4 2 3 2 2 3 3 5 1 5 0 9 5 2 6 3 8 2 9 1 3 1 2 3 3 1 1 6 0 10 5 9 3 1 9 8 3 1 1 2 1 1 1 2 3 1 1 7 0 8 7 7 3 3 5 9 8 1 1 1 2 1 1 3 1 3 2 1 5 1 3 6 1 5 0 7 8 7 3 3 9 3 1 1 1 1 2 1 1 7 0 11 7 1 2 6 2 3 5 9 1 3 8 2 3 3 1 2 1 2 1 5 0 9 2 3 1 1 4 4 7 1 2 1 2 3 2 1 4 2 5 4 3 3 3 6 1 9 0 6 6 3 7 7 1 7 2 1 1 1 2 1 2 1 3 1 6 0 9 8 3 3 5 1 2 2 5 4 1 3 2 3 2 3 1 7 0 9 1 3 8 1 9 2 3 8 8 1 1 1 1 1 1 1 3 2 3 5 4 3 3 6 1 9 0 11 1 2 3 1 9 1 6 8 5 4 9 1 3 1 2 1 2 1 2 1 1 9 0 9 6 9 1 9 9 9 8 5 2 3 3 3 2 2 1 3 1 3 1 9 0 9 1 3 9 9 6 4 5 5 1 2 1 2 1 2 2 2 3 3 1 1 5 1 1 1 5 6 3 5 5 3 5 1 6 0 10 8 6 6 4 9 1 1 5 1 2 2 1 2 3 2 1 1 7 0 7 1 4 1 5 4 7 2 2 1 3 3 1 1 3 1 7 0 9 9 6 9 9 1 8 3 5 9 1 3 1 2 3 3 2 4 1 1 1 2 3 6 1 6 0 10 3 5 9 1 1 7 4 4 6 5 2 3 1 1 1 2 1 7 0 6 1 8 2 7 4 7 2 3 2 1 1 3 1 1 6 0 7 1 2 4 5 3 7 2 1 1 2 3 2 2 2 2 2 4 3 3 3 7 1 7 0 9 1 4 4 8 1 9 2 8 2 1 2 3 2 2 1 1 1 8 0 9 1 5 8 8 2 4 2 5 1 3 1 3 3 1 1 3 1 1 6 0 11 1 1 8 6 7 5 2 1 9 6 3 3 2 2 2 2 1 1 5 4 3 2 4 5 3 4 1 8 0 10 3 7 8 9 1 1 9 7 2 8 1 1 2 2 3 1 2 1 1 6 0 10 5 3 7 4 4 3 9 6 7 1 3 2 1 2 1 3 1 5 0 9 6 3 1 1 8 8 3 3 2 2 2 1 2 1 2 3 2 2 3 7 1 8 0 6 9 3 1 8 2 4 1 3 2 3 3 1 1 1 1 6 0 10 4 4 3 2 8 1 6 5 8 9 1 2 1 3 1 1 1 5 0 10 6 3 3 3 6 1 1 5 6 7 1 2 2 1 3 1 3 3 3 3 3 2 6 2 2 3 6 5 3 3 4 1 7 0 10 4 1 9 3 6 2 6 3 5 8 3 1 1 1 1 1 2 1 9 0 10 7 2 1 9 3 2 8 7 3 1 1 1 2 2 2 3 3 2 1 1 8 0 6 3 9 3 1 2 1 3 1 1 2 1 2 2 2 3 5 3 2 3 4 1 8 0 9 4 3 6 8 5 8 1 7 8 3 1 3 1 3 1 3 3 1 5 0 11 2 5 5 3 8 8 3 2 1 4 8 1 1 2 2 2 1 7 0 11 1 9 1 5 3 6 2 7 3 7 8 1 3 1 2 1 1 3 3 3 1 4 3 7 1 7 0 7 6 9 2 9 1 8 1 1 3 2 2 1 2 2 1 6 0 10 1 9 8 5 5 7 2 7 8 3 1 3 1 1 1 2 1 5 0 9 1 4 2 3 1 7 6 1 5 2 1 1 1 1 5 3 1 1 4 3 1 3 4 1 5 0 6 8 6 1 9 3 9 3 3 1 2 2 1 9 0 8 3 4 1 6 1 1 8 5 2 2 1 2 1 1 3 2 1 1 9 0 7 1 3 4 5 9 4 6 2 2 1 1 1 1 1 1 1 1 2 5 5 3 4 1 7 0 9 9 5 1 7 1 3 7 8 5 3 1 1 3 1 3 2 1 8 0 11 1 3 5 6 3 7 7 3 6 4 8 2 2 3 1 1 1 2 3 1 9 0 9 4 1 2 4 8 8 6 1 3 2 3 1 1 2 2 3 3 2 2 4 4 1 7 2 5 4 5 3 5 1 9 0 7 1 9 6 6 3 4 4 2 3 3 1 1 1 1 1 3 1 5 0 8 3 1 7 4 4 1 8 1 1 2 3 1 3 1 9 0 8 6 7 9 2 1 5 1 6 2 3 2 3 3 1 1 1 1 3 1 2 4 1 3 7 1 6 0 7 3 1 5 6 2 9 2 2 2 2 1 1 3 1 6 0 9 1 7 6 8 6 4 1 5 8 1 2 2 1 1 3 1 5 0 10 9 6 3 8 6 7 8 6 8 1 2 1 1 2 2 5 4 3 3 2 2 1 3 7 1 6 0 6 6 9 1 6 1 5 1 2 2 2 3 2 1 6 0 7 1 4 2 8 5 3 4 3 3 1 1 1 3 1 5 0 9 1 9 1 7 6 9 9 2 8 1 3 1 1 1 3 1 1 4 3 3 4 3 6 1 9 0 9 2 8 7 6 1 6 9 9 2 3 3 3 1 3 1 1 1 1 1 7 0 9 5 1 8 6 5 8 4 6 6 3 2 2 2 2 1 3 1 6 0 6 7 2 1 5 3 4 3 3 1 2 2 1 2 2 4 4 2 4 6 2 2 1 3 5 4 3 6 1 8 0 11 9 1 9 3 1 9 1 1 5 2 7 3 3 3 2 3 1 2 1 1 9 0 10 4 1 9 2 5 5 1 9 1 9 3 1 2 2 1 1 3 2 3 1 9 0 11 8 2 3 2 6 5 6 2 1 6 4 3 1 3 3 3 2 3 3 2 1 3 2 3 4 3 3 5 1 7 0 11 5 8 4 7 9 7 5 1 3 2 7 1 1 3 1 2 3 1 1 8 0 10 1 3 6 1 9 1 5 1 1 4 3 1 1 1 3 1 1 3 1 5 0 9 1 3 4 9 1 6 6 2 9 1 1 1 2 1 2 1 1 3 4 3 5 1 8 0 10 8 6 7 1 5 4 9 7 9 2 1 2 1 2 1 2 1 3 1 5 0 6 2 9 3 1 1 9 1 1 1 1 3 1 5 0 9 5 9 9 3 1 6 4 8 1 2 1 1 1 3 5 4 2 2 1 3 7 1 7 0 10 3 1 6 3 2 9 1 6 1 7 1 1 3 1 1 2 1 1 6 0 9 5 1 3 2 9 1 4 2 2 3 2 3 1 1 2 1 5 0 11 2 7 9 2 3 1 3 7 1 4 2 3 1 1 2 3 1 1 5 1 4 3 4 3 7 1 8 0 6 8 9 9 2 1 1 3 2 2 3 1 3 1 3 1 6 0 6 1 1 3 3 9 2 1 1 1 2 2 3 1 5 0 8 1 9 2 4 3 6 9 6 1 2 2 1 3 3 3 1 1 2 2 2 7 3 4 2 2 3 1 7 2 5 5 3 7 1 8 0 6 1 7 7 8 7 5 2 2 1 2 1 2 1 1 1 6 0 9 5 4 4 1 1 3 5 3 3 3 2 1 2 1 2 1 7 0 7 7 6 5 1 1 2 5 3 2 1 2 3 2 1 1 3 2 3 2 4 2 3 4 1 8 0 10 1 1 7 3 5 6 2 2 9 5 2 2 3 1 1 2 3 3 1 6 0 11 3 5 6 5 8 1 1 2 3 7 4 1 2 1 1 1 2 1 5 0 8 1 4 1 7 8 7 7 9 1 3 3 1 2 1 4 3 2 3 6 1 6 0 7 8 8 3 6 2 6 1 1 1 1 2 1 1 1 5 0 9 7 1 5 1 9 1 7 9 6 2 2 1 2 1 1 9 0 6 2 8 6 8 6 1 2 3 2 2 3 2 2 1 3 1 2 1 3 4 4 3 7 1 8 0 7 2 1 3 3 4 5 3 2 1 2 3 3 1 3 1 1 6 0 8 1 6 4 1 6 3 2 1 3 1 1 2 1 1 1 8 0 11 7 3 1 2 2 4 1 2 2 4 3 2 3 2 2 3 1 1 1 1 5 5 2 1 3 1 3 5 1 5 0 9 7 5 6 7 5 1 6 1 5 1 2 2 1 1 1 6 0 6 3 2 8 1 3 3 1 1 1 1 3 2 1 7 0 7 1 1 5 4 8 2 5 3 1 1 3 3 3 3 5 2 5 4 1 2 1 2 4 2 4 3 3 6 1 9 0 11 2 3 8 2 1 7 5 1 7 7 3 1 1 3 2 1 1 1 2 1 1 9 0 10 2 6 9 3 9 4 2 5 1 2 2 2 2 2 2 1 2 2 2 1 5 0 8 4 4 8 1 7 1 8 3 2 2 1 2 3 5 2 4 3 1 3 3 5 1 6 0 9 6 4 2 1 9 2 4 6 2 2 1 1 1 1 2 1 9 0 10 6 2 7 4 4 2 6 4 7 1 1 2 1 1 3 1 3 3 1 1 8 0 9 1 3 7 1 1 6 3 7 1 3 1 3 1 2 1 3 3 4 4 2 1 1 3 7 1 9 0 6 9 5 1 7 1 5 1 2 1 3 2 1 3 3 3 1 5 0 7 1 1 5 3 6 6 5 3 1 1 1 2 1 7 0 7 3 5 9 2 1 2 4 1 1 1 2 1 2 2 2 5 1 1 3 3 3 3 7 1 7 0 8 4 6 2 4 7 5 6 1 3 3 1 2 3 2 1 1 8 0 7 4 6 5 2 1 1 8 1 3 3 1 1 2 2 2 1 9 0 9 4 7 3 3 1 1 9 7 7 1 1 1 2 1 1 2 3 2 4 5 3 2 4 2 2 6 6 3 4 4 3 7 1 6 0 8 1 4 3 3 6 5 3 9 1 1 2 1 3 3 1 9 0 7 4 4 9 1 3 1 2 1 1 3 1 1 2 1 2 1 1 6 0 9 8 9 7 1 4 9 6 9 3 2 2 1 2 2 2 1 2 4 4 2 3 4 3 6 1 6 0 8 6 6 6 5 2 4 1 9 3 1 1 1 2 3 1 6 0 8 3 1 5 1 5 4 5 5 1 1 3 3 3 3 1 6 0 8 2 1 3 6 1 1 1 3 1 1 2 1 2 2 2 3 1 5 5 1 3 7 1 6 0 11 4 2 1 7 5 6 3 5 9 1 8 2 3 1 3 2 1 1 6 0 10 7 2 1 6 6 9 6 1 7 9 3 1 2 2 1 2 1 8 0 10 1 1 1 9 7 2 3 6 2 8 1 2 3 2 2 1 1 2 3 5 1 2 3 2 5 3 6 1 5 0 9 5 6 7 7 9 9 4 3 1 2 3 1 1 1 1 9 0 7 4 6 8 7 4 1 9 1 1 1 3 2 1 3 1 1 1 5 0 8 6 9 4 1 2 3 5 1 1 2 1 3 1 4 2 3 3 3 4 4 3 3 4 5 4 3 6 1 6 0 10 7 5 5 3 5 7 4 9 2 1 1 1 3 2 1 1 1 7 0 8 7 8 2 1 9 7 1 6 1 1 1 2 3 3 3 1 9 0 6 7 1 7 1 6 1 1 3 2 2 2 3 3 1 2 2 1 4 1 3 5 3 7 1 6 0 7 2 8 8 6 1 4 6 2 3 3 1 1 1 1 8 0 10 3 9 1 5 2 6 8 4 1 8 3 1 3 3 2 2 3 1 1 7 0 9 7 2 6 1 8 7 2 9 1 1 3 1 1 3 1 1 3 1 5 5 1 3 3 3 5 1 6 0 10 2 2 9 6 7 6 4 4 1 4 1 3 1 3 1 1 1 9 0 9 6 8 9 1 1 7 9 1 6 1 3 3 2 3 2 2 1 3 1 7 0 10 5 1 8 8 9 7 6 1 1 1 2 3 1 1 2 2 2 2 3 5 1 3 3 6 1 8 0 11 2 5 6 3 2 8 5 1 1 1 9 3 2 2 3 2 1 1 3 1 9 0 11 6 1 7 6 5 7 6 1 1 4 8 3 1 2 1 2 2 3 3 3 1 6 0 10 8 8 8 3 9 6 1 1 8 3 2 1 1 1 1 3 3 2 3 4 3 5 3 4 1 9 0 9 4 6 1 7 8 7 7 5 6 3 3 2 3 1 2 1 2 3 1 8 0 9 6 7 3 1 7 1 2 2 1 2 2 1 1 1 3 2 1 1 5 0 10 1 4 5 3 3 7 5 1 1 1 3 1 1 1 1 4 1 1 5 3 7 6 1 5 5 3 6 1 7 0 11 8 1 1 8 7 7 4 6 2 1 3 1 3 3 3 3 3 1 1 6 0 11 1 1 3 6 1 9 5 6 7 1 3 3 1 1 1 1 2 1 5 0 6 5 8 9 9 3 1 2 1 1 3 1 4 3 3 5 3 4 3 7 1 8 0 7 5 4 8 9 1 8 4 1 1 1 1 3 3 3 2 1 5 0 6 6 2 2 5 8 1 1 1 3 2 2 1 6 0 8 4 4 1 6 4 1 8 6 2 1 2 1 2 3 4 3 2 3 5 4 2 3 4 1 8 0 9 9 5 2 1 4 6 5 3 4 1 2 1 1 3 2 3 2 1 7 0 11 3 7 1 2 2 6 1 8 8 2 2 3 3 3 1 3 2 1 1 7 0 8 1 3 4 1 8 5 1 7 2 3 2 3 1 3 3 3 2 3 3 3 7 1 5 0 8 2 8 7 3 8 1 3 6 1 1 2 1 3 1 9 0 6 1 1 9 5 1 7 2 1 1 1 2 2 2 1 1 1 7 0 9 1 1 4 2 3 2 4 8 7 2 1 3 3 2 1 2 2 3 4 5 5 2 1 3 7 1 5 0 7 4 5 8 8 1 8 4 1 1 1 2 1 1 5 0 9 7 7 1 9 4 6 8 7 1 1 1 1 3 2 1 8 0 6 1 9 5 7 2 4 2 1 3 2 3 2 1 3 4 3 4 5 5 3 1 6 5 7 7 1 5 3 3 4 1 9 0 6 1 9 7 4 1 9 3 1 3 2 3 3 1 1 3 1 5 0 11 4 1 6 9 1 8 2 4 2 8 7 1 2 1 1 3 1 7 0 7 8 1 6 8 6 9 3 3 3 3 2 2 1 1 1 2 5 4 3 6 1 5 0 6 8 9 6 9 1 7 1 2 1 3 1 1 5 0 7 2 1 7 7 5 3 4 3 1 1 2 1 1 8 0 8 8 1 2 4 6 6 1 4 3 3 1 3 3 2 1 1 3 3 4 1 1 5 3 5 1 9 0 11 9 9 5 1 1 7 5 5 1 4 9 3 3 3 2 3 3 1 1 3 1 5 0 6 7 5 1 5 2 3 1 3 1 3 2 1 8 0 7 1 2 9 9 1 8 1 3 2 1 1 2 2 1 3 2 2 4 2 3 3 4 1 5 0 7 6 2 2 1 5 4 1 1 1 2 2 1 1 5 0 11 1 8 9 4 9 2 1 2 3 8 9 1 1 3 3 2 1 5 0 9 9 5 3 7 1 3 7 3 1 1 2 3 3 1 5 5 3 4 3 7 1 9 0 7 8 7 2 5 1 4 3 1 2 1 3 2 1 2 3 3 1 8 0 7 4 4 9 4 7 1 9 3 2 3 2 1 1 2 1 1 5 0 11 9 1 7 2 6 1 7 3 9 1 2 1 2 1 2 1 3 3 1 2 3 3 3 2 2 6 5 4 3 4 1 9 0 7 3 5 9 7 7 1 3 2 3 2 1 1 3 2 1 1 1 9 0 9 1 6 4 3 6 9 1 8 4 1 2 1 3 3 1 2 2 2 1 7 0 10 1 2 1 6 7 4 3 6 6 5 2 2 1 1 2 1 2 2 4 1 5 3 5 1 8 0 6 9 1 8 3 3 1 1 1 3 1 1 2 2 3 1 5 0 6 1 4 1 1 9 2 2 2 3 1 1 1 7 0 8 6 6 4 5 5 8 1 7 2 3 1 2 1 1 1 2 1 2 3 5 3 7 1 8 0 11 1 5 5 7 1 2 5 1 7 2 2 2 3 1 3 2 1 3 2 1 7 0 11 5 1 6 8 3 5 2 7 9 7 9 1 3 2 1 1 2 3 1 9 0 10 5 7 9 1 6 4 8 2 3 5 1 1 3 3 1 2 3 1 2 1 5 5 1 5 1 2 3 6 1 6 0 7 3 1 2 5 8 9 3 1 1 3 2 3 2 1 6 0 11 9 7 1 6 9 7 2 3 1 8 8 2 1 3 2 3 1 1 8 0 7 6 1 2 1 1 3 8 3 1 1 1 2 2 1 2 2 4 4 2 1 2 3 5 1 9 0 6 6 9 5 4 1 2 3 3 1 2 1 2 1 1 2 1 7 0 11 8 7 2 2 4 1 6 1 1 8 2 1 2 2 3 1 1 1 1 5 0 10 1 9 3 7 3 1 9 6 9 8 2 1 2 1 1 3 1 5 1 3 6 6 2 4 9 3 7 3 5 3 3 4 1 6 0 9 3 2 5 2 6 3 1 4 8 1 3 2 1 3 2 1 9 0 6 6 7 8 3 1 8 3 1 3 3 2 2 1 1 1 1 9 0 7 1 2 9 4 6 1 9 2 1 2 2 1 3 2 1 3 3 2 3 2 3 6 1 8 0 10 6 6 5 4 8 1 5 8 9 5 2 1 2 1 2 2 3 2 1 9 0 7 2 7 4 7 1 8 8 1 2 1 1 2 2 1 3 1 1 8 0 8 9 4 4 1 3 1 8 1 3 3 2 2 1 1 1 2 1 2 4 2 1 1 3 6 1 6 0 11 4 7 1 1 2 9 8 4 4 8 2 2 2 3 1 3 1 1 7 0 8 4 9 1 5 3 6 8 9 1 3 1 1 3 1 3 1 8 0 11 5 6 1 7 2 3 5 1 2 5 7 1 1 3 2 1 2 3 3 3 3 3 5 2 1 3 4 1 9 0 9 2 6 8 1 3 2 8 6 2 2 2 2 1 1 1 1 3 1 1 7 0 6 8 1 4 6 3 6 1 1 2 2 1 3 2 1 5 0 9 7 5 3 4 1 9 9 9 9 2 1 2 3 1 1 1 4 2 3 7 1 7 0 10 5 5 1 5 8 5 5 9 7 5 2 1 1 3 2 3 3 1 8 0 9 8 1 1 4 8 5 3 8 3 2 2 3 2 2 1 2 3 1 7 0 9 1 1 2 6 1 4 1 9 3 1 1 3 2 3 3 2 3 5 5 2 1 2 2 4 4 3 4 4 3 5 1 8 0 11 4 1 3 7 7 1 9 3 8 5 9 2 3 1 3 1 1 2 3 1 7 0 7 4 3 2 8 4 5 1 2 1 1 1 1 1 3 1 8 0 9 1 6 5 8 8 6 6 7 4 1 3 3 1 3 1 1 1 5 4 1 5 2 3 7 1 9 0 11 9 7 5 1 2 8 2 2 1 4 7 3 1 2 3 3 3 2 1 1 1 8 0 10 8 1 9 5 5 8 6 1 3 2 1 1 2 2 3 3 2 3 1 5 0 6 1 1 7 4 8 7 2 3 1 3 1 2 2 5 1 5 3 3 3 4 1 7 0 8 7 4 5 9 1 4 7 5 3 3 1 3 2 3 1 1 9 0 7 8 6 8 5 9 1 2 1 2 2 2 2 1 1 1 1 1 8 0 11 4 1 5 6 4 3 7 1 6 6 8 2 1 1 2 1 1 3 3 4 3 2 3 3 6 1 6 0 8 6 2 5 4 1 5 7 5 1 2 3 3 3 3 1 5 0 9 1 3 9 1 8 1 7 5 6 2 3 1 2 1 1 8 0 7 3 7 1 4 1 2 7 1 1 2 3 1 2 1 3 4 1 5 5 1 1 3 1 2 2 5 3 3 5 1 6 0 8 8 1 1 1 6 6 5 7 1 2 2 1 3 1 1 8 0 6 3 4 4 1 9 9 2 1 2 1 1 3 3 2 1 7 0 6 2 6 7 1 7 3 1 2 1 1 1 1 2 2 5 4 5 1 3 7 1 5 0 10 4 3 4 9 1 8 5 1 4 4 2 1 2 2 1 1 7 0 8 8 6 1 9 1 8 3 6 3 2 3 2 2 1 2 1 5 0 6 6 1 1 1 6 8 1 3 2 1 2 4 4 3 3 3 1 1 3 5 1 9 0 10 8 4 2 8 1 1 6 1 8 1 1 2 1 1 1 1 2 2 2 1 5 0 10 3 1 1 6 8 7 4 6 3 2 3 1 1 3 3 1 6 0 7 3 2 1 3 1 4 1 3 1 1 2 2 3 1 1 3 1 2 3 7 1 8 0 8 2 4 5 6 8 1 3 7 1 2 1 1 1 1 2 1 1 8 0 11 1 5 1 2 6 3 8 4 1 8 9 2 2 2 1 1 3 3 3 1 6 0 9 7 5 6 8 2 1 2 5 5 2 1 2 3 3 1 1 4 1 5 4 1 2 3 6 1 9 0 7 7 7 3 3 1 4 2 3 1 3 1 1 3 2 3 3 1 9 0 11 7 8 3 3 4 1 8 5 7 1 9 1 3 1 1 1 2 3 1 1 1 5 0 7 7 9 4 5 7 1 8 2 2 2 1 3 1 3 2 2 5 4 2 2 7 5 5 3 5 1 8 0 9 9 7 1 6 1 9 2 5 7 1 1 3 2 1 1 3 2 1 8 0 9 1 1 1 3 6 1 7 4 7 2 3 2 3 2 1 1 1 1 8 0 9 7 3 1 3 3 9 5 9 2 1 2 3 3 2 2 3 1 2 3 1 1 5 3 5 1 7 0 11 1 7 1 9 9 7 4 5 4 1 2 2 2 3 1 1 1 3 1 8 0 8 9 2 1 1 6 6 6 4 2 3 1 2 1 3 2 2 1 8 0 10 8 6 1 6 4 1 7 5 7 9 3 3 2 3 2 1 3 1 5 2 5 2 4 3 7 1 5 0 10 6 5 7 7 9 1 8 1 2 7 2 1 1 1 1 1 6 0 8 4 1 7 9 1 1 1 1 2 1 3 3 2 3 1 6 0 8 4 8 4 7 1 1 3 7 1 1 1 1 1 3 1 3 2 2 3 2 2 3 4 1 9 0 10 9 1 6 9 5 7 4 5 2 3 2 1 1 2 3 1 3 2 3 1 6 0 8 3 4 6 2 9 1 5 9 3 1 3 1 1 1 1 5 0 11 1 1 7 4 3 3 2 1 7 5 3 2 1 1 3 2 2 4 3 4 3 5 1 9 0 11 7 2 5 4 9 3 1 5 6 9 6 3 1 2 2 1 1 2 1 1 1 7 0 9 2 7 4 8 6 2 2 1 2 3 2 1 1 1 2 1 1 8 0 8 6 6 5 1 3 6 8 7 1 1 2 1 3 3 1 2 5 2 3 3 2 5 5 4 2 4 4 3 3 5 1 9 0 8 9 5 4 8 3 3 3 1 1 1 1 3 2 3 3 2 1 1 8 0 11 9 9 2 9 2 9 1 5 4 7 9 3 3 1 2 2 1 3 1 1 6 0 6 4 7 3 2 3 1 1 1 2 3 3 1 3 1 1 5 2 3 7 1 7 0 8 1 7 7 3 8 6 5 6 3 1 2 3 3 3 2 1 8 0 7 7 1 7 8 1 1 6 1 1 2 1 1 1 3 1 1 6 0 9 3 7 5 1 8 3 1 2 3 2 1 2 1 1 2 2 5 4 2 3 4 3 3 5 1 7 0 9 4 6 3 1 5 4 6 4 5 1 2 1 3 2 1 2 1 7 0 7 2 1 1 2 3 3 9 1 1 3 1 3 3 1 1 5 0 7 2 4 6 9 1 2 3 2 2 1 3 1 1 1 5 5 1 3 6 1 6 0 6 9 8 1 2 2 8 1 3 3 1 1 3 1 8 0 9 8 8 8 1 8 7 8 1 1 1 3 2 1 1 3 1 3 1 7 0 10 1 7 9 9 8 9 3 5 5 5 2 1 1 1 2 3 3 2 1 2 3 1 4 4 4 1 5 5 3 4 1 5 0 11 2 4 1 7 1 2 7 4 4 8 8 1 3 1 1 3 1 5 0 10 4 7 1 7 4 3 8 5 7 7 1 1 3 1 2 1 6 0 11 7 3 1 6 6 3 7 1 8 2 5 3 2 1 1 3 3 3 2 1 5 3 7 1 6 0 6 9 7 1 2 3 5 1 1 3 2 3 1 1 9 0 8 5 8 1 7 3 1 6 4 3 1 3 1 1 2 2 1 3 1 8 0 7 1 3 4 5 9 6 7 1 1 2 3 1 2 2 2 5 1 3 5 4 4 5 3 7 1 7 0 6 3 5 1 1 6 1 2 1 3 3 1 3 1 1 5 0 7 7 9 1 4 6 2 4 3 1 1 1 1 1 7 0 8 9 1 9 5 4 3 7 6 1 3 1 2 3 2 3 1 4 2 1 5 5 4 3 5 1 9 0 9 7 4 2 5 3 7 2 6 1 3 1 3 1 3 3 2 3 3 1 5 0 8 8 4 5 5 8 2 1 3 3 1 3 1 2 1 6 0 7 9 4 9 7 1 1 7 2 3 1 1 2 1 4 4 2 1 5 3 5 1 7 0 7 6 1 8 4 2 9 3 2 1 2 1 1 2 3 1 6 0 11 5 7 3 1 2 6 4 4 3 2 5 1 2 2 3 2 3 1 9 0 6 2 1 6 1 5 6 2 3 3 2 3 1 1 1 1 1 4 3 4 5 6 4 7 5 5 5 5 3 6 1 5 0 11 2 4 9 2 1 8 3 5 1 4 8 1 2 3 1 1 1 8 0 10 2 3 3 7 6 4 4 1 4 4 3 3 2 2 1 1 1 3 1 9 0 8 1 5 7 2 3 1 6 2 1 2 2 1 3 1 2 1 3 3 1 5 2 2 4 3 7 1 5 0 7 4 5 6 3 2 6 1 1 1 2 2 2 1 6 0 7 9 8 1 1 9 2 4 1 2 1 1 3 1 1 5 0 8 7 6 3 4 1 4 8 6 3 1 1 1 2 2 4 4 3 1 3 5 3 4 1 7 0 9 1 1 3 3 2 9 1 4 4 2 1 2 3 3 1 1 1 9 0 10 9 2 2 1 2 2 5 9 5 1 1 1 1 3 2 3 3 1 1 1 6 0 6 1 6 7 3 4 5 1 1 3 1 1 2 1 1 5 1 3 7 1 9 0 6 7 7 8 1 4 6 1 2 1 1 1 2 2 3 1 1 9 0 10 8 2 4 8 6 2 9 8 1 4 3 3 3 1 1 1 1 1 2 1 5 0 9 2 3 2 5 1 5 8 3 4 3 1 2 3 3 2 4 3 5 4 4 2 3 5 1 6 0 8 1 5 2 3 2 6 1 8 1 3 3 2 1 1 1 9 0 9 1 2 6 9 6 7 8 4 4 1 3 2 1 1 2 2 3 2 1 8 0 11 8 6 8 8 1 2 9 6 1 7 5 1 1 3 1 2 3 3 2 5 3 3 1 1 4 1 2 2 3 6 6 7 4 8 7 10 3 6 5 6 9 3 7"
    ;"2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"
    (format "[%s]")
    (edn/read-string)))


(set! *print-length* 20)


(defn f1 [[n m & tail :as xs]]
  (loop [total    0
         todo     tail
         levels   (list [n m])]
    (let [[[chil nmeta] & ancestors] levels]
      (cond
        (empty? todo)
        total

        (zero? chil)
        (recur
          (->> todo (take nmeta) (reduce + total))
          (->> todo (drop nmeta))
          (let [[[chil nmeta] & ancestors] ancestors]
            (when chil
              (conj ancestors [(dec chil) nmeta]))))

        :else
        (recur
          total
          (->> todo (drop 2))
          (->> todo (take 2) (conj levels)))))))


(defn get-score [nmeta scores xs]
  (let [metas (take nmeta xs)]
    (if (empty? scores)
      (reduce + 0 metas)
      (reduce + 0 (->> metas
                    (map dec)
                    (map #(get scores % 0)))))))


(defn f2 [[n m & tail :as xs]]
  (loop [todo   tail
         levels (list [n m []])]
    (let [[[chil nmeta scores] & ancestors] levels]
      (cond
        ;; end
        (and (empty? ancestors) (zero? chil))
        (get-score nmeta scores todo)

        ;; up, then right
        (zero? chil)
        (let [node-score (get-score nmeta scores todo)
              todo (drop nmeta todo)
              [[chil nmeta scores] & ancestors] ancestors]
          (recur
            todo
            (conj ancestors [(dec chil) nmeta (conj scores node-score)])))

        ;; down
        :else
        (let [[chil nmeta & todo] todo]
          (recur todo (conj levels [chil nmeta []])))))))





(assert (= (f1 input) 41521))
(assert (= (f2 input) 19990))

