(ns adventofcode.solution.s25)


(defn code-for [row column]
  (let [start-code 20151125
        current-code (atom start-code)
        the-result (atom nil)]
    (doseq [r (range 2 (+ (+ column (- row 1)) 1))]
      (doseq [running-column (range 1 (+ r 1))]
        (let [running-row (- r (- running-column 1))
              result (mod (* @current-code 252533) 33554393)]
          (when (and
                  (= running-row row)
                  (= running-column column))
            (reset! the-result result))
          (reset! current-code result))))
    @the-result))

(defn starta []
  (println "Starting solution nr. 25a")
  (let [row 3010,
        column 3019]
    (-> (code-for row column) (println))))