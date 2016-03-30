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

(defn valid-md5? [byte-seq]
  (and
    (= 0 (get byte-seq 0))
    (= 0 (get byte-seq 1))
    (= 0 (first-four-bits (get byte-seq 2)))))

;iwrupvqb346386
(defn starta []
  (println "Starting solution nr. 4a")
  (let [input "iwrupvqb"]
    (-> (loop [i 0]
          (let [inputwithi (str input i)]
            (if (valid-md5? (md5-bytes inputwithi))
              {:hex   (md5-hex-string inputwithi)
               :input inputwithi}
              (recur (inc i)))))
        (println))))
