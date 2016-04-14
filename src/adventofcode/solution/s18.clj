(ns adventofcode.solution.s18
  (:require [clojure.java.io :as io]))

(defn parse-coordinate-char [coordinate-char]
  (case coordinate-char
    \# 1
    \. 0))

(defn add-coordinate [col-index coordinate-char]
  [col-index (parse-coordinate-char coordinate-char)])

(defn parse-coordinates-line [row-index line]
  [row-index
   (into {}
         (map-indexed add-coordinate line))])

(defn parse-grid [line-s]
  (into {}
        (map-indexed parse-coordinates-line line-s)))


(defn neighbours [the-grid [row col]]
  (let [neighbours [[(- row 1) (- col 1)] [(- row 1) col] [(- row 1) (+ col 1)]
                    [row (+ col 1)]
                    [(+ row 1) (- col 1)] [(+ row 1) col] [(+ row 1) (+ col 1)]
                    [row (- col 1)]]]
    (map
      #(get-in the-grid % 0)
      neighbours)))

(defn new-light-state [new-grid coordinate old-val fixed-corners?]
  (if (and
        (= fixed-corners? :fixed-corners)
        (or
          (= coordinate [0 0])
          (= coordinate [0 99])
          (= coordinate [99 99])
          (= coordinate [99 0])))
    1
    (let [neighbour-lights-on (apply + (neighbours new-grid coordinate))]
      (case old-val
        1 (if (or (= neighbour-lights-on 2) (= neighbour-lights-on 3)) 1 0)
        0 (if (= neighbour-lights-on 3) 1 0)))))

(defn light-iteration [new-grid fixed-corners?]
  (into {}
        (map
          (fn [[row-id col]]
            [row-id
             (into {}
                   (map
                     (fn [[col-id old-val]]
                       [col-id
                        (new-light-state new-grid [row-id col-id] old-val fixed-corners?)])
                     col))])
          new-grid)))

(defn apply-light-iterations [the-grid nr-iterations fixed-corners?]
  (loop [iteration 0
         new-grid the-grid]
    (if (= iteration nr-iterations)
      new-grid
      (recur (inc iteration) (light-iteration new-grid fixed-corners?)))))

(defn light-count [the-grid]
  (apply +
         (map val
              (mapcat val the-grid))))

(defn starta []
  (println "Starting solution nr. 18a")
  (with-open [rdr (io/reader "resources/18/input.txt")]
    (let [the-grid (parse-grid (line-seq rdr))]
      (println (light-count (apply-light-iterations the-grid 100 :no-fixed-corners))))))

(defn startb []
  (println "Starting solution nr. 18b")
  (with-open [rdr (io/reader "resources/18/input.txt")]
    (let [the-grid (-> (parse-grid (line-seq rdr))
                       (assoc-in [0 0] 1)
                       (assoc-in [0 99] 1)
                       (assoc-in [99 99] 1)
                       (assoc-in [99 0] 1))]
      (println (light-count (apply-light-iterations the-grid 100 :fixed-corners)))
      )))
