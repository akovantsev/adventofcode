(ns adventofcode.y2022.day15
  (:require
   [clojure.math.combinatorics :as combo]
   [adventofcode.utils :as u]))

(def sample "Sensor at x=2, y=18: closest beacon is at x=-2, y=15\nSensor at x=9, y=16: closest beacon is at x=10, y=16\nSensor at x=13, y=2: closest beacon is at x=15, y=3\nSensor at x=12, y=14: closest beacon is at x=10, y=16\nSensor at x=10, y=20: closest beacon is at x=10, y=16\nSensor at x=14, y=17: closest beacon is at x=10, y=16\nSensor at x=8, y=7: closest beacon is at x=2, y=10\nSensor at x=2, y=0: closest beacon is at x=2, y=10\nSensor at x=0, y=11: closest beacon is at x=2, y=10\nSensor at x=20, y=14: closest beacon is at x=25, y=17\nSensor at x=17, y=20: closest beacon is at x=21, y=22\nSensor at x=16, y=7: closest beacon is at x=15, y=3\nSensor at x=14, y=3: closest beacon is at x=15, y=3\nSensor at x=20, y=1: closest beacon is at x=15, y=3")
(def input "Sensor at x=13820, y=3995710: closest beacon is at x=1532002, y=3577287\nSensor at x=3286002, y=2959504: closest beacon is at x=3931431, y=2926694\nSensor at x=3654160, y=2649422: closest beacon is at x=3702627, y=2598480\nSensor at x=3702414, y=2602790: closest beacon is at x=3702627, y=2598480\nSensor at x=375280, y=2377181: closest beacon is at x=2120140, y=2591883\nSensor at x=3875726, y=2708666: closest beacon is at x=3931431, y=2926694\nSensor at x=3786107, y=2547075: closest beacon is at x=3702627, y=2598480\nSensor at x=2334266, y=3754737: closest beacon is at x=2707879, y=3424224\nSensor at x=1613400, y=1057722: closest beacon is at x=1686376, y=-104303\nSensor at x=3305964, y=2380628: closest beacon is at x=3702627, y=2598480\nSensor at x=1744420, y=3927424: closest beacon is at x=1532002, y=3577287\nSensor at x=3696849, y=2604845: closest beacon is at x=3702627, y=2598480\nSensor at x=2357787, y=401688: closest beacon is at x=1686376, y=-104303\nSensor at x=2127900, y=1984887: closest beacon is at x=2332340, y=2000000\nSensor at x=3705551, y=2604421: closest beacon is at x=3702627, y=2598480\nSensor at x=1783014, y=2978242: closest beacon is at x=2120140, y=2591883\nSensor at x=2536648, y=2910642: closest beacon is at x=2707879, y=3424224\nSensor at x=3999189, y=2989409: closest beacon is at x=3931431, y=2926694\nSensor at x=3939169, y=2382534: closest beacon is at x=3702627, y=2598480\nSensor at x=2792378, y=2002602: closest beacon is at x=2332340, y=2000000\nSensor at x=3520934, y=3617637: closest beacon is at x=2707879, y=3424224\nSensor at x=2614525, y=1628105: closest beacon is at x=2332340, y=2000000\nSensor at x=2828931, y=3996545: closest beacon is at x=2707879, y=3424224\nSensor at x=2184699, y=2161391: closest beacon is at x=2332340, y=2000000\nSensor at x=2272873, y=1816621: closest beacon is at x=2332340, y=2000000\nSensor at x=1630899, y=3675405: closest beacon is at x=1532002, y=3577287\nSensor at x=3683190, y=2619409: closest beacon is at x=3702627, y=2598480\nSensor at x=180960, y=185390: closest beacon is at x=187063, y=-1440697\nSensor at x=1528472, y=3321640: closest beacon is at x=1532002, y=3577287\nSensor at x=3993470, y=2905566: closest beacon is at x=3931431, y=2926694\nSensor at x=1684313, y=20931: closest beacon is at x=1686376, y=-104303\nSensor at x=2547761, y=2464195: closest beacon is at x=2120140, y=2591883\nSensor at x=3711518, y=845968: closest beacon is at x=3702627, y=2598480\nSensor at x=3925049, y=2897039: closest beacon is at x=3931431, y=2926694\nSensor at x=1590740, y=3586256: closest beacon is at x=1532002, y=3577287\nSensor at x=1033496, y=3762565: closest beacon is at x=1532002, y=3577287")

(def sample-row 10)
(def input-row 2000000)

(defn dist [[x1 y1] [x2 y2]]
  (+ (abs (- x1 x2))
     (abs (- y1 y2))))

(defn add-radius [[[sx sy :as s] [bx by :as b]]]
  [sx sy (dist s b)])

(defn cant-range [rowy [sx sy sr]]
  (let [rd (abs (- rowy sy))
        dx (- sr rd)]
    (when (<= 0 dx)
      [(- sx dx) (+ sx dx)])))

(defn merge-sorted-ranges [rs]
  (reduce
    (fn rf [res [R1 R2]]
      (let [[L1 L2] (peek res)]
        (cond
          (<= R1 L2) (-> res pop (conj [L1 (max L2 R2)]))
          (< L2 R1), (-> res (conj [R1 R2])))))
    [(first rs)]
    (rest rs)))

(defn triples [ss] (->> ss u/str->ints (partition 2) (partition 2) (map add-radius)))

(defn p1 [ss y]
  (->> ss
    (triples)
    (map (partial cant-range y))
    (remove nil?)
    (sort)
    (merge-sorted-ranges)
    (map (fn [[a b]] (- b a)))
    (reduce +)))

(assert (= 26 (p1 sample sample-row)))
(assert (= 5525990 (p1 input input-row)))




(def sample-max 20)
(def input-max 4000000)

(defn freq [[x y]] (-> x (* 4000000) (+ y)))

;; was gonna loop through all the outside points, but found this instead:
;; https://old.reddit.com/r/adventofcode/comments/zmcn64/2022_day_15_solutions/j0b90nr/
;; 1) find all intersections of slopes-just-outside-the-ranges
;; 2) find one that is outside of all ranges:

(defn p2 [ss MAX]
  (let [xyrs     (triples ss)
        outside? (fn [[x1 y1] [x2 y2 r]]
                   (< r (dist [x1 y1] [x2 y2])))]
    (->> xyrs
      (reduce
        (fn slopes [[as bs] [x y r]]
          [(conj as
             (-> y (- x) (+ r) inc)
             (-> y (- x) (- r) dec))
           (conj bs
             (-> y (+ x) (+ r) inc)
             (-> y (+ x) (- r) dec))])
        [[] []])
      (apply combo/cartesian-product)
      (map (fn intersect-xy [[a b]]
             [(-> b (- a) (/ 2))
              (-> b (+ a) (/ 2))]))
      (filter #(<= 0 (first %) MAX))
      (filter #(<= 0 (second %) MAX))
      (filter #(every? (partial outside? %) xyrs))
      (first)
      (freq))))


(assert (= 56000011 (p2 sample sample-max)))
(time (assert (= 11756174628223 (p2 input input-max))))
"Elapsed time: 10.970655 msecs"
