(ns adventofcode.solution.s17
  (:require [clojure.java.io :as io]))


(defn all-sub-seqs [buckets]
  (loop [b buckets
         results []]
    (if (empty? b)
      results
      (recur (rest b) (conj results b)))))

(defn all-bucket-combinations
  ([buckets input]
   (all-bucket-combinations buckets input []))
  ([buckets input result]
   (if (= input 0)
     [result]
     (if-not (empty? buckets)
       (mapcat
         (fn [[first-bucket & last-buckets]]
           (all-bucket-combinations last-buckets (- input first-bucket) (conj result first-bucket)))
         (all-sub-seqs buckets))))))

(defn starta []
  (println "Starting solution nr. 17a")
  (with-open [rdr (io/reader "resources/17/input.txt")]
    (let [buckets (map #(Integer/parseInt %) (line-seq rdr))
          input 150]
      (println (count (all-bucket-combinations buckets input)))
      )
    ))

(defn startb []
  (println "Starting solution nr. 17b")
  (with-open [rdr (io/reader "resources/17/input.txt")]
    ))
