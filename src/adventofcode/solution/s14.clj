(ns adventofcode.solution.s14
  (:require [clojure.java.io :as io]))

(defn km-calc-fn [km km-sec rest-sec]
  (fn [sec]
    (if (<= sec km-sec)
      (* sec km)
      (let [sec-without-first-full-kms (- sec km-sec)
            one-full-kms-period (+ rest-sec km-sec)
            nr-full-kms (int (/ sec-without-first-full-kms one-full-kms-period))
            last-started (max (- (mod sec-without-first-full-kms one-full-kms-period) rest-sec) 0)]
        (+ (* (+ nr-full-kms 1) (* km-sec km)) (* last-started km))))))

(defn add-reindeer [reindeers line]
  (let [[_ reindeer km km-sec rest-sec] (re-find #"^([a-zA-Z]+)\scan\sfly\s(\d+).*for\s(\d+)\s.*for\s(\d+)\sseconds.$" line)
        i-km-sec (Integer/parseInt km-sec)
        i-rest-sec (Integer/parseInt rest-sec)
        i-km (Integer/parseInt km)]
    (assoc reindeers
      reindeer (km-calc-fn i-km i-km-sec i-rest-sec))))

(defn current-leads [reindeers sec]
  (let [reindeers-with-scores (into {} (map (fn [[r km-fn]] [r (km-fn sec)]) reindeers))
        [_ highest-score] (->> reindeers-with-scores
                               (sort-by val)
                               (last))]
    (filter (fn [[_ r-s]] (= r-s highest-score)) reindeers-with-scores)))

(defn starta []
  (println "Starting solution nr. 14a")
  (with-open [rdr (io/reader "resources/14/input.txt")]
    (let [reindeers (reduce add-reindeer {} (line-seq rdr))
          duration 2503]
      (->> (current-leads reindeers duration)
           (first)
           (println)))))

;not 1057 too low
;not 1058 too low
(defn inc-lead-scores [reindeer-scores leads]
  (reduce
    (fn [scores [lname _]]
      (update scores lname inc))
    reindeer-scores
    leads))

(defn startb []
  (println "Starting solution nr. 14b")
  (with-open [rdr (io/reader "resources/14/input.txt")]
    (let [reindeers (reduce add-reindeer {} (line-seq rdr))
          all-reindeer-names (keys reindeers)
          duration 2503]
      (loop [i 1
             reindeer-scores (into {} (map (fn [n] [n 0]) all-reindeer-names))]
        (let [leads (current-leads reindeers i)
              new-scores (inc-lead-scores reindeer-scores leads)]
          (if (= i duration)
            (println (->> new-scores
                          (sort-by val)
                          (last)))
            (recur (inc i) new-scores)))))))
