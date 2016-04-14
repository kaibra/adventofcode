(ns adventofcode.solution.s18-test
  (:require [clojure.test :refer :all]
            [adventofcode.solution.s18 :as sol18]))


(deftest should-get-neighbours
  (testing "should get the neighbours for a coordinate"
    (is (= [1 1 1 1 0 0 0 1]
           (sol18/neighbours {0 {0 1
                                 1 1
                                 2 1}
                              1 {0 1
                                 1 1
                                 2 1}
                              2 {0 0
                                 1 0
                                 2 0}} [1 1])))))