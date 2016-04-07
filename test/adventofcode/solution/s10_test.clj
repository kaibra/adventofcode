(ns adventofcode.solution.s10-test
  (:require [clojure.test :refer :all]
            [adventofcode.solution.s10 :as sol10]))

(deftest parsing
  (testing "should parse lines"
    (is (= ["11" "22" "33"]
           (sol10/parse-pairs-of-numbers "112233")))
    (is (= ["1" "2" "1" "22" "3" "8" "3"]
           (sol10/parse-pairs-of-numbers "12122383")))))

(deftest creating-new-lines
  (testing "should build new look and say line"
    (is (= "41"
           (sol10/new-look-and-say-str "1111")))
    (is (= "22"
           (sol10/new-look-and-say-str "22")))
    (is (= "35"
           (sol10/new-look-and-say-str "555")))
    (is (= "129"
           (sol10/new-look-and-say-str "999999999999")))))


(deftest look-and-say
  (testing "should look and say"
    (is (= "111"
           (sol10/look-and-say "11111111111")))
    (is (= "111211"
           (sol10/look-and-say "121")))
    (is (= "111221"
           (sol10/look-and-say "1211")))
    (is (= "312211"
           (sol10/look-and-say "111221")))))


(deftest split-line
  (testing "leading split part"
    (is (= "1122"
           (sol10/leading-splitable-part "11223" 4)))
    (is (= "11222222222"
           (sol10/leading-splitable-part "112222222223" 4)))
    (is (= "113333333333"
           (sol10/leading-splitable-part "113333333333" 4))))

  (testing "should split the line without breaking numbers"
    (is (= ["1122"]
           (sol10/split-line "1122" 4)))
    (is (= ["1122" "33"]
           (sol10/split-line "112233" 4)))
    (is (= ["1122" "3344"]
           (sol10/split-line "11223344" 4)))
    (is (= ["1122" "33444444444444"]
           (sol10/split-line "112233444444444444" 4)))
    (is (= ["1122" "33444444444444" "55"]
           (sol10/split-line "11223344444444444455" 4)))
    (is (= ["1122" "33444444444444" "5555"]
           (sol10/split-line "1122334444444444445555" 4)))
    (is (= ["1122" "33444444444444" "5555" "66"]
           (sol10/split-line "112233444444444444555566" 4)))
    (is (= ["1122" "33444444444444" "5555" "6678"]
           (sol10/split-line "11223344444444444455556678" 4)))))
