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
    [adventofcode.solution.s11 :as sol11]
    [adventofcode.solution.s12 :as sol12]
    [adventofcode.solution.s13 :as sol13]
    [adventofcode.solution.s14 :as sol14]
    [adventofcode.solution.s15 :as sol15]
    [adventofcode.solution.s16 :as sol16]
    [adventofcode.solution.s17 :as sol17]
    [adventofcode.solution.s18 :as sol18]
    [adventofcode.solution.s19 :as sol19]
    [adventofcode.solution.s20 :as sol20]
    [adventofcode.solution.s21 :as sol21]
    [adventofcode.solution.s22 :as sol22]
    [adventofcode.solution.s23 :as sol23]
    [adventofcode.solution.s24 :as sol24]
    [adventofcode.solution.s25 :as sol25]
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
      (= (first args) "11a") (sol11/starta)
      (= (first args) "11b") (sol11/startb)
      (= (first args) "12a") (sol12/starta)
      (= (first args) "12b") (sol12/startb)
      (= (first args) "13a") (sol13/starta)
      (= (first args) "13b") (sol13/startb)
      (= (first args) "14a") (sol14/starta)
      (= (first args) "14b") (sol14/startb)
      (= (first args) "15a") (sol15/starta)
      (= (first args) "15b") (sol15/startb)
      (= (first args) "16a") (sol16/starta)
      (= (first args) "16b") (sol16/startb)
      (= (first args) "17a") (sol17/starta)
      (= (first args) "17b") (sol17/startb)
      (= (first args) "18a") (sol18/starta)
      (= (first args) "18b") (sol18/startb)
      (= (first args) "19a") (sol19/starta)
      (= (first args) "19b") (sol19/startb)
      (= (first args) "20a") (sol20/starta)
      (= (first args) "20b") (sol20/startb)
      (= (first args) "21a") (sol21/starta)
      (= (first args) "21b") (sol21/startb)
      (= (first args) "22a") (sol22/starta)
      (= (first args) "22b") (sol22/startb)
      (= (first args) "23a") (sol23/starta)
      (= (first args) "23b") (sol23/startb)
      (= (first args) "24a") (sol24/starta)
      (= (first args) "24b") (sol24/startb)
      (= (first args) "25a") (sol25/starta)
      :default (println "nothing to do"))))
