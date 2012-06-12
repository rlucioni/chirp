;;; define two entities, authors and posts, using Korma's defentity macro
(ns clog.models
	(:use korma.db
		  korma.core))

(defdb clogdb
	{:classname   "org.postgresql.Driver"
	 :subprotocol "postgresql"
	 :subname     "clogdb"
	 :user        "clog"
	 :password    "lono123"})

(defentity authors)
(defentity posts)