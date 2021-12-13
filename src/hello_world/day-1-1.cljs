(ns hello-world.core)

(def x [1 2 3 0 0 0 1 1 1 1 1 2])
;;(def y (map x))

(defn build-map [last num] (apply hash-map [:last last :num num]))

(defn calc [a b] (if (> a b) 1 0))

(defn submarine [agg cur] (let [num (get agg :num) addTo (calc cur (get agg :last))]
                            (build-map cur (+ num addTo))))

(reduce submarine x)
