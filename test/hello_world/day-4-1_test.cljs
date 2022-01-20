(ns hello-world.day-4-1-test
  (:require [clojure.test :as t :refer [deftest is testing run-tests]]
            [hello-world.day-4-1 :as bingo]))

(def boards [[[0 1]
              [2 3]]
             [[1 2]
              [3 4]]])

(defn set= [& vectors] (apply = (map set vectors)))

(def first-norm-board (bingo/normalize-board (first boards)))

(deftest normalize-test
  (is (set= first-norm-board [#{0 1} #{2 3} #{0 2} #{1 3}])))

(deftest line-test-too-few
  (is (= (bingo/line-won? #{0} #{0 1}) false)))

(deftest line-test-exactly
  (is (= (bingo/line-won? #{0 1} #{0 1}) true)))

(deftest line-test-enough
  (is (= (bingo/line-won? #{0 1 2} #{0 1}) true)))


(deftest bingo-none
  (is (= (bingo/bingo [0] boards) '())))

(deftest bingo-row
  (is (= (bingo/bingo [0 1] boards) '([[0 1] [2 3]]))))

(deftest bingo-col
  (is (= (bingo/bingo [2 4] boards) '([[1 2] [3 4]]))))

(run-tests)
