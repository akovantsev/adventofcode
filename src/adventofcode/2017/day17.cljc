(ns adventofcode.2017.day17)


(def step 316)


;; p1
(loop [idx  0
       x    1
       coll [0]]
  (if (= 2018 x)
    (get coll (inc idx))
    (let [at (+ 1 (mod (+ step idx) x))]
      (recur at (inc x)
        (into
          (conj (subvec coll 0 at) x)
          (subvec coll at))))))

180

;;p2
(time
  (loop [idx  0
         x    1
         v    nil]
    (if (= 50000001 x)
      v
      (let [at (+ 1 (mod (+ step idx) x))]
        (recur at (inc x) (if (= at 1) x v))))))

13326437