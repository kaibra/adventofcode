(ns adventofcode.core
  (:require
    [adventofcode.solution.s1 :as sol1]
    [adventofcode.solution.s2 :as sol2]
    [adventofcode.solution.s3 :as sol3]
    [adventofcode.solution.s4 :as sol4]
    [adventofcode.solution.s5 :as sol5]
    [adventofcode.solution.s6 :as sol6]
    [adventofcode.solution.s7 :as sol7]
    [adventofcode.solution.s8 :as sol8]
    [adventofcode.solution.s9 :as sol9]
    [adventofcode.solution.s10 :as sol10]
    )
  (:gen-class))

(defmacro timed! [& body]
  `(let [start# (System/currentTimeMillis)
         result# ~@body
         timetaken# (- (System/currentTimeMillis) start#)]
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
      (= (first args) "7a") (sol7/starta)
      (= (first args) "7b") (sol7/startb)
      (= (first args) "8a") (sol8/starta)
      (= (first args) "8b") (sol8/startb)
      (= (first args) "9a") (sol9/starta)
      (= (first args) "9b") (sol9/startb)
      (= (first args) "10a") (sol10/starta)
      (= (first args) "10b") (sol10/startb)
      :default (println "nothing to do"))))
