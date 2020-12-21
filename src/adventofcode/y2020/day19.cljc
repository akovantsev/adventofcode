(ns adventofcode.y2020.day19
  (:require
   [clojure.string :as str]
   [instaparse.core :as insta]))

(def t "0: 4 1 5\n1: 2 3 | 3 2\n2: 4 4 | 5 5\n3: 4 5 | 5 4\n4: \"a\"\n5: \"b\"\n\nababbb\nbababa\nabbbab\naaabbb\naaaabbb")
(def y "42: 9 14 | 10 1\n9: 14 27 | 1 26\n10: 23 14 | 28 1\n1: \"a\"\n11: 42 31\n5: 1 14 | 15 1\n19: 14 1 | 14 14\n12: 24 14 | 19 1\n16: 15 1 | 14 14\n31: 14 17 | 1 13\n6: 14 14 | 1 14\n2: 1 24 | 14 4\n0: 8 11\n13: 14 3 | 1 12\n15: 1 | 14\n17: 14 2 | 1 7\n23: 25 1 | 22 14\n28: 16 1\n4: 1 1\n20: 14 14 | 1 15\n3: 5 14 | 16 1\n27: 1 6 | 14 18\n14: \"b\"\n21: 14 1 | 1 14\n25: 1 1 | 1 14\n22: 14 14\n8: 42\n26: 14 22 | 1 20\n18: 15 15\n7: 14 5 | 1 21\n24: 14 1\n\nabbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa\nbbabbbbaabaabba\nbabbbbaabbbbbabbbbbbaabaaabaaa\naaabbbbbbaaaabaababaabababbabaaabbababababaaa\nbbbbbbbaaaabbbbaaabbabaaa\nbbbababbbbaaaaaaaabbababaaababaabab\nababaaaaaabaaab\nababaaaaabbbaba\nbaabbaaaabbaaaababbaababb\nabbbbabbbbaaaababbbbbbaaaababb\naaaaabbaabaaaaababaa\naaaabbaaaabbaaa\naaaabbaabbaaaaaaabbbabbbaaabbaabaaa\nbabaaabbbaaabaababbaabababaaab\naabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba")
(def i "97: 138 57 | 12 83\n131: 20 83 | 74 57\n7: 57 110 | 83 51\n48: 17 83 | 56 57\n2: 83 57\n40: 57 101 | 83 93\n16: 12 83 | 47 57\n42: 15 83 | 66 57\n62: 83 134 | 57 18\n55: 124 57 | 45 83\n1: 57 116 | 83 93\n63: 83 1 | 57 32\n83: \"a\"\n72: 57 83 | 83 83\n18: 83 57 | 57 83\n67: 90 83 | 99 57\n91: 83 108 | 57 72\n8: 42\n116: 83 18 | 57 72\n41: 57 83\n130: 57 84 | 83 50\n26: 57 12 | 83 124\n103: 134 57 | 45 83\n39: 83 18 | 57 138\n46: 83 47 | 57 138\n129: 57 138 | 83 108\n58: 108 57 | 72 83\n138: 57 57 | 83 57\n49: 58 57 | 26 83\n133: 57 79 | 83 13\n125: 83 124 | 57 72\n123: 105 83 | 134 57\n128: 45 57 | 2 83\n24: 83 117 | 57 76\n86: 57 12 | 83 85\n76: 107 57 | 123 83\n70: 83 37 | 57 112\n51: 83 45 | 57 54\n37: 47 57 | 72 83\n9: 57 124 | 83 105\n61: 57 19 | 83 47\n75: 108 83 | 105 57\n120: 83 105 | 57 18\n124: 57 57 | 83 83\n4: 41 83 | 12 57\n0: 8 11\n27: 83 41\n89: 57 29 | 83 40\n111: 54 83 | 18 57\n12: 57 57\n53: 57 52 | 83 73\n90: 83 130 | 57 48\n59: 55 83 | 125 57\n21: 45 83 | 19 57\n30: 83 7 | 57 70\n88: 41 57 | 2 83\n71: 60 57 | 44 83\n74: 98 57 | 14 83\n87: 131 57 | 30 83\n73: 49 83 | 94 57\n82: 83 41 | 57 138\n35: 83 22 | 57 89\n136: 57 105 | 83 108\n94: 83 129 | 57 86\n65: 83 72 | 57 108\n105: 95 95\n77: 83 78 | 57 92\n19: 83 83\n92: 95 138\n104: 18 83 | 124 57\n43: 83 102 | 57 103\n99: 115 57 | 23 83\n135: 83 93 | 57 97\n23: 81 57 | 69 83\n115: 83 132 | 57 25\n110: 85 57 | 18 83\n52: 83 3 | 57 137\n3: 68 57 | 106 83\n15: 35 83 | 53 57\n106: 41 83 | 85 57\n60: 113 83 | 24 57\n93: 12 57\n66: 87 83 | 133 57\n34: 55 83 | 28 57\n80: 83 41 | 57 85\n28: 72 57 | 124 83\n45: 83 57 | 83 83\n38: 58 57 | 61 83\n25: 4 83 | 62 57\n14: 57 124 | 83 138\n29: 136 83 | 121 57\n64: 18 83 | 19 57\n81: 122 57 | 9 83\n137: 33 83 | 91 57\n118: 100 57 | 120 83\n98: 108 57 | 41 83\n114: 83 72 | 57 45\n132: 128 57 | 75 83\n107: 83 134 | 57 72\n50: 123 83 | 114 57\n78: 41 83 | 19 57\n79: 57 118 | 83 77\n32: 62 57 | 109 83\n10: 59 83 | 43 57\n119: 39 57 | 111 83\n36: 57 134 | 83 41\n6: 83 2 | 57 138\n69: 126 83 | 27 57\n57: \"b\"\n113: 5 83 | 38 57\n112: 138 83 | 105 57\n33: 108 83 | 45 57\n127: 41 57 | 12 83\n22: 83 34 | 57 119\n20: 57 127 | 83 80\n85: 95 57 | 57 83\n68: 134 57 | 19 83\n44: 83 63 | 57 10\n47: 83 95 | 57 57\n54: 57 57 | 57 83\n96: 36 57 | 21 83\n84: 98 57 | 46 83\n117: 83 16 | 57 65\n95: 83 | 57\n122: 18 83 | 41 57\n101: 83 41 | 57 72\n121: 134 57 | 108 83\n56: 82 83 | 6 57\n17: 83 88 | 57 93\n108: 57 95 | 83 83\n109: 134 83 | 124 57\n134: 95 83 | 83 57\n5: 104 83 | 64 57\n100: 41 83 | 138 57\n126: 54 83 | 45 57\n11: 42 31\n13: 57 135 | 83 96\n31: 83 71 | 57 67\n102: 19 57 | 72 83\n\nbabbaaaabbbbbbabaaaaabbb\nbbaababbbbabbaaabaabbabbbbbabbbabbbbbaba\nbaaabbababaabbbabbbbbaab\nbbbaabbbbbbbaaababbabbabaabaababaaababbabbbababa\nbababababababbbbabaaaaaababbabaababbabaaabbaabbb\nbbbbaaababbaababaaabaaba\nbbbabaabaabbbbabbbbabbba\naaaaabbaabbbabababbbaaaabbbbaabaabbbabbbabbbabaababbaabb\nbababbabaaabbbabbbabaabaaaaaabaaaaaaaaaabbbbbabbaaaaabaaaaaabaab\naaaababaaabbbbbabbaabbaaaaaababbabaaaaabbaaababb\nababbbbaaabbaaaababaaaaabaabaabaabbbbabbaabaaaaaaababbaababaabaabaabbaaaabababbb\naababbaabaabbabababbbabb\nabaaabaabaabbaaaaababaab\nabbbbbaababbbabababbabab\nabaababbbaabaaaaaababbbaabaaabbaababbaaabaabaabaaababbabbabbbbabaababbbbaabaabba\nbbababbabaaabbababaabbaabaaaabab\naabbaaabbbbbbabaaaabbbbbbaabbbbb\nabbaababbbabbabbbbbaaabababababaabbababaabbbabaaababbbab\nababbaaaababbbbbaaaaabbabbabbabbabbbbabbbbbaabaa\nbaaabaabbaaabbabbabbbbba\naabbbabaabbbaabbbbabbbbbaabbbabaaaabbbabbbababbaaaaaabaa\nababbbababbaabaabaaaaaba\nbaabaabbaababbaabaabbbabbbaaabbb\nabbabbabaaaabbaaabbbbbbbbbbbaaabbaaaabbbbbaaaabaaaaabbbb\nbbbbbaaaaabbbbbaababaaba\nbbaabbaabaabbaabbabababb\naaaabbaabbabaabbbabaaaaa\nabbbbbbaabbbbababbaabbba\naaaaabaaabbaaaabaabbabaabbbbbabaabbbabbb\nabaabbbbaababbbaaababbbbaaaabaaaabaabaaabbbaaaab\nbbbaaaabaaaababaababbbabbabbababaabbababbabbbbaabbbaaaaabbbbbabaaaabbbaa\nbbabababaabbaababaaabbbb\naaaaaabbaabbbbaaabaaaaabaabbaaaabaabbaaabababbba\nbabaaaabababbaaabbabaaaaaabaabbbbbabaaababaabbbabaabbaaa\nbbaabaaaaaaaaabbaaaabbaabbbabbbabaaabbbb\nbaabbbaabbaababaaabbbbabbaababaa\nbbbbbaaaaabbbbababbaabaababbbbbababbbaaaaaaabaaabbaababbbbbabbab\nbabbaaaabbbabbaaabbababb\nbbbaaabaaabbbbbaababaaaabaabaaab\naaaaaabbbbaababaaaaaaaabbaaababa\nabaabbaabbabbabbbbbbabbaabbbbbbababbabbaaabaaaba\nabbbababbabaaababaaaabaaabbbabba\nbabaabbbbbababbaaababbbaababaaaa\nbbbaabbbbbbabaabbbbaaabb\naababbbbabbaababbbbaaababbaaaaaa\naabbbaaaababbbbabbaaaababbbbaaaa\nbbababbababababaabaaaabb\naaabaabaabbababbbaababaaabbaaaababbbabaabbabbbabbbbbbabbbabaaabb\naababbaaaaaaabaaaabababb\nbbbbbbbbbabaabbbaaaababbabbbaabaabaabbaaaababbabbbaabbbbbbbbbaba\nbbaaaabbabbbbaabbbabaaabbbbaabbabaaaabbbaabaabba\naaaabaaabababaabbbaabaaabaaaababbaababab\nabbbababaaaabaaababaaaaaaaabbaaaaabaaabbbbbbabaabbbbaaaaaababaabaabbbabb\naaabbababbbaabaababbabba\naabbabbbaaabbabaabbabaabbaaaababbbbbbabbabababbaabaaabba\naabaababaaaaaabaaabbbaaaaabbabaaababbbbaaabaaabbabbbabbaabbabbba\nabbabaaabaabbaabaababbbaaabbaaaabbbbbabb\naabbbaabababbaabbbaaaabaabaaababbbbaaabb\nbbbabaabababbbbbbbbaabbbaaaabbbb\nbabbaabbaabaaababaaabbabbbabbabaabaabbbabbbbbabbbaaaabbabaaaaaaabbbabbba\naababbabaaaabbabaaaababbbababbbbbaabbbbaaabaaaabbaaaaabaaaabbbabbaaaabababaabbaabbbbabaaaabababb\nabbbbabbbbaababaaaabbabb\nbbaabbaaaababbabbaaabaababbbaaaaabaaabbaaaaaabbbbbaabbbb\nabbbbabbbaabbababaaaabba\naaaaaabaabbbbababbbaaabb\nabaabbbabbaaabaabbbbaabababaabba\naaababbaababbbbbbabbbbbbbaaabbbb\nbbbabaaaaabaabbaaabbbbabaaababbbaabbaaabbaababaa\naabbbaaaaaabbabaabbbbbab\nbaaaaaabaaabbaabbbabbabbabbabaabbbaabbab\nabbabaaabbbaabababababbb\nbbaaaaaabbbbaaaaaabbabab\nabbbbbaaaaaaaabaabbbbabbbbbbbbbbbbabbbbbabaabaab\nababababbbaababbaabbaaabbbaabbaabaaabababbabaabb\naaaaaaaababababaaabaaaaa\nbabbbaaabbaaaabbaaaabbba\nabbbbaabbbbaabbbbabaaaabbabababababaabbbaaabbbbb\nbababbbabbbbaaaabbaaabbabaaaabbaababbbaa\naabaababbbaaabaaaaaabaabbaababba\nbabaaaabbaabbaabbabaabba\nbbabbababbbaababbbabbbbaaabbabab\nbbaaaabaaabbabaabbabaaaaabbaabbb\naabbabbbbabbbbabbbaaaabbbaabbbbbaabbabba\nbabbabbbaaaaaaaabaabaaab\nabbbbbbbaaaabbbbaaaabbbababaaaaa\naababbabbbbbbaababbbbbab\naabbabaaaaabaabbaaaabbaaaabaaaaaabaabbab\nababababaabbbbababbbabaaabbbbabbbbabbbbaababbaabbbaaaaaaabbbaaaa\nabaabababbbbabbbaaabbbab\nbaababbbaaababaabababaab\nabbbaababbbbaabbaaababab\naabaabaaaabaabaababbbbaaabaaaabaaabababbbaaaabab\nbbbbaabaabaabaaabaaaabaabbabbaabbabaabbabaaaaabbabaabaab\naaaabaababaababbabaaaaaabbababaabaaabbaa\nbabababaaaaabaabbbbbabaaabaababbabaaaaab\nabababaabbabbabbbbbaaabb\nbbbabbbbabbbaabbbbaaabbbbbbbaaaabbaaaabbabbaababaabaabaa\nabbbaaaabbbbaaabbabbabbbbabbbbabbaaaaabb\nbbabaaababbbababbbaabaaa\nbbaaabaababbaaaabbbabbabaaababaabababaab\nbabababbbabaaabaaabbbabaababbbbb\nbbbbbbbbbababbbbaaaabaaabbababba\naaaaaabbbbbbbbbbababbbaa\nbbaabababbaababbbaaabbbb\nbbbbbaaaabbaaaabbbaaabab\naaaabaaaabaabbbababababaaaabbbaa\nabbabbabaaabbababbbabbba\naaaabababaaaababaabaaaba\naaababbaababaaababbabbabbbaaabaaabbbbaaabaaababbbaaaaaba\naaaaabaaabababbabbbbbbbbbbaaaabbabaabbababaaabab\nabbbbbbaabbbbabbbabababb\nbabbaaaaabaaababaabbbabbbaabaaaa\nbababbbabbabaaababbaaabb\nababababbbaaaabaabbaabbbabbbabaa\nbbbbaabaaababbabbbaabbab\naabbaabababaaabababbbbaaababaaba\naaaababaaaaaabaabaababab\nbbabaabbbbbaabaababbabab\nbabaabbbbbbaabbaaabbbabb\nbabbbababaaabaabaababbabaaababaa\naaaabaaabbaaaabababaabaababaabab\naaaababbbbbaaabaaabbabbbbaaabaabbbaabbbb\nabababaabaabbabaababababbabbabaa\nbaaabaabaaababbaaabababa\nabababaaabbabaabbbbaaaab\nabbbbaabaaaabbaabaababbbbbaabaabaabbbaababaabbbbabaaaaaaabaabaabbbbabbaabaabbabbaaabaabbabaaaaba\nbababbbbabababaabbbbbabb\naaaaabaaabbbbbbabababababaababbb\naabaababbbbaababaababbaaabbbbabbaabbbaba\nabbbababaaaabbbbbbbbbaababaabaaababababaabbababaaaabaaab\naaaabaabaaabaabbbbabbabbaabaaaabbabbbbba\nbbbabbabbbbaabbbbbbababa\naaabaaaaaababbbaabaaaaba\nabaaaaabbaaaabaaabaaaabb\nbaabbababbaababbbbaabbaababbaaaabaaaababaaabbbaa\nbbbbaabbaaabaaaabbababaa\nabbaababbbbbbbbbbbbbabaabaababab\nabaabbabbaabbbbbababaaba\nabbbbbaabbbbaababbaabbaabaabbaaaaabbabab\nbabbaaabbbaababbbbbbabbbbaabbabbababaaba\naabbbbabaaabbabaabbbaaab\nbbabbabaabbbbabbabbababb\nababbaabaaababbaaabbaababbbabaaabbaaabbbabaabbbbbaababaabbaaabbbaabbabab\nabbbaaaabbabbabbabbababa\nbbbbbaabbbbbabbaaabaaaab\nbbabbaaaaaaabaabbabbaaaaaaaabbabaabbabba\nbaabaabbaabbbbaaaabbbabaaabaabaa\nabbabaabbaabbabaaaaabbbabaaaaabb\nababbbbabababababbbaaabbbabbbbbabaaababbbbababbababaaabb\nbbbabaababbbbbbbaaababbaabbabbaabbabbabbabbbabbb\nababbbbbaabbabbbbaabaaababbabbbbaabaaaaaabbbbbaa\naaabaabbabaabaababbaabbbabaabbbababbaababbbbabbabaabaabbbabbbabbaabababbaaaaabbbbbbaabbb\nbaaaaaabbbbaababababbbab\nbaaaaaababbbababaaabbaab\nababbababbbaabababbbabba\nbabbaaaaaaabaabbbaabbabaaabbbaba\nbabaaaaabbabaaaabaabbbbbbbbbabbb\nbbbaaaaaababbaababaaabaa\nabaabbbabbabaaababbabbabbaabbabb\nbbbabbabababbaaaabbaaaababaabaaa\nbbbababbaaaaabaabbbbabaaabbbbbbbabbaaaabababaabaabbbabaa\nbbaaabbaaabaabbbbaabbbbaababbbaababbbaaa\naababbbbabababaaaabaaaab\naabbaabaabbbaabaabbaaaabbbbbbaababbbbbbaabaaaabb\nbbbaabaabbabbbbababaababaabaabba\nabbbaababbbaabbabaababab\naababbaababbabbbaabababa\nbbbaababaaaabaabbababababbbbaaabbbbabaababaabbab\nbabaaaabaabbbbbaabbbbbaaaaaaaabbbbbaabbaaababaabbabbaaab\naaaabaabaaabbabaababbaaabbabbbaabbbabbbaaaabbbbbaabbabba\nbabbbaaaaabbbaabaaaaaabaaaaaabbaabaaabbbaabaaaba\naabbbaaabbabababaabbabaaaaabbaaababbabaa\nbbaaabbabbbabaaaabbaabaaabaaabba\nbaabbbababbababbbaaabababaabaababbaaababbabbbbaa\naabaababbbaaaabbbaabbabababaaabaabaabaaa\naaaabaaaabbaababaabbbbaa\nbbbbababbabaabbababbaababbbbbbbaabbaabbabbaabbab\nabbbbbaabbbbaabaabbbbbab\nbbaabbaabbaaabbaabaaaaaa\naaaababbbbbbbaabaabaabba\nbbaaaaaabbbabbabbaabaaaa\nbbbababbaaaaaaabaabbabbabaabaaaaaabaaaba\nbbaabaabbaaabbbaaaabaaab\nbbababaabbbabbabaaababbbbbabaabbaaaaabaaaaaababa\nbbaababaabbaabaaabababbbbaaabbbbbaaaaabbbbabababbabbabbaaaaaaaba\nbbabababbabaaaababbababa\nbbabaabbbaaabbbaaaababab\nabbbaababbaaaababaaabbabaabaaababbbbbbba\nababbbbaaaaababaaaaabbba\nababbbbbaabbbaaaaabbabba\nbbabbabbabbaaaabbbbababa\nabbabbbbbbabaaaaaababbbbababbababbaaabaa\nbbbbbbbbaabbabaabbabbbab\nbbababbabaabaabbabbaababbabbbbbbababaaaababaabaa\naaaababbaaabbbbaaabbaabb\nbaaabbabbababaaabaabaaba\nbaaaabbaabbaaaaabaaabbaa\naabbabaaabbbbabaababbaaabbabbbbababbabab\nbaaaaaabaabbbaabaababbaaaababbbbbbaaababbabbaababababbab\nbabaaaabbabaabababaaabbabbabbbababaabbbb\nbaabbabaaababbbaababbabb\naaaaaabababbbaabbaaabaabbaababaaabaaaaababbbbaaaabbbaaba\nbbaaaabbbbabaaaabbbbbabb\nabaabbaabbabbbbbbaaabbbabbabaaaabbbaaabbbbaabbab\nababaaaababbbbbabaabaaababbbaabbabaaaabbbaabababaaababaababbbbaabaababaabbbabaaa\nbabbbabaabbabaabbbaabbba\nababababbbabbabababbaaab\nabababababababbabbababbbbababaabababbbabaaabbaabababaaaa\nbbbabbaabababaaabaaabaabaabbaababababbaa\nabbbababaaaabaaaabbabaaabaaaabbbabbbabbaaabaaaaaaaabababbaaaaaaa\nbababaaaaaababbababbabaa\naaababbabbaaaabbabaaabab\nbbbabbabbaabbbbaaaaabbab\nabababbaabaababaabbaababbbaaaababaaabbababbababa\nbabaabbbbabbaaaabbaaaaab\naaaaaabbbbbbbaabaaaaabaaabbbbbbaaabbbbabbaabaaabbabaabab\nabaabbaabbbbbbbbaaaaaaababaabbbabbabaaaa\nbaabbbbaaaabbababaabaaaa\naabaababaaaabbaaaababaaa\naabbabaabbbabaabbaabbbaababaabaa\naaaaaabaabbbaabaabaaabba\naaaaaabaabaaaaabababbabbbabaabbaaabbbbaaaaabbaaaabbaabaa\nabbbaabbbaabbbbaabbbaaaabbbaaababbbbaabaabbababbbbbbaabbaaaabaab\nbaabbbbbabaabbbbabababaabbbaaaba\naaaabbaaabbabbabbabaabbb\nbbaaabbabaaabbbaaabbaaabbaaaabaa\nbabbaaaaabaabbbaabaabbbaabbbbaaa\nbbaaabbabbbaababbbabbabbbaabbaababaaabbaaabbabba\nabaabbaabbabaabbbabbbaaaabaaaabb\nabbabababbbabbabbabbabbbbbaaaaaaaaaabbbbabbabbaaaabbabaa\nbbaabaabababbbbbaabababa\naabbbaabbabbbaabaabaaaaa\nbbbbbbabaabaababbaaababb\nbbbbbaabaaabbaababbbbabbaabbbbbbaabaaabbabaababaaabbababbbbbababbbbbabbabaababbaabbabaab\nabbaaaabbbabbbbabbbbaabaabbbabaa\nababaaaabbabaabbabbbbaabbbbbbbabbaaabaaa\nabbaaaaaaababbbabbaabaaa\nbababaaaabbbbababaabbbabbbbbabab\naabbbbabaababbaaaabaaaaa\nbabbbaabbbabbaabaababbabaaaaabab\naaaaaababbaabaabbbababbaaaaabbbb\nbababaabbaaaaaaaabbbabba\naaabaaabbabababbbbbbabbaaaabaaababbbaabbaaaaababbaaabbbbbbaabbbbabbabababaabaabb\nbbabbbbaaaaaabaaababaaba\naaaabbbaaaaababbaabaaababababababbbbaababaaaaaaaababaababbbbababaaaabaaaaaabaabb\nabbababaaababaabababbabb\naababbaabbbbabbaababbabb\nbbbbabaabababaaaaabbbabb\nbabaababbbbbaabaababaabbbbbabaab\naabbbbaaaabbababaaaabbaabbbbaabaababbaaaabbaabab\nbaabbbababaabbaaabbbbaabaabaaabbaabbbabb\nbbaaabbaabbabaabbbababaa\nbaaaababababbbbbbbaaababababbaaabbabbaaaaaaabbbbbabbbaaaababbaab\nbabbabbbaababbbbbaababbb\nbababaaabbaabaabbaabaabbaaababbabbbbaabbaaababab\nbbabaaabbababbbbbbbabbabaababbbaaaababaa\naaabbababbaaaababbaabbbb\nbbbbaaababaabbbabbbabbbb\nbbababbaabbaaaababaaaaaa\nbbbaaabaaaaabaabbabababb\naaabaaaabbbbaabbbaaaaaaa\nbbabbaabaaabbbbaaaababba\nbbbaaaaaabbbaabaaababbaaaaabaaab\nabaaabbbababaaabbbbaaababaaabaabaabaabbabbbabbabbbaaaaaaaabbabaa\naabbabbaababbabbbbbbbbba\nbaabbbbaaabababbabaabaab\naaaababaaabbbbbaaaaaabbb\nbbbbbaaabababbbbababababaaabaaab\nababbbbbbbababbabbabbbbaabbababa\nbabaaabaaaabbabaaabbaabb\nabaabababbbabbabbbbbabaababaabab\nabbbababbabababababaabba\nbbaabbaabaabbbababababbabababbbabaabaabbaabbabab\nbbaababaaabbbaababbbbbbaaaaabbba\nbaaabbabbbbabbabbbaabaaa\nabbabbbbbbbaabababaaabaa\nbaaaabbbabaabbaaabbaabaa\nabbbabbbbbbbabbbaabaaabbbbbbaabbbaabbabbbaaabbab\nbbbabaababbaababbbabaaba\nababbbbabaaabbbaaaabaaaa\nababbaaabbabaaaabaababba\nabbaaaabbbaabaabbabbabab\naaabbbbbabbbaabbbaaabbbbabaaaaab\nbababbbbbaaabbabbbaaabab\nbabbbaabbbabbbbaabbbaabb\nbbaababbbbabbabaaabaababaababaaa\nabaababaabbabbaaabbbbaaa\nbbababbabbbbbaaabbababaa\naaaaaabaaabbbaaaabaaaabaaaabaaba\nbbababbabaaabbbababbabaa\nbbbabbabbbbababbabbbaaaaaabbbaaaabbbbaaabbbaababbbbaaaaababbaaababbaabaa\nbbbbbaabaaaaabbaaaaabbaabbaaabbaabbabaabbaaaaabb\nbbbbabbabbabaaabababaaba\naaabaabbbbaabaababbabbaabbbbbaabaababbbaabbaabbabbabbbaaabbbabaababbbbbb\nabaabbbabbbbaabaabbaaaaaaabaaaab\nabbbbbbbabaabababaaabbbabaaabbbabaaababbbabaaabbabbbabaa\nabbabaaabaaabbbaaababaaa\nbaabbbbaaaababbaaabaababababbbabbabbaaba\nbbaabababbaabbaaabbbabbbaaabbaaa\nabbaababbabbbbbbabbaabaa\nbbaababaaaabbabaaababbabbbaababbbababaabbbbbbabb\nbbbbbaabaaaabaaabaaaabab\nabbabbaaabbbaaaabbbbbaba\nbbbbaabaaabbaaabbbbbbaabaabbabaaaababaaa\nabbabbabaabbabbbabbbbabaabbbabbabababbab\naabbaaabbbabaabbaaaabbbb\nbbabbaaababaaababbbaababbbaabbbbaabbaabbbbbbbaba\nabababababbaaaaaabbaaaabbbbbbabbaabaaaba\nbbbaabbababaaabaaaabbbbababbbbabbbbbabababaaabbb\nbbaababbabbaaaaaabbbababbababbab\nbbabbaaaaaaabbaaababaaaa\naababbabbbaaabaabbbbabbb\naaababbabaabbbbabbabaabbabbbababbbbbaaaaaabbbbbabaaaaaaa\naabbaaabaaababbbbababbbbabaabaaa\naaaaabababbbbbaabbbaaababbbaaaaaabbaaaab\naaaaaabbbbbabaababaababb\nababbbbbaaaaaababbbbbaba\nbaabbabaababbaabbbabbbbaaaaabaabaabbabbabaaaabab\nabbbababbbbbaaaaabbbbbabbbabaaababbaaabaababbabbaaaabbaabbaabaaabaabbbbb\nabbbbabaaabbbbabaabbbaaa\nbbabaaababbabbaabababaaababaabaa\nabababababbabbbbaaabbbaa\naababbabbabbbababaaaababbbbbbbaaabbaabbb\nabababbaabbbbabbbabaabbbabbbaaaabbabbaaaabaaaaab\nbbbbabaaababbabaaaabbabaabbabaaaabaababb\naabbaaabbababaaaaabaaaab\nabbbababaabbbbbabbabbababbabbaabbbaaaabbbbbbabaababaabab\naabbabbababababbabbaabbbabbbabbb\nbabababbabaaaabaabbbaaabbbaabbbb\nbabaaabababaaaabaabbabbbababababbbababbabbbaaabbaabbabab\nbabaaaabbbabbbbaabaabaab\nbaaabbbabaabbbbabaaabaaa\nababbbbbabaabababaabbaabbbbbbbabbababaab\naaabaabbaaababbbbabbabab\naaaaaabaaaaabaabbaabbababbbabbaabaaaaabbbaabbbbbbaaaabab\naabaaabbbaaaabaabaaabbbbbabbaaabaaaabababbbaabbbbababaababaaabbbabaaaababaabbabb\naaaabaabaabbabaababbbaaa\nbabaabbbaabaabbaaabbaabbaabaabaa\nbbabbbbbababababaabbbbbabaaaaabbabbababa\naabbbaaaaaaabbaaaabababb\nbabbbaaabbabbbbaaaaabaaabbaabbbb\naabaabbbbbaaabaabababbbaabaaaaab\nbbbaababbbababbabbaababaaababbaabaaaabab\nbbaabaabbaabaabaabbaaabb\naababbaabbaaabbbaaabbabaaabaabbbaaababbabababbabbbaaaabb\nabaabbaabbabbaabbbbabbabbaababaaababbabb\nbaabbbaabbbbbaabbabbabaa\naaaaaaaaababbaabaababbbaababababaaaaaaabaabbbbaa\nbbabaaabbabababaaaabbabababbbbaaabbbbaaa\nbaabbaabaabbaaabbbababaa\nbaababbbaaabbaabbbaaabab\nabaaababaaabababaabaaaab\naaabbbbaabbbbabaaaabbababbbababbabbabaababbbbbbaaabbbabbbaababbbbaaabbbb\nbaaababbaababbbabbbbaaaabbbababbbbbabbaa\nabbbbaaabaaabbabaababbbabbbbbbbabaabbbbbabbaaabbabbbbaaa\nbbabaaabaaaabababbabbaaaaaaaaaabaaabbbbb")

(defn to-grammar [rules]
  (str "S = 0\n" (str/replace rules #":" "=")))

(defn solve [input]
  (let [[rules messages] (str/split input #"\n\n")
        grammar (to-grammar rules)
        parser  (insta/parser grammar)]
     (->> messages
       (str/split-lines)
       (map #(insta/parse parser %))
       (remove insta/failure?)
       (count))))

(solve i)
(solve (-> i
         (str/replace "8: 42" "8: 42 | 42 8")
         (str/replace "11: 42 31" "11: 42 31 | 42 11 31")))