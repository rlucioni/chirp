;;; define two entities, authors and posts, using Korma's defentity macro
(ns chirp.models
	(:use korma.db
		  korma.core)
	(:import (java.net URI))))

; (defdb chirpdb
; 	{:classname   "org.postgresql.Driver"
; 	 :subprotocol "postgresql"
; 	 :subname     "qmqhhfanll" ;;; "chirpdb"
; 	 :user        "qmqhhfanll" ;;; "chirp"
; 	 :password    "WrScztZeVktVZjE4rkpt" ;;; "lono123"
; 	 })

(defn heroku-db
  "Generate the db map according to Heroku environment when available."
  []
  (when (System/getenv "DATABASE_URL")
    (let [url (URI. (System/getenv "DATABASE_URL"))
          host (.getHost url)
          port (if (pos? (.getPort url)) (.getPort url) 5432)
          path (.getPath url)]
      (merge
       {:subname (str "//" host ":" port path)}
       (when-let [user-info (.getUserInfo url)]
         {:user (first (str/split user-info #":"))
          :password (second (str/split user-info #":"))})))))

(defdb chirpdb
  (merge {:classname   "org.postgresql.Driver"
          :subprotocol "postgresql"
          :subname     "//localhost:8080/chirp"}
          (heroku-db)))

(defentity authors)
(defentity posts)