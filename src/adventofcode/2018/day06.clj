(ns adventofcode.2018.day06
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))



(def input "152, 292\n163, 90\n258, 65\n123, 147\n342, 42\n325, 185\n69, 45\n249, 336\n92, 134\n230, 241\n74, 262\n241, 78\n299, 58\n231, 146\n239, 87\n44, 157\n156, 340\n227, 226\n212, 318\n194, 135\n235, 146\n171, 197\n160, 59\n218, 205\n323, 102\n290, 356\n244, 214\n174, 250\n70, 331\n288, 80\n268, 128\n359, 98\n78, 249\n221, 48\n321, 228\n52, 225\n151, 302\n183, 150\n142, 327\n172, 56\n72, 321\n225, 298\n265, 300\n86, 288\n78, 120\n146, 345\n268, 181\n243, 235\n262, 268\n40, 60")
;(def input "1, 1\n1, 6\n8, 3\n3, 4\n5, 5\n8, 9")

(def center-points
  (->> (str/split input #"(, )|\n")
    (map edn/read-string)
    (partition 2)))


(def MINX (->> center-points (map first) (reduce min)))
(def MINY (->> center-points (map second) (reduce min)))

(def MAXX (->> center-points (map first) (reduce max)))
(def MAXY (->> center-points (map second) (reduce max)))


(def all-points
  (for [x (range MINX (inc MAXX))
        y (range MINX (inc MAXY))]
    [x y]))

(defn city-distance
  [[^int x1 ^int y1]
   [^int x2 ^int y2]]
  (+ (Math/abs (- x2 x1))
     (Math/abs (- y1 y2))))

(defn border? [[^int x ^int y]]
  (or (#{MINX MAXX} x) (#{MINY MAXY} y)))


(defn closest [p points]
  (let [close (->> points
                (map city-distance (repeat p))
                (map vector points)
                (sort-by second <)
                (partition-by second)
                (first))]
    (when (-> close count (= 1))
      (ffirst close))))


(defn f1 [center-points all-points]
  (let [zinc (fnil inc 0)
        rf   (fn rf1 [[infi m] p]
               (let [center (closest p center-points)]
                 (cond
                   (nil? center) [infi m]
                   (border? p)   [(conj infi center) m]
                   :else         [infi (update m center zinc)])))
        [banned neighbors] (reduce rf [#{} {}] all-points)]
    (sort-by second >
      (apply dissoc neighbors banned))))



(defn f2 [center-points all-points]
  (->> all-points
    (sequence
      (comp
        (map #(map city-distance (repeat %) center-points))
        (map #(reduce + %))
        (filter #(< % 10000))))
    (count)))


(let [one (f1 center-points all-points)]
  (assert (not= one 3749))
  (assert (= one 3687)))

(assert (= 40134 (f2 center-points all-points)))