(ns ad-fontes.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [ad-fontes.core :as core]))

(deftest fake-test
  (testing "fake description"
    (is (= 1 2))))
