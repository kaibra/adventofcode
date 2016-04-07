(ns adventofcode.solution.s10
  (:require [clojure.core.async :as async]))

(defn parse-pairs-of-numbers [line]
  (let [extract-leading #"^(1+|2+|3+|4+|5+|6+|7+|8+|9+).*$"]
    (loop [s line
           result []]
      (if (empty? s)
        result
        (let [leading (second (re-find extract-leading s))]
          (recur
            (subs s (count leading) (count s))
            (conj result leading)))))))

(defn new-look-and-say-str [line]
  (let [the-char (first line)]
    (str (count line) the-char)))

(defn look-and-say [line]
  (->> (parse-pairs-of-numbers line)
       (map new-look-and-say-str)
       (apply str)))

(defn leading-splitable-part [line size]
  (let [split-char (get line (- size 1))]
    (loop [pos size]
      (if-not (= split-char (get line pos))
        (subs line 0 pos)
        (recur (inc pos))))))

(defn split-line [line size]
  (when (> (count line) 0)
    (if (<= (count line) size)
      [line]
      (if-let [leading-part (leading-splitable-part line size)]
        (concat
          [leading-part]
          (split-line (subs line (count leading-part)) size))))))

(defn gather-result [result-chan nr-results]
  (loop [result []
         nr nr-results]
    (if (= 0 nr)
      (apply str result)
      (recur (conj result (async/<!! result-chan)) (dec nr)))))

(defn calc-new-result [result threads work result-chan]
  (let [;split-size (int (/ (count result) threads))
        split-size 10000
        splitted (split-line result split-size)
        nr-results (count splitted)]
    (doseq [line splitted]
      (async/>!! work line))
    (gather-result result-chan nr-results)))

(defn start-calc [iterations]
  (let [threads 4
        work (async/chan 1000)
        result-chan (async/chan 1000)]
    (async/pipeline threads result-chan (map look-and-say) work)
    (loop [result "3113322113"
           i iterations]
      (println "i: " i)
      (if (= i 0)
        (println (count result))
        (recur
          (calc-new-result result threads work result-chan)
          (dec i))))))

(defn starta []
  (println "Starting solution nr. 10a")
  (start-calc 40))

(defn startb []
  (println "Starting solution nr. 10b")
  (start-calc 50))
