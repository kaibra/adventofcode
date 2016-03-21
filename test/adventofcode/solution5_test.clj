(ns adventofcode.solution5-test
  (:require
    [clojure.test :refer :all]
    [adventofcode.solution5 :as sol]))


(deftest vowels
  (testing ""
    (is (= true
           (sol/at-least-three-vowels "aei")))
    (is (= true
           (sol/at-least-three-vowels "xazegov")))
    (is (= true
           (sol/at-least-three-vowels "aeiouaeiouaeiou")))
    (is (= false
           (sol/at-least-three-vowels "x")))
    (is (= false
           (sol/at-least-three-vowels "ae")))))

(deftest letter-twice
  (testing ""
    (is (= true
           (sol/at-least-one-letter-twice-in-a-row "aa")))
    (is (= false
           (sol/at-least-one-letter-twice-in-a-row "aba")))
    (is (= true
           (sol/at-least-one-letter-twice-in-a-row "efeabba")))
    (is (= false
           (sol/at-least-one-letter-twice-in-a-row "efeabatzurewqbanem")))
    (is (= true
           (sol/at-least-one-letter-twice-in-a-row "efeabatzzurewqbanem")))
    ))

(deftest no-special-words
  (testing ""
    (is (= true
           (sol/no-special-words "rrrrrrrrr")))
    (is (= true
           (sol/no-special-words "aaaaaaaaaaa")))
    (is (= false
           (sol/no-special-words "rrrxyrrrrrr")))
    (is (= false
           (sol/no-special-words "rrrpqrrrrrr")))
    (is (= false
           (sol/no-special-words "rrrcdrrrrrr")))
    (is (= false
           (sol/no-special-words "rrrabrrrrrr")))))
