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

(def chart (substitution-chart simple-alphabet))

(def encode-char
  "Return a function that takes a pair of characters and returns
   the encoded character. [R,C]->X"
  (partial get chart))

(defn reverse-chart [chart]
  "Reverse a substitution chart of [R,C]->X
   into a chart of [R,X]->C"
  (reduce into
          (for [[rc v] chart]
            (let [[r c] rc]
              {[r v] c}))))

(def decode-char (partial get (reverse-chart chart)))

(defn encode [keyword message]
  (->>
    (interleave (cycle keyword) (seq message))
    (partition 2)
    (map encode-char)
    (apply str)))

(defn decode [keyword message]
  (->>
    (interleave (cycle keyword) (seq message))
    (partition 2)
    (map decode-char)
    (apply str)))

(defn decipher [cipher message]
  "decypherme")

