(ns adventofcode.2019.day25)

(def t [5764801 17807724])
(def i [17115212 3667832])


(defn step [sn x]
  (-> x (* sn) (mod 20201227)))

(defn encription-key [pk loop-size]
  (->> 1
    (iterate (partial step pk))
    (drop loop-size)
    (first)))

(defn find-loop-size [pk]
  (->> 1
    (iterate (partial step 7))
    (take-while #(not= % pk))
    (count)))

(defn solve [input]
  (map encription-key
    (reverse input)
    (map find-loop-size input)))

(solve i)