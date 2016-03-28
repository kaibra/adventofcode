(ns adventofcode.solution.s6
  (:require [clojure.java.io :as io]
            [clojure.core.async :as async]))

(def total-width 1000)
(def thread-pool-size 4)
(def chan-size 1000)

(def parsing-regex #"^(turn off|turn on|toggle) (\d{1,3}),(\d{1,3}) through (\d{1,3}),(\d{1,3})$")

(defn tasks [command [x1 y1] [x2 y2]]
  (map
    (fn [h]
      {:cmd         command
       :coordinates (range (+ (* h total-width) y1)
                           (+ (* h total-width) y2 1))})
    (range x1 (+ 1 x2))))

(defn int-tuple [a b]
  [(Integer/parseInt a) (Integer/parseInt b)])

(defn parse-to-cmd-tasks [line]
  (let [[_ command x1 y1 x2 y2] (re-find parsing-regex line)]
    (tasks command (int-tuple x1 y1) (int-tuple x2 y2))))

(defn execute-parallel [worker-count structure {:keys [cmd coordinates]}]
  (try
    (swap! worker-count inc)
    (let [cmd-fn (case cmd
                   "toggle" #(not (get structure %))
                   "turn on" (constantly true)
                   "turn off" (constantly false))]
      (doseq [coordinate coordinates]
        (assoc! structure coordinate (cmd-fn coordinate))))
    (finally
      (swap! worker-count dec))))

(defn wait-for-command-to-be-finished [worker-count work]
  (while (or
           (> (count (.buf work)) 0)
           (> @worker-count 0))
    (Thread/sleep 1)))

(defn nr-of-switched-on-lights [structure]
  (count (filter true? (persistent! structure))))

(defn start []
  (println "Starting solution nr. 6")
  (with-open [rdr (io/reader "resources/6/input.txt")]
    (let [worker-count (atom 0)
          structure (transient (into [] (repeat (* total-width total-width) false)))
          work (async/chan chan-size)]
      (async/pipeline thread-pool-size
                      (async/chan (async/dropping-buffer chan-size))
                      (keep (partial execute-parallel worker-count structure))
                      work)
      (doseq [cmd-taks (map parse-to-cmd-tasks (line-seq rdr))]
        (doseq [single-task cmd-taks]
          (async/>!! work single-task))
        (wait-for-command-to-be-finished worker-count work))
      (println (nr-of-switched-on-lights structure)))))
