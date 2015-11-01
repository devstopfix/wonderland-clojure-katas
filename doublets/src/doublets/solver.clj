(ns doublets.solver
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn])
  (:use [doublets.rot :only [rot13]]))

(def words (-> "words.edn"
               (io/resource)
               (slurp)
               (read-string)))

(defn simple-word? [w]
  "Return true iif the word consists of all
   lowercase english letters without
   hyphens."
  (not (nil? (re-matches #"^[a-z]+$" w))))

(defn remove-bad-words [ws]
  "Remove bad words from the given list
   of words. Returns a set."
  (->> "bad-words-rot13.edn"
      (io/resource)
      (slurp)
      (read-string)
      (map rot13)
      (clojure.set/difference ws)))

(def dict-words
  "Return a set of the simple words found
   in the local dictionary on a Mac"
  (->> "/usr/share/dict/words"
       (clojure.java.io/reader)
       (line-seq)
       (filter simple-word?)
       (into (sorted-set))))

; 3=1125 4=4316
(defn words-of-length [ws n]
  "Filter list of words ws and return
   a set of those of length n characters"
  (->> ws
       (filter #(= n (.length %)))
       (into #{})))


(defn doublets [word1 word2]
  "make me work")
