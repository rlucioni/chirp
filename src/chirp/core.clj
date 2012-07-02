(ns chirp.core
  (:require [clojure.pprint :as pprint])
  (:use [ring.adapter.jetty :only (run-jetty)]
        [ring.middleware.keyword-params :only (wrap-keyword-params)]
        [ring.middleware.params :only (wrap-params)]
        [ring.middleware.resource :only (wrap-resource)]
        [ring.middleware.session :only (wrap-session)]
        [ring.middleware.session.cookie :only (cookie-store)]
        [net.cgrand.moustache :only (app delegate)]
        [chirp.controller :only (admin
                                 index
                                 login-admin
                                 login-profile
                                 logout
                                 post
                                 profile
                                 register)]))

(defn wrap-logger [handler]
  (fn [req]
    (pprint/pprint req)
    (handler req)))

;; from http://mmcgrana.github.com/2010/07/develop-deploy-clojure-web-applications.html
(defn wrap-bounce-favicon [handler]
  (fn [req]
    (if (= [:get "/favicon.ico"] [(:request-method req) (:uri req)])
      {:status 404
       :headers {}
       :body ""}
            (handler req))))

;; define routes
(def routes
  (app
   (wrap-bounce-favicon)
   (wrap-resource "public")
   (wrap-params)
   (wrap-keyword-params)
   (wrap-session {:cookie-name "chirp-session" :store (cookie-store)})
   (wrap-logger)

   ;; delegate call below is due to moustache syntax - function can't be used directly
   ;; as the handler, since we want the parameters of the handler (in this case "req")
   ;; to be passed to the function; so, delegate is used to pass the request as the
   ;; first argument

   ;; route for the login-profile page
   ["login-profile"] (delegate login-profile)
   ;; route for the login-admin page
   ["login-admin"] (delegate login-admin)
   ;; route for logout
   ["logout"] (delegate logout)
   ;; route for registration page
   ["register"] (delegate register)
   ;; route for the admin page
   ["admin"] (delegate admin)
   ;; route for profile page
   ["profile"] (delegate profile)
   ;; route for the homepage
   [""] (delegate index)
   ;; route for viewing posts
   [id] (delegate post id)))

;; function for starting jetty
(defn start [port]
  (run-jetty #'routes {:port (or port 8080) :join? false}))

(defn -main []
  (let [port (Integer/parseInt (System/getenv "PORT"))]
    (start port)))