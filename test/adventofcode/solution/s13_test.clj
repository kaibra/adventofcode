(ns adventofcode.solution.s13-test
  (:require [clojure.test :refer :all]
            [adventofcode.solution.s13 :as sol13]))


(deftest all-seating-combinations
  (testing "test all-seating-combinations"
    (is (= [{:s-combination   ["Hans"
                               "Peter"
                               "Viktor"
                               "Max"]
             :total-happiness 32}
            {:s-combination   ["Hans"
                               "Peter"
                               "Max"
                               "Viktor"]
             :total-happiness 31}
            {:s-combination   ["Hans"
                               "Viktor"
                               "Peter"
                               "Max"]
             :total-happiness 17}
            {:s-combination   ["Hans"
                               "Viktor"
                               "Max"
                               "Peter"]
             :total-happiness 31}
            {:s-combination   ["Hans"
                               "Max"
                               "Peter"
                               "Viktor"]
             :total-happiness 17}
            {:s-combination   ["Hans"
                               "Max"
                               "Viktor"
                               "Peter"]
             :total-happiness 32}]
           (sol13/all-seating-combinations {"Hans"   {"Peter"  -10
                                                      "Max"    3
                                                      "Viktor" 10}
                                            "Peter"  {"Hans"   5
                                                      "Max"    -7
                                                      "Viktor" 1}
                                            "Viktor" {"Hans"  3
                                                      "Max"   17
                                                      "Peter" 2}
                                            "Max"    {"Hans"   3
                                                      "Peter"  2
                                                      "Viktor" 11}})))
    ))