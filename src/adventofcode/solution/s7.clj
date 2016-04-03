(ns adventofcode.solution.s7
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn wire-entry [target-var wire-cmd calc-fn]
  (let [state (atom nil)]
    {:wire-cmd   wire-cmd
     :target-var target-var
     :state      state
     :calc-fn    (fn [circuit]
                   (if-let [current-state @state]
                     current-state
                     (reset! state (calc-fn circuit))))}))

(defn calc-fn [the-fn & args]
  (fn [circuit]
    (let [only-if-not-number (fn [f] (fn [i] (if (number? i) i (f i))))
          entries (map (only-if-not-number #(get circuit %)) args)
          results (map (only-if-not-number #((:calc-fn %) circuit)) entries)]
      (apply the-fn results))))

(defn add-parsed-or [circuit line]
  (if-let [[_ a b out] (re-find #"^([a-z]+)\sOR\s([a-z]+)\s->\s([a-z]+)$" line)]
    (assoc circuit out (wire-entry out line (calc-fn bit-or a b)))
    (throw (RuntimeException. (str "not parseable: " line)))))

(defn add-parsed-and [circuit line]
  (if-let [[_ a b out] (re-find #"^([a-z]+)\sAND\s([a-z]+)\s->\s([a-z]+)$" line)]
    (assoc circuit out (wire-entry out line (calc-fn bit-and a b)))
    (if-let [[_ constant-val a2 out] (re-find #"^(\d+)\sAND\s([a-z]+)\s->\s([a-z]+)$" line)]
      (assoc circuit out (wire-entry out line (calc-fn bit-and (Long/parseLong constant-val) a2)))
      (throw (RuntimeException. (str "not parseable: " line))))))

(defn add-parsed-not [circuit line]
  (if-let [[_ a out] (re-find #"^NOT\s([a-z]+)\s->\s([a-z]+)$" line)]
    (assoc circuit out (wire-entry out line (calc-fn bit-not a)))
    (throw (RuntimeException. (str "not parseable: " line)))))

(defn add-parsed-rshift [circuit line]
  (if-let [[_ a shift-val out] (re-find #"^([a-z]+)\sRSHIFT\s(\d+)\s->\s([a-z]+)$" line)]
    (assoc circuit out (wire-entry out line (calc-fn bit-shift-right a (Long/parseLong shift-val))))
    (throw (RuntimeException. (str "not parseable: " line)))))

(defn add-parsed-lshift [circuit line]
  (if-let [[_ a shift-val out] (re-find #"^([a-z]+)\sLSHIFT\s(\d+)\s->\s([a-z]+)$" line)]
    (assoc circuit out (wire-entry out line (calc-fn bit-shift-left a (Long/parseLong shift-val))))
    (throw (RuntimeException. (str "not parseable: " line)))))

(def assignment-regex #"^(\d+)\s->\s([a-z]+)$")

(defn is-assignment? [line]
  (not (nil? (re-find assignment-regex line))))

(defn add-parsed-assignment [circuit line]
  (let [[_ the-val out] (re-find assignment-regex line)]
    (assoc circuit out (wire-entry out line (constantly (Long/parseLong the-val))))))

(def var-assignment-regex #"^([a-z]+)\s->\s([a-z]+)$")

(defn is-var-assignment? [line]
  (not (nil? (re-find var-assignment-regex line))))

(defn add-parsed-var-assignment [circuit line]
  (let [[_ a out] (re-find var-assignment-regex line)]
    (assoc circuit out (wire-entry out line (calc-fn identity a)))))

(defn add-wire [circuit line]
  (println "add line " line)
  (cond
    (str/includes? line "OR") (add-parsed-or circuit line)
    (str/includes? line "AND") (add-parsed-and circuit line)
    (str/includes? line "NOT") (add-parsed-not circuit line)
    (str/includes? line "RSHIFT") (add-parsed-rshift circuit line)
    (str/includes? line "LSHIFT") (add-parsed-lshift circuit line)
    (is-assignment? line) (add-parsed-assignment circuit line)
    (is-var-assignment? line) (add-parsed-var-assignment circuit line)
    :default (throw (RuntimeException. (str "Not implemented: " line)))))

(defn starta []
  (println "Starting solution nr. 7a")
  (with-open [rdr (io/reader "resources/7/input.txt")]
    (let [the-circuit (reduce add-wire {} (line-seq rdr))]
      (-> ((:calc-fn (get the-circuit "a")) the-circuit)
          (println)))))

(defn startb []
  (println "Starting solution nr. 7b")
  (with-open [rdr (io/reader "resources/7/input.txt")]
    (let [the-circuit (reduce add-wire {} (line-seq rdr))]
      (reset! (get-in the-circuit ["b" :state]) 956)
      (-> ((:calc-fn (get the-circuit "a")) the-circuit)
          (println)))))
