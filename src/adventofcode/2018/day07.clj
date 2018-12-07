(ns adventofcode.2018.day07
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(set! *print-length* 50)

(def input "Step A must be finished before step I can begin.\nStep M must be finished before step Q can begin.\nStep B must be finished before step S can begin.\nStep G must be finished before step N can begin.\nStep Y must be finished before step R can begin.\nStep E must be finished before step H can begin.\nStep K must be finished before step L can begin.\nStep H must be finished before step Z can begin.\nStep C must be finished before step P can begin.\nStep W must be finished before step U can begin.\nStep V must be finished before step L can begin.\nStep O must be finished before step N can begin.\nStep U must be finished before step I can begin.\nStep D must be finished before step P can begin.\nStep Q must be finished before step L can begin.\nStep F must be finished before step Z can begin.\nStep L must be finished before step N can begin.\nStep P must be finished before step S can begin.\nStep I must be finished before step S can begin.\nStep S must be finished before step R can begin.\nStep T must be finished before step N can begin.\nStep N must be finished before step X can begin.\nStep Z must be finished before step J can begin.\nStep R must be finished before step J can begin.\nStep J must be finished before step X can begin.\nStep E must be finished before step I can begin.\nStep T must be finished before step R can begin.\nStep I must be finished before step N can begin.\nStep K must be finished before step C can begin.\nStep B must be finished before step D can begin.\nStep K must be finished before step T can begin.\nStep E must be finished before step P can begin.\nStep F must be finished before step I can begin.\nStep O must be finished before step U can begin.\nStep I must be finished before step J can begin.\nStep S must be finished before step Z can begin.\nStep L must be finished before step J can begin.\nStep F must be finished before step T can begin.\nStep F must be finished before step P can begin.\nStep I must be finished before step T can begin.\nStep G must be finished before step S can begin.\nStep V must be finished before step U can begin.\nStep F must be finished before step R can begin.\nStep L must be finished before step R can begin.\nStep Y must be finished before step D can begin.\nStep M must be finished before step E can begin.\nStep U must be finished before step L can begin.\nStep C must be finished before step D can begin.\nStep W must be finished before step N can begin.\nStep S must be finished before step N can begin.\nStep O must be finished before step S can begin.\nStep B must be finished before step T can begin.\nStep V must be finished before step T can begin.\nStep S must be finished before step X can begin.\nStep V must be finished before step P can begin.\nStep F must be finished before step L can begin.\nStep P must be finished before step R can begin.\nStep D must be finished before step N can begin.\nStep C must be finished before step L can begin.\nStep O must be finished before step Q can begin.\nStep N must be finished before step Z can begin.\nStep Y must be finished before step L can begin.\nStep B must be finished before step K can begin.\nStep P must be finished before step Z can begin.\nStep V must be finished before step Z can begin.\nStep U must be finished before step J can begin.\nStep Q must be finished before step S can begin.\nStep H must be finished before step F can begin.\nStep E must be finished before step O can begin.\nStep D must be finished before step F can begin.\nStep D must be finished before step X can begin.\nStep L must be finished before step S can begin.\nStep Z must be finished before step R can begin.\nStep K must be finished before step X can begin.\nStep M must be finished before step V can begin.\nStep A must be finished before step M can begin.\nStep B must be finished before step W can begin.\nStep A must be finished before step P can begin.\nStep W must be finished before step Q can begin.\nStep R must be finished before step X can begin.\nStep M must be finished before step H can begin.\nStep F must be finished before step S can begin.\nStep K must be finished before step Q can begin.\nStep Y must be finished before step Q can begin.\nStep W must be finished before step S can begin.\nStep Q must be finished before step T can begin.\nStep K must be finished before step H can begin.\nStep K must be finished before step D can begin.\nStep E must be finished before step T can begin.\nStep Y must be finished before step E can begin.\nStep A must be finished before step O can begin.\nStep G must be finished before step E can begin.\nStep C must be finished before step O can begin.\nStep G must be finished before step H can begin.\nStep Y must be finished before step I can begin.\nStep V must be finished before step S can begin.\nStep B must be finished before step R can begin.\nStep B must be finished before step X can begin.\nStep V must be finished before step I can begin.\nStep N must be finished before step J can begin.\nStep H must be finished before step I can begin.")
;(def input "Step C must be finished before step A can begin.\nStep C must be finished before step F can begin.\nStep A must be finished before step B can begin.\nStep A must be finished before step D can begin.\nStep B must be finished before step E can begin.\nStep D must be finished before step E can begin.\nStep F must be finished before step E can begin.")

(defn parse-line [line]
  (->> line
    (re-matches #"Step ([A-Z]{1}) must be finished before step ([A-Z]{1}) can begin.")
    (rest)))


(def sconj (fnil conj #{}))

(def MAP  ;; {requirement #{locked}}
  (->> input
    (str/split-lines)
    (map parse-line)
    (reduce
      (fn rf [m [requirement locked]]
        (update m requirement sconj locked))
      {})))

(def START
  (let [all-requirements (->> MAP keys set)
        all-locked       (->> MAP vals (reduce into #{}))]
    (set/difference all-requirements all-locked)))



(def r1
  (loop [todo      MAP
         used      []
         available START]
    (let [step (-> available sort first)]
      (if (nil? step)
        (str/join used)
        (let [candidates    (get todo step)
              todo          (dissoc todo step)
              still-locked  (->> todo vals (reduce into #{}))
              unlocked      (remove still-locked candidates)]
          (recur
            todo
            (-> used (conj step))
            (-> available (disj step) (into unlocked))))))))


(assert (not= r1 "ABDFGEHIJKCLMNOPQRSTUVWXYZ"))
(assert (= r1 "ABGKCMVWYDEHFOPQUILSTNZRJX"))


(def DURATION
  (->> (rest (range))
    (map #(+ 60 %))
    (map vector (map str "ABCDEFGHIJKLMNOPQRSTUVWXYZ"))
    (into {})))

(assert (-> "Z" DURATION (= 86)))



(def r2
  (loop [seconds     0
         todo        MAP
         used        []
         available   START
         workers     5
         in-progress {}]
    (let [finished   (->> in-progress
                       (filter #(-> % val zero?))
                       (map key)
                       (seq))
          all-done?  (and (empty? in-progress) (empty? available))
          some-done? (some? finished)
          can-start? (and (pos? workers) (seq available))]
      (cond
        all-done?
        seconds

        some-done?
        (let [candidates   (->> (select-keys todo finished)
                             (vals)
                             (reduce into #{}))
              todo         (reduce dissoc todo finished)
              still-locked (->> todo vals (reduce into #{}))
              unlocked     (remove still-locked candidates)]
          (recur seconds todo
            (into used finished)
            (into available unlocked)
            (+ workers (count finished))
            (reduce dissoc in-progress finished)))

        can-start?
        (let [steps (->> available sort (take workers))]
          (recur seconds todo used
            (reduce disj available steps)
            (- workers (count steps))
            (reduce-kv assoc in-progress (select-keys DURATION steps))))

        :tick
        (recur (inc seconds) todo used available workers
          (reduce-kv #(assoc %1 %2 (dec %3)) {} in-progress))))))

(assert (= r2 898))