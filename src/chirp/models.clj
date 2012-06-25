;; define two entities, authors and posts, using Korma's defentity macro
(ns chirp.models
  (:use korma.db
        korma.core))


(defdb chirpdb
  {:classname   "org.postgresql.Driver"
   :subprotocol "postgresql"
   :subname     "chirpdb"
   :user        "chirp"
   :password    "lono123"})

(defentity authors)
(defentity posts)