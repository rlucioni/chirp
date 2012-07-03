;; define two entities, authors and posts, using Korma's defentity macro
(ns chirp.models.sql
  (:use [korma.db :only (defdb)]
        [korma.core :only (defentity fields insert order select values where)])
  (:require [chirp.db.config :as config]
            [chirp.models :as models]
            [clojure.java.jdbc :as sql]))

(defdb chirpdb config/connection)

(defentity authors)
(defentity posts)

;; posts
(defmethod models/add-post *ns*
  [post]
  (let [id (inc (count (select posts)))]
    (insert posts (values (assoc post :id id)))))

(defmethod models/post *ns*
  [id]
  (first (select posts (where {:id id}))))

(defmethod models/posts *ns*
  ([]
     (select posts (order :created :DESC)))
  ([username]
     (select posts (order :created :DESC) (where {:author username}))))


;; authors
(defmethod models/add-author *ns*
  [author]
  (insert authors (values (assoc author :id (inc (count (select authors)))))))

(defmethod models/author *ns*
  [username]
  (first (select authors (fields :password) (where {:username username}))))

(defmethod models/author-exists? *ns*
  [username]
  (= (select authors (fields :username)) [{:username username}]))

;; util
(defn use-sql-backend! []
  (models/set-backend! (find-ns 'chirp.models.sql)))
