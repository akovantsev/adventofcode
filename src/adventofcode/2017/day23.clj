(ns adventofcode.2017.day23
  (:require [adventofcode.2017.day18 :as d18]
            [clojure.string :as str]
            [adventofcode.utils :as u]))




(def i "set b 79\nset c b\njnz a 2\njnz 1 5\nmul b 100\nsub b -100000\nset c b\nsub c -17000\nset f 1\nset d 2\nset e 2\nset g d\nmul g e\nsub g b\njnz g 2\nset f 0\nsub e -1\nset g e\nsub g b\njnz g -8\nsub d -1\nset g d\nsub g b\njnz g -13\njnz f 2\nsub h -1\nset g b\nsub g c\njnz g 2\njnz 1 3\nsub b -17\njnz 1 -23")


(defn doop [db]
  (let [[op x y] (get (:ins db) (:idx db))
        db (update db :log conj op)
        v  #(cond-> % (symbol? %) db)
        vx (v x)
        vy (v y)]
    ;(prn [op x y])
    (case op
      set (-> db (update :idx + 1) (assoc x vy))
      sub (-> db (update :idx + 1) (update x - vy))
      mul (-> db (update :idx + 1) (update x * vy))
      jnz (-> db (update :idx + (if (zero? vx) 1 vy))))))

(defn make-db [input]
  (let [instrs (->> input
                 str/split-lines
                 (mapv #(mapv read-string (str/split % #"\s"))))
        len    (count instrs)
        exit?  (fn [db]
                 (let [i (:idx db)]
                   (or (< i 0) (<= len i))))]
    (assoc
     (zipmap
       (->> "abcdefgh" (map str) (map symbol))
       (repeat 0))
     :idx 0
     :ins instrs
     :exit? exit?)))

(make-db i)

(defn p [db]
  (mapv db ['a 'b 'c 'd 'e 'f 'g 'h]))

(defn step [db]
  (-> db doop (u/spyf> p)))

(let [db (make-db i)]
  (->> db
    (iterate step)
    (drop-while (complement (:exit? db)))
    (first)
    :log
    (filter #{'mul})
    (count)))

5929

(set! *print-length* 30)

;#_
(let [db   (make-db i)
      step #(-> % doop (u/spyf> p))]
  (->> db
    (#(assoc % 'a 1))
    (iterate step)
    (drop 100)
    ;(drop-while (complement (:exit? db)))
    (first)
    p))

(not= 1)

;thanks reddit:
;;Looking at this, then h is the number of composite numbers [not prime]
;; between the lower limit and the upper limit, counting by 17.

(defn not-prime? [n]
  (some #(->> % (mod n) zero?) (range 2 (-> n (/ 2) (Math/ceil) int))))

(not-prime? 11)

;[1 107900 124900 2 12 1 -107888 0]
;[1 107900 124900 2 12 1 -107888 0]
;[1 107900 124900 2 12 1 2 0]
;[1 107900 124900 2 12 1 24 0]
;[1 107900 124900 2 12 1 -107876 0]
;[1 107900 124900 2 12 1 -107876 0]
;[1 107900 124900 2 13 1 -107876 0]
;[1 107900 124900 2 13 1 13 0]
;[1 107900 124900 2 13 1 -107887 0]
;[1 107900 124900 2 13 1 -107887 0]
;[1 107900 124900 2 13 1 2 0]
;[1 107900 124900 2 13 1 26 0]

(->> (range 107900 (inc 124900) 17)
  (filter not-prime?)
  (count))



