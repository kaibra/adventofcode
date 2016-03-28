(ns adventofcode.core
  (:require
    [adventofcode.solution.s1 :as sol1]
    [adventofcode.solution.s2 :as sol2]
    [adventofcode.solution.s5 :as sol5]
    [adventofcode.solution.s6 :as sol6]
    )
  (:gen-class))

(defmacro timed! [& body]
  `(let [start# (System/currentTimeMillis)
         result# ~@body
         timetaken# (- (System/currentTimeMillis) start# )]
     (println "Execution time was " timetaken# "ms")
     result#))

(defn -main [& args]
  (timed!
    (cond
      (= (first args) "1a") (sol1/starta)
      (= (first args) "1b") (sol1/startb)
      (= (first args) "2a") (sol2/starta)
      (= (first args) "5") (sol5/start)
      (= (first args) "6") (sol6/start)
      :default (println "nothing to do"))))
