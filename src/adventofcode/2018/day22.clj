(ns adventofcode.2018.day22)


(def DEPTH 11541)
(def XMAX 14)
(def YMAX 778)

(defn target? [xy] (= xy [XMAX YMAX]))

(defn erosion [geo] (-> geo (+ DEPTH) (mod 20183)))
(defn risk    [ero] (-> ero (mod 3)))

(defn geoidx [cave [x y :as xy]]
  (cond
    (zero? x)    (* y 48271)
    (zero? y)    (* x 16807)
    (target? xy) 0
    :else        (let [[g1 e1] (get cave [(dec x) y])
                       [g2 e2] (get cave [x (dec y)])]
                   (* e1 e2))))


(def CAVE
  (->>
    (for [x (range (XMAX inc))
          y (range (inc YMAX))]
      [x y])
    (reduce
      (fn rf1 [m xy]
        (let [g (geoidx m xy)
              e (erosion g)]
          (assoc! m xy [g e])))
      (transient {}))
    (persistent!)))


(defn f1 [cave]
  (->> cave
    (remove #(-> % key first  (> XMAX)))
    (remove #(-> % key second (> YMAX)))
    (map val)
    (map second)
    (map risk)
    (reduce + 0)))


(assert (= 11575 (f1 CAVE)))


()