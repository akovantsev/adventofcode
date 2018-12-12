(ns adventofcode.2018.day12
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(set! *print-length* 200)
(def initial-state "#...####.##..####..#.##....##...###.##.#..######..#..#..###..##.#.###.#####.##.#.#.#.##....#..#..#..")
(def input "...## => .\n...#. => #\n....# => .\n###.# => #\n..... => .\n..#.. => .\n#.#.# => .\n#..#. => .\n#...# => .\n##... => .\n.#.#. => #\n.#..# => .\n.###. => .\n#..## => #\n..#.# => #\n.#### => #\n##..# => #\n##.#. => #\n.#... => #\n#.#.. => .\n##### => .\n###.. => #\n.##.# => .\n#.##. => .\n..### => .\n.#.## => #\n..##. => #\n#.### => .\n.##.. => #\n##.## => .\n#.... => .\n####. => #")

(def LIMIT1 20)
(def LIMIT2 50000000000)
(def REPLACEMENTS (->> input str/split-lines (map #(str/split % #" => ")) (into {})))
(def INITIAL-POTS (->> initial-state
                    (map str)
                    (map-indexed vector)
                    (into {})))



(assert (-> REPLACEMENTS (get ".....") (= ".")))

(defn shrink-left!
  "if first 3 are '.' - can dissoc first one."
  [lowest-idx !m]
  (let [idxs (range lowest-idx (+ lowest-idx 3))
        vs   (-> !m (select-keys idxs) vals)]
    (if (= vs '("." "." "."))
      (recur (inc lowest-idx) (dissoc! !m lowest-idx))
      !m)))

(defn shrink-right!
  "if last 3 are '.' - can dissoc last one."
  [highest-idx !m]
  (let [idxs (range (- highest-idx 2) (+ highest-idx 1))
        vs   (-> !m (select-keys idxs) vals)]
    (if (= vs '("." "." "."))
      (recur (dec highest-idx) (dissoc! !m highest-idx))
      !m)))



(defn next-val [replacements pots idx]
  (let [idxs (map + (repeat idx) (range -2 3))]
    (->> idxs
      (map #(get pots % "."))
      (str/join)
      (get replacements))))


(defn step [pots]
  (let [sorted-idxs     (-> pots keys sort)
        lowest-idx      (-> sorted-idxs first)
        highest-idx     (-> sorted-idxs count dec (+ lowest-idx))
        new-lowest-idx  (- lowest-idx 2)
        new-highest-idx (+ highest-idx 2)
        idxs            (range new-lowest-idx new-highest-idx)]
    (->> idxs
      (reduce
        (fn rf [!m idx]
          (assoc! !m idx (next-val REPLACEMENTS pots idx)))
        (transient {}))
      (shrink-left! new-lowest-idx)
      (shrink-right! new-highest-idx)
      (persistent!))))




(defn str-pots [m]
  (->> m (sort-by key) (map val) str/join))

(defn stats [m]
  {::score        (->> m (filter #(-> % val (= "#"))) (map key) (reduce +))
   ::plants-count (->> m (filter #(-> % val (= "#"))) count)
   ::lowest-idx   (->> m keys (reduce min))
   ::string-repr  (->> m (str-pots))})


(defn different? [[_idx [m1 m2]]]
  (not= (str-pots m1) (str-pots m2)))

(defn score [[current-step [m1 m2]]]
  (let [st1         (stats m1)
        st2         (stats m2)
        step-shift  (->> [st2 st1] (map ::lowest-idx) (apply -))
        steps-togo  (- LIMIT2 current-step)
        total-shift (* steps-togo step-shift (::plants-count st1))]
    (-> st1 ::score (+ total-shift))))



(defn plot [m]
  (->> m stats ((juxt ::lowest-idx ::string-repr)) (str/join " ")))


(defn f1 [initial-m]
  (time
    (->> initial-m
      (iterate step)
      (drop LIMIT1)
      (first)
      (stats)
      ::score)))

(defn f2 [initial-m]
  (time
    (->> initial-m
      (iterate step)
      (take LIMIT2)
      (partition 2 1)
      (map-indexed vector)
      (drop-while different?)
      (first)
      (score))))

(assert (not= (f1 INITIAL-POTS) 837))
(assert (= (f1 INITIAL-POTS) 3410))
(assert (= (f2 INITIAL-POTS) 4000000001480))
