;; define two entities, authors and posts, using Korma's defentity macro
(ns chirp.models.in-memory)

(def authors-store (ref {}))
(def posts-store (ref []))

(defn posts
  ([]
     (rseq @posts-store))
  ([username]
     (filter #(= (-> % :author) username) (posts))))

(defn post [id]
  (nth @posts-store id))

(defn author [username]
  (@authors-store username))

(defn author-exists? [username]
  (contains? @authors-store username))

(defn assoc-unless-exists [map key val]
  (if (contains? map key)
    (throw (Exception. (str "Key " (pr-str key) " already exists in map.")))
    (assoc map key val)))

(defn add-author [author]
  (dosync
   (alter authors-store
          assoc-unless-exists (:username author) author)))

(defn add-post [post]
  (dosync
   (alter posts-store
          (fn [posts]
            (let [id (count posts)]
              (conj posts (assoc post :id id)))))))