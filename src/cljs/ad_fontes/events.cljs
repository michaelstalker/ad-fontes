(ns ad-fontes.events
    (:require [re-frame.core :as re-frame]
              [ad-fontes.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
 :update-text
 (fn [db [_ text]]
   (assoc db :text text)))
