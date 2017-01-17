(ns ad-fontes.views
  (:require
   [clojure.string :as s]
   [re-frame.core :as re-frame]
   [cognitect.transit :as t]))

(defn text-panel
  []
  (let [reader (t/reader :json)
        payload (re-frame/subscribe [:text])
        verses (:verses (t/read reader @payload))
        verses (into (sorted-map) verses)]
    [:ol.verses
     (for [[verse words] verses]
       ^{:key (-> words first :id)} [:li.verse
                                     [:span.verse-number verse]
                                     [:span (s/join " " (map :word words))]])]))

(defn main-panel
  []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div
       (text-panel)])))
