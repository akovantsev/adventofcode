(ns adventofcode.2018.day22)


(def DEPTH 11541)
(def XMAX 14)
(def YMAX 778)

(defn target? [xy] (= xy [XMAX YMAX]))

(defn erosion [geo] (-> geo (+ DEPTH) (mod 20183)))
(defn risk    [ero] (-> ero (mod 3)))

(def GEOTYPE [:geotype/rocky :geotype/wet :geotype/narrow]) ;;from risk

(defn geoidx [cave [x y :as xy]]
  (cond
    (zero? x)    (* y 48271)
    (zero? y)    (* x 16807)
    (target? xy) 0
    :else        (* (-> [(dec x) y] cave ::ero)
                    (-> [x (dec y)] cave ::ero))))

(defn set-stats! [!cave xy]
  (let [g (geoidx !cave xy)
        e (erosion g)
        r (risk e)]
    (assoc! !cave xy {::geo  g
                      ::ero  e
                      ::risk r
                      ::type (GEOTYPE r)})))

(def CAVE
  (->>
    (for [x (range (-> XMAX inc (+ 50)))
          y (range (-> YMAX inc (+ 50)))]
      [x y])
    (reduce set-stats! (transient {}))
    (persistent!)))


(defn f1 [cave]
  (->> cave
    (remove #(-> % key first  (> XMAX)))
    (remove #(-> % key second (> YMAX)))
    (map val)
    (map ::risk)
    (reduce + 0)))


(assert (= 11575 (f1 CAVE)))


(def EQUIP-REQUIRED
  {:geotype/rocky  #{\G \T}
   :geotype/wet    #{\G \N}
   :geotype/narrow #{\T \N}})

