(ns adventofcode.y2020.day22
  (:require
   [clojure.string :as str])
  (:import
   [clojure.lang PersistentQueue]))


(def t "Player 1:\n9\n2\n6\n3\n1\n\nPlayer 2:\n5\n8\n4\n7\n10")
(def i "Player 1:\n26\n8\n2\n17\n19\n29\n41\n7\n25\n33\n50\n16\n36\n37\n32\n4\n46\n12\n21\n48\n11\n6\n13\n23\n9\n\nPlayer 2:\n27\n47\n15\n45\n10\n14\n3\n44\n31\n39\n42\n5\n49\n24\n22\n20\n30\n1\n35\n38\n18\n43\n28\n40\n34")

(defn score [p]
  (reduce +
    (map * p (range (count p) 0 -1))))

(score [1 2 3])

(defn step1 [[p1 p2]]
  (let [x1 (peek p1) p1 (pop p1)
        x2 (peek p2) p2 (pop p2)]
    (cond
      (< x1 x2) [p1 (conj p2 x2 x1)]
      (> x1 x2) [(conj p1 x1 x2) p2]
      (= x1 x2) [(conj p1 x1) (conj p2 x2)])))

(step1 [[1] [2]])


(defn players [s]
  (->> (str/split s #"\n\n")
    (map str/split-lines)
    (map rest)
    (map #(->> % (mapv read-string) (into PersistentQueue/EMPTY)))))

;;p1
(->> i
  (players)
  (iterate step1)
  (drop-while #(not-any? empty? %))
  (first)
  (remove empty?)
  (first)
  (score))

32472



(defn make-state [p]
  [p #{}])

(declare game2)

(defn step2 [[s1 s2]]
  (let [[p1 seen1] s1
        [p2 seen2] s2]
    (cond
      (empty? p1)   {:player 2 :score (score p2)}
      (empty? p2)   {:player 1 :score (score p1)}
      (or
        (seen1 p1)
        (seen2 p2)) {:player 1 :score (score p1)}
      :else
      (let [seen1 (conj seen1 p1)
            seen2 (conj seen2 p2)
            x1 (peek p1)  p1 (pop p1)
            x2 (peek p2)  p2 (pop p2)
            [p1 p2] (if (and
                          (<= x1 (count p1))
                          (<= x2 (count p2)))
                      ;;recursive:
                      (let [s1* (->> p1 (take x1) (into PersistentQueue/EMPTY) make-state)
                            s2* (->> p2 (take x2) (into PersistentQueue/EMPTY) make-state)
                            {:keys [player score]} (game2 [s1* s2*])]
                        (if (= 1 player)
                          [(conj p1 x1 x2) p2]
                          [p1 (conj p2 x2 x1)]))
                      ;;usual
                      (cond
                        (< x1 x2) [p1 (conj p2 x2 x1)]
                        (> x1 x2) [(conj p1 x1 x2) p2]
                        (= x1 x2) [(conj p1 x1) (conj p2 x2)]))]
        [[p1 seen1]
         [p2 seen2]]))))

(defn game2 [states]
  (->> states
    (iterate step2)
    (drop-while (complement map?))
    (first)))


(->> i
  (players)
  (map make-state)
  (game2)
  (time))

"Elapsed time: 634.934112 msecs"
{:player 1, :score 36463}
