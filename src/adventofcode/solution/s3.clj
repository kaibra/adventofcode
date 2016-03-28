(ns adventofcode.solution.s3
  (:require [clojure.java.io :as io]
            [adventofcode.solution.reading-files :as rf]))

(defn do-step [visited {[x y] :pos} cmd]
  (let [new-pos (case cmd
                  \^ [x (inc y)]
                  \v [x (dec y)]
                  \> [(inc x) y]
                  \< [(dec x) y])]
    (swap! visited #(conj % new-pos))
    {:pos new-pos}))

(defn start []
  (println "Starting solution nr. 3")
  (with-open [rdr (io/reader "resources/3/input.txt")]
    (let [visited (atom #{[0 0]})]
      (reduce (partial do-step visited) {:pos [0 0]} (rf/char-seq rdr))
      (println (count @visited)))))