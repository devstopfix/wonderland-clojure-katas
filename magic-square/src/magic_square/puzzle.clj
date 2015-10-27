(ns magic-square.puzzle
  (:require [clojure.math.combinatorics :as combo]))

(def values [1.0 1.5 2.0 2.5 3.0 3.5 4.0 4.5 5.0])

(def dimension
  "Dimension of a square (e.g. 3x3)"
  3)

(defn rows [ns]
  "Convert seq of values into a seq of rows"
  (partition dimension ns))

(defn cols [ns]
  "Convert seq of values into a seq of columns"
  (for [i (range dimension)]
    (->> ns
         (drop i)
         (take-nth dimension))))

(defn diagonal [ns]
  "Return the values on the major diagonal
   (top-left to bottom-right"
  (take-nth (inc dimension) ns))

(defn anti-diagonal [ns]
  "Return the values on the minor diagonal
   (top-right to bottom-left"
  (->> ns
       (drop (dec dimension))
       (take-nth (dec dimension))
       (take dimension)))

(defn ∑ [ns]
  "Return the sum of a seq of numbers"
  (reduce + ns) )

(defn sums-to [s ns]
  "Returns true if sequence of numbers ns sum to s"
  (= s (∑ ns)))

(defn is-magic-square? [ns]
  "Returns true if ns (a sequence of numbers) is
   a magic square. The algorithm calculates the
   magic number of the first row and tests to see
   if all other rows, columns and diagonals match."
  (let [rs (rows ns)
        n  (∑ (first rs))]
    (and
      (every? #(sums-to n %) (rest rs))
      (every? #(sums-to n %) (cols ns))
      (sums-to n (diagonal ns))
      (sums-to n (anti-diagonal ns)))))

(defn generate-magic-squares [values]
  "Return a lazy seq of all the magic squares that can be made from the
   given list of values"
  (for [ns (combo/permutations values) :when (is-magic-square? ns)] ns))

(defn format-sq [ns]
  "Format square as a vector of vectors"
  (->> ns
       (partition dimension)
       (map vec)
       (vec)))

(defn magic-square [values]
  "Return the first valid magic square arrangement
   for the given values"
  (->> values
       (generate-magic-squares)
       (map format-sq)
       (first)))
