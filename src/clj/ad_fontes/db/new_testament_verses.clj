(ns ad-fontes.db.new-testament-verses
  (:require
   [clojure.java.io :refer [reader]]
   [clojure.tools.logging :as log]
   [clojure.java.jdbc :as sql]
   [config.core :refer [env]]))

(def parts-of-speech {"A-" "Adjective"
                      "C-" "Conjunction"
                      "D-" "Adverb"
                      "I-" "Interjection"
                      "N-" "Noun"
                      "P-" "Preposition"
                      "RA" "Definite article"
                      "RD" "Demonstrative pronoun"
                      "RI" "Interrogative/indefinite pronoun"
                      "RP" "Personal pronoun"
                      "RR" "Relative pronoun"
                      "V-" "Verb"
                      "X-" "Particle"})

(def degrees {"C" "Comparative"
              "S" "Superlative"})

(def persons {"1" "1st"
              "2" "2nd"
              "3" "3rd"})

(def tenses {"P" "Present"
             "I" "Imperfect"
             "F" "Future"
             "A" "Aorist"
             "X" "Perfect"
             "Y" "Pluperfect"})

(def moods  {"I" "Indicative"
             "D" "Imperative"
             "S" "Subjunctive"
             "O" "Optative"
             "N" "Infinitive"
             "P" "Participle"})

(def cases  {"N" "Nominative"
             "G" "Genitive"
             "D" "Dative"
             "A" "Accusative"})

(def voices  {"A" "Active"
              "M" "Middle"
              "P" "Passive"})

(def numbers  {"S" "Singular"
               "P" "Plural"})

(def genders {"M" "Masculine"
              "F" "Feminine"
              "N" "Neuter"})

(def degrees  {"C" "Comparative"
               "S" "Superlative"})

;; code argument is of this form: "260122"
;; See https://github.com/morphgnt/sblgnt/blob/master/README.md and the first column of
;; https://github.com/morphgnt/sblgnt/blob/master/86-Jud-morphgnt.txt
(defn reference-fn
  [index]
  (fn [code]
    (Integer. (subs code index (+ index 2)))))

(def morphological-book-id (reference-fn 0))
(def chapter (reference-fn 2))
(def verse (reference-fn 4))

(defn book-id
  [code]
  (+ (morphological-book-id code) 39))

(defn part-of-speech
  [abbreviation]
  (get parts-of-speech abbreviation))

;; code argument is of this form: "-PAPNPM-"
;; See https://github.com/morphgnt/sblgnt/blob/master/README.md#part-of-speech-code
(defn parsing-code-fn
  [mapping index]
  (fn [code]
    (get mapping (subs code index (inc index)))))

(def person (parsing-code-fn persons 0))
(def tense (parsing-code-fn tenses 1))
(def voice (parsing-code-fn voices 2))
(def mood (parsing-code-fn moods 3))
(def word-case (parsing-code-fn cases 4))
(def number-form (parsing-code-fn numbers 5))
(def gender (parsing-code-fn genders 6))
(def degree (parsing-code-fn degrees 7))

(defn morphology
  [string]
  (let [pieces (clojure.string/split string #" ")
        reference-code (get pieces 0)
        parsing-code (get pieces 2)]
    {:book_id (book-id reference-code) ;; dry this
     :chapter (chapter reference-code)
     :verse (verse reference-code)
     :part_of_speech (part-of-speech (get pieces 1))
     :person (person parsing-code)
     :tense (tense parsing-code)
     :voice (voice parsing-code)
     :mood (mood parsing-code)
     :word_case (word-case parsing-code)
     :number_form (number-form parsing-code)
     :gender (gender parsing-code)
     :degree (degree parsing-code)
     :word (get pieces 3)
     :word_no_punctuation (get pieces 4)
     :normalized_word (get pieces 5)
     :lemma (get pieces 6)}))

(defn insert!
  []
  (let [directory (clojure.java.io/file "resources/submodules/sblgnt")
        all-files (file-seq directory)
        chapter-files (filter #(clojure.string/ends-with? (.getPath %) ".txt") all-files)]
    (log/info "Adding New Testament verses into the database...")
    (doseq [file chapter-files]
      (log/info "  Adding verses from " (.getPath file) " ...")
      (with-open [rdr (reader file)]
        (let [lines (line-seq rdr)]
          (sql/insert-multi! (:database-url env)
                             :verses
                             (map morphology lines))
          (log/info "  Done!\n"))))
    (log/info "Done adding New Testament verses!\n")))
