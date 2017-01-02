(ns ad-fontes.handler
  (:require
   [compojure.core :refer [GET defroutes]]
   [compojure.route :refer [resources]]
   [ring.util.response :refer [resource-response]]
   [ring.middleware.reload :refer [wrap-reload]]
   [clojure.java.jdbc :as sql]
   [config.core :refer [env]]))

;; TODO: Move this
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
                    (clojure.string/capitalize book)
                    (Integer. chapter)])]
    (clojure.string/join " " (map :word words))))

(defroutes routes
  (GET "/" [] (resource-response "index.html" {:root "public"}))
  (GET "/:book/:chapter" [_ _] (resource-response "index.html" {:root "public"}))
  (GET "/api/:book/:chapter" [book chapter] (verses book chapter))
  (resources "/"))

(def dev-handler (-> #'routes wrap-reload))

(def handler routes)
