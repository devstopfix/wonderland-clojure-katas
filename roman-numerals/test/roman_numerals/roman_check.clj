(ns roman-numerals.roman-check
  (:require [clojure.test :refer :all]
            [roman-numerals.roman :refer :all]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :as ct :refer (defspec)]))

