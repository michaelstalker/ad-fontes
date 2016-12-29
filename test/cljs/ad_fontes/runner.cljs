(ns ad-fontes.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [ad-fontes.core-test]))

(doo-tests 'ad-fontes.core-test)
