;; Move some dependencies to dev section
(defproject ad-fontes "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.9.946"]
                 [org.clojure/java.jdbc "0.7.5"]
                 [org.postgresql/postgresql "42.1.4"]
                 [re-frame "0.10.2"]
                 [compojure "1.6.0"]
                 [yogthos/config "0.8"]
                 [migratus "1.0.3"]
                 [com.cognitect/transit-clj "0.8.300"]
                 [com.cognitect/transit-cljs "0.8.243"]
                 [org.slf4j/slf4j-log4j12 "1.7.9"] ;; To avoid silent migratus failures.
                 [ring "1.6.3"] ;; Do I need all of ring, or just ring-core?
                 [secretary "1.2.3"]]

  :plugins [[lein-cljsbuild "1.1.7"]
            [migratus-lein "0.5.4"]]

  :min-lein-version "2.5.3"

  :jvm-opts ["--add-modules" "java.xml.bind"]

  :source-paths ["src/clj"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"
                                    "test/js"]

  :figwheel {:css-dirs ["resources/public/css"]
             :ring-handler ad-fontes.handler/dev-handler}

  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

  :migratus {:store :database
             :migration-dir "migrations"
             :db ~(or (get (System/getenv) "DATABASE_URL")
                      "postgresql://localhost:5432/ad_fontes")}

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "0.8.2"]
                   [figwheel-sidecar "0.5.14"]
                   [com.cemerick/piggieback "0.2.1"]
                   [re-frisk "0.5.3"]
                   [lein-doo "0.1.7"]]

    :plugins      [[lein-figwheel "0.5.14"]
                   [lein-doo "0.1.7"]]
    :resource-paths ["config/dev"]}

   :prod {:resource-paths ["config/prod"]}}

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs"]
     :figwheel     {:on-jsload "ad-fontes.core/mount-root"}
     :compiler     {:main                 ad-fontes.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "/js/compiled/out"
                    :source-map-timestamp true
                    :preloads             [devtools.preload
                                           re-frisk.preload]
                    :external-config      {:devtools/config {:features-to-install :all}}}}

    {:id           "prod"
     :source-paths ["src/cljs"]
     :jar true
     :compiler     {:main            ad-fontes.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}

    {:id           "test"
     :source-paths ["src/cljs" "test/cljs"]
     :compiler     {:main          ad-fontes.runner
                    :output-to     "resources/public/js/compiled/test.js"
                    :output-dir    "resources/public/js/compiled/test/out"
                    :optimizations :none}}]}

  :main ad-fontes.server

  :aot [ad-fontes.server]

  :uberjar-name "ad-fontes.jar"

  :prep-tasks [["cljsbuild" "once" "prod"] "compile"]
  )
