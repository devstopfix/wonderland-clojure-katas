(ns alphabet-cipher.coder)

(defn substitution-chart [alphabet]
  "Return a substitution chart for the given alphabet,
   which is map of [R,C]->X where R and C are the row/column
   characters to lookup and X is the resulting encoded character."
  (let [vchars (vec alphabet)
        dim    (count alphabet)]
    (reduce into
            (for [row (range dim) col (range dim)]
              (let [r (nth vchars row)
                    c (nth vchars col)
                    x (nth vchars (mod (+ row col) dim))]
                {[r c] x})))))

(def simple-alphabet "abcdefghijklmnopqrstuvwxyz")

(def chart     (substitution-chart simple-alphabet))

(def encode-char
  "Return a function that takes a pair of characters and returns
   the encoded character. [R,C]->X"
  (partial get chart))

(defn encode [keyword message]
  (->>
    (interleave (cycle keyword) (seq message))
    (partition 2)
    (map encode-char)
    (apply str)))

(defn decode [keyword message]
  "decodeme")

(defn decipher [cipher message]
  "decypherme")

