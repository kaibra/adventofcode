(ns adventofcode.solution.s5
  (:require [clojure.java.io :as io]
            [clojure.string :as cstr]))

(defn is-vowel? [c]
  (not
    (nil?
      (get #{\a \e \i \o \u} c))))

(defn at-least-three-vowels [i]
  (let [vowels (filter is-vowel? (seq i))]
    (>= (count vowels) 3)))

(defn at-least-one-letter-twice-in-a-row [i]
  (loop [the-rest (rest i)
         p (first i)]
    (if (empty? the-rest)
      false
      (if (= p (first the-rest))
        true
        (recur (rest the-rest) (first the-rest))))))

(defn no-special-words [i]
  (not
    (or
      (cstr/includes? i "ab")
      (cstr/includes? i "cd")
      (cstr/includes? i "pq")
      (cstr/includes? i "xy"))))

(defn add-pair [result iseq]
  (if (> (count iseq) 1)
    (conj result (str (first iseq) (second iseq)))
    result))

(defn calc-pairs [i]
  (loop [iseq i
         result #{}]
    (if (empty? iseq)
      result
      (recur (rest iseq) (add-pair result iseq)))))

(defn two-pairs-of-any-letters [i]
  (loop [all-pairs (calc-pairs i)]
    (if (empty? all-pairs)
      false
      (or
        (not (nil?
               (re-find (re-pattern (str ".*" (first all-pairs) ".*" (first all-pairs) ".*")) i)))
        (recur (rest all-pairs))))))

(defn one-repeating-letter-with-sep [i]
  (loop [the-seq i]
    (if (empty? the-seq)
      false
      (or
        (not (nil?
               (re-find (re-pattern (str "^" (first the-seq) "." (first the-seq) ".*")) (apply str the-seq))))
        (recur (rest the-seq))))))

(defn starta []
  (println "Starting solution nr. 5a")
  (with-open [rdr (io/reader "resources/5/input.txt")]
    (->> (line-seq rdr)
         (filter at-least-three-vowels)
         (filter at-least-one-letter-twice-in-a-row)
         (filter no-special-words)
         (count)
         (println))))

(defn startb []
  (println "Starting solution nr. 5b")
  (with-open [rdr (io/reader "resources/5/input.txt")]
    (->> (line-seq rdr)
         (filter two-pairs-of-any-letters)
         (filter one-repeating-letter-with-sep)
         (count)
         (println))))
