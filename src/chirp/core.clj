(ns chirp.core
	(:use ring.adapter.jetty
	      ring.middleware.resource
	      ring.middleware.reload
	      ring.middleware.file
	      ring.middleware.params
	      ring.middleware.session
	      ring.middleware.session.cookie
	      ring.util.response
	      net.cgrand.moustache
	      chirp.controller))

;;; define routes
(def routes
	(app
		(wrap-file "resources/public")
		(wrap-params)
		(wrap-session {:cookie-name "chirp-session" :store (cookie-store)})

		;;; delegate call below is due to moustache syntax - function can't be used directly
		;;; as the handler, since we want the parameters of the handler (in this case "req")
		;;; to be passed to the function; so, delegate is used to pass the request as the
		;;; first argument

		;;; route for the login page
		["login"] (delegate login)
		;;; route for logout
		["logout"] (delegate logout)
		;;; route for registration page
		["register"] (delegate register)
		;;; route for the admin page
		["admin"] (delegate admin)
		;;; route for profile page
		["profile"] (delegate profile)
		;;; route for the homepage
		[""] (delegate index)
		;;; route for viewing posts
		[id] (delegate post id)))

;;; function for starting jetty
(defn start [port]
	(run-jetty #'routes {:port (or port 8080) :join? false}))

(defn -main []
	(let [port (Integer/parseInt (System/getenv "PORT"))]
		(start port)))