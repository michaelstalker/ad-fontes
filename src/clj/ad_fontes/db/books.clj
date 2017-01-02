(ns ad-fontes.db.books
  (:require
   [clojure.java.jdbc :as sql]
   [config.core :refer [env]]))

(defn insert!
  []
  (sql/insert-multi! (:database-url env)
                     :books [{:id 1 :title "Genesis"}
                             {:id 2 :title "Exodus"}
                             {:id 3 :title "Leviticus"}
                             {:id 4 :title "Numbers"}
                             {:id 5 :title "Deuteronomy"}
                             {:id 6 :title "Joshua"}
                             {:id 7 :title "Judges"}
                             {:id 8 :title "Ruth"}
                             {:id 9 :title "1 Samuel"}
                             {:id 10 :title "2 Samuel"}
                             {:id 11 :title "1 Kings"}
                             {:id 12 :title "2 Kings"}
                             {:id 13 :title "1 Chronicles"}
                             {:id 14 :title "2 Chronicles"}
                             {:id 15 :title "Ezra"}
                             {:id 16 :title "Nehemiah"}
                             {:id 17 :title "Esther"}
                             {:id 18 :title "Job"}
                             {:id 19 :title "Psalms"}
                             {:id 20 :title "Proverbs"}
                             {:id 21 :title "Ecclesiastes"}
                             {:id 22 :title "Song of Solomon"}
                             {:id 23 :title "Isaiah"}
                             {:id 24 :title "Jeremiah"}
                             {:id 25 :title "Lamentations"}
                             {:id 26 :title "Ezekiel"}
                             {:id 27 :title "Daniel"}
                             {:id 28 :title "Hosea"}
                             {:id 29 :title "Joel"}
                             {:id 30 :title "Amos"}
                             {:id 31 :title "Obadiah"}
                             {:id 32 :title "Jonah"}
                             {:id 33 :title "Micah"}
                             {:id 34 :title "Nahum"}
                             {:id 35 :title "Habakkuk"}
                             {:id 36 :title "Zephaniah"}
                             {:id 37 :title "Haggai"}
                             {:id 38 :title "Zechariah"}
                             {:id 39 :title "Malachi"}
                             {:id 40 :title "Matthew"}
                             {:id 41 :title "Mark"}
                             {:id 42 :title "Luke"}
                             {:id 43 :title "John"}
                             {:id 44 :title "Acts"}
                             {:id 45 :title "Romans"}
                             {:id 46 :title "1 Corinthians"}
                             {:id 47 :title "2 Corinthians"}
                             {:id 48 :title "Galatians"}
                             {:id 49 :title "Ephesians"}
                             {:id 50 :title "Philippians"}
                             {:id 51 :title "Colossians"}
                             {:id 52 :title "1 Thessalonians"}
                             {:id 53 :title "2 Thessalonians"}
                             {:id 54 :title "1 Timothy"}
                             {:id 55 :title "2 Timothy"}
                             {:id 56 :title "Titus"}
                             {:id 57 :title "Philemon"}
                             {:id 58 :title "Hebrews"}
                             {:id 59 :title "James"}
                             {:id 60 :title "1 Peter"}
                             {:id 61 :title "2 Peter"}
                             {:id 62 :title "1 John"}
                             {:id 63 :title "2 John"}
                             {:id 64 :title "3 John"}
                             {:id 65 :title "Jude"}
                             {:id 66 :title "Revelation"}]))
