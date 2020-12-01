(ns adventofcode.y2020.day01
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]))

(def input "1780\n1693\n1830\n1756\n1858\n1868\n1968\n1809\n1996\n1962\n1800\n1974\n1805\n1795\n170\n1684\n1659\n1713\n1848\n1749\n1717\n1734\n956\n1782\n1834\n1785\n1786\n1994\n1652\n1669\n1812\n1954\n1984\n1665\n1987\n1562\n2004\n2010\n1551\n961\n1854\n2005\n1883\n1965\n475\n1776\n1791\n262\n1912\n1227\n1486\n1989\n1857\n825\n1683\n1991\n1875\n1982\n1654\n1767\n1673\n1973\n1886\n1731\n1745\n1770\n1995\n1721\n1662\n1679\n1783\n1999\n1889\n1746\n1902\n2003\n1698\n1794\n1798\n1951\n1953\n2007\n1899\n1658\n1705\n62\n1819\n1708\n1666\n2006\n1763\n1732\n1613\n1841\n1747\n1489\n1845\n2008\n1885\n2002\n1735\n1656\n1771\n1950\n1704\n1737\n1748\n1759\n1802\n2000\n1955\n1738\n1761\n1765\n1853\n1900\n1709\n1979\n1911\n1775\n1813\n1949\n1966\n1774\n1977\n1757\n1992\n2009\n1956\n1840\n1988\n1985\n1993\n1718\n1976\n1078\n1997\n1897\n1792\n1790\n1801\n1871\n1727\n1700\n1485\n942\n1686\n1859\n1676\n802\n1952\n1998\n1961\n1844\n1808\n1703\n1980\n1766\n1963\n1849\n1670\n1716\n1957\n1660\n1816\n1762\n1829\n526\n359\n2001\n1874\n1778\n1873\n1511\n1810\n1699\n1970\n1690\n1978\n1892\n1691\n1781\n1777\n1975\n1967\n1694\n1969\n1959\n1910\n1826\n1672\n1655\n1839\n1986\n1872\n1983\n1981\n1972\n1772\n1760")
(def xs (->> input (str/split-lines) (map read-string) (sort <)))



(defn match [xs]
  (when (= 2020 (apply + xs))
    (apply * xs)))

(some match (for [x xs y xs] [x y]))
(time (some match (for [x xs y xs z xs] [x y z])))


(time
  (let [xs   (->> input (str/split-lines) (map read-string) (sort <))
        vecs (for [x xs
                   y xs
                   :while (< (+ x y) 2020)
                   z xs
                   :let   [s (+ y x z)]
                   :while (<= s 2020)
                   :when  (= s 2020)]
               [x y z])]
    (some->> vecs first (apply *))))



(time
  (->> (combo/combinations xs 3)
    (filter #(= (apply + %) 2020))
    (apply reduce *)))