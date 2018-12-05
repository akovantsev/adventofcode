(ns adventofcode.2015.day14
  (:require [clojure.edn :as edn]
            [clojure.string :as str]))


(def input "Vixen can fly 19 km/s for 7 seconds, but then must rest for 124 seconds.\nRudolph can fly 3 km/s for 15 seconds, but then must rest for 28 seconds.\nDonner can fly 19 km/s for 9 seconds, but then must rest for 164 seconds.\nBlitzen can fly 19 km/s for 9 seconds, but then must rest for 158 seconds.\nComet can fly 13 km/s for 7 seconds, but then must rest for 82 seconds.\nCupid can fly 25 km/s for 6 seconds, but then must rest for 145 seconds.\nDasher can fly 14 km/s for 3 seconds, but then must rest for 38 seconds.\nDancer can fly 3 km/s for 16 seconds, but then must rest for 37 seconds.\nPrancer can fly 25 km/s for 6 seconds, but then must rest for 143 seconds.")
(def TIMELIMIT 2503)


(defn parse-line [line]
  (->> line
    (re-matches #"(\w+) can fly (\d+) km/s for (\d+) seconds, but then must rest for (\d+) seconds.")
    rest
    (map edn/read-string)
    (zipmap [:id :speed :on :off])))

(defn parse-input [input]
  (->> input str/split-lines (map parse-line)))


(defn infinite-distances [{:as deer :keys [:speed :on :off]}]
  (->> (concat
         (repeat on speed)
         (repeat off 0))
    (cycle)))

(defn position [deer]
  (->> deer infinite-distances (take TIMELIMIT) (reduce +)))

(defn f1 [input]
  (->> input
    parse-input
    (map position)
    (reduce max)))



(defn award [MAX score position]
  (if (= position MAX)
    (inc score)
    score))


(defn f2 [input]
  (let [deers     (parse-input input)
        distances (map infinite-distances deers)]
    (loop [sec       1
           distances (map rest distances)
           positions (map first distances)
           scores    (repeat (count deers) 0)]
      (if (= TIMELIMIT sec)
        (reduce max scores)
        (let [positions (->> distances (map first) (map + positions))
              distances (->> distances (map rest))
              furthest  (reduce max positions)
              scores    (mapv award (repeat furthest) scores positions)]
          (recur (inc sec) distances positions scores))))))


(assert (not= (f1 input) 31300))
(assert (not= (f2 input) 1760046))
(assert (= (f1 input) 2660))
(assert (= (f2 input) 1256))