(ns wonderland-number.finder)

(defn numbers-have-same-digits? [n1 n2]
  "Return true iff n1 and n2 have all the same digits"
  (= (set (str n1)) (set (str n2))))

(defn multiple-has-same-digits? [n m]
  "Return true if m•n has same digits as n"
  (numbers-have-same-digits? n (* n m)))

(defn wonder? [n]
  "Return true iff n is a wonder number"
  (and
    (= 6 (count (str n)))
    (every? #(multiple-has-same-digits? n %)
            (range 2 (inc 6)))))

(def wonderland-numbers
  "Lazy seq of 6 digit wonderland numbers"
  (filter wonder? (range 100000 999999)))

(defn wonderland-number []
  (first wonderland-numbers))
