(ns adventofcode.y2020.day08
  (:require [clojure.string :as str]))

(def i "acc +37\nacc -1\nnop +512\nacc +0\njmp +60\nacc -3\nnop +317\njmp +130\nacc +22\nacc +34\njmp +486\nacc -18\nnop +610\nacc -14\nnop +274\njmp +439\nacc -6\nacc -1\nacc -4\nacc +7\njmp +175\nnop +179\njmp +197\njmp +76\nacc -1\nacc +24\njmp +472\nacc +8\nacc -15\nacc +0\njmp +551\nacc +46\nacc +27\njmp +1\nacc +45\njmp +153\nacc +14\njmp +207\njmp +1\njmp +557\nnop +424\njmp +571\nnop -19\nnop +9\nacc +2\nacc +29\njmp +14\nacc -10\nacc +43\nacc +43\njmp +75\njmp +311\njmp +517\nacc -6\nacc +13\njmp +140\nnop +348\nacc +0\njmp +275\njmp +52\njmp +110\nacc +11\nacc +15\njmp +13\nacc +7\njmp +216\njmp +174\nnop +24\nacc -16\nacc +46\nacc -17\njmp +519\nacc -15\nacc +46\nacc -4\njmp +309\nacc +14\nacc +36\nacc -15\njmp +244\nacc +37\nacc +43\njmp +146\njmp +260\njmp +217\nacc +39\njmp +454\nnop +16\nnop +255\nacc +31\njmp +13\nacc +38\nacc +45\nacc +24\njmp +534\nacc +13\nacc +44\nacc +34\njmp +286\njmp +1\nacc -12\njmp -45\njmp +147\nacc -2\nacc +24\nnop +391\nacc +11\njmp +242\nacc +1\nacc +28\njmp +423\nacc +5\njmp +319\nacc +45\nnop +350\nacc +34\nacc +7\njmp +47\nnop +32\nacc +2\nacc +0\njmp +252\nacc -4\nacc +23\njmp +452\nacc -5\nacc +48\njmp -104\nacc +38\njmp +172\nacc +7\nacc +31\njmp +5\nacc +19\nacc +12\nacc +26\njmp +232\nacc -12\nnop +121\nnop +80\nacc +46\njmp +126\njmp +82\nnop +69\njmp -128\nacc +18\nacc +45\nacc +14\nacc +45\njmp +219\njmp +422\nacc +2\nacc +40\nacc +13\njmp +450\nacc +48\njmp +172\nacc +19\nacc -10\njmp +69\nnop +336\nnop -6\njmp +265\njmp -136\njmp +350\nacc +31\nacc +39\nnop +389\nnop +404\njmp +16\nacc +13\nnop -41\nacc -2\nacc -14\njmp +159\njmp -111\nacc +40\nacc +36\nacc -17\njmp -143\nacc +36\nacc +29\nacc +19\nacc +0\njmp +159\njmp +279\nacc +31\njmp +346\nacc +15\nnop +173\nacc +48\njmp -183\nacc +16\nacc +31\njmp +418\nacc -13\njmp +280\nacc +30\nnop +229\njmp -139\nacc +0\nacc +9\njmp +354\nacc +12\njmp +310\njmp -129\nacc -8\njmp -96\nacc -3\nacc +1\njmp +51\njmp +303\nacc +28\njmp -186\nacc +36\nacc -10\nnop +72\nnop +345\njmp +200\nacc +6\nacc -14\njmp +87\nnop +318\njmp +273\nnop +309\nacc +50\njmp +147\njmp +387\nacc +38\nnop -169\nacc +44\njmp +28\nnop +208\nnop +43\nacc +26\nacc -13\njmp -160\njmp +233\nacc +22\njmp +357\njmp +374\nacc -6\nacc +38\njmp +100\njmp -36\nacc +38\nnop +330\nacc +46\njmp -43\nacc +34\nnop +239\nacc +45\nacc +15\njmp +48\nacc +49\nacc +20\nacc -5\nacc +41\njmp +70\njmp +211\njmp +144\nacc +29\nacc +36\nacc -15\njmp -24\njmp +1\njmp -17\nacc -18\nacc +27\nacc +34\njmp -21\njmp +1\nacc +35\nacc -5\nacc +24\njmp +337\nnop -240\njmp +180\nacc -1\nnop +49\njmp +260\nacc +40\nacc +42\njmp -165\nacc +31\nacc +30\nnop -234\njmp +27\nacc +45\nacc +48\nacc +44\nacc -19\njmp +70\nacc +20\nacc +18\njmp +219\nacc +46\njmp -85\nacc +43\nacc +21\njmp -4\nacc +37\nacc +26\nacc +16\njmp -257\nacc +39\nacc +7\njmp -260\nacc +42\nacc +10\nacc +36\nacc +47\njmp +2\njmp -249\nacc +20\nacc -1\nacc +21\njmp +74\njmp +31\nacc +32\njmp +64\nacc +34\njmp -255\nacc -8\nacc -2\nacc +26\njmp -102\njmp +229\nacc -14\nacc +25\njmp -154\nacc -15\njmp -92\nnop -37\nacc -5\nacc +50\nacc +43\njmp +73\nacc +1\nacc -17\nacc +19\nacc +24\njmp -319\nnop -225\njmp -304\nacc +49\nacc +5\nacc -17\njmp +14\nacc +42\nacc -9\nacc -10\nacc +45\njmp -125\njmp -46\nacc +13\nacc +11\nnop +199\nacc -19\njmp -159\nacc +1\njmp +253\nacc +7\njmp +233\nnop -76\nacc +31\nacc +44\njmp -18\nacc +47\nnop +227\njmp +178\nnop -22\njmp -44\njmp +24\nnop +122\nacc +20\nacc +43\njmp -81\nacc -15\nacc +10\nacc +40\njmp +108\nacc +45\njmp +35\nacc +44\njmp +36\nnop -2\nnop -320\njmp +1\nacc +47\njmp -6\nacc -16\nacc +49\nnop +56\njmp +104\nacc +40\njmp -159\nacc +30\njmp +56\nacc +47\nacc -6\nacc +47\nacc +2\njmp -102\nacc +45\njmp -262\nacc +36\nacc +42\nacc -17\njmp -90\nacc +18\nnop +7\nacc -14\njmp -194\nacc +16\nacc +31\nacc +26\njmp -257\nacc +25\njmp -367\njmp +69\nnop -102\nacc +47\njmp -356\nnop -105\nacc +6\njmp -42\nacc +40\njmp -368\nacc +42\njmp +84\nacc +17\nacc +14\nacc -17\nacc -14\njmp -80\nacc +42\nacc +11\nacc -14\njmp -77\nacc -12\nacc +8\nacc -19\njmp -206\nacc +6\nacc +18\nnop +94\nacc -2\njmp -330\nacc -15\njmp -367\nacc -15\nacc +40\njmp +143\njmp -178\nacc -1\njmp +140\nacc +13\nacc +47\njmp -271\nacc +29\nnop -30\nnop -344\njmp -251\njmp +98\nacc +45\nacc -17\nacc +5\njmp +1\njmp -299\nacc +34\nacc +7\nacc +7\nnop +16\njmp -106\njmp -399\njmp -291\nacc -4\nacc +26\njmp -376\nnop -444\nnop +59\nacc +27\nnop +89\njmp -188\nacc +21\nnop -246\nacc +6\njmp -24\nacc +35\njmp +1\njmp -361\nacc +48\nacc -5\nacc +19\njmp +74\njmp -56\njmp +43\nacc +50\nnop -275\nacc +39\nacc -11\njmp -258\nacc +8\njmp -190\nacc +46\njmp +1\nnop -188\nacc -15\njmp +12\nnop -5\nnop -444\nacc +0\njmp -129\nacc -11\nacc +28\njmp -452\nacc -4\nacc +24\nnop -176\njmp -56\nacc +47\nacc +33\njmp -432\njmp -19\nacc +32\njmp +1\nacc +7\nnop -179\njmp -49\nnop -66\nacc +20\njmp -122\nacc +1\nacc +10\nacc +16\njmp +40\nacc +11\nacc +6\njmp -454\nacc -2\nacc +12\nnop -228\njmp -165\nacc +42\nnop -212\nacc +49\njmp -286\nacc +42\nacc +24\nacc +38\njmp -440\nacc +29\nacc +8\nacc +21\njmp -288\nacc +2\njmp -427\nacc +17\nacc +45\nacc +33\njmp -333\nacc +6\njmp -445\nnop -283\nacc -18\njmp +1\njmp +1\njmp -492\njmp +53\nacc +26\njmp -107\nnop -377\njmp -155\nacc +22\njmp -523\njmp -127\nacc +2\nnop -168\nacc +15\njmp -343\nacc +34\nacc +0\nacc +0\njmp -241\nacc +30\nacc +40\nacc +46\nacc -11\njmp -216\nacc +31\njmp -86\nacc +34\nacc -15\nnop -4\njmp -74\nacc -1\nacc +13\nacc -2\njmp -119\nacc +21\nnop -516\nacc +24\njmp -580\nnop -200\nacc +18\njmp -318\nacc +0\nnop -483\nacc +15\nacc -9\njmp -30\njmp -462\njmp -476\nacc +18\nacc -14\njmp -91\nacc -6\nacc +32\nnop -611\nacc +8\njmp -613\nacc +23\nacc +7\nacc +30\nacc +48\njmp -222\njmp -326\nacc +46\nnop -108\nacc +17\nacc -16\njmp +1")
(def t "nop +0\nacc +1\njmp +4\nacc +3\njmp -3\nacc -99\nacc +1\njmp -4\nacc +6")


(defn parse [s]
  (->> s str/split-lines (mapv #(map read-string (str/split % #"\s")))))

;p1
(let [ops (parse i)]
  (loop [seen #{}
         idx 0
         acc 0]
    (if (seen idx)
      acc
      (let [[op arg] (get ops idx)
            seen (conj seen idx)]
        ;(prn idx seen op arg)
        (case op
          acc (recur seen (+ idx 1) (+ acc arg))
          jmp (recur seen (+ idx arg) acc)
          nop (recur seen (+ idx 1) acc))))))


;p2
(defn step [ops]
  (loop [seen #{}
         idx  0
         acc  0]
    (cond
      (>= idx (count ops)) acc
      (seen idx)           :fail
      :else
      (let [[op arg] (get ops idx)
            seen (conj seen idx)]
        ;(prn  idx seen op arg)
        (case op
          acc (recur seen (+ idx 1) (+ acc arg))
          jmp (recur seen (+ idx arg) acc)
          nop (recur seen (+ idx 1) acc))))))


(let [ops     (parse i)
      swap    (fn [idx [op arg]]
                (when-let [op ({'jmp 'nop 'nop 'jmp} op)]
                  (assoc ops idx [op arg])))]
  (->> ops
    (map-indexed swap)
    (remove nil?)
    (map step)
    (drop-while #{:fail})
    first))