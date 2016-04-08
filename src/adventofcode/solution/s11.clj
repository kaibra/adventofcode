(ns adventofcode.solution.s11
  (:require [clojure.string :as s]))

(def char-lookup
  {\a \b
   \b \c
   \c \d
   \d \e
   \e \f
   \f \g
   \g \h
   \h \j
   \i \j
   \j \k
   \k \m
   \l \m
   \m \n
   \n \p
   \o \p
   \p \q
   \q \r
   \r \s
   \s \t
   \t \u
   \u \v
   \v \w
   \w \x
   \x \y
   \y \z
   \z \a})

(def pair-regex
  (let [our-alphabet (keys char-lookup)
        all-possible-pairs (map (fn [c] (str c c)) our-alphabet)
        a-pair-match (str "(" (s/join "|" all-possible-pairs) ")")]
    (re-pattern (str ".*" a-pair-match ".*" a-pair-match ".*"))))

(defn all-increasing-straights [our-alphabet]
  (loop [straights []
         left-chars our-alphabet]
    (if (< (count left-chars) 3)
      straights
      (recur
        (conj straights (apply str (take 3 left-chars)))
        (rest left-chars)))))

(def inc-straights-regex
  (let [our-alphabet (keys char-lookup)
        all-inc-straights (all-increasing-straights our-alphabet)]
    (re-pattern (str ".*(" (s/join "|" all-inc-straights) ").*"))))

(defn replacement-char [pwd-char]
  (get char-lookup pwd-char))

(defn contains-two-different-pairs? [pwd]
  (let [result (filter #(not (nil? %)) (re-find pair-regex pwd))]
    (> (count result) 2)))

(defn contains-increasing-straight? [pwd]
  (not (nil? (re-find inc-straights-regex pwd))))

(defn char-replacement [{:keys [result last-repl]} pwd-char]
  (if (or
        (= last-repl \a)
        (= last-repl :none))
    (let [repl-char (replacement-char pwd-char)]
      {:result    (str result repl-char)
       :last-repl repl-char})
    {:result    (str result pwd-char)
     :last-repl last-repl}))

(defn increase [pwd]
  (let [start-value {:result    ""
                     :last-repl :none}
        {:keys [result last-repl]} (reduce char-replacement start-value (reverse pwd))
        ordered-result (apply str (reverse result))]
    (if (= last-repl \a)
      (str "a" ordered-result)
      ordered-result)))

(def invalid-chars #{\i \o \l})

(defn contains-invalid-char? [pwd]
  (or
    (s/includes? pwd "i")
    (s/includes? pwd "o")
    (s/includes? pwd "l")))

(defn valid-pwd? [pwd]
  (and
    (contains-increasing-straight? pwd)
    (not (contains-invalid-char? pwd))
    (contains-two-different-pairs? pwd)))

(defn skip-invalid-chars [pwd] ;does not work if invalid char is z
  (->> (reduce
        (fn [{:keys [result skip?] :as input} next-char]
          (if skip?
            (assoc input :result (str result "a"))
            (if-let [_ (get invalid-chars next-char)]
              {:result (str result (get char-lookup next-char))
               :skip? true}
              (assoc input :result (str result next-char)))))
        {:result ""
         :skip? false}
        pwd)
       :result))

(defn find-next-valid-pwd [input-pwd]
  (loop [pwd input-pwd]
    (let [next-pwd (increase (skip-invalid-chars pwd))]
      (if (valid-pwd? next-pwd)
        next-pwd
        (recur next-pwd)))))

(defn starta []
  (println "Starting solution nr. 11a")
  (let [input "hxbxwxba"]
    (println (find-next-valid-pwd input))))

(defn startb []
  (println "Starting solution nr. 11b")
  (let [input "hxbxxyzz"]
    (println (find-next-valid-pwd input))))

