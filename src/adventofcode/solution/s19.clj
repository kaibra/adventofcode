(ns adventofcode.solution.s19
  (:require
    [clojure.string :as st]
    [clojure.java.io :as io]
    [clojure.string :as s]))

(defn parse-replacement-rule [replacement-rules line]
  (let [[_ input replacement] (re-find #"^(.*)\s=>\s(.*)$" line)]
    (conj replacement-rules [input replacement])))

(defn build-new-molecule [the-str occurence [input replacement]]
  (loop [str-left ""
         str-right the-str
         nr-occurence 0]
    (let [the-index (st/index-of str-right input)]
      (when-not (nil? the-index)
        (if (= nr-occurence occurence)
          (str str-left (s/replace-first str-right input replacement))
          (recur
            (str str-left (subs str-right 0 (+ the-index (count input))))
            (subs str-right (+ the-index (count input)))
            (inc nr-occurence)))))))

(defn molecules-for-replacement-rule [molecule replacement-rule]
  (loop [results []
         occurence 0]
    (if-let [new-molecule (build-new-molecule molecule occurence replacement-rule)]
      (recur (conj results new-molecule) (inc occurence))
      results)))

(defn starta []
  (println "Starting solution nr. 19a")
  (with-open [rdr (io/reader "resources/19/input.txt")]
    (let [medicine-molecule "CRnCaSiRnBSiRnFArTiBPTiTiBFArPBCaSiThSiRnTiBPBPMgArCaSiRnTiMgArCaSiThCaSiRnFArRnSiRnFArTiTiBFArCaCaSiRnSiThCaCaSiRnMgArFYSiRnFYCaFArSiThCaSiThPBPTiMgArCaPRnSiAlArPBCaCaSiRnFYSiThCaRnFArArCaCaSiRnPBSiRnFArMgYCaCaCaCaSiThCaCaSiAlArCaCaSiRnPBSiAlArBCaCaCaCaSiThCaPBSiThPBPBCaSiRnFYFArSiThCaSiRnFArBCaCaSiRnFYFArSiThCaPBSiThCaSiRnPMgArRnFArPTiBCaPRnFArCaCaCaCaSiRnCaCaSiRnFYFArFArBCaSiThFArThSiThSiRnTiRnPMgArFArCaSiThCaPBCaSiRnBFArCaCaPRnCaCaPMgArSiRnFYFArCaSiThRnPBPMgAr"
          replacement-rules (reduce parse-replacement-rule [] (line-seq rdr))]
      (-> (mapcat (partial molecules-for-replacement-rule medicine-molecule) replacement-rules)
          (distinct)
          (count)
          (println)))))
