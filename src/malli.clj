(ns malli
  (:require [malli.core :as m]
            [malli.instrument :as mi]
            [malli.dev :as dev]))

(defn plus [x y]
  (+ x y))

;; Défini le schéma de la fonction "plus"
(m/=> plus
      (m/schema
        [:=> [:cat :int :int] :int]))

(defn plus-as-string [x y]
   (str (+ x y)))

(m/=> plus-as-string
  (m/schema
    [:=> [:cat :int :int] [:string]]))

;; Instrumente toutes les fonctions
(mi/instrument!)
;; Instrumente toutes les fonctions et lance des tests génératifs
(mi/check)

;; Lance l'instrumentation sur toutes les specs et mets un watch en cas de changement. Jolie log si on ne respecte pas
;; une spec.
(dev/start!)

(plus 1 1)
(plus-as-string 1 2)

;; Montre une erreur car "" est une string et pas un int.
(plus "" 2)
;; Montre une erreur car plus-as-string renvoie une string et pas un int
(plus 1 (plus-as-string 2 3))