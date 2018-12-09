(ns ad-fontes.core
  (:require
   [clojure.string :as s]
   [cognitect.transit :as t]
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [secretary.core :as secretary :refer-macros [defroute]]
   [ad-fontes.events]
   [ad-fontes.subs]
   [ad-fontes.views.main :as views]
   [ad-fontes.config :as config]))

(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn normalized-book
  [book]
  (let [words (s/split book #"-")]
    (->> words
         (map s/capitalize)
         (s/join " "))))

;; TODO: Replace this with cljs-ajax or cljs-http code
(defn update-verses
  [book chapter]
  (let [promise (js/fetch (str "/api/" book "/" chapter))]
    (.then promise (fn [res]
                     (let [promise2 (.text res)]
                       (.then promise2 (fn [text]
                                         (let [reader (t/reader :json)
                                               decoded-text (t/read reader text)]
                                           (re-frame/dispatch [:update-text decoded-text])))))))))

(defn update-chapter
  [chapter]
  (re-frame/dispatch [:update-chapter chapter]))

(defn update-book
  [book]
  (re-frame/dispatch [:update-book book]))

(defroute "/:book/:chapter"
  [book chapter]
  (let [book (normalized-book book)]
    (update-verses book chapter)
    (update-book book)
    (update-chapter chapter)))

(defroute "/"
  []
  (update-verses "Matthew" 1)
  (update-book "Matthew")
  (update-chapter 1))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (secretary/dispatch! js/window.location.pathname)
  (mount-root))
