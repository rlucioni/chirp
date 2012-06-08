(ns clog.core
	(:use ring.adapter.jetty
	      ring.middleware.resource
	      ring.middleware.reload
	      ring.util.response
	      net.cgrand.moustache
	      clog.controller))

;;; define routes
(def routes
	(app
		;;; delegate call is due to moustache syntax - function can't be used directly
		;;; as the handler, since we want the parameters of the handler (in this case "req")
		;;; to be passed to the function; so, delegate is used to pass the request as the
		;;; first argument
		[""] (delegate index)))

;;; function for starting jetty
(defn start [port]
	(run-jetty #'routes {:port (or port 8080) :join? false}))

(defn -main []
	(let [port (Integer/parseInt (System/getenv "PORT"))]
		(start port)))