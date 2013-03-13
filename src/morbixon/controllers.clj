(ns morbixon.controllers
  (:use [clojure-commons.error-codes]
        [slingshot.slingshot :only [try+ throw+]])
  (:require [validateur.validation :as vdtr]
            [clojure.tools.logging :as log]
            [cheshire.core :as json]))

(def top-level-vmap
  (vdtr/validation-set
   (vdtr/presence-of :service)
   (vdtr/presence-of :version)
   (vdtr/presence-of :events)))

(def event-vmap
  (vdtr/validation-set
   (vdtr/presence-of :uid)
   (vdtr/presence-of :event)
   (vdtr/presence-of :user)
   (vdtr/presence-of :category)))

(defn do-provenance
  [request]
  (log/debug "do-provenance: " request)

  (let [body (:body request)]
    (when-not (vdtr/valid? top-level-vmap body)
      (throw+ {:error_code ERR_BAD_OR_MISSING_FIELD
               :provenance body}))

    (when-not (every? #(vdtr/valid? event-vmap %) (:events body))
      (throw+ {:error_code ERR_BAD_OR_MISSING_FIELD
               :provenance body}))
    (let [resp (json/generate-string body)]
      (log/warn resp)
      resp)))