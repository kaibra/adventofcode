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

(defn starta []
  (println "Starting solution nr. 1a")
  (with-open [rdr (io/reader "resources/1/input.txt")]
    (-> (reduce apply-cmd 0 (char-seq rdr))
        (println))))

(defn startb []
  (println "Starting solution nr. 1b")
  (with-open [rdr (io/reader "resources/1/input.txt")]
    (-> (loop [the-seq (char-seq rdr)
               floor 0
               nr 0]
          (if (or
                (< floor 0)
                (empty? the-seq))
            nr
            (recur (rest the-seq) (apply-cmd floor (first the-seq)) (inc nr))))
        (println))))
