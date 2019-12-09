(ns adventofcode.2019.day05
  (:require
   [adventofcode.utils :as u]
   [clojure.string :as str]))


(def input "3,225,1,225,6,6,1100,1,238,225,104,0,1002,188,27,224,1001,224,-2241,224,4,224,102,8,223,223,1001,224,6,224,1,223,224,223,101,65,153,224,101,-108,224,224,4,224,1002,223,8,223,1001,224,1,224,1,224,223,223,1,158,191,224,101,-113,224,224,4,224,102,8,223,223,1001,224,7,224,1,223,224,223,1001,195,14,224,1001,224,-81,224,4,224,1002,223,8,223,101,3,224,224,1,224,223,223,1102,47,76,225,1102,35,69,224,101,-2415,224,224,4,224,102,8,223,223,101,2,224,224,1,224,223,223,1101,32,38,224,101,-70,224,224,4,224,102,8,223,223,101,3,224,224,1,224,223,223,1102,66,13,225,1102,43,84,225,1101,12,62,225,1102,30,35,225,2,149,101,224,101,-3102,224,224,4,224,102,8,223,223,101,4,224,224,1,223,224,223,1101,76,83,225,1102,51,51,225,1102,67,75,225,102,42,162,224,101,-1470,224,224,4,224,102,8,223,223,101,1,224,224,1,223,224,223,4,223,99,0,0,0,677,0,0,0,0,0,0,0,0,0,0,0,1105,0,99999,1105,227,247,1105,1,99999,1005,227,99999,1005,0,256,1105,1,99999,1106,227,99999,1106,0,265,1105,1,99999,1006,0,99999,1006,227,274,1105,1,99999,1105,1,280,1105,1,99999,1,225,225,225,1101,294,0,0,105,1,0,1105,1,99999,1106,0,300,1105,1,99999,1,225,225,225,1101,314,0,0,106,0,0,1105,1,99999,1108,226,677,224,1002,223,2,223,1005,224,329,101,1,223,223,108,226,226,224,1002,223,2,223,1005,224,344,1001,223,1,223,1107,677,226,224,1002,223,2,223,1006,224,359,101,1,223,223,1008,226,226,224,1002,223,2,223,1005,224,374,101,1,223,223,8,226,677,224,102,2,223,223,1006,224,389,101,1,223,223,7,226,677,224,1002,223,2,223,1005,224,404,1001,223,1,223,7,226,226,224,1002,223,2,223,1005,224,419,101,1,223,223,107,226,677,224,1002,223,2,223,1005,224,434,101,1,223,223,107,226,226,224,1002,223,2,223,1005,224,449,1001,223,1,223,1107,226,677,224,102,2,223,223,1006,224,464,1001,223,1,223,1007,677,226,224,1002,223,2,223,1006,224,479,1001,223,1,223,1107,677,677,224,1002,223,2,223,1005,224,494,101,1,223,223,1108,677,226,224,102,2,223,223,1006,224,509,101,1,223,223,7,677,226,224,1002,223,2,223,1005,224,524,1001,223,1,223,1008,677,226,224,102,2,223,223,1005,224,539,1001,223,1,223,1108,226,226,224,102,2,223,223,1005,224,554,101,1,223,223,107,677,677,224,102,2,223,223,1006,224,569,1001,223,1,223,1007,226,226,224,102,2,223,223,1006,224,584,101,1,223,223,8,677,677,224,102,2,223,223,1005,224,599,1001,223,1,223,108,677,677,224,1002,223,2,223,1005,224,614,101,1,223,223,108,226,677,224,102,2,223,223,1005,224,629,101,1,223,223,8,677,226,224,102,2,223,223,1006,224,644,1001,223,1,223,1007,677,677,224,1002,223,2,223,1006,224,659,1001,223,1,223,1008,677,677,224,1002,223,2,223,1005,224,674,101,1,223,223,4,223,99,226")

(defn parse [s]
  (->> (str/split s #",")
    (mapv u/to-int)
    (map-indexed vector)
    (into {})))

(defn readv [state mode rel-base x]
  (case mode
    0 (get state x 0)
    1 x
    2 (state (+ rel-base x) 0)))

(defn writev [mode rel-base x]
  (case mode
    0 x
    1 x
    2 (+ rel-base x)))

(defn exit? [n] (= 99 n))

(def WIDTH {1 4, 2 4, 3 2, 4 2, 5 3, 6 3, 7 4, 8 4, 9 2})

(defn ins-vec [n] (->> n (format "%05d") (map str) (mapv u/to-int)))

(assert (= [0 0 0 0 3] (ins-vec 3)))
(assert (= [0 0 1 0 3] (ins-vec 103)))


(defn next-input [input]
  ;; this is bullshit. reuse last input if all consumed
  (or (next input) input))

(defn step [{::keys [input state idx relbase]}]
  (loop [rb    (or relbase 0)
         input input
         idx   idx
         state state]
    (let [ins (state idx)]
      (if (exit? ins)
        ::halt
        (let [[mc mb ma _ op] (ins-vec ins)
              idx* (-> op WIDTH (+ idx))
              a    (-> idx (+ 1) (state 0))
              b    (-> idx (+ 2) (state 0))
              c    (-> idx (+ 3) (state 0))
              ai   (writev ma rb a)
              ci   (writev mc rb c)
              A    (readv state ma rb a)
              B    (readv state mb rb b)]
          (case op
            1 (recur rb input idx* (assoc state ci (+ A B)))
            2 (recur rb input idx* (assoc state ci (* A B)))
            3 (recur rb (next-input input) idx* (assoc state ai (first input)))
            4 {::idx     idx*
               ::relbase rb
               ::state   state
               ::input   input
               ::output  A}
            5 (recur rb input (if (zero? A) idx* B) state)
            6 (recur rb input (if (zero? A) B idx*) state)
            7 (recur rb input idx* (assoc state ci (if (< A B) 1 0)))
            8 (recur rb input idx* (assoc state ci (if (= A B) 1 0)))
            9 (recur (+ rb A) input idx* state)))))))


(defn output-before-halt [input init]
  (->> {::state init ::idx 0 ::input input}
    (iterate step)
    (take-while #(not= ::halt %))
    last
    ::output))


(assert (= 13087969 (output-before-halt [2] (parse input))))
(assert (= 14110739 (output-before-halt [5] (parse input))))