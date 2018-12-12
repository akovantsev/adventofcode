(ns adventofcode.2016.day08
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))

(def input "rect 1x1\nrotate row y=0 by 10\nrect 1x1\nrotate row y=0 by 10\nrect 1x1\nrotate row y=0 by 5\nrect 1x1\nrotate row y=0 by 3\nrect 2x1\nrotate row y=0 by 4\nrect 1x1\nrotate row y=0 by 3\nrect 1x1\nrotate row y=0 by 2\nrect 1x1\nrotate row y=0 by 3\nrect 2x1\nrotate row y=0 by 2\nrect 1x1\nrotate row y=0 by 3\nrect 2x1\nrotate row y=0 by 5\nrotate column x=0 by 1\nrect 4x1\nrotate row y=1 by 12\nrotate row y=0 by 10\nrotate column x=0 by 1\nrect 9x1\nrotate column x=7 by 1\nrotate row y=1 by 3\nrotate row y=0 by 2\nrect 1x2\nrotate row y=1 by 3\nrotate row y=0 by 1\nrect 1x3\nrotate column x=35 by 1\nrotate column x=5 by 2\nrotate row y=2 by 5\nrotate row y=1 by 5\nrotate row y=0 by 2\nrect 1x3\nrotate row y=2 by 8\nrotate row y=1 by 10\nrotate row y=0 by 5\nrotate column x=5 by 1\nrotate column x=0 by 1\nrect 6x1\nrotate row y=2 by 7\nrotate row y=0 by 5\nrotate column x=0 by 1\nrect 4x1\nrotate column x=40 by 2\nrotate row y=2 by 10\nrotate row y=0 by 12\nrotate column x=5 by 1\nrotate column x=0 by 1\nrect 9x1\nrotate column x=43 by 1\nrotate column x=40 by 2\nrotate column x=38 by 1\nrotate column x=15 by 1\nrotate row y=3 by 35\nrotate row y=2 by 35\nrotate row y=1 by 32\nrotate row y=0 by 40\nrotate column x=32 by 1\nrotate column x=29 by 1\nrotate column x=27 by 1\nrotate column x=25 by 1\nrotate column x=23 by 2\nrotate column x=22 by 1\nrotate column x=21 by 3\nrotate column x=20 by 1\nrotate column x=18 by 3\nrotate column x=17 by 1\nrotate column x=15 by 1\nrotate column x=14 by 1\nrotate column x=12 by 1\nrotate column x=11 by 3\nrotate column x=10 by 1\nrotate column x=9 by 1\nrotate column x=8 by 2\nrotate column x=7 by 1\nrotate column x=4 by 1\nrotate column x=3 by 1\nrotate column x=2 by 1\nrotate column x=0 by 1\nrect 34x1\nrotate column x=44 by 1\nrotate column x=24 by 1\nrotate column x=19 by 1\nrotate row y=1 by 8\nrotate row y=0 by 10\nrotate column x=8 by 1\nrotate column x=7 by 1\nrotate column x=6 by 1\nrotate column x=5 by 2\nrotate column x=3 by 1\nrotate column x=2 by 1\nrotate column x=1 by 1\nrotate column x=0 by 1\nrect 9x1\nrotate row y=0 by 40\nrotate column x=43 by 1\nrotate row y=4 by 10\nrotate row y=3 by 10\nrotate row y=2 by 5\nrotate row y=1 by 10\nrotate row y=0 by 15\nrotate column x=7 by 2\nrotate column x=6 by 3\nrotate column x=5 by 2\nrotate column x=3 by 2\nrotate column x=2 by 4\nrotate column x=0 by 2\nrect 9x2\nrotate row y=3 by 47\nrotate row y=0 by 10\nrotate column x=42 by 3\nrotate column x=39 by 4\nrotate column x=34 by 3\nrotate column x=32 by 3\nrotate column x=29 by 3\nrotate column x=22 by 3\nrotate column x=19 by 3\nrotate column x=14 by 4\nrotate column x=4 by 3\nrotate row y=4 by 3\nrotate row y=3 by 8\nrotate row y=1 by 5\nrotate column x=2 by 3\nrotate column x=1 by 3\nrotate column x=0 by 2\nrect 3x2\nrotate row y=4 by 8\nrotate column x=45 by 1\nrotate column x=40 by 5\nrotate column x=26 by 3\nrotate column x=25 by 5\nrotate column x=15 by 5\nrotate column x=10 by 5\nrotate column x=7 by 5\nrotate row y=5 by 35\nrotate row y=4 by 42\nrotate row y=2 by 5\nrotate row y=1 by 20\nrotate row y=0 by 45\nrotate column x=48 by 5\nrotate column x=47 by 5\nrotate column x=46 by 5\nrotate column x=43 by 5\nrotate column x=41 by 5\nrotate column x=38 by 5\nrotate column x=37 by 5\nrotate column x=36 by 5\nrotate column x=33 by 1\nrotate column x=32 by 5\nrotate column x=31 by 5\nrotate column x=30 by 1\nrotate column x=28 by 5\nrotate column x=27 by 5\nrotate column x=26 by 5\nrotate column x=23 by 1\nrotate column x=22 by 5\nrotate column x=21 by 5\nrotate column x=20 by 1\nrotate column x=17 by 5\nrotate column x=16 by 5\nrotate column x=13 by 1\nrotate column x=12 by 3\nrotate column x=7 by 5\nrotate column x=6 by 5\nrotate column x=3 by 1\nrotate column x=2 by 3")


(defn parse-line [s]
  (let [rect-re #"(rec)t (\d+)x(\d+)"
        col-re  #"rotate (col)umn x=(\d+) by (\d+)"
        row-re  #"rotate (row) y=(\d+) by (\d+)"
        matches (or
                  (re-matches rect-re s)
                  (re-matches col-re s)
                  (re-matches row-re s))
        to-map (fn to-map [[op a b]]
                 (let [[a b] (map edn/read-string [a b])]
                   (case op
                     "rec" {::op op ::h a ::w b}
                     "col" {::op op ::col a ::dist b}
                     "row" {::op op ::row a ::dist b})))]
    (->> matches rest to-map)))

(def INSTRUCTIONS (->> input str/split-lines (map parse-line)))


(def WIDTH 50)
(def HEIGHT 6)
(def EMPTY-ROW   (vec (repeat WIDTH ".")))
(def EMPTY-STATE (vec (repeat HEIGHT EMPTY-ROW)))

(defn render-line [s]
  (->> s (partition 5) (map str/join) (str/join "  ")))

(defn render [state]
  (str/replace
    (->> state (map render-line) (str/join "\n"))
    "." " "))

(render EMPTY-STATE)

(defn create-rect [state w h]
  (reduce
    #(assoc-in %1 %2 "#")
    state
    (for [x (range w)
          y (range h)]
      [x y])))

(defn rotate-seq [dist xs]
  (let [len  (count xs)
        skip (- len dist)]
    (->> xs (cycle) (drop skip) (take len))))

(defn rotate-row [state y dist]
    (assoc state y (->> y state (rotate-seq dist) (vec))))

(defn rotate-col [state x dist]
  (->> state
    (map #(nth % x))
    (rotate-seq dist)
    (map-indexed vector)
    (reduce
      (fn rf [state [idx v]]
        (assoc-in state [idx x] v))
      state)))

(defn step [state {::keys [op w h col row dist] :as instruction}]
  (case op
    "rec" (create-rect state w h)
    "col" (rotate-col state col dist)
    "row" (rotate-row state row dist)))


(def r1
  (let [stats (->> INSTRUCTIONS
                (reduce step EMPTY-STATE)
                (map frequencies)
                (apply merge-with +))]
    (get stats "#")))

(assert (not= r1 102))
(assert (= r1 121))


(render (reduce step EMPTY-STATE INSTRUCTIONS))
"###    #  #   ###    #  #    ##    ####    ##    ####    ###   #
 #  #   #  #   #  #   #  #   #  #   #      #  #   #        #    #
 #  #   #  #   #  #   #  #   #      ###    #  #   ###      #    #
 ###    #  #   ###    #  #   #      #      #  #   #        #    #
 # #    #  #   # #    #  #   #  #   #      #  #   #        #    #
 #  #    ##    #  #    ##     ##    ####    ##    ####    ###   #### "