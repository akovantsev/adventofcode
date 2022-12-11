(ns adventofcode.y2022.day10
  (:require [clojure.string :as str]
            [adventofcode.utils :as u]))

(def sample "addx 15\naddx -11\naddx 6\naddx -3\naddx 5\naddx -1\naddx -8\naddx 13\naddx 4\nnoop\naddx -1\naddx 5\naddx -1\naddx 5\naddx -1\naddx 5\naddx -1\naddx 5\naddx -1\naddx -35\naddx 1\naddx 24\naddx -19\naddx 1\naddx 16\naddx -11\nnoop\nnoop\naddx 21\naddx -15\nnoop\nnoop\naddx -3\naddx 9\naddx 1\naddx -3\naddx 8\naddx 1\naddx 5\nnoop\nnoop\nnoop\nnoop\nnoop\naddx -36\nnoop\naddx 1\naddx 7\nnoop\nnoop\nnoop\naddx 2\naddx 6\nnoop\nnoop\nnoop\nnoop\nnoop\naddx 1\nnoop\nnoop\naddx 7\naddx 1\nnoop\naddx -13\naddx 13\naddx 7\nnoop\naddx 1\naddx -33\nnoop\nnoop\nnoop\naddx 2\nnoop\nnoop\nnoop\naddx 8\nnoop\naddx -1\naddx 2\naddx 1\nnoop\naddx 17\naddx -9\naddx 1\naddx 1\naddx -3\naddx 11\nnoop\nnoop\naddx 1\nnoop\naddx 1\nnoop\nnoop\naddx -13\naddx -19\naddx 1\naddx 3\naddx 26\naddx -30\naddx 12\naddx -1\naddx 3\naddx 1\nnoop\nnoop\nnoop\naddx -9\naddx 18\naddx 1\naddx 2\nnoop\nnoop\naddx 9\nnoop\nnoop\nnoop\naddx -1\naddx 2\naddx -37\naddx 1\naddx 3\nnoop\naddx 15\naddx -21\naddx 22\naddx -6\naddx 1\nnoop\naddx 2\naddx 1\nnoop\naddx -10\nnoop\nnoop\naddx 20\naddx 1\naddx 2\naddx 2\naddx -6\naddx -11\nnoop\nnoop\nnoop")
(def input "addx 1\nnoop\naddx 2\naddx 11\naddx -4\nnoop\nnoop\nnoop\nnoop\naddx 3\naddx -3\naddx 10\naddx 1\nnoop\naddx 12\naddx -8\naddx 5\nnoop\nnoop\naddx 1\naddx 4\naddx -12\nnoop\naddx -25\naddx 14\naddx -7\nnoop\naddx 11\nnoop\naddx -6\naddx 3\nnoop\naddx 2\naddx 22\naddx -12\naddx -17\naddx 15\naddx 2\naddx 10\naddx -9\nnoop\nnoop\nnoop\naddx 5\naddx 2\naddx -33\nnoop\nnoop\nnoop\nnoop\naddx 12\naddx -9\naddx 7\nnoop\nnoop\naddx 3\naddx -2\naddx 2\naddx 26\naddx -31\naddx 14\naddx 3\nnoop\naddx 13\naddx -1\nnoop\naddx -5\naddx -13\naddx 14\nnoop\naddx -20\naddx -15\nnoop\naddx 7\nnoop\naddx 31\nnoop\naddx -26\nnoop\nnoop\nnoop\naddx 5\naddx 20\naddx -11\naddx -3\naddx 9\naddx -5\naddx 2\nnoop\naddx 4\nnoop\naddx 4\nnoop\nnoop\naddx -7\naddx -30\nnoop\naddx 7\nnoop\nnoop\naddx -2\naddx -4\naddx 11\naddx 14\naddx -9\naddx -2\nnoop\naddx 7\nnoop\naddx -11\naddx -5\naddx 19\naddx 5\naddx 2\naddx 5\nnoop\nnoop\naddx -2\naddx -27\naddx -6\naddx 1\nnoop\nnoop\naddx 4\naddx 1\naddx 4\naddx 5\nnoop\nnoop\nnoop\naddx 1\nnoop\naddx 4\naddx 1\nnoop\nnoop\naddx 5\nnoop\nnoop\naddx 4\naddx 1\nnoop\naddx 4\naddx 1\nnoop\nnoop\nnoop\nnoop")

(defn parse [ss]
  (->> ss
    str/split-lines
    (mapcat #(str/split % #"\s"))
    (map read-string)
    (reductions
      (fn [x y]
        (if (int? y) (+ x y) x))
      1)))


(defn p1 [ss]
  (->> ss
    parse
    (map vector (rest(range)))
    (filter #(-> % first #{20 60 100 140 180 220}))
    (map (partial apply *))
    (reduce +)))


(assert (= 13140 (p1 sample)))
(assert (= 14780 (p1 input)))

(defn pixel [[idx pos]] (if (#{-1 0 1} (- pos idx)) "#" "."))

(defn p2 [ss] (->> ss parse (map vector (cycle (range 40))) (map pixel) (partition 40) (map str/join) (str/join "\n")))

(assert (= (p2 sample) "##..##..##..##..##..##..##..##..##..##..\n###...###...###...###...###...###...###.\n####....####....####....####....####....\n#####.....#####.....#####.....#####.....\n######......######......######......####\n#######.......#######.......#######....."))

(assert (= (p2 input) "####.#....###..#....####..##..####.#....\n#....#....#..#.#.......#.#..#....#.#....\n###..#....#..#.#......#..#......#..#....\n#....#....###..#.....#...#.##..#...#....\n#....#....#....#....#....#..#.#....#....\n####.####.#....####.####..###.####.####."))
(print (p2 input))