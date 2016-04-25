(ns adventofcode.solution.s21-test
  (:require [clojure.test :refer :all]
            [adventofcode.solution.s21 :as sol21]))

(deftest game-results
  (testing "should calc game-result"
    (let [the-boss {:armor      100
                    :damage     40
                    :hit-points 100}]
      (is (= {:cost 30
              :win  :boss}
             (sol21/to-game-results the-boss
                                    [(sol21/item "A" 10 5 5)
                                     (sol21/item "B" 20 2 2)])))
      (is (= {:cost 10
              :win  :player}
             (sol21/to-game-results the-boss
                                    [(sol21/item "A" 10 100 135)]))))))

(deftest player-conf
  (testing "should build player conf"
    (is (= {:hit-points 100
            :armor      7
            :cost       30
            :damage     7}
           (sol21/to-player-conf [(sol21/item "A" 10 5 5)
                                  (sol21/item "B" 20 2 2)])))))
