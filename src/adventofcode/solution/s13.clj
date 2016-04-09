(ns adventofcode.solution.s13
  (:require [clojure.java.io :as io]))

(defn add-happiness-info [infos line]
  (let [[_ guest gain-or-lose units guest-neighbour] (re-find #"^([a-zA-Z]+)\swould\s(gain|lose)\s(\d+)\s.*next\sto\s([a-zA-Z]+).$" line)
        i-units (Integer/parseInt units)]
    (assoc-in infos [guest guest-neighbour]
              (case gain-or-lose
                "gain" i-units
                "lose" (- i-units)))))

(defn all-seating-combinations
  ([h-infos]
   (let [result (all-seating-combinations 0 [] h-infos)]
     (map
       (fn [{:keys [s-combination] :as inp}]
         (let [first-guest (first s-combination)
               last-guest (last s-combination)]
           (update inp :total-happiness + (get-in h-infos [first-guest last-guest] 0) (get-in h-infos [last-guest first-guest] 0))))
       result)))
  ([total-happiness s-combination h-infos]
   (if (= [(last s-combination)] (keys h-infos))
     [{:s-combination   s-combination
       :total-happiness total-happiness}]
     (if (empty? s-combination)
       (all-seating-combinations total-happiness [(first (keys h-infos))] h-infos)
       (let [last-guest (last s-combination)
             new-h-infos (dissoc h-infos last-guest)]
         (mapcat
           (fn [next-guest]
             (let [lg-happ (get-in h-infos [last-guest next-guest] 0)
                   ng-happ (get-in h-infos [next-guest last-guest] 0)]
               (all-seating-combinations (+ total-happiness lg-happ ng-happ)
                                         (conj s-combination next-guest)
                                         new-h-infos)))
           (keys new-h-infos)))))))

(defn starta []
  (println "Starting solution nr. 13a")
  (with-open [rdr (io/reader "resources/13/input.txt")]
    (let [h-infos (reduce add-happiness-info {} (line-seq rdr))]
      (->> (all-seating-combinations h-infos)
           (sort-by :total-happiness)
           (last)
           (println)))))

(defn startb []
  (println "Starting solution nr. 13b")
  (with-open [rdr (io/reader "resources/13/input.txt")]
    (let [h-infos (reduce add-happiness-info {} (line-seq rdr))]
      (->> (all-seating-combinations (merge h-infos {"Me" {}}))
           (sort-by :total-happiness)
           (last)
           (println)))))
