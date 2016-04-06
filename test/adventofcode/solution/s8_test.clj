(ns adventofcode.solution.s8-test
  (:require
    [clojure.test :refer :all]
    [adventofcode.solution.s8 :as sol8]))


(deftest encoding
  (testing "should encode a line of input"
    (is (= "\"\\\"bv\\\\\\\"mds\\\\\\\\zhfusiepgrz\\\\\\\\b\\\\x32fscdzz\\\"\""
           (sol8/encoded-line "\"bv\\\"mds\\\\zhfusiepgrz\\\\b\\x32fscdzz\"")))
    (is (= "\"\\\"\\\"\""
           (sol8/encoded-line "\"\"")))
    (is (= "\"\\\"abc\\\"\""
           (sol8/encoded-line "\"abc\"")))
    (is (= "\"\\\"aaa\\\\\\\"aaa\\\"\""
           (sol8/encoded-line "\"aaa\\\"aaa\"")))
    (is (= "\"\\\"\\\\x27\\\"\""
           (sol8/encoded-line "\"\\x27\"")))))
