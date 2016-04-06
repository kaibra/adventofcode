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
        (recur (subs the-str (count leading-seq) (count the-str)) (inc sum))
        (recur (subs the-str 1 (count the-str)) (inc sum))))))

(defn add-totalchars-minus-memchars [sum line]
  (let [string-code (count line)
        nr-of-chars-in-mem (chars-in-mem line)]
    (+ sum (- string-code nr-of-chars-in-mem))))

(defn escaped [leading-seq]
  (cond
    (= leading-seq "\\\"") "\\\\\\\""
    (= leading-seq "\\\\") "\\\\\\\\"
    (s/starts-with? leading-seq "\\x") (str "\\\\x" (subs leading-seq 2 (count leading-seq)))))

(defn encoded-line [line]
  (-> (loop [the-str (subs line 1 (- (count line) 1))
             encoded ""]
        (if (empty? the-str)
          encoded
          (if-let [leading-seq (leading-escape-seq? the-str)]
            (recur (subs the-str (count leading-seq) (count the-str)) (str encoded (escaped leading-seq)))
            (recur (subs the-str 1 (count the-str)) (str encoded (first the-str))))))
      (#(str "\"\\\"" % "\\\"\""))))

(defn add-totalencoded-minus-totalchars [sum line]
  (let [string-code (count line)
        encoded-line (encoded-line line)
        nr-of-ecoded-chars (count encoded-line)]
    (+ sum (- nr-of-ecoded-chars string-code))))

(defn starta []
  (println "Starting solution nr. 8a")
  (with-open [rdr (io/reader "resources/8/input.txt")]
    (-> (reduce add-totalchars-minus-memchars 0 (line-seq rdr))
        (println))))

(defn startb []
  (println "Starting solution nr. 8b")
  (with-open [rdr (io/reader "resources/8/input.txt")]
    (-> (reduce add-totalencoded-minus-totalchars 0 (line-seq rdr))
        (println))))
