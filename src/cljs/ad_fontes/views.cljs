(ns ad-fontes.views
    (:require [re-frame.core :as re-frame]))

(defn text-panel
  []
  (let [text (re-frame/subscribe [:text])]
    [:div @text]))

(defn main-panel
  []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div
       (text-panel)])))
