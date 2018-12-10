(ns adventofcode.2015.day19
  (:require [clojure.string :as str])
  (:import [clojure.lang PersistentQueue]))


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


(defn partition-by-elements [s] ;;fixme merge results to reflect available replacements
  (reduce
    (fn [els ch]
      (if (Character/isUpperCase ^Character ch)
        (conj els (str ch))
        (conj (pop els) (str (peek els) ch))))
    [] s))


#_
(let [[molecule pairs] (parse-input input)
      mc          (count molecule)
      too-large?  #(-> % count (> mc))
      empty-stack []];PersistentQueue/EMPTY]
  (loop [banned   (transient #{})
         stack    (conj empty-stack [0 "e"])
         unlocked ""
         locked   (partition-by-elements molecule)]
    (let [[n s]      (peek stack)
          new-banned (conj! banned s)]
      (prn [unlocked (count banned) n s])
      (cond
        (empty? stack)
        :did-not-find-solution

        (= s molecule)
        n

        (not (str/starts-with? s unlocked))
        (recur new-banned (pop stack) unlocked locked)

        :else
        (let [replace (fn mf [[a b]]
                        (str/replace-first s (re-pattern a) b))
              new-unlocked  (str unlocked (first locked))
              gf (fn gf [s]
                   (cond
                     (too-large? s) :ba
                     (banned s)     :ba
                     (str/starts-with? s new-unlocked) :br
                     (str/starts-with? s unlocked)     :to
                     :else :ba))

              {breakthrough :br
               banned       :ba
               todo         :to} (->> pairs
                                   (get-all-replacements s)
                                   (group-by gf))]
          (if breakthrough
            (recur
              (reduce conj! new-banned (concat todo banned))
              (->> breakthrough
                (map vector (repeat (inc n)))
                (into (pop stack)))
              new-unlocked
              (rest locked))
            (recur
              (reduce conj! new-banned banned)
              (->> todo
                (map vector (repeat (inc n)))
                (into (pop stack)))
              unlocked
              locked)))))))
