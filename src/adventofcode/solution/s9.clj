(ns adventofcode.solution.s9
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))

(def distance-regex #"^([a-zA-Z]+)\sto\s([a-zA-Z]+)\s=\s(\d+)$")

(defn parse-distance [result line]
  (let [[_ start end distance] (re-find distance-regex line)
        num-distance (Integer/parseInt distance)]
    (-> result
        (assoc-in [start end] num-distance)
        (assoc-in [end start] num-distance))))

(defn visited [the-map target]
  (->> (dissoc the-map target)
       (keep (fn [[k v]]
               (let [new-v (dissoc v target)]
                 (when-not (empty? new-v)
                   [k new-v]))))
       (into {})))

(defn all-routes
  ([locations]
   (all-routes 0 [] locations))
  ([total-dist route locations]
   (if (empty? locations)
     [{:route route
       :total-dist total-dist}]
     (if (empty? route)
       (mapcat
         (fn [[start _]]
           (all-routes total-dist [start] locations))
         locations)
       (mapcat
         (fn [[target dist]]
           (all-routes (+ total-dist dist) (conj route target) (visited locations (last route))))
         (get locations (last route)))))))

(defn starta []
  (println "Starting solution nr. 9a")
  (with-open [rdr (io/reader "resources/9/input.txt")]
    (let [locations (reduce parse-distance {} (line-seq rdr))]
      (->> (all-routes locations)
           (sort-by :total-dist)
           (first)
           (println)))))

(defn startb []
  (println "Starting solution nr. 9b")
  (with-open [rdr (io/reader "resources/9/input.txt")]
    (let [locations (reduce parse-distance {} (line-seq rdr))]
      (->> (all-routes locations)
           (sort-by :total-dist)
           (last)
           (println)))))
