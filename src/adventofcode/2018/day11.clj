(ns adventofcode.2018.day11
  (:require [clojure.edn :as edn]
            [clojure.string :as str]))

;; as of this commit, CACHE calculation takes forever:
;; "Elapsed time: 2826829.717059 msecs"
;; brb :D


(def N 5034)
(def WIDTH 300)

(defn power-level [n [x y]]
  (let [id (+ x 10)]
    (-> id (* y) (+ n) (* id) (str) (reverse) (nth 2 \0) (str) (edn/read-string) (or 0) (- 5))))


(power-level 8 [3 5]) ;;4
(power-level 57 [122,79]) ;; -5

(def points
  (for [x (range WIDTH)
        y (range WIDTH)]
    (list x y)))

(def GRID
  (->> points
    (reduce
      #(assoc! %1 %2 (power-level N %2))
      (transient{}))
    (persistent!)))



(defn coords-to-scores [xys]
  (->> xys (map GRID) (reduce + 0)))

;; part 1 leftovers:
#_
(defn window-score [size [xs ys]]
  {:score (->> (combo/cartesian-product xs ys) (coords-to-scores))
   :size  size
   :xy    [(first xs) (first ys)]})

#_
(defn foo [size]
  (time
    (let [xs (partition size 1 (range WIDTH))]
      (->> (combo/cartesian-product xs xs)
        (map (partial window-score size))
        (sort-by :score >)
        (first)))))


[235 63]  ;; part 1
[[229 251 16] 109]  ;; part 2

(set! *print-length* 100)
(set! *unchecked-math* true)

(defn calc-slow-score [cache n x y]
  (let [nx           (dec (+ n x))
        ny           (dec (+ n y))
        smaller      (get cache [x y (dec n)])
        extra-x-col  (coords-to-scores (map list (range x nx) (repeat ny)))
        extra-y-row  (coords-to-scores (map list (repeat nx) (range y ny)))
        bottom-right (get cache [nx ny 1])]
    (+ smaller extra-x-col extra-y-row bottom-right)))


(defn calc-fast-score [cache n k x y]
  (let [a  (/ n k)
        steps (case k
                2 [0 a]
                3 [0 a (* a 2)])
        coords (for [xs steps
                     ys steps]
                 [(+ x xs) (+ y ys) a])]
    (->> coords
      (map (partial get cache))
      (reduce +))))


(defn update-cache! [!cache n x y]
  (let [score (cond
                (= 1 n)           (get GRID (list x y))
                (zero? (rem n 2)) (calc-fast-score !cache n 2 x y)
                (zero? (rem n 3)) (calc-fast-score !cache n 3 x y)
                :else             (calc-slow-score !cache n x y))]
    (assoc! !cache [x y n] score)))

;#_
(def CACHE
  (time
    (let [limit      WIDTH
          into-cache #(apply update-cache! %1 %2)
          args       (for [n (range 1 (inc limit))
                           x (range limit)
                           y (range limit)
                           :when (and (<= x (- limit n)) (<= y (- limit n)))]
                       (do
                         (when (and (= x 0) (= y 0) (rem n 10))
                           (prn n))
                         [n x y]))]
      (->> args
        (reduce into-cache (transient {}))
        (persistent!)))))







(defn print-table [m filt-f sort-f part-f v-f]
  (->> m
    (filter filt-f)
    (sort-by sort-f)
    (partition-by part-f)
    (map (partial map #(->> % v-f (format "%6d"))))
    (map str/join)
    (map prn)
    (doall))
  nil)

(defn print-grid []
  (prn :grid)
  (print-table GRID
    identity
    #(-> % key reverse vec)
    #(-> % key second)
    val))

(defn print-cache [n]
  (prn :cache n)
  (print-table CACHE
    #(-> % key last (= n))
    #(-> % key reverse vec)
    #(-> % key second)
    val))

#_#_#_#_#_
(print-grid)
(print-cache 2)
(print-cache 3)
(print-cache 4)
(print-cache 5)