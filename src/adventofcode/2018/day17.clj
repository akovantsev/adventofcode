(ns adventofcode.2018.day17
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            [adventofcode.utils :as u])
  (:import [java.util Deque ArrayDeque]))

(set! *print-length* 20)

(def input "x=301, y=218..246\nx=500, y=823..830\ny=1424, x=368..382\nx=506, y=429..440\nx=486, y=1773..1782\nx=409, y=1414..1426\nx=335, y=1372..1388\nx=474, y=1567..1571\ny=1819, x=386..390\nx=293, y=95..117\ny=1697, x=428..430\nx=517, y=296..307\nx=424, y=168..176\nx=453, y=915..921\nx=447, y=838..860\nx=471, y=181..199\nx=423, y=1819..1827\ny=674, x=373..387\ny=674, x=431..452\nx=323, y=626..632\nx=308, y=1162..1188\ny=504, x=457..459\nx=438, y=181..191\ny=1622, x=509..518\nx=293, y=447..473\ny=306, x=431..434\nx=449, y=474..488\nx=439, y=72..80\ny=1260, x=386..388\ny=1737, x=519..521\nx=406, y=463..467\ny=1559, x=489..491\ny=234, x=499..502\ny=763, x=422..427\nx=428, y=991..1003\nx=447, y=1421..1424\nx=370, y=340..357\ny=828, x=447..469\nx=294, y=1611..1619\ny=1112, x=401..403\ny=1575, x=342..344\nx=374, y=1148..1156\nx=472, y=1437..1449\nx=435, y=1414..1426\nx=342, y=1278..1292\ny=1498, x=498..508\nx=438, y=716..725\nx=380, y=777..789\nx=322, y=317..319\nx=527, y=587..590\ny=814, x=482..497\nx=418, y=1058..1072\ny=274, x=293..295\nx=313, y=170..175\nx=394, y=1838..1843\nx=372, y=1684..1693\nx=379, y=1517..1531\nx=497, y=870..873\nx=448, y=308..323\nx=359, y=148..162\nx=301, y=1542..1570\ny=340, x=418..420\ny=41, x=482..486\ny=1026, x=293..439\ny=682, x=405..424\nx=468, y=1034..1041\nx=461, y=1288..1300\ny=680, x=486..489\nx=431, y=436..448\nx=525, y=992..1003\nx=340, y=220..222\ny=1715, x=396..404\nx=456, y=1223..1237\ny=80, x=439..442\ny=1329, x=483..492\nx=489, y=664..680\nx=341, y=1538..1557\nx=467, y=1270..1282\nx=496, y=499..506\nx=354, y=1411..1413\nx=444, y=1287..1300\nx=430, y=1687..1697\nx=326, y=1164..1171\ny=20, x=486..513\ny=659, x=311..332\ny=1822, x=313..323\nx=429, y=218..226\nx=447, y=805..828\ny=511, x=322..333\nx=316, y=103..117\ny=1465, x=437..443\ny=1293, x=379..382\nx=446, y=159..172\nx=464, y=677..680\nx=376, y=1172..1174\nx=480, y=282..284\nx=326, y=1459..1483\nx=433, y=1223..1237\ny=1695, x=306..309\nx=440, y=938..960\nx=462, y=967..995\nx=481, y=554..559\nx=493, y=1749..1762\ny=1529, x=437..461\ny=1064, x=427..430\ny=1255, x=386..388\ny=980, x=416..435\ny=545, x=385..394\ny=760, x=342..349\nx=396, y=719..721\nx=483, y=308..312\ny=520, x=347..359\nx=486, y=10..20\nx=321, y=564..566\ny=596, x=412..429\nx=494, y=282..284\nx=342, y=294..296\ny=241, x=491..514\nx=296, y=625..632\nx=462, y=1707..1720\nx=429, y=966..977\nx=426, y=168..176\nx=412, y=670..679\ny=458, x=319..334\ny=1476, x=515..525\nx=304, y=1258..1263\nx=457, y=1418..1430\ny=169, x=456..459\ny=933, x=391..393\nx=360, y=967..971\nx=386, y=925..936\ny=1041, x=468..479\nx=304, y=947..957\nx=512, y=1651..1656\nx=415, y=7..18\nx=512, y=299..311\nx=418, y=1682..1700\nx=473, y=305..316\nx=293, y=1161..1188\ny=1586, x=479..496\nx=335, y=278..289\nx=426, y=966..977\ny=786, x=385..392\nx=466, y=401..422\nx=387, y=464..467\nx=382, y=1421..1424\nx=520, y=686..706\nx=427, y=742..763\nx=495, y=124..136\ny=853, x=373..375\nx=418, y=1315..1329\nx=525, y=1452..1476\ny=1305, x=408..423\ny=1124, x=487..497\nx=406, y=66..76\nx=388, y=1842..1853\ny=268, x=313..337\nx=477, y=1811..1821\nx=474, y=188..194\nx=311, y=640..659\nx=450, y=1151..1156\nx=427, y=1596..1609\ny=143, x=345..349\nx=441, y=523..526\nx=385, y=13..27\ny=1227, x=446..450\nx=450, y=1249..1259\ny=375, x=437..439\nx=365, y=823..831\ny=1460, x=418..420\ny=566, x=321..333\nx=395, y=861..883\nx=365, y=422..427\ny=176, x=424..426\nx=487, y=1098..1124\nx=386, y=1255..1260\nx=413, y=1269..1271\ny=238, x=499..502\ny=1741, x=448..473\nx=404, y=757..772\nx=392, y=158..176\nx=413, y=459..462\nx=513, y=367..378\ny=184, x=362..374\nx=334, y=1196..1214\ny=513, x=421..424\nx=306, y=687..701\nx=395, y=1168..1178\ny=1403, x=493..497\ny=595, x=309..321\nx=365, y=1791..1801\nx=491, y=1692..1695\nx=359, y=405..430\nx=498, y=304..316\nx=422, y=1380..1394\ny=1158, x=454..475\ny=573, x=422..444\ny=1174, x=376..383\ny=421, x=471..490\nx=519, y=296..307\nx=388, y=715..727\nx=496, y=323..336\nx=460, y=1063..1071\nx=294, y=1302..1318\nx=319, y=1235..1245\nx=356, y=1035..1043\ny=1234, x=446..450\ny=426, x=343..348\ny=388, x=308..323\nx=344, y=1575..1589\nx=454, y=600..624\ny=1031, x=498..520\nx=454, y=1268..1279\ny=1148, x=415..417\nx=367, y=1196..1205\ny=382, x=300..309\ny=1619, x=294..302\nx=347, y=667..669\nx=391, y=1414..1437\ny=1279, x=454..459\nx=499, y=234..238\ny=1297, x=451..454\nx=425, y=1100..1109\nx=387, y=52..55\ny=1649, x=448..457\nx=475, y=1583..1584\nx=464, y=1493..1505\ny=910, x=474..486\nx=401, y=1754..1771\nx=433, y=1767..1775\nx=333, y=965..985\nx=352, y=1693..1696\ny=1689, x=450..466\nx=312, y=1036..1047\nx=488, y=1793..1804\nx=481, y=1553..1565\nx=424, y=1248..1259\ny=507, x=449..466\nx=405, y=1500..1513\nx=492, y=1154..1167\nx=311, y=687..701\ny=794, x=422..431\nx=369, y=1746..1750\nx=518, y=1336..1344\nx=451, y=1421..1424\nx=416, y=947..957\nx=398, y=719..721\nx=369, y=548..570\nx=311, y=1601..1618\ny=955, x=484..496\nx=459, y=516..529\nx=489, y=1550..1559\nx=493, y=660..674\ny=1296, x=390..392\nx=500, y=588..590\nx=344, y=1320..1340\nx=423, y=1120..1132\nx=322, y=193..206\ny=382, x=420..445\nx=445, y=416..419\ny=1251, x=463..465\ny=1201, x=450..460\ny=777, x=471..498\ny=769, x=361..386\nx=435, y=1557..1582\ny=624, x=454..464\nx=315, y=1452..1473\nx=451, y=1437..1449\ny=1132, x=423..447\nx=309, y=1691..1695\nx=346, y=821..839\ny=159, x=366..369\nx=319, y=1711..1714\nx=366, y=198..206\nx=363, y=1060..1073\nx=405, y=1628..1631\nx=413, y=1134..1135\ny=172, x=486..503\nx=413, y=128..156\nx=336, y=104..117\nx=349, y=125..143\ny=620, x=429..446\nx=406, y=1141..1151\nx=468, y=1596..1607\ny=1740, x=308..329\nx=453, y=327..343\nx=313, y=966..976\nx=345, y=1279..1292\ny=727, x=388..409\nx=367, y=232..258\nx=377, y=303..316\ny=1426, x=409..435\ny=1718, x=382..388\nx=440, y=1365..1367\nx=485, y=1172..1174\ny=1592, x=307..314\nx=313, y=1820..1822\nx=310, y=1059..1065\ny=1000, x=415..419\ny=226, x=426..429\ny=752, x=361..371\nx=469, y=858..885\nx=413, y=1682..1700\nx=368, y=422..427\nx=483, y=1305..1329\nx=491, y=228..241\nx=499, y=408..418\nx=451, y=985..990\nx=309, y=780..794\nx=389, y=799..813\nx=339, y=692..694\nx=480, y=414..417\ny=646, x=356..380\nx=479, y=187..194\ny=1468, x=487..492\nx=485, y=1692..1695\ny=129, x=451..457\ny=1602, x=297..300\ny=1124, x=435..441\nx=501, y=131..133\nx=385, y=1107..1117\nx=356, y=633..646\ny=1409, x=431..448\nx=503, y=131..133\nx=331, y=467..470\nx=485, y=1747..1757\ny=1072, x=418..445\nx=359, y=503..520\ny=885, x=469..480\nx=428, y=1687..1697\ny=796, x=488..491\nx=307, y=1587..1592\nx=413, y=1538..1557\nx=472, y=1684..1698\nx=472, y=1304..1307\nx=352, y=1493..1507\ny=764, x=371..375\nx=405, y=926..936\nx=422, y=539..555\nx=357, y=1421..1444\nx=447, y=1491..1492\nx=450, y=1195..1201\nx=466, y=1685..1689\ny=1479, x=354..367\ny=12, x=421..424\ny=995, x=442..462\ny=1778, x=309..312\nx=517, y=60..64\nx=385, y=975..983\nx=357, y=1635..1654\ny=1571, x=446..474\nx=496, y=581..604\ny=322, x=312..334\ny=84, x=348..361\nx=366, y=156..159\nx=342, y=756..760\nx=407, y=654..664\ny=165, x=491..496\nx=449, y=498..507\nx=446, y=1227..1234\ny=1135, x=394..413\nx=306, y=808..825\nx=420, y=538..555\ny=760, x=308..324\ny=1529, x=509..521\nx=345, y=583..605\nx=497, y=812..814\nx=352, y=1321..1340\nx=375, y=1004..1005\nx=502, y=234..238\nx=409, y=715..727\ny=794, x=304..309\nx=313, y=1601..1618\nx=409, y=696..699\nx=416, y=1573..1577\nx=346, y=1121..1124\nx=457, y=716..725\nx=522, y=893..911\ny=1029, x=472..479\ny=527, x=412..415\nx=443, y=523..526\nx=451, y=653..664\nx=434, y=809..812\nx=365, y=1545..1563\ny=491, x=337..342\nx=404, y=236..240\nx=503, y=100..113\nx=503, y=1020..1024\ny=289, x=410..512\nx=337, y=262..268\ny=976, x=471..480\ny=381, x=374..376\nx=364, y=1421..1444\nx=437, y=809..812\ny=1009, x=495..507\nx=433, y=1102..1112\nx=386, y=1815..1819\nx=507, y=919..927\ny=809, x=434..437\nx=305, y=1662..1664\nx=467, y=117..138\nx=351, y=1836..1845\nx=480, y=834..846\ny=246, x=301..321\ny=1557, x=405..413\nx=430, y=39..50\nx=381, y=883..894\nx=443, y=1380..1394\nx=383, y=242..248\nx=328, y=1373..1388\nx=401, y=1792..1804\ny=319, x=317..322\ny=117, x=316..336\nx=407, y=104..117\nx=482, y=414..417\nx=481, y=1534..1543\nx=409, y=1392..1396\ny=217, x=485..503\ny=559, x=481..483\nx=503, y=919..927\nx=510, y=1315..1318\nx=452, y=672..674\nx=489, y=1221..1230\ny=1171, x=324..326\nx=451, y=1331..1344\ny=632, x=296..323\nx=321, y=1416..1435\nx=436, y=1397..1405\nx=419, y=998..1000\ny=722, x=334..362\nx=491, y=790..796\nx=367, y=1476..1479\ny=571, x=502..527\ny=1585, x=502..507\nx=484, y=1419..1444\nx=486, y=160..172\nx=343, y=1157..1166\nx=500, y=1601..1605\nx=414, y=163..185\nx=352, y=1245..1270\ny=669, x=518..521\ny=927, x=503..507\nx=359, y=583..605\nx=427, y=334..345\ny=883, x=395..407\nx=370, y=950..953\ny=705, x=396..417\nx=441, y=1042..1044\nx=399, y=1473..1484\ny=1796, x=497..521\nx=524, y=1840..1860\ny=1699, x=333..361\ny=862, x=454..461\nx=367, y=779..790\ny=1709, x=516..526\nx=319, y=433..458\nx=432, y=1357..1371\ny=1513, x=405..413\ny=367, x=412..417\nx=401, y=1112..1114\nx=458, y=181..199\nx=344, y=468..470\nx=391, y=929..933\nx=354, y=92..100\nx=340, y=1157..1166\nx=415, y=1138..1148\ny=829, x=390..392\ny=1060, x=335..347\nx=496, y=941..955\ny=555, x=398..412\ny=739, x=314..317\ny=44, x=471..493\nx=381, y=843..856\ny=494, x=496..508\nx=359, y=340..357\nx=312, y=1079..1083\nx=422, y=562..573\nx=419, y=830..833\nx=431, y=1849..1860\ny=833, x=419..421\nx=390, y=1464..1487\ny=206, x=306..322\ny=274, x=380..396\nx=390, y=609..633\nx=518, y=662..669\nx=298, y=1341..1345\ny=1020, x=503..505\nx=361, y=485..487\ny=1419, x=417..426\ny=1405, x=436..441\ny=1181, x=299..302\nx=314, y=1055..1059\nx=387, y=659..674\nx=480, y=857..885\nx=329, y=1732..1740\nx=470, y=1794..1804\nx=360, y=981..992\ny=1505, x=464..483\nx=435, y=517..529\nx=423, y=1140..1151\nx=446, y=1357..1371\ny=873, x=495..497\ny=435, x=393..419\ny=162, x=359..375\nx=360, y=1245..1270\nx=405, y=673..682\ny=1309, x=300..307\ny=1127, x=330..356\ny=1449, x=451..472\nx=439, y=375..377\ny=1656, x=512..525\ny=1239, x=325..327\ny=357, x=505..525\ny=745, x=376..418\ny=1270, x=352..360\ny=467, x=449..530\ny=1271, x=413..436\nx=402, y=612..614\nx=388, y=1712..1718\nx=417, y=780..791\nx=437, y=307..323\ny=1848, x=461..465\nx=445, y=1059..1072\nx=527, y=652..680\ny=1171, x=407..425\nx=521, y=182..194\nx=459, y=433..444\nx=422, y=743..763\nx=441, y=1417..1430\ny=1205, x=367..387\nx=446, y=240..250\ny=1480, x=406..430\ny=343, x=443..453\nx=451, y=1457..1468\nx=324, y=240..248\nx=376, y=1812..1825\nx=433, y=1150..1156\nx=386, y=755..769\nx=361, y=79..84\nx=351, y=310..336\nx=436, y=459..462\nx=294, y=892..896\nx=396, y=1813..1825\nx=362, y=706..722\nx=503, y=159..172\nx=309, y=574..595\ny=617, x=434..438\ny=1318, x=510..512\ny=1741, x=519..521\nx=412, y=537..555\nx=442, y=197..208\ny=1515, x=294..303\nx=315, y=1804..1815\ny=311, x=512..529\nx=479, y=1035..1041\nx=437, y=375..377\nx=457, y=1647..1649\nx=429, y=101..108\ny=222, x=340..344\ny=202, x=311..316\ny=835, x=381..398\nx=402, y=1337..1350\nx=332, y=641..659\nx=380, y=252..274\nx=409, y=1453..1467\nx=521, y=1778..1796\nx=368, y=1420..1424\nx=425, y=1848..1860\ny=831, x=390..392\nx=519, y=734..739\ny=176, x=392..397\nx=392, y=1620..1648\nx=493, y=31..44\nx=385, y=781..786\ny=1825, x=376..396\ny=379, x=374..376\nx=373, y=840..853\nx=478, y=1670..1678\nx=527, y=562..571\ny=500, x=306..318\nx=366, y=293..296\ny=1582, x=433..435\ny=1750, x=369..376\nx=470, y=1355..1375\nx=508, y=1832..1846\nx=479, y=1572..1586\nx=300, y=1587..1602\nx=350, y=996..999\ny=42, x=465..468\nx=465, y=33..42\nx=503, y=205..217\nx=495, y=870..873\nx=459, y=167..169\ny=312, x=481..483\ny=1744, x=508..528\nx=510, y=76..89\nx=475, y=1142..1158\ny=526, x=441..443\nx=494, y=1154..1167\ny=345, x=403..427\nx=443, y=1645..1656\ny=1664, x=305..315\nx=418, y=735..745\nx=346, y=780..790\nx=347, y=503..520\ny=625, x=356..374\ny=791, x=414..417\ny=880, x=414..420\nx=376, y=736..745\ny=1767, x=315..331\ny=1005, x=370..375\nx=301, y=1232..1237\nx=421, y=830..833\nx=394, y=1245..1263\nx=325, y=524..535\nx=355, y=1514..1535\nx=397, y=322..332\ny=317, x=317..322\nx=458, y=1533..1543\ny=1720, x=462..507\ny=1618, x=440..443\nx=509, y=1509..1529\ny=1239, x=371..374\nx=486, y=664..680\ny=1114, x=401..403\nx=293, y=1231..1237\nx=444, y=197..208\nx=364, y=1293..1311\ny=1706, x=422..439\nx=342, y=483..491\nx=331, y=1754..1767\nx=330, y=1101..1127\nx=424, y=496..503\ny=1483, x=326..340\nx=398, y=536..555\ny=673, x=296..298\nx=361, y=1689..1699\nx=393, y=417..435\nx=436, y=221..231\nx=332, y=1502..1505\nx=384, y=373..399\nx=466, y=1145..1147\nx=501, y=141..151\nx=433, y=1080..1092\ny=1340, x=344..352\nx=438, y=1365..1367\nx=338, y=1494..1507\ny=1580, x=486..490\nx=412, y=584..596\ny=332, x=373..397\ny=1350, x=380..402\nx=388, y=1464..1487\nx=455, y=180..191\ny=1367, x=438..440\nx=500, y=1812..1821\nx=431, y=6..18\ny=1414, x=480..508\ny=876, x=486..503\nx=465, y=68..79\nx=340, y=1460..1483\nx=317, y=317..319\nx=430, y=1766..1775\nx=417, y=1417..1419\nx=294, y=522..550\nx=316, y=903..915\ny=701, x=365..391\nx=396, y=1712..1715\nx=313, y=263..268\nx=498, y=1014..1031\nx=489, y=547..572\ny=316, x=473..498\nx=404, y=1090..1104\ny=1109, x=425..427\nx=498, y=1667..1672\ny=680, x=464..477\ny=1092, x=433..457\nx=506, y=320..332\ny=943, x=335..363\nx=491, y=833..846\ny=185, x=414..432\nx=396, y=612..614\ny=248, x=324..336\nx=509, y=123..136\nx=419, y=896..902\nx=376, y=1747..1750\nx=328, y=1526..1534\nx=373, y=658..674\nx=388, y=1255..1260\ny=790, x=346..367\nx=339, y=968..971\nx=418, y=1451..1460\nx=381, y=1621..1648\nx=505, y=345..357\nx=299, y=1171..1181\ny=876, x=370..389\nx=453, y=416..419\nx=440, y=1609..1618\nx=452, y=884..902\nx=405, y=1537..1557\ny=1846, x=492..508\nx=313, y=335..343\nx=496, y=489..494\nx=371, y=304..316\nx=432, y=163..185\nx=477, y=432..444\nx=341, y=1404..1406\ny=831, x=365..367\nx=436, y=1268..1271\nx=329, y=525..535\nx=369, y=156..159\nx=391, y=1147..1157\nx=441, y=1006..1009\nx=465, y=1251..1255\ny=973, x=503..524\nx=382, y=1273..1293\ny=1843, x=394..405\ny=572, x=468..489\nx=448, y=985..990\ny=1396, x=402..409\ny=416, x=445..453\nx=360, y=311..336\nx=486, y=1663..1675\nx=468, y=1108..1134\nx=308, y=1733..1740\nx=348, y=1693..1696\nx=486, y=901..910\ny=992, x=354..360\nx=349, y=756..760\ny=138, x=444..467\ny=1286, x=299..306\nx=403, y=335..345\nx=296, y=522..550\ny=368, x=316..333\ny=342, x=418..420\nx=374, y=379..381\nx=302, y=1171..1181\ny=1437, x=495..497\nx=431, y=295..306\ny=1605, x=500..513\nx=333, y=1690..1699\ny=1582, x=402..426\nx=370, y=865..876\nx=297, y=485..486\nx=439, y=628..642\ny=172, x=446..466\ny=1318, x=294..319\nx=491, y=157..165\nx=371, y=735..752\nx=394, y=1133..1135\nx=442, y=967..995\ny=1818, x=453..465\nx=503, y=320..332\nx=498, y=760..777\ny=971, x=339..360\nx=332, y=1080..1083\ny=64, x=517..528\nx=530, y=453..467\nx=358, y=1725..1732\nx=306, y=1335..1347\ny=488, x=418..420\ny=488, x=433..449\nx=414, y=70..72\nx=457, y=495..504\ny=1787, x=368..394\nx=483, y=554..559\nx=491, y=1550..1559\nx=441, y=39..50\nx=480, y=627..630\nx=466, y=158..172\ny=812, x=434..437\nx=503, y=965..973\ny=725, x=438..457\ny=1417, x=417..426\nx=313, y=1503..1505\nx=359, y=1794..1798\nx=527, y=539..555\nx=385, y=538..545\ny=907, x=509..515\nx=468, y=33..42\ny=67, x=485..502\ny=271, x=418..499\nx=414, y=1157..1168\nx=479, y=1019..1029\nx=335, y=1035..1047\ny=160, x=303..329\ny=902, x=452..461\ny=1563, x=365..389\nx=396, y=252..274\nx=413, y=1501..1513\nx=467, y=295..298\ny=108, x=429..433\nx=481, y=1107..1134\nx=502, y=1068..1072\nx=370, y=1580..1605\nx=454, y=1142..1158\ny=936, x=386..405\nx=529, y=298..311\ny=1648, x=484..488\ny=1845, x=330..351\ny=473, x=293..308\nx=469, y=1582..1584\nx=314, y=1113..1125\nx=420, y=1451..1460\nx=394, y=539..545\ny=1801, x=343..365\nx=371, y=106..132\nx=342, y=1575..1589\nx=427, y=1064..1069\ny=535, x=325..329\ny=1282, x=448..467\nx=452, y=540..552\ny=630, x=480..490\nx=309, y=1542..1570\nx=303, y=73..95\ny=136, x=495..509\nx=343, y=419..426\ny=482, x=438..441\nx=424, y=511..513\ny=744, x=430..436\nx=354, y=1645..1655\ny=786, x=352..354\nx=407, y=862..883\nx=374, y=12..27\nx=374, y=618..625\ny=430, x=359..374\ny=896, x=294..308\nx=481, y=308..312\nx=501, y=1857..1868\nx=506, y=733..739\ny=1532, x=415..432\ny=191, x=509..515\nx=492, y=1833..1846\ny=1230, x=489..508\nx=337, y=692..694\nx=381, y=825..835\nx=322, y=1649..1667\nx=429, y=607..620\nx=445, y=1331..1344\nx=521, y=662..669\nx=367, y=822..831\nx=375, y=148..162\ny=953, x=370..398\ny=1091, x=377..385\nx=503, y=661..674\ny=27, x=374..385\nx=415, y=1526..1532\ny=1504, x=491..518\nx=462, y=701..728\nx=384, y=105..132\nx=408, y=1280..1305\nx=396, y=692..705\ny=825, x=298..306\nx=473, y=1728..1741\ny=1104, x=395..404\nx=427, y=1454..1467\ny=1655, x=327..354\nx=298, y=171..180\nx=465, y=1644..1656\ny=117, x=293..298\nx=365, y=693..701\nx=414, y=853..880\nx=391, y=694..701\nx=374, y=171..184\nx=454, y=1285..1297\nx=491, y=430..440\ny=957, x=304..416\nx=300, y=459..462\ny=231, x=419..436\ny=1255, x=463..465\nx=355, y=1668..1675\nx=418, y=257..271\ny=550, x=294..296\nx=298, y=94..117\nx=497, y=1778..1796\ny=1112, x=414..433\ny=637, x=470..481\ny=1444, x=484..504\nx=442, y=1309..1323\nx=433, y=475..488\nx=431, y=1490..1492\nx=499, y=1684..1698\nx=320, y=1055..1059\nx=302, y=968..979\nx=351, y=1572..1598\nx=319, y=1302..1318\nx=334, y=1416..1435\nx=294, y=1511..1515\nx=466, y=701..728\nx=345, y=548..570\nx=449, y=1604..1630\ny=1435, x=321..334\ny=1413, x=354..362\nx=296, y=646..673\nx=308, y=966..976\nx=482, y=38..41\nx=338, y=1196..1214\nx=337, y=997..999\ny=1157, x=391..402\nx=427, y=819..839\ny=1804, x=470..488\ny=1630, x=431..449\nx=324, y=883..894\nx=512, y=322..336\nx=480, y=1791..1799\ny=1695, x=485..491\nx=471, y=412..421\nx=343, y=1792..1801\nx=448, y=1647..1649\nx=431, y=1399..1409\nx=509, y=179..191\nx=412, y=1180..1193\ny=1584, x=469..475\nx=401, y=776..789\ny=555, x=517..527\nx=516, y=837..855\nx=427, y=1315..1329\ny=1782, x=463..486\nx=313, y=560..568\ny=1069, x=427..430\nx=308, y=891..896\nx=498, y=1495..1498\nx=341, y=38..46\ny=1860, x=425..431\nx=490, y=1862..1864\nx=468, y=548..572\nx=475, y=498..506\nx=438, y=1042..1044\nx=503, y=181..194\ny=1259, x=450..471\nx=298, y=646..673\nx=327, y=1645..1655\nx=409, y=1108..1117\nx=295, y=260..274\ny=701, x=306..311\nx=308, y=447..473\nx=463, y=1772..1782\nx=295, y=171..180\ny=523, x=441..443\nx=323, y=1821..1822\nx=353, y=821..839\ny=332, x=503..506\ny=855, x=502..516\nx=465, y=1833..1848\nx=299, y=1278..1286\nx=460, y=1333..1347\nx=484, y=1288..1298\nx=338, y=1526..1534\ny=1505, x=313..332\nx=446, y=587..598\nx=525, y=344..357\ny=830, x=419..421\nx=314, y=739..744\nx=412, y=357..367\nx=397, y=159..176\ny=1044, x=438..441\ny=839, x=402..427\ny=979, x=302..319\nx=515, y=179..191\nx=392, y=829..831\ny=1492, x=431..447\ny=1798, x=357..359\nx=508, y=692..695\ny=669, x=325..347\ny=554, x=481..483\nx=507, y=1706..1720\ny=1237, x=293..301\ny=1065, x=294..310\ny=248, x=383..396\nx=461, y=1524..1529\nx=477, y=1491..1502\nx=485, y=54..67\nx=431, y=771..794\ny=1537, x=465..468\ny=611, x=364..367\ny=1618, x=311..313\nx=493, y=1403..1405\nx=456, y=141..151\ny=1444, x=457..464\nx=508, y=489..494\nx=380, y=1148..1156\nx=433, y=1558..1582\ny=46, x=341..361\ny=1311, x=350..364\nx=505, y=1020..1024\ny=1821, x=477..500\ny=156, x=402..413\ny=503, x=400..424\nx=409, y=1753..1771\ny=1484, x=399..481\nx=324, y=964..985\nx=451, y=125..129\nx=447, y=1034..1047\nx=418, y=475..488\nx=463, y=1251..1255\nx=468, y=1145..1147\nx=470, y=1305..1307\nx=423, y=67..76\ny=998, x=415..419\nx=524, y=966..973\nx=427, y=1436..1445\nx=322, y=500..511\nx=471, y=968..976\nx=437, y=1523..1529\nx=456, y=167..169\ny=440, x=491..506\nx=512, y=1315..1318\nx=498, y=1337..1351\nx=496, y=203..214\ny=1662, x=305..315\ny=113, x=503..518\ny=1151, x=406..423\nx=526, y=1696..1709\nx=306, y=499..500\ny=343, x=310..313\ny=915, x=316..397\ny=1298, x=484..500\nx=480, y=1398..1414\nx=466, y=497..507\nx=507, y=1002..1009\ny=1214, x=424..443\nx=396, y=241..248\nx=417, y=70..72\ny=1347, x=440..460\ny=1827, x=399..423\nx=334, y=432..458\nx=482, y=1791..1799\ny=1237, x=433..456\ny=1731, x=460..463\nx=486, y=1613..1625\nx=491, y=1482..1504\ny=1816, x=486..491\nx=434, y=605..617\ny=1605, x=370..377\nx=402, y=819..839\ny=1345, x=298..300\ny=1619, x=402..409\nx=441, y=1122..1124\ny=1473, x=315..322\ny=1329, x=418..427\ny=1134, x=468..481\nx=422, y=103..117\nx=438, y=872..896\ny=1696, x=348..352\nx=443, y=1204..1214\ny=168, x=424..426\nx=420, y=628..642\ny=1047, x=433..447\nx=515, y=1451..1476\nx=492, y=1616..1628\nx=338, y=56..62\nx=376, y=379..381\nx=327, y=686..700\nx=429, y=585..596\ny=894, x=324..381\ny=211, x=436..453\nx=390, y=1815..1819\nx=511, y=692..695\nx=417, y=1138..1148\ny=486, x=297..321\ny=700, x=327..346\nx=433, y=541..552\ny=990, x=448..451\ny=1698, x=472..499\nx=448, y=1400..1409\nx=512, y=280..289\nx=356, y=1102..1127\nx=436, y=839..860\nx=294, y=1060..1065\nx=323, y=56..62\ny=921, x=447..453\nx=528, y=1731..1744\nx=414, y=779..791\nx=408, y=609..633\ny=506, x=475..496\nx=411, y=237..240\nx=521, y=1510..1529\ny=1174, x=468..485\nx=464, y=1441..1444\nx=333, y=564..566\nx=475, y=1491..1502\nx=444, y=1249..1259\ny=1534, x=328..338\nx=441, y=1397..1405\nx=334, y=1800..1818\ny=860, x=436..447\nx=406, y=696..699\nx=377, y=1793..1804\ny=570, x=345..369\nx=509, y=1614..1622\ny=830, x=478..500\ny=462, x=413..436\nx=347, y=1055..1060\nx=369, y=1403..1406\nx=302, y=459..462\nx=379, y=1272..1293\nx=377, y=1064..1091\nx=508, y=1399..1414\nx=334, y=1234..1245\nx=476, y=1638..1651\nx=492, y=1304..1329\nx=389, y=1545..1563\ny=706, x=499..520\nx=438, y=588..598\ny=76, x=406..423\nx=490, y=411..421\nx=448, y=1727..1741\ny=1009, x=441..452\nx=507, y=1577..1585\ny=422, x=440..466\nx=430, y=1476..1480\ny=1625, x=483..486\nx=481, y=1474..1484\nx=499, y=687..706\nx=508, y=1222..1230\nx=357, y=1794..1798\nx=464, y=601..624\nx=304, y=781..794\nx=426, y=1417..1419\nx=335, y=799..813\ny=1263, x=304..332\nx=293, y=1013..1026\nx=444, y=538..546\nx=474, y=901..910\nx=306, y=73..95\ny=694, x=337..339\nx=417, y=358..367\ny=911, x=496..522\nx=450, y=1307..1316\nx=328, y=1801..1818\nx=483, y=1613..1625\nx=302, y=1612..1619\nx=327, y=1239..1242\ny=132, x=371..384\nx=371, y=53..55\ny=1307, x=470..472\nx=293, y=261..274\nx=516, y=1695..1709\nx=467, y=69..79\nx=459, y=1268..1279\nx=487, y=1862..1864\nx=375, y=761..764\ny=214, x=470..480\ny=1539, x=465..468\nx=434, y=437..448\nx=420, y=475..488\nx=514, y=227..241\nx=520, y=408..418\nx=471, y=1248..1259\nx=345, y=126..143\ny=1003, x=408..428\ny=772, x=396..404\ny=1071, x=452..460\nx=297, y=1586..1602\nx=321, y=218..246\ny=1306, x=300..307\nx=507, y=389..400\nx=432, y=1525..1532\nx=332, y=1257..1263\nx=392, y=781..786\nx=354, y=1475..1479\nx=459, y=495..504\ny=1024, x=503..505\nx=486, y=38..41\ny=206, x=338..366\nx=439, y=1014..1026\nx=465, y=1799..1818\nx=348, y=78..84\nx=419, y=418..435\ny=1323, x=442..458\nx=453, y=1798..1818\nx=364, y=372..399\nx=446, y=608..620\ny=418, x=499..520\nx=525, y=1652..1656\ny=1818, x=486..491\ny=1388, x=328..335\ny=1502, x=475..477\nx=338, y=198..206\ny=679, x=412..416\nx=415, y=514..527\nx=342, y=91..100\ny=1321, x=379..395\ny=323, x=437..448\nx=453, y=199..211\nx=496, y=157..165\ny=18, x=415..431\nx=481, y=633..637\nx=354, y=782..786\nx=449, y=454..467\ny=1387, x=433..437\ny=55, x=371..387\nx=309, y=1209..1232\ny=1188, x=293..308\nx=446, y=872..896\nx=430, y=733..744\ny=568, x=311..313\nx=513, y=9..20\nx=383, y=1172..1174\ny=1351, x=496..498\ny=1804, x=377..401\ny=175, x=313..319\ny=378, x=496..513\ny=896, x=509..515\ny=470, x=331..344\ny=1083, x=312..332\ny=896, x=438..446\nx=387, y=1195..1205\nx=471, y=31..44\nx=354, y=981..992\nx=308, y=385..388\nx=444, y=116..138\nx=408, y=990..1003\ny=1468, x=431..451\ny=399, x=364..384\nx=420, y=370..382\nx=447, y=916..921\nx=424, y=1203..1214\nx=431, y=1604..1630\nx=303, y=134..160\ny=1073, x=363..374\nx=380, y=634..646\ny=1598, x=325..351\nx=393, y=1414..1437\nx=300, y=1679..1700\nx=420, y=340..342\ny=1864, x=487..490\ny=1167, x=492..494\nx=482, y=811..814\nx=443, y=1454..1465\nx=495, y=1427..1437\nx=375, y=840..853\nx=490, y=626..630\ny=418, x=377..386\nx=362, y=842..856\nx=431, y=672..674\nx=502, y=55..67\nx=497, y=1403..1405\ny=1394, x=422..443\ny=1648, x=381..392\nx=364, y=593..611\ny=1675, x=355..372\ny=605, x=345..359\ny=1430, x=441..457\nx=324, y=732..760\nx=361, y=755..769\ny=284, x=480..494\nx=311, y=560..568\nx=380, y=1338..1350\nx=441, y=472..482\nx=370, y=1003..1005\nx=403, y=1595..1609\ny=89, x=493..510\ny=194, x=503..521\ny=761, x=371..375\nx=486, y=864..876\ny=336, x=496..512\nx=462, y=296..298\ny=1672, x=498..500\nx=306, y=1278..1286\nx=434, y=295..306\nx=458, y=1310..1323\nx=348, y=420..426\nx=361, y=734..752\ny=674, x=493..503\ny=1003, x=518..525\nx=322, y=1453..1473\nx=442, y=72..80\ny=431, x=399..401\ny=1047, x=312..335\nx=330, y=1837..1845\ny=1391, x=433..437\nx=371, y=1213..1239\nx=426, y=1562..1582\ny=999, x=337..350\ny=15, x=421..424\nx=502, y=562..571\nx=430, y=1064..1069\nx=483, y=1747..1757\ny=1259, x=424..444\ny=1059, x=314..320\ny=417, x=480..482\ny=1757, x=483..485\nx=509, y=896..907\nx=395, y=356..366\ny=983, x=385..405\nx=461, y=848..862\ny=419, x=445..453\nx=337, y=483..491\ny=1166, x=340..343\nx=399, y=424..431\nx=363, y=921..943\nx=427, y=1100..1109\nx=308, y=1112..1125\nx=485, y=206..217\nx=483, y=1494..1505\ny=1300, x=444..461\nx=402, y=1561..1582\ny=214, x=493..496\nx=398, y=950..953\nx=415, y=998..1000\nx=319, y=969..979\nx=416, y=670..679\ny=1437, x=391..393\nx=422, y=1682..1706\nx=374, y=404..430\nx=362, y=170..184\ny=191, x=438..455\ny=1714, x=314..319\nx=394, y=1771..1787\nx=503, y=864..876\nx=402, y=1148..1157\nx=500, y=1552..1565\ny=448, x=431..434\ny=552, x=433..452\nx=469, y=804..828\ny=1072, x=484..502\nx=472, y=1020..1029\nx=471, y=761..777\nx=502, y=1576..1585\nx=480, y=969..976\nx=390, y=1276..1296\nx=407, y=1159..1171\ny=598, x=438..446\ny=1427, x=495..497\ny=377, x=437..439\ny=680, x=512..527\ny=1344, x=445..451\ny=357, x=359..370\ny=1145, x=466..468\nx=309, y=355..382\ny=1700, x=413..418\ny=1700, x=300..315\nx=465, y=1537..1539\ny=1675, x=486..506\ny=546, x=438..444\nx=438, y=538..546\ny=1815, x=386..390\nx=416, y=897..902\nx=492, y=1455..1468\ny=1156, x=433..450\nx=433, y=101..108\nx=393, y=929..933\nx=303, y=1511..1515\nx=496, y=1338..1351\nx=448, y=1271..1282\ny=366, x=376..395\nx=316, y=348..368\nx=431, y=1456..1468\nx=368, y=1771..1787\ny=1771, x=401..409\ny=1853, x=363..388\nx=369, y=485..487\ny=1628, x=474..492\nx=318, y=498..500\nx=436, y=200..211\nx=521, y=1737..1741\ny=467, x=387..406\ny=1693, x=372..398\ny=1245, x=319..334\nx=499, y=257..271\nx=346, y=687..700\nx=336, y=240..248\nx=457, y=1441..1444\nx=478, y=1858..1868\nx=344, y=1121..1124\nx=435, y=1122..1124\nx=443, y=1435..1445\ny=739, x=506..519\nx=314, y=1586..1592\nx=468, y=1537..1539\nx=325, y=668..669\nx=404, y=1713..1715\nx=379, y=1302..1321\ny=298, x=462..467\ny=1043, x=356..383\nx=414, y=1103..1112\nx=468, y=1172..1174\ny=1242, x=325..327\nx=315, y=1679..1700\ny=902, x=416..419\ny=1732, x=358..381\nx=415, y=1627..1631\nx=307, y=1306..1309\nx=461, y=885..902\nx=477, y=676..680\nx=376, y=355..366\nx=517, y=539..555\nx=463, y=1731..1735\nx=496, y=1573..1586\ny=976, x=308..313\ny=316, x=371..377\nx=425, y=1160..1171\nx=370, y=1244..1263\nx=395, y=1301..1321\nx=508, y=1732..1744\ny=789, x=380..401\ny=258, x=357..367\nx=470, y=207..214\nx=308, y=733..760\ny=695, x=508..511\ny=1117, x=385..409\nx=452, y=1006..1009\nx=518, y=1614..1622\nx=497, y=1427..1437\nx=438, y=472..482\nx=506, y=1662..1675\nx=382, y=1712..1718\nx=500, y=1288..1298\nx=362, y=1411..1413\nx=383, y=1035..1043\nx=473, y=1750..1762\nx=412, y=514..527\nx=386, y=417..418\nx=436, y=732..744\ny=1147, x=466..468\nx=315, y=1662..1664\ny=1232, x=309..312\nx=491, y=1816..1818\nx=512, y=652..680\ny=1868, x=478..501\nx=381, y=1726..1732\nx=324, y=1164..1171\ny=250, x=427..446\nx=418, y=1573..1577\ny=100, x=342..354\ny=699, x=406..409\ny=590, x=500..527\nx=403, y=1112..1114\nx=306, y=192..206\nx=421, y=512..513\nx=485, y=580..604\ny=692, x=508..511\nx=471, y=1512..1526\nx=518, y=100..113\ny=1200, x=511..527\nx=425, y=806..816\ny=400, x=482..507\ny=208, x=442..444\ny=960, x=424..440\nx=300, y=1340..1345\nx=433, y=1035..1047\nx=402, y=128..156\nx=464, y=1355..1375\nx=451, y=1285..1297\nx=395, y=1091..1104\nx=373, y=321..332\ny=307, x=517..519\nx=452, y=1064..1071\nx=321, y=573..595\ny=529, x=435..459\ny=151, x=456..501\ny=1631, x=405..415\nx=389, y=865..876\nx=402, y=1392..1396\ny=240, x=404..411\ny=1570, x=301..309\ny=1405, x=493..497\nx=492, y=1512..1526\ny=199, x=458..471\ny=1860, x=516..524\nx=513, y=1602..1605\ny=1375, x=464..470\nx=421, y=12..15\nx=435, y=969..980\ny=194, x=474..479\nx=508, y=1495..1498\ny=296, x=342..366\nx=314, y=1710..1714\nx=341, y=278..289\nx=377, y=416..418\nx=352, y=782..786\ny=1589, x=342..344\ny=1535, x=355..371\nx=398, y=824..835\nx=330, y=19..40\nx=496, y=893..911\ny=167, x=456..459\nx=500, y=1667..1672\nx=371, y=1514..1535\ny=1531, x=375..379\ny=1406, x=341..369\nx=515, y=896..907\ny=1735, x=460..463\nx=445, y=369..382\nx=457, y=1079..1092\ny=604, x=485..496\nx=344, y=219..222\nx=443, y=1609..1618\nx=312, y=1805..1815\ny=813, x=335..389\nx=422, y=771..794\nx=528, y=61..64\ny=1371, x=432..446\nx=446, y=1568..1571\ny=664, x=407..451\nx=298, y=809..825\ny=1445, x=427..443\ny=1557, x=341..347\nx=454, y=1670..1678\nx=478, y=823..830\nx=494, y=1639..1651\nx=493, y=76..89\nx=367, y=592..611\nx=405, y=975..983\nx=461, y=1834..1848\nx=444, y=561..573\nx=309, y=1764..1778\nx=427, y=241..250\nx=313, y=19..40\ny=427, x=365..368\ny=1171, x=299..302\nx=420, y=854..880\nx=433, y=1387..1391\nx=350, y=1293..1311\nx=480, y=207..214\nx=439, y=1681..1706\ny=1214, x=334..338\nx=516, y=1337..1344\ny=985, x=324..333\nx=370, y=1168..1178\nx=372, y=1667..1675\nx=300, y=356..382\nx=474, y=1615..1628\ny=133, x=501..503\nx=490, y=1597..1607\nx=300, y=1306..1309\nx=443, y=327..343\nx=416, y=968..980\nx=484, y=942..955\ny=40, x=313..330\nx=400, y=495..503\ny=336, x=351..360\ny=1124, x=344..346\ny=444, x=459..477\nx=424, y=939..960\nx=426, y=218..226\nx=390, y=829..831\nx=520, y=1014..1031\nx=312, y=1763..1778\ny=1344, x=516..518\ny=1467, x=409..427\nx=450, y=1227..1234\ny=614, x=396..402\nx=321, y=484..486\ny=180, x=295..298\ny=633, x=390..408\ny=816, x=425..443\nx=440, y=401..422\nx=450, y=1684..1689\ny=79, x=465..467\ny=62, x=323..338\ny=1667, x=299..322\ny=728, x=462..466\ny=1292, x=342..345\nx=317, y=739..744\ny=1263, x=370..394\ny=1607, x=468..490\nx=325, y=1239..1242\ny=1487, x=388..390\nx=511, y=1194..1200\ny=1818, x=328..334\ny=642, x=420..439\nx=410, y=279..289\nx=502, y=837..855\nx=504, y=1419..1444\ny=744, x=314..317\ny=1421, x=447..451\ny=846, x=480..491\nx=437, y=1387..1391\nx=417, y=691..705\nx=409, y=1614..1619\nx=399, y=1818..1827\ny=1156, x=374..380\nx=418, y=340..342\nx=375, y=1518..1531\ny=721, x=396..398\ny=50, x=430..441\nx=332, y=1334..1347\ny=289, x=335..341\nx=516, y=1841..1860\ny=856, x=362..381\nx=424, y=672..682\nx=518, y=992..1003\nx=519, y=1737..1741\ny=1815, x=312..315\nx=347, y=1537..1557\nx=396, y=756..772\nx=315, y=1754..1767\nx=401, y=424..431\nx=356, y=617..625\nx=490, y=1570..1580\nx=497, y=1097..1124\nx=435, y=1181..1193\nx=335, y=1055..1060\nx=374, y=1060..1073\ny=1799, x=480..482\ny=839, x=346..353\ny=1565, x=481..500\nx=496, y=368..378\nx=443, y=805..816\ny=72, x=414..417\nx=361, y=38..46\ny=459, x=300..302\nx=406, y=1476..1480\nx=486, y=1816..1818\nx=369, y=1634..1654\ny=1316, x=448..450\nx=527, y=1193..1200\nx=312, y=1209..1232\nx=518, y=1482..1504\ny=1507, x=338..352\nx=482, y=389..400\nx=423, y=1279..1305\nx=371, y=761..764\ny=1347, x=306..332\nx=457, y=125..129\nx=460, y=1196..1201\ny=1178, x=370..395\nx=460, y=1731..1735\nx=325, y=1572..1598\ny=1125, x=308..314\nx=424, y=12..15\ny=1577, x=416..418\nx=493, y=203..214\nx=329, y=135..160\nx=487, y=1454..1468\nx=299, y=1650..1667\ny=1526, x=471..492\nx=334, y=707..722\ny=1424, x=447..451\nx=397, y=903..915\nx=374, y=1214..1239\nx=486, y=1570..1580\ny=1543, x=458..481\ny=117, x=407..422\ny=95, x=303..306\ny=1168, x=414..419\nx=333, y=347..368\nx=448, y=1307..1316\ny=1678, x=454..478\nx=385, y=1065..1091\nx=488, y=789..796\nx=335, y=922..943\nx=419, y=220..231\nx=484, y=1069..1072\nx=312, y=295..322\nx=311, y=200..202\ny=977, x=426..429\ny=487, x=361..369\nx=319, y=170..175\nx=495, y=1002..1009\nx=470, y=633..637\ny=1656, x=443..465\ny=555, x=420..422\nx=454, y=847..862\ny=1651, x=476..494\nx=447, y=1119..1132\ny=1444, x=357..364\nx=398, y=1685..1693\ny=1609, x=440..443\nx=306, y=1691..1695\nx=363, y=1841..1853\ny=1762, x=473..493\nx=310, y=336..343\nx=437, y=1454..1465\nx=316, y=200..202\nx=377, y=1580..1605\ny=1609, x=403..427\nx=333, y=500..511\nx=440, y=1334..1347\ny=462, x=300..302\nx=392, y=1275..1296\nx=419, y=1157..1168\ny=1775, x=430..433\ny=1654, x=357..369\nx=405, y=1838..1843\nx=488, y=1636..1648\nx=334, y=296..322\nx=357, y=233..258\nx=402, y=1614..1619\ny=929, x=391..393\nx=484, y=1636..1648\nx=323, y=385..388\nx=438, y=605..617\ny=1193, x=412..435")
;(def input "x=495, y=2..7\ny=7, x=495..501\nx=501, y=3..7\nx=498, y=2..4\nx=506, y=1..2\nx=498, y=10..13\nx=504, y=10..13\ny=13, x=498..504")


(defn parse-line [s]
  (let [repa #"(x|y)=(\d+), (?:x|y)=(\d+)\.\.(\d+)"
        [coo a b1 b2] (rest (re-matches repa s))
        [a b1 b2] (map edn/read-string [a b1 b2])
        k1 (case coo "x" :xs :ys)
        k2 (case coo "x" :ys :xs)
        m {k1 (range a (inc a))
           k2 (range b1 (inc b2))}]
    (for [x (:xs m)
          y (:ys m)]
      [x y])))

(def sconj (fnil conj #{}))
(def sinto (fnil into #{}))

(def CLAY (->> input str/split-lines (mapcat parse-line) (set)))
(def CLAY2 (reduce
             (fn rf [m [x y]]
               (update m y sconj x))
             {} CLAY))
CLAY2

(count CLAY)
(def MINX (->> CLAY (map first) (reduce min)))
(def MAXX (->> CLAY (map first) (reduce max)))
(def MINY (->> CLAY (map second) (reduce min)))
(def MAXY (->> CLAY (map second) (reduce max)))
[[MINX MINY] [MAXX MAXY]]


(defn render [water-xys]
  (let [points (for [x (range (- MINX 1) (+ MAXX 2)) ;; extra room for overflows
                     y (range 0 (inc MAXY))]
                 [x y])
        draw  (fn [xy]
                (cond
                  (= [500 0] xy)           "+"
                  (contains? CLAY xy)      "#"
                  (contains? water-xys xy) "~"
                  :else                    "."))]
    (->> points
      (sort-by (juxt second first))
      (map draw)
      (partition-all (+ 3 (- MAXX MINX)))
      (map str/join)
      (str/join "\n")
      (spit "/tmp/water.txt"))))


(defn side-expand [walls y xs head-el-fn inc-fn]
  (let [x (head-el-fn xs)]
    (if-not (-> y inc walls (contains? x))
      [xs [x]] ;; down
      (let [nx (inc-fn x)]
        (if (-> y walls (contains? nx))
          [xs nil] ;; stop
          (recur walls y (conj xs nx) head-el-fn inc-fn)))))) ;; sideways

(defn expand-left  [walls y xs] (side-expand walls y (seq xs) first dec))
(defn expand-right [walls y xs] (side-expand walls y (vec xs) peek  inc))


(defn expand [walls y xs]
  (let [[l-side l-downstream] (expand-left walls y xs)
        [r-side r-downstream] (expand-right walls y xs)
        puddle      (concat l-side r-side)
        downstreams (->> [l-downstream r-downstream]
                      (remove nil?)
                      (into #{}))]
    [puddle downstreams]))


(defn level-state [expanded]
  ;(u/print-locals-map)
  (reduce
    (fn rf1 [m [puddle downstreams]]
      (let [m (update m ::puddles conj puddle)]
        (if (empty? downstreams)
          (-> m (update ::overflows into puddle))
          (-> m (update ::puddles conj puddle)
                (update ::downstreams into downstreams)))))
    {::overflows   #{}
     ::puddles     #{}
     ::downstreams #{}}
    expanded))



(defn water-xys [running stale]
  (as-> #{} $
    (reduce-kv
      (fn rf [xys y xss]
        (let [xs (reduce into #{} xss)]
          (into xys (map vector xs (repeat y)))))
      $ running)
    (reduce-kv
      (fn rf [xys y xs]
        (into xys (map vector xs (repeat y))))
      $ stale)))


(defn f1 []
  (time
    (let [tapx 500
          tapy 0]
      (loop [y       tapy
             running {y #{[tapx]}} ; {y [xs]}
             walls   CLAY2
             stale   {}] ;; {y xs}
        (if (< MAXY y)
          (let [banned (into #{y} (range tapy MINY))
                water  (water-xys
                         (apply dissoc running banned)
                         (apply dissoc stale banned))]
            ;(render water)
            (count water))
          (let [{::keys [overflows
                         puddles
                         downstreams]} (->> (get running y)
                                         (map (partial expand walls y))
                                         (level-state))
                running (-> running
                          (assoc y puddles)
                          (assoc (inc y) downstreams))]
            (if (empty? overflows)
              (recur (inc y) running walls stale)
              ;; recalculate 1 level above:
              (recur (dec y) running
                (update walls y sinto overflows)
                (update stale y sinto overflows)))))))))

(let [r1 (f1)]
  (assert (< r1 34780))
  (assert (< r1 34784))
  (assert (> r1 28956))
  (assert (> r1 33358))
  (assert (> r1 33363))
  (assert (= r1 34775)))