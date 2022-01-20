(ns hello-world.core)

;; Input logic
(def fs (js/require "fs"))
(require '[clojure.string :as str])

(def so (.readFileSync fs "./data/day1" "utf8" (fn [_ data] (data))))

(def inputIn (str/split so #"\n"))

(def x (map js/parseInt inputIn))

;; Core logic

(def x [1 2 3 0 0 0 1 1 1 1 1 2 1 1 1 99 99])

(defn build-map [last num] (apply hash-map [:last last :num num]))

(defn calc [a b] (if (> a b) 1 0))

(defn submarine [agg cur] (let [num (get agg :num) addTo (calc cur (get agg :last))] (build-map cur (+ num addTo))))

(defn onlyThrees [x] (= 3 (count (filter #(not= %1 nil) x))))
;;(defn triples [x] (partition 3 x))
(defn sumTriples [x] (reduce + x))

(nth x 0)
(def windowed (map-indexed (fn [idx itm] [itm (nth x (+ idx 1) nil) (nth x (+ idx 2) nil)]) x))

(def output (reduce submarine (map sumTriples (filter onlyThrees windowed))))


(output :num)