(ns adventofcode.2018.day10
  (:require [clojure.edn :as edn]
            [clojure.string :as str]))

(def input "position=<-10351, -10360> velocity=< 1,  1>\nposition=< 52528,  31539> velocity=<-5, -3>\nposition=<-31270, -20838> velocity=< 3,  2>\nposition=< 52486, -10365> velocity=<-5,  1>\nposition=< 31558,  10589> velocity=<-3, -1>\nposition=<-52253,  21064> velocity=< 5, -2>\nposition=<-10354,  42015> velocity=< 1, -4>\nposition=<-41798,  42013> velocity=< 4, -4>\nposition=<-52253, -52267> velocity=< 5,  5>\nposition=< 31550, -41793> velocity=<-3,  4>\nposition=<-31290,  10591> velocity=< 3, -1>\nposition=< 31542, -10363> velocity=<-3,  1>\nposition=< 21117,  52487> velocity=<-2, -5>\nposition=< 21074, -41796> velocity=<-2,  4>\nposition=< 10619, -20840> velocity=<-1,  2>\nposition=< 31562,  52495> velocity=<-3, -5>\nposition=< 31586, -20844> velocity=<-3,  2>\nposition=<-20837,  42020> velocity=< 2, -4>\nposition=< 52486,  10589> velocity=<-5, -1>\nposition=< 52518, -31313> velocity=<-5,  3>\nposition=<-31286,  21063> velocity=< 3, -2>\nposition=< 31536, -41793> velocity=<-3,  4>\nposition=< 52523, -52268> velocity=<-5,  5>\nposition=<-20830, -10364> velocity=< 2,  1>\nposition=< 31568,  10587> velocity=<-3, -1>\nposition=< 21116, -10369> velocity=<-2,  1>\nposition=< 31558,  21060> velocity=<-3, -2>\nposition=< 21074,  42013> velocity=<-2, -4>\nposition=< 21114,  42015> velocity=<-2, -4>\nposition=<-52231, -52269> velocity=< 5,  5>\nposition=<-20819,  21064> velocity=< 2, -2>\nposition=< 42062, -10365> velocity=<-4,  1>\nposition=<-31271,  52496> velocity=< 3, -5>\nposition=< 10598, -41791> velocity=<-1,  4>\nposition=< 42038, -10361> velocity=<-4,  1>\nposition=< 42047, -52273> velocity=<-4,  5>\nposition=<-31290,  10591> velocity=< 3, -1>\nposition=<-41742,  21066> velocity=< 4, -2>\nposition=< 31590, -41790> velocity=<-3,  4>\nposition=<-31314, -10360> velocity=< 3,  1>\nposition=< 42011,  42011> velocity=<-4, -4>\nposition=<-10366,  10583> velocity=< 1, -1>\nposition=<-10349, -20844> velocity=< 1,  2>\nposition=<-20806, -41790> velocity=< 2,  4>\nposition=< 42018,  52494> velocity=<-4, -5>\nposition=<-41746, -52271> velocity=< 4,  5>\nposition=< 52507,  10590> velocity=<-5, -1>\nposition=<-20793,  52487> velocity=< 2, -5>\nposition=< 21106,  21067> velocity=<-2, -2>\nposition=< 42047,  21063> velocity=<-4, -2>\nposition=<-20794, -31321> velocity=< 2,  3>\nposition=< 10611, -20838> velocity=<-1,  2>\nposition=<-41742, -41793> velocity=< 4,  4>\nposition=<-41747, -31321> velocity=< 4,  3>\nposition=<-20838, -10367> velocity=< 2,  1>\nposition=<-52274, -31314> velocity=< 5,  3>\nposition=< 52526, -20841> velocity=<-5,  2>\nposition=< 21066,  42017> velocity=<-2, -4>\nposition=<-10354, -41789> velocity=< 1,  4>\nposition=<-10326,  31535> velocity=< 1, -3>\nposition=< 42062, -20837> velocity=<-4,  2>\nposition=<-10346,  31539> velocity=< 1, -3>\nposition=< 21090,  52494> velocity=<-2, -5>\nposition=<-20829, -41797> velocity=< 2,  4>\nposition=< 21077,  21068> velocity=<-2, -2>\nposition=<-20814, -20845> velocity=< 2,  2>\nposition=< 21103, -10366> velocity=<-2,  1>\nposition=<-52250, -52266> velocity=< 5,  5>\nposition=<-10341,  31537> velocity=< 1, -3>\nposition=< 10603, -20839> velocity=<-1,  2>\nposition=<-41782, -20839> velocity=< 4,  2>\nposition=<-52226, -41790> velocity=< 5,  4>\nposition=< 31586, -41791> velocity=<-3,  4>\nposition=<-20790,  31543> velocity=< 2, -3>\nposition=< 31575, -41793> velocity=<-3,  4>\nposition=< 52490,  42011> velocity=<-5, -4>\nposition=<-41741,  31535> velocity=< 4, -3>\nposition=<-10318, -31316> velocity=< 1,  3>\nposition=< 10585, -10369> velocity=<-1,  1>\nposition=< 10606,  10591> velocity=<-1, -1>\nposition=< 52510,  31544> velocity=<-5, -3>\nposition=<-20846, -20840> velocity=< 2,  2>\nposition=< 21058,  52490> velocity=<-2, -5>\nposition=<-10367, -31321> velocity=< 1,  3>\nposition=<-41774,  21062> velocity=< 4, -2>\nposition=<-52254,  52492> velocity=< 5, -5>\nposition=< 10640, -10360> velocity=<-1,  1>\nposition=<-20846, -20839> velocity=< 2,  2>\nposition=<-10341, -52272> velocity=< 1,  5>\nposition=<-10330, -10363> velocity=< 1,  1>\nposition=<-10353, -41797> velocity=< 1,  4>\nposition=<-41782,  52494> velocity=< 4, -5>\nposition=<-41761,  10584> velocity=< 4, -1>\nposition=< 42018, -10369> velocity=<-4,  1>\nposition=<-10365,  42014> velocity=< 1, -4>\nposition=<-10318,  42018> velocity=< 1, -4>\nposition=<-52242, -31315> velocity=< 5,  3>\nposition=< 21095, -20838> velocity=<-2,  2>\nposition=< 21058, -10368> velocity=<-2,  1>\nposition=<-31282,  10584> velocity=< 3, -1>\nposition=<-31322, -31321> velocity=< 3,  3>\nposition=< 21117, -52273> velocity=<-2,  5>\nposition=< 31543,  52496> velocity=<-3, -5>\nposition=< 52538,  21066> velocity=<-5, -2>\nposition=< 10582,  52492> velocity=<-1, -5>\nposition=<-41777, -52265> velocity=< 4,  5>\nposition=<-10357, -52264> velocity=< 1,  5>\nposition=< 42053,  31535> velocity=<-4, -3>\nposition=< 42028, -20845> velocity=<-4,  2>\nposition=<-10326, -20845> velocity=< 1,  2>\nposition=< 10638,  10585> velocity=<-1, -1>\nposition=< 31591,  42020> velocity=<-3, -4>\nposition=< 52505, -52273> velocity=<-5,  5>\nposition=<-10338,  21059> velocity=< 1, -2>\nposition=<-10341,  42012> velocity=< 1, -4>\nposition=< 31566,  31544> velocity=<-3, -3>\nposition=<-52215, -10360> velocity=< 5,  1>\nposition=< 52526,  21060> velocity=<-5, -2>\nposition=< 10638,  10589> velocity=<-1, -1>\nposition=< 42034,  10584> velocity=<-4, -1>\nposition=< 31553, -52264> velocity=<-3,  5>\nposition=<-10353, -10360> velocity=< 1,  1>\nposition=<-20795, -31321> velocity=< 2,  3>\nposition=<-20830,  42014> velocity=< 2, -4>\nposition=< 10606, -31319> velocity=<-1,  3>\nposition=<-20814, -41796> velocity=< 2,  4>\nposition=<-52239,  31539> velocity=< 5, -3>\nposition=< 31586,  10586> velocity=<-3, -1>\nposition=<-10318,  31540> velocity=< 1, -3>\nposition=< 10643,  10584> velocity=<-1, -1>\nposition=< 42042,  10592> velocity=<-4, -1>\nposition=<-41749, -10360> velocity=< 4,  1>\nposition=<-41774,  52490> velocity=< 4, -5>\nposition=< 31590,  31536> velocity=<-3, -3>\nposition=<-10350, -41789> velocity=< 1,  4>\nposition=< 42047, -52264> velocity=<-4,  5>\nposition=< 10631, -20836> velocity=<-1,  2>\nposition=< 52542, -52270> velocity=<-5,  5>\nposition=<-52266,  21062> velocity=< 5, -2>\nposition=<-52258, -10363> velocity=< 5,  1>\nposition=<-41793,  42016> velocity=< 4, -4>\nposition=<-31277, -10367> velocity=< 3,  1>\nposition=< 52510, -52270> velocity=<-5,  5>\nposition=<-20842, -52269> velocity=< 2,  5>\nposition=< 10630, -10362> velocity=<-1,  1>\nposition=< 21082,  42011> velocity=<-2, -4>\nposition=< 21062,  52487> velocity=<-2, -5>\nposition=< 52503,  31544> velocity=<-5, -3>\nposition=< 10587, -31318> velocity=<-1,  3>\nposition=<-41766,  42015> velocity=< 4, -4>\nposition=<-52245, -52267> velocity=< 5,  5>\nposition=<-31277,  31538> velocity=< 3, -3>\nposition=<-10362,  10583> velocity=< 1, -1>\nposition=< 31593,  52496> velocity=<-3, -5>\nposition=< 21108, -20836> velocity=<-2,  2>\nposition=<-52266,  52490> velocity=< 5, -5>\nposition=< 21094, -10365> velocity=<-2,  1>\nposition=< 31575, -20841> velocity=<-3,  2>\nposition=<-31285,  42013> velocity=< 3, -4>\nposition=< 10625,  10583> velocity=<-1, -1>\nposition=<-10313, -52264> velocity=< 1,  5>\nposition=< 42036,  42014> velocity=<-4, -4>\nposition=< 31561,  10589> velocity=<-3, -1>\nposition=< 10624, -41793> velocity=<-1,  4>\nposition=<-52274, -31314> velocity=< 5,  3>\nposition=< 10587, -10361> velocity=<-1,  1>\nposition=< 31545, -41788> velocity=<-3,  4>\nposition=< 21063, -20838> velocity=<-2,  2>\nposition=<-52234, -41790> velocity=< 5,  4>\nposition=< 52523,  52490> velocity=<-5, -5>\nposition=< 42047, -10361> velocity=<-4,  1>\nposition=<-41774,  10587> velocity=< 4, -1>\nposition=<-52274,  42013> velocity=< 5, -4>\nposition=< 21106,  52494> velocity=<-2, -5>\nposition=< 52546, -10360> velocity=<-5,  1>\nposition=< 10633, -20836> velocity=<-1,  2>\nposition=<-10370, -20844> velocity=< 1,  2>\nposition=< 42030, -20837> velocity=<-4,  2>\nposition=< 21075,  42020> velocity=<-2, -4>\nposition=<-20820,  21063> velocity=< 2, -2>\nposition=< 10622,  21062> velocity=<-1, -2>\nposition=< 42066,  21065> velocity=<-4, -2>\nposition=< 52528, -52273> velocity=<-5,  5>\nposition=< 10610, -31313> velocity=<-1,  3>\nposition=<-10370, -20837> velocity=< 1,  2>\nposition=<-20788,  31535> velocity=< 2, -3>\nposition=<-20844,  52487> velocity=< 2, -5>\nposition=< 52510, -41788> velocity=<-5,  4>\nposition=< 31566, -20844> velocity=<-3,  2>\nposition=< 42047,  42012> velocity=<-4, -4>\nposition=<-31282, -41789> velocity=< 3,  4>\nposition=<-41742,  52493> velocity=< 4, -5>\nposition=<-41758,  21061> velocity=< 4, -2>\nposition=< 31579,  52489> velocity=<-3, -5>\nposition=<-31317,  42013> velocity=< 3, -4>\nposition=< 10611,  21059> velocity=<-1, -2>\nposition=< 31535, -41797> velocity=<-3,  4>\nposition=<-10338, -10365> velocity=< 1,  1>\nposition=<-52261,  31544> velocity=< 5, -3>\nposition=<-10368, -10369> velocity=< 1,  1>\nposition=<-41746,  10587> velocity=< 4, -1>\nposition=< 31571,  31535> velocity=<-3, -3>\nposition=<-20786, -10360> velocity=< 2,  1>\nposition=<-52258,  10584> velocity=< 5, -1>\nposition=< 52494, -31315> velocity=<-5,  3>\nposition=<-31317,  52492> velocity=< 3, -5>\nposition=< 31566, -20839> velocity=<-3,  2>\nposition=<-20825, -52264> velocity=< 2,  5>\nposition=< 10614, -10366> velocity=<-1,  1>\nposition=<-31277, -10366> velocity=< 3,  1>\nposition=< 52488,  42020> velocity=<-5, -4>\nposition=<-41795,  31539> velocity=< 4, -3>\nposition=< 52520, -52269> velocity=<-5,  5>\nposition=< 21070,  52496> velocity=<-2, -5>\nposition=<-31293, -31315> velocity=< 3,  3>\nposition=<-10314,  42016> velocity=< 1, -4>\nposition=<-20814, -31319> velocity=< 2,  3>\nposition=<-41777, -52265> velocity=< 4,  5>\nposition=< 21079,  52494> velocity=<-2, -5>\nposition=< 21101, -10369> velocity=<-2,  1>\nposition=<-20821,  31537> velocity=< 2, -3>\nposition=<-20802,  21063> velocity=< 2, -2>\nposition=< 52531,  21060> velocity=<-5, -2>\nposition=<-20828,  52496> velocity=< 2, -5>\nposition=< 10590,  52488> velocity=<-1, -5>\nposition=< 52511,  31537> velocity=<-5, -3>\nposition=<-20817, -20839> velocity=< 2,  2>\nposition=< 21082,  10587> velocity=<-2, -1>\nposition=< 52530, -31321> velocity=<-5,  3>\nposition=<-41782, -41795> velocity=< 4,  4>\nposition=< 42034, -10360> velocity=<-4,  1>\nposition=<-41765, -20841> velocity=< 4,  2>\nposition=<-41788,  42020> velocity=< 4, -4>\nposition=< 42047,  31544> velocity=<-4, -3>\nposition=<-31314, -41793> velocity=< 3,  4>\nposition=< 10611,  31543> velocity=<-1, -3>\nposition=< 10611, -10360> velocity=<-1,  1>\nposition=< 31542, -41795> velocity=<-3,  4>\nposition=< 42050, -52269> velocity=<-4,  5>\nposition=< 31592,  10592> velocity=<-3, -1>\nposition=<-10317,  21059> velocity=< 1, -2>\nposition=< 52544, -31321> velocity=<-5,  3>\nposition=<-31282,  52488> velocity=< 3, -5>\nposition=<-52242,  31535> velocity=< 5, -3>\nposition=<-31290, -52271> velocity=< 3,  5>\nposition=<-52256, -31312> velocity=< 5,  3>\nposition=<-31306, -10364> velocity=< 3,  1>\nposition=< 21077, -20840> velocity=<-2,  2>\nposition=< 10598, -10365> velocity=<-1,  1>\nposition=<-41761, -20837> velocity=< 4,  2>\nposition=< 31571, -31315> velocity=<-3,  3>\nposition=<-41772,  10587> velocity=< 4, -1>\nposition=<-41750,  21067> velocity=< 4, -2>\nposition=< 31539, -31314> velocity=<-3,  3>\nposition=< 10619,  10590> velocity=<-1, -1>\nposition=<-10341,  31539> velocity=< 1, -3>\nposition=<-20814, -52268> velocity=< 2,  5>\nposition=<-20814,  21066> velocity=< 2, -2>\nposition=< 10614,  10585> velocity=<-1, -1>\nposition=<-41774,  21061> velocity=< 4, -2>\nposition=< 10622, -20840> velocity=<-1,  2>\nposition=<-41793,  31541> velocity=< 4, -3>\nposition=<-20841,  21061> velocity=< 2, -2>\nposition=< 10622, -41792> velocity=<-1,  4>\nposition=<-31317,  10590> velocity=< 3, -1>\nposition=<-10341, -10369> velocity=< 1,  1>\nposition=<-31322,  42018> velocity=< 3, -4>\nposition=<-20838, -20840> velocity=< 2,  2>\nposition=<-20844, -10360> velocity=< 2,  1>\nposition=< 10634, -41789> velocity=<-1,  4>\nposition=< 42010,  52496> velocity=<-4, -5>\nposition=<-41777,  31542> velocity=< 4, -3>\nposition=< 10602, -31316> velocity=<-1,  3>\nposition=< 52538,  42015> velocity=<-5, -4>\nposition=< 21083,  52489> velocity=<-2, -5>\nposition=< 21066,  21060> velocity=<-2, -2>\nposition=<-20805, -41793> velocity=< 2,  4>\nposition=< 21087, -41788> velocity=<-2,  4>\nposition=< 52514,  21066> velocity=<-5, -2>\nposition=< 52515, -41790> velocity=<-5,  4>\nposition=< 21066, -31313> velocity=<-2,  3>\nposition=<-41753, -52272> velocity=< 4,  5>\nposition=< 10587,  31537> velocity=<-1, -3>\nposition=<-20814, -31312> velocity=< 2,  3>\nposition=< 10627,  52488> velocity=<-1, -5>\nposition=< 52523, -20837> velocity=<-5,  2>\nposition=<-41774,  52492> velocity=< 4, -5>\nposition=<-52269,  21062> velocity=< 5, -2>\nposition=<-31282,  10583> velocity=< 3, -1>\nposition=<-31322,  21059> velocity=< 3, -2>\nposition=<-31302, -20845> velocity=< 3,  2>\nposition=< 52526, -10360> velocity=<-5,  1>\nposition=< 31536, -41793> velocity=<-3,  4>\nposition=<-20809,  42015> velocity=< 2, -4>\nposition=< 31542, -52271> velocity=<-3,  5>\nposition=< 52486, -10363> velocity=<-5,  1>\nposition=<-10311,  42011> velocity=< 1, -4>\nposition=<-41761,  10584> velocity=< 4, -1>\nposition=< 42050, -41789> velocity=<-4,  4>\nposition=< 52515, -20838> velocity=<-5,  2>\nposition=< 21058, -20841> velocity=<-2,  2>\nposition=< 31586, -31320> velocity=<-3,  3>\nposition=<-41766,  31541> velocity=< 4, -3>\nposition=<-41769,  31540> velocity=< 4, -3>\nposition=<-52250, -31320> velocity=< 5,  3>\nposition=< 52543,  42020> velocity=<-5, -4>\nposition=< 42052, -20841> velocity=<-4,  2>\nposition=< 52499,  31544> velocity=<-5, -3>\nposition=< 52515, -20842> velocity=<-5,  2>\nposition=< 31537,  31535> velocity=<-3, -3>\nposition=< 52490, -20836> velocity=<-5,  2>\nposition=< 52518,  21066> velocity=<-5, -2>\nposition=< 31590,  52492> velocity=<-3, -5>\nposition=<-10310, -10369> velocity=< 1,  1>\nposition=< 31582,  42019> velocity=<-3, -4>\nposition=<-10368, -52269> velocity=< 1,  5>\nposition=< 31539, -20844> velocity=<-3,  2>\nposition=< 10590, -41796> velocity=<-1,  4>\nposition=< 52538, -20837> velocity=<-5,  2>\nposition=<-41737,  31543> velocity=< 4, -3>\nposition=<-52271, -31312> velocity=< 5,  3>\nposition=< 31559,  42012> velocity=<-3, -4>\nposition=<-10330,  31542> velocity=< 1, -3>\nposition=<-41797,  31539> velocity=< 4, -3>\nposition=< 10639,  52487> velocity=<-1, -5>\nposition=< 31566, -41792> velocity=<-3,  4>\nposition=< 42068,  42020> velocity=<-4, -4>\nposition=< 42066, -20840> velocity=<-4,  2>\nposition=< 10583, -52264> velocity=<-1,  5>\nposition=< 21066,  31543> velocity=<-2, -3>\nposition=< 10606, -41790> velocity=<-1,  4>\nposition=< 42042, -41793> velocity=<-4,  4>\nposition=< 42038, -31314> velocity=<-4,  3>\nposition=<-20806, -20842> velocity=< 2,  2>\nposition=< 42010, -10366> velocity=<-4,  1>\nposition=<-41795, -20841> velocity=< 4,  2>\nposition=< 10633,  21059> velocity=<-1, -2>\nposition=< 52527, -10369> velocity=<-5,  1>\nposition=< 21109, -31312> velocity=<-2,  3>\nposition=<-52224,  52496> velocity=< 5, -5>\nposition=< 10583,  10592> velocity=<-1, -1>")
(def test-input "position=< 9,  1> velocity=< 0,  2>\nposition=< 7,  0> velocity=<-1,  0>\nposition=< 3, -2> velocity=<-1,  1>\nposition=< 6, 10> velocity=<-2, -1>\nposition=< 2, -4> velocity=< 2,  2>\nposition=<-6, 10> velocity=< 2, -2>\nposition=< 1,  8> velocity=< 1, -1>\nposition=< 1,  7> velocity=< 1,  0>\nposition=<-3, 11> velocity=< 1, -2>\nposition=< 7,  6> velocity=<-1, -1>\nposition=<-2,  3> velocity=< 1,  0>\nposition=<-4,  3> velocity=< 2,  0>\nposition=<10, -3> velocity=<-1,  1>\nposition=< 5, 11> velocity=< 1, -2>\nposition=< 4,  7> velocity=< 0, -1>\nposition=< 8, -2> velocity=< 0,  1>\nposition=<15,  0> velocity=<-2,  0>\nposition=< 1,  6> velocity=< 1,  0>\nposition=< 8,  9> velocity=< 0, -1>\nposition=< 3,  3> velocity=<-1,  1>\nposition=< 0,  5> velocity=< 0, -1>\nposition=<-2,  2> velocity=< 2,  0>\nposition=< 5, -2> velocity=< 1,  2>\nposition=< 1,  4> velocity=< 2,  1>\nposition=<-2,  7> velocity=< 2, -2>\nposition=< 3,  6> velocity=<-1, -1>\nposition=< 5,  0> velocity=< 1,  0>\nposition=<-6,  0> velocity=< 2,  0>\nposition=< 5,  9> velocity=< 1, -2>\nposition=<14,  7> velocity=<-2,  0>\nposition=<-3,  6> velocity=< 2, -1>")

(defn parse-line [line]
  (let [[x y dx dy] (->> (str/replace line " " "")
                      (re-matches #"position=<(-?\d+?),(-?\d+?)>velocity=<(-?\d+?),(-?\d+?)>")
                      (rest)
                      (map edn/read-string))]
    [[x y] [dx dy]]))



(defn frame [points]
  (reduce
    (fn [[xs ys x1 x2 y1 y2] [px py]]
      [(conj xs px) (conj ys py)
       (min x1 px) (max x2 px)
       (min y1 py) (max y2 py)])
    [#{} #{} 0 0 0 0]
    points))


(defn set-stats [points]
  (let [[xs ys & [x1 x2 y1 y2 :as corners]] (frame points)]
    {:points  points
     :corners corners
     :height  (- y2 y1)
     :width   (- x2 x1)
     :count   (+ (count xs) (count ys))}))


(defn make-canvas [{:keys [:points :corners]}]
  (let [[x1 x2 y1 y2] corners
        blanks (for [x (range x1 (inc x2))
                     y (range y1 (inc y2))]
                 [x y])
        canvas (as-> blanks $
                 (reduce #(assoc! %1 %2 \.) (transient {}) $)
                 (reduce #(assoc! %1 %2 \#) $ points)
                 (persistent! $))]
    (->> canvas
      (sort-by first)
      (partition-by ffirst)
      (map #(map second %))
      (reverse))))


(defn draw [filepath stats]
  (->> stats
    (make-canvas)
    (map str/join)
    (str/join "\n")
    (spit filepath)))


(defn tick [velocities points]
  (map (partial mapv +) points velocities))

(defn shrinking? [stats]
  (->> stats (map :height) (apply >=)))


(defn changes [input]
  (let [lines      (->> input
                     (str/split-lines)
                     (map parse-line))
        points     (map first lines)
        velocities (map second lines)]
    (->> points
      (iterate (partial tick velocities))
      (map set-stats)
      (partition 2 1))))


(defn f1 [input]
  (->> input
    (changes)
    (drop-while shrinking?)
    (ffirst)
    (draw "/tmp/day10.txt")))


(defn f2 [input]
  (->> input
    (changes)
    (take-while shrinking?)
    (count)))

(f1 input)  ;; BLGNHPJC

(assert (= (f2 test-input) 3))
(assert (= (f2 input) 10476))