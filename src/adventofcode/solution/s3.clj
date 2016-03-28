(ns adventofcode.solution.s3
  (:require [clojure.java.io :as io]
            [adventofcode.solution.reading-files :as rf]))

(defn do-step [visited [x y] cmd]
  (let [new-pos (case cmd
                  \^ [x (inc y)]
                  \v [x (dec y)]
                  \> [(inc x) y]
                  \< [(dec x) y])]
    (swap! visited #(conj % new-pos))
    new-pos))

(defn start-santa [visited start-pos s]
  (reduce (partial do-step visited) start-pos s))

(defn when-even [i cmd]
  (when (even? i) cmd))

(defn when-not-even [i cmd]
  (when-not (even? i) cmd))

(defn starta []
  (println "Starting solution nr. 3a")
  (with-open [rdr (io/reader "resources/3/input.txt")]
    (let [start-pos [0 0]
          visited (atom #{start-pos})]
      (start-santa visited start-pos (rf/char-seq rdr))
      (println (count @visited)))))

(defn startb []
  (println "Starting solution nr. 3b")
  (with-open [rdr (io/reader "resources/3/input.txt")]
    (let [the-input (rf/char-seq rdr)
          start-pos [0 0]
          visited (atom #{start-pos})]
      (start-santa visited start-pos (keep-indexed when-even the-input))
      (start-santa visited start-pos (keep-indexed when-not-even the-input))
      (println (count @visited)))))