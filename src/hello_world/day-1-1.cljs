(ns hello-world.core)

(def x [1 2 3 0 0 0 1 1 1 1 1 2])
;;(def y (map x))

;; Input logic
(def fs (js/require "fs"))
(require '[clojure.string :as str])

(defn read-edn [path f]
  (.readFile fs path "utf8" (fn [err data] (f data))))

(def so (.readFileSync fs "./data/day1" "utf8" (fn [_ data] (data))))

(def inputIn (str/split so #"\n"))

(def x (map js/parseInt inputIn))


;; Core logic
(defn build-map [last num] (apply hash-map [:last last :num num]))

(defn calc [a b] (if (> a b) 1 0))

(defn submarine [agg cur] (let [num (get agg :num) addTo (calc cur (get agg :last))]
                            (build-map cur (+ num addTo))))

(def output (reduce submarine x))

(output :num)