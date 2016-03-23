(ns adventofcode.solution6
  (:require [clojure.java.io :as io]
            [clojure.core.async :as async]))

(def total-width 1000)

(def parsing-regex #"^(turn off|turn on|toggle) (\d{1,3}),(\d{1,3}) through (\d{1,3}),(\d{1,3})$")

(defn coordinates [x1 y1 width height]
  (mapcat
    (fn [w]
      (map
        (fn [h]
          [(+ x1 w) (+ y1 h)])
        (range 0 (+ height 1))))
    (range 0 (+ width 1))))

(defn flat-coordinates [command [x1 y1] [x2 y2]]
  (if (> (- x2 x1)
         (- y2 y1))
    (map
      (fn [h]
        {:cmd command
         :coordinates (range (+ (* h total-width) x1)
                             (+ (* h total-width) x2 1))})
      (range y1 (+ 1 y2)))
    (map
      (fn [h]
        {:cmd command
         :coordinates (range (+ (* h total-width) y1)
                             (+ (* h total-width) y2 1))})
      (range x1 (+ 1 x2)))))

(defn parse-operation [line]
  (let [[_ command x1 y1 x2 y2] (re-find parsing-regex line)
        ix1 (Integer/parseInt x1)
        ix2 (Integer/parseInt x2)
        iy1 (Integer/parseInt y1)
        iy2 (Integer/parseInt y2)]
    (flat-coordinates command [ix1 iy1] [ix2 iy2])))

(defn execute-parallel [worker-count structure {:keys [cmd coordinates]}]
  (try
    (swap! worker-count inc)
    (let [lec (case cmd
                "toggle" #(not (get structure %))
                "turn on" (constantly true)
                "turn off" (constantly false))]
      (doseq [coordinate coordinates]
        (assoc! structure coordinate (lec coordinate))))
    (finally
      (swap! worker-count dec))))

(defn start []
  (println "Starting solution nr. 6")
  (with-open [rdr (io/reader "resources/6/input.txt")]
    (let [raw-input (line-seq rdr)
          worker-count (atom 0)
          parsed (map parse-operation raw-input)
          structure (transient (into [] (repeat (* total-width total-width) false)))
          work (async/chan 1000)]
      (async/pipeline 1024 (async/chan (async/dropping-buffer 10)) (keep (partial execute-parallel worker-count structure)) work)
      (doseq [items parsed]
        (doseq [item items]
          (async/>!! work item))
        (while (or
                 (> (count (.buf work)) 0)
                 (> @worker-count 0))
          (Thread/sleep 1)))
      (println
        (count (filter
                 true?
                 (persistent! structure)))))))
