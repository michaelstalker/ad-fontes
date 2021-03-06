(ns ad-fontes.subs
  (:require-macros
   [reagent.ratom :refer [reaction]])
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 :text
 (fn [db]
   (:text db)))

(re-frame/reg-sub
 :chapter
 (fn [db]
   (:chapter db)))

(re-frame/reg-sub
 :book
 (fn [db]
   (:book db)))
