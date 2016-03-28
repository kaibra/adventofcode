(ns adventofcode.solution.s2
  (:require [clojure.java.io :as io]))

(def measureregex #"^(\d+)x(\d+)x(\d+)$")

(defn calc-feets [sum measure]
  (let [[_ ls ws hs] (re-find measureregex measure)
        l (Integer/parseInt ls)
        w (Integer/parseInt ws)
        h (Integer/parseInt hs)
        sidea (* l w)
        sideb (* l h)
        sidec (* w h)
        surface (+ (* 2 sidea) (* 2 sidec) (* 2 sideb))
        extra-paper (min sidea sideb sidec)]
    (+ sum surface extra-paper)))

(defn starta []
  (println "Starting solution nr. 2a")
  (with-open [rdr (io/reader "resources/2/input.txt")]
    (-> (reduce calc-feets 0 (line-seq rdr))
        (println))))