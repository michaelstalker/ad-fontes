(ns ad-fontes.server
  (:require
   [config.core :refer [env]]
   [ring.adapter.jetty :refer [run-jetty]]
   [ad-fontes.handler :refer [handler]])
  (:gen-class))

 (defn -main [& args]
   (let [port (Integer/parseInt (or (env :port) "3000"))]
     (run-jetty handler {:port port :join? false})))
