(ns ad-fontes.db.seed
  (:require
   [ad-fontes.db.books :as books]
   [ad-fontes.db.new-testament-verses :as new-testament-verses]))

(defn -main
  []
  (books/insert!)
  (new-testament-verses/insert!))
