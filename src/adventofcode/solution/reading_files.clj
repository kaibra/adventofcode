(ns adventofcode.solution.reading-files
  (:import (java.io Reader)))

(defn char-seq [^Reader rdr]
  (let [c (.read rdr)]
    (if (>= c 0)
      (cons (char c) (lazy-seq (char-seq rdr)))
      [])))