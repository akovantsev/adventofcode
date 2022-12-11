(ns adventofcode.y2022.day09
  (:require [clojure.string :as str]
            [com.akovantsev.blet.core :refer [blet blet!]]
            [adventofcode.utils :as u]
            [clojure.math :as math]))


(def sample "R 4\nU 4\nL 3\nD 1\nR 4\nD 1\nL 5\nR 2")
(def input "D 2\nU 2\nL 2\nR 1\nL 1\nU 1\nL 2\nD 1\nL 2\nD 1\nL 2\nD 2\nL 1\nR 2\nU 1\nR 1\nU 2\nR 1\nU 2\nD 1\nL 2\nU 1\nD 1\nL 1\nD 1\nU 1\nR 2\nD 2\nL 1\nR 1\nL 2\nD 1\nR 2\nD 1\nU 2\nD 2\nR 2\nL 2\nU 2\nR 2\nL 1\nD 1\nR 1\nU 1\nR 2\nL 1\nD 1\nR 1\nL 2\nD 2\nU 1\nL 1\nR 1\nU 1\nL 2\nD 2\nL 2\nU 1\nL 2\nR 1\nL 1\nR 1\nD 1\nR 2\nD 1\nR 1\nD 1\nU 1\nL 1\nR 1\nL 1\nR 1\nD 2\nL 1\nD 2\nR 1\nD 1\nU 2\nR 2\nU 2\nL 1\nU 1\nR 1\nL 2\nD 2\nR 1\nU 1\nL 1\nU 2\nL 1\nD 2\nR 1\nU 1\nR 1\nU 2\nR 1\nU 2\nL 1\nR 1\nU 2\nR 2\nU 1\nL 2\nR 1\nL 2\nU 2\nL 2\nU 2\nR 2\nD 1\nU 2\nL 2\nD 3\nU 1\nL 1\nR 1\nD 3\nU 1\nL 3\nR 1\nU 1\nR 3\nD 2\nU 1\nR 2\nL 2\nU 1\nD 1\nU 1\nD 1\nL 2\nU 2\nR 3\nD 2\nL 2\nD 2\nU 3\nD 3\nU 2\nL 1\nU 2\nR 3\nD 2\nR 2\nD 1\nU 3\nL 3\nD 1\nU 3\nR 3\nD 1\nL 1\nD 3\nU 3\nD 1\nU 2\nR 1\nD 2\nU 3\nD 1\nR 2\nD 3\nL 3\nR 1\nD 3\nL 2\nD 3\nU 3\nR 1\nU 3\nL 2\nU 2\nL 2\nD 3\nL 3\nU 2\nR 2\nL 3\nR 2\nU 3\nR 3\nL 1\nU 3\nD 3\nL 2\nD 2\nU 2\nD 3\nL 3\nD 2\nL 1\nU 3\nD 2\nR 3\nL 3\nR 1\nU 1\nD 1\nR 2\nL 3\nU 2\nD 1\nR 3\nL 1\nR 1\nL 2\nR 1\nU 3\nR 2\nU 1\nL 1\nR 3\nD 3\nU 2\nR 2\nD 1\nR 3\nD 2\nL 3\nR 1\nU 3\nR 2\nU 4\nL 2\nD 1\nU 1\nR 4\nU 2\nR 2\nU 3\nR 1\nL 3\nU 3\nR 3\nU 4\nR 4\nD 1\nU 2\nR 1\nL 1\nR 2\nU 4\nL 1\nU 2\nL 1\nR 3\nL 2\nD 4\nR 4\nD 3\nR 4\nU 1\nD 4\nR 2\nD 1\nR 4\nU 3\nR 4\nL 1\nU 4\nD 2\nL 1\nD 3\nL 1\nR 2\nL 2\nD 4\nU 3\nD 1\nL 3\nD 3\nU 3\nL 4\nR 3\nU 1\nR 2\nD 1\nU 4\nD 4\nR 4\nU 2\nR 4\nL 4\nD 2\nL 4\nD 2\nU 3\nD 2\nU 4\nR 1\nU 1\nL 1\nU 1\nL 1\nD 3\nU 4\nL 3\nU 1\nL 4\nR 4\nL 3\nR 2\nU 2\nL 3\nD 2\nU 1\nD 1\nU 4\nR 2\nL 2\nD 1\nL 4\nD 3\nU 3\nL 2\nU 4\nR 4\nL 2\nR 3\nL 1\nD 4\nU 1\nL 2\nD 4\nL 2\nD 3\nU 4\nR 4\nU 2\nR 2\nU 4\nD 3\nU 3\nL 4\nD 2\nR 1\nU 4\nR 1\nU 1\nD 3\nU 1\nL 5\nU 5\nR 2\nD 3\nU 5\nD 5\nU 1\nD 2\nL 2\nU 5\nL 1\nR 5\nL 1\nD 5\nL 4\nD 4\nR 4\nL 1\nR 1\nU 1\nR 4\nU 2\nR 5\nL 4\nR 3\nU 4\nR 2\nU 4\nL 4\nU 3\nL 3\nU 1\nL 4\nR 1\nU 3\nR 3\nL 5\nU 5\nL 4\nD 1\nL 2\nU 1\nD 2\nL 1\nU 5\nD 4\nU 1\nR 2\nD 2\nU 5\nR 5\nU 1\nL 3\nU 5\nL 4\nR 3\nU 4\nL 2\nR 4\nD 2\nL 2\nR 4\nU 2\nD 4\nR 3\nD 4\nU 1\nL 3\nR 4\nL 3\nU 1\nR 2\nU 3\nL 3\nD 2\nU 4\nR 1\nL 3\nD 4\nL 1\nR 1\nD 5\nR 1\nU 2\nD 1\nU 4\nL 2\nU 2\nR 4\nU 3\nR 3\nD 2\nR 5\nD 2\nU 5\nD 5\nU 2\nD 1\nR 5\nU 3\nL 3\nU 1\nR 5\nL 4\nD 6\nL 1\nU 2\nD 4\nU 6\nD 6\nR 1\nU 3\nL 5\nR 5\nU 2\nR 2\nL 5\nD 4\nL 3\nR 1\nU 4\nD 3\nL 2\nD 5\nL 3\nR 3\nD 2\nL 1\nU 5\nL 5\nD 6\nU 3\nR 5\nL 5\nU 2\nL 3\nU 5\nD 6\nR 4\nL 5\nU 6\nL 2\nR 1\nL 1\nU 5\nD 3\nU 2\nR 1\nU 2\nR 2\nD 5\nR 1\nD 5\nR 5\nU 2\nL 3\nR 1\nU 5\nD 5\nR 2\nL 5\nD 6\nL 1\nU 1\nD 6\nU 4\nD 5\nL 5\nD 3\nL 4\nD 1\nL 4\nD 6\nR 5\nD 6\nU 6\nD 2\nL 3\nD 3\nR 5\nU 6\nD 2\nU 4\nL 1\nU 6\nL 2\nU 4\nD 6\nR 1\nD 1\nU 2\nL 3\nD 3\nR 2\nL 6\nU 3\nD 5\nU 3\nL 4\nU 5\nL 5\nU 6\nL 3\nR 1\nU 3\nR 1\nU 4\nL 4\nD 5\nL 6\nR 1\nL 3\nD 4\nL 3\nR 1\nL 4\nR 3\nD 3\nL 1\nU 4\nD 2\nU 5\nL 3\nD 6\nU 2\nR 3\nD 5\nL 1\nD 1\nL 3\nU 2\nR 3\nL 3\nU 7\nR 2\nD 1\nL 3\nR 4\nD 2\nR 5\nL 1\nD 5\nL 2\nR 2\nU 7\nL 7\nD 2\nL 7\nD 5\nU 2\nL 2\nU 2\nD 4\nU 1\nD 4\nL 1\nD 2\nR 5\nD 3\nL 2\nU 4\nR 7\nD 4\nR 3\nU 2\nR 4\nL 1\nU 4\nL 4\nU 7\nR 1\nL 6\nD 5\nR 5\nL 1\nR 4\nL 1\nU 3\nL 7\nR 7\nD 4\nL 3\nR 3\nD 1\nU 7\nR 3\nD 2\nU 4\nD 2\nR 3\nD 1\nR 3\nD 3\nU 1\nR 6\nL 2\nD 2\nU 4\nD 5\nL 7\nR 1\nL 7\nU 6\nD 2\nR 3\nD 5\nU 1\nL 7\nD 6\nU 3\nD 6\nL 1\nR 2\nL 6\nD 7\nR 5\nU 1\nL 7\nU 2\nD 2\nL 5\nR 7\nD 4\nU 3\nR 7\nL 4\nU 8\nD 8\nR 2\nU 2\nR 7\nU 1\nD 7\nR 1\nL 7\nR 7\nL 8\nD 3\nR 5\nL 4\nD 3\nL 6\nR 6\nD 6\nL 3\nD 5\nR 1\nU 5\nD 7\nR 8\nU 3\nD 5\nU 8\nL 8\nR 6\nD 8\nU 5\nR 1\nD 1\nR 5\nU 2\nR 8\nD 7\nL 3\nD 3\nU 7\nD 3\nU 7\nR 7\nL 4\nD 7\nU 2\nL 3\nU 7\nL 1\nD 3\nL 1\nR 4\nD 8\nL 5\nU 5\nD 3\nL 5\nD 6\nL 1\nR 5\nU 4\nR 4\nD 1\nR 6\nL 1\nD 7\nR 4\nL 2\nR 3\nU 4\nL 8\nD 1\nR 6\nL 6\nR 8\nD 8\nU 4\nR 1\nD 2\nR 5\nD 1\nU 8\nL 5\nD 5\nU 5\nR 8\nL 6\nR 4\nU 1\nR 5\nU 8\nD 1\nU 1\nD 5\nL 7\nR 6\nL 3\nR 1\nU 2\nL 6\nR 5\nD 4\nR 6\nD 4\nR 2\nL 7\nR 8\nL 6\nD 2\nL 8\nU 4\nR 4\nU 3\nD 8\nU 8\nL 8\nD 5\nR 2\nD 5\nU 7\nL 9\nR 8\nU 9\nR 9\nU 6\nL 6\nD 1\nU 3\nL 1\nD 7\nR 6\nL 6\nD 7\nU 1\nR 9\nD 7\nU 8\nR 6\nU 1\nD 5\nU 5\nD 1\nL 1\nD 9\nU 1\nR 6\nU 9\nR 6\nU 6\nD 7\nR 1\nL 1\nD 8\nL 3\nD 7\nU 8\nL 7\nD 2\nU 6\nD 4\nL 8\nU 3\nD 1\nU 6\nD 3\nR 8\nL 9\nR 3\nL 6\nU 9\nD 9\nL 3\nU 1\nD 2\nR 4\nL 5\nR 2\nU 3\nD 5\nR 5\nL 4\nU 3\nL 7\nU 5\nD 9\nU 6\nR 5\nD 3\nU 8\nL 6\nD 5\nR 9\nU 8\nL 5\nD 1\nR 8\nL 4\nR 2\nL 3\nR 9\nD 9\nR 7\nD 5\nR 6\nU 2\nD 4\nL 8\nD 2\nL 1\nD 2\nU 3\nL 2\nR 3\nD 9\nU 3\nR 4\nD 1\nL 8\nR 3\nU 4\nL 4\nR 3\nL 5\nD 4\nR 2\nD 4\nR 4\nU 4\nR 7\nL 6\nR 9\nD 1\nR 8\nL 5\nU 4\nD 10\nL 6\nD 3\nR 2\nU 1\nD 5\nR 2\nU 6\nL 9\nU 7\nL 10\nU 4\nL 8\nD 3\nU 7\nD 7\nU 9\nL 2\nR 6\nU 4\nR 9\nL 5\nU 3\nL 4\nR 1\nD 8\nU 10\nD 10\nR 3\nU 3\nD 2\nL 5\nU 3\nD 4\nU 1\nR 9\nL 3\nD 8\nL 5\nU 8\nR 3\nU 10\nL 10\nR 4\nD 5\nU 9\nR 8\nL 5\nU 9\nL 6\nU 10\nD 8\nU 2\nL 8\nD 7\nU 2\nD 5\nU 8\nL 2\nD 3\nU 8\nR 8\nD 10\nR 3\nL 3\nU 4\nR 3\nU 4\nL 6\nU 4\nR 3\nD 9\nL 9\nU 5\nD 1\nR 3\nD 2\nL 9\nU 9\nR 9\nU 6\nR 7\nL 8\nU 2\nL 4\nD 10\nL 4\nD 9\nL 9\nR 10\nL 3\nR 7\nU 2\nL 3\nR 1\nD 4\nL 2\nR 10\nD 5\nL 2\nD 2\nL 4\nU 6\nD 9\nR 6\nU 9\nD 4\nL 6\nU 8\nD 1\nR 1\nL 10\nU 4\nR 6\nU 11\nR 8\nU 1\nD 5\nR 7\nD 8\nL 5\nR 6\nU 5\nR 1\nU 7\nL 4\nD 6\nU 8\nR 10\nD 1\nL 8\nR 3\nU 7\nR 2\nU 9\nD 9\nR 9\nL 3\nR 4\nU 10\nL 7\nR 1\nL 5\nD 11\nR 4\nU 10\nR 10\nL 3\nD 2\nL 4\nU 9\nL 1\nD 10\nL 7\nR 11\nD 5\nU 3\nD 10\nU 5\nL 2\nR 9\nD 10\nU 1\nR 10\nU 1\nL 11\nR 10\nL 8\nD 10\nL 5\nR 2\nL 3\nU 4\nL 10\nR 3\nD 5\nR 8\nL 2\nU 1\nD 7\nU 7\nL 1\nU 11\nD 2\nL 2\nD 11\nL 6\nR 9\nU 10\nR 7\nL 11\nR 7\nD 1\nR 3\nU 6\nR 7\nD 8\nU 3\nL 1\nD 11\nU 7\nR 1\nL 7\nR 2\nL 7\nD 3\nU 10\nD 9\nU 10\nR 5\nD 4\nU 7\nL 11\nU 1\nL 12\nR 10\nU 7\nR 3\nL 2\nU 11\nR 6\nU 8\nD 2\nR 2\nL 8\nU 7\nD 10\nU 3\nD 1\nU 8\nR 2\nD 9\nL 8\nR 10\nL 1\nU 8\nR 11\nU 8\nR 12\nD 10\nL 5\nU 6\nL 11\nD 12\nL 5\nU 4\nL 8\nU 11\nD 2\nU 4\nL 6\nR 1\nL 4\nU 2\nL 5\nR 2\nD 7\nL 12\nR 4\nL 1\nD 7\nL 11\nU 3\nD 9\nR 12\nU 10\nD 9\nU 10\nL 6\nR 6\nD 10\nR 10\nD 5\nU 9\nR 2\nD 4\nL 9\nU 2\nR 5\nL 8\nR 1\nD 9\nL 7\nD 11\nR 3\nL 8\nU 3\nD 7\nU 3\nR 8\nU 7\nL 1\nU 10\nD 8\nR 11\nU 10\nD 11\nU 8\nR 12\nU 9\nD 12\nU 4\nD 11\nR 8\nD 1\nL 4\nR 9\nL 7\nU 8\nR 6\nD 5\nR 8\nD 2\nR 6\nL 4\nD 7\nL 3\nU 11\nR 2\nL 10\nD 13\nR 2\nD 10\nU 9\nD 5\nU 5\nD 5\nL 11\nU 3\nR 5\nU 7\nL 6\nD 4\nL 3\nR 2\nD 9\nU 7\nD 9\nU 1\nD 8\nL 11\nD 7\nU 7\nR 3\nL 6\nD 6\nR 5\nL 6\nR 3\nU 10\nL 13\nU 9\nR 11\nL 13\nR 9\nD 3\nU 3\nL 12\nU 4\nL 3\nD 12\nR 13\nD 7\nU 6\nL 11\nU 4\nL 2\nD 7\nL 8\nR 10\nU 13\nR 9\nU 8\nR 11\nU 8\nR 7\nU 11\nL 8\nD 3\nL 4\nU 6\nD 12\nR 13\nU 10\nL 4\nR 12\nU 13\nL 11\nR 10\nL 8\nU 6\nL 1\nD 11\nU 12\nL 8\nU 2\nL 6\nU 2\nL 1\nU 3\nD 5\nU 2\nD 10\nU 11\nL 9\nD 9\nR 7\nL 3\nU 3\nD 2\nU 2\nR 10\nD 1\nR 5\nL 1\nD 3\nR 8\nU 4\nR 1\nD 13\nL 12\nU 1\nD 13\nR 4\nD 3\nR 8\nD 2\nU 3\nD 8\nL 3\nU 9\nR 4\nD 8\nU 9\nD 3\nL 8\nD 11\nL 8\nU 1\nD 13\nU 14\nD 10\nU 9\nR 4\nD 14\nL 10\nD 10\nU 6\nD 7\nR 8\nU 5\nL 8\nR 9\nD 12\nL 9\nD 8\nR 13\nL 7\nU 11\nR 8\nD 5\nU 14\nD 5\nU 9\nL 7\nD 12\nR 2\nL 14\nU 1\nR 9\nU 10\nR 4\nU 4\nR 14\nD 10\nL 3\nR 4\nL 13\nU 3\nR 6\nL 2\nU 6\nR 2\nD 10\nU 4\nL 10\nU 8\nL 6\nD 2\nR 1\nD 5\nU 14\nL 4\nD 5\nL 5\nU 10\nD 5\nR 13\nD 14\nU 14\nR 4\nD 13\nU 10\nR 13\nD 12\nR 10\nL 12\nU 8\nD 10\nU 12\nD 5\nL 11\nD 13\nR 8\nD 10\nR 13\nL 13\nD 1\nU 10\nL 4\nU 14\nD 12\nU 4\nD 5\nR 10\nL 8\nR 13\nD 13\nU 5\nD 4\nL 1\nD 9\nL 14\nU 14\nR 3\nU 6\nR 14\nL 4\nR 7\nD 3\nR 8\nD 5\nL 2\nU 5\nD 8\nU 3\nR 7\nD 13\nU 14\nR 11\nU 6\nL 15\nR 4\nU 12\nR 1\nL 8\nU 15\nR 5\nL 2\nD 1\nR 12\nD 1\nR 12\nD 1\nL 1\nR 9\nU 11\nD 2\nU 12\nD 3\nU 6\nD 12\nU 11\nR 6\nD 11\nL 5\nR 3\nD 1\nU 7\nL 10\nD 4\nL 7\nR 2\nU 8\nD 11\nL 12\nR 6\nL 7\nD 13\nU 11\nR 5\nD 1\nL 11\nD 2\nU 15\nL 11\nR 3\nL 9\nD 12\nU 8\nD 9\nL 14\nD 10\nL 12\nR 13\nD 3\nL 9\nD 13\nR 8\nD 15\nU 14\nR 9\nU 12\nR 2\nD 3\nU 5\nL 3\nR 7\nU 15\nR 10\nL 6\nR 6\nD 12\nR 14\nL 7\nU 8\nL 11\nR 13\nU 8\nL 8\nR 5\nD 3\nU 8\nD 4\nL 1\nD 11\nU 15\nL 14\nR 8\nL 1\nR 13\nL 2\nR 13\nU 9\nD 12\nL 14\nR 13\nL 5\nR 7\nD 13\nU 15\nR 2\nL 12\nD 12\nR 13\nU 2\nD 4\nR 14\nL 1\nU 12\nD 3\nU 6\nR 6\nL 6\nU 9\nL 16\nU 16\nD 8\nR 8\nU 1\nD 14\nR 13\nD 3\nU 14\nR 7\nU 7\nR 7\nD 12\nU 1\nD 2\nU 2\nR 7\nD 11\nU 4\nR 13\nU 2\nL 16\nU 12\nL 15\nD 10\nU 11\nL 1\nD 1\nL 3\nD 15\nL 12\nR 2\nD 12\nU 16\nR 16\nU 11\nD 15\nR 11\nU 13\nL 3\nR 15\nD 16\nR 9\nD 1\nL 3\nU 9\nD 12\nU 11\nL 13\nR 10\nD 4\nU 12\nL 2\nR 8\nL 1\nU 9\nL 1\nD 7\nR 14\nU 12\nL 9\nR 5\nD 7\nR 10\nD 14\nL 2\nU 2\nL 4\nD 15\nU 7\nD 9\nL 4\nR 1\nL 8\nD 14\nU 9\nD 1\nR 15\nU 9\nR 10\nD 11\nL 14\nR 12\nL 12\nU 13\nR 12\nL 1\nR 10\nD 14\nR 3\nU 5\nD 10\nL 13\nU 11\nR 16\nL 14\nU 6\nD 5\nR 16\nD 7\nL 16\nU 12\nR 14\nU 7\nR 6\nU 17\nR 3\nU 15\nR 17\nU 3\nR 4\nU 6\nD 13\nU 7\nR 12\nU 10\nR 7\nL 13\nU 16\nD 13\nU 2\nL 13\nR 2\nD 1\nU 10\nR 6\nD 11\nR 10\nD 6\nR 2\nL 1\nR 1\nL 5\nD 16\nU 1\nL 13\nU 3\nR 9\nL 10\nD 12\nR 9\nU 17\nR 10\nU 1\nD 13\nL 13\nR 6\nL 5\nD 5\nR 15\nD 15\nL 7\nU 11\nD 1\nU 11\nL 17\nR 5\nL 11\nR 15\nU 16\nD 2\nL 5\nD 9\nR 13\nD 14\nU 6\nL 10\nU 15\nD 15\nR 5\nD 4\nL 13\nR 11\nD 15\nU 13\nR 8\nU 6\nR 8\nU 3\nR 12\nU 11\nL 6\nU 9\nD 6\nL 1\nU 4\nL 6\nR 4\nD 2\nL 6\nU 11\nL 1\nU 10\nR 2\nU 6\nR 1\nU 7\nR 16\nL 12\nU 9\nD 9\nU 6\nR 13\nU 8\nL 6\nU 2\nR 17\nL 6\nU 5\nD 14\nR 15\nD 13\nL 5\nU 18\nR 9\nL 10\nR 9\nD 6\nL 8\nU 3\nD 8\nR 17\nL 4\nR 9\nL 13\nU 3\nR 12\nD 4\nR 11\nU 13\nL 15\nR 13\nD 18\nR 8\nL 7\nR 10\nD 11\nU 8\nR 4\nL 7\nU 15\nL 3\nD 15\nR 3\nU 6\nL 17\nD 17\nL 12\nD 16\nR 14\nL 1\nR 7\nD 15\nU 2\nL 5\nU 16\nD 7\nL 11\nU 1\nR 2\nD 4\nL 13\nU 17\nR 5\nD 17\nR 14\nL 1\nD 11\nU 11\nR 8\nU 12\nD 12\nU 17\nR 15\nL 4\nD 7\nU 8\nR 4\nL 14\nU 11\nL 3\nR 1\nL 14\nU 3\nL 3\nD 15\nL 14\nD 7\nR 2\nL 6\nU 6\nR 12\nU 17\nD 1\nR 15\nD 1\nU 14\nD 6\nU 4\nR 4\nL 3\nU 14\nL 1\nR 4\nU 13\nD 18\nL 11\nU 4\nD 9\nR 10\nU 17\nR 8\nD 12\nR 9\nL 5\nD 6\nL 9\nR 9\nL 17\nD 9\nR 2\nL 4\nR 8\nD 8\nR 8\nD 13\nR 12\nL 13\nU 8\nD 8\nL 16\nD 15\nU 11\nL 8\nR 7\nD 2\nR 6\nU 19\nD 4\nU 7\nL 17\nR 9\nU 18\nD 4\nR 19\nL 14\nU 8\nD 19\nL 12\nR 14\nU 13\nD 3\nL 8\nD 4\nR 9\nU 18\nD 1\nU 2\nL 16\nU 2\nD 12\nU 5\nL 4\nD 6\nU 14\nR 1\nL 7\nD 7\nL 19\nD 5\nU 16\nD 15\nR 14\nU 19\nD 19\nL 6\nR 3\nL 14\nR 16\nL 1\nU 4\nR 19\nU 2\nL 3\nD 19\nL 17\nU 13\nD 5\nL 10\nU 18\nR 13\nD 6\nL 17\nD 9\nR 10\nD 8\nR 19\nL 6\nD 7\nR 18\nU 8\nL 6\nU 18\nL 18\nD 16\nR 8\nL 10\nR 6\nD 1\nL 6\nU 8\nL 19\nD 18\nU 3\nD 17\nU 9\nR 13\nD 18\nR 2\nL 10\nD 17\nL 17\nU 8\nL 13\nD 5\nL 1\nU 10\nL 3\nR 3\nD 12\nU 11\nL 15\nD 15")
(defn parse [s]
  (let [[d n] (str/split s #"\s+")]
    (repeat (u/to-int n) d)))

(defn move-head [[x y] d]
  (case d
    "R" [(inc x) y]
    "L" [(dec x) y]
    "U" [x (dec y)]
    "D" [x (inc y)]))


(defn move-tail [[hx hy] [tx ty]]
  (case [(- hx tx) (- hy ty)]
    ([0 0] [0 1] [0 -1] [1 0] [-1 0] [1 1] [-1 -1] [1 -1] [-1 1]) [tx ty]
    [+0 +2] [(+ tx 0) (+ ty 1)]
    [+1 +2] [(+ tx 1) (+ ty 1)]
    [-1 +2] [(- tx 1) (+ ty 1)]
    [+0 -2] [(+ tx 0) (- ty 1)]
    [+1 -2] [(+ tx 1) (- ty 1)]
    [-1 -2] [(- tx 1) (- ty 1)]
    [+2 +0] [(+ tx 1) (+ ty 0)]
    [+2 +1] [(+ tx 1) (+ ty 1)]
    [+2 +2] [(+ tx 1) (+ ty 1)]
    [+2 -1] [(+ tx 1) (- ty 1)]
    [+2 -2] [(+ tx 1) (- ty 1)]
    [-2 +0] [(- tx 1) (+ ty 0)]
    [-2 +1] [(- tx 1) (+ ty 1)]
    [-2 +2] [(- tx 1) (+ ty 1)]
    [-2 -1] [(- tx 1) (- ty 1)]
    [-2 -2] [(- tx 1) (- ty 1)]))

(defn p1 [ss]
  (loop [todo  (->> ss str/split-lines (mapcat parse))
         head  [0 0]
         tail  [0 0]
         been  #{[0 0]}]
    (blet [op    (first todo)
           todo- (next todo)
           head* (move-head head op)
           tail* (move-tail head* tail)
           been+ (conj been tail*)]
      (if (empty? todo)
        (count been)
        (recur todo- head* tail* been+)))))

(assert (= 13 (p1 sample)))

(assert (= 5695 (p1 input)))


(defn p2 [ss]
  (loop [todo  (->> ss str/split-lines (mapcat parse))
         rope  (vec (repeat 10 [0 0]))
         been  #{}]
    (blet [op    (first todo)
           todo- (next todo)
           head  (first rope)
           tail  (next rope)
           head* (move-head head op)
           rope*  (reduce
                    (fn [r t]
                      (conj r (move-tail (peek r) t)))
                    [head*]
                    tail)
           been+ (conj been (last tail))]
      (if (empty? todo)
        (count been+)
        (recur todo- rope* been+)))))

(assert (= 1 (p2 sample)))
(assert (= 2434 (p2 input)))