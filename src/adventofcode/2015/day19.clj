(ns adventofcode.2015.day19
  (:require [clojure.string :as str]))


(def input "Al => ThF\nAl => ThRnFAr\nB => BCa\nB => TiB\nB => TiRnFAr\nCa => CaCa\nCa => PB\nCa => PRnFAr\nCa => SiRnFYFAr\nCa => SiRnMgAr\nCa => SiTh\nF => CaF\nF => PMg\nF => SiAl\nH => CRnAlAr\nH => CRnFYFYFAr\nH => CRnFYMgAr\nH => CRnMgYFAr\nH => HCa\nH => NRnFYFAr\nH => NRnMgAr\nH => NTh\nH => OB\nH => ORnFAr\nMg => BF\nMg => TiMg\nN => CRnFAr\nN => HSi\nO => CRnFYFAr\nO => CRnMgAr\nO => HP\nO => NRnFAr\nO => OTi\nP => CaP\nP => PTi\nP => SiRnFAr\nSi => CaSi\nTh => ThCa\nTi => BP\nTi => TiTi\ne => HF\ne => NAl\ne => OMg\n\nCRnSiRnCaPTiMgYCaPTiRnFArSiThFArCaSiThSiThPBCaCaSiRnSiRnTiTiMgArPBCaPMgYPTiRnFArFArCaSiRnBPMgArPRnCaPTiRnFArCaSiThCaCaFArPBCaCaPTiTiRnFArCaSiRnSiAlYSiThRnFArArCaSiRnBFArCaCaSiRnSiThCaCaCaFYCaPTiBCaSiThCaSiThPMgArSiRnCaPBFYCaCaFArCaCaCaCaSiThCaSiRnPRnFArPBSiThPRnFArSiRnMgArCaFYFArCaSiRnSiAlArTiTiTiTiTiTiTiRnPMgArPTiTiTiBSiRnSiAlArTiTiRnPMgArCaFYBPBPTiRnSiRnMgArSiThCaFArCaSiThFArPRnFArCaSiRnTiBSiThSiRnSiAlYCaFArPRnFArSiThCaFArCaCaSiThCaCaCaSiRnPRnCaFArFYPMgArCaPBCaPBSiRnFYPBCaFArCaSiAl")
(def test-input "e => H\ne => O\nH => HO\nH => OH\nO => HH\n\nHOH")

(defn parse-input [input]
  (let [[replacements molecule] (str/split input #"\n\n")
        repls (->> replacements
                str/split-lines
                (map #(str/split % #" => ")))]
    [molecule repls]))

(defn get-replacements [s [a b]]
  ;; because
  ;;(str/split "xax" #"x")  => ["" "a"]
  (let [n (count a)]
    (loop [idx   (str/index-of s a)
           repls #{}]
      (if (nil? idx)
        repls
        (let [to   (+ idx n)
              repl (str (subs s 0 idx) b (subs s to))]
          (recur
            (str/index-of s a to)
            (conj repls repl)))))))

(defn get-all-replacements [s pairs]
  (->> pairs
    (map (partial get-replacements s))
    (reduce into #{})))


(defn f1 [input]
  (let [[molecule pairs] (parse-input input)]
    (count (get-all-replacements molecule pairs))))


(assert (= 4 (f1 test-input)))
(assert (= 518 (f1 input)))


(defn get-all-replacements2 [s reps]
  ;; greedy replace-first lol
  (->> reps
    (map (fn [[k v]]
           (when (str/includes? s k)
             (str/replace-first s k v))))
    (remove nil?)
    (sort-by count >)))


(defn f2 [input]
  (time
    (let [[molecule pairs] (parse-input input)
          reps             (->> pairs (map reverse) (map vec) (sort-by #(-> % first count) >))]
      (loop [todo  [[0 molecule]]
             ban   (transient #{})]
        (let [[n s] (peek todo)
              todo  (pop todo)]
          (cond
            (= s "e")             n
            (contains? ban s)     (recur todo ban)
            (str/index-of s "e")  (recur todo (conj! ban s))
            :else                 (let [ss (map vector
                                             (repeat (inc n))
                                             (get-all-replacements2 s reps))]
                                    (recur (into todo ss) (conj! ban s)))))))))

(assert (= 200 (f2 input)))
"Elapsed time: 9.255621 msecs" ;;lol

