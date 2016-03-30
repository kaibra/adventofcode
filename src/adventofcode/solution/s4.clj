(ns adventofcode.solution.s4)

(defn md5-bytes [input]
  (let [ib (.getBytes input "UTF-8")
        hb (.digest (java.security.MessageDigest/getInstance "MD5") ib)]
    hb))

(defn md5-hex-string [input]
  (apply str
         (map #(format "%02x" %) (md5-bytes input))))

(defn first-four-bits [b]
  (bit-shift-right b 4))

(defn starts-with-5-zeros? [byte-seq]
  (and
    (= 0 (get byte-seq 0))
    (= 0 (get byte-seq 1))
    (= 0 (first-four-bits (get byte-seq 2)))))

(defn starts-with-6-zeros? [byte-seq]
  (and
    (= 0 (get byte-seq 0))
    (= 0 (get byte-seq 1))
    (= 0 (get byte-seq 2))))

(def input "iwrupvqb")

;iwrupvqb346386
(defn starta []
  (println "Starting solution nr. 4a")
  (-> (loop [i 0]
        (let [inputwithi (str input i)]
          (if (starts-with-5-zeros? (md5-bytes inputwithi))
            {:hex   (md5-hex-string inputwithi)
             :input inputwithi}
            (recur (inc i)))))
      (println)))

(defn startb []
  (println "Starting solution nr. 4b")
  (-> (loop [i 0]
        (let [inputwithi (str input i)]
          (if (starts-with-6-zeros? (md5-bytes inputwithi))
            {:hex   (md5-hex-string inputwithi)
             :input inputwithi}
            (recur (inc i)))))
      (println)))
