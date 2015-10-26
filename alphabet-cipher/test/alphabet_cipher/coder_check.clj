(ns alphabet-cipher.coder-check
  (:require [clojure.test :refer :all]
            [alphabet-cipher.coder :refer :all]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :as ct :refer (defspec)]))

(def simple-word
  "Generator of a simple word using only the letters a-z (e.g. 'igpixi'). Never empty."
  (->> (gen/vector (gen/fmap char (gen/choose 97 122)))
       (gen/such-that not-empty)
       (gen/fmap clojure.string/join)))

(def simple-words
  "Generates a possibly empty list of simple words"
  (gen/vector simple-word))

; Test we can encode a sentence with a keyword,
; and then decode the result and get back the original sentence.
(defspec test-coder-round-trip
           (prop/for-all [keyword  simple-word
                          sentence simple-words]
                         (let [message         (apply str sentence)
                               message-encoded (encode keyword message)]
                           (is
                             (= message (decode keyword message-encoded))
                             (format "(encode \"%s\" \"%s\") -> \"%s\"" keyword message message-encoded)))))

; Test we can determine keyword used to encode a message given the original message and it's ciphertext.
; We have to
(defspec test-decipher
         (prop/for-all [keyword  simple-word
                        sentence (gen/such-that not-empty simple-words)]
                       (let [plaintext  (apply str sentence)
                             ciphertext (encode keyword plaintext)
                             expected-keyword (if (<= (.length keyword) (.length plaintext))
                                                keyword
                                                (.substring keyword 0 (.length plaintext)))]
                         (is (= expected-keyword (decipher ciphertext plaintext))
                           (format "(decipher \"%s\" \"%s\") -> \"%s\"" ciphertext plaintext expected-keyword)))))
