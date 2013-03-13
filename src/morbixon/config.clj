(ns morbixon.config
  (:require [clojure.string :as string]
            [clojure.tools.logging :as log]
            [clojure-commons.clavin-client :as cl]
            [clojure-commons.props :as props]))

(def properties (atom nil))

(defn listen-port [] (Integer/parseInt (get @properties "morbixon.listen-port")))

(defn log-config
  [properties]
  (let [not-password? #(not (re-matches #".*password.*" %))]
    (doseq [prop-pair (filter not-password? (seq properties))]
      (log/warn (first prop-pair) " = " (last prop-pair)))))

(defn zk-init
  []
  (let [tmp-props (props/parse-properties "zkhosts.properties")
        zkurl     (get tmp-props "zookeeper")]
    (cl/with-zk
      zkurl
      (when-not (cl/can-run?)
        (log/warn
         "THIS APPLICATION CANNOT RUN ON THIS MACHINE. SO SAYETH ZOOKEEPER.")
        (log/warn "THIS APPLICATION WILL NOT EXECUTE CORRECTLY."))
      (reset! properties (cl/properties "morbixon")))))

(defn local-init
  [cfg-path]
  (reset! properties (props/read-properties cfg-path)))
