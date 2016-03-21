(ns adventofcode.solution5
  (:require [clojure.java.io :as io]))

(defn is-vowel? [c]
  (not
    (nil?
      (get #{\a \e \i \o \u} c))))

(defn at-least-three-vowels [i]
  (let [vowels (filter is-vowel? (seq i))]
    (>= (count vowels) 3)))

(defn at-least-one-letter-twice-in-a-row
  ([i]
   (at-least-one-letter-twice-in-a-row (first i) (rest i)))
  ([p i]
   (if (empty? i)
     false
     (if (= p (first i))
       true
       (at-least-one-letter-twice-in-a-row (first i) (rest i))))))


(defn start []
  (with-open [rdr (io/reader "resources/5/input.txt")]
    (->> (line-seq rdr)
         (filter at-least-three-vowels)
         (filter at-least-one-letter-twice-in-a-row)

         (count)
         (println))))