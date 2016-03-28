(ns adventofcode.solution.s1
  (:require [clojure.java.io :as io])
  (:import (java.io Reader)))

(defn char-seq [^Reader rdr]
  (let [c (.read rdr)]
    (if (>= c 0)
      (cons (char c) (lazy-seq (char-seq rdr)))
      [])))

(defn apply-cmd [floor cmd]
  (case cmd
    \( (inc floor)
    \) (dec floor)))

(defn start []
  (println "Starting solution nr. 1")
  (with-open [rdr (io/reader "resources/1/input.txt")]
    (-> (loop [the-seq (char-seq rdr)
               floor 0]
          (if (empty? the-seq)
            floor
            (recur (rest the-seq) (apply-cmd floor (first the-seq)))))
        (println))))
