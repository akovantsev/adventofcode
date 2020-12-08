(ns adventofcode.2017.day18
  (:require [clojure.string :as str])
  (:import [clojure.lang PersistentQueue]))


(def i "set i 31\nset a 1\nmul p 17\njgz p p\nmul a 2\nadd i -1\njgz i -2\nadd a -1\nset i 127\nset p 680\nmul p 8505\nmod p a\nmul p 129749\nadd p 12345\nmod p a\nset b p\nmod b 10000\nsnd b\nadd i -1\njgz i -9\njgz a 3\nrcv b\njgz b -1\nset f 0\nset i 126\nrcv a\nrcv b\nset p a\nmul p -1\nadd p b\njgz p 4\nsnd a\nset a b\njgz 1 3\nsnd b\nset f 1\nadd i -1\njgz i -11\nsnd a\njgz f -16\njgz a -19")

(def t "set a 1\nadd a 2\nmul a a\nmod a 5\nsnd a\nset a 0\nrcv a\njgz a -1\nset a 1\njgz a -2")

(let [instrs (->> i
               str/split-lines
               (mapv #(mapv read-string (str/split % #"\s"))))
      exit?  #(or (< % 0) (<= (count instrs) %))]
  (loop [idx  0
         freq nil
         regs {}]
    (if (exit? idx)
      :exit
      (let [[op r v] (instrs idx)
            v    (if (number? v) v (regs v 0))
            idx' (inc idx)]
        (prn op r v)
        (case op
          snd (recur idx' (regs r 0) regs)
          set (recur idx' freq (assoc regs r v))
          add (recur idx' freq (update regs r (fnil + 0) v))
          mul (recur idx' freq (update regs r (fnil * 0) v))
          mod (recur idx' freq (update regs r (fnil mod 0) v))
          rcv (if (zero? (regs r 0))
                (recur idx' freq regs)
                freq)
          jgz (if (pos? (regs r 0))
                (recur (+ idx v) freq regs)
                (recur (+ idx 1) freq regs)))))))

3188

;snd X plays a sound with a frequency equal to the value of X.
;set X Y sets register X to the value of Y.
  ;add X Y increases register X by the value of Y.
  ;mul X Y sets register X to the result of multiplying the value contained in register X by the value of Y.
  ;mod X Y sets register X to the remainder of dividing the value contained in register X by the value of Y (that is, it sets X to the result of X modulo Y).
  ;rcv X recovers the frequency of the last sound played, but only when the value of X is not zero. (If it is zero, the command does nothing.)
  ;jgz X Y jumps with an offset of the value of Y, but only if the value of X is greater than zero. (An offset of 2 skips the next instruction, an offset of -1 jumps to the previous instruction, and so on.)


(defn step [instrs this that]
  (let [{:keys [terminated idx q waiting]} this]
    (cond
      terminated
      [this that]

      (and waiting (empty? q))
      [this that]

      (or (neg? idx) (<= (count instrs) idx))
      [(assoc this :terminated true) that]

      :else
      (let [[op r v] (instrs idx)
            op   (keyword op)
            v    (if (number? v) v (this v 0))
            that (cond-> that
                   (= op :snd) (->
                                 (dissoc :waiting)
                                 (update :q conj (if (number? r) r (this r 0)))))
            this (update this :idx inc)
            this (case op
                   :set (assoc this r v)
                   :add (update this r (fnil + 0) v)
                   :mul (update this r (fnil * 0) v)
                   :mod (update this r (fnil mod 0) v)
                   :jgz (update this :idx + (if (pos? (if (number? r) r (this r 0))) (dec v) 0))
                   :snd (update this :n inc)
                   :rcv (if-let [v (peek q)]
                          (-> this (assoc r v) (update :q pop))
                          (-> this (assoc :waiting true) (update :idx dec))))]
        [this that]))))


(def t "snd 1\nsnd 2\nsnd p\nrcv a\nrcv b\nrcv c\nrcv d")

(let [instrs (->> i str/split-lines (mapv #(mapv read-string (str/split % #"\s"))))]
  (loop [p0 {:id 0 'p 0 :idx 0 :q PersistentQueue/EMPTY :n 0}
         p1 {:id 1 'p 1 :idx 0 :q PersistentQueue/EMPTY :n 0}]
    ;(prn (update p0 :q count) (update p1 :q count))
    (cond
      (and
        (or (:waiting p0) (:terminated p0))
        (or (:waiting p1) (:terminated p1)))
      (map #(update % :q vec) [p0 p1])

      (or (:waiting p0) (:terminated p0))
      (recur p1 p0)

      :else
      (let [[p0 p1] (step instrs p0 p1)]
        (recur p0 p1)))))

127 132
< 413832

'({:q [], i 0, p -15, a 95, :n 7112, :waiting true, :id 1, :idx 21, b 95, f 0}
  {:q [], i 0, p -15, a 95, :n 7239, :waiting true, :id 0, :idx 21, b 95, f 0})
