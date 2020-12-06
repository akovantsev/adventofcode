(ns adventofcode.2017.day15)


(def t [65 8921])
(def i [618 814])

(defn makegen [factor]
  (fn gen [prev]
    (-> prev (* factor) (rem 2147483647))))

(def m (Math/pow 2 16))
(def A (makegen 16807))
(def B (makegen 48271))
(def tail #(mod % m))
(defn gen [g n]
  (rest (map tail (iterate g n))))

(defn f1 [[a b]]
  (->>
    (map = (gen A a) (gen B b))
    (sequence
      (comp
        (take 40000000)
        (filter true?)))
    (count)))

#_(time (assert (= 588 (f1 t))))
#_(time (assert (= 577 (f1 i))))

(defn f2 [[a b]]
  (->>
    (map =
      (filter #(zero? (mod % 4)) (gen A a))
      (filter #(zero? (mod % 8)) (gen B b)))
    (sequence
      (comp
        (take 5000000)
        (filter true?)))
    (count)))

#_(time (assert (= 309 (f2 t))))
#_(time (assert (= 316 (f2 i))))