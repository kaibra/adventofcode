(ns adventofcode.solution.s15-test
  (:require [clojure.test :refer :all]
            [adventofcode.solution.s15 :as sol15]))


(deftest generate-all-weight-combinations
  (is (= [{"A" 0
           "B" 2}
          {"A" 1
           "B" 1}
          {"A" 2
           "B" 0}]
         (sol15/generate-all-weight-combinations ["A" "B"] 2)))
  (is (= [{"A" 0
           "B" 0
           "C" 2}
          {"A" 0
           "B" 1
           "C" 1}
          {"A" 0
           "B" 2
           "C" 0}
          {"A" 1
           "B" 0
           "C" 1}
          {"A" 1
           "B" 1
           "C" 0}
          {"A" 2
           "B" 0
           "C" 0}]
         (sol15/generate-all-weight-combinations ["A" "B" "C"] 2)))
  (is (= [{"A" 0
           "B" 0
           "C" 3}
          {"A" 0
           "B" 1
           "C" 2}
          {"A" 0
           "B" 2
           "C" 1}
          {"A" 0
           "B" 3
           "C" 0}
          {"A" 1
           "B" 0
           "C" 2}
          {"A" 1
           "B" 1
           "C" 1}
          {"A" 1
           "B" 2
           "C" 0}
          {"A" 2
           "B" 0
           "C" 1}
          {"A" 2
           "B" 1
           "C" 0}
          {"A" 3
           "B" 0
           "C" 0}]
         (sol15/generate-all-weight-combinations ["A" "B" "C"] 3))))


