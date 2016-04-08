(ns adventofcode.solution.s12
  (:require [clojure.walk :as wlk]
            [clojure.data.json :as json]))

(defn starta []
  (println "Starting solution nr. 12a")
  (let [json (json/read-str (slurp "resources/12/input.txt") :key-fn keyword)
        sum (atom 0)]
    (wlk/prewalk (fn [i]
                   (when (number? i)
                     (swap! sum + i))
                   i) json)
    (println @sum)))

(defn startb []
  (println "Starting solution nr. 12b")
  )

