(ns adventofcode.solution.s21
  (:require [clojure.math.combinatorics :as combo]))

(defn item [i-name cost damage armor]
  {:name   i-name
   :cost   cost
   :damage damage
   :armor  armor})

(defn item-without-name [item]
  (dissoc item :name))

(def weapons
  [(item "Dagger" 8 4 0)
   (item "Shortsword" 10 5 0)
   (item "Warhammer" 25 6 0)
   (item "Longsword" 40 7 0)
   (item "Greataxe" 74 8 0)])

(def armor
  [(item "Leather" 13 0 1)
   (item "Chainmail" 31 0 2)
   (item "Splintmail" 53 0 3)
   (item "Bandedmail" 75 0 4)
   (item "Platemail" 102 0 5)])

(def rings
  [(item "Damage +1" 25 1 0)
   (item "Damage +2" 50 2 0)
   (item "Damage +3" 100 3 0)
   (item "Defense +1" 20 0 1)
   (item "Defense +2" 40 0 2)
   (item "Defense +3" 80 0 3)])

(def all-weapons-choices
  (map (fn [w] [w]) weapons))

(def all-armor-choices
  (conj
    (map (fn [w] [w]) armor)
    []))

(def all-rings-choices
  (mapcat
    (fn [i]
      (combo/combinations rings i))
    (range 0 3)))

(defn join-with [seq1 seq2]
  (mapcat
    (fn [i]
      (map
        #(concat i %)
        seq2))
    seq1))

(def all-player-conf
  (-> all-weapons-choices
      (join-with all-armor-choices)
      (join-with all-rings-choices)))

(defn to-player-conf [player-items]
  (assoc (apply merge-with + (map item-without-name player-items))
    :hit-points 100))

(defn to-game-results [{b-armor :armor b-damage :damage b-hit-points :hit-points} player-items]
  (let [{:keys [armor damage hit-points cost] :as pconf} (to-player-conf player-items)
        player-attack (max (- damage b-armor) 1)
        boss-attack (max (- b-damage armor) 1)
        player-rounds (Math/round (float (/ b-hit-points player-attack)))
        boss-rounds (Math/round (float (/ hit-points boss-attack)))]
    (if (<= player-rounds boss-rounds)
      {:win          :player
       :cost         cost}
      {:win          :boss
       :cost         cost})))

(def the-boss
  {:hit-points 109
   :damage     8
   :armor      2})

(defn starta []
  (println "Starting solution nr. 21a")
  (let [results (map (partial to-game-results the-boss) all-player-conf)]
    (println
      (first
        (sort-by :cost
                 (filter #(= :player (:win %)) results))))))

(defn startb []
  (println "Starting solution nr. 21b")
  (let [results (map (partial to-game-results the-boss) all-player-conf)]
    (println
      (last
        (sort-by :cost
                 (filter #(= :boss (:win %)) results))))))
