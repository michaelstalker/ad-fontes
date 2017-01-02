(ns ad-fontes.db.new-testament-verses-test
  (:require [clojure.test :refer :all]
            [ad-fontes.db.new-testament-verses :refer :all]))

(def book-chapter-verse "270501")

(deftest book-id-test
  (is (= 66
         (book-id book-chapter-verse))))

(deftest chapter-test
  (is (= 5
         (chapter book-chapter-verse))))

(deftest verse-test
  (is (= 1
         (verse book-chapter-verse))))

(deftest part-of-speech-test
  (are [word abbreviation] (= word (part-of-speech abbreviation))
    "Adjective" "A-"
    "Conjunction" "C-"
    "Adverb" "D-"
    "Interjection" "I-"
    "Noun" "N-"
    "Preposition" "P-"
    "Definite article" "RA"
    "Demonstrative pronoun" "RD"
    "Interrogative/indefinite pronoun" "RI"
    "Personal pronoun" "RP"
    "Relative pronoun" "RR"
    "Verb" "V-"
    "Particle" "X-"))

(deftest person-test
  (are [word code] (= word (person code))
    "1st" "1-------"
    "2nd" "2-------"
    "3rd" "3-------"
    nil "--------"))

(deftest tense-test
  (are [word code] (= word (tense code))
    "Present" "-P------"
    "Imperfect" "-I------"
    "Future" "-F------"
    "Aorist" "-A------"
    "Perfect" "-X------"
    "Pluperfect" "-Y------"
    nil "--------"))

(deftest voice-test
  (are [word code] (= word (voice code))
    "Active" "--A-----"
    "Middle" "--M-----"
    "Passive" "--P-----"
    nil "--------"))

(deftest mood-test
  (are [word code] (= word (mood code))
    "Indicative" "---I----"
    "Imperative" "---D----"
    "Subjunctive" "---S----"
    "Optative" "---O----"
    "Infinitive" "---N----"
    "Participle" "---P----"
    nil "--------"))

(deftest word-case-test
  (are [word code] (= word (word-case code))
    "Nominative" "----N---"
    "Genitive" "----G---"
    "Dative" "----D---"
    "Accusative" "----A---"
    nil "--------"))

(deftest number-form-test
  (are [word code] (= word (number-form code))
    "Singular" "-----S--"
    "Plural" "-----P--"
    nil "--------"))

(deftest gender-test
  (are [word code] (= word (gender code))
    "Masculine" "------M-"
    "Feminine" "------F-"
    "Neuter" "------N-"
    nil "--------"))

(deftest degree-test
  (are [word code] (= word (degree code))
    "Comparative" "-------C"
    "Superlative" "-------S"
    nil "--------"))

(deftest morphology-test
  (is (= {:book_id 65
          :chapter 1
          :verse 1
          :part_of_speech "Noun"
          :person nil
          :tense nil
          :voice nil
          :mood nil
          :word_case "Nominative"
          :number_form "Singular"
          :gender "Masculine"
          :degree nil
          :word "Ἰούδας"
          :word_no_punctuation "Ἰούδας"
          :normalized_word "Ἰούδας"
          :lemma "Ἰούδας"}
         (morphology "260101 N- ----NSM- Ἰούδας Ἰούδας Ἰούδας Ἰούδας"))))
