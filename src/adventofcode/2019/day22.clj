(ns adventofcode.2019.day22
  (:require [clojure.string :as str]
            [adventofcode.utils :as u]))


(def deck (range 10007))

(def input "deal with increment 10\ncut -5908\ndeal with increment 75\ncut 8705\ndeal with increment 49\ncut -1609\ndeal with increment 69\ncut 7797\ndeal into new stack\ncut -6211\ndeal with increment 10\ncut 6188\ndeal with increment 57\ncut -1659\ndeal with increment 16\ncut -5930\ndeal into new stack\ncut -2758\ndeal with increment 33\ncut -3275\ndeal with increment 39\ncut -1301\ndeal with increment 50\ncut 7837\ndeal with increment 74\ncut 1200\ndeal with increment 23\ndeal into new stack\ncut -9922\ndeal with increment 65\ncut 4896\ndeal with increment 61\ndeal into new stack\ncut 5945\ndeal with increment 9\ndeal into new stack\ndeal with increment 2\ncut -8205\ndeal with increment 75\ncut -4063\ndeal with increment 40\ndeal into new stack\ncut -7366\ndeal with increment 51\ncut 7213\ndeal into new stack\ncut 4763\ndeal with increment 43\ncut 3963\ndeal with increment 50\ncut -8856\ndeal with increment 43\ncut 8604\ndeal with increment 72\ncut -7026\ndeal into new stack\ndeal with increment 25\ncut 7843\ndeal with increment 71\ncut -1272\ndeal with increment 64\ncut 7770\ndeal with increment 18\ncut -5278\ndeal with increment 67\ndeal into new stack\ndeal with increment 18\ndeal into new stack\ncut 2216\ndeal with increment 42\ncut 3206\ndeal with increment 14\ndeal into new stack\ncut -6559\ndeal into new stack\ndeal with increment 12\ndeal into new stack\ndeal with increment 75\ndeal into new stack\ndeal with increment 41\ncut 7378\ndeal with increment 44\ncut 774\ndeal with increment 60\ncut 7357\ndeal with increment 41\ncut 479\ndeal with increment 40\ncut 5146\ndeal with increment 13\ncut 2017\ndeal into new stack\ndeal with increment 35\ncut 9218\ndeal into new stack\ndeal with increment 22\ncut -2462\ndeal with increment 23\ncut -1820\ndeal with increment 69")


(defn parse-line [s]
  (let [x (some->> s (re-seq #"-?\d+") first u/to-int)]
    (cond
      (str/starts-with? s "deal with increment") [::increment x]
      (str/starts-with? s "cut")                 [::cut x]
      (str/starts-with? s "deal into new stack") [::new-stack])))

(defn parse [s]
  (->> s
    str/split-lines
    (map parse-line)))

(parse input)


(set! *unchecked-math* true)
(set! *warn-on-reflection* true)


(defn step-forward [^long size ^long idx [op ^long x]]
  (case op
   ::new-stack (- size idx 1)
   ::increment (-> (* idx x) (mod size))
   ::cut       (-> (- idx x) (mod size))))

(defn shuffle-forward [size start-idx steps]
  (reduce (partial step-forward size) start-idx steps))

(defn f1 [input]
  (->> input parse (shuffle-forward 10007 2019)))

(assert (= 4649 (f1 input)))


(defn modinv [x y]
  (.modInverse (biginteger x) (biginteger y)))

(defn modpow [x y z]
  (.modPow (biginteger x) (biginteger y) (biginteger z)))

(defn step-backward [^long size ^long idx [op ^long x]]
  ;https://www.reddit.com/r/adventofcode/comments/ee0rqi/2019_day_22_solutions/fbnifwk/
  (case op
    ::new-stack (- size idx 1)
    ;return modinv(N, D) * i % D  # modinv is modular inverse:
    ::increment (-> (modinv x size) (* idx) (mod size))
    ::cut       (-> (+ size idx x) (mod size))))

;;To find such A and B, we just need two equations. In my case, I took X=2020 (my input), Y=f(2020), and Z=f(f(2020)). We have A*X+B = Y and A*Y+B = Z. Subtracting second from the first, we get A*(X-Y) = Y-Z and thus A = (Y-Z)/(X-Y). Once A is found, B is simply Y-A*X. In code form:
;
;X = 2020
;Y = f(X)
;Z = f(Y)
;A = (Y-Z) * modinv(X-Y+D, D) % D
;B = (Y-A*X) % D
;print(A, B)


(defn f2 [input]
  (let [size  (biginteger 119315717514047)
        times (biginteger 101741582076661)
        steps (-> input parse reverse)
        once  (fn [idx]
                (reduce
                  (partial step-backward size)
                  idx steps))
        modinv  #(modinv % size)
        modsize #(mod % size)
        X     2020
        Y     (once X)
        Z     (once Y)
        A     (modsize
                (* (- Y Z) (modinv (- X Y))))
        B     (modsize (- Y (* A X)))
        ;; (pow(A, n, D)*X + (pow(A, n, D)-1) * modinv(A-1, D) * B) % D)  ;; pow(A, n, D) = pow(A,n)%D
        res   (modsize (+
                         (* X (modpow A times size))
                         (* B (modinv (- A 1))  (dec (modpow A times size)))))]
    res))

(assert (= 68849657493596N (f2 input))) ;;fuck this puzzle

