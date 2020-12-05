(ns adventofcode.y2020.day05
  (:require [clojure.string :as str]))

(def input "FFBFFBBLRL\nFBFBBFBLRR\nFBFFBBBRLL\nFFBBFBFRRR\nBFBBBBFLRR\nBBFBBBFRRL\nBFBBBBBLLR\nFBFFBFBRRR\nBBFFBBBLRL\nBBBFFFFLLR\nFFFFBFFRLL\nFFBFFFFRLL\nFFFBFBBLRR\nBFBFBBBRLR\nFBBFBBBLRR\nBBFFFFBRRR\nFBBFFFBRRR\nBFFFBFFLLR\nFBBBBFFLRL\nBFBFFFBRRL\nFFBFBBFRRL\nBBFBFFFRRR\nFFBFFBFLRR\nFFBFFBBLLR\nFBFBFFFLRR\nFFFBFFFRRL\nFFFFFBFRRL\nBBBFFFFRLL\nFBBFBFFLLL\nFBBBBFBLRL\nBBBFBBFRRR\nBBBFBFBLLL\nFBBFFBBLLL\nFBBBFBFLRL\nFBBBFBFRLL\nBFFFFFFLRR\nFFFFBBBRRR\nFBFBBBFLLL\nFBFBFBFRLL\nFBBBFBFLLR\nFBBFFFFLRR\nFFBBBBFRRL\nFFBBBBFRLR\nFBFBBBBLRL\nBFFFFFFRRL\nFBBBFFBLLL\nFBFFFFFRLL\nBFFFFBFRRL\nFFBBBFBLRL\nFBFFBFFRLR\nFBFFBBFLRL\nBFFFBFBRLL\nBFFFFFFLLL\nFFFBBFBLRR\nFBFBFBBRLR\nBBFFBFFLRR\nBBFFFBBLRR\nBFBFBBFRLR\nFBBFBBBLLR\nFFFFBBFLLL\nBBFBBBFLRL\nBFFBBBFRLR\nFBFFFFBLLR\nFFFFBFFLRL\nBBFBFBFLRR\nBFFBFFBLLR\nBBFBBBFLRR\nFFFFBBFRLR\nFBBFFBBLRL\nFBBBBBBRLL\nFBBFBFBRLR\nFFBBFBFRLL\nFFBFBFFLRR\nBFFBBBBLLR\nFBFBFBFRRL\nBBBFBBFLLL\nFFBBFFBLRR\nBBFBBFBLRR\nBFFFBBBLLL\nBBBFFBFRRL\nFBBBFBFLLL\nFBFBFBBRRL\nBFBFFBBLLR\nFFBFFBBRRR\nBBFFFFFLLL\nBFFBFFBLLL\nBBBFBFFRRL\nFBFFBFBRLR\nBFFBBFFLRR\nBFBFBBFLRR\nFBBBBFBRLR\nBFBBBBBRLL\nBFBBBFBLRR\nBFFFFFBLLR\nBBBFFFBLRL\nBFFFBFBLRL\nBFFFBBBLRR\nBBFBBBBRLL\nFFBBFBFLLL\nBFFBFBBRRL\nBFFBBFBRRR\nBBFBBBBRRR\nFFBBBBBLLL\nFBBBFFBLRL\nFFBFFBFLRL\nFFBBBBFRLL\nFFFFBBFLRR\nBBFFFFBRRL\nBFFFFBBRRR\nFFFBFBFRLR\nFBBBBBFLRR\nFFFFBBBRRL\nBFBFBFBRRR\nBFFBBBFRRR\nFFFFFFBLLL\nFFFBBFBLLL\nFBFFFBFLRL\nBFFFBBFLRR\nFBBBBBFRLL\nFBFFFFBRLL\nFBFBBBFRLL\nFFBFBBBLRL\nBFBFBFFLLL\nBBBFBFBLLR\nFBBBBFFRRR\nFBBFBFFRLR\nFBFFBFFRRR\nBFBFBFFLRR\nBBFFFBFLRL\nBBFFFBBRRL\nBFFBBFFLLL\nBFBBFFFRLL\nFFFBFFBRLR\nBBFBBFBLLR\nBBBFFFFRLR\nBFBBFBBRRR\nFBBBFBFRRL\nBBFBBBBLLR\nBFFBBBBRRR\nFFFFFBFLLL\nBBBFBFFLRL\nFBFFFFBLLL\nBFFBBBFRRL\nFBFBBFBLRL\nFFBFBBBRLR\nFFFBBBBLLL\nBBFBBFFRLL\nBBFBFBBRLL\nBBFBFBBRRL\nBFBBBFBRLR\nFBFBFFFRLL\nBFFFBFFRRL\nBFFFFBFLRR\nBFBBFFBRRL\nBBFFBBFRRL\nBBBFFFFLLL\nFBFFBFBRRL\nFFFBBFBRRL\nFBFFBFFLLL\nFFFBFBBLRL\nBBFFBFBLLL\nFFBBBFBLLL\nBFBBBFFLRL\nBFBFBBBRRL\nFFBBBBFLRR\nFBBFFBFRRR\nFFBFBFBRLL\nBFBBFFBLLR\nBFFBFBFRRL\nFBFFFFFLRR\nFBFBBBBRLR\nBFFFFFBLRR\nBBFBFFFRLL\nFFBFBBFLRR\nBBFFFFFRLL\nBFFBBFBRLR\nFFBBFBBRLR\nBFBBFFFLRL\nFFFFFFFRRL\nFFBBBBBLRL\nFFFBBBBLLR\nFFFBBFFLLR\nFFFBFFFRLL\nBFBFFBBRRL\nBBFBBFFRRL\nFFBFFBBRRL\nFFBFFFFRRL\nFFFBFFBRLL\nFBFBFFFRLR\nFFFBFBFRRR\nBFBBBBFRRR\nBBBFBFFRLL\nFBBFFFFRLR\nBFFBBBBLRR\nFFBBBBBRLL\nFFBBFBFLRL\nBBBFFBBRLL\nBBFBBBFRLL\nBFBBFBFRRR\nFBFBBFBRRL\nFBBFBBBRRL\nFFBFFBBRLR\nFFFFFBFLRL\nFBFBFFFLLL\nFBFFBBFRLL\nFBBBFFFRRR\nBFFBFBBRLL\nBFBFFFFRLL\nBBFFFBBLLR\nFFBBFBBLLL\nBBBFFFBLLR\nFFFBBBBRLL\nBFBBBBFRLL\nBFBBBFFLRR\nBFFFFFFLLR\nBFBBBBFLLL\nFBBBFFFLRL\nFBBBBFBLLL\nBFFFBBFRRL\nFBBBBFFRLR\nBBFBFBFRLR\nBFFBFBFLRR\nBBFBFFBRRL\nFFFFFFBRRL\nFBFBFBBLRL\nBFBFFBBLRR\nFFBBFBFRRL\nFBFFFBFLLR\nFBBBBBBRRR\nBBBFBFFLRR\nFBBFBBBRLL\nFFBFFBBLLL\nBBFFBFBRRR\nBFFBBBFLRR\nFFFBFFFLLR\nFBBFBFFRLL\nBBFFFFBLLL\nBFBFBFFLRL\nBBFBBFBRRR\nBBFFFBBRLL\nBFFBBFBRLL\nFFFFFFBRLL\nBFFBFFBRLL\nBFFBBFBLRR\nBFBBBBFLRL\nBBFFFFBLRL\nFFFBBBFLRR\nBFBBFBBLRL\nFBBBFBBRLL\nBBFBBFBRLR\nBFFBBFBLLL\nFBBBBBFRRL\nBFFBBBFLRL\nFFFBBFFRLR\nFBBFFFFRLL\nBFFFFBFRLL\nFFBBBFBRLL\nFFFBFFFRLR\nBFBBFFFRRL\nBFBFFBBRRR\nFFBBFBFLRR\nFFFBFFBLLR\nBBFBBFFRLR\nBFFBBFFRRL\nFBBFFFFLRL\nFBFBBFFRLL\nBBFBFBBLLR\nFFFFFBBLRR\nBFBBBFFLLR\nBFBFFBFLRR\nBFBBFBFRRL\nFBBFBBFRLR\nBBFBBBFLLR\nBFFFBBBRLR\nFBFFBFFRLL\nBFBBFFFRLR\nBBFFBBFRLR\nBBFBFFBLRL\nBBBFBFBRLL\nFFFFBFFLRR\nBFBFBBBLLL\nBFFFFBFLRL\nBFBBBFBRLL\nFFFBFFBLRR\nFFFBBFBRLR\nBBFFFBBLLL\nFFFBBBBRRL\nFFBFBFFRLR\nBBBFFBBLRL\nFFBFFBFLLL\nFFFBFBBLLR\nFFBFFFFLRL\nFFBBFFFLLL\nFBFFFBBRRL\nBFFFFBBLLR\nBFBFFFFRRR\nFBFBFBBRLL\nBFBBBFFRLL\nBFFBBFFLRL\nFFBBFFBLLL\nFBBBBFBRRL\nBBFFFBFRRL\nFFFBBFBLRL\nBBBFFFBRLR\nBFBBFFFRRR\nFFBFBBBRRR\nBFFFBFBRRL\nFBBFFBFRLL\nBBBFFBBLLL\nFBBFBBBRLR\nBBBFBBFLRR\nBBFFFFBRLR\nBFFFBFBRLR\nFBBBFBFRLR\nBFBBFFFLLL\nFBBBBBBRRL\nBBFFBBBRLR\nBFFFFFFRRR\nBBFFFBBRLR\nFBFBBFBLLL\nBFBFBBBLLR\nFBBBFBBLRL\nFFBFFFBRRR\nBFFFFBBRLL\nBBFBBFFLRR\nFBBBFBFLRR\nBFFBFFFLLR\nBBBFFFFLRR\nBFFBFBFRRR\nFFFFFFBRRR\nBFBFFFFLRR\nBFFFBBBLRL\nFBBBFFBLRR\nFFFFBFFLLL\nFFBBFFFLLR\nBFBBFFBRLR\nFBFFBBFLLL\nFFFBBBFRLL\nBFBBFFBLRR\nBFFBFFBRRR\nFBFBBFFLRR\nBFFBFBFLLR\nFBFFFFBLRR\nFBFFFBBLRR\nBFBFFBFRRL\nFFBFBFBLRL\nFBFBBBBRRL\nBBFFFFFRLR\nFFBFFBFRRL\nFFBBFFBRRR\nBBFFBFFRRL\nFFFFFBBRLR\nBFBBFFFLLR\nFFFBBFFLRR\nBFFBFBBRLR\nFBFFBFBLLL\nFFBBFBFLLR\nFFBBFFFRLR\nBFBFFFFRRL\nFBBFBBBRRR\nBFFFFBFRRR\nBFBFBFBLLR\nBBBFBFFRLR\nBFFFBFBRRR\nFBFBFFBLRR\nBBFFBBFRLL\nBFBFBBFLRL\nBFBFFFFLRL\nBBBFBFBRLR\nFFBBBBFLLL\nFBBBFFBRLL\nFFBFFFBRLR\nFFBBBFFRRL\nFFBBBBBLLR\nFFFFBFBLRL\nFFFBFFFLRL\nFBBBBBBLLR\nBFFFBBFLRL\nFBBFBFFLRL\nFFBFFBFLLR\nFFBBBBBRRR\nFFFBFBFRRL\nBFFBFFFRLR\nFFBFFBBLRR\nBBFFFFFLRR\nBBBFBBFRRL\nBBFFFBFRLL\nFBFBFBBLRR\nBBFFFBBRRR\nBBBFBFBLRL\nFBBBFFFRLR\nBFFFBBFLLL\nFBBBBFFLRR\nFFBBFFBRLR\nFFBFFFFRRR\nFBFBFBFLRR\nBBBFFFFRRR\nFBFFFBFRRL\nFFBFFFFLLL\nBBFBBBBRRL\nFBFFBBFLRR\nFBFBBFFRRR\nBFFFBBBLLR\nFFBBBFFLRR\nBFFFFBBRRL\nFFBFFFFLRR\nBFFBFBFLRL\nBFFBBBBRLR\nBFFBFBBLLL\nBFBBBBBRRR\nFFBBFBBLRL\nBFBFFBFLRL\nBBFBBBFRLR\nFBBFBBFRLL\nBFBFFBBRLL\nFBFBBBBRLL\nFFBBFBBLRR\nBFBFFBBLLL\nFFBFFFBLLR\nBFBBBFBRRL\nBBFFFBFLRR\nBBFFBBFLRL\nFBBFBFBLLR\nBBBFBBFRLR\nFBBBBBFRLR\nBFBBFBFLLL\nFBFFFFBLRL\nFBBFFBBLLR\nFBBFFBBRLR\nFFFFBFBRRR\nFBBFFBFLRL\nFBFBBBBRRR\nFBBFFFFRRL\nFFFBBBBLRL\nBFFBFBFRLL\nFFBFBFFRRR\nFFFBBFFRRR\nBBFFBBBLRR\nFFBFFFBLRR\nFFBBFBBRLL\nFFBBFFFRLL\nFBBBFBBLLL\nBBFFBFFRLL\nFBBFBFBLRR\nBBBFFFBLLL\nFBFBFBFLRL\nBFFBBFBLLR\nBFBFBBFLLR\nBFBBBFFRRR\nFBFFFBFRRR\nFFBFFFBRRL\nBBFFBBFRRR\nBFBBFBBLLL\nFFFFFBBRLL\nFBFFBFFLRL\nFFBFBFBLLR\nFFBBBBFLLR\nBFBFFBFRRR\nBFFFFFBRRL\nBFFFFBFLLR\nFFFBBBFLLR\nBFBFBFBLRR\nFFFFBBFLLR\nBBBFFBBRRR\nFFBFBFBRRR\nFBFBBBFLRR\nBFFFFBBRLR\nBFFBFFBRLR\nBFBBFBBRRL\nFFBBFBBRRR\nFBBFBBFLRR\nBBFFBBBRRL\nFBBBBBBLRR\nFFFBFFFRRR\nFFFFBFBRRL\nFBFFBFFLRR\nBFFBFFFLRR\nFFBFFBBRLL\nFBFBBFFLLR\nBFBFFBFRLR\nBBFBFFFLRL\nBFFFFFFLRL\nFBFFFBFRLR\nFBFBBFBLLR\nFBBBBFFLLL\nBFBFBBFLLL\nBBFFFBFRLR\nBFBBBBFLLR\nFFFFBBBRLR\nBFBFFBFLLR\nBBBFBFFLLL\nBFFFFFBRLL\nFFBBFBBLLR\nFBBBBFFRRL\nFFBBFFBLRL\nFBFBBBFLLR\nFBFFBFBLRL\nFBBBFFFLRR\nFFBFBFFRRL\nFBBBBBBRLR\nFFFBBFBRLL\nFBFBFFBRLR\nBBBFBFBRRR\nFBBBBBBLLL\nFFFFBBBLLL\nBBFBFBFLRL\nBBFFFFFRRL\nFBBBFFBRLR\nFFFFBFFLLR\nBFFBBFFRLL\nFBFFBBFRRL\nBFBFFFBRRR\nFBFFBFFRRL\nFFFFFBBLLR\nBBFFFFBRLL\nFFFBFBFLLL\nBFBBFBFRLR\nBFBFBBBLRR\nBFFFBBBRRR\nFFFFFFBLRL\nFFFFFFBLRR\nBFBFFBBRLR\nBBBFFBFLLR\nFBFFFBBLLL\nBBBFFBFLLL\nBBFFBBFLRR\nBFFBFFFRRL\nFFBFBBBRLL\nFBBBBBFLLL\nBBFBFFBRRR\nBBFFFFFLRL\nBFFBFBBLRL\nBFBBBFFLLL\nBFBFBFFRRR\nFFBBFBBRRL\nFBFBBFFLLL\nBBFFBFFRRR\nFBBFFFBRRL\nBBBFBBFRLL\nFFFFBBFRLL\nBBBFFBBRRL\nBBFBBBFRRR\nFBBFFBBRRL\nBFBBBBFRLR\nBFBFBBFRLL\nFFBFBFBLRR\nBFFBFBBLLR\nFBFBBBFRRR\nFFFBBBBLRR\nFBFFFBBRLL\nBBFFFBFRRR\nFBFFFFBRRL\nFBBFFFBRLL\nFBFFFFFLLL\nFBBBBBFLRL\nBBFFBFFLRL\nBBBFBBFLLR\nFBFFBFFLLR\nFFBBFFFRRR\nBFFFFFBRRR\nFFBFBFBLLL\nFFFFBFBRLL\nBBFBFFBRLR\nBBFFBFBRLL\nFBBFFBFRLR\nFBBBFFBRRL\nFFFBBBBRRR\nBBFFFFBLRR\nFFBFBBFRRR\nBFFFFFBLRL\nFFBFBFBRRL\nFFBBFBFRLR\nFFBFBBBRRL\nBFFFFBBLRL\nFBBFBFBRRR\nBFFFFFFRLR\nFFFFBFFRLR\nBFBFFBFRLL\nBFFBBFFRRR\nBFFBFFBLRR\nFBFBFFFLLR\nBFBFBFBRLL\nBBFFFBFLLL\nBFBFFFBRLL\nBFBFBFFRLL\nFBFBBBFLRL\nFBFFFFFLRL\nBBFBBFBRRL\nFFBFFFBRLL\nFBBFFFBLLR\nFFBBBFFLRL\nBFFBFBFRLR\nFBFFFBBLLR\nFFFBFFFLLL\nFBBBBBFRRR\nBFBFBFBLRL\nBBBFFBFLRR\nBBBFBBFLRL\nBBFBFFFLLR\nFBBFFFBLLL\nBFBFFFBLRL\nFBFBFBBRRR\nFBBBFBBRRL\nFFFFFFBRLR\nFBBFBBFRRL\nBBFBFBFLLL\nBBFBBBBRLR\nBFBBBBBLRL\nFBBBBFBRLL\nBFFBFFFLRL\nBFBBBFFRRL\nFFBBBFBLLR\nFBFFBBBLLL\nFFFFFFBLLR\nFFFBFFBLRL\nFFFBFFFLRR\nFFBBFFFLRL\nFBFBFFFRRR\nFBFBFBBLLR\nFFFFBBBLLR\nFFBBBBBRLR\nBFBBBBBLRR\nBBFBFBFRLL\nBFBFFFBLLL\nFBBBFBFRRR\nFBBFBBFLRL\nFFFBBFFLRL\nFFFBFBFLRL\nFBFBBBBLRR\nFFFBBFBRRR\nFFFBBFFRLL\nFFBBFFBLLR\nFBBBBBFLLR\nBFBFBFFRRL\nBBFBBFBRLL\nFBBBFFBRRR\nFBFBBFBRLR\nFBBFBBBLRL\nBFFFFBFRLR\nBBFBBBBLRR\nBFFBBFFLLR\nFBBFFFFLLL\nFBFFFFBRLR\nFFFFBBFRRL\nBFFFBFFLLL\nFFFFBBBLRR\nFBFFBBBLRL\nFFBFFFFRLR\nBBBFFBFLRL\nBFFBBBBLLL\nFFFFFBBRRL\nBFBBFFBLLL\nBFBBFBFLRR\nBFFBBBFLLL\nFBFFFBFLLL\nFBBFFFBLRL\nFFBBBBFLRL\nFBFFFBFRLL\nFBBFFFBRLR\nFBFBBFFLRL\nFBBBBFBRRR\nFBBBBFBLRR\nBBFFBFBLLR\nFFBFBBBLLL\nFFFFFFFRRR\nFFFFFBFRLR\nFFBFFFBLRL\nBBBFFFBLRR\nFBBBBFFRLL\nFFBFBBFLLL\nBFFFFFFRLL\nFFBBBFBRRL\nFBFFBFBLRR\nBFFBFBBLRR\nBFFFBBFRRR\nFBBFBBFLLL\nBFBBFBBLRR\nBBFBBFFLRL\nFBBBFBBRLR\nBFBBBFFRLR\nFFFFFBBRRR\nBBFFFBFLLR\nBFFFFFBLLL\nFBFBFFBRRR\nBBFFBFBRLR\nBFBBFBFLLR\nFBFBFBFLLR\nFBBBFFFRLL\nBBFBFFBLRR\nBBFBFFFRLR\nBFBFFFBLRR\nBFFBFFBRRL\nFFFBFFBLLL\nFFBFBFFRLL\nBBFBFFBLLL\nFBFFFFBRRR\nBFBBBFBLLR\nFBFBFFBRLL\nBFBFBFBRRL\nBFFBFFBLRL\nFFBFFBFRLR\nFBBBFFFLLL\nFBFFFFFRRR\nFFBFFFFLLR\nFBBFFFFRRR\nBFFFBFFRLR\nBBBFFBBRLR\nFFBFBFFLRL\nFFFBFBBLLL\nBBFBFBBRLR\nFFFFFBBLRL\nBFBFFBBLRL\nFBBFFFFLLR\nBBFBBFBLLL\nBFFBBBBRRL\nFBFFBBBRLR\nBBBFBFFLLR\nFFFFFBFRRR\nFFFBBBBRLR\nFFBBBFFLLL\nFFFBBFFRRL\nBFFFBFFLRR\nFBBFFFBLRR\nBFFBBFBRRL\nFBFBBBFRLR\nFBFFFBBRLR\nFBFBFBBLLL\nFFBFBBFLRL\nFFBBBFFRLR\nBBFBFBFRRL\nFBBBFBBLRR\nBFBBBFBLLL\nFFFFBFBRLR\nFFBBFFBRRL\nFBFFBBBLRR\nFFBFFFBLLL\nFBBFFBFLLL\nFFBBBFFRLL\nFBFBFBFLLL\nBBFFBFFRLR\nBFFBFFFLLL\nBBFBFBFRRR\nFBFFFFFRLR\nFBBFBFFRRR\nBBFFFFFLLR\nBFFFFBBLLL\nFFBFBBBLRR\nBFFBBBBLRL\nBFBFBBBRRR\nBFFBFBBRRR\nFBBBFFBLLR\nBBFBFFFRRL\nFFFBBFBLLR\nFBFBFFFLRL\nFBFBBFBRLL\nFFFBBBFRLR\nBBFFBBBLLR\nFBFBBFBRRR\nBFBBFFBRLL\nFBBFBFFRRL\nFBFFFFFRRL\nFFFFFBFLRR\nFBBBFFFLLR\nFBBBFFFRRL\nBBFBFBBLRL\nBFFFBFFLRL\nFFFBBFFLLL\nBBFFBBBRLL\nFBFFBBFLLR\nBFFBFFFRLL\nFBFBBFFRRL\nBFFBBFFRLR\nFFFBBBFLLL\nFBFFFFFLLR\nBFBFBBFRRR\nBFBBFFFLRR\nFFBFBBFRLL\nFBBFBFBLRL\nFFFFBFFRRR\nFFFBFBFLLR\nFFBBFFFRRL\nFBBFBBFLLR\nBFBBBBBRRL\nFFFFBFFRRL\nFBFBBBBLLR\nBBFBBBBLRL\nBFBBBBFRRL\nBBBFFBBLLR\nBFFFBBBRLL\nFBBBFBBLLR\nFFFFBBBLRL\nFBBFBFFLLR\nFFBBBBFRRR\nFFFFFBBLLL\nFFFBFBBRLR\nBFBBFBFRLL\nFFFBBBFLRL\nFBBFBFBRRL\nFFFFBBFLRL\nBFFBBBBRLL\nFFFBFFBRRL\nBFFFBBFRLR\nFFFBFBBRRR\nBFBBFBFLRL\nBFBBBBBRLR\nBBFBFBFLLR\nFFFBBBFRRL\nFBBFFBFRRL\nFBBBBBBLRL\nFFFBFBFLRR\nBFFFBBFLLR\nBFFBBBFRLL\nBBFFBFBLRL\nFFBBBBBLRR\nFBFBFBFRRR\nBFBBFFBLRL\nBFFBBBFLLR\nFFBFBBFLLR\nBFFFBBFRLL\nFFBFBFFLLR\nFBBFBFFLRR\nBFBBBFBRRR\nBBBFFFBRRL\nBFFFBFFRLL\nFFBFBFFLLL\nBBBFBFBRRL\nFFFBFBBRRL\nBFFBFFFRRR\nBFFFBFFRRR\nBFBFBFFLLR\nBBFFFBBLRL\nBFFFBFBLLR\nBFBBBBBLLL\nBFBFFFFRLR\nFBFFFBBRRR\nBBBFFBFRLL\nFFFBBBFRRR\nBFBFBFFRLR\nFFBFBBBLLR\nFBBFFBBRLL\nBBBFFBBLRR\nBBBFBFFRRR\nFFFFBBFRRR\nFBFFBBBRRR\nBBBFFBFRLR\nBBFBFFFLRR\nFBFBFFFRRL\nBBFBFBBLRR\nFBBFBBBLLL\nBFFFBFBLRR\nFBBBBFFLLR\nFBBBBFBLLR\nFBBFFBBRRR\nBBBFFFFRRL\nBFFFBFBLLL\nFBBBFBBRRR\nFFBFBFBRLR\nFFFFBBBRLL\nBFBBBFBLRL\nBBFBFFBLLR\nFBFFBBBLLR\nFFBBBFFRRR\nFBBFBFBRLL\nBBFFFFBLLR\nFFBBFFFLRR\nFBFFFBFLRR\nBBBFFFFLRL\nFFFFBFBLLR\nFFFFFBFLLR\nFFFFBFBLLL\nFBFFBBFRLR\nBBFFBBFLLR\nFBFFFBBLRL\nBBFBBFFLLR\nBFBBFBBRLL\nBFFFFFBRLR\nFBBFBBFRRR\nFFFBFBFRLL\nBBFBBBBLLL\nFFFFBFBLRR\nBBBFFFBRLL\nBBFFBFFLLR\nFFBBBBBRRL\nBFBFBBBLRL\nFFBBBFBRLR\nBFBFBBFRRL\nFFBBBFBRRR\nFFFBFBBRLL\nBBFFBBBRRR\nBFFBFBFLLL\nBFFBBFBLRL\nBFBFFFFLLR\nFFBBFFBRLL\nBBFBFFBRLL\nBBFBFBBLLL\nFBFBBBBLLL\nFBFBBBFRRL\nBFBFFFBLLR\nBBFFBBBLLL\nFBFFBFBRLL\nBFBBFBBRLR\nFFFBFFBRRR\nBBFBFBBRRR\nFBFFBBFRRR\nFFBFFBFRLL\nFBBFFBFLRR\nBBBFBFBLRR\nBBBFFBFRRR\nFBBFBFBLLL\nBFFFFBBLRR\nFBFBFBFRLR\nFBFBBFFRLR\nFBFBFFBRRL\nBFBBFFBRRR\nFBFFBFBLLR\nBFBFBFBLLL\nBBFFFFFRRR\nFBFBFFBLRL\nBBFFBBFLLL\nBFBFFFFLLL\nFFBFBBFRLR\nBBFFBFFLLL\nBFBBFBBLLR\nFFFFFBFRLL\nBBFBBFFRRR\nBFFFBBBRRL\nBBFBBFFLLL\nFBBFFBFLLR\nFFBBBFBLRR\nBFBFBFBRLR\nBBBFFFBRRR\nFBBFFBBLRR\nFFBBBFFLLR\nBFFFFBFLLL\nBFBFFBFLLL\nBBFBFFFLLL\nFBFBFFBLLL\nBBFFBFBRRL\nBBFBBFBLRL\nFBFBFFBLLR\nBBFFBFBLRR\nFBFFBBBRRL\nFFBFFBFRRR\nBBFBBBFLLL\nBFBFBBBRLL")

(defn id [ticket]
  (Integer/parseInt
    (str/join (map {\F 0 \L 0 \R 1 \B 1} ticket))
    2))

(def input-ids (->> input str/split-lines (map id)))

;;p1
(reduce max input-ids)

;;p2
(let [input-pairs   (->> input-ids (sort <) (partition-all 2 1))
      all-ids       (range (Math/pow 2 (+ 7 3)))
      all-neighbors (->> all-ids
                      (sort <)
                      (map (fn [x] [[(dec x) (inc x)] x]))
                      (into {}))]
  (->> input-pairs (map all-neighbors) (remove nil?) (first)))

;too verbose. instead, see https://github.com/nbardiuk/adventofcode/blob/master/2020/src/day05.clj