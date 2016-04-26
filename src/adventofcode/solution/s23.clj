(ns adventofcode.solution.s23
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn increase-position [registers]
  (update registers :pos inc))

(defn parse-hlf [line]
  (let [[_ register] (re-find #"^hlf ([ab])$" line)]
    (fn [registers]
      (-> registers
          (update register / 2)
          (increase-position)))))

(defn parse-tpl [line]
  (let [[_ register] (re-find #"^tpl ([ab])$" line)]
    (fn [registers]
      (-> registers
          (update register * 3)
          (increase-position)))))

(defn parse-inc [line]
  (let [[_ register] (re-find #"^inc ([ab])$" line)]
    (fn [registers]
      (-> registers
          (update register inc)
          (increase-position)))))

(defn parse-jmp [line]
  (let [[_ offset] (re-find #"^jmp ([\+\-]?\d+)$" line)]
    (fn [registers]
      (update registers :pos + (Integer/parseInt offset)))))

(defn parse-jie [line]
  (let [[_ register offset] (re-find #"^jie ([ab]), ([\+\-]?\d+)$" line)]
    (fn [registers]
      (if (even? (get registers register))
        (update registers :pos + (Integer/parseInt offset))
        (increase-position registers)))))

(defn parse-jio [line]
  (let [[_ register offset] (re-find #"^jio ([ab]), ([\+\-]?\d+)$" line)]
    (fn [registers]
      (if (= (get registers register) 1)
        (update registers :pos + (Integer/parseInt offset))
        (increase-position registers)))))

(defn parse-instruction [line]
  (cond
    (str/starts-with? line "hlf") (parse-hlf line)
    (str/starts-with? line "tpl") (parse-tpl line)
    (str/starts-with? line "inc") (parse-inc line)
    (str/starts-with? line "jmp") (parse-jmp line)
    (str/starts-with? line "jie") (parse-jie line)
    (str/starts-with? line "jio") (parse-jio line)))

(defn starta []
  (println "Starting solution nr. 23a")
  (with-open [rdr (io/reader "resources/23/input.txt")]
    (let [instructions (into [] (map parse-instruction (line-seq rdr)))]
      (loop [registeres {"a" 0
                         "b" 0
                         :pos 0}
             run 1]
        (let [current-pos (:pos registeres)]
          (println run "  " current-pos "  " registeres)
          (if (and
                (>= current-pos 0)
                (< current-pos (count instructions)))
            (recur ((get instructions current-pos) registeres) (inc run))
            (println "End: " registeres)))))))
