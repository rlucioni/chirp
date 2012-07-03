(ns chirp.models)

(def ^:dynamic *backend* nil)

(defn get-backend [& args] *backend*)

(defn set-backend! [ns]
  (alter-var-root #'*backend* (fn [_] ns)))

;; posts
(defmulti add-post get-backend)
(defmulti post get-backend)
(defmulti posts get-backend)

;; authors
(defmulti add-author get-backend)
(defmulti author get-backend)
(defmulti author-exists? get-backend)
