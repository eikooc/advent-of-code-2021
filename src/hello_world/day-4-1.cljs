;; require core.async and cljs-http
(ns hello-world.day-4-1)
  ;;(:require [cljs.reader :as reader]))
;; (def boards [[[22 13 17 11 0]
;;               [8 2 23 4 24]
;;               [21 9 14 16 7]
;;               [6 10 3 18 5]
;;               [1 12 20 15 19]]])


;; Transposes a matrix. For example given [[0 1] [2 3]] returns [[0 2] [1 3]]
(defn transpose [m]
  (apply mapv vector m))

;; Helper functions to normalize board to what we expect
;; We are given vectors but sets seemed nicer to work with.
(defn board-to-lines [board] (concat (transpose board) board))
(defn lines-to-set [board-lines] (map #(set %1) board-lines))
(defn normalize-board [board] (lines-to-set (board-to-lines board)))

;; To do the bingo we should check the current input against the boards
;; Either by taking one input at a time and producing new boards with marks
;; Or by taking a sequence of input and marking the boards with them
;; Then checking a win condition.
(defn line-won? [input-set board] (empty? (apply disj board input-set)))
(defn get-winning-lines [number-set board] (some #(line-won? number-set %1) board))
(defn bingo [numbers boards] (let [number-set (set numbers)]
                               (filter #(get-winning-lines number-set (normalize-board %1)) boards)))

;; Can we write it in a reduce form?
(defn fbb [acc number] (let [next-numbers (conj (acc :numbers) number) boards-with-bingo (bingo next-numbers (acc :boards))]
                         (if (not (empty? boards-with-bingo)) (reduced {:bingo boards-with-bingo}) (update acc :numbers next-numbers))))
(defn find-best-board [numbers boards]
  (reduce #(fbb %1 %2) {:boards boards :numbers []} numbers))

;; Easier for me to do like this:
(defn find-best-board [numbers boards]
  (loop [n 0 ns numbers]
    (when (<= n (count numbers))
      (let [bb (bingo (take n numbers) boards)]
        (if (empty? bb)
          (recur (inc n) ns)
          {:board bb :numbers (take n numbers)})))))

;; since we do not get the data as a string. We need to convert it first.
;; We can use a multi method for this. switch on map as input.
;;(defmulti blingo identity)
;;(defmethod blingo [numbers] (blingo (set)))


;; read logic
(def fs (js/require "fs"))
(require '[clojure.string :as str])

(def so (.readFileSync fs "./data/day4" "utf8" (fn [_ data] (data))))

(def inputIn (str/split so #"\n"))



(defn map-split-spaces [text] (map #(str/split (str/trim %1) #"\s+") text))
(defn to-int [text] (map #(js/parseInt %1) text))

(def boards (as-> inputIn v
              (nthrest v 2)
              (partition-by #(not= "" %1) v)
              (filter #(> (count %1) 1) v)
              (map #(map-split-spaces %1) v)
              (map #(map to-int %1) v)))
(def num-seq (to-int (str/split (first (take 1 inputIn)) ",")))


(defn not-in [v1 v2]
  (filter #(not (contains? (set v2) %1)) v1))
(not-in [1 2 3 4] [3 4])
(defn calc-bingo-score [opt]
  (let [b (opt :board) ns (opt :numbers)]
    (* (apply + (mapcat + (map #(not-in %1 ns) (first b)))) (last ns))))


(calc-bingo-score (find-best-board num-seq boards))
