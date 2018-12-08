(ns adventofcode.utils)


(defmacro locals-map
  ;; https://gist.github.com/noisesmith/3490f2d3ed98e294e033b002bc2de178
  []
  (into {}
    (for [[sym val] &env]
      [(keyword (name sym)) sym])))