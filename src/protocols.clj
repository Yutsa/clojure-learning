(ns protocols
  (:import (java.util Collection)))

; Déclarer un protocol revient à créer une interface. On déclare la signature d'une ou plusieurs fonctions.
(defprotocol Affichable
  "Un protocol de test"
  (afficher [this] "Affiche la classe de l'élément dans la console"))

; On peut étendre des types existants pour leur faire implémenter le protocol.
(extend String
  Affichable
  {:afficher (fn [_]
               (str "Je suis une String"))})

; La macro extend-type permet une syntaxe plus jolie pour implémenter des protocols sur des types existants.
(extend-type Collection
  Affichable
  (afficher [_]
    (str "Je suis une collection")))

; La macro extend-protocol permet d'implémenter un protocol pour plusieurs types d'un coup
(extend-protocol Affichable
  Number
  (afficher [_] (str "Je suis un nombre 2"))
  Character
  (afficher [_] (str "Je suis un caractère")))

; Un nouveau type peut être créé qui va implémenter le protocol
(deftype Produit [nom]
  Affichable
  (afficher [this] (str "Je suis un produit qui s'appelle " (.nom this))))

; Un record peut également être créé, ce qui permet de le manipuler comme une Map
(defrecord Parcelle [nom])
; Le record peut ensuite être étendu pour implémenter un protocol
(extend-type Parcelle
  Affichable
  (afficher [this] (str "Je suis une parcelle qui s'appelle " (:nom this))))

; Un record peut être créé et implémenter un protocol directement
(defrecord Cepage [nom]
  Affichable
  (afficher [this] (str "Je suis un cépage qui s'appelle " (str "'" (:nom this) "'"))))

; Enfin une instance anonyme implémentant un ou des protocols peut êter créé avec reify
(def anonyme (reify Affichable
               (afficher [_] (str "Je suis un type anonyme"))))
(afficher anonyme)

; Les appels à la fonction définie dans le protocol utilisent le polymorphisme pour appeler la bonne implémentation.
(afficher (String.))
(afficher 1)
(afficher [1 2])
(afficher \a)
(afficher (->Produit "Heliocuivre"))
(afficher (->Parcelle "La parcelle du fond"))
(afficher (->Cepage "Chardonnay"))
; Si aucune implémentation n'existe, une erreur a lieu.
(afficher (Thread/currentThread))
; On peut vérifier si un type implémente un protocol
(extends? Affichable Produit)

; Un datatype ne peut pas être utilisé comme une map
(def heliocuivre (->Produit "heliocuivre"))
(:nom heliocuivre)
; Il faut utiliser la syntaxe avec le point comme pour l'interop Java
(.nom heliocuivre)

; Un record peut s'utiliser comme une map. On peut également utiliser toutes les fonctions sur les maps (assoc etc)
(def parcelle1 (->Parcelle "La Butte"))
(:nom parcelle1)
; Et comme un datatype également
(.nom parcelle1)