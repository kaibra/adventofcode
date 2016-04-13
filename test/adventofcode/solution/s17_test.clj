(ns adventofcode.solution.s17-test
  (:require [clojure.test :refer :all]
            [adventofcode.solution.s17 :as sol17]))

(deftest test-all-bucket-combinations
  (is (= [[1 2]]
         (sol17/all-bucket-combinations [1 2] 3)))
  (is (= [[1 2]
          [3]]
         (sol17/all-bucket-combinations [1 2 3] 3)))
  (is (= [[20 5]
          [20 5]
          [15 10]
          [15 5 5]]
         (sol17/all-bucket-combinations [20 15 10 5 5] 25))))


(deftest all-sub-seqs
  (is (= [[1 2 3] [2 3] [3]]
         (sol17/all-sub-seqs [1 2 3]))))