(ns adventofcode.solution.s2
  (:require [clojure.java.io :as io]))

(def measureregex #"^(\d+)x(\d+)x(\d+)$")

(defn calc-ribbon [l w h]
  (+
    (* l w h)
    (min
      (+ l l w w)
      (+ l l h h)
      (+ w w h h))))

(defn calc-feets [{:keys [sum ribbon]} measure]
  (let [[_ ls ws hs] (re-find measureregex measure)
        l (Integer/parseInt ls)
        w (Integer/parseInt ws)
        h (Integer/parseInt hs)
        sidea (* l w)
        sideb (* l h)
        sidec (* w h)
        surface (+ (* 2 sidea) (* 2 sidec) (* 2 sideb))
        extra-paper (min sidea sideb sidec)]
    {:sum (+ sum surface extra-paper)
     :ribbon (+ ribbon (calc-ribbon l w h))}))

(defn start []
  (println "Starting solution nr. 2")
  (with-open [rdr (io/reader "resources/2/input.txt")]
    (-> (reduce calc-feets {:sum    0
                            :ribbon 0} (line-seq rdr))
        (println))))