(ns ad-fontes.db.queries
  (:require
   [clojure.java.jdbc :as sql]
   [clojure.string :as s]
   [config.core :refer [env]]))

(defn verses
  [book chapter]
  (let [words
        (sql/query (:database-url env)
                   ["SELECT verses.word, verses.verse
                     FROM verses
                     INNER JOIN books
                     ON verses.book_id = books.id
                     WHERE books.title = ?
                     AND verses.chapter = ?
                     ORDER BY verses.id"
                    (s/capitalize book)
                    (Integer. chapter)])]
    (s/join " " (map :word words))))
