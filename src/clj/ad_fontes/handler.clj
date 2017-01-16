(ns ad-fontes.handler
  (:require
   [compojure.core :refer [GET defroutes]]
   [compojure.route :refer [resources]]
   [ring.util.response :refer [resource-response]]
   [ring.middleware.reload :refer [wrap-reload]]
   [ad-fontes.db.queries :as queries]))

(defroutes routes
  (GET "/" [] (resource-response "index.html" {:root "public"}))
  (GET "/:book/:chapter" [_ _] (resource-response "index.html" {:root "public"}))
  (GET "/api/:book/:chapter" [book chapter] (queries/verses book chapter))
  (resources "/"))

(def dev-handler (-> #'routes wrap-reload))

(def handler routes)
