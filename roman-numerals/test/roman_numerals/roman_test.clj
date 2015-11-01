(ns roman-numerals.roman-test
  (:require [clojure.test :refer :all]
            [roman-numerals.roman :refer :all]))

(deftest test-1
  (testing "Number 1."
    (is (= 1 (decode "I")))))
