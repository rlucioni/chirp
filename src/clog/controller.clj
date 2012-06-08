;;; controller functions - connect views to requests
(ns clog.controller
	(:use clog.templates
		  clog.models
		  ring.util.response))

;;; handler for the index page
(defn index
	"Index page handler"
	[req]
	(->> (home-page) response)) ;; sexy way of writing (reponse (home-page))