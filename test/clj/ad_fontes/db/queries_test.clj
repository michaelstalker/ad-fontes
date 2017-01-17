(ns ad-fontes.db.queries-test
  (:require [clojure.test :refer :all]
            [ad-fontes.db.queries :refer :all]))


(deftest normalized-book-test
  (testing "a name in the right format"
    (is (= "Jude"
           (normalized-book "Jude"))))

  (testing "a lowercase name"
    (is (= "Jude"
           (normalized-book "jude"))))

  (testing "a book with multiple words"
    (is (= "1 Corinthians"
           (normalized-book "1-corinthians")))))
