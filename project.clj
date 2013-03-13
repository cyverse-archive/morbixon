(defproject morbixon "0.1.0-SNAPSHOT"
  :description "Logging service and provenance facade."
  :dependencies [[org.clojure/clojure "1.5.0"]
                 [org.clojure/tools.logging "0.2.6"]
                 [org.clojure/tools.cli "0.2.2"]
                 [slingshot "0.10.3"]
                 [compojure "1.1.5"]
                 [ring/ring-jetty-adapter "1.1.8"]
                 [ring/ring-devel "1.1.8"]
                 [cheshire "5.0.2"]
                 [com.cemerick/url "0.0.7"]
                 [org.iplantc/clojure-commons "1.4.0-SNAPSHOT"]]
  :ring {:handler morbixon.core/app}
  :iplant-rpm {:summary "morbixon"
               :dependencies ["iplant-service-config >= 0.1.0-5"]
               :config-files ["log4j.properties"]
               :config-path "conf"}
  :main morbixon.core
  :plugins [[lein-ring "0.8.3"]
            [org.iplantc/lein-iplant-rpm "1.4.0-SNAPSHOT"]]
  :repositories {"iplant"
                 "http://projects.iplantcollaborative.org/archiva/repository/internal/"})
