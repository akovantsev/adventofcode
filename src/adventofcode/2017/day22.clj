(ns adventofcode.2017.day22
  (:require [clojure.string :as str]
            [adventofcode.utils :as u]))


(def t "..#\n#..\n...")
(def i "..#..##...##.######.##...\n..#...#####..#.#####..#..\n...##.#..##.#.##....#...#\n#.#.#.#..###...#....##..#\n..#..#####.....##..#.#..#\n.##.#####.#.....###.#..#.\n##..####...#.##.#...##...\n###.#.#####...##.#.##..#.\n#.##..##.#....#.#..#.##..\n###.######......####..#.#\n###.....#.##.##.######..#\n...####.###.#....#..##.##\n#..####.#.....#....###.#.\n#..##..#.####.#.##..#.#..\n#..#.#.##...#...#####.##.\n#.###..#.##.#..##.#######\n...###..#..####.####.#.#.\n.#..###..###.#....#######\n.####..##.#####.#.#..#.#.\n#.#....##.....##.##.....#\n....####.....#..#.##..##.\n######..##..#.###...###..\n..##...##.....#..###.###.\n##.#.#..##.#.#.##....##.#\n.#.###..##..#....#...##.#")

(defn make-db [input]
  (let [lines (str/split-lines input)
        h     (count lines)
        w     (count (first lines))
        space (->>
                (for [y (range h) x (range w)]
                  (when (= \# (get-in lines [y x]))
                    [x y]))
                (remove nil?)
                (into #{}))
        start [(-> w (- 1) (/ 2) int)
               (-> h (- 1) (/ 2) int)]]
    [space start]))

(make-db t)


(def next-xy (memoize u/next-xy))

(defn step [[infected dir xy infections]]
  (if (infected xy)
    (let [dir (u/turn-right dir)]
      [(disj infected xy) dir (next-xy dir xy) infections])
    (let [dir (u/turn-left dir)]
      [(conj infected xy) dir (next-xy dir xy) (inc infections)])))

;p1
(let [[infected xy] (make-db i)]
  (->> [infected :up xy 0]
    (iterate step)
    (drop 10000)
    (first)
    (last)))
    ;(= 5587)))

5433



(defn step2 [[dir xy infections weakened infected flagged]]
  (cond
    (weakened xy)
    (let [dir dir]
      [dir (next-xy dir xy) (inc infections) (disj! weakened xy) (conj! infected xy) flagged])

    (infected xy)
    (let [dir (u/turn-right dir)]
      [dir (next-xy dir xy) infections weakened (disj! infected xy) (conj! flagged xy)])

    (flagged xy)
    (let [dir (u/turn-back dir)]
      [dir (next-xy dir xy) infections weakened infected (disj! flagged xy)])

    :clean
    (let [dir (u/turn-left dir)]
      [dir (next-xy dir xy) infections (conj! weakened xy) infected flagged])))


;p2
(let [[infected xy] (make-db t)]
  (->> [:up xy 0 (transient #{}) (transient infected) (transient #{})]
    (iterate step2)
    (drop 100)
    (first)
    (time)))
;(= 5587)))

2512599