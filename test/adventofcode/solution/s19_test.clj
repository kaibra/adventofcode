(ns adventofcode.solution.s19-test
  (:require [clojure.test :refer :all]
            [adventofcode.solution.s19 :as sol19]))


(deftest replacing-occurences-in-strings
  (testing "should replace occurence of string"
    (is (= "ABA"
           (sol19/build-new-molecule "AAA" 1 ["A" "B"])))
    (is (= "AAB"
           (sol19/build-new-molecule "AAA" 2 ["A" "B"])))
    (is (= nil
           (sol19/build-new-molecule "AAA" 3 ["A" "B"])))
    (is (= "abcdeabCDe"
           (sol19/build-new-molecule "abcdeabcde" 1 ["cd" "CD"]))))

  (testing "should replace occurence of long string"
    (let [the-long-string "wiufnbqwxIUfewunboUbqwxIUfBWfbbqwxIUfob"
          long-str-replacement ["bqwxIUf" "-----"]]
      (is (= "wiufn-----ewunboUbqwxIUfBWfbbqwxIUfob"
             (sol19/build-new-molecule the-long-string 0 long-str-replacement)))
      (is (= "wiufnbqwxIUfewunboU-----BWfbbqwxIUfob"
             (sol19/build-new-molecule the-long-string 1 long-str-replacement)))
      (is (= "wiufnbqwxIUfewunboUbqwxIUfBWfb-----ob"
             (sol19/build-new-molecule the-long-string 2 long-str-replacement)))
      (is (= nil
             (sol19/build-new-molecule the-long-string 3 long-str-replacement))))))

(deftest molecules-for-replacement-rule
  (testing "should build all possible molecules for replacement rules"
    (is (= ["HOOH" "HOHO"]
           (sol19/molecules-for-replacement-rule "HOH" ["H" "HO"])))
    (is (= ["OHOH" "HOOH"]
           (sol19/molecules-for-replacement-rule "HOH" ["H" "OH"])))
    (is (= ["HHHH"]
           (sol19/molecules-for-replacement-rule "HOH" ["O" "HH"])))))


(deftest a-random-result
  (testing "should return a radnom result"
    (is (= false
           (nil? (get #{"A" "B" "C"}
                      (sol19/rand-result ["A" "B" "C"])))))))

(deftest a-random-path
  (testing "should find a random path"
    (let [result (:molecule (sol19/random-single-path [["A" "B"] ["A" "C"]] 1 "A"))]
      (is (or (= "B" result)
              (= "C" result))))))