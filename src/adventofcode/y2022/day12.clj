(ns adventofcode.y2022.day12
  (:require [adventofcode.utils :as u]
            [com.akovantsev.blet.core :refer [blet blet!]]))


(def sample "Sabqponm\nabcryxxl\naccszExk\nacctuvwj\nabdefghi")
(def input "abccccccccccccccccccaaaaaaaaacccccccccccccccccccccccccccccccccccccaaaa\nabcccccccccccccccaaaaaaaaaaacccccccccccccccccccccccccccccccccccccaaaaa\nabcaaccaacccccccccaaaaaaaaaacccccccccccccccccccccaaacccccccccccccaaaaa\nabcaaaaaaccccccccaaaaaaaaaaaaacccccccccccccccccccaacccccccccccccaaaaaa\nabcaaaaaacccaaacccccaaaaaaaaaaaccccccccccccccccccaaaccccccccccccccccaa\nabaaaaaaacccaaaaccccaaaaaacaaaacccccccccccaaaacjjjacccccccccccccccccca\nabaaaaaaaaccaaaaccccaaaaaaccccccaccccccccccaajjjjjkkcccccccccccccccccc\nabaaaaaaaaccaaacccccccaaaccccccaaccccccccccajjjjjjkkkaaacccaaaccaccccc\nabccaaacccccccccccccccaaccccaaaaaaaacccccccjjjjoookkkkaacccaaaaaaccccc\nabcccaacccccccccccccccccccccaaaaaaaaccccccjjjjoooookkkkcccccaaaaaccccc\nabcccccccaacccccccccccccccccccaaaacccccccijjjoooooookkkkccaaaaaaaccccc\nabccaaccaaaccccccccccccccccccaaaaacccccciijjooouuuoppkkkkkaaaaaaaacccc\nabccaaaaaaaccccccccccaaaaacccaacaaaccciiiiiooouuuuupppkkklllaaaaaacccc\nabccaaaaaacccccccccccaaaaacccacccaaciiiiiiqooouuuuuupppkllllllacaccccc\nabcccaaaaaaaacccccccaaaaaaccccaacaiiiiiqqqqoouuuxuuupppppplllllccccccc\nabccaaaaaaaaaccaaaccaaaaaaccccaaaaiiiiqqqqqqttuxxxuuuppppppplllccccccc\nabccaaaaaaaacccaaaaaaaaaaacccaaaahiiiqqqttttttuxxxxuuuvvpppplllccccccc\nabcaaaaaaacccaaaaaaaaaaacccccaaaahhhqqqqtttttttxxxxuuvvvvvqqlllccccccc\nabcccccaaaccaaaaaaaaaccccccccacaahhhqqqttttxxxxxxxyyyyyvvvqqlllccccccc\nabcccccaaaccaaaaaaaacccccccccccaahhhqqqtttxxxxxxxyyyyyyvvqqqlllccccccc\nSbcccccccccccaaaaaaaaaccccccccccchhhqqqtttxxxxEzzzyyyyvvvqqqmmlccccccc\nabcccccccccccaaaaaaaacccaacccccccchhhppptttxxxxyyyyyvvvvqqqmmmcccccccc\nabccccccccccaaaaaaaaaaccaacccccccchhhpppptttsxxyyyyyvvvqqqmmmccccccccc\nabcaacccccccaaaaaaacaaaaaaccccccccchhhppppsswwyyyyyyyvvqqmmmmccccccccc\nabaaaacccccccaccaaaccaaaaaaacccccccchhhpppsswwyywwyyyvvqqmmmddcccccccc\nabaaaaccccccccccaaaccaaaaaaacccccccchhhpppsswwwwwwwwwvvqqqmmdddccccccc\nabaaaacccccccccaaaccaaaaaaccccccccccgggpppsswwwwrrwwwwvrqqmmdddccccccc\nabccccccaaaaaccaaaacaaaaaaccccccaacccggpppssswwsrrrwwwvrrqmmdddacccccc\nabccccccaaaaaccaaaacccccaaccccaaaaaacggpppssssssrrrrrrrrrnmmdddaaccccc\nabcccccaaaaaaccaaaccccccccccccaaaaaacggppossssssoorrrrrrrnnmdddacccccc\nabcccccaaaaaaccccccccaaaaccccccaaaaacgggoooossoooonnnrrnnnnmddaaaacccc\nabccccccaaaaaccccccccaaaacccccaaaaaccgggoooooooooonnnnnnnnndddaaaacccc\nabccccccaaaccccccccccaaaacccccaaaaacccgggoooooooffennnnnnnedddaaaacccc\nabcccccccccccccccccccaaacccccccaacccccggggffffffffeeeeeeeeeedaaacccccc\nabccccccccccccccccccaaacccccaccaaccccccggfffffffffeeeeeeeeeecaaacccccc\nabccccccccccccccccccaaaacccaaaaaaaaaccccfffffffaaaaaeeeeeecccccccccccc\nabccccccccaacaaccccaaaaaacaaaaaaaaaaccccccccccaaaccaaaaccccccccccccccc\nabccccccccaaaaacccaaaaaaaaaaacaaaaccccccccccccaaaccccaaccccccccccaaaca\nabcccccccaaaaaccccaaaaaaaaaaacaaaaacccccccccccaaaccccccccccccccccaaaaa\nabcccccccaaaaaacccaaaaaaaaaacaaaaaacccccccccccaaccccccccccccccccccaaaa\nabcccccccccaaaaccaaaaaaaaaaaaaaccaaccccccccccccccccccccccccccccccaaaaa")

(def ALIAS {\S \a \E \z})
(def SCORE int)

(defn parse [ss]
  (loop [todo ss x 0 y 0 m {}]
    (blet [a     (first todo)
           i     (SCORE (ALIAS a a))
           todo- (rest todo)
           x+    (inc x)
           y+    (inc y)
           m+    (assoc m [x y] i)
           ms    (assoc m+ ::start-xy [x y])
           me    (assoc m+ ::end-xy [x y])]
      (cond
        (empty? todo), m
        (= \newline a) (recur todo- 0 y+ m)
        (= \S a),,,,,, (recur todo- x+ y ms)
        (= \E a),,,,,, (recur todo- x+ y me)
        :else,,,,,,,,, (recur todo- x+ y m+)))))

(parse sample)


(defn solve [MAP start-xys]
  (let [{:keys [::end-xy]} MAP
        paths (u/queue (map (fn [xy] [0 xy #{}]) start-xys))
        best  (zipmap start-xys (repeat ##Inf))]
    (loop [paths    paths
           best     best
           shortest [##Inf]]
      (blet [paths-    (pop paths)
             path      (peek paths)
             min-steps (first shortest)
             [steps at-xy been?] path
             best-here (get best at-xy ##Inf)
             could     (-> at-xy MAP inc)
             steps+    (inc steps)
             been?+    (conj been? at-xy)
             best*     (assoc best at-xy steps)
             next-path (fn [next-xy]
                         (when-let [s (MAP next-xy)]
                           (when (<= s could)
                             (when-not (been? next-xy)
                               [steps+ next-xy been?+]))))
             path+     (->> at-xy u/neighbours (keep next-path))
             paths+    (into paths- path+)]
        (cond
          (empty? paths),,,,,, shortest
          (<= min-steps steps) (recur paths- best shortest)
          (<= best-here steps) (recur paths- best shortest)
          (= at-xy end-xy),,,, (recur paths- best* path)
          :else,,,,,,,,,,,,,,, (recur paths+ best* shortest))))))

(defn p1 [ss]
  (let [MAP (parse ss)
        xys [(::start-xy MAP)]]
    (solve MAP xys)))

(defn p2 [ss]
  (let [MAP   (parse ss)
        start (SCORE \a)
        xys   (->> MAP
                (filter #(-> % val (= start)))
                (map key))]
    (solve MAP xys)))


(time (assert (= 31 (first (p1 sample)))))
(time (assert (= 29 (first (p2 sample)))))
(time (assert (= 352 (first (p1 input)))))
(time (assert (= 345 (first (p2 input)))))
"Elapsed time: 0.850549 msecs"
"Elapsed time: 0.571332 msecs"
"Elapsed time: 25.028481 msecs"
"Elapsed time: 25.007999 msecs"