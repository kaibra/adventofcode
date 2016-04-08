(ns adventofcode.solution.s11-test
  (:require [clojure.test :refer :all]
            [adventofcode.solution.s11 :as sol11]))

(deftest increasing
  (testing "should simply increase"
    (is (= "abd"
           (sol11/increase "abc")))
    (is (= "aca"
           (sol11/increase "abz")))
    (is (= "aaaa"
           (-> "zzx"
               (sol11/increase)
               (sol11/increase)
               (sol11/increase))))
    (is (= "aaaa"
           (sol11/increase "zzz")))))


(deftest contains-increasing-straight?
  (testing "should find increasing straights"
    (is (= true
           (sol11/contains-increasing-straight? "zabcz")))
    (is (= false
           (sol11/contains-increasing-straight? "zbbcz")))
    (is (= true
           (sol11/contains-increasing-straight? "zbbczxyzu")))
    (is (= true
           (sol11/contains-increasing-straight? "zbbczfghxxzu")))
    (is (= false
           (sol11/contains-increasing-straight? "zbbczfdhxxzu")))
    (is (= true
           (sol11/contains-increasing-straight? "zbbczfhijdhxxzu")))))

(deftest all-increasing-straights
  (testing "should build all increasing straights from alphabet"
    (is (= ["abc" "bcd" "cde"]
           (sol11/all-increasing-straights [\a \b \c \d \e])))
    (is (= ["abc"]
           (sol11/all-increasing-straights [\a \b \c])))))

(deftest no-i-o-l
  (testing "should not contain not allowd chars"
    (is (= 26
           (count (keys sol11/char-lookup))))))

(deftest contains-two-different-pairs?
  (testing "should check for different pairs"
    (is (= true
           (sol11/contains-two-different-pairs? "aabb")))
    (is (= false
           (sol11/contains-two-different-pairs? "aab")))
    (is (= true
           (sol11/contains-two-different-pairs? "aabzuigerbb")))
    (is (= true
           (sol11/contains-two-different-pairs? "zweqlpoerrbzuigerbnawwwuerghe")))
    (is (= false
           (sol11/contains-two-different-pairs? "zweqlpoerrbzuigerbnawuerghe")))))


(deftest find-next-valid-pwd
  (testing "should find next valid"
    (is (= "abcdffaa"
           (sol11/find-next-valid-pwd "abcdefgh")))
    (is (= "ghjaabcc"
           (sol11/find-next-valid-pwd "ghijklmn")))))

(deftest skip-invalid-chars
  (is (= "abjaaaaaaaa"
         (sol11/skip-invalid-chars "abihagepown")))
  (is (= "jaaaaaaaaaaaaaaaaaaaaaaaa"
         (sol11/skip-invalid-chars "iiiiiiiiiiwouefhwefoijwef"))))
