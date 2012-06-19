;;; defines a namespace migration with one var chirpdb which is a map of database
;;; connection information
(ns lobos.migrations
	;;; exclude some clojure built-in symbols so we can use the lobos symbols
	(:refer-clojure :exclude [alter drop
							  bigint boolean char double float time])
	;;; use only defmigration macro from lobos
	(:use (lobos [migration :only [defmigration]]
		   core
		   schema)))

;;; define the database for lobos migrations
(def chirpdb
	{:classname   "org.postgresql.Driver"
	 :subprotocol "postgresql"
	 :subname     "dfyohnwqis" ;;;"chirpdb"
	 :user        "dfyohnwqis" ;;;"chirp"
	 :password    "Z4vPDD5H0TiKhY69DyTd" ;;;"lono123"
	 })

;;; first migration to create the authors table
(defmigration add-authors-table
	;;; to be executed when migrating schema "up" using "migrate"
	(up [] (create chirpdb
				(table :authors 
					(integer :id :primary-key)
					(varchar :username 100 :unique)
					(varchar :password 100 :not-null)
					(varchar :email    255))))
	;;; to be executed when migrating schema "down" using "rollback"
	(down [] (drop (table :authors))))

;;; migration to create the posts table
(defmigration add-posts-table
	;;; to be executed when migrating schema "up" using "migrate"
	(up [] (create chirpdb
				(table :posts 
					(integer   :id :primary-key)
					(varchar   :title 250)
					(text      :content)
					(boolean   :status (default false))
					(timestamp :created (default (now)))
					(timestamp :published)
					(varchar   :author [:refer :authors :username] :not-null))))
	;;; to be executed when migrating schema "down" using "rollback"
	(down [] (drop (table :posts))))