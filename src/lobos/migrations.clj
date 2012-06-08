(ns lobos.migrations
	;;; exclude some clojure built-in symbols so we can use the lobos symbols
	(:refer-clojure :exclude [alter drop
							  bigint boolean char double float time])
	;;; use only defmigration macro from lobos
	(:use (lobos [migration :only [defmigration]]
		   core
		   schema)))

;;; define the database for lobos migrations
(def clogdb
	(:classname   "org.postgresql.Driver"
	 :subprotocol "postgresql"
	 :subname     "clogdb"
	 :user        "clog"
	 :password    "lono123"))

;;; first migration to add the authors table
(defmigration add-posts-table
	(up [] (create clogdb
				(table :posts (integer :id :primary-key)
					(varchar   :title 250)
					(text      :content)
					(boolean   :status (default false))
					(timestamp :created (default (now)))
					(timestamp :published)
					(integer   :author [:refer :authors :id] :not-null))))
	(down [] (drop (table :posts))))