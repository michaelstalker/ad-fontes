(ns ad-fontes.views.main
  (:require
   [clojure.string :as s]
   [re-frame.core :as re-frame]))

(defn text-panel
  [verses]
  [:ol.verses
   (for [[verse words] verses]
     ^{:key (-> words first :id)} [:li.verse
                                   [:span.verse-number verse]
                                   [:span (s/join " " (map :word words))]])])

(defn heading
  [text]
  [:h1 text])

(def footer
  [:blockquote
   "Scripture quotations marked"
   [:a {:href "http://sblgnt.com"} "SBLGNT"]
   "are from the"
   [:a {:href "http://sblgnt.com"} "SBL Greek New Testament"]
   "."
   "Copyright © 2010 Scripture quotations marked"
   [:a {:href "http://www.sbl-site.org"} "Society of Biblical Literature"]
   "and"
   [:a {:href "http://www.logos.com"} "Logos Bible Software"]
   "."])

(defn main-panel
  []
  (fn []
    (let [text (re-frame/subscribe [:text])
          verses (into (sorted-map) @text)
          book (re-frame/subscribe [:book])
          chapter (re-frame/subscribe [:chapter])]
      [:div
       (heading (str @book " " @chapter))
       (text-panel verses)
       footer])))
