(ns adventofcode.solution.s8
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))

(defn leading-escape-seq? [the-str]
  (if (s/starts-with? the-str "\\\\")
    "\\\\"
    (if (s/starts-with? the-str "\\\"")
      "\\\""
      (if-let [match (second (re-find #"^(\\x[0-9a-fA-F][0-9a-fA-F]).*$" the-str))]
        match))))

(defn chars-in-mem [line]
  (loop [the-str (subs line 1 (- (count line) 1))
         sum 0]
    (if (empty? the-str)
      sum
      (if-let [leading-seq (leading-escape-seq? the-str)]
        (do
          (println "found escape " leading-seq)
          (recur (subs the-str (count leading-seq) (count the-str)) (inc sum)))
        (recur (subs the-str 1 (count the-str)) (inc sum))))))

(defn add-count-to-sum [sum line]
  (let [string-code (count line)
        nr-of-chars-in-mem (chars-in-mem line)
        result (- string-code
                  nr-of-chars-in-mem)]
    (println line)
    (println "-->" string-code "-" nr-of-chars-in-mem "=" result)
    (println)
    (+ sum result)))

(defn encoded-line [line]
  (-> line
      (s/replace "\\\"" "\\\\\\\"")
      (s/replace "\\x" "\\\\x")
      (#(str "\"\\\"" (subs % 1 (- (count %) 1)) "\\\"\""))))

(defn add-count-to-sum-b [sum line]
  (let [string-code (count line)
        encoded-line (encoded-line line)
        nr-of-ecoded-chars (count encoded-line)
        result (- nr-of-ecoded-chars string-code)]
    (println line)
    (println encoded-line)
    (println "-->" nr-of-ecoded-chars "-" string-code "=" result)
    (println)
    (+ sum result)))

(defn starta []
  (println "Starting solution nr. 8a")
  (with-open [rdr (io/reader "resources/8/input.txt")]
    (let [the-circuit (reduce add-count-to-sum 0 (line-seq rdr))]
      (-> the-circuit (println)))))

(defn startb []
  (println "Starting solution nr. 8b")
  (with-open [rdr (io/reader "resources/8/input.txt")]
    (let [the-circuit (reduce add-count-to-sum-b 0 (line-seq rdr))]
      (-> the-circuit (println)))))
