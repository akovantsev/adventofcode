(ns adventofcode.2019.day14
  (:require [clojure.string :as str]
            [adventofcode.utils :as u]))


(def input "2 LGNW, 1 FKHJ => 3 KCRD\n5 FVXTS => 5 VSVK\n1 RBTG => 8 FKHJ\n2 TLXRM, 1 VWJSD => 8 CDGX\n1 MVSL, 2 PZDR, 9 CHJRF => 8 CLMZ\n11 BMSFK => 5 JMSWX\n10 XRMC => 1 MQLFC\n20 ZPWQB, 1 SBJTD, 9 LWZXV => 4 JFZNR\n2 FVXTS => 3 FBHT\n10 ZPWQB => 8 LGNW\n5 WBDGL, 16 KZHQ => 2 FVXTS\n124 ORE => 7 BXFVM\n5 KCRD => 1 RNVMC\n5 CGPZC, 4 WJCT, 1 PQXV => 8 VKQXP\n4 KFVH => 4 FGTKD\n11 QWQG => 6 LWZXV\n9 ZMZPB, 8 KFVH, 5 FNPRJ => 3 VKVP\n1 LFQW, 8 PQXV, 2 TLXRM, 1 VKQXP, 1 BMSFK, 1 QKJPV, 3 JZCFD, 8 VWJSD => 6 WXBC\n2 SLDWK, 32 JZCFD, 10 RNVMC, 1 FVXTS, 34 LGTX, 1 NTPZK, 1 VKQXP, 1 QTKL => 9 LDZV\n31 FBHT => 2 BMSFK\n35 KZHQ, 3 ZPWQB => 3 PCNVM\n6 DRSG, 1 TDRK, 1 VSVK => 2 VWJSD\n3 DGMH => 3 ZPWQB\n162 ORE => 9 RBTG\n11 LFQW, 1 LPQCK => 8 LGTX\n8 MQLFC => 1 SBJTD\n1 KGTB => 9 TGNB\n1 BXFVM, 1 ZMZPB => 8 FNPRJ\n1 PCNVM, 15 ZSZBQ => 4 PQXV\n15 XRMC => 9 ZSZBQ\n18 VWJSD, 12 CHJRF => 6 KTPH\n8 RBTG, 5 ZMZPB => 6 KFVH\n6 SLDWK => 1 XVTRS\n3 VSVK, 6 BMSFK, 3 NTPZK => 1 JZCFD\n3 FVXTS, 2 MTMKN => 5 CHJRF\n9 FNPRJ => 2 QWQG\n1 FBHT, 1 MVSL, 1 FNPRJ => 1 DRSG\n35 LPQCK, 19 LWZXV, 28 LGNW => 5 TLXRM\n5 NKMV => 3 QKJPV\n3 MGZM, 2 TGNB => 8 PZDR\n2 FKHJ => 2 WBDGL\n1 NKMV => 1 KGTB\n129 ORE => 7 ZMZPB\n3 LMNQ, 2 BMSFK, 4 RNVMC, 4 KGTB, 4 DRSG, 2 JFZNR, 7 QTKL => 4 CKQZ\n1 MQLFC => 7 MGZM\n7 SLDWK, 2 KCRD => 4 WJCT\n1 QKJPV => 4 LPQCK\n1 JFZNR => 6 TDRK\n4 CLMZ, 1 LGTX => 9 PMSZG\n6 QWQG => 8 CGPZC\n10 QWQG => 6 LMNQ\n2 PMSZG, 1 VKVP => 3 QTKL\n2 DGMH => 8 KZHQ\n14 RBTG => 9 DGMH\n62 RNVMC, 4 KTPH, 20 XVTRS, 7 JZCFD, 18 CDGX, 13 WXBC, 14 LDZV, 2 CKQZ, 33 FNPRJ => 1 FUEL\n8 KGTB, 1 JMSWX => 7 NTPZK\n1 VKVP, 7 DGMH => 7 NKMV\n4 LPQCK => 5 MVSL\n6 KGTB => 2 LFQW\n2 FGTKD => 9 SLDWK\n1 WBDGL, 1 ZMZPB, 1 DGMH => 6 XRMC\n4 VKVP => 7 MTMKN")

(defn parse-one [s]
  (let [[n x] (str/split s #"\s")]
    [(u/to-int n) x]))

(defn parse-row [s]
  (let [[L R] (str/split s #" => ")
        LS    (str/split s #", ")]
    [(parse-one R)
     (->> LS
       (mapv parse-one)
       (reduce
         (fn [m [n x]]
           (assoc m x n))
         {}))]))

(defn parse [s]
  (->> s
    str/split-lines
    (mapv parse-row)
    (reduce
      (fn f1 [m [[n x] cost]]
        (assoc m x {::amount n ::cost cost}))
      {})))

(parse input)


(def sample1 "10 ORE => 10 A\n1 ORE => 1 B\n7 A, 1 B => 1 C\n7 A, 1 C => 1 D\n7 A, 1 D => 1 E\n7 A, 1 E => 1 FUEL")
(def sample2 "9 ORE => 2 A\n8 ORE => 3 B\n7 ORE => 5 C\n3 A, 4 B => 1 AB\n5 B, 7 C => 1 BC\n4 C, 1 A => 1 CA\n2 AB, 3 BC, 4 CA => 1 FUEL")
(def sample3 "157 ORE => 5 NZVS\n165 ORE => 6 DCFZ\n44 XJWVT, 5 KHKGT, 1 QDVJ, 29 NZVS, 9 GPVTF, 48 HKGWZ => 1 FUEL\n12 HKGWZ, 1 GPVTF, 8 PSHF => 9 QDVJ\n179 ORE => 7 PSHF\n177 ORE => 5 HKGWZ\n7 DCFZ, 7 PSHF => 2 XJWVT\n165 ORE => 2 GPVTF\n3 DCFZ, 7 NZVS, 5 HKGWZ, 10 PSHF => 8 KHKGT")
(def sample4 "2 VPVL, 7 FWMGM, 2 CXFTF, 11 MNCFX => 1 STKFG\n17 NVRVD, 3 JNWZP => 8 VPVL\n53 STKFG, 6 MNCFX, 46 VJHF, 81 HVMC, 68 CXFTF, 25 GNMV => 1 FUEL\n22 VJHF, 37 MNCFX => 5 FWMGM\n139 ORE => 4 NVRVD\n144 ORE => 7 JNWZP\n5 MNCFX, 7 RFSQX, 2 FWMGM, 2 VPVL, 19 CXFTF => 3 HVMC\n5 VJHF, 7 MNCFX, 9 VPVL, 37 CXFTF => 6 GNMV\n145 ORE => 6 MNCFX\n1 NVRVD => 8 CXFTF\n1 VJHF, 6 MNCFX => 4 RFSQX\n176 ORE => 6 VJHF")
(def sample5 "171 ORE => 8 CNZTR\n7 ZLQW, 3 BMBT, 9 XCVML, 26 XMNCP, 1 WPTQ, 2 MZWV, 1 RJRHP => 4 PLWSL\n114 ORE => 4 BHXH\n14 VRPVC => 6 BMBT\n6 BHXH, 18 KTJDG, 12 WPTQ, 7 PLWSL, 31 FHTLT, 37 ZDVW => 1 FUEL\n6 WPTQ, 2 BMBT, 8 ZLQW, 18 KTJDG, 1 XMNCP, 6 MZWV, 1 RJRHP => 6 FHTLT\n15 XDBXC, 2 LTCX, 1 VRPVC => 6 ZLQW\n13 WPTQ, 10 LTCX, 3 RJRHP, 14 XMNCP, 2 MZWV, 1 ZLQW => 1 ZDVW\n5 BMBT => 4 WPTQ\n189 ORE => 9 KTJDG\n1 MZWV, 17 XDBXC, 3 XCVML => 2 XMNCP\n12 VRPVC, 27 CNZTR => 2 XDBXC\n15 KTJDG, 12 BHXH => 5 XCVML\n3 BHXH, 2 VRPVC => 7 MZWV\n121 ORE => 7 VRPVC\n7 XCVML => 6 RJRHP\n5 BHXH, 4 VRPVC => 5 LTCX")

(parse sample1)

(defn reaction [db x n]
  (let [n (long n)
        {::keys [amount cost]} (db x)]
    (cond
      (= n amount) cost
      (< n amount) (assoc cost x (- n amount))
      (> n amount)
      (let [times (+ (quot n amount) (if (zero? (rem n amount)) 0 1))
            cost  (reduce-kv
                    (fn f [m k v] (assoc m k (* times v)))
                    {} cost)]
        (assoc cost x (- (rem (* times amount) n)))))))

(assert (= {"ORE" 20 "A" -6} (reaction (parse sample1) "A" 14)))

(defn next-kv [m]
  (->> m (filter #(-> % val pos?)) first))


(defn make [db chemicals]
  (loop [todo  chemicals
         ore   0]
    (if (->> todo vals (not-any? pos?))
      {::ore ore ::surplus (->> todo (filter #(-> % val neg?)) (into {}))}
      (let [[x n] (next-kv todo)
            cost  (reaction db x n)
            todo  (dissoc todo x)
            todo  (merge-with + todo cost)
            ore   (-> "ORE" (todo 0) (+ ore))
            todo  (dissoc todo "ORE")]
        (recur todo ore)))))

(defn f1 [input] (-> input parse (make {"FUEL" 1}) ::ore))


(assert (= 31 (f1 sample1)))
(assert (= 165 (f1 sample2)))
(assert (= 13312 (f1 sample3)))
(assert (= 180697 (f1 sample4)))
(assert (= 2210736 (f1 sample5)))
(assert (= 443537 (time (f1 input))))


(defn f2 [input]  ;; melts cpu and ram :D
  (let [db (parse input)]
    (loop [surplus {}
           fuel    0
           ORE     1000000000000]
      (cond
        (zero? ORE) fuel
        (neg?  ORE) (dec fuel)
        (pos?  ORE) (let [{::keys [ore surplus]} (make db surplus)]
                      (recur surplus (inc fuel) (- ORE ore)))))))

(defn f3 [input]
  (let [db             (parse input)
        budget         1000000000000
        cost-of-1      (-> db (make {"FUEL" 1}) ::ore)
        max-multiplier (quot budget cost-of-1)
        pows-of-10     (-> max-multiplier str count dec)]
    (loop [leftovers {}
           pow10     pows-of-10
           budget    budget
           fuel      0]
      (if (neg? pow10)
        (long fuel)
        (let [n (Math/pow 10 pow10)
              {::keys [ore surplus]} (make db (assoc leftovers "FUEL" n))]
          ;(prn {:pow pow10 :n n :fuel fuel :budget budget :ore ore :left leftovers})
          (cond
            ;(= leftovers surplus) :yo
            (= budget ore) (+ fuel n)
            (< budget ore) (recur leftovers (dec pow10) budget fuel)
            (> budget ore) (recur surplus pow10 (- budget ore) (+ fuel n))))))))


(assert (= 82892753 (f3 sample3)))
(assert (= 5586022 (f3 sample4)))
(assert (= 460664 (f3 sample5)))
(assert (= 2910558 (time (f3 input))))