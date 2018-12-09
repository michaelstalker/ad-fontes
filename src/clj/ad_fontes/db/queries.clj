(ns ad-fontes.db.queries
  (:require
   [clojure.java.jdbc :as sql]
   [clojure.string :as s]
   [cognitect.transit :as t]
   [clojure.data.json :as json]
   [config.core :refer [env]])
  (:import [java.io ByteArrayOutputStream]))

(defn verses
  [book chapter]
  (let [out (ByteArrayOutputStream. 4096)
        writer (t/writer out :json)
        words (sql/query (:database-url env)
                         ["SELECT verses.word,
                                  verses.id,
                                  verses.verse,
                                  verses.word_case,
                                  verses.number_form,
                                  verses.gender
                           FROM verses
                           INNER JOIN books
                           ON verses.book_id = books.id
                           WHERE books.name = ?
                           AND verses.chapter = ?
                           ORDER BY verses.id"
                          book
                          (Integer. chapter)])]
    (t/write writer (group-by :verse words))
    (.toString out)))
