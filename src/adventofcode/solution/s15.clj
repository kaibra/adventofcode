(ns adventofcode.solution.s15
  (:require [clojure.java.io :as io]))

(defn ingredient-props [i-capacity i-durability i-flavor i-texture i-calories]
  {:capacity   i-capacity
   :durability i-durability
   :flavor     i-flavor
   :texture    i-texture
   :calories   i-calories
   })

(defn add-ingredient [ingredients line]
  (let [[_ ingredient-name capacity durability flavor texture calories] (re-find #"^([a-zA-Z]+): capacity (-?\d+), durability (-?\d+), flavor (-?\d+), texture (-?\d+), calories (-?\d+)$" line)
        i-capacity (Integer/parseInt capacity)
        i-durability (Integer/parseInt durability)
        i-flavor (Integer/parseInt flavor)
        i-texture (Integer/parseInt texture)
        i-calories (Integer/parseInt calories)]
    (->> (ingredient-props i-capacity i-durability i-flavor i-texture i-calories)
         (assoc ingredients ingredient-name))))

(defn ingredient-prop-score [ingredients weights ingredient-prop]
  (max
    (reduce
      (fn [score [ingredient i-weight]]
        (let [prop-factor (get-in ingredients [ingredient ingredient-prop])
              ing-prop-score (* prop-factor i-weight)]
          (+ score ing-prop-score)))
      0
      weights)
    0))

(defn recipe-score [ingredients weights]
  (let [ing-properties [:capacity :durability :flavor :texture]
        total-score (->> (map (partial ingredient-prop-score ingredients weights) ing-properties) (apply *))]
    {:score        total-score
     :weights      weights
     :calories500? (= 500 (ingredient-prop-score ingredients weights :calories))}))

(defn generate-all-weight-combinations
  [all-ingredient-names max-val]
  (if (empty? all-ingredient-names)
    [[]]
    (if (> (count all-ingredient-names) 1)
      (map
        #(into {} %)
        (mapcat
          (fn [the-val]
            (map
              (fn [rs]
                (concat [{(first all-ingredient-names) the-val}] rs))
              (generate-all-weight-combinations (rest all-ingredient-names) (- max-val the-val))))
          (range 0 (+ max-val 1))))
      [[{(first all-ingredient-names) max-val}]])))

(defn keep-better-result [{old-score :score :as old-result} {next-score :score :as next-result}]
  (if old-score
    (if (> next-score old-score)
      next-result
      old-result)
    next-result))

(defn keep-better-result-with-500-cals [{old-score :score :as old-result}
                                        {next-cals-500? :calories500? next-score :score :as next-result}]
  (if next-cals-500?
    (if old-score
      (if (> next-score old-score)
        next-result
        old-result)
      next-result)
    old-result))


(defn starta []
  (println "Starting solution nr. 15a")
  (with-open [rdr (io/reader "resources/15/input.txt")]
    (let [ingredients (reduce add-ingredient {} (line-seq rdr))]
      (->> (generate-all-weight-combinations (keys ingredients) 100)
           (map (partial recipe-score ingredients))
           (reduce keep-better-result nil)
           (println)))))

(defn startb []
  (println "Starting solution nr. 15b")
  (with-open [rdr (io/reader "resources/15/input.txt")]
    (let [ingredients (reduce add-ingredient {} (line-seq rdr))]
      (println ingredients)
      (->> (generate-all-weight-combinations (keys ingredients) 100)
           (map (partial recipe-score ingredients))
           (reduce keep-better-result-with-500-cals nil)
           (println)))))