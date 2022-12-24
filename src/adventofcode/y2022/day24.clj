(ns adventofcode.y2022.day24
  (:require [clojure.string :as str]
            [com.akovantsev.blet.core :refer [blet blet!]]
            [adventofcode.utils :as u]))

(def sample "#.######\n#>>.<^<#\n#.<..<<#\n#>v.><>#\n#<^v^^>#\n######.#")
(def input "#.########################################################################################################################\n#.v^<>^^.<^^<v>v>>>.<^<<v.^>vv>>^<<<v>.v<^v^<v<<^<^^<^<^v><^vv.v^^.>^><vv><^<.<^v<>.v<v..^>><v>.vvv.v>^^<^>.<<^<^<^>v.^^<#\n#.^^<v>><<v><vv<>..<>v<^v><<>><>v.>v.^^<<.v.^<^<vv><v<<v><<^<>^v>v>><>v..v^<^.v><>^>><v<<>v^<<v<v>v.v<v>><<v>^v<v^...<<<>#\n#><vv>>v>v<^>^^^^vv^<v^<<v<>^<^.^^>^<^vv>^v>><<^^<.>^>v<^v<.v.^<^^v.<.>.<^>v^>.v>>^vv^v.>>>v<<<<v.^^v<^^<^><<<<.^>.>vvv>>#\n#<.vv>.><<.<^v.>v^v>><v>><<^vv>^v^>.><v>>><^><v<v^^vv>.v^^<v>vv<>v>^v.>>v^>..v<>^v.^>><.vv><^...<>^^>v>vvv>^<^<><v>v<^^^<#\n#>^.^>vvv>vv>^^vv>v^^v>vv>v^v>^<v>>vvvv^>.v^<v^><^>vvv<>vv<^v<.<^v^>^vv^<><>.^.^>><<^^>^<v^>>>>v><<v^^>^^>>v^v.<^vv^<^v<>#\n#><^>><vv^vvv.v><v<>^v>^<<<^^<><^<<v<>^<<v><<<v>><v<^^<vv<.>^v>^<v>.>v>>^^>^>><>.<<v<^><>>>^.<vv>.^^<.^^>>v.><<v<>><>v^><#\n#>>^.<<v.^v>^^v.>v.vv^<<.v<v<..<<v^^>^v.><>v<^^<<.>v>v>v>^v>.<>v<><<^v><^>><^v>v^v>^vvv>>>..>^>>.><v><>.^^v.><<vv<.v>^^<.#\n#>vv>^^.<>^v<^<<v^>v<^^v><>>>>v^vv>v<^v^><><^.<v^>.<^.v^>><vv>>vvv>^v<<<><vv<v^<<>v>>^v>^^^>^v><^^>^^^<<^^<v<<v<v>^^^<^>>#\n#>.v^<<.vv<^v>^>>v<>v^v^^v^v<^^<<..<<^v<<<<^<v>^>.<>v<v<v<.<.v>vv^>><^.<.v<^<^v><v<v>.v.><<^>>v^.^vv.vv^.>^^>vv<>^..v>^v<#\n#..v.>^><<>..<>.<^<<v>^^^^><<><<vv><^>v>^v<vv>v><vv.^<.<<<<v^v<>^^^vv>.>>>v^v<v<^.<v<<^.v<><<>>v<<<.>>v.^^>^^.>.<.>vv>^>>#\n#>^^vv<<><<v<^.v>>v>^^^v^<v.^><<<<<v^v>v<<^v..^<.vv.^v>vvv>^v<..v<><<^<<vv<>>>>^>^^<>^<^<^^.vv>>^>^>^v^>.<^.<<.>v^>vv<.^<#\n#<v^vv>^^v>vv>^.^>v>v^v>vv.>>.>v^v^>.^v<^.>^<<<v.>^>^^<>vv<.<^^<^v<^><v^v^.^<><v^^v^>.<vv^>v^v<v^><>^<vv>v<><^v<v.<<^.>^>#\n#<>^<^<v><^^.^^>..^^>v>>>>^v<.<>>.<^v^>vvvvv>^<<.><^<v<.v<^><^^>vv<^<^<>v>v<>^^v>>^^>>v<^^<v>v^^^vvv<<<<<>>>v>^v.<^^>^>^<#\n#>.<^v<.<^v.v>^.>>><^<<^^<<.<>><>>>^vvv<v<v<<^^vv<<<^v^v^vv<^v>>^^vv.^>^.v^^^>>v^>.<>.^^>v^^^^><^^>^>>.v<v^<^>^v^>^<<<v<<#\n#>><><.>v^<>^^>.^>^v>^.^<v><><>.<v<^v>>>>v.<>>.^vv>v.vv.><vv^v>^<>>v>vv.v>v>v<><>>>^..^v>^^><..^..^..^vvv>><>v<>v<^<<>v<<#\n#<vv^vvv><>v^v.<^v^^v<.<>v<>.^v^<<^<><.^vv<>^>>.v<<><vv<^^<>>v^.^v><v^^<>v.v<.>^^v^<<<^^<<v<^^^<<<<>^^v>><><<><>v>v<v.>^>#\n#.<.^>^>^>^v..v>^.<v<^<<<^vvv>vv><^^v^<.>>.^<.^^<<>>^><<>>^^.^<<^^><^v<.><>.<<<.vvv>>vv^v<^v<>vv<<^v^^<v.v^<<^>v^>v<><vv<#\n#.vvvv>>^>^<..^>>^>vv^.^>^<<v>^^v>v>^<>.>vv<v^>>>^.^<<^>>^>^>vvv><vv^^<^.^^.<^^v.<v<>^<<<v^>><<.^^^^>^>>>v<<>>v>v<>v<v><.#\n#<<vv.<v>^^v<.>>^>^<>^><.<v^><>>.^<v^v<<.v>>.<>.v>^>^v><><^<><<<v^<><<v^..^v^v>^.><>>>v^.^>vv<<^>v<><.^^v<>vv>v^v<><^>>><#\n#><>^v>v<>><vv>>v<>^<v>v^^>^v^>^^<v>v>^^v><<^v>>vv<^><>^>^^>>v^^<^<v>.^>v^vv^<.v>^v^.v^><><>>v.v^vv^<v>^>^><v>^>^>v>^v..<#\n#>v<^^<^>.<>v<v<.^vv^>>v><^^v<^^>>^>^<^^>>>^<v<^v<>v<^>v>^>v^v>^.<<v^>vv<^<.><^><<^>v><^<v>^<^v>v^>><^vvv^>>v>>vv>v^>^><<#\n#<vvv<><^.>v.^><v<^^<v><^<vvv.^..v^>...v^><<^^><^>^^vv>^v>v^<v<<>^>^^..<^<<v><<>.><<>^vvvv>v^><.^^>>v>>>^>>^<^<^<v^>vvv>>#\n#><<<^^.^<.^.^^<>^.vv^^^<v^<<<vv<<>^<v<^>>v^>>v<<v^^.v^<.vv<v<><<.v><<^>>v><.v><v>v^^v^v<^><vvvvv<^.<<>v^.<>^.^><>^v><>^<#\n#<.<>.>v^><<>^^<<vv>^>^v>>>v>v><^v><v.>>^<>>.<vv<<<v<v^<<vv^<.v^^>vv>>^<^<v<v<<>vv.><v..>>>.>^v<<vv><v^^^vv^^<>^>v<^^>v.>#\n#<>^^<>>^v>v<<>^.>>^^^<<v>^vv^<^<.<<<vv>>v>^^<^v><><>.<>.^><<^^^>^vv><>v.<>v<.<v>>><v.^>^>vv<v>^<v^<<v^v>v..>^^<^^.><>^><#\n########################################################################################################################.#")

(defn parse [ss]
  (let [lines (->> ss str/split-lines (mapv #(into [] %)))
        W     (-> lines first count)
        H     (-> lines count)
        db    (->>
                (for [x (range 0 W)
                      y (range 0 H)]
                  [x y])
                (reduce
                  (fn [db [x y :as xy]]
                    (blet [db+ (update db ::all u/sconj xy)]
                      (case (get-in lines [y x])
                        \. db+
                        \^ (update db+ ::U u/sconj xy)
                        \> (update db+ ::R u/sconj xy)
                        \v (update db+ ::D u/sconj xy)
                        \< (update db+ ::L u/sconj xy)
                        \# db)))
                  {}))]
    (-> db
      (dissoc ::all)
      (merge
        {::start-xy  (->> db ::all (apply min-key second))
         ::finish-xy (->> db ::all (apply max-key second))
         ::mins      0
         ;; no vertical winds on entrance/exit columns:
         ::windminx  1
         ::windminy  1
         ::windmaxx  (-> W dec dec)
         ::windmaxy  (-> H dec dec)}))))


(defn uniq-dbs-count [db]
  (let [SEQ #(->> % repeat (reductions + %))]
    (loop [aa (SEQ (-> db ::windmaxx))
           bb (SEQ (-> db ::windmaxy))]
      (blet [a (first aa) aa- (rest aa)
             b (first bb) bb- (rest bb)]
        (cond
          (= a b) a
          (< a b) (if (zero? (mod b a)) a (recur aa- bb))
          (> a b) (if (zero? (mod a b)) b (recur aa bb-)))))))


(defn neighbours [[x y]]
  [[x y]
   [x (dec y)]
   [x (inc y)]
   [(dec x) y]
   [(inc x) y]])

(defn walk [init-db]
  (let [{:keys [::windminx ::windmaxx ::start-xy ::finish-xy
                ::windminy ::windmaxy]} init-db
        move-wind   (memoize
                      (fn mw [dir [x y]]
                        (case dir
                          ::L (if (= windminx x) [windmaxx y] [(dec x) y])
                          ::R (if (= windmaxx x) [windminx y] [(inc x) y])
                          ::U (if (= windminy y) [x windmaxy] [x (dec y)])
                          ::D (if (= windmaxy y) [x windminy] [x (inc y)]))))
        move-dir    (memoize
                      (fn md [xys dir]
                        (set (map #(move-wind dir %) xys))))

        next-db-mem (memoize
                      (fn mdb [db]
                        (-> db
                          (update ::U move-dir ::U)
                          (update ::D move-dir ::D)
                          (update ::L move-dir ::L)
                          (update ::R move-dir ::R))))
        next-db     (fn mdb [db]
                      (-> db
                        (dissoc ::mins)
                        (next-db-mem)
                        (assoc ::mins (inc (::mins db)))))

        wall?       (memoize
                      (fn w [[x y]]
                        (and
                          (not= [x y] finish-xy)
                          (not= [x y] start-xy)
                          (or
                            (<= x (dec windminx))
                            (>= x (inc windmaxx))
                            (<= y (dec windminy))
                            (>= y (inc windmaxy))))))

        next-xys    (fn [db xys]
                      (->> xys
                        (into #{}
                          (comp
                            (mapcat neighbours)
                            (remove wall?)
                            (remove (::U db))
                            (remove (::D db))
                            (remove (::L db))
                            (remove (::R db))))))]

    (loop [db    init-db
           xys   #{start-xy}]
      (blet [db+  (next-db db)
             xys+ (next-xys db+ xys)]
        (if (contains? xys finish-xy)
          db
          (recur db+ xys+))))))

(defn turn [db]
  (-> db
    (assoc ::finish-xy (::start-xy db))
    (assoc ::start-xy (::finish-xy db))))

(defn p1 [ss] (-> ss parse walk ::mins))
(defn p2 [ss] (-> ss parse walk turn walk turn walk ::mins))


(time (assert (= 18 (p1 sample))))
(time (assert (= 269 (p1 input))))
(time (assert (= 54 (p2 sample))))
(time (assert (= 825 (p2 input))))