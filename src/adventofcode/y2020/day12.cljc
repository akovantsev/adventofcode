(ns adventofcode.y2020.day12)


(def t "F10\nN3\nF7\nR90\nF11")
(def i "S1\nF17\nS3\nF56\nW5\nF11\nN4\nF94\nW4\nS1\nL180\nE2\nF38\nS3\nF46\nL90\nN2\nS3\nW3\nL90\nE1\nL180\nF73\nL90\nE1\nN3\nR90\nS5\nE5\nL90\nW2\nF1\nE5\nR270\nW3\nS4\nW5\nF55\nE2\nL90\nW5\nL90\nE1\nL90\nF9\nW5\nF21\nE4\nF63\nE1\nF48\nN1\nF80\nE5\nL90\nN1\nE1\nS4\nR180\nF48\nF87\nN5\nW2\nF90\nS4\nE5\nF76\nF37\nR180\nE5\nF51\nS3\nR90\nF79\nF25\nL90\nW1\nF100\nS2\nE3\nL90\nN1\nW4\nF41\nS5\nL90\nW5\nL90\nW2\nS4\nW3\nF75\nL90\nF20\nE5\nN5\nR90\nF99\nN4\nN1\nL90\nN5\nN2\nE2\nS4\nF29\nL180\nN1\nW3\nL90\nW3\nL180\nF84\nF25\nE1\nS3\nR90\nF64\nL90\nE1\nF40\nN3\nR90\nN3\nF67\nF37\nL90\nF17\nE4\nF87\nN3\nW5\nS2\nR90\nE4\nN1\nW4\nF75\nE2\nF18\nR90\nF6\nS4\nF13\nE1\nL270\nF50\nW2\nR90\nW4\nS5\nR90\nF14\nL180\nN5\nL180\nF78\nE1\nS3\nR90\nE2\nR270\nE4\nS1\nL90\nL180\nF91\nW3\nW5\nF45\nN4\nF44\nL90\nN3\nL90\nS2\nL90\nF76\nW3\nR90\nF3\nW3\nF24\nL90\nF83\nW2\nF19\nS4\nL90\nN4\nE5\nL180\nN5\nW5\nF67\nS3\nR90\nE4\nF51\nL90\nE5\nL90\nF79\nE2\nN2\nW4\nE2\nL90\nF41\nE4\nN2\nL90\nN2\nL90\nN3\nF51\nF79\nN2\nW2\nN5\nW4\nF60\nN3\nR90\nW4\nS1\nF59\nW1\nF3\nN5\nL180\nN5\nF23\nN4\nE3\nR90\nF14\nW4\nF86\nL90\nN2\nS1\nW3\nR90\nN1\nF25\nL90\nF22\nE5\nF88\nL270\nF14\nN5\nF32\nN1\nF98\nS1\nW2\nE5\nL180\nE4\nS2\nF46\nL180\nW4\nF87\nE2\nF83\nR90\nS5\nF68\nE5\nF95\nN1\nF43\nE2\nF64\nS3\nF5\nS1\nW3\nL90\nE4\nL90\nF63\nS3\nF44\nN4\nR90\nF95\nS5\nW1\nN4\nW4\nF87\nW3\nN3\nL180\nW4\nR180\nN2\nE2\nN2\nL90\nW5\nR90\nW1\nF22\nL180\nN4\nW1\nS1\nR90\nN3\nR180\nS5\nW4\nR90\nE3\nS1\nE3\nN1\nL270\nF96\nE2\nN1\nF98\nW4\nL90\nW1\nW4\nR90\nW2\nS3\nF64\nS3\nF67\nN2\nR180\nS5\nF13\nN4\nF53\nW1\nN1\nL90\nF54\nS1\nE2\nF28\nR90\nW3\nL90\nE3\nN4\nF34\nR90\nF51\nL90\nF24\nR180\nW4\nN2\nF88\nF78\nS2\nE1\nN2\nL180\nF58\nE4\nR90\nN4\nL90\nN5\nR180\nN5\nR270\nW3\nF41\nS4\nF61\nR90\nF71\nS1\nF9\nR90\nF47\nW2\nN4\nL180\nW5\nF52\nL90\nN4\nF11\nW3\nL90\nW5\nN4\nF10\nS1\nF75\nS5\nL90\nS2\nF28\nL90\nF80\nE3\nR180\nE4\nF42\nE4\nF85\nN1\nW4\nR90\nE2\nF38\nR90\nF77\nF95\nL90\nS5\nF86\nR90\nN1\nE2\nR90\nN3\nF28\nR180\nN5\nF25\nE3\nF52\nN4\nE3\nN4\nE3\nF37\nL180\nN1\nE2\nL180\nE5\nF82\nN4\nF100\nN3\nE3\nL90\nW3\nS1\nR270\nE5\nW5\nR90\nF44\nF15\nR180\nS3\nL90\nE4\nF44\nR90\nN1\nE4\nN4\nF62\nR90\nS4\nL180\nS1\nF13\nE3\nF70\nL270\nF52\nL270\nF80\nL90\nF89\nN4\nF79\nW5\nF55\nR90\nF62\nE4\nF91\nR180\nE2\nF74\nR90\nF18\nL90\nE3\nN5\nE2\nR90\nW2\nR180\nN3\nF69\nW3\nR90\nF92\nE2\nF48\nN2\nL90\nS3\nF49\nE3\nL180\nF6\nR90\nS5\nF58\nF67\nE5\nS2\nL90\nW5\nF3\nS1\nF73\nW1\nE4\nR180\nN3\nE2\nF72\nL90\nS1\nF31\nW1\nF44\nL180\nF100\nW3\nN1\nW5\nL90\nS2\nN3\nR90\nW4\nS5\nF62\nE5\nF55\nL180\nW2\nL90\nE1\nF45\nS3\nW2\nF49\nN4\nL90\nE3\nN2\nR180\nE5\nN1\nN5\nE5\nR90\nW3\nF73\nE3\nF66\nL90\nS2\nE3\nF100\nS3\nW2\nS4\nW5\nR180\nE4\nS3\nS1\nW2\nR90\nF22\nR90\nF76\nL270\nS5\nF95\nL90\nN3\nF16\nR270\nS3\nN5\nF66\nR90\nF63\nW5\nF37\nN2\nL90\nW1\nF68\nR90\nF98\nE4\nS3\nR90\nE2\nS1\nF91\nE1\nF42\nE1\nF13\nS4\nF10\nR180\nE4\nR90\nN2\nW4\nL180\nN5\nR90\nN3\nF26\nR90\nF42\nL180\nE5\nS3\nL90\nS3\nE3\nF79\nR270\nE4\nF54\nN5\nW3\nF16\nW1\nS3\nR90\nF100\nS1\nW3\nN2\nE1\nS4\nF1\nR180\nF66\nW2\nR90\nW2\nF27\nE2\nF16\nR90\nS3\nF61\nE1\nF4\nL90\nN5\nF45\nL90\nN1\nL90\nF50\nW1\nR90\nN1\nF44\nS2\nF53\nS5\nF59\nE4\nS3\nE2\nN5\nE2\nL90\nE4\nL90\nN1\nL90\nN5\nF31\nR180\nN2\nF18\nE2\nF27\nL90\nF57\nR90\nF40\nR180\nE2\nF94\nS3\nE2\nS4\nE4\nR90\nL90\nE5\nN5\nE1\nN3\nF97\nS3\nF16\nE3\nW5\nF80\nS1\nW2\nS4\nF18\nN4\nE5\nR90\nN1\nF43\nN4\nE4\nN1\nE2\nW1\nF99\nW4\nF79\nF20\nE3\nF63\nW4\nF21\nE1\nF82\nE3\nR90\nF8\nN5\nW2\nL180\nW5\nF13\nS2\nL90\nN3\nR180\nN4\nF9\nL90\nF39")
(defn parse [input]
  (->> input (re-seq #"(\d+)|(\w)") (map first) (map read-string) (partition 2)))

(defn p [input stepf init]
  (->> input parse (reduce stepf init) (take 2) (map #(Math/abs %)) (reduce +)))

(def turn
  {'L {'N {90 'W 180 'S 270 'E}
       'W {90 'S 180 'E 270 'N}
       'S {90 'E 180 'N 270 'W}
       'E {90 'N 180 'W 270 'S}}
   'R {'N {90 'E 180 'S 270 'W}
       'W {90 'N 180 'E 270 'S}
       'S {90 'W 180 'N 270 'E}
       'E {90 'S 180 'W 270 'N}}})


(defn step [[x y facing] [op n]]
  (case op
    W [(- x n) y facing]
    E [(+ x n) y facing]
    N [x (- y n) facing]
    S [x (+ y n) facing]
    F (step [x y facing] [facing n])
    [x y (get-in turn [op facing n])]))


(defn move [op n m d]
  (case [op d]
    ([N N] [S S] [W W] [E E]) [(+ m n) d]
    ([N S] [S N] [W E] [E W]) [(- m n) d]
    #_else                    [m d]))

(defn step2 [[x y [n1 d1] [n2 d2]] [op n]]
  (case op
    (W E N S) [x y (move op n n1 d1) (move op n n2 d2)]
    (L R)     [x y [n1 (get-in turn [op d1 n])] [n2 (get-in turn [op d2 n])]]
    F         (let [[x y] (-> [x y]
                            (step [d1 (* n n1)])
                            (step [d2 (* n n2)]))]
                [x y [n1 d1] [n2 d2]])))


(defn p1 [input] (p input step [0 0 'E]))
(defn p2 [input] (p input step2 [0 0 [10 'E] [1 'N]]))

(assert (= (p1 t) 25))
(assert (= (p1 i) 441))

(assert (= (p2 t) 286))
(assert (= (p2 i) 40014))