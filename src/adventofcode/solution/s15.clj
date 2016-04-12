(ns adventofcode.solution.s15
  (:require [clojure.java.io :as io]
            [clojure.core.async :as async]))

(defn ingredient-props [i-capacity i-durability i-flavor i-texture i-calories]
  {:capacity   i-capacity
   :durability i-durability
   :flavor     i-flavor
   :texture    i-texture
   ;:calories   i-calories
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
  (let [ing-properties (keys (ingredient-props 0 0 0 0 0))]
    (-> (reduce
          (fn [score ingredient-prop]
            (let [prop-score (ingredient-prop-score ingredients weights ingredient-prop)]
              (* score prop-score)))
          1
          ing-properties)
        ((fn [result]
           {:score   result
            :weights weights})))))

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


(defn starta []
  (println "Starting solution nr. 15a")
  (with-open [rdr (io/reader "resources/15/input.txt")]
    (let [ingredients (reduce add-ingredient {} (line-seq rdr))
          recipe-fn (partial recipe-score ingredients)
          thread-pool-size 4
          out-chan (async/chan 1000000)
          work-chan (async/chan (* thread-pool-size 2))
          elements-put (atom 0)
          best-result (atom nil)
          all-weight-combinations (generate-all-weight-combinations (keys ingredients) 100)]
      (async/pipeline thread-pool-size out-chan (map recipe-fn) work-chan)

      (doseq [w-comb all-weight-combinations]
        (swap! elements-put inc)
        (println "put" w-comb)
        (async/>!! work-chan w-comb))

      (doseq [r (range 0 @elements-put)]
        (let [{next-score :score :as next-result} (async/<!! out-chan)]
          (swap! best-result (fn [{old-score :score :as old-result}]
                               (if old-score
                                 (if (> next-score old-score)
                                   next-result
                                   old-result)
                                 next-result)))))

      (println @best-result))
    ))