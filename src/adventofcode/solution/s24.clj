(ns adventofcode.solution.s24
  (:require [clojure.java.io :as io]
            [clojure.math.combinatorics :as combo]))

(defn find-all-combinations [presents part-sum]
  (loop [result []
         element-picked 1]
    (if-not (empty? result)
      result
      (recur
        (->> (combo/combinations presents element-picked)
             (filter #(= (apply + %) part-sum)))
        (inc element-picked)))))

(defn with-qe [c]
  {:combination c
   :qe (apply * c)})

(defn best-sleigh-conf [presents split-nr]
  (let [total-sum (apply + presents)
        all-combs (find-all-combinations presents (/ total-sum split-nr))]
    (println "combs done" (count all-combs))
    (->> all-combs
         (map with-qe)
         (sort-by :qe)
         (first))))

(defn starta []
  (println "Starting solution nr. 24a")
  (with-open [rdr (io/reader "resources/24/input.txt")]
    (let [presents (into [] (map #(Integer/parseInt %) (line-seq rdr)))]
      (-> (best-sleigh-conf presents 3)
          (println)))))

(defn startb []
  (println "Starting solution nr. 24b")
  (with-open [rdr (io/reader "resources/24/input.txt")]
    (let [presents (into [] (map #(Integer/parseInt %) (line-seq rdr)))]
      (-> (best-sleigh-conf presents 4)
          (println)))))
