;; define two entities, authors and posts, using Korma's defentity macro
(ns chirp.models
  (:use [korma.db :only (defdb)]
        [korma.core :only (defentity)])
  (:require [chirp.db.config :as config]
            [clojure.java.jdbc :as sql]))

(defdb chirpdb config/connection)

(defentity authors)
(defentity posts)