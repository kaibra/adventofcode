(ns adventofcode.solution6-test
  (:require
    [clojure.test :refer :all]
    [adventofcode.solution6 :as sol]))

(deftest parsing
  (with-redefs [sol/total-width 10]
    (is (= [{:cmd         "turn off"
             :coordinates [11]}]
           (sol/parse-to-cmd-tasks "turn off 1,1 through 1,1")))
    (is (= [{:cmd         "turn on"
             :coordinates [12 13]}
            {:cmd         "turn on"
             :coordinates [22 23]}]
           (sol/parse-to-cmd-tasks "turn on 1,2 through 2,3")))
    (is (= [{:cmd         "toggle"
             :coordinates [12 13]}
            {:cmd         "toggle"
             :coordinates [22 23]}]
           (sol/parse-to-cmd-tasks "toggle 1,2 through 2,3")))))

(deftest building-coordinates
  (with-redefs [sol/total-width 10]
    (is (= [{:cmd         "toggle"
             :coordinates [0]}]
           (sol/tasks "toggle" [0 0] [0 0])))
    (is (= [{:cmd         "toggle"
             :coordinates [0]}
            {:cmd         "toggle"
             :coordinates [10]}]
           (sol/tasks "toggle" [0 0] [1 0])))
    (is (= [{:cmd         "turn on"
             :coordinates [50 51]}
            {:cmd         "turn on"
             :coordinates [60 61]}
            {:cmd         "turn on"
             :coordinates [70 71]}
            {:cmd         "turn on"
             :coordinates [80 81]}
            {:cmd         "turn on"
             :coordinates [90 91]}]
           (sol/tasks "turn on" [5 0] [9 1])))))

(deftest modification
  (let [structure (transient [false false false])
        worker-count (atom 0)]
    (sol/execute-parallel worker-count structure {:cmd         "toggle"
                                                  :coordinates [1]})
    (sol/execute-parallel worker-count structure {:cmd         "turn off"
                                                  :coordinates [1]})
    (sol/execute-parallel worker-count structure {:cmd         "turn on"
                                                  :coordinates [0]})
    (is (= [true false false] (into [] (persistent! structure))))))
