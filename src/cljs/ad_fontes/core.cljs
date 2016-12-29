(ns ad-fontes.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [re-frisk.core :refer [enable-re-frisk!]]
   [secretary.core :as secretary :refer-macros [defroute]]
   [ad-fontes.events]
   [ad-fontes.subs]
   [ad-fontes.views :as views]
   [ad-fontes.config :as config]))

(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (enable-re-frisk!)
    (println "dev mode")))

;; Replace this with cljs-ajax or cljs-http code
;; Ask about those in Slack
(defroute "/:book/:chapter"
  [book chapter]
  (let [promise (js/fetch (str "/api/" book "/" chapter))]
    (.then promise (fn [res]
                     (let [promise2 (.text res)]
                       (.then promise2 (fn [text]
                                         (re-frame/dispatch [:update-text text]))))))))

(defroute "/" [] (re-frame/dispatch [:udpate-text {:book "matthew" :chapter 1}]))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (secretary/dispatch! js/window.location.pathname) ;; Is this bad form?
  (mount-root))
