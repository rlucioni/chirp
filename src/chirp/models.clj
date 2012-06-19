;;; define two entities, authors and posts, using Korma's defentity macro
(ns chirp.models
	(:use korma.db
		  korma.core))

(defdb chirpdb
	{:classname   "org.postgresql.Driver"
	 :subprotocol "postgresql"
	 :subname     "dfyohnwqis" ;;;"chirpdb"
	 :user        "dfyohnwqis" ;;;"chirp"
	 :password    "Z4vPDD5H0TiKhY69DyTd" ;;;"lono123"
	 })

(defentity authors)
(defentity posts)