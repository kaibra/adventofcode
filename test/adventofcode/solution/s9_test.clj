(ns adventofcode.solution.s9-test
  (:require [clojure.test :refer :all]
            [adventofcode.solution.s9 :as sol9]))

(deftest calc-all-routes
  (testing "should calc all combinations of routes"
    (is (= [{:route [:a :b :c]
             :total-dist 5}
            {:route [:a :c :b]
             :total-dist 6}
            {:route [:b :a :c]
             :total-dist 3}
            {:route [:b :c :a]
             :total-dist 6}
            {:route [:c :a :b]
             :total-dist 3}
            {:route [:c :b :a]
             :total-dist 5}]
           (sol9/all-routes {:a {:b 1
                                 :c 2}
                             :b {:a 1
                                 :c 4}
                             :c {:a 2
                                 :b 4}})))))
