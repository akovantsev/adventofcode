(ns adventofcode.2019.day21
  (:require
   [adventofcode.2019.day05 :as cpu]
   [adventofcode.2019.day09 :refer [make]]
   [clojure.string :as str]
   [clojure.math.combinatorics :as combo]
   [clojure.walk :as walk])
  (:import [clojure.lang PersistentQueue]))
   ;[adventofcode.2019.day17]))

;; abandoned

(def input "109,2050,21101,966,0,1,21102,13,1,0,1105,1,1378,21102,20,1,0,1106,0,1337,21101,0,27,0,1106,0,1279,1208,1,65,748,1005,748,73,1208,1,79,748,1005,748,110,1208,1,78,748,1005,748,132,1208,1,87,748,1005,748,169,1208,1,82,748,1005,748,239,21101,0,1041,1,21102,1,73,0,1106,0,1421,21102,1,78,1,21101,1041,0,2,21102,88,1,0,1106,0,1301,21101,68,0,1,21101,1041,0,2,21102,1,103,0,1106,0,1301,1101,0,1,750,1106,0,298,21101,0,82,1,21101,0,1041,2,21101,125,0,0,1105,1,1301,1101,0,2,750,1106,0,298,21102,79,1,1,21102,1,1041,2,21101,0,147,0,1106,0,1301,21101,84,0,1,21101,0,1041,2,21101,0,162,0,1106,0,1301,1101,0,3,750,1105,1,298,21102,65,1,1,21101,0,1041,2,21102,1,184,0,1105,1,1301,21101,0,76,1,21101,1041,0,2,21101,199,0,0,1105,1,1301,21101,75,0,1,21101,0,1041,2,21102,214,1,0,1106,0,1301,21101,0,221,0,1105,1,1337,21101,10,0,1,21102,1041,1,2,21102,1,236,0,1105,1,1301,1106,0,553,21102,85,1,1,21101,0,1041,2,21102,254,1,0,1105,1,1301,21101,78,0,1,21102,1041,1,2,21101,0,269,0,1105,1,1301,21102,1,276,0,1105,1,1337,21101,10,0,1,21101,1041,0,2,21102,1,291,0,1105,1,1301,1102,1,1,755,1105,1,553,21101,0,32,1,21101,0,1041,2,21102,313,1,0,1105,1,1301,21101,0,320,0,1105,1,1337,21102,327,1,0,1105,1,1279,2101,0,1,749,21102,1,65,2,21101,0,73,3,21101,346,0,0,1105,1,1889,1206,1,367,1007,749,69,748,1005,748,360,1101,0,1,756,1001,749,-64,751,1106,0,406,1008,749,74,748,1006,748,381,1101,0,-1,751,1106,0,406,1008,749,84,748,1006,748,395,1102,1,-2,751,1106,0,406,21101,1100,0,1,21102,406,1,0,1106,0,1421,21101,32,0,1,21102,1,1100,2,21102,421,1,0,1106,0,1301,21102,1,428,0,1106,0,1337,21102,1,435,0,1106,0,1279,1201,1,0,749,1008,749,74,748,1006,748,453,1102,1,-1,752,1105,1,478,1008,749,84,748,1006,748,467,1102,-2,1,752,1105,1,478,21101,1168,0,1,21101,0,478,0,1106,0,1421,21101,0,485,0,1105,1,1337,21101,0,10,1,21102,1,1168,2,21102,1,500,0,1106,0,1301,1007,920,15,748,1005,748,518,21101,0,1209,1,21102,518,1,0,1105,1,1421,1002,920,3,529,1001,529,921,529,102,1,750,0,1001,529,1,537,101,0,751,0,1001,537,1,545,102,1,752,0,1001,920,1,920,1106,0,13,1005,755,577,1006,756,570,21102,1,1100,1,21102,570,1,0,1106,0,1421,21101,987,0,1,1105,1,581,21102,1,1001,1,21101,588,0,0,1105,1,1378,1102,1,758,594,102,1,0,753,1006,753,654,21002,753,1,1,21102,1,610,0,1105,1,667,21102,0,1,1,21102,621,1,0,1106,0,1463,1205,1,647,21101,1015,0,1,21102,635,1,0,1106,0,1378,21102,1,1,1,21102,1,646,0,1106,0,1463,99,1001,594,1,594,1106,0,592,1006,755,664,1102,1,0,755,1106,0,647,4,754,99,109,2,1102,1,726,757,22102,1,-1,1,21102,9,1,2,21101,0,697,3,21102,692,1,0,1105,1,1913,109,-2,2106,0,0,109,2,1002,757,1,706,2102,1,-1,0,1001,757,1,757,109,-2,2105,1,0,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,255,63,159,223,95,127,191,0,93,86,181,200,155,184,153,212,143,49,199,116,54,201,253,213,178,239,174,51,71,102,247,228,114,168,113,175,254,162,154,38,121,46,87,172,206,242,183,218,84,185,234,142,141,85,177,57,78,115,61,108,230,70,122,98,117,68,76,250,56,42,43,248,34,221,249,77,152,236,103,79,198,232,163,190,62,196,110,137,39,222,214,126,53,125,227,123,119,156,205,47,231,158,189,220,244,219,235,136,138,50,166,157,60,170,92,217,229,187,241,109,238,169,179,243,207,186,251,188,120,69,167,171,58,204,197,245,59,216,139,111,140,233,226,94,237,106,215,173,99,55,101,100,107,124,246,35,118,202,252,182,203,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,20,73,110,112,117,116,32,105,110,115,116,114,117,99,116,105,111,110,115,58,10,13,10,87,97,108,107,105,110,103,46,46,46,10,10,13,10,82,117,110,110,105,110,103,46,46,46,10,10,25,10,68,105,100,110,39,116,32,109,97,107,101,32,105,116,32,97,99,114,111,115,115,58,10,10,58,73,110,118,97,108,105,100,32,111,112,101,114,97,116,105,111,110,59,32,101,120,112,101,99,116,101,100,32,115,111,109,101,116,104,105,110,103,32,108,105,107,101,32,65,78,68,44,32,79,82,44,32,111,114,32,78,79,84,67,73,110,118,97,108,105,100,32,102,105,114,115,116,32,97,114,103,117,109,101,110,116,59,32,101,120,112,101,99,116,101,100,32,115,111,109,101,116,104,105,110,103,32,108,105,107,101,32,65,44,32,66,44,32,67,44,32,68,44,32,74,44,32,111,114,32,84,40,73,110,118,97,108,105,100,32,115,101,99,111,110,100,32,97,114,103,117,109,101,110,116,59,32,101,120,112,101,99,116,101,100,32,74,32,111,114,32,84,52,79,117,116,32,111,102,32,109,101,109,111,114,121,59,32,97,116,32,109,111,115,116,32,49,53,32,105,110,115,116,114,117,99,116,105,111,110,115,32,99,97,110,32,98,101,32,115,116,111,114,101,100,0,109,1,1005,1262,1270,3,1262,21002,1262,1,0,109,-1,2105,1,0,109,1,21102,1,1288,0,1105,1,1263,20101,0,1262,0,1102,0,1,1262,109,-1,2105,1,0,109,5,21102,1310,1,0,1105,1,1279,21201,1,0,-2,22208,-2,-4,-1,1205,-1,1332,22102,1,-3,1,21102,1,1332,0,1106,0,1421,109,-5,2106,0,0,109,2,21102,1346,1,0,1106,0,1263,21208,1,32,-1,1205,-1,1363,21208,1,9,-1,1205,-1,1363,1106,0,1373,21101,0,1370,0,1106,0,1279,1105,1,1339,109,-2,2106,0,0,109,5,1202,-4,1,1385,21001,0,0,-2,22101,1,-4,-4,21101,0,0,-3,22208,-3,-2,-1,1205,-1,1416,2201,-4,-3,1408,4,0,21201,-3,1,-3,1106,0,1396,109,-5,2106,0,0,109,2,104,10,22101,0,-1,1,21101,1436,0,0,1105,1,1378,104,10,99,109,-2,2105,1,0,109,3,20002,594,753,-1,22202,-1,-2,-1,201,-1,754,754,109,-3,2105,1,0,109,10,21101,0,5,-5,21101,0,1,-4,21102,1,0,-3,1206,-9,1555,21101,3,0,-6,21102,1,5,-7,22208,-7,-5,-8,1206,-8,1507,22208,-6,-4,-8,1206,-8,1507,104,64,1105,1,1529,1205,-6,1527,1201,-7,716,1515,21002,0,-11,-8,21201,-8,46,-8,204,-8,1105,1,1529,104,46,21201,-7,1,-7,21207,-7,22,-8,1205,-8,1488,104,10,21201,-6,-1,-6,21207,-6,0,-8,1206,-8,1484,104,10,21207,-4,1,-8,1206,-8,1569,21101,0,0,-9,1105,1,1689,21208,-5,21,-8,1206,-8,1583,21101,1,0,-9,1106,0,1689,1201,-5,716,1589,20101,0,0,-2,21208,-4,1,-1,22202,-2,-1,-1,1205,-2,1613,21201,-5,0,1,21102,1,1613,0,1106,0,1444,1206,-1,1634,21202,-5,1,1,21102,1,1627,0,1105,1,1694,1206,1,1634,21102,1,2,-3,22107,1,-4,-8,22201,-1,-8,-8,1206,-8,1649,21201,-5,1,-5,1206,-3,1663,21201,-3,-1,-3,21201,-4,1,-4,1106,0,1667,21201,-4,-1,-4,21208,-4,0,-1,1201,-5,716,1676,22002,0,-1,-1,1206,-1,1686,21102,1,1,-4,1105,1,1477,109,-10,2106,0,0,109,11,21102,1,0,-6,21101,0,0,-8,21101,0,0,-7,20208,-6,920,-9,1205,-9,1880,21202,-6,3,-9,1201,-9,921,1724,21002,0,1,-5,1001,1724,1,1733,20101,0,0,-4,21202,-4,1,1,21101,0,1,2,21101,9,0,3,21101,0,1754,0,1105,1,1889,1206,1,1772,2201,-10,-4,1767,1001,1767,716,1767,20102,1,0,-3,1105,1,1790,21208,-4,-1,-9,1206,-9,1786,21201,-8,0,-3,1105,1,1790,22102,1,-7,-3,1001,1733,1,1796,20102,1,0,-2,21208,-2,-1,-9,1206,-9,1812,21202,-8,1,-1,1106,0,1816,22101,0,-7,-1,21208,-5,1,-9,1205,-9,1837,21208,-5,2,-9,1205,-9,1844,21208,-3,0,-1,1106,0,1855,22202,-3,-1,-1,1106,0,1855,22201,-3,-1,-1,22107,0,-1,-1,1106,0,1855,21208,-2,-1,-9,1206,-9,1869,22102,1,-1,-8,1105,1,1873,21202,-1,1,-7,21201,-6,1,-6,1106,0,1708,22102,1,-8,-10,109,-11,2106,0,0,109,7,22207,-6,-5,-3,22207,-4,-6,-2,22201,-3,-2,-1,21208,-1,0,-6,109,-7,2105,1,0,0,109,5,1202,-2,1,1912,21207,-4,0,-1,1206,-1,1930,21102,0,1,-4,22101,0,-4,1,21201,-3,0,2,21101,1,0,3,21102,1,1949,0,1105,1,1954,109,-5,2106,0,0,109,6,21207,-4,1,-1,1206,-1,1977,22207,-5,-3,-1,1206,-1,1977,21201,-5,0,-5,1105,1,2045,21202,-5,1,1,21201,-4,-1,2,21202,-3,2,3,21102,1996,1,0,1106,0,1954,21202,1,1,-5,21101,1,0,-2,22207,-5,-3,-1,1206,-1,2015,21101,0,0,-2,22202,-3,-2,-3,22107,0,-4,-1,1206,-1,2037,22101,0,-2,1,21101,0,2037,0,105,1,1912,21202,-3,-1,-3,22201,-5,-3,-5,109,-6,2105,1,0")

(defn encode [s]
  (map int (str s "\n")))

(defn decode [ins]
  (str/replace
    (->> ins (map char) (str/join))
    "\n"
    "  "))

(defn set-input [input cpu]
  (assoc cpu ::cpu/input input))

(def possible-instructions-1
  (for [y  [\J \T]
        op ["AND" "NOT" "OR"]
        x  [\J \T \A \B \C \D]]
    (encode (str/join " " [op x y]))))

(def possible-instructions-2
  (for [op ["AND" "NOT" "OR"]
        x  [\J \T \A \B \C \D \E \F \G \H \I]
        y  [\J \T]]
    (encode (str/join " " [op x y]))))

(defn move [cpu instructions mode]
  (->> cpu
    (set-input (concat instructions (encode (-> mode name str/upper-case))))
    (iterate cpu/step)
    (rest)
    (take-while #(not= ::cpu/halt %))
    (map ::cpu/output)))

(defn please-be-result [outputs]
  (let [maybe-result (last outputs)]
    (try
      (char maybe-result)
      ;(->> outputs (map char) (str/join))
      (catch IllegalArgumentException e
        maybe-result))))


(defn last-line [outs]
  (->> outs (map char) (str/join) (str/split-lines) (remove str/blank?) (last)))
(defn print-entire-output [outs]
  (->> outs (map char) (str/join) println))

#_
(time
  (let [cpu      (make input)
        limit    15
        possible possible-instructions-1
        mode     :WALK ;:RUN
        clones   (fn [len seen ins]
                   (->> possible
                     (map (partial concat ins))
                     (map vector (repeat len) (repeat seen))))]
    (loop [SEEN  #{}
           queue (->> (clones 1 #{} [])
                   ;(into [])
                   (into (PersistentQueue/EMPTY)))]
      ;(prn (count queue))
      (if (empty? queue)
        "¯\\_(ツ)_/¯"
        (let [[len seen ins] (peek queue)
              _ (prn (decode ins))
              queue      (pop queue)
              outs       (move cpu ins mode)
              x          (please-be-result outs)
              line       (last-line outs)
              _          (when-not (SEEN line) (println line))
              SEEN       (conj SEEN line)]
          (cond
            (number? x)   x
            (= limit len) (recur SEEN queue)
            (seen line)   (recur SEEN queue)
            :else         (recur SEEN (into queue
                                          (clones (inc len) (conj seen line) ins)))))))))

(defn pew [mode ins]
  (->> (make input)
    (set-input (encode (str ins "\n" mode)))
    (iterate cpu/step)
    (rest)
    (take-while #(not= ::cpu/halt %))
    (map ::cpu/output)
    (last-line)))

(defn WALK [ins] (pew "WALK" ins))


(defn all [ ins]
  (->> (make input)
    (set-input (encode (str ins "\nWALK")))
    (iterate cpu/step)
    (rest)
    (take-while #(not= ::cpu/halt %))
    (map ::cpu/output)
    (print-entire-output)))

(all
  "OR D J
   OR A J
   OR B J
   OR C J")

;#####@###########
;#####@..#########
;#####.@.#########
;#####..@#########
;#####@#..########
;#####.#@.########
;#####.#.@########

;#####.###########
;#####.#..########
;#####...#########

; 1) should I jump?
; 2) can I land?


(let [AND "AND" OR "OR" NOT "NOT"
      => (fn [x & vs]
           (loop [[v & vs] vs
                  x x]
             (cond
               (nil? v)    x
               (string? x) (recur vs (str x "\n" v " " (last x)))
               (char? x)   (recur vs (str v " " x)))))]

  (let [hole1? "NOT A T"
        hole2? "NOT B T"
        hole3? "NOT C T"
        still-must-jump? "OR  T J"

        can-land? "AND D J"
        must-jump? ""]
    (println
      (str/join "\n"
        [can-land?
         still-must-jump?
         hole1?
         still-must-jump?
         hole2?
         still-must-jump?
         hole3?]))))
    ;[AND must-jump? can-land?]
    ;(=> \J hole1? hole2? hole3?)))

(declare t j a b c d e f g h)
(def P1
  '(and d ;can land
     (or ; need to jump
       (not a) (not b) (not c))))

(defn postwalk-demo2
  [form]
  (walk/postwalk
    (fn [x]
      (when-not (symbol? x)
        (print "Walked: ") (prn x))
      x)
    form))

;; or can overwrite
;; and has to remember

;(walk/postwalk-demo P1)
;(postwalk-demo2 P1)



(defn NOT [x]
  [(str "NOT " (str/upper-case x) " T")])

(defn IDENTITY [x]
  (concat (NOT x) (NOT \T)))

(declare OP)

'(or a b c)
"OR a t, OR b t, OR c t"

(defn OR [forms]
  (mapcat concat
    (map OP forms)
    (repeat ["OR T J"])))

(OR '(a b c))
(OR '((not a) (not b) (not c)))

(defn AND [forms]
  (mapcat concat
    (map OP forms)
    (repeat ["AND T J"])))

(def TRUE-T "NOT A T\nOR  A T")
(def TRUE-J "NOT A J\nOR  A J")

(defn OP* [form]
  (println
    (str/join "\n"
      (cons
        TRUE-J
        (OP form)))))


(OP* '(or (not a) (not b) (not c)))


(OP*
  '(and
     (or
       (not a) (not b) (not c))
     d))

(defn OP [form]
  (if (symbol? form)
    (IDENTITY form)
    (case (first form)
      not (NOT (second form))
      and (AND (rest form))
      or  (OR  (rest form)))))

(OP 'a)

'(and
   d
   (or (not a) (not b) (not c))
   (or h ;;can land again
     (not e))) ;;can walk

(def p1
  "NOT A J
   NOT B T
   OR  T J
   NOT C T
   OR  T J
   AND D J") ;; I can land and I need to jump


(WALK p1)
(WALK
  "AND D J")

(WALK "AND D J\nOR T J\nNOT A T\nOR T J\nNOT B T\nOR T J\nNOT C T")

;#####.#.##.#.####
;#####..#.#.##.###
;#####..#.###.####

[\_ \A \B \C \D \E \F \G \H \I]
; ^           v           ^  ^
; ^           ^           v
; ^           v  ^           v
;; D is always landing
;; should jump if can D, and can walk or jump again
(defn RUN  [ins] (pew "RUN" (str p1 "\n" ins)))
(RUN
  ; J is whether I need to jump on a tile before A, not should/can jump on D
  (str/join "\n"
               ;; J=true at this point
    ["NOT E T" ;; must jump immediately? (next tile is a hole)
     "NOT T T" ;; next tile is not hole?
     "OR  H T" ;; can step on next, or jump immediately
     "AND T J"])) ;; jump if had to jump (yes, after p1), but only if have landing
