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

(defn next-shortest [target-distances]
  (first (sort-by val target-distances)))

(defn visited [the-map target]
  (->> (dissoc the-map target)
       (keep (fn [[k v]]
               (let [new-v (dissoc v target)]
                 (when-not (empty? new-v)
                   [k new-v]))))
       (into {})))

(defn determine-shortest-for-start [locations start]
  (loop [route [start]
         locs locations
         total-dist 0]
    (if (empty? locs)
      {:route      route
       :total-dist total-dist}
      (let [[next dist] (next-shortest (get locs (last route)))]
        (recur
          (conj route next)
          (visited locs (last route))
          (+ total-dist dist))))))


(defn determine-shortest [locations]
  (->> (map (partial determine-shortest-for-start locations) (keys locations))
       (sort-by :total-dist)
       (first)))

; not 361
(defn starta []
  (println "Starting solution nr. 9a")
  (with-open [rdr (io/reader "resources/9/input.txt")]
    (let [the-circuit (reduce parse-distance {} (line-seq rdr))
          the-route (determine-shortest the-circuit)]
      (->> the-route
           (println)))))
