(ns adventofcode.solution.s7-test
  (:require
    [clojure.test :refer :all]
    [adventofcode.solution.s7 :as sol]))

(deftest parsing-command
  (testing "should build fn which does more fancy things"
    (let [the-circuit (-> {}
                          (sol/add-parsed-assignment "12 -> a")
                          (sol/add-parsed-not "NOT a -> b")
                          (sol/add-parsed-not "NOT b -> c"))]
      (is (= ["a" "b" "c"]
             (keys the-circuit)))
      (is (= 12
             ((get-in the-circuit ["c" :calc-fn]) the-circuit)))))

  (testing "should build fn which does even more fancy things"
    (let [the-circuit (-> {}
                          (sol/add-parsed-assignment "2 -> a")
                          (sol/add-parsed-assignment "1 -> b")
                          (sol/add-parsed-or "a OR b -> c")
                          (sol/add-parsed-rshift "c RSHIFT 1 -> d"))]
      (is (= ["a" "b" "c" "d"]
             (keys the-circuit)))
      (is (= 3 ((get-in the-circuit ["c" :calc-fn]) the-circuit)))
      (is (= 1 ((get-in the-circuit ["d" :calc-fn]) the-circuit))))))
