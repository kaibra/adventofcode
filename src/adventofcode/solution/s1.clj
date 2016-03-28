(ns adventofcode.solution.s1
  (:require [clojure.java.io :as io]
            [adventofcode.solution.reading-files :as rf])
  (:import (java.io Reader)))

(defn apply-cmd [floor cmd]
  (case cmd
    \( (inc floor)
    \) (dec floor)))

(defn starta []
  (println "Starting solution nr. 1a")
  (with-open [rdr (io/reader "resources/1/input.txt")]
    (-> (reduce apply-cmd 0 (rf/char-seq rdr))
        (println))))

(defn startb []
  (println "Starting solution nr. 1b")
  (with-open [rdr (io/reader "resources/1/input.txt")]
    (-> (loop [the-seq (rf/char-seq rdr)
               floor 0
               nr 0]
          (if (or
                (< floor 0)
                (empty? the-seq))
            nr
            (recur (rest the-seq) (apply-cmd floor (first the-seq)) (inc nr))))
        (println))))
