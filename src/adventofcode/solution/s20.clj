(ns adventofcode.solution.s20)

(defn starta []
  (println "Starting solution nr. 20a")
  (let [presents-limit 34000000
        a-guess-max 1000000
        sums (transient (into [] (repeat a-guess-max 0)))]
    (doseq [elve (range 1 a-guess-max)]
      (doseq [house-for-elve (range elve a-guess-max elve)]
        (let [old-val (get sums house-for-elve)]
          (assoc! sums house-for-elve (+ old-val (* 10 elve))))))

    (loop [h 1]
      (if (> (get sums h) presents-limit)
        (println h)
        (recur (inc h))))))

(defn startb []
  (println "Starting solution nr. 20b")
  (let [presents-limit 34000000
        a-guess-max 1000000
        sums (transient (into [] (repeat a-guess-max 0)))]
    (doseq [elve (range 1 a-guess-max)]
      (doseq [house-for-elve (range elve (min a-guess-max (* 50 elve)) elve)]
        (let [old-val (get sums house-for-elve)]
          (assoc! sums house-for-elve (+ old-val (* 11 elve))))))

    (loop [h 1]
      (if (> (get sums h) presents-limit)
        (println h)
        (recur (inc h))))))
