(ns adventofcode.2017.day21
  (:require [clojure.string :as str]))

(def init ".#.\n..#\n###")

(def t "../.# => ##./#../...\n.#./..#/### => #..#/..../..../#..#")
(def i "../.. => #.#/.#./...\n#./.. => .../..#/..#\n##/.. => .#./##./###\n.#/#. => ..#/#../##.\n##/#. => ##./..#/#.#\n##/## => ###/###/.##\n.../.../... => ...#/#.##/.##./#..#\n#../.../... => .###/#.##/##.#/##.#\n.#./.../... => #.../.###/..#./#.##\n##./.../... => .#.#/.###/##../#.##\n#.#/.../... => .##./..../##.#/#...\n###/.../... => ..##/.#.#/###./#..#\n.#./#../... => .#.#/##.#/.#.#/.##.\n##./#../... => ###./.###/#.../...#\n..#/#../... => #.##/##../.#../.##.\n#.#/#../... => ..##/..../..##/...#\n.##/#../... => ####/#..#/.#../....\n###/#../... => ##../..#./##../....\n.../.#./... => .###/...#/#.../#...\n#../.#./... => .#../.#.#/..##/#.#.\n.#./.#./... => ##.#/####/.#.#/##..\n##./.#./... => ..../.###/#.#./..##\n#.#/.#./... => ..#./..#./..../....\n###/.#./... => ###./..../..#./....\n.#./##./... => ..../.##./##.#/....\n##./##./... => ..#./.#../..##/#...\n..#/##./... => #.##/.##./..#./.##.\n#.#/##./... => .###/#.../##.#/..#.\n.##/##./... => ###./##../..#./..##\n###/##./... => ..#./.##./.###/#..#\n.../#.#/... => ###./#.../####/#.#.\n#../#.#/... => .##./.#../#.##/.#..\n.#./#.#/... => .#../##../..##/.#.#\n##./#.#/... => ###./#.#./##.#/##..\n#.#/#.#/... => #.../.##./#.##/#.#.\n###/#.#/... => ###./..##/#.##/###.\n.../###/... => ##../...#/#.#./#.#.\n#../###/... => .#../...#/##.#/####\n.#./###/... => #.#./..##/#.#./.##.\n##./###/... => ..##/..##/.###/#...\n#.#/###/... => ####/##../..../#..#\n###/###/... => ...#/#.##/#.##/#.#.\n..#/.../#.. => .#.#/..##/#.##/#..#\n#.#/.../#.. => ...#/..#./##../#..#\n.##/.../#.. => ####/##../..../##..\n###/.../#.. => ..#./..#./##.#/#..#\n.##/#../#.. => .#../####/.###/#..#\n###/#../#.. => ####/.#.#/...#/..##\n..#/.#./#.. => #.#./.##./####/....\n#.#/.#./#.. => ##../###./.#../##..\n.##/.#./#.. => ###./.#../...#/....\n###/.#./#.. => .#../.###/##../##.#\n.##/##./#.. => .#../#..#/.###/#...\n###/##./#.. => ..../.##./##../...#\n#../..#/#.. => ##.#/...#/.###/##.#\n.#./..#/#.. => ##../##../..../#.#.\n##./..#/#.. => ..##/.#../#.#./.#.#\n#.#/..#/#.. => ..../..##/...#/...#\n.##/..#/#.. => #.../..##/...#/####\n###/..#/#.. => #.../..#./##.#/.#.#\n#../#.#/#.. => ..##/#.../#..#/..#.\n.#./#.#/#.. => #..#/#.../.##./#.##\n##./#.#/#.. => ##.#/.##./##.#/...#\n..#/#.#/#.. => ####/.#.#/.##./#.#.\n#.#/#.#/#.. => #..#/.##./.##./.###\n.##/#.#/#.. => ...#/...#/..../.##.\n###/#.#/#.. => .#../###./..../.###\n#../.##/#.. => ##.#/##../#.#./...#\n.#./.##/#.. => ###./.#.#/#.##/####\n##./.##/#.. => #.##/..#./.#../#..#\n#.#/.##/#.. => #.#./..##/..##/.#.#\n.##/.##/#.. => .#../.###/.###/#.##\n###/.##/#.. => #.../##../#.#./.#..\n#../###/#.. => #.#./###./.##./..#.\n.#./###/#.. => #.../#.../.##./.#..\n##./###/#.. => ..#./.###/..##/#...\n..#/###/#.. => #.##/.#../###./.###\n#.#/###/#.. => .#.#/#..#/###./##..\n.##/###/#.. => #.#./#.##/..##/.#..\n###/###/#.. => ##../#.../..#./#..#\n.#./#.#/.#. => #..#/####/#.#./#..#\n##./#.#/.#. => ..##/.#../##.#/#..#\n#.#/#.#/.#. => ####/#.#./#..#/#.#.\n###/#.#/.#. => #.../##.#/..../#...\n.#./###/.#. => ..##/.##./####/.###\n##./###/.#. => .##./..#./#.##/#..#\n#.#/###/.#. => ##.#/##../####/...#\n###/###/.#. => ..##/####/...#/.#..\n#.#/..#/##. => #.##/.#.#/#.#./#.##\n###/..#/##. => ...#/##.#/#..#/..#.\n.##/#.#/##. => .#.#/..#./..../###.\n###/#.#/##. => ###./####/##.#/#.##\n#.#/.##/##. => ##.#/#.##/.##./##..\n###/.##/##. => .#.#/#.#./###./####\n.##/###/##. => .#../####/.#../....\n###/###/##. => .#../..../##.#/.##.\n#.#/.../#.# => #.../#.../..##/..##\n###/.../#.# => ...#/..#./##.#/####\n###/#../#.# => .###/..##/.#../....\n#.#/.#./#.# => ###./####/.#../#..#\n###/.#./#.# => #.../#.##/..../###.\n###/##./#.# => .###/####/#..#/.###\n#.#/#.#/#.# => .#.#/...#/.#.#/#.##\n###/#.#/#.# => ..../..#./..#./####\n#.#/###/#.# => ..##/...#/.#.#/.##.\n###/###/#.# => .###/.##./..##/####\n###/#.#/### => #.#./.#../.#.#/#.#.\n###/###/### => #..#/##../#.#./####")



(defn flip [square]
  (->> square reverse vec))

(flip ["###" "..#" ".#."])

(defn turn [square]
  (->> square
    (apply map vector)
    (map reverse)
    (mapv vec)))

(defn make-grid [lines]
  (mapv vec lines))

(def mops (->> [flip turn] (map memoize) (apply juxt) memoize))

(defn transformations [lines]
  (->> #{lines}
    (iterate (fn [variants]
               (->> variants
                 (map mops)
                 (reduce into variants))))
    (partition 2)
    (drop-while #(apply not= %))
    (ffirst)))

(defn parse-rule [line]
  (let [[from to] (->> (str/split line #" => ")
                       (map #(str/split % #"/")))
        to (mapv vec to)]
    (->> from
      make-grid
      transformations
      (reduce
        (fn [m k]
          (assoc m k to))
        {}))))



(defn make-db [input]
  (->> input str/split-lines (map parse-rule) (reduce merge)))



(defn split [rows]
  (let [n (if (-> rows count (mod 2) zero?) 2 3)
        part #(partition n %)]
    (->> rows
      (map part)
      ;; columns:
      (apply map vector)
      (map part)
      ;; rows:
      (apply map vector))))


(defn glue [grid]
  (mapcat #(apply map concat %) grid))

(let [db     (make-db i)
      grid   (make-grid (str/split-lines init))
      lookup (fn [square] (map #(map db %) square))
      step   #(-> % split lookup glue)]
  (->> grid
    (iterate step)
    ;(drop 2)
    ;(drop 5)
    (drop 18)
    (first)
    (reduce into [])
    (filter #{\#})
    (count)))

155
2449665

[[1 2 3 4]
 [5 6 7 8]
 [9 0 :a :b]
 [:c :d :f :e]]

(->> '([((1 2) (5 6)) ((3 4) (7 8))]
       [((9 0) (:c :d)) ((:a :b) (:f :e))])
  ;(reduce into [])
  (mapcat #(apply map concat %)))