(ns adventofcode.y2020.day23
  (:require [clojure.string :as str])
  (:import [java.util Deque ArrayDeque]))


(set! *print-length* 100)


(defn clockwise! [^Deque q]
  (->> (.removeFirst q) (.addLast q))
  q)

(defn destination [curr MAX abc]
  (let [banned (set abc)]
    (or
      (->> curr dec (iterate dec) (drop-while banned) (take-while pos?) (first))
      (->> MAX  (iterate dec) (drop-while banned) (first)))))


(destination 1 9 [7 8 9])
(destination 1 9 [7 8])
(destination 5 9 [4 2])

(defn scroll-to [Q n]
  (while (not= n (first Q))
    (clockwise! Q))
  (clockwise! Q))

(defn solve [moves MAX numbers]
  (let [Q (new ArrayDeque)]
    (doseq [n numbers]
      (.addLast Q n))
    (dotimes [_ moves]
      (let [curr (first Q)
            _    (clockwise! Q)
            abc  [(.removeFirst Q) (.removeFirst Q) (.removeFirst Q)]
            dest (destination curr MAX abc)]
        ;; scroll until dest is at the end
        (scroll-to Q dest)
        ;; insert abc after dest
        (doseq [x abc]
          (.addLast Q x))
        ;; scroll until curr is at the end
        (scroll-to Q curr)
        (println curr abc dest (into [] Q))))
    Q))

(defn p1 [input]
  (let [numbers (->> input (map str) (map read-string))
        Q (solve 100 (reduce max numbers) numbers)]
    (scroll-to Q 1)
    (str/join (pop (into [] Q)))))



(def t "389125467")
(def i "364297581")

(p1 i)
"47382659"


;; p1
(defn play! [MAX moves cups]
  (dotimes [n moves]
    (let [A         cups
          cur       (aget A 0)
          a         (aget A cur)
          b         (aget A a)
          c         (aget A b)
          after-c   (aget A c)
          des       (destination cur MAX [a b c])
          after-des (aget A des)]
      ;;remove abc:
      (aset A cur after-c)
      ;; insert abc after dest
      (aset A des a)
      (aset A c after-des)
      ;; set new current as el 0:
      (aset A 0 after-c)
      (when (zero? (mod n 100))
        (prn [n (*
                  (->> 1 (get A))
                  (->> 1 (get A) (get A)))])))))



;p1
(time
  (let [numbers (->> i (map str) (mapv read-string))
        moves   100
        MAX     (reduce max numbers)
        A       (int-array (inc (count numbers)))]
    ;; init:
    ;; set current into 0th
    (aset A 0 (first numbers))
    (doseq [[x y] (partition 2 1 numbers)]
      (aset A x y))
    (aset A (peek numbers) (first numbers))
    (play! MAX moves A)
    (->> 1
      (iterate #(aget A %))
      (rest)
      (take-while #(not= % 1))
      (str/join))))

"47382659"

;p2

(let [numbers (->> i (map str) (mapv read-string))
      moves   1000;0000
      MAX     1000000
      A       (int-array (range 1 (inc (inc MAX))))]
  ;; init:
  ;; set current into 0th
  (aset A 0 (first numbers))
  (doseq [[x y] (partition 2 1 numbers)]
    (aset A x y))
  ;; last given number to first generated number:
  (aset A (peek numbers) (inc (reduce max numbers)))
  ;; last ever number to first given number:
  (aset A MAX (first numbers))
  ;(map-indexed vector (into [] A))))
  (time (play! MAX moves A))
  (*
    (->> 1 (get A))
    (->> 1 (get A) (get A))))


(set! *unchecked-math* true)

(time (dotimes [_ 1000000]
        (destination 2 1000 [1 1000 999])))

(defn destination2 [cur max a b c])


(->> 1
  (iterate [0 6 9 8 5 2 7 3 4 1])
  (rest)
  (take-while #(not= % 1)))

"Elapsed time: 912366.338705 msecs"

;;42271866720


(defn play2! [MAX moves cups]
  (dotimes [n moves]
    (let [M         cups
          cur       (get M 0)
          a         (get M cur)
          b         (get M a)
          c         (get M b)
          after-c   (get M c)
          des       (destination cur MAX [a b c])
          after-des (get M des)]
      ;;remove abc:
      (assoc! M cur after-c)
      ;; insert abc after dest
      (assoc! M des a)
      (assoc! M c after-des)
      ;; set new current as el 0:
      (assoc! M 0 after-c))))

(let [numbers (->> i (map str) (mapv read-string))
      moves   10000000
      MAX     1000000
      A       (transient (zipmap (range) (range 1 (inc (inc MAX)))))]
  ;; init:
  ;; set current into 0th
  (assoc! A 0 (first numbers))
  (doseq [[x y] (partition 2 1 numbers)]
    (assoc! A x y))
  ;; last given number to first generated number:
  (assoc! A (peek numbers) (inc (reduce max numbers)))
  ;; last ever number to first given number:
  (assoc! A MAX (first numbers))
  ;(map-indexed vector (into [] A))))
  (time (play2! MAX moves A))
  (*
    (->> 1 (get A))
    (->> 1 (get A) (get A))))

"Elapsed time: 28510.814642 msecs"


(defn play3! [MAX moves ^java.util.HashMap cups]
  (dotimes [n moves]
    (let [M         cups
          cur       (get M 0)
          a         (get M cur)
          b         (get M a)
          c         (get M b)
          after-c   (get M c)
          des       (destination cur MAX [a b c])
          after-des (get M des)]
      ;;remove abc:
      (.put M cur after-c)
      ;; insert abc after dest
      (.put M des a)
      (.put M c after-des)
      ;; set new current as el 0:
      (.put M 0 after-c))))

(let [numbers (->> i (map str) (mapv read-string))
      moves   10000000
      MAX     1000000
      A       (new java.util.HashMap)
      _       (doseq [n (range 1 (inc (inc MAX)))]
                (.put A (dec n) n))]
  ;; init:
  ;; set current into 0th
  (.put A 0 (first numbers))
  (doseq [[x y] (partition 2 1 numbers)]
    (.put A x y))
  ;; last given number to first generated number:
  (.put A (peek numbers) (inc (reduce max numbers)))
  ;; last ever number to first given number:
  (.put A MAX (first numbers))
  ;(map-indexed vector (into [] A))))
  (time (play3! MAX moves A))
  (*
    (->> 1 (.get A))
    (->> 1 (.get A) (.get A))))

(set! *warn-on-reflection* true)

"Elapsed time: 13782.338101 msecs"
;42271866720

