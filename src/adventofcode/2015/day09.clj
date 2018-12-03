(ns adventofcode.2015.day09
  (:require [clojure.string :as str]))

(def input "Faerun to Norrath = 129\nFaerun to Tristram = 58\nFaerun to AlphaCentauri = 13\nFaerun to Arbre = 24\nFaerun to Snowdin = 60\nFaerun to Tambi = 71\nFaerun to Straylight = 67\nNorrath to Tristram = 142\nNorrath to AlphaCentauri = 15\nNorrath to Arbre = 135\nNorrath to Snowdin = 75\nNorrath to Tambi = 82\nNorrath to Straylight = 54\nTristram to AlphaCentauri = 118\nTristram to Arbre = 122\nTristram to Snowdin = 103\nTristram to Tambi = 49\nTristram to Straylight = 97\nAlphaCentauri to Arbre = 116\nAlphaCentauri to Snowdin = 12\nAlphaCentauri to Tambi = 18\nAlphaCentauri to Straylight = 91\nArbre to Snowdin = 129\nArbre to Tambi = 53\nArbre to Straylight = 40\nSnowdin to Tambi = 15\nSnowdin to Straylight = 99\nTambi to Straylight = 70")
(def test-input "London to Dublin = 464\nLondon to Belfast = 518\nDublin to Belfast = 141")


(defn parse-input [s]
  (->> s
    (str/split-lines)
    (map (fn mf [x]
           (-> (re-matches #"(\w+|\W+) to (\w+|\W+) = (\d+)" x)
             (update 3 #(Integer/parseInt % 10))
             (rest)
             (vec))))))


(defn distances [legs]
  (reduce
    (fn rf [m [a b x]]
      (-> m
        (assoc-in [a b] x)
        (assoc-in [b a] x)))
    {}
    legs))


(defn -f [input rank-fn]
  (let [all-legs  (parse-input input)
        MAP       (distances all-legs)
        all-nodes (->> all-legs (mapcat pop) (into #{}))
        start     (->> MAP
                    (keys)
                    (map (fn mf1 [from]
                           {:options (-> from MAP keys)
                            :total   0
                            :current from
                            :togo    (disj all-nodes from)
                            :path   [from]})))]
    (loop [done []
           todo start]
      (if (empty? todo)
        (->> done
          (filter #(-> % :togo empty?))
          (map :total)
          (apply rank-fn))

        (let [[route & todo] todo
              {:keys [:options :total :current :togo :path]} route
              routes  (->> options
                        (map (fn mf2 [to]
                               {:options (->> to MAP keys (filter togo))
                                :total   (+ total (get-in MAP [current to]))
                                :current to
                                :togo    (disj togo to)
                                :path    (conj path to)})))]
          (if (empty? options)
            (recur (conj done route) todo)
            (recur done (into todo routes))))))))

(defn f1 [s] (-f s min))
(defn f2 [s] (-f s max))

(assert (not= 719 (f1 input)))
(assert (= 207 (f1 input)))
(assert (= 605 (f1 test-input)))
(assert (= 982 (f2 test-input)))
(assert (= 804 (f2 input)))


