(ns calcul_ift
  (:require [clojure.spec.alpha :as s]))
  

(s/def ::name string?)
(s/def ::quantity number?)
(s/def ::unit #{:kg_ha :kg_l :l_ha})
(s/def ::produit (s/keys :req [::name ::quantity ::unit]))


(defn double-quantity [produit]
  (assoc produit :quantity (* 2 (:quantity produit)))
  )

(s/fdef double-quantity
        :args (s/cat :produit ::produit)
        :ret ::produit)

(def produit {:name "Heliocuivre"
              :quantity 2
              :unit :kg_ha}
  )

(println produit)

(println (double-quantity 1))

(s/validate-fn (double-quantity 1))