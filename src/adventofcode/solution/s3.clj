(ns adventofcode.solution.s3
  (:require [clojure.java.io :as io]
            [adventofcode.solution.reading-files :as rf]))

(defn do-step [{:keys [visited] [x y] :pos} cmd]
  (let [new-pos (case cmd
                  \^ [x (inc y)]
                  \v [x (dec y)]
                  \> [(inc x) y]
                  \< [(dec x) y])]
    {:pos     new-pos
     :visited (conj visited new-pos)}))

(defn start []
  (println "Starting solution nr. 3")
  (with-open [rdr (io/reader "resources/3/input.txt")]
    (-> (reduce do-step {:pos     [0 0]
                         :visited #{[0 0]}} (rf/char-seq rdr))
        :visited
        (count)
        (println))))