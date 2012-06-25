;; define two entities, authors and posts, using Korma's defentity macro
(ns chirp.models
  (:use [korma.db :only (defdb)]
        [korma.core :only (defentity)])
  (:require [clojure.java.jdbc :as sql]))


(defdb chirpdb
  {:classname   "com.mysql.jdbc.Driver"
   :subprotocol "mysql"
   :subname     "//chirpdbinstance.cvnjt4wjg36t.us-east-1.rds.amazonaws.com:3306/chirpdb"
   :user        "chirpuser"
   :password    "lono1234"})

(defentity authors)
(defentity posts)