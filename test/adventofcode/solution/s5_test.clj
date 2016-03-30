(ns adventofcode.solution.s5-test
  (:require
    [clojure.test :refer :all]
    [adventofcode.solution.s5 :as sol]))


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


(deftest two-pairs-of-any-letters
  (testing "not containing two pairs"
    (is (= false
           (sol/two-pairs-of-any-letters "aaa")
           (sol/two-pairs-of-any-letters "xyxz"))))
  (testing "containing two pairs"
    (is (= true
           (sol/two-pairs-of-any-letters "aaaa")
           (sol/two-pairs-of-any-letters "xyxy")
           (sol/two-pairs-of-any-letters "aabcdefgaa")))))

(deftest calc-pairs
  (testing "return a set of all pairs"
    (is (= #{"aa"}
           (sol/calc-pairs "aaa")))
    (is (= #{"xy" "yx"}
           (sol/calc-pairs "xyxy")))
    (is (= #{"ab" "bc" "cd" "de" "ef"}
           (sol/calc-pairs "abcdef")))))

(deftest one-repeating-letter-with-sep
  (testing "contains one repeating letter with sep"
    (is (= true
           (sol/one-repeating-letter-with-sep "aba")
           (sol/one-repeating-letter-with-sep "abeae")
           (sol/one-repeating-letter-with-sep "abcdefeghi"))))
  (testing "contains no repeating letter with sep"
    (is (= false
           (sol/one-repeating-letter-with-sep "abb")
           (sol/one-repeating-letter-with-sep "abeaf")
           (sol/one-repeating-letter-with-sep "abcdefaghi")))))
