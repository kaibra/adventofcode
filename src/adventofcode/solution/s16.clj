(ns adventofcode.solution.s16
  (:require [clojure.java.io :as io]))

(defn extract [line name]
  (some-> (re-find (re-pattern (str ".*" name ": (\\d+).*")) line)
          (second)
          (Integer/parseInt)))

(defn parse-sue [result line]
  (conj result
        {:sue-nr      (second (re-find #"^Sue (\d+):.*" line))
         :children    (extract line "children")
         :cats        (extract line "cats")
         :samoyeds    (extract line "samoyeds")
         :pomeranians (extract line "pomeranians")
         :akitas      (extract line "akitas")
         :vizslas     (extract line "vizslas")
         :goldfish    (extract line "goldfish")
         :trees       (extract line "trees")
         :cars        (extract line "cars")
         :perfumes    (extract line "perfumes")}))

(defn to-match-score [match entry]
  (let [score (reduce
                (fn [score [field fval]]
                  (if (or
                        (= fval (get entry field))
                        (nil? (get entry field)))
                    (inc score)
                    score))
                0
                match)]
    {:sue-nr (:sue-nr entry)
     :score  score}))

(defn starta []
  (println "Starting solution nr. 16a")
  (with-open [rdr (io/reader "resources/16/input.txt")]
    (let [sues (reduce parse-sue [] (line-seq rdr))
          match {:children    3
                 :cats        7
                 :samoyeds    2
                 :pomeranians 3
                 :akitas      0
                 :vizslas     0
                 :goldfish    5
                 :trees       3
                 :cars        2
                 :perfumes    1}]
      (->> (map (partial to-match-score match) sues)
           (sort-by :score)
           (last)
           (println)))))
