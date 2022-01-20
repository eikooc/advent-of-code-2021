;; require core.async and cljs-http
(ns hello-world.core)
;;(def insts [[:forward 2] [:down 8] [:down 1] [:up 7]])

;; read logic
(def fs (js/require "fs"))
(require '[clojure.string :as str])

(def so (.readFileSync fs "./data/day2" "utf8" (fn [_ data] (data))))

(def inputIn (str/split so #"\n"))
inputIn

(def input2 (map #(str/split %1 " ") inputIn))
input2

(def insts (map (fn [[one two]] [(keyword one) (js/parseInt two)]) input2))

(def x (map js/parseInt inputIn))


;; core logic

(defn submarine [agg [inst val]] (let [cur_depth (:depth agg)
                                       x_pos (:forwards agg)] {:forwards (if (= inst :forward) (+ val x_pos) x_pos)
                                                               :depth (if (= inst :up) (- cur_depth val) (if (= inst :down) (+ val cur_depth) cur_depth))}))

(reduce submarine {:forwards 0 :depth 0} insts)