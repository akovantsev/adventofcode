(ns adventofcode.2018.day22
  (:require [clojure.set :as set]))


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
    (for [x (range (-> XMAX inc (+ 150)))
          y (range (-> YMAX inc (+ 150)))]
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



(def EQUIP-REQUIRED
  {:geotype/rocky  #{\G \T}
   :geotype/wet    #{\G \N}
   :geotype/narrow #{\T \N}})

(def ALL-XYZ
  (->> CAVE
    (reduce-kv
      (fn rf [!s [x y] {::keys [type]}]
        (->> type
          EQUIP-REQUIRED
          (map (partial conj [x y]))
          (reduce conj! !s)))
      (transient #{}))
    (persistent!)))


(defn next-xyzs [[x y z]]
  [,,,,,,,,,,,,, [x (dec y) z] ,,,,,,,,,,,
   [(dec x) y z] ,,,,,,,,,,,,, [(inc x) y z]
   ,,,,,,,,,,,,, [x (inc y) z] ,,,,,,,,,,,])


(defn switch [[x y z]]
  (->> z
    (disj #{\T \N \G})
    (map (fn [z] [x y z]))))

(def TARGET-XYZ [XMAX YMAX \T])
(def START-XYZ [0 0 \T])

(def intos (fnil into #{}))


(defn f2 []
  ;; thanks @drowsy for `sets` approach
  ;; thanks @gklijs for "can't switch to torch while in water" detail.
  (time
    (loop [cost 0
           left ALL-XYZ
           done {0 #{START-XYZ}}]
      (if (-> cost done (contains? TARGET-XYZ))
        cost
        (let [xyzs       (get done cost)
              cheap-xyzs (->> xyzs
                           (mapcat next-xyzs)
                           (filter left))
              expen-xyzs (->> xyzs
                           (mapcat switch)
                           (filter ALL-XYZ)  ;; <-- @gklijs
                           (mapcat next-xyzs)
                           (filter left))
              done'      (-> done
                           (update (+ 1 cost) intos cheap-xyzs)
                           (update (+ 8 cost) intos expen-xyzs))
              left'      (set/difference
                           (set left)
                           (set xyzs)
                           (set cheap-xyzs))]
          (recur (inc cost) left' done'))))))



(assert (= 11575 (f1 CAVE)))

(let [r2 (f2)]
  (assert (< r2 2262))
  (assert (not= r2 1092))
  (assert (not= r2 1093))
  (assert (not= r2 1064))
  (assert (not= r2 1065))
  (assert (= r2 1068)))
