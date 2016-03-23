(ns adventofcode.solution6-test
  (:require
    [clojure.test :refer :all]
    [adventofcode.solution6 :as sol]))


(deftest parsing
  (with-redefs [sol/total-width 10]
    (testing ""
      (is (= {:cmd         "turn off"
              :coordinates [11]}
             (sol/parse-to-cmd-tasks "turn off 1,1 through 1,1")))
      (is (= {:cmd         "turn on"
              :coordinates [21
                             22
                             31
                             32]}
             (sol/parse-to-cmd-tasks "turn on 1,2 through 2,3")))
      (is (= {:cmd         "toggle"
              :coordinates [21
                            22
                            31
                            32]}
             (sol/parse-to-cmd-tasks "toggle 1,2 through 2,3"))))))

(deftest building-coordinates
  (with-redefs [sol/total-width 10]
    (testing ""
      (is (= [0]
             (sol/tasks [0 0] [0 0])))
      (is (= [0 1]
             (sol/tasks [0 0] [1 0])))
      (is (= [0 1 2 3 4 5]
             (sol/tasks [0 0] [5 0])))
      (is (= [0 10]
             (sol/tasks [0 0] [0 1])))
      (is (= [5 15]
             (sol/tasks [5 0] [5 1])))
      (is (= [5 6 7 8 9 15 16 17 18 19]
             (sol/tasks [5 0] [9 1])))))
  (with-redefs [sol/total-width 3]
    (testing ""
      (is (= [0 1 3 4]
             (sol/tasks [0 0] [1 1])))
      (is (= [4 5 7 8]
             (sol/tasks [1 1] [2 2]))))))

(deftest modification
  (testing "toggle"
    (let [structure (int-array 3)]
      (sol/execute-parallel structure {:cmd         "toggle"
                                       :coordinates [1]})
      (sol/execute-parallel structure {:cmd         "turn off"
                                       :coordinates [1]})
      (sol/execute-parallel structure {:cmd         "turn on"
                                       :coordinates [0]})
      (is (= [1 0 0] (into [] structure))))))
