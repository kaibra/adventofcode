(ns adventofcode.solution.s4-test
  (:require
    [clojure.test :refer :all]
    [adventofcode.solution.s4 :as s4]))

(deftest md5-test
  (testing "should return corresponding bytes"
    (is (= 16
           (count (s4/md5-bytes "abcdef609043"))))
    (is (= "00"
           (format "%02x"
                   (get (s4/md5-bytes "abcdef609043") 0))))
    (is (= "00"
           (format "%02x"
                   (get (s4/md5-bytes "abcdef609043") 1))))
    (is (= "01"
           (format "%02x"
                   (get (s4/md5-bytes "abcdef609043") 2))))
    (is (= "06"
           (format "%02x"
                   (get (s4/md5-bytes "pqrstuv1048970") 2))))
    (is (= "0f"
           (format "%02x"
                   15)))
    (is (= "10"
           (format "%02x"
                   16))))

  (testing "should build full md5-hex-string"
    (is (= 32
           (count (s4/md5-hex-string "abcdef609043"))))
    (is (= "000001dbbfa3a5c83a2d506429c7b00e"
           (s4/md5-hex-string "abcdef609043")))))

(deftest md5-validity
  (testing "should test for md5 validity"
    (is (= true
           (s4/starts-with-5-zeros? [0 0 0])))
    (is (= true
           (s4/starts-with-5-zeros? [0 0 1])))
    (is (= false
           (s4/starts-with-5-zeros? [0 0 16])))
    (is (= false
           (s4/starts-with-5-zeros? [0 0 -1])))))