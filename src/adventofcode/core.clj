(ns adventofcode.core
  (:require
    [adventofcode.solution.s1 :as sol1]
    [adventofcode.solution.s2 :as sol2]
    [adventofcode.solution.s3 :as sol3]
    [adventofcode.solution.s4 :as sol4]
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
      (= (first args) "2a") (sol2/start)
      (= (first args) "2b") (sol2/start)
      (= (first args) "3a") (sol3/starta)
      (= (first args) "3b") (sol3/startb)
      (= (first args) "4a") (sol4/starta)
      (= (first args) "4b") (sol4/startb)
      (= (first args) "5a") (sol5/starta)
      (= (first args) "5b") (sol5/startb)
      (= (first args) "6a") (sol6/starta)
      (= (first args) "6b") (sol6/startb)
      :default (println "nothing to do"))))
