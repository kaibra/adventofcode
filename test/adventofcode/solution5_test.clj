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