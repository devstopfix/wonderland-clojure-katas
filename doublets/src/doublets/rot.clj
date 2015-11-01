(ns doublets.rot)

; http://rosettacode.org/wiki/Rot-13#Clojure

(def A (into #{} "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"))

(def Am (->> (cycle A) (drop 26) (take 52) (zipmap A)))

(defn rot13 [^String in]
  (apply str (map #(Am % %) in)))