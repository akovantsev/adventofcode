(ns adventofcode.y2022.day07
  (:require [clojure.string :as str]
            [com.akovantsev.blet.core :refer [blet blet!]]
            [adventofcode.utils :as u]))


(def sample "$ cd /\n$ ls\ndir a\n14848514 b.txt\n8504156 c.dat\ndir d\n$ cd a\n$ ls\ndir e\n29116 f\n2557 g\n62596 h.lst\n$ cd e\n$ ls\n584 i\n$ cd ..\n$ cd ..\n$ cd d\n$ ls\n4060174 j\n8033020 d.log\n5626152 d.ext\n7214296 k")
(def input "$ cd /\n$ ls\n113975 bqpslnv\n50243 btttmt.nmb\ndir gbjh\ndir hlpzbht\n43500 lblt\ndir phpmmtvc\ndir plbjmdl\ndir tggr\n268766 zmllsrzc.qqq\n$ cd gbjh\n$ ls\n64971 dhmprc.qpl\ndir jtgbg\ndir pzdn\ndir slwhsqw\n$ cd jtgbg\n$ ls\n243089 fbfmm\ndir hzjcc\ndir jgpnm\n7952 ljgwgqg\n51001 lzwrpqvq.tfq\n139239 qbn.gbr\ndir smb\ndir vvhmmn\n15541 zhvgcc\n$ cd hzjcc\n$ ls\n262498 zmllsrzc\n$ cd ..\n$ cd jgpnm\n$ ls\ndir php\ndir rlp\n$ cd php\n$ ls\n289068 lqdsjjm\n$ cd ..\n$ cd rlp\n$ ls\ndir dhlspmh\ndir mlsqrz\ndir slwhsqw\n$ cd dhlspmh\n$ ls\n249350 gtbp.ttr\n$ cd ..\n$ cd mlsqrz\n$ ls\n31876 scwj.cjv\n$ cd ..\n$ cd slwhsqw\n$ ls\n2424 vbpwn.qjn\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd smb\n$ ls\n29124 jgpnm.qrq\n$ cd ..\n$ cd vvhmmn\n$ ls\n40455 fbfmm\n$ cd ..\n$ cd ..\n$ cd pzdn\n$ ls\ndir bpdbclp\ndir gvvgncqh\ndir jfzw\ndir nlwnv\n$ cd bpdbclp\n$ ls\n65147 gdrj.qfs\n$ cd ..\n$ cd gvvgncqh\n$ ls\ndir fdcdh\ndir jnfhsqrl\n52531 lblt\ndir lprd\ndir qzmcfrhq\ndir rmbmpjc\n$ cd fdcdh\n$ ls\n285507 vrbhb.fqr\n$ cd ..\n$ cd jnfhsqrl\n$ ls\ndir ddzqtsvf\n189748 fdcdh.dhj\n217915 rpfw.wtt\ndir wwrrtc\n122077 zctzcb\n$ cd ddzqtsvf\n$ ls\ndir fdcdh\n$ cd fdcdh\n$ ls\ndir fhmpzq\n193340 pqq\n267704 scwj.cjv\n$ cd fhmpzq\n$ ls\ndir ghzrhzs\n198001 thddfc.mlv\n$ cd ghzrhzs\n$ ls\n82916 rjclmm.wcp\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd wwrrtc\n$ ls\n291755 fdcdh\n$ cd ..\n$ cd ..\n$ cd lprd\n$ ls\n247564 czqmpt\n$ cd ..\n$ cd qzmcfrhq\n$ ls\n102306 bqpslnv\n$ cd ..\n$ cd rmbmpjc\n$ ls\n282221 ddvhnf\n50544 qcbsqcp.hsp\ndir tshl\n$ cd tshl\n$ ls\n592 fdcdh\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd jfzw\n$ ls\ndir fcwsrnjg\ndir ftnlwhpn\ndir ghqt\n52762 qblnjwmq.mgl\n45621 zmllsrzc.jcv\n$ cd fcwsrnjg\n$ ls\n81276 mbvcdgvl\n$ cd ..\n$ cd ftnlwhpn\n$ ls\n78460 fbfmm\n$ cd ..\n$ cd ghqt\n$ ls\n110015 dvtl.nbw\n102205 rlblp.zcw\n$ cd ..\n$ cd ..\n$ cd nlwnv\n$ ls\ndir cvbq\ndir jgpnm\n226628 scwj.cjv\ndir zmllsrzc\n$ cd cvbq\n$ ls\n224362 fvrh.fcp\ndir ndjlpwpw\n93098 pphz.tmm\n30583 qdsgm.hjr\ndir qmqlf\n$ cd ndjlpwpw\n$ ls\n17383 ndl.cml\n$ cd ..\n$ cd qmqlf\n$ ls\n296208 fdcdh.bwr\n246624 lblt\n194615 slwhsqw.jhl\ndir tsrgs\n31676 zmllsrzc.scg\n$ cd tsrgs\n$ ls\ndir fdcdh\n164026 jnhfrb.mzm\n109383 lblt\n138073 zctzcb\n$ cd fdcdh\n$ ls\n128762 lblt\n264881 zmllsrzc\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd jgpnm\n$ ls\ndir bgv\n49488 wmp\n$ cd bgv\n$ ls\ndir fpvvw\n296599 rdng.ngn\n59418 vqvbq.tzz\ndir wvdqjn\ndir zlwvlpw\n$ cd fpvvw\n$ ls\n215634 gts.znn\n12520 lblt\n304330 nvd.tlj\n84828 qcgqj.mwg\n$ cd ..\n$ cd wvdqjn\n$ ls\n142231 zmllsrzc\n$ cd ..\n$ cd zlwvlpw\n$ ls\ndir slllz\n$ cd slllz\n$ ls\n201551 scwj.cjv\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd zmllsrzc\n$ ls\ndir dgdgccc\n262704 dwcvsn.lgf\ndir fqrcw\n277851 rjclmm.wcp\ndir zmllsrzc\n$ cd dgdgccc\n$ ls\n276609 bqpslnv.mcr\n$ cd ..\n$ cd fqrcw\n$ ls\ndir fghsd\n320352 rjclmm.wcp\n$ cd fghsd\n$ ls\n207271 fbfmm\n236098 gwvhh.nsv\n$ cd ..\n$ cd ..\n$ cd zmllsrzc\n$ ls\n58596 rjclmm.wcp\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd slwhsqw\n$ ls\n206029 bqpslnv.zjg\ndir jfp\n$ cd jfp\n$ ls\n51699 zmllsrzc\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd hlpzbht\n$ ls\n302660 bqpslnv\n221877 lblt\n148517 vpdfdb.vsr\n54658 zmllsrzc\n$ cd ..\n$ cd phpmmtvc\n$ ls\ndir bqpslnv\ndir dptzbgc\n311157 fbfmm\n162934 gpvnh.tnb\ndir ldcqq\ndir nld\n116676 rjclmm.wcp\ndir vnswqbm\ndir zmllsrzc\n$ cd bqpslnv\n$ ls\ndir cwbdcvgv\ndir dtbbbgw\n296450 fbfmm\ndir jnfvpnm\ndir lmqtmbh\ndir lqb\n49347 qpt.jsl\ndir srq\ndir vtnptsl\n$ cd cwbdcvgv\n$ ls\n103874 gqztffsq.vst\n116399 sjjstn\n$ cd ..\n$ cd dtbbbgw\n$ ls\ndir dptrzvz\n17883 fbfmm\ndir jgrdzbbh\n$ cd dptrzvz\n$ ls\ndir gfzfhjn\n287625 nplcdq.ltn\ndir vssdlrp\n323499 wlz\n$ cd gfzfhjn\n$ ls\n217616 fbfmm\n18148 lblt\n79165 rbtvqtrr.dqp\n$ cd ..\n$ cd vssdlrp\n$ ls\n86305 lblt\n$ cd ..\n$ cd ..\n$ cd jgrdzbbh\n$ ls\ndir fdcdh\ndir fttv\ndir wzwndq\n$ cd fdcdh\n$ ls\n242121 lblt\n$ cd ..\n$ cd fttv\n$ ls\n67997 zctzcb\n$ cd ..\n$ cd wzwndq\n$ ls\n16310 bqpslnv.rfj\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd jnfvpnm\n$ ls\n17107 lblt\n$ cd ..\n$ cd lmqtmbh\n$ ls\n200855 bqpslnv\ndir cpdt\n304568 rlnf.dfw\n$ cd cpdt\n$ ls\n56206 fdcdh.jrc\n138559 jgpnm\n123081 rgclnp.vtg\n$ cd ..\n$ cd ..\n$ cd lqb\n$ ls\ndir sbrzrb\n$ cd sbrzrb\n$ ls\ndir mglsdblq\n$ cd mglsdblq\n$ ls\n172704 rjclmm.wcp\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd srq\n$ ls\ndir bqpslnv\n193258 cqslbqml\n123266 fbfmm\ndir hmhbtnp\ndir pcrmfr\n27362 pqprb.chw\n47189 rjclmm.wcp\n288989 slwhsqw\n$ cd bqpslnv\n$ ls\n66777 qqm.jvh\n$ cd ..\n$ cd hmhbtnp\n$ ls\n296063 dzm.chg\n204474 fbfmm\n146902 rjclmm.wcp\n$ cd ..\n$ cd pcrmfr\n$ ls\n94907 bqpslnv.wtm\n$ cd ..\n$ cd ..\n$ cd vtnptsl\n$ ls\ndir bwrbw\n12048 djczcg\ndir drhf\n97998 fdcdh\ndir hjljrm\n171153 jgpnm.vwr\n169093 pzftw.ccl\n241263 slwhsqw.ntc\ndir thjbhrzj\n$ cd bwrbw\n$ ls\n226255 fdcdh.qzw\n283525 pjwv.mql\n131501 slwhsqw.gbr\n257703 wqfbq\n87789 zmllsrzc\n$ cd ..\n$ cd drhf\n$ ls\n297259 ffgv.jzr\ndir rszprww\n12806 zvgmpdnn.psr\n$ cd rszprww\n$ ls\ndir bgsnrdqv\ndir grvmtw\n251007 scwj.cjv\n$ cd bgsnrdqv\n$ ls\n56538 jdbbfgj.fpw\n$ cd ..\n$ cd grvmtw\n$ ls\n68025 trbfdqbz.gdw\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd hjljrm\n$ ls\ndir dzjwf\n$ cd dzjwf\n$ ls\n230855 jgpnm\ndir jvd\ndir nnwc\ndir zmllsrzc\n$ cd jvd\n$ ls\n280910 fztmh\n$ cd ..\n$ cd nnwc\n$ ls\ndir bffsm\n110991 jgpnm.wbq\ndir ttfh\n$ cd bffsm\n$ ls\ndir dwgp\n$ cd dwgp\n$ ls\n5659 bnlvzmbr.tqc\n$ cd ..\n$ cd ..\n$ cd ttfh\n$ ls\n147196 zpqgfp.qmm\n$ cd ..\n$ cd ..\n$ cd zmllsrzc\n$ ls\ndir gzmjpctz\n$ cd gzmjpctz\n$ ls\n72856 cffjfsl.mhf\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd thjbhrzj\n$ ls\ndir fdcdh\n260378 lblt\n264613 zmllsrzc.pjd\n$ cd fdcdh\n$ ls\n225223 fhfgv.wjn\n313245 whs\n197514 zctzcb\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd dptzbgc\n$ ls\ndir nmsdfhfc\ndir nmvpwnmh\ndir nsqv\n148224 plq.mns\n101844 scwj.cjv\ndir slwhsqw\ndir wmm\n$ cd nmsdfhfc\n$ ls\ndir crfw\n$ cd crfw\n$ ls\n90593 vpjd\n$ cd ..\n$ cd ..\n$ cd nmvpwnmh\n$ ls\n265813 fbfmm\ndir fdcdh\n267289 fdcdh.gcj\ndir hnttb\ndir jgpnm\n168680 pbfz.zcb\n198084 zdlbf\n$ cd fdcdh\n$ ls\n141722 fdcdh.pll\n$ cd ..\n$ cd hnttb\n$ ls\ndir gbfvsg\ndir jngrjqm\ndir slwhsqw\n$ cd gbfvsg\n$ ls\n84991 rjclmm.wcp\n55681 slwhsqw.gtl\n219041 vtvlz.rws\n2947 zmllsrzc.lzr\n$ cd ..\n$ cd jngrjqm\n$ ls\ndir bqpslnv\n125321 fbfmm\n222370 gjpt\n$ cd bqpslnv\n$ ls\n47973 rhzrhrh.mdh\n$ cd ..\n$ cd ..\n$ cd slwhsqw\n$ ls\n126807 hwp\n169701 sjgzj\ndir tgpnwrn\n286591 wnfjnp.lst\n172105 zbrwg.ljs\n111461 zmllsrzc.vmj\n$ cd tgpnwrn\n$ ls\n298873 lblt\n92666 wjzpj.qzj\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd jgpnm\n$ ls\n135042 fdcdh.fgg\n240749 rjclmm.wcp\n$ cd ..\n$ cd ..\n$ cd nsqv\n$ ls\n233412 pfnvqv.qdn\n$ cd ..\n$ cd slwhsqw\n$ ls\ndir nrt\ndir spsjgzfr\ndir zmllsrzc\n$ cd nrt\n$ ls\n210674 sffpw.gwb\n$ cd ..\n$ cd spsjgzfr\n$ ls\n272099 fbfmm\n312467 vvtlvcz.qhp\n2119 wcmdmqh\n$ cd ..\n$ cd zmllsrzc\n$ ls\n242647 fbfmm\n307133 lblt\n279148 mngdrg.qlq\n63394 sgprzhv.vlj\n$ cd ..\n$ cd ..\n$ cd wmm\n$ ls\ndir bcndl\ndir cnlsb\n$ cd bcndl\n$ ls\ndir cqgjzqt\ndir hrsdjfv\ndir zmllsrzc\n$ cd cqgjzqt\n$ ls\n91704 cfvmd.qnv\n$ cd ..\n$ cd hrsdjfv\n$ ls\n240036 bqpslnv.tvl\n63562 fbfmm\n308727 pnvrr\n133855 zctzcb\n$ cd ..\n$ cd zmllsrzc\n$ ls\n177997 bqpslnv\n$ cd ..\n$ cd ..\n$ cd cnlsb\n$ ls\ndir vprqjr\n$ cd vprqjr\n$ ls\n128434 slwhsqw.vbt\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd ldcqq\n$ ls\n230118 bqpslnv\n18831 hdbnpq.mfb\ndir nqmwqb\n40981 rjclmm.wcp\n147598 zctzcb\n$ cd nqmwqb\n$ ls\n313907 dhcphcg.pgp\n$ cd ..\n$ cd ..\n$ cd nld\n$ ls\ndir bqpslnv\ndir chw\n317665 fdcdh\n321737 slwhsqw.tjb\ndir twbt\ndir vmvj\n79938 wvv.swg\n$ cd bqpslnv\n$ ls\ndir tvsv\n$ cd tvsv\n$ ls\n271095 fbfmm\ndir fdcdh\ndir jgpnm\n208219 ncgzcg.scr\n41278 scwj.cjv\n10197 tcsjntm.tmr\n255687 zctzcb\n$ cd fdcdh\n$ ls\ndir jvcwgbfn\n$ cd jvcwgbfn\n$ ls\n255839 lwnnjz\n$ cd ..\n$ cd ..\n$ cd jgpnm\n$ ls\n66446 rjclmm.wcp\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd chw\n$ ls\n163229 bqpslnv.cnb\n261637 fbfmm\n$ cd ..\n$ cd twbt\n$ ls\n180495 sbg.qtm\n$ cd ..\n$ cd vmvj\n$ ls\ndir bqpslnv\ndir lcjjc\ndir wsw\n$ cd bqpslnv\n$ ls\n189635 fbfmm\n87919 hgvh.gbq\n75372 pht.pjs\n198496 rhvqbnc\ndir sgjszb\n146711 zmllsrzc\n$ cd sgjszb\n$ ls\ndir jlr\n177552 tsvzdnwb\n$ cd jlr\n$ ls\n244272 zctzcb\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd lcjjc\n$ ls\ndir cmwm\n$ cd cmwm\n$ ls\n48565 lblt\n$ cd ..\n$ cd ..\n$ cd wsw\n$ ls\ndir nccwbcj\n$ cd nccwbcj\n$ ls\n203613 djcvsqs.njh\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd vnswqbm\n$ ls\ndir bmsqrg\ndir bqpslnv\n17334 dnj.bdf\n244204 fbfmm\n238971 fdcdh.qjd\ndir glb\ndir pzrvbvcn\ndir qjmtppt\ndir rtshvzr\n106110 sqsrsph.vwv\ndir wtdq\n230599 zctzcb\ndir zmllsrzc\n$ cd bmsqrg\n$ ls\n289739 mcs\ndir vvph\n195219 vwrrbpr.mzv\n144639 zctzcb\n$ cd vvph\n$ ls\ndir rqpzdtwl\n$ cd rqpzdtwl\n$ ls\n208410 gqzpsbtj\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd bqpslnv\n$ ls\ndir bsrgvwfd\n$ cd bsrgvwfd\n$ ls\ndir tlrgw\n236101 zhmsfq\n$ cd tlrgw\n$ ls\n18732 zctzcb\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd glb\n$ ls\ndir fdcdh\n227141 fdcdh.ddb\n124010 gwvf.thb\n31007 lnjwndc.pbf\ndir slwhsqw\ndir wmp\n316521 zctzcb\n$ cd fdcdh\n$ ls\n98547 scwj.cjv\n$ cd ..\n$ cd slwhsqw\n$ ls\ndir hcqtzl\n$ cd hcqtzl\n$ ls\n299368 lsvr.ccj\n141718 zctzcb\n$ cd ..\n$ cd ..\n$ cd wmp\n$ ls\n84719 fbfmm\ndir hwwlqrh\ndir nfbrq\ndir slwhsqw\n18295 zctzcb\n66949 zmllsrzc.spj\n$ cd hwwlqrh\n$ ls\ndir bvfsgfm\ndir jbttmc\n$ cd bvfsgfm\n$ ls\n105325 nbnbbf.rbj\n$ cd ..\n$ cd jbttmc\n$ ls\ndir tpdnt\n$ cd tpdnt\n$ ls\n256828 cwbwzq\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd nfbrq\n$ ls\n277164 bqpslnv.bzm\n$ cd ..\n$ cd slwhsqw\n$ ls\n56736 hwwng.hsr\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd pzrvbvcn\n$ ls\n177454 bvwv.gdg\ndir fdcdh\ndir mzzgvjs\ndir qsdmzl\n260924 scwj.cjv\ndir sfhpt\n190128 slwhsqw\n$ cd fdcdh\n$ ls\n15882 dstdsnr.jrm\n62377 lblt\ndir wnvgtp\n$ cd wnvgtp\n$ ls\n8890 wrzrp\n$ cd ..\n$ cd ..\n$ cd mzzgvjs\n$ ls\n324487 dhlgfwcv\n141946 dqm.rws\ndir mhr\ndir pdjn\n2675 rjclmm.wcp\ndir scdlp\n$ cd mhr\n$ ls\n287618 sdwmpzg.mcq\n$ cd ..\n$ cd pdjn\n$ ls\ndir gfhzg\ndir mcpzqvgn\n$ cd gfhzg\n$ ls\ndir bqpslnv\ndir pqrbn\n$ cd bqpslnv\n$ ls\n204588 rjclmm.wcp\ndir ztbmb\n$ cd ztbmb\n$ ls\n180817 zmllsrzc.mbd\n$ cd ..\n$ cd ..\n$ cd pqrbn\n$ ls\n254533 scwj.cjv\n78174 zmllsrzc.hlm\n$ cd ..\n$ cd ..\n$ cd mcpzqvgn\n$ ls\n117203 hwtps.twz\n$ cd ..\n$ cd ..\n$ cd scdlp\n$ ls\ndir mgqdbgm\n37561 nvb.plr\n$ cd mgqdbgm\n$ ls\ndir fhnz\n$ cd fhnz\n$ ls\n217104 jgpnm.pmw\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd qsdmzl\n$ ls\n249206 jgpnm\n$ cd ..\n$ cd sfhpt\n$ ls\n95502 lblt\n148395 qqtvgcdm.wjf\n$ cd ..\n$ cd ..\n$ cd qjmtppt\n$ ls\n201654 drtgpjh.dzn\n36730 hpmp\n$ cd ..\n$ cd rtshvzr\n$ ls\n261331 tztfqcl.msd\n$ cd ..\n$ cd wtdq\n$ ls\n176817 jwfdct\n$ cd ..\n$ cd zmllsrzc\n$ ls\ndir bvqwrs\n99444 lblt\n72341 qjwdwfdg.vzh\n$ cd bvqwrs\n$ ls\n259109 bjvgfmq.twd\ndir rclm\ndir zmllsrzc\n$ cd rclm\n$ ls\n157704 cgdtzs.plp\n224325 cvh.vms\ndir nhflts\n9088 slwhsqw\n$ cd nhflts\n$ ls\ndir vmmbsfw\n$ cd vmmbsfw\n$ ls\n22078 slwhsqw\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd zmllsrzc\n$ ls\n199587 cmglvt.cmr\n216785 fdcdh\ndir gccwrq\n$ cd gccwrq\n$ ls\n68584 ffdd.tsp\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd zmllsrzc\n$ ls\n213942 bclj.nnv\n$ cd ..\n$ cd ..\n$ cd plbjmdl\n$ ls\ndir bqpslnv\ndir gbs\n159730 jgpnm.pdj\ndir qdbsj\n145186 slwhsqw.hdf\ndir tlptvz\n226459 vdp\n$ cd bqpslnv\n$ ls\n177530 cbj.lpr\n303777 mtbwq.gjs\n$ cd ..\n$ cd gbs\n$ ls\n228129 dfdg\n$ cd ..\n$ cd qdbsj\n$ ls\n137655 bqpslnv\n$ cd ..\n$ cd tlptvz\n$ ls\ndir brssb\n303007 ddg\n70932 fzqdhn.qsn\ndir hhmq\n226788 qwvhc.qwj\n160118 zctzcb\n138524 zmllsrzc.vdm\n$ cd brssb\n$ ls\n222065 fdcdh.lgm\n$ cd ..\n$ cd hhmq\n$ ls\n23600 rjclmm.wcp\n$ cd ..\n$ cd ..\n$ cd ..\n$ cd tggr\n$ ls\n223836 lnwhpd.wnl")


(defn get-size [k m]
  (->> k m
    (map (fn [[k v]]
           (if (= v 'dir)
             (get-size k m)
             v)))
    (reduce + 0))

  #_
  (reduce-kv
    (fn [size k v]
      (if (= v 'dir)
        (+ size (get-size k m))
        (+ size v)))
    0 (m k)))

(defn get-dirs [ss]
  (let [path0 []]
    (loop [todo (str/split-lines ss)
           path []
           dirs {}]
      (blet [[line & todo-] todo
             [cd dir]    (str/split line #"\$ cd ")
             [size file] (str/split line #"\s")
             home   (= line "$ cd /")
             back   (= line "$ cd ..")
             ls     (= line "$ ls")
             path-  (pop path)
             path+  (conj path dir)
             dirs+  (assoc-in dirs [path (conj path file)] 'dir)
             files+ (assoc-in dirs [path file] (u/to-int size))]
        (cond
          (empty? todo), dirs
          home,,,,,,,,,, (recur todo- path0 dirs)
          back,,,,,,,,,, (recur todo- path- dirs)
          dir,,,,,,,,,,, (recur todo- path+ dirs)
          ls,,,,,,,,,,,, (recur todo- path dirs)
          (= size "dir") (recur todo- path dirs+)
          :else,,,,,,,,, (recur todo- path files+))))))

(defn p1 [ss]
  (let [dirs (get-dirs ss)]
    (->> dirs (keys) (map #(get-size % dirs)) (filter #(<= % 100000)) (reduce +))))

(defn p2 [ss]
  (blet [dirs     (get-dirs ss)
         names    (keys dirs)
         sizes    (zipmap names (map #(get-size % dirs) names))
         total    (sizes [])
         at-least (-> total (- 70000000) (+ 30000000))]
    (->> sizes vals (sort <) (drop-while #(< % at-least)) first)))


(assert (= 95437 (p1 sample)))
(assert (= 2061777 (p1 input)))
(assert (= 24933642 (p2 sample)))
(assert (= 4473403 (p2 input)))