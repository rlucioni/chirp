(ns chirp.models.in-memory
  (:require [chirp.models :as models]))

(def authors-store (ref {}))
(def posts-store (ref []))

(defn assoc-unless-exists [map key val]
  (if (contains? map key)
    (throw (Exception. (str "Key " (pr-str key) " already exists in map.")))
    (assoc map key val)))

;; posts
(defmethod models/add-post *ns*
  [post]
  (dosync
   (alter posts-store
          (fn [posts]
            (let [id (count posts)]
              (conj posts (assoc post :id id)))))))

(defmethod models/post *ns*
  [id]
  (nth @posts-store id))

(defmethod models/posts *ns*
  ([]
     (rseq @posts-store))
  ([username]
     (filter #(= (-> % :author) username) (models/posts))))

;; authors
(defmethod models/add-author *ns*
  [author]
  (dosync
   (alter authors-store
          assoc-unless-exists (:username author) author)))

(defmethod models/author *ns*
  [username]
  (@authors-store username))

(defmethod models/author-exists? *ns*
  [username]
  (contains? @authors-store username))

;; util
(defn use-in-memory-backend! []
  (models/set-backend! (find-ns 'chirp.models.in-memory)))
