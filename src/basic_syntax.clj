(ns basic-syntax)

(def results [:h :t :t :h :h :h])

(partition 2 1 results)

(def count-if (comp count filter))

(defn count-runs
  "Compte le nombre d'occurence de n fois pred dans la collection coll"
  [n pred coll]
  (count-if #(every? pred %) (partition n 1 coll))
  )

(count-runs 2 #{:t} results)