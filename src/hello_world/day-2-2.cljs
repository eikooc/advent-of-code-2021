;; require core.async and cljs-http
(ns hello-world.core)
(def insts [[:forward 2] [:down 8] [:down 1] [:forward 4] [:down 4] [:down 3] [:forward 2]])

(defn submarine [agg [inst val]]
  (let [aim (:aim agg)
        ho_pos (:ho_pos agg)
        depth (:depth agg)]
    {:ho_pos (if (= inst :forward) (+ val ho_pos) ho_pos)
     :aim (if (= inst :up) (- aim val) (if (= inst :down) (+ val aim) aim))
     :depth (if (= inst :forward) (max (+ depth (* val aim)) 0) depth)}))

(reduce submarine {:ho_pos 0 :aim 0 :depth 0} insts)