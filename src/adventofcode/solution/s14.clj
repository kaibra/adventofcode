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
        ;(println "(+ (* (+ " nr-full-kms " 1) (* " km-sec " " km ")) (* " last-started " " km "))")
        (+ (* (+ nr-full-kms 1) (* km-sec km)) (* last-started km))))))

(defn add-reindeer [reindeers line]
  (let [[_ reindeer km km-sec rest-sec] (re-find #"^([a-zA-Z]+)\scan\sfly\s(\d+).*for\s(\d+)\s.*for\s(\d+)\sseconds.$" line)
        i-km-sec (Integer/parseInt km-sec)
        i-rest-sec (Integer/parseInt rest-sec)
        i-km (Integer/parseInt km)]
    (assoc reindeers
      reindeer (km-calc-fn i-km i-km-sec i-rest-sec))))

(defn starta []
  (println "Starting solution nr. 14a")
  (with-open [rdr (io/reader "resources/14/input.txt")]
    (let [reindeers (reduce add-reindeer {} (line-seq rdr))
          duration 2503]
      (->> (into {} (map (fn [[r km-fn]] [r (km-fn duration)]) reindeers))
           (sort-by val)
           (last)
           (println)))))