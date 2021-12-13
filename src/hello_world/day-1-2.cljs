(ns hello-world.core)

(def x [1 2 3 0 0 0 1 1 1 1 1 2 1 1 1 99 99])

(defn build-map [last num] (apply hash-map [:last last :num num]))

(defn calc [a b] (if (> a b) 1 0))

(defn submarine [agg cur] (let [num (get agg :num) addTo (calc cur (get agg :last))] (build-map cur (+ num addTo))))

(defn onlyThrees [x] (= 3 (count (filter #(not= %1 nil) x))))
(defn triples [x] (partition 3 x))
(defn sumTriples [x] (reduce + x))

(nth x 0)
(def windowed (map-indexed (fn [idx itm] [itm (nth x (+ idx 1) nil) (nth x (+ idx 2) nil)]) x))

(reduce submarine (map sumTriples (filter onlyThrees windowed)))
