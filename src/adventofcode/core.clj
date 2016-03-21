(ns adventofcode.core
  (:require
    [adventofcode.solution6 :as sol6]
    [adventofcode.solution5 :as sol5])
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
      (= (first args) "5") (sol5/start)
      (= (first args) "6") (sol6/start)
      :default (println "nothing to do"))))
