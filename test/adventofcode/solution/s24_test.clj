(ns adventofcode.solution.s24-test
  (:require [clojure.test :refer :all]
            [adventofcode.solution.s24 :as sol24]))

(deftest best-sleigh-conf
  (testing "should find all combinations with fewest elements"
    (is (= [[3]]
           (sol24/find-all-combinations [1 2 3] 3))))
  (testing "should find best sleigh conf"
    (is (= {:combination [9 11]
            :qe          99}
           (sol24/best-sleigh-conf [1 2 3 4 5 7 8 9 10 11])))))

