(ns adventofcode.y2020.day04
  (:require [clojure.string :as str]))


(def test-data "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd\nbyr:1937 iyr:2017 cid:147 hgt:183cm\n\niyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884\nhcl:#cfa07d byr:1929\n\nhcl:#ae17e1 iyr:2013\neyr:2024\necl:brn pid:760753108 byr:1931\nhgt:179cm\n\nhcl:#cfa07d eyr:2025 pid:166559648\niyr:2011 ecl:brn hgt:59in")
(def data "iyr:2013 hcl:#ceb3a1\nhgt:151cm eyr:2030\nbyr:1943 ecl:grn\n\neyr:1988\niyr:2015 ecl:gry\nhgt:153in pid:173cm\nhcl:0c6261 byr:1966\n\nhcl:#733820\nhgt:166cm eyr:2025 pid:79215921 byr:1952 iyr:2014 ecl:blu\n\neyr:2022\nhgt:165cm hcl:#733820\niyr:2013 pid:073015801 ecl:oth\ncid:101\n\niyr:2013\necl:brn hcl:#623a2f\ncid:246 byr:1948 pid:122719649\nhgt:160cm\neyr:2026\n\neyr:2028\npid:229371724 hgt:154cm hcl:#ceb3a1 byr:2000 ecl:hzl iyr:2017\n\neyr:2029 ecl:amb\nbyr:1980\nhgt:177cm pid:914628384 hcl:#623a2f iyr:2013\n\niyr:2019\neyr:2026 hcl:#efcc98 pid:475316185 byr:1945\nhgt:76in\necl:amb\n\npid:371759305 iyr:2018 hcl:#623a2f eyr:2026 byr:1984 hgt:174cm\n\niyr:2010 pid:184800910 ecl:amb cid:108 eyr:2028\nhcl:#cfa07d\n\niyr:2012 hcl:#b6652a hgt:160cm pid:223041037\neyr:2029 byr:1920 ecl:oth cid:212\n\npid:775975903\nhgt:163cm byr:1966\neyr:2023 ecl:amb iyr:2010\n\npid:465404256 hcl:#7a54af ecl:blu hgt:180cm iyr:2018\neyr:2024 byr:1933\n\nhcl:#18171d\niyr:2018 hgt:185cm\nbyr:1929 eyr:2024\necl:oth\npid:#b0990a\n\nbyr:2017\nhcl:#cfa07d pid:184cm hgt:187in ecl:#e97c0d iyr:2022 eyr:2013\n\nhgt:176cm iyr:2019 eyr:2024 ecl:brn byr:1943\npid:532967054 hcl:007b47\n\npid:171225044 hcl:#888785 byr:1992 ecl:amb iyr:2012 eyr:2030\nhgt:180cm\n\nbyr:1969\npid:689216275 iyr:1934 eyr:2031 ecl:#e5bc14 hcl:#fffffd\n\npid:166619054 cid:125 hcl:#cfa07d hgt:164cm byr:1946\necl:brn iyr:2014 eyr:2023\n\necl:#c1ae72 pid:458692331 hcl:#b6652a eyr:1966 byr:1951 iyr:2023\n\ncid:253 pid:9096156879 hcl:9911e5\nbyr:2007\niyr:2019 eyr:2033 hgt:181cm\necl:blu\n\necl:oth pid:180428876 iyr:2019 byr:2001 eyr:2024 hgt:172cm hcl:#7d3b0c\ncid:81\n\nhgt:167cm\nbyr:2007 eyr:2030\niyr:1961 ecl:lzr hcl:#cfa07d\npid:#e254d8\n\npid:377737133 hgt:160cm byr:1958 hcl:#866857 ecl:oth iyr:2015 eyr:2022 cid:350\n\nhgt:188cm\neyr:2025 ecl:brn hcl:#efcc98\niyr:2015 pid:321192707\nbyr:1996\n\nbyr:1956 iyr:2018 hcl:#b6652a\neyr:2024\necl:blu cid:343\nhgt:152cm pid:192659885\n\nhgt:163cm\niyr:2012 byr:1952 eyr:2022 hcl:#efcc98 ecl:grn pid:337866006\n\ncid:134\nbyr:1941 eyr:2027 ecl:oth\niyr:2012 pid:303690324 hcl:#7d3b0c\n\necl:grn byr:1945 eyr:2028 hcl:#cfa07d iyr:2012 cid:108 pid:388941584 hgt:171cm\n\npid:962215061 ecl:brn iyr:2019 eyr:2025 cid:163 hcl:#cfa07d byr:1958 hgt:187in\n\necl:amb\nbyr:1989 pid:118257086 iyr:2019 cid:320 hgt:158cm eyr:2027 hcl:#733820\n\npid:813286578\niyr:2019 eyr:2031 hcl:#341e13 hgt:161cm byr:1950\necl:oth\n\nbyr:1976 ecl:blu\neyr:2024\nhcl:#fffffd\nhgt:153cm pid:552154655 iyr:2019\n\neyr:2025 pid:109518209 iyr:2013 byr:1923\nhgt:162cm\necl:oth\n\nhgt:178cm byr:2007 hcl:#7f431f pid:7365694093 eyr:2037\necl:blu iyr:2018\n\neyr:2021\ncid:105 iyr:2013 byr:1995 ecl:grn hgt:181cm\npid:733189859 hcl:#b6652a\n\nhcl:#6b5442 eyr:2028 iyr:2013 byr:1958 hgt:169cm\necl:gry pid:651263830\ncid:51\n\niyr:2018 cid:59 eyr:2027 ecl:blu\nhgt:174cm pid:269821917 byr:1971\n\nbyr:1936 eyr:2025 ecl:hzl hcl:#b6652a\niyr:2019\npid:670202082\nhgt:186cm\n\nbyr:1958\necl:gry hcl:#7d3b0c pid:000522430 eyr:2020\nhgt:168cm\n\niyr:2015 eyr:2022\npid:630105579 hcl:#7d3b0c byr:1935\necl:gry hgt:153cm\n\necl:gry hcl:#fffffd hgt:172cm byr:1944\neyr:2021\niyr:2013 cid:58 pid:554670072\n\nbyr:1983\neyr:2022\niyr:2012 hgt:176cm\necl:brn pid:201965494 hcl:#dd8296\n\ncid:124 byr:1935 eyr:2020 ecl:blu\nhcl:#a97842 pid:666776663 iyr:2010\nhgt:68in\n\nbyr:1943 pid:139343878 hgt:181cm hcl:#733820 cid:206 iyr:2018 ecl:brn\neyr:2029\n\npid:114742677\necl:amb hgt:160cm\nbyr:1975 eyr:2030\nhcl:#866857\n\niyr:2012 ecl:amb hgt:170cm pid:221200950 eyr:2028\nhcl:#733820 cid:274\n\neyr:2020\npid:167438086 iyr:2020 hcl:#fffffd hgt:178cm byr:1996\necl:blu\n\niyr:2015 hcl:#b6652a\npid:905439259\nbyr:1962 cid:209 ecl:brn hgt:150cm eyr:2024\n\niyr:2016 ecl:amb\npid:855119486\ncid:77\nhgt:192cm byr:1947 eyr:2021 hcl:#888785\n\nhcl:#341e13\niyr:2011 hgt:157cm eyr:2030\npid:103806645 cid:90 ecl:grn byr:1976\n\nhcl:#fffffd byr:1957\ncid:284 eyr:2026\npid:031705251 iyr:2019\nhgt:166cm\n\neyr:2019 byr:2019 hgt:167in\niyr:2014 ecl:gmt\n\nhcl:#18171d cid:93\neyr:2027\niyr:2013\npid:821161144 ecl:brn byr:2002 hgt:170cm\n\nhgt:181cm\nbyr:1972 iyr:2018 hcl:#fffffd\npid:745667222 eyr:2023\necl:hzl\n\nhcl:#602927 byr:1996 ecl:blu pid:503245375\nhgt:166cm eyr:2028 iyr:2018\n\nhgt:59in byr:1966\necl:oth iyr:2016 eyr:2029 hcl:#866857\n\ncid:179 iyr:2012 hgt:182cm eyr:2024 pid:451186596 hcl:#6b5442 ecl:blu\n\neyr:2023 iyr:2016\nhcl:#18171d hgt:173cm cid:182 pid:979409688 ecl:brn byr:1972\n\niyr:2014 eyr:2027 byr:1977 ecl:brn hgt:171cm hcl:#b6652a pid:124307431\n\nbyr:1929 pid:048990845 ecl:hzl hgt:193cm cid:159 hcl:#b6652a eyr:2028\n\necl:gry hgt:181cm iyr:1934 byr:1970\neyr:2015 pid:5818817055 cid:98\n\nhgt:179cm\niyr:2014\neyr:2030\npid:397317466 byr:1967\nhcl:#a97842\necl:grn\n\npid:138485312 ecl:hzl\nhgt:151cm\niyr:2010 hcl:#866857 byr:1936 cid:345 eyr:2021\n\necl:oth\npid:687490850 eyr:2028 hcl:#9bd268 hgt:157cm iyr:2012 byr:1994\n\nbyr:1933 pid:575158994 hgt:192cm iyr:2017 eyr:2022 hcl:#602927\necl:gry\n\niyr:2012\nhcl:#7d3b0c\necl:oth\neyr:2023 pid:615952261 byr:1959\nhgt:152cm\n\nbyr:2010\niyr:2011 hcl:z ecl:grn hgt:152cm pid:6079261766\n\nhcl:#18171d iyr:2012\nbyr:1986 ecl:blu\npid:836367740 hgt:191cm eyr:2024 cid:200\n\necl:blu pid:909247319 iyr:2019 eyr:2026 byr:1958\nhgt:170cm\nhcl:#b6652a cid:256\n\neyr:2027 hcl:#efcc98 iyr:2010 hgt:191cm ecl:brn byr:1933\n\nbyr:1921\npid:414637667 hcl:#8d840f cid:291 iyr:2013 ecl:amb eyr:2029 hgt:172cm\n\nbyr:1961 iyr:2020 eyr:2025 pid:675731511 hgt:71in\necl:blu hcl:#623a2f\n\nbyr:1959 iyr:2002\nhcl:#ceb3a1 eyr:2027 ecl:#633ff4\npid:815877728\n\necl:gry pid:812104470 byr:1938\nhgt:70cm\neyr:2028 cid:285 iyr:2016 hcl:#b6652a\n\ncid:99 hgt:152cm byr:1971\neyr:2020 hcl:#733820\necl:gry\niyr:2012 pid:020521112\n\npid:803066169 hgt:157cm iyr:2014 hcl:#b6652a eyr:2025 ecl:brn byr:1981\n\nhcl:fafcd9 eyr:2021\nhgt:76in\npid:359286290 cid:207 iyr:2018 ecl:grt\n\npid:179471060 byr:1966 ecl:amb hcl:#18171d eyr:2029 iyr:2015 hgt:190cm\n\nhcl:#efcc98 ecl:gry\nbyr:1942\neyr:2026 iyr:2011\nhgt:158cm pid:721512314\n\necl:oth eyr:2024 hcl:#8bc738\nhgt:167cm iyr:2014 pid:729168992\n\nhcl:#b6652a\npid:179977662\niyr:2018\necl:gry\nbyr:1973\nhgt:155cm\ncid:126 eyr:2030\n\nbyr:2012 eyr:1955 hcl:z iyr:1981 pid:#66167e hgt:175in ecl:grt\n\nhgt:154cm cid:301 iyr:2010 hcl:#7d3b0c pid:573851812 ecl:grn eyr:2030\n\npid:601712917 ecl:hzl eyr:2001 cid:70 hgt:162cm hcl:#6b5442 iyr:2018\nbyr:1959\n\nhgt:183cm byr:1996 eyr:2023 hcl:#866857 iyr:2018 pid:259910953 ecl:brn\n\npid:751991211 hcl:z iyr:2015 eyr:2024 byr:1939 ecl:oth hgt:161cm\n\neyr:2026 byr:1947\necl:grn iyr:2018 cid:248 hcl:#ef5900 hgt:66in\n\niyr:2028 eyr:1994 pid:9211015765\necl:amb\nhcl:cd429d\nbyr:2013\nhgt:176cm\n\nhgt:193cm ecl:amb eyr:2023\niyr:2020 byr:1933\ncid:50\nhcl:#efcc98 pid:482745318\n\nbyr:2014 pid:619629999\necl:oth hgt:159cm eyr:2027 iyr:2018 hcl:#b6652a\n\nhcl:#b6652a eyr:2028 byr:1921\necl:blu\npid:866536313 hgt:185cm iyr:2020\n\neyr:2027 pid:904225608 iyr:2010 cid:57 hgt:161cm hcl:#83ee3c ecl:gry byr:2001\n\nbyr:1968 pid:120450875 iyr:2018 hgt:165cm ecl:grn eyr:2022 hcl:#7d3b0c\n\nhcl:#a97842 byr:1964\neyr:2027 ecl:oth hgt:186cm iyr:2010\ncid:226 pid:632344779\n\npid:135449714\ncid:348 iyr:2019\nhcl:#602927\necl:oth eyr:2029\nhgt:184cm byr:1992\n\neyr:2027\nbyr:1929 hgt:181cm hcl:#fffffd iyr:2019 pid:369478657\necl:blu\n\nhgt:177cm eyr:2021 iyr:2016 ecl:gry byr:1938 hcl:#602927 pid:034365656\n\niyr:2020 hcl:#866857\neyr:2028\npid:183612456 ecl:#9e25d3 hgt:159cm byr:1934\n\npid:664990234 hcl:#efcc98 cid:185 iyr:2018\necl:brn\nhgt:192cm\nbyr:1942\neyr:2029\n\neyr:2027\nhgt:163cm pid:760854338 cid:228\nbyr:1958 iyr:2014\nhcl:#efcc98 ecl:gry\n\niyr:2014\nhcl:#18171d hgt:157cm ecl:amb eyr:2025 byr:1957 pid:347369874\n\npid:013801431 byr:1999\nhgt:181cm eyr:2029\necl:amb iyr:2010\nhcl:#b6652a\n\nhgt:153 eyr:2030 byr:2022\nhcl:z cid:84 ecl:hzl iyr:2020\n\ncid:315 iyr:2011\nhgt:151cm hcl:#ffb5f9\npid:427813663 byr:1999 ecl:brn eyr:2025\n\neyr:2024\ncid:205 byr:2000 iyr:2020 hgt:173cm ecl:gry\n\neyr:2026 byr:1996 iyr:2018 ecl:grn hgt:158cm pid:026432567 hcl:#602927\n\niyr:2014\necl:hzl\nbyr:1964 eyr:2020 hgt:184cm pid:031793197 hcl:#fffffd\n\necl:oth hgt:165cm byr:1982 pid:172329608 hcl:#733820\niyr:2019\neyr:2020 cid:347\n\nbyr:2014 iyr:2011 hgt:179cm\nhcl:z eyr:2020 ecl:grt\ncid:134\n\necl:gry\niyr:2011 hcl:#a97842\nbyr:1942 pid:789953865 cid:172 hgt:180cm eyr:2027\n\nhgt:179cm pid:975853536\niyr:2018 byr:1989 hcl:#602927\neyr:2021 ecl:amb\n\nbyr:2011\niyr:2015\necl:oth\nhcl:85d233 cid:207 eyr:2020 hgt:138 pid:6169876\n\necl:hzl eyr:2029 hcl:#efcc98\nhgt:188cm iyr:2018 cid:143 byr:1973\n\nhcl:#ceb3a1 pid:762609805\nbyr:1962\nhgt:150cm\niyr:2016 eyr:2024 ecl:oth\n\niyr:2012\npid:181821528 byr:1922 ecl:gry hcl:#a97842 hgt:169cm eyr:2028\n\neyr:2029 pid:776605704 byr:1964 hgt:175cm hcl:#623a2f ecl:grn\niyr:2011\n\nbyr:1989 iyr:2019\necl:gry pid:397990288\nhcl:#602927 hgt:67in eyr:2030\ncid:259\n\nhcl:#866857 iyr:2018 ecl:blu byr:1938 cid:227 eyr:2028 hgt:171cm\npid:779192850\n\nbyr:1974 hcl:#cfa07d eyr:2026 hgt:59in cid:51 iyr:2019\necl:oth pid:091591737\n\nbyr:1952 cid:301 pid:804465935 hgt:152cm eyr:2025 hcl:#888785\niyr:2013 ecl:hzl\n\nhcl:6a32f0 eyr:2027\niyr:2014\necl:amb byr:1933 cid:185\nhgt:75cm\n\nbyr:1945 hcl:#b6652a\nhgt:163cm ecl:brn eyr:2025 iyr:2015 pid:829875350\n\nbyr:1940 hgt:174cm iyr:2014 pid:9989523268 ecl:gry eyr:2026\nhcl:#efcc98\n\niyr:2011 ecl:lzr\nbyr:1998 hgt:182cm\neyr:2029 hcl:z\n\npid:091142801\nhcl:#c0946f byr:1983 ecl:blu iyr:2017 hgt:183cm eyr:2027\n\necl:oth iyr:2011 hgt:158cm byr:1950 pid:902512428 hcl:#623a2f eyr:2028\n\neyr:2020\niyr:2020\necl:oth byr:1938 hcl:#d5eb7e\npid:829945241 hgt:171cm\n\nbyr:1971\npid:998912876 eyr:2022\niyr:2012 hcl:#cfa07d cid:338 ecl:brn\nhgt:183cm\n\necl:amb hcl:#7d3b0c pid:331124964 iyr:2013 byr:1945 hgt:189cm eyr:2028\n\npid:012740434 iyr:2019\nbyr:1998\nhgt:172cm eyr:2028\ncid:102 hcl:#866857\necl:amb\n\nhcl:#866857\neyr:2027 hgt:181cm byr:1954 ecl:blu\niyr:2010\npid:211721858\n\nbyr:1984 pid:876360762 hgt:72cm\neyr:2040 hcl:a60c15 iyr:1948 ecl:lzr\n\nhgt:167cm byr:1930 ecl:oth pid:740024142 eyr:2024 hcl:#341e13\niyr:2018\n\nbyr:1968 hcl:#623a2f ecl:brn\ncid:210 hgt:155cm\niyr:2017 pid:216618180 eyr:2024\n\nbyr:1966 pid:131332466 hgt:174cm ecl:amb\nhcl:#733820\niyr:2013 eyr:2023\n\neyr:1985\npid:#fde6c1\nhcl:z byr:2017 iyr:2027 hgt:163cm\n\niyr:2016 ecl:hzl hgt:171cm\nhcl:#888785 pid:390140479 byr:1976\n\ncid:327 eyr:2023 byr:1954\nhgt:192cm iyr:2012\npid:413357852 ecl:blu\n\niyr:2014 ecl:hzl hgt:154cm byr:1962 pid:904474869 hcl:#6b5442 eyr:2023\n\necl:hzl iyr:2012 eyr:2027 pid:663644982 hgt:175cm cid:56\nbyr:1980 hcl:#ceb3a1\n\neyr:2020 iyr:2012 hgt:190cm byr:1993 hcl:#a97842\necl:amb pid:418635216\n\npid:845398140\nbyr:1933 iyr:2016 ecl:oth eyr:2024 hcl:#cfa07d hgt:169cm\n\neyr:2040 hgt:161cm\nbyr:2018 ecl:blu\niyr:2022 hcl:#866857 cid:252\npid:286344655\n\nhcl:#6b5442\neyr:2027\nhgt:191cm iyr:2012\npid:642929864 ecl:amb byr:1934\n\niyr:2015 hcl:#cfa07d ecl:grn\neyr:2029 pid:163012663 hgt:150cm\n\neyr:2030 byr:1938 hgt:188cm iyr:2018\npid:652645847 hcl:#b6652a ecl:grn\n\npid:157056211 iyr:2010 hcl:#cfa07d ecl:gry\neyr:2024 byr:1961 hgt:177cm\n\neyr:2020 hcl:#60945e ecl:brn iyr:2011 pid:688090869 hgt:171cm\nbyr:1941\n\neyr:2023 hgt:188cm byr:1964 hcl:#7d3b0c ecl:gry iyr:2017\n\nbyr:1996\nhcl:#733820 pid:142902538\neyr:2023 iyr:2012 ecl:oth\nhgt:166cm\n\nhgt:62in cid:125 eyr:2022 hcl:#b6652a iyr:2011\necl:amb pid:220826562\n\necl:grn hgt:72in byr:1991 eyr:2020 pid:281076310 hcl:#6b5442\n\niyr:2030 hgt:78 eyr:1966 byr:2008 cid:260 ecl:grt hcl:5d2e21\n\niyr:2017 pid:388674097 byr:1950 ecl:grn cid:338\nhgt:159cm\nhcl:#6b5442\neyr:2025\n\nhgt:92 eyr:2031 cid:52 pid:169cm hcl:2695be\niyr:1987\nbyr:2011\n\npid:396358436 hcl:#ceb3a1\nbyr:1976 eyr:2025\niyr:2012 ecl:brn\nhgt:174cm\n\npid:389292752\neyr:2027 cid:335 hgt:65in byr:1974 hcl:#6b5442 ecl:oth iyr:2019\n\necl:amb iyr:2010 hcl:#602927 hgt:164cm byr:1966 pid:749383114 eyr:2026\n\npid:656206688\nhgt:175cm\nhcl:#6b5442 byr:1961 eyr:2028\necl:amb iyr:2010\n\nhgt:179cm eyr:2028 byr:1958\npid:095076581 ecl:gry\nhcl:#733820\niyr:2017\n\nbyr:1960\ncid:309 ecl:utc iyr:2012\nhgt:172cm pid:395100903\neyr:2030 hcl:#cfa07d\n\neyr:2021 cid:98 pid:387957353 byr:1941\nhgt:192cm\nhcl:#efcc98 ecl:brn\n\npid:143359781\nhcl:#a97842\nbyr:1996\nhgt:184cm eyr:2020 cid:319 ecl:blu iyr:2020\n\neyr:2025 iyr:2013 hgt:154cm hcl:#b6652a ecl:oth byr:1979 pid:976151938\n\niyr:2017\nhgt:118 ecl:#f0f31e\neyr:2028\nhcl:z\n\niyr:2016 hgt:153cm hcl:#6d55cd eyr:2026 ecl:hzl byr:1943 pid:257485710 cid:165\n\nhcl:#ceb3a1 ecl:blu byr:1937 hgt:152cm iyr:2020 eyr:2026 pid:032844291\n\nhcl:#fffffd byr:1937 pid:122104515 ecl:hzl\niyr:2018\neyr:2027 hgt:163cm\n\nbyr:1987 pid:052848077 hcl:#341e13\niyr:2015 hgt:172cm eyr:2022\n\npid:897642631 iyr:2015 byr:1941 hcl:#733820 ecl:brn eyr:2020 hgt:179cm\n\nhcl:#7d3b0c ecl:grn eyr:2029 pid:232785519 cid:258 iyr:2013 byr:1999\n\nhgt:152cm ecl:gry hcl:#18171d eyr:2020 byr:1971 iyr:2020 pid:211826434\n\nhcl:#866857\neyr:2022 pid:979633771 ecl:hzl iyr:2014 byr:1963\n\ncid:124\necl:utc hgt:77 hcl:#866857\nbyr:1979 iyr:2013\npid:92518200 eyr:1968\n\nhcl:#888785 eyr:2022 pid:233642738 byr:1959 hgt:191cm\necl:blu iyr:2010\n\npid:#adbcd3 ecl:zzz eyr:2025 cid:129 byr:2028 hcl:z hgt:166cm iyr:2011\n\ncid:131 hgt:169cm\niyr:2020\nbyr:1994\neyr:2021\necl:amb hcl:#c0946f\npid:243158461\n\nhcl:#18171d byr:1980 ecl:oth hgt:153cm eyr:2028 iyr:2020 pid:629055498\n\nhcl:#b6652a\nhgt:152 ecl:blu pid:175cm iyr:2015 byr:1941 eyr:1961\n\neyr:2025 hcl:#7d6ede byr:1988\nhgt:150cm\necl:hzl cid:267\npid:794369607\niyr:2012\n\npid:538963835\necl:oth byr:1958\nhgt:173cm\neyr:2027 cid:63\niyr:2018 hcl:#602927\n\nhcl:#866857\nhgt:163cm byr:1925 iyr:2011\necl:oth\neyr:2023 cid:163\n\necl:grn pid:411555227 byr:1974 eyr:2020 hgt:153cm iyr:2015 hcl:#a97842\n\ncid:111 pid:473064654 byr:1948\necl:brn iyr:2013\nhcl:#16fa7a\neyr:2024 hgt:184cm\n\niyr:2016 hgt:170cm byr:1993 pid:487807940 hcl:#efcc98 eyr:2024 ecl:gry\n\nhcl:#a97842 pid:891517350 iyr:2012\nbyr:1937\necl:amb\neyr:2030\nhgt:171cm\n\ncid:330 ecl:gry\nhgt:163cm eyr:2021\nhcl:#733820 byr:1991 pid:109734880\n\necl:brn iyr:1934 eyr:2021\nhgt:62\nhcl:z pid:720470429\n\necl:blu\npid:669045673 iyr:2015\nhgt:164cm\nhcl:#866857\n\nhgt:158cm hcl:#623a2f byr:1950\neyr:2020 ecl:gry pid:708073090\niyr:2018\n\nhgt:158cm iyr:2017 eyr:2027\necl:brn pid:840573419 hcl:#18171d byr:1968\n\necl:oth cid:81 hcl:#ceb3a1 eyr:2021\niyr:2020 byr:1959\nhgt:62in\npid:634019849\n\nhgt:180cm pid:651174767 iyr:2013 byr:1948 eyr:2022 hcl:#efcc98 ecl:grn\n\neyr:2021 ecl:grn\ncid:95 hcl:#733820 iyr:2016 hgt:174cm byr:1931\n\nhcl:90e96c eyr:1948 ecl:zzz\nbyr:1984 pid:8325286529\nhgt:59in\ncid:180\n\ncid:293 hgt:193cm ecl:oth hcl:#602927\neyr:2021 byr:1959 iyr:2016\n\npid:425216058\nhcl:#7d3b0c hgt:67in ecl:blu eyr:2022\niyr:2016 byr:1936 cid:182\n\necl:#c93c79 byr:2021 iyr:2021\nhgt:69cm pid:#fa092e eyr:2040\n\nhcl:#733820 iyr:1964\nhgt:190cm\npid:121828083 byr:2028 ecl:blu eyr:2030\n\nbyr:1986\neyr:2023 ecl:hzl hcl:#c0946f hgt:152cm pid:750393977 cid:308 iyr:2015\n\nhgt:152cm byr:2004 hcl:z eyr:2038 ecl:#e92725 iyr:2023 pid:55783937\n\necl:grn eyr:2023 hcl:#7d3b0c byr:1940 iyr:2017 pid:312213917 hgt:166cm\n\necl:grn pid:293691668\nbyr:1949 hgt:60in\neyr:2021\nhcl:#efcc98 iyr:2017\n\niyr:2018 hgt:156cm ecl:oth\nhcl:#ceb3a1\npid:622764582 eyr:2020 cid:124\n\neyr:2021\npid:146888876 byr:1962 iyr:2010 cid:63 ecl:amb hgt:188cm hcl:#a97842\n\npid:861542171 hcl:#866857\necl:brn\niyr:2010 byr:1975\neyr:2020\n\nhgt:157cm hcl:#18171d eyr:2022 byr:1936 iyr:2014 pid:478341738 ecl:amb\n\necl:brn\neyr:2026\npid:553753060 hgt:186cm\niyr:2019\nhcl:#623a2f\nbyr:1991\n\niyr:2018 ecl:blu hcl:#cfa07d hgt:162cm byr:1924 pid:721804049 eyr:2023\n\nbyr:1970 hgt:181cm eyr:2027 ecl:hzl pid:171cm iyr:2012 hcl:#5f4282\n\ncid:52\necl:blu iyr:2011\npid:027908077 hcl:#b6652a hgt:158cm\nbyr:1986\n\npid:160cm hcl:91324c eyr:2034 iyr:2015\nbyr:2021\nhgt:175in ecl:brn\n\ncid:164 iyr:2014 byr:1948 hgt:163cm pid:701930596 ecl:gry\neyr:2020 hcl:#733820\n\nbyr:1951 hcl:#fffffd pid:456803587 hgt:157cm ecl:blu iyr:2010 eyr:2020\n\necl:#663e48\npid:9157891148 hgt:66cm iyr:1989\nbyr:1932\n\nbyr:1993\nhgt:167cm\necl:amb iyr:2010 hcl:#3da943 pid:340209998 eyr:2021\n\nbyr:1968\neyr:2021 pid:915482982 hcl:#84a907 iyr:2020 hgt:176cm ecl:grn\n\nhgt:190 ecl:oth hcl:#6b5442\nbyr:2023\npid:5211866539\niyr:2028 cid:276\n\nhcl:#ceb3a1 hgt:177cm byr:1966\npid:#9f5b93\niyr:2002 eyr:1958\necl:oth\n\nhcl:#888785 eyr:2025 iyr:2017\nhgt:187cm pid:856993600 ecl:oth\n\nhgt:186\nhcl:z\neyr:2025\nbyr:2013 pid:6869591443 ecl:oth\n\niyr:2015 pid:317156655 cid:144\nhgt:151cm hcl:#623a2f ecl:brn\nbyr:1966 eyr:2021\n\necl:hzl pid:161653223 eyr:2026 iyr:2017 byr:1980\nhcl:#18171d\n\niyr:2009\npid:618443261\nbyr:1989 hcl:#23ef8c eyr:2026 hgt:167cm ecl:grn\n\necl:oth\nhcl:#fffffd iyr:2018 eyr:2022 pid:953490888\n\neyr:2026 hcl:#c0946f hgt:165cm iyr:2016 byr:1921\necl:blu pid:490419824\n\nhgt:150cm hcl:#b6652a ecl:brn byr:1937 iyr:2013 pid:824096447 eyr:2027\n\necl:amb\nhcl:#733820 byr:1997 iyr:2015 cid:269 hgt:96 eyr:2022 pid:475968048\n\nbyr:1985 hgt:186cm eyr:2022 ecl:gry\npid:050842095 iyr:2014\n\necl:hzl byr:1942 cid:294 hgt:191cm pid:768437232\neyr:2024 hcl:#623a2f\n\nbyr:1974 eyr:2022 hcl:#afb1a8 ecl:oth\nhgt:159cm\ncid:252\npid:619988658 iyr:2018\n\necl:oth eyr:2029 hgt:175cm\npid:548668762 byr:1982 iyr:2020 hcl:#c0946f\n\necl:gry hcl:#18171d\nhgt:166 pid:#3f6172 iyr:1978\neyr:2031 byr:2021\n\nbyr:1933 hcl:#c0946f ecl:gry eyr:2028\npid:594772420\nhgt:167cm\niyr:2020\n\ncid:332 hcl:#623a2f iyr:2018 pid:706055429 byr:1971 eyr:2022 ecl:gry\n\nhgt:187cm hcl:#888785\npid:224041851 eyr:2029\necl:blu iyr:2012 byr:1991\n\necl:#9c38d0\nhgt:161in\nhcl:099d45 byr:2015 eyr:1994\npid:2730108307\niyr:2024 cid:229\n\nhgt:184cm ecl:grn byr:1938 pid:996091727\ncid:115\nhcl:#866857 iyr:2020 eyr:2020\n\npid:883396674 iyr:2019\neyr:2023 ecl:blu\nbyr:1979 hcl:#733820 cid:125\nhgt:181cm\n\nhgt:190cm cid:325 hcl:#866857 eyr:2027 ecl:gry\niyr:2013 pid:317703100 byr:1968\n\nhcl:#866857\necl:gry pid:652638412\neyr:2025 iyr:2015 hgt:158cm byr:1953\n\nbyr:1956\nhcl:#cfa07d eyr:2025\npid:728272575 hgt:162cm\ncid:291 ecl:blu iyr:2020\n\nhgt:189cm byr:1980 hcl:#97fd64 ecl:grn iyr:2013\npid:181599378 eyr:2029\n\niyr:2018\nhcl:#007101 ecl:brn byr:1947 cid:265 hgt:159cm pid:288707610 eyr:2024\n\nhgt:62cm pid:2407695078\neyr:2039 byr:2014\niyr:1930 hcl:z\necl:grt\n\nhgt:65cm\necl:hzl\nbyr:1994 eyr:2004 hcl:z cid:130 pid:863191800\n\npid:125685599 byr:1995\necl:blu cid:57 hgt:172cm\niyr:2020\nhcl:#b6652a\neyr:2024\n\necl:gry byr:1961\niyr:2010 eyr:2022 pid:591409441\ncid:314\nhcl:#341e13\n\niyr:2020\necl:hzl hcl:#efcc98 byr:1983 hgt:174cm\neyr:2028\n\neyr:2029 byr:1924 iyr:2013 pid:662719101\necl:amb cid:77\n\necl:oth\niyr:2013 byr:1947 pid:532607157 hcl:#7d3b0c eyr:2030 hgt:191cm\n\npid:528940525 hcl:#18171d\niyr:2014 eyr:2029 cid:181\nbyr:1967\necl:gry hgt:64in\n\nhgt:71in iyr:2019 hcl:#cfa07d eyr:2029\npid:785691813\nbyr:1996 ecl:hzl cid:335\n\nbyr:2015\npid:174cm hgt:79 hcl:z iyr:1970 cid:66 eyr:1993 ecl:lzr\n\nhgt:188cm\neyr:2025 cid:107 ecl:grn\npid:286480470 hcl:#623a2f byr:1967\niyr:2015\n\neyr:2027 hcl:#7d3b0c cid:287\npid:319840760 iyr:2017 hgt:179cm ecl:gry\n\nbyr:1976 ecl:brn iyr:2020 eyr:2023 hgt:154cm pid:964286153\n\niyr:2015 eyr:2027 hgt:173cm byr:1983 pid:500176757 ecl:amb hcl:#7d3b0c\n\ncid:282 pid:697942299 hgt:185cm\neyr:2026 hcl:#7d3b0c iyr:2017 byr:2002 ecl:amb\n\niyr:2014 hcl:#18171d pid:044482202\necl:oth\nhgt:163cm eyr:2021 byr:1982\n\necl:gry hcl:#7d3b0c\niyr:2019 eyr:2020 hgt:187cm\npid:617117265\n\neyr:2035\necl:#5525a9 cid:123 byr:2009 pid:15381071 iyr:2028\nhcl:#b6652a\n\neyr:2021 hgt:165cm cid:207\niyr:2010 ecl:gry\nbyr:1929 hcl:#733820 pid:442632632\n\npid:69132960 cid:84 hgt:107 eyr:2023\necl:gmt iyr:2016\nhcl:z\nbyr:2020\n\neyr:2027 pid:281765118 hcl:#ceb3a1 hgt:193cm\nbyr:1955 ecl:gry cid:321 iyr:2010\n\necl:brn hgt:71in\ncid:189 byr:1962 eyr:2023 iyr:2018 pid:780797141 hcl:#866857\n\npid:984503466 ecl:amb hgt:192cm byr:1942 hcl:#dcc50d iyr:2020 eyr:2030 cid:250\n\nhcl:#7d3b0c eyr:2028\nhgt:178cm\necl:gry\niyr:2011\nbyr:1923\npid:960277768\n\nbyr:2000 ecl:amb cid:199 eyr:2027 iyr:2020 pid:785585164 hcl:#888785\nhgt:164cm\n\niyr:2015 pid:619005249\necl:brn byr:1955 eyr:2028\nhgt:183cm\nhcl:#a97842\n\necl:grn\nbyr:1970 cid:339\niyr:2011 hcl:#a97842 pid:952307953 eyr:2027 hgt:159cm\n\nhcl:ff4451 iyr:2022\nbyr:1972 ecl:#86cbc5 pid:29044223 eyr:1985\nhgt:62cm\n\nhcl:#623a2f iyr:2013 eyr:2023 hgt:164cm byr:1956\necl:oth\n\neyr:2028 hcl:#733820 pid:767003752 byr:1935 hgt:167cm iyr:2016\necl:oth\ncid:215\n\ncid:125 ecl:amb\niyr:2019 hcl:#18171d eyr:2022 hgt:163cm\npid:239764055\nbyr:1954\n\necl:gry\niyr:2019 hcl:#cfa07d byr:1929 pid:221011852\ncid:274 eyr:2026 hgt:158cm\n\nhcl:#a97842 iyr:2016 hgt:159cm\nbyr:1998 ecl:hzl eyr:2020 cid:207\n\npid:051242790\niyr:2024\nbyr:1975 hcl:#602927\necl:grn hgt:160cm eyr:2014\n\necl:#0b3ea5 hcl:z pid:#122ff0\nbyr:2007\nhgt:178 iyr:2013\neyr:1950\n\nhgt:171cm hcl:#cfa07d pid:674448249\necl:hzl eyr:2026\ncid:297 byr:1928\n\necl:hzl eyr:2021 hcl:#b6652a pid:856617617\nbyr:1949\nhgt:153cm iyr:2015\n\nhgt:164cm ecl:gry\neyr:2025\npid:147932207 iyr:2011 byr:1984 hcl:#fffffd\n\neyr:2027 hcl:#7d3b0c\npid:377701492 ecl:gry byr:1971 hgt:174cm\niyr:2023\n\nbyr:2001 hcl:#4784a2 hgt:161cm iyr:2014 eyr:2025 pid:955262336\necl:amb")
(->> (str/split data #"\n\n")
  (map #(str/split % #"\s+|:"))
  (map (partial apply hash-map))
  (map #(dissoc % "cid"))
  (map count)
  (filter #{7})
  (count))

(->> (str/split data #"\n\n")
  (map #(str/split % #"\s+|:"))
  (map (partial apply hash-map))
  (map #(dissoc % "cid"))
  (filter #(-> % count (= 7)))
  (filter #(<= 1920 (read-string (% "byr")) 2002))
  (filter #(<= 2010 (read-string (% "iyr")) 2020))
  (filter #(<= 2020 (read-string (% "eyr")) 2030))
  (filter #(let [hgt (% "hgt")]
             (cond
               (str/ends-with? hgt "cm") (<= 150 (read-string (str/replace hgt "cm" "")) 193)
               (str/ends-with? hgt "in") (<= 59 (read-string (str/replace hgt "in" "")) 76))))
  (filter #(->> "hcl" % (re-matches #"#[0-9a-f]{6}")))
  (filter #(->> "ecl" % #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"}))
  (filter #(->> "pid" % (re-matches #"\d{9}")))
  (count))
