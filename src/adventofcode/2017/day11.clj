(ns adventofcode.2017.day11
  (:require [clojure.string :as str]))

(def input "nw,n,ne,sw,s,s,se,se,ne,s,se,se,ne,ne,s,se,s,ne,sw,ne,ne,s,se,nw,n,ne,nw,n,n,ne,n,n,ne,n,ne,n,n,n,n,nw,n,n,nw,n,nw,n,s,sw,sw,sw,n,nw,n,nw,nw,n,nw,nw,nw,nw,nw,nw,se,nw,nw,nw,se,sw,sw,nw,nw,sw,sw,nw,nw,nw,nw,nw,s,sw,nw,s,sw,sw,nw,sw,sw,nw,sw,sw,nw,sw,sw,nw,sw,sw,sw,sw,sw,sw,sw,sw,s,se,sw,sw,nw,sw,sw,se,sw,sw,s,sw,sw,sw,s,n,s,sw,sw,s,sw,s,sw,ne,sw,s,s,s,n,sw,s,s,sw,s,s,s,nw,s,s,n,nw,ne,n,s,se,n,s,s,s,s,ne,s,s,s,sw,s,ne,n,s,se,s,se,s,s,s,s,ne,s,nw,se,se,ne,nw,se,s,nw,s,s,s,nw,s,sw,s,s,se,nw,se,sw,se,sw,s,s,sw,se,se,s,n,se,se,se,se,se,se,s,se,s,se,se,nw,s,n,se,se,se,se,n,nw,se,se,se,se,se,se,se,se,se,se,se,ne,se,se,sw,se,s,se,ne,se,n,se,ne,se,se,se,se,se,n,se,se,ne,ne,ne,ne,se,se,ne,se,se,s,ne,se,se,n,se,sw,s,se,se,ne,ne,ne,s,s,ne,se,nw,nw,s,se,ne,ne,ne,se,sw,ne,se,ne,ne,ne,se,n,s,n,se,ne,ne,se,se,nw,ne,se,ne,nw,ne,se,ne,ne,n,ne,ne,ne,ne,ne,se,ne,nw,se,ne,ne,ne,s,ne,ne,ne,ne,ne,sw,ne,ne,ne,se,ne,ne,ne,ne,nw,ne,ne,s,sw,ne,ne,ne,ne,ne,n,s,sw,n,ne,s,ne,nw,sw,sw,s,ne,ne,ne,ne,sw,n,ne,ne,nw,n,ne,n,n,s,n,ne,nw,ne,n,ne,ne,ne,ne,sw,n,ne,sw,sw,ne,nw,ne,ne,n,ne,ne,ne,n,n,s,ne,ne,ne,n,ne,ne,n,n,n,n,nw,n,n,ne,n,ne,ne,n,ne,ne,ne,ne,ne,n,n,n,ne,n,n,n,n,n,n,n,n,n,n,ne,n,n,nw,ne,n,s,nw,se,ne,se,n,n,n,sw,n,n,ne,sw,n,n,s,s,n,n,n,ne,nw,nw,n,n,n,s,se,n,n,n,nw,n,nw,n,n,n,n,n,se,n,n,n,s,sw,n,n,n,n,n,n,n,n,n,n,n,se,n,n,n,nw,n,n,se,ne,ne,nw,n,n,n,nw,n,n,n,ne,s,n,nw,ne,n,s,n,n,n,n,sw,n,nw,n,n,n,ne,s,n,s,se,n,n,n,nw,nw,n,nw,sw,n,n,nw,n,n,nw,nw,n,n,sw,nw,nw,nw,n,se,nw,n,nw,nw,nw,n,n,nw,n,n,nw,nw,n,ne,nw,nw,sw,n,n,n,nw,nw,n,n,n,n,n,sw,nw,nw,n,nw,n,nw,nw,nw,n,nw,n,n,n,n,nw,nw,nw,s,s,n,nw,sw,nw,n,nw,ne,n,nw,ne,ne,sw,n,se,nw,sw,s,nw,nw,nw,nw,nw,ne,nw,nw,nw,ne,nw,se,n,n,nw,se,nw,nw,nw,n,nw,se,nw,nw,nw,nw,nw,nw,nw,nw,nw,ne,nw,nw,nw,se,nw,nw,nw,nw,s,s,s,nw,nw,nw,nw,s,n,nw,nw,se,nw,nw,nw,nw,nw,sw,nw,nw,nw,nw,nw,ne,sw,sw,sw,nw,nw,sw,nw,nw,ne,sw,ne,nw,nw,nw,nw,nw,s,nw,nw,nw,nw,nw,se,sw,nw,n,sw,ne,nw,nw,nw,nw,nw,sw,nw,se,sw,sw,sw,nw,s,nw,nw,s,ne,nw,nw,nw,nw,nw,sw,nw,s,sw,nw,nw,se,sw,sw,nw,nw,sw,sw,n,sw,nw,ne,se,nw,nw,ne,nw,nw,sw,nw,nw,se,sw,nw,sw,nw,nw,ne,nw,s,nw,nw,nw,nw,nw,nw,nw,nw,nw,sw,n,sw,sw,se,sw,nw,nw,nw,nw,nw,sw,nw,nw,nw,sw,nw,nw,se,sw,nw,nw,n,nw,sw,nw,nw,nw,nw,n,sw,sw,sw,se,nw,nw,sw,sw,sw,sw,nw,nw,sw,sw,sw,nw,sw,sw,se,sw,sw,ne,sw,nw,s,s,se,ne,sw,sw,sw,sw,nw,nw,nw,sw,nw,nw,nw,s,nw,nw,nw,sw,s,s,nw,sw,sw,ne,n,s,nw,nw,sw,nw,nw,nw,sw,sw,ne,sw,sw,nw,n,sw,sw,nw,se,sw,sw,se,ne,sw,sw,sw,nw,sw,sw,sw,sw,nw,nw,nw,nw,n,sw,sw,se,n,nw,nw,sw,sw,sw,sw,nw,se,sw,sw,se,n,s,sw,sw,n,n,sw,sw,n,sw,sw,sw,sw,sw,se,sw,sw,sw,se,nw,sw,s,sw,sw,sw,ne,sw,sw,sw,sw,sw,sw,sw,n,sw,sw,sw,sw,sw,sw,sw,nw,sw,sw,sw,ne,sw,s,sw,sw,sw,sw,sw,sw,sw,s,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,s,n,s,s,sw,n,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,n,sw,s,sw,sw,sw,sw,sw,sw,ne,sw,se,sw,sw,nw,sw,sw,sw,s,sw,sw,sw,sw,sw,s,sw,sw,ne,sw,sw,sw,sw,n,sw,n,sw,n,sw,sw,sw,sw,sw,sw,n,s,se,s,ne,sw,sw,s,s,s,se,s,nw,s,sw,s,s,s,se,s,sw,sw,se,sw,sw,sw,s,se,nw,sw,sw,se,s,s,sw,sw,s,sw,sw,s,sw,sw,ne,n,sw,sw,nw,ne,s,sw,sw,sw,sw,sw,sw,sw,sw,sw,s,n,sw,s,n,s,s,sw,sw,nw,se,n,sw,sw,sw,nw,s,sw,sw,s,sw,sw,sw,sw,sw,sw,sw,sw,s,s,sw,s,n,s,sw,sw,sw,s,s,nw,s,sw,s,s,s,sw,sw,s,se,se,nw,sw,sw,sw,s,s,sw,sw,ne,sw,s,sw,s,s,s,sw,s,s,sw,sw,sw,nw,sw,s,sw,sw,n,sw,sw,sw,s,sw,sw,sw,sw,s,nw,nw,sw,sw,s,s,n,sw,sw,se,s,sw,s,n,s,sw,s,sw,s,sw,sw,sw,sw,sw,n,s,se,s,s,s,s,s,ne,s,sw,n,sw,s,s,sw,s,sw,s,sw,ne,s,s,nw,n,s,s,n,se,sw,sw,s,s,s,s,s,s,s,s,sw,se,sw,s,nw,s,s,s,s,s,se,s,s,sw,sw,ne,s,s,s,sw,ne,s,sw,s,s,s,s,s,s,n,s,s,s,s,sw,s,s,s,sw,sw,s,sw,sw,nw,s,ne,s,s,s,se,sw,se,s,s,s,sw,sw,s,sw,s,s,s,n,s,sw,s,sw,s,s,nw,s,s,n,nw,s,ne,s,sw,s,s,s,s,ne,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,nw,s,s,sw,nw,s,n,s,s,nw,sw,s,se,s,s,s,s,sw,s,ne,s,s,s,s,s,s,s,s,s,sw,se,s,s,s,s,s,s,s,s,n,s,s,s,s,ne,n,ne,s,sw,n,s,s,s,s,s,s,s,s,ne,s,s,nw,se,s,s,s,s,s,s,ne,s,s,s,ne,s,s,s,s,s,se,s,s,s,s,s,s,s,ne,s,s,s,s,s,s,s,se,s,s,s,s,se,s,s,ne,s,n,ne,s,s,s,nw,ne,se,s,s,s,n,sw,s,s,s,s,s,s,s,sw,s,s,se,s,s,s,s,n,s,se,s,s,nw,s,s,sw,s,sw,se,s,s,s,s,s,s,s,s,se,se,se,s,se,s,s,s,s,se,s,s,se,s,se,s,n,s,s,se,s,sw,se,s,s,se,se,s,s,se,s,se,se,nw,n,s,nw,ne,ne,s,s,sw,s,se,nw,ne,s,s,se,s,s,s,s,s,s,s,se,s,s,se,s,se,s,ne,se,se,s,se,se,s,nw,s,ne,sw,s,s,se,s,s,se,s,sw,s,s,se,s,sw,n,s,nw,se,s,n,nw,s,s,se,s,s,nw,s,se,s,ne,s,s,se,s,se,s,nw,se,ne,s,nw,se,s,se,ne,s,s,sw,s,s,s,se,se,se,s,s,s,s,se,nw,s,se,s,s,s,s,n,se,s,n,se,se,se,se,nw,sw,nw,se,se,se,s,se,n,s,se,s,nw,ne,se,s,se,s,se,s,ne,se,nw,se,se,ne,s,nw,sw,n,se,se,se,n,se,se,se,nw,se,sw,s,n,se,s,sw,se,s,s,se,s,se,se,se,nw,se,se,s,nw,se,n,se,se,se,s,nw,se,se,s,ne,se,se,s,s,s,se,se,ne,s,nw,s,se,se,sw,se,se,se,se,s,se,se,se,s,s,se,nw,se,se,se,s,se,s,nw,se,se,se,ne,se,se,se,se,se,se,se,se,se,se,s,ne,se,ne,se,n,sw,s,sw,se,sw,se,se,se,sw,se,se,s,se,se,s,nw,se,nw,se,se,se,se,se,se,n,se,se,se,se,s,se,nw,se,se,se,se,se,nw,nw,se,se,se,se,s,sw,s,s,se,se,s,se,se,nw,se,se,se,s,s,s,se,se,n,se,nw,sw,n,se,se,se,se,se,se,se,se,s,ne,se,ne,se,se,se,se,nw,se,se,s,se,n,se,se,se,se,se,se,s,se,s,s,ne,s,se,sw,ne,se,se,se,sw,se,se,se,se,s,nw,n,se,se,se,se,s,se,se,se,se,n,se,se,se,se,se,nw,ne,se,se,n,se,s,se,se,s,se,n,se,se,ne,se,s,s,ne,s,se,se,se,se,se,se,nw,nw,se,se,se,se,n,se,se,se,se,s,se,s,se,se,se,se,se,n,se,se,se,se,se,se,se,n,n,sw,se,n,se,se,se,se,se,se,se,se,s,sw,se,nw,nw,ne,se,se,se,se,se,se,ne,se,ne,se,se,ne,ne,se,sw,se,se,se,ne,se,se,se,ne,se,nw,se,se,se,sw,se,nw,se,se,se,se,ne,sw,se,se,se,se,se,se,s,ne,sw,se,s,s,se,se,nw,se,ne,se,se,se,se,se,se,s,se,se,se,se,se,se,se,se,sw,ne,sw,se,se,se,se,nw,se,se,se,se,ne,se,se,se,n,se,ne,nw,se,se,se,sw,se,se,se,se,se,ne,se,se,sw,se,se,n,n,se,se,se,se,se,se,se,se,nw,se,s,s,se,se,s,se,ne,se,n,n,se,nw,se,se,se,se,nw,sw,s,ne,nw,se,ne,se,n,se,se,se,ne,se,se,sw,s,se,se,n,ne,se,se,ne,se,se,ne,se,se,se,se,se,se,se,se,se,nw,n,sw,se,se,se,se,ne,se,se,se,se,se,se,se,ne,se,ne,se,se,n,se,se,n,ne,n,se,se,se,se,ne,se,se,se,sw,se,se,ne,se,s,se,ne,se,n,se,se,se,se,sw,se,ne,se,s,se,se,ne,n,se,se,nw,se,se,nw,nw,ne,se,se,sw,se,se,n,ne,n,ne,s,se,se,se,se,ne,s,nw,se,se,se,ne,se,se,sw,ne,s,se,ne,se,se,se,se,sw,se,se,sw,ne,ne,ne,se,se,se,ne,ne,ne,ne,se,se,ne,ne,se,s,nw,se,se,se,se,ne,se,se,se,se,se,n,se,se,ne,se,se,se,se,se,se,s,se,n,se,ne,ne,se,s,ne,ne,s,ne,ne,se,se,se,ne,se,ne,se,se,ne,sw,ne,ne,ne,se,se,ne,ne,se,n,se,nw,se,n,ne,ne,ne,se,se,se,ne,ne,se,s,ne,ne,se,ne,se,nw,ne,ne,ne,ne,n,se,ne,se,ne,se,se,ne,ne,s,ne,ne,ne,ne,se,ne,ne,se,ne,ne,se,sw,s,s,se,se,se,ne,se,ne,ne,ne,ne,ne,se,se,ne,se,se,se,ne,ne,nw,sw,se,se,sw,sw,ne,se,se,se,se,s,nw,sw,ne,se,se,ne,ne,ne,ne,se,se,se,se,se,ne,se,ne,ne,ne,s,ne,ne,n,ne,ne,ne,s,n,se,se,nw,nw,ne,ne,n,ne,ne,ne,se,se,ne,se,se,sw,ne,se,ne,ne,se,se,ne,se,s,ne,ne,ne,n,sw,ne,ne,ne,ne,ne,se,se,s,nw,se,ne,ne,ne,n,ne,ne,n,ne,se,sw,ne,ne,se,sw,ne,ne,s,ne,ne,nw,sw,se,ne,ne,sw,n,ne,ne,ne,sw,ne,s,ne,ne,se,ne,nw,ne,ne,ne,se,n,ne,n,ne,ne,ne,ne,ne,ne,ne,ne,se,se,ne,ne,ne,ne,ne,s,ne,ne,ne,ne,se,ne,ne,ne,ne,ne,ne,ne,ne,ne,n,n,se,ne,ne,ne,ne,ne,sw,s,ne,ne,sw,n,ne,n,nw,se,ne,se,ne,ne,ne,ne,se,ne,ne,se,ne,se,se,ne,ne,s,n,sw,se,sw,se,ne,ne,nw,ne,se,ne,ne,ne,se,ne,ne,se,nw,ne,ne,ne,ne,ne,ne,se,se,ne,se,ne,s,se,nw,se,ne,ne,ne,se,s,ne,ne,ne,se,se,se,se,se,ne,se,nw,se,ne,ne,ne,n,n,n,nw,ne,s,ne,se,ne,ne,nw,s,ne,nw,ne,nw,se,ne,ne,n,ne,ne,ne,sw,ne,se,n,ne,ne,ne,ne,se,s,ne,ne,sw,ne,ne,s,ne,se,ne,ne,ne,ne,ne,ne,ne,se,ne,nw,ne,n,ne,s,ne,ne,ne,nw,ne,n,ne,nw,ne,ne,ne,ne,s,ne,ne,n,ne,ne,ne,ne,sw,ne,ne,ne,se,ne,se,ne,se,nw,ne,ne,ne,sw,n,n,nw,nw,ne,ne,ne,ne,se,ne,ne,ne,ne,ne,s,ne,ne,sw,ne,ne,s,ne,ne,ne,ne,s,ne,ne,ne,ne,ne,ne,ne,ne,ne,s,ne,ne,s,sw,ne,nw,ne,ne,ne,ne,ne,ne,ne,ne,s,ne,sw,se,ne,se,ne,ne,n,ne,s,ne,ne,ne,n,ne,n,n,ne,ne,ne,ne,ne,n,ne,se,ne,se,ne,ne,s,ne,n,ne,ne,ne,ne,se,ne,nw,ne,s,sw,ne,ne,ne,sw,ne,ne,s,ne,ne,s,se,ne,s,n,ne,ne,ne,ne,ne,se,ne,ne,n,ne,ne,nw,nw,ne,ne,ne,ne,nw,ne,n,ne,ne,ne,ne,n,ne,ne,ne,ne,ne,s,n,ne,n,n,ne,nw,nw,ne,n,nw,ne,ne,n,ne,ne,s,s,ne,se,ne,ne,ne,n,ne,ne,ne,ne,ne,n,s,se,ne,ne,s,ne,ne,se,ne,ne,n,n,ne,ne,se,n,ne,s,ne,ne,n,ne,nw,ne,nw,nw,ne,se,ne,ne,ne,ne,se,ne,se,ne,ne,ne,n,ne,nw,n,ne,ne,nw,ne,ne,ne,nw,ne,ne,sw,n,ne,ne,ne,ne,ne,ne,ne,n,ne,sw,n,ne,nw,ne,ne,ne,ne,ne,sw,sw,nw,ne,ne,ne,se,nw,sw,n,ne,n,nw,ne,ne,ne,n,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,sw,ne,ne,ne,ne,s,ne,ne,se,ne,ne,n,ne,n,ne,ne,ne,s,ne,ne,se,ne,ne,ne,ne,ne,ne,se,ne,n,ne,ne,ne,ne,ne,ne,ne,ne,n,ne,s,se,se,n,s,ne,s,s,ne,ne,n,ne,ne,n,ne,ne,ne,ne,ne,ne,ne,sw,ne,ne,n,ne,ne,se,se,nw,n,ne,se,sw,ne,ne,ne,ne,n,sw,se,ne,n,se,n,sw,ne,se,ne,ne,ne,ne,s,ne,ne,n,s,n,ne,ne,ne,s,ne,n,n,ne,ne,s,ne,n,ne,ne,ne,n,n,n,n,ne,ne,s,ne,ne,ne,ne,ne,ne,s,ne,ne,ne,ne,s,n,ne,se,ne,n,n,n,nw,ne,ne,n,n,ne,n,se,ne,n,ne,ne,ne,ne,n,nw,ne,s,ne,n,ne,n,ne,s,n,ne,n,ne,ne,n,n,se,n,ne,nw,ne,ne,se,n,ne,n,ne,ne,ne,ne,n,ne,n,ne,sw,ne,ne,ne,n,ne,ne,ne,s,n,n,ne,ne,ne,se,ne,nw,ne,ne,nw,n,ne,ne,ne,n,ne,ne,se,ne,n,nw,s,n,ne,nw,ne,n,ne,nw,s,ne,s,n,n,se,n,ne,ne,ne,ne,n,s,se,sw,n,n,ne,nw,n,ne,sw,ne,ne,n,ne,n,ne,n,se,sw,n,nw,ne,ne,ne,n,ne,n,n,ne,n,ne,ne,n,n,n,sw,ne,ne,n,ne,nw,n,n,ne,n,n,n,nw,n,se,n,ne,ne,sw,ne,ne,ne,ne,n,s,ne,se,n,ne,n,ne,ne,se,ne,ne,n,ne,n,se,ne,n,ne,ne,n,n,s,n,se,n,ne,n,ne,n,ne,n,ne,ne,se,ne,se,ne,sw,n,n,n,se,ne,ne,se,ne,n,ne,n,nw,ne,n,ne,n,ne,sw,n,sw,s,n,n,ne,ne,ne,ne,ne,n,s,n,n,nw,n,ne,n,n,sw,n,n,ne,n,nw,n,n,se,n,ne,ne,ne,ne,n,ne,ne,ne,n,ne,n,n,n,n,ne,n,n,n,n,sw,n,n,ne,n,ne,n,n,n,n,sw,ne,n,s,ne,n,se,ne,ne,ne,sw,n,n,ne,se,n,sw,se,ne,nw,n,sw,n,n,n,n,n,n,ne,se,ne,n,n,n,n,n,s,n,n,n,ne,ne,nw,n,se,s,s,ne,ne,ne,s,ne,ne,ne,s,se,s,n,ne,ne,n,sw,n,ne,n,ne,s,n,ne,n,s,n,ne,n,ne,ne,sw,n,s,ne,ne,n,ne,sw,n,ne,ne,ne,n,sw,n,ne,sw,n,ne,ne,ne,n,ne,ne,n,n,s,ne,ne,sw,n,n,n,ne,n,s,n,n,n,n,sw,n,ne,n,n,n,n,n,ne,s,n,ne,se,ne,ne,n,ne,n,n,s,n,n,n,n,ne,se,sw,n,n,n,n,nw,n,nw,ne,n,n,ne,n,n,sw,s,n,ne,sw,n,s,n,s,n,ne,n,n,n,n,n,n,n,ne,ne,n,ne,sw,ne,ne,n,n,n,n,sw,ne,se,se,s,n,n,se,ne,n,ne,se,n,n,n,n,ne,ne,n,n,n,n,ne,n,s,ne,ne,n,n,n,se,ne,s,nw,n,ne,se,ne,n,sw,n,sw,ne,sw,ne,ne,n,nw,n,n,n,n,n,ne,n,s,s,n,ne,n,ne,n,ne,s,ne,ne,n,n,sw,ne,n,n,n,n,n,n,n,n,ne,se,sw,n,n,s,nw,n,n,n,n,ne,n,ne,sw,n,n,n,ne,nw,sw,nw,s,n,n,s,se,n,n,n,ne,n,ne,n,nw,se,n,n,n,n,nw,n,ne,sw,n,ne,se,n,n,ne,n,n,ne,n,n,n,n,ne,n,nw,n,n,n,n,n,n,n,n,n,n,s,s,n,n,n,ne,sw,n,sw,s,n,n,n,n,n,s,ne,n,se,n,n,s,n,sw,n,n,n,n,n,nw,n,n,n,n,se,s,nw,n,n,ne,n,n,n,sw,n,ne,ne,n,n,s,sw,nw,n,sw,n,se,n,n,s,n,n,sw,n,n,n,n,nw,n,n,ne,n,ne,s,n,ne,sw,n,n,n,n,n,nw,n,s,n,nw,n,n,n,ne,sw,n,n,s,nw,n,nw,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,ne,n,ne,n,n,n,nw,sw,se,n,n,n,n,ne,ne,n,n,n,n,ne,ne,n,ne,n,n,ne,n,n,n,n,nw,n,n,n,sw,n,n,n,n,s,nw,ne,se,nw,s,n,n,n,n,n,se,n,n,sw,ne,n,n,n,n,n,se,n,n,n,n,n,n,n,n,nw,n,n,n,n,n,s,sw,n,n,s,n,s,n,n,n,n,ne,nw,nw,n,ne,n,n,n,n,n,n,n,s,n,n,n,nw,n,n,n,n,n,n,n,se,sw,n,n,nw,nw,n,n,s,s,n,nw,n,n,n,n,n,ne,n,n,n,n,ne,n,n,n,n,n,n,n,n,sw,n,n,n,n,sw,n,n,n,n,n,n,nw,n,sw,nw,sw,se,n,sw,se,sw,se,n,se,n,n,n,s,n,n,ne,nw,n,se,n,ne,n,n,nw,sw,n,n,ne,n,n,n,se,nw,nw,n,n,n,n,ne,n,n,n,s,se,n,n,n,n,se,n,ne,sw,s,n,s,n,n,ne,se,n,sw,nw,n,n,n,nw,sw,n,n,ne,s,sw,nw,n,sw,ne,n,n,nw,n,n,n,nw,n,n,nw,n,n,n,s,n,n,nw,n,n,se,n,n,n,ne,n,ne,n,nw,n,n,ne,n,n,n,sw,ne,sw,n,n,sw,n,n,n,ne,n,n,n,n,nw,n,n,s,n,nw,n,n,n,n,nw,n,n,n,nw,n,n,s,n,n,n,n,n,nw,se,n,n,nw,sw,n,n,n,n,sw,n,se,n,n,s,n,n,n,n,n,n,n,n,n,n,n,nw,nw,n,nw,nw,n,n,nw,n,n,n,n,n,n,n,n,n,nw,n,n,ne,nw,n,sw,nw,n,n,sw,n,n,nw,n,n,ne,n,se,sw,sw,n,n,n,nw,se,se,s,n,n,n,s,n,n,n,n,n,s,n,n,n,n,n,n,n,se,n,n,se,sw,n,n,nw,n,n,n,sw,ne,n,n,n,s,n,n,n,s,s,n,n,s,n,nw,ne,nw,ne,nw,nw,n,ne,n,n,sw,n,n,se,nw,n,se,nw,n,n,n,nw,nw,n,n,nw,s,nw,n,ne,n,nw,n,nw,nw,n,n,n,n,n,sw,nw,ne,n,s,n,nw,n,nw,n,n,n,n,nw,nw,n,nw,n,n,n,n,n,n,n,n,n,n,sw,nw,s,n,n,se,se,n,nw,n,n,s,n,se,nw,n,n,nw,n,sw,se,sw,nw,nw,n,n,n,n,sw,n,se,n,n,sw,sw,nw,nw,n,nw,n,nw,n,se,se,n,ne,n,n,n,sw,n,n,sw,n,s,n,nw,n,n,n,s,n,nw,n,ne,sw,n,s,s,nw,sw,ne,n,ne,ne,n,n,nw,nw,nw,nw,s,nw,nw,n,n,n,n,n,n,n,nw,ne,n,n,n,n,sw,n,n,n,ne,sw,sw,n,ne,n,n,nw,ne,n,nw,n,n,n,n,n,n,n,nw,ne,se,n,n,nw,ne,n,s,s,n,s,nw,n,ne,n,nw,n,n,sw,nw,s,s,nw,nw,n,nw,n,se,n,n,n,n,nw,sw,nw,se,nw,sw,n,nw,nw,n,n,n,nw,n,ne,n,n,nw,nw,nw,se,s,nw,n,nw,nw,n,nw,n,n,n,nw,sw,n,n,n,ne,nw,n,n,n,nw,n,n,n,n,nw,n,nw,n,n,n,n,n,s,n,n,n,n,se,n,n,nw,n,n,nw,nw,se,n,nw,n,n,n,nw,se,nw,s,nw,n,nw,nw,n,nw,n,nw,nw,nw,sw,nw,ne,nw,sw,n,ne,ne,n,n,n,sw,n,n,nw,s,n,nw,ne,nw,sw,nw,sw,n,nw,nw,n,nw,nw,nw,sw,nw,n,nw,nw,n,nw,nw,sw,nw,s,n,nw,nw,n,n,nw,nw,n,n,sw,n,se,n,n,n,n,n,nw,nw,n,nw,nw,nw,nw,n,nw,s,se,nw,s,nw,ne,n,nw,nw,n,n,s,n,s,n,nw,s,n,nw,n,ne,s,n,nw,n,n,s,n,n,n,n,n,se,nw,n,n,sw,se,n,nw,n,n,nw,n,n,n,n,nw,ne,s,n,nw,n,n,nw,n,n,sw,n,n,nw,nw,nw,nw,nw,n,sw,se,nw,n,n,nw,nw,sw,s,nw,n,nw,n,n,n,ne,nw,n,nw,nw,n,n,nw,s,nw,nw,nw,ne,nw,nw,nw,nw,s,nw,n,n,nw,n,nw,n,nw,nw,nw,nw,nw,ne,nw,n,nw,nw,n,n,nw,nw,se,n,s,nw,sw,nw,nw,n,n,nw,ne,nw,nw,n,n,se,ne,nw,s,n,n,n,n,sw,se,nw,n,nw,nw,ne,n,nw,n,n,n,n,n,n,n,se,nw,nw,nw,ne,nw,nw,nw,s,n,nw,sw,se,nw,nw,n,nw,nw,n,nw,nw,nw,nw,ne,sw,se,n,sw,s,nw,n,n,se,nw,nw,ne,nw,n,n,nw,ne,s,n,nw,nw,nw,se,n,s,se,s,nw,n,s,nw,nw,nw,sw,n,n,nw,nw,n,nw,nw,n,nw,n,sw,ne,nw,n,s,n,nw,nw,sw,nw,n,sw,nw,n,nw,s,nw,n,nw,nw,sw,nw,nw,nw,nw,nw,n,n,n,nw,nw,nw,nw,n,nw,n,n,n,n,n,se,n,se,nw,nw,nw,nw,n,nw,nw,nw,n,nw,nw,nw,nw,nw,nw,nw,nw,sw,n,ne,n,se,nw,s,n,nw,nw,n,nw,nw,s,n,nw,nw,nw,nw,nw,se,nw,nw,n,nw,n,nw,se,nw,n,n,n,nw,n,nw,n,sw,n,n,nw,nw,n,nw,sw,nw,n,n,nw,nw,n,sw,nw,n,nw,nw,nw,nw,nw,nw,nw,s,nw,nw,nw,n,nw,n,nw,nw,nw,nw,nw,nw,nw,se,n,se,nw,s,nw,nw,n,nw,n,nw,n,ne,nw,nw,s,n,nw,n,se,n,nw,nw,nw,n,nw,nw,nw,nw,nw,nw,nw,n,n,se,n,nw,nw,nw,nw,ne,se,nw,ne,ne,nw,nw,nw,nw,nw,n,sw,s,sw,nw,n,sw,nw,nw,nw,s,nw,nw,sw,sw,n,nw,nw,nw,n,nw,ne,nw,nw,nw,n,n,nw,nw,nw,se,n,nw,n,n,n,se,nw,n,n,nw,nw,nw,nw,nw,nw,nw,ne,n,nw,nw,nw,nw,nw,ne,se,s,nw,nw,nw,sw,nw,nw,nw,n,nw,nw,nw,nw,s,nw,nw,nw,nw,nw,se,nw,s,nw,se,se,s,nw,nw,n,n,nw,nw,nw,nw,nw,n,nw,nw,nw,nw,nw,n,s,nw,n,n,nw,nw,n,nw,n,s,sw,nw,s,nw,nw,n,se,nw,ne,nw,n,sw,ne,sw,s,sw,n,n,nw,se,nw,nw,nw,n,ne,se,ne,nw,nw,nw,s,sw,n,nw,nw,s,nw,nw,nw,nw,nw,n,nw,nw,nw,s,nw,se,nw,nw,nw,nw,nw,nw,n,nw,nw,nw,n,sw,nw,ne,nw,n,nw,se,n,se,sw,nw,nw,nw,se,n,n,nw,nw,nw,nw,nw,nw,ne,nw,ne,n,sw,n,nw,s,nw,nw,nw,s,nw,nw,sw,nw,nw,n,n,nw,s,nw,nw,s,nw,se,s,nw,sw,n,se,nw,nw,n,s,n,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,ne,nw,nw,nw,s,nw,sw,nw,nw,nw,n,nw,nw,sw,nw,ne,nw,n,nw,nw,nw,nw,nw,n,nw,n,nw,nw,s,nw,ne,nw,n,nw,nw,ne,n,nw,ne,n,nw,nw,nw,nw,ne,nw,nw,nw,nw,sw,nw,nw,nw,n,n,nw,nw,nw,ne,n,s,s,nw,nw,nw,nw,nw,nw,nw,nw,n,ne,nw,nw,nw,n,sw,nw,nw,nw,nw,nw,nw,nw,n,nw,nw,nw,n,n,nw,s,nw,nw,nw,nw,nw,nw,nw,nw,nw,n,nw,ne,ne,nw,nw,nw,nw,nw,nw,nw,nw,ne,nw,n,nw,se,s,s,nw,se,nw,nw,nw,nw,n,s,nw,s,nw,ne,nw,nw,nw,sw,nw,sw,nw,nw,nw,ne,nw,nw,nw,nw,nw,ne,nw,nw,nw,sw,nw,nw,nw,nw,nw,nw,nw,nw,se,nw,s,nw,nw,se,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,n,nw,nw,nw,s,n,nw,ne,nw,nw,se,ne,nw,nw,ne,nw,nw,nw,nw,nw,se,nw,nw,nw,s,s,ne,ne,ne,se,se,sw,se,se,s,s,s,s,s,s,sw,sw,sw,sw,ne,ne,sw,nw,sw,nw,nw,sw,ne,sw,sw,nw,sw,nw,sw,nw,ne,nw,nw,nw,nw,n,ne,sw,nw,nw,nw,n,ne,n,n,n,n,se,nw,se,nw,n,n,n,nw,n,n,n,n,s,n,n,ne,n,n,n,n,n,se,n,n,n,se,n,n,ne,ne,n,ne,ne,ne,se,nw,n,n,ne,nw,n,ne,ne,ne,sw,ne,ne,ne,ne,ne,ne,ne,s,nw,ne,ne,se,ne,ne,se,se,ne,nw,ne,ne,ne,sw,ne,ne,ne,se,se,se,ne,se,ne,se,se,se,se,ne,se,se,ne,se,se,se,se,se,se,sw,s,se,se,s,sw,se,se,se,se,se,se,se,se,se,se,ne,ne,se,se,ne,se,s,s,s,se,sw,se,sw,se,se,s,ne,ne,se,n,se,se,s,nw,n,se,s,se,se,se,se,se,se,se,se,s,se,s,s,se,s,s,s,s,ne,s,ne,n,se,s,nw,nw,s,ne,s,s,s,s,n,s,s,s,s,n,s,n,s,n,s,se,s,s,sw,s,s,nw,s,se,s,s,ne,s,s,s,s,n,sw,s,sw,s,s,sw,n,n,s,ne,sw,ne,n,s,s,s,s,sw,sw,s,sw,s,se,sw,se,sw,nw,s,sw,n,sw,sw,sw,s,nw,sw,s,sw,s,ne,sw,s,sw,s,n,ne,sw,ne,sw,sw,se,sw,n,sw,nw,sw,sw,sw,sw,ne,sw,sw,nw,s,n,nw,s,se,sw,sw,s,sw,sw,sw,sw,sw,sw,nw,sw,sw,sw,sw,sw,sw,sw,nw,sw,sw,sw,sw,sw,sw,sw,nw,sw,nw,sw,s,s,sw,sw,nw,se,se,sw,sw,nw,sw,s,sw,nw,sw,sw,nw,sw,s,sw,nw,s,se,sw,ne,sw,nw,sw,nw,sw,n,nw,sw,sw,sw,sw,n,sw,nw,n,sw,sw,sw,nw,sw,n,nw,nw,sw,sw,nw,nw,sw,sw,nw,sw,sw,sw,ne,sw,nw,sw,nw,nw,n,nw,ne,n,nw,sw,nw,sw,nw,nw,nw,nw,nw,nw,se,sw,nw,sw,nw,sw,sw,se,sw,sw,s,s,nw,nw,nw,nw,nw,nw,nw,n,sw,nw,nw,s,sw,nw,nw,nw,sw,ne,sw,se,nw,nw,n,nw,nw,nw,nw,sw,nw,nw,nw,nw,sw,nw,se,sw,nw,nw,nw,nw,nw,nw,nw,nw,se,nw,nw,nw,nw,nw,s,nw,ne,nw,n,nw,nw,ne,nw,nw,ne,sw,nw,se,nw,nw,nw,ne,nw,n,nw,nw,ne,sw,nw,nw,nw,n,nw,nw,nw,se,nw,nw,n,nw,nw,nw,nw,nw,nw,n,nw,nw,nw,nw,nw,ne,n,nw,nw,n,nw,n,nw,s,nw,nw,n,nw,nw,se,n,nw,se,nw,nw,nw,ne,n,nw,n,sw,n,nw,nw,n,nw,n,n,nw,nw,s,sw,n,nw,n,nw,nw,n,n,se,nw,nw,nw,nw,nw,n,n,n,n,sw,n,n,n,nw,n,n,n,n,n,n,ne,n,n,ne,nw,sw,ne,s,n,n,n,n,nw,n,ne,nw,nw,n,n,sw,n,ne,n,n,n,s,nw,sw,n,n,n,nw,n,sw,n,nw,se,n,n,se,n,n,n,nw,n,n,n,n,n,n,nw,nw,n,n,n,nw,ne,se,s,n,n,n,n,se,nw,nw,n,n,n,n,n,n,n,n,ne,nw,n,n,n,n,n,n,sw,nw,s,sw,n,n,n,ne,ne,n,n,n,n,n,n,n,ne,n,nw,n,ne,n,n,se,n,n,n,ne,n,nw,n,sw,n,nw,n,n,n,n,n,n,n,n,n,ne,ne,n,n,n,ne,n,se,n,n,se,n,n,n,se,sw,se,n,n,n,nw,se,n,ne,n,ne,n,ne,ne,n,n,se,n,n,n,n,n,ne,ne,nw,n,ne,n,n,n,ne,sw,n,n,s,n,n,s,ne,n,ne,n,n,ne,n,n,ne,se,sw,n,s,ne,n,sw,n,n,n,se,ne,n,n,n,s,sw,se,ne,s,se,ne,n,ne,n,se,n,ne,ne,ne,ne,ne,ne,n,n,ne,ne,ne,nw,nw,ne,ne,ne,sw,n,ne,n,ne,n,ne,sw,n,n,n,s,ne,n,ne,ne,ne,se,sw,ne,se,sw,ne,ne,se,sw,ne,n,n,ne,ne,ne,ne,ne,n,n,n,s,n,n,n,ne,ne,ne,ne,ne,ne,s,ne,ne,ne,ne,ne,ne,n,n,n,ne,se,nw,ne,ne,s,ne,n,n,n,ne,ne,n,ne,n,se,se,n,ne,ne,ne,se,ne,ne,s,s,ne,ne,ne,ne,n,sw,ne,ne,ne,n,ne,ne,ne,ne,ne,ne,n,ne,nw,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,se,ne,s,ne,sw,se,ne,sw,n,n,ne,n,se,ne,ne,ne,se,ne,ne,ne,s,ne,ne,n,se,ne,nw,ne,ne,ne,ne,ne,ne,ne,s,ne,ne,n,se,se,se,se,ne,s,ne,ne,ne,ne,ne,ne,sw,ne,ne,ne,ne,ne,ne,nw,ne,ne,ne,s,se,ne,ne,ne,n,n,ne,se,ne,ne,se,ne,ne,se,se,ne,ne,ne,ne,ne,ne,se,nw,se,sw,ne,ne,ne,nw,ne,ne,ne,n,ne,se,sw,nw,s,ne,se,ne,se,s,ne,ne,ne,ne,ne,s,ne,se,n,ne,ne,se,se,ne,ne,ne,ne,n,ne,ne,se,ne,ne,ne,ne,se,ne,ne,ne,ne,ne,se,ne,ne,ne,nw,ne,ne,se,ne,ne,se,se,sw,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,sw,se,ne,n,n,n,se,ne,s,se,sw,sw,ne,ne,ne,sw,ne,se,ne,ne,ne,ne,ne,ne,nw,se,se,se,s,se,ne,s,se,ne,se,s,ne,ne,se,n,se,se,ne,n,ne,ne,se,nw,ne,se,se,nw,sw,s,ne,sw,se,se,ne,ne,s,se,ne,ne,ne,se,nw,ne,se,se,sw,nw,sw,se,ne,ne,se,se,se,se,ne,ne,ne,s,s,se,se,s,se,ne,se,ne,se,ne,ne,se,ne,se,ne,se,se,se,ne,se,se,se,s,se,se,se,n,se,se,ne,ne,se,ne,ne,se,se,se,se,ne,se,se,se,s,ne,se,ne,s,se,se,se,nw,ne,se,n,se,se,se,nw,se,se,nw,s,se,ne,se,nw,se,ne,se,ne,nw,se,se,nw,ne,se,se,n,nw,sw,se,ne,se,n,se,ne,ne,se,nw,se,ne,s,se,ne,se,se,ne,ne,se,n,ne,nw,se,ne,s,se,se,se,s,se,ne,se,se,se,se,se,nw,se,se,se,ne,n,se,ne,se,ne,se,n,se,nw,se,ne,n,nw,ne,ne,se,ne,se,nw,s,se,s,se,se,ne,se,s,se,n,se,se,se,se,se,s,se,s,se,se,se,ne,se,se,ne,se,se,nw,se,se,se,se,se,se,se,se,se,se,se,se,se,ne,s,se,se,se,se,s,se,se,se,n,se,sw,se,nw,se,se,n,ne,sw,se,se,se,se,ne,se,se,se,se,se,se,se,se,se,se,n,se,se,se,n,ne,s,se,se,se,se,se,se,se,se,se,se,se,n,se,s,ne,se,ne,se,se,se,se,se,ne,s,s,se,s,ne,se,se,s,se,se,se,se,se,se,sw,se,se,s,se,se,se,se,se,se,se,se,se,se,se,se,se,se,se,sw,se,se,se,nw,se,se,s,se,n,se,se,se,se,se,se,se,se,se,s,se,n,se,ne,se,s,s,s,se,nw,se,se,se,s,se,se,se,n,ne,se,s,se,nw,se,se,s,s,se,se,se,se,se,se,s,se,nw,se,s,se,s,s,se,se,s,se,se,ne,se,n,se,s,s,s,ne,se,s,se,s,se,se,se,n,se,sw,se,s,se,n,s,se,s,se,se,se,se,s,se,s,se,s,se,s,s,se,se,se,se,se,se,s,se,sw,se,se,nw,s,n,se,sw,s,se,se,sw,ne,sw,nw,s,s,s,s,nw,se,se,se,s,se,se,se,se,ne,s,se,nw,s,se,se,se,s,se,se,s,sw,s,s,se,se,ne,s,se,se,s,se,s,se,s,s,se,se,se,n,se,sw,s,se,s,s,s,se,se,se,ne,se,s,s,s,se,s,ne,se,n,se,s,s,s,s,s,s,se,se,se,se,nw,se,s,s,se,s,se,s,se,s,se,sw,s,nw,se,se,se,se,se,s,s,sw,se,nw,s,se,s,s,s,ne,s,s,s,s,se,s,se,s,nw,se,s,se,s,se,se,nw,nw,se,nw,nw,s,s,nw,se,s,s,nw,se,s,se,se,se,s,s,se,se,ne,s,s,s,s,se,s,se,se,se,se,ne,s,se,se,s,s,se,se,se,nw,s,ne,s,se,s,s,s,s,s,s,ne,s,s,s,s,se,s,s,s,se,sw,se,s,s,se,se,ne,s,se,se,se,s,s,sw,s,s,nw,sw,sw,se,s,s,s,se,s,s,s,se,se,s,s,s,nw,se,se,se,nw,se,s,s,s,s,s,se,ne,se,s,se,s,s,ne,se,se,ne,s,s,s,ne,s,se,ne,se,s,s,s,s,s,s,s,s,s,sw,s,ne,s,s,se,se,se,s,sw,s,sw,s,s,s,n,nw,s,s,s,se,s,nw,se,se,s,s,s,s,s,ne,se,s,s,s,se,s,s,s,s,nw,s,sw,sw,s,s,s,se,ne,s,s,s,ne,s,s,s,n,s,s,s,ne,se,s,s,s,s,s,s,s,s,s,s,nw,s,se,s,s,ne,s,ne,se,s,s,sw,s,ne,s,s,s,se,se,s,s,nw,s,s,s,s,s,nw,s,s,s,nw,s,s,s,s,s,s,s,se,s,sw,s,se,ne,s,n,nw,se,s,s,s,se,s,se,s,n,s,s,ne,s,s,nw,nw,nw,s,s,s,s,nw,s,s,s,s,s,s,s,nw,s,s,s,s,sw,s,s,sw,n,nw,ne,s,s,s,s,n,s,s,s,n,sw,n,s,s,s,s,s,s,nw,s,n,se,s,s,s,ne,s,se,nw,sw,se,sw,s,s,s,s,s,s,sw,s,se,s,n,s,sw,s,s,sw,sw,s,se,s,sw,s,s,s,sw,s,ne,s,s,s,s,sw,s,s,s,s,nw,s,se,sw,se,nw,se,ne,s,s,s,n,sw,s,sw,s,s,s,s,s,s,s,s,s,s,sw,sw,s,se,s,nw,nw,s,ne,nw,s,n,se,n,n,s,s,ne,sw,s,se,s,sw,s,s,s,se,sw,s,sw,nw,sw,sw,s,se,s,n,ne,s,s,sw,sw,s,s,sw,sw,s,n,s,sw,s,sw,s,s,nw,sw,s,s,s,sw,ne,ne,sw,sw,s,sw,se,s,se,s,ne,se,s,s,nw,sw,s,ne,sw,ne,s,sw,s,s,s,s,s,s,s,s,s,n,se,s,s,s,s,s,s,s,sw,sw,s,n,sw,sw,sw,s,s,sw,s,ne,se,s,s,s,sw,s,s,s,sw,s,sw,s,sw,s,s,s,s,s,sw,s,sw,nw,sw,s,se,sw,s,nw,s,s,s,n,ne,sw,sw,s,s,s,s,sw,s,s,s,n,s,sw,n,s,s,sw,s,sw,s,sw,s,s,n,s,n,n,sw,sw,sw,sw,sw,sw,s,ne,ne,s,sw,s,sw,s,sw,se,sw,s,sw,sw,n,s,ne,sw,sw,s,sw,se,se,nw,sw,sw,se,ne,s,s,s,s,s,sw,sw,sw,n,s,se,s,s,n,s,sw,n,sw,sw,sw,ne,s,sw,sw,s,n,sw,n,s,s,s,ne,s,se,s,sw,ne,sw,sw,s,sw,sw,sw,s,s,se,s,sw,s,s,ne,sw,s,sw,sw,s,s,sw,ne,s,sw,sw,sw,sw,n,s,ne,ne,ne,s,sw,s,se,se,sw,s,sw,sw,n,sw,sw,nw,sw,s,sw,s,sw,s,sw,s,sw,sw,s,sw,sw,sw,n,s,sw,ne,sw,s,nw,s,s,sw,s,sw,nw,sw,sw,sw,sw,nw,sw,sw,sw,sw,sw,s,s,s,s,n,s,sw,s,sw,sw,s,ne,s,nw,n,nw,s,sw,sw,sw,sw,sw,sw,sw,sw,s,s,n,sw,s,s,n,se,s,se,sw,s,sw,ne,sw,sw,sw,sw,s,sw,sw,ne,s,sw,sw,se,sw,s,n,s,s,sw,s,se,sw,sw,se,sw,sw,sw,nw,sw,sw,sw,nw,sw,s,s,s,nw,sw,s,n,sw,s,sw,n,sw,sw,s,sw,sw,s,s,nw,s,sw,sw,sw,ne,sw,sw,nw,s,ne,s,sw,sw,sw,sw,sw,n,sw,sw,sw,s,n,sw,s,sw,se,sw,sw,sw,sw,sw,sw,sw,s,sw,sw,ne,sw,s,sw,s,sw,sw,sw,n,sw,sw,sw,s,sw,sw,s,sw,sw,sw,sw,n,s,s,sw,ne,sw,se,n,sw,s,sw,n,sw,sw,sw,nw,sw,s,s,n,sw,s,sw,sw,sw,ne,sw,sw,nw,ne,n,sw,sw,sw,sw,s,sw,n,sw,s,s,sw,s,s,s,sw,sw,sw,s,sw,sw,s,s,sw,s,s,se,sw,sw,s,sw,sw,se,sw,s,se,sw,sw,sw,sw,se,sw,sw,sw,sw,s,sw,s,n,sw,sw,sw,n,n,sw,sw,nw,sw,sw,sw,se,sw,nw,sw,sw,sw,sw,sw,sw,sw,s,sw,n,nw,n,sw,nw,sw,sw,sw,sw,s,ne,sw,ne,sw,se,n,s,s,sw,sw,sw,n,se,s,sw,se,s,sw,sw,sw,ne,s,n,sw,sw,sw,sw,sw,s,sw,nw,sw,sw,sw,sw,sw,sw,nw,sw,nw,sw,s,sw,sw,sw,sw,se,sw,sw,sw,sw,sw,ne,se,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,nw,sw,sw,sw,sw,sw,nw,sw,s,sw,nw,sw,sw,sw,sw,n")

(defn parse [s]
  (map keyword (str/split s #",")))

(str/replace "n,s" #"\bn,s\b" "")