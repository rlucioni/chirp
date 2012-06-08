;;; controller functions - connect views to requests
(ns clog.controller
	(:use clog.templates
		  clog.models
		  ring.util.response
		  korma.core))

;;; handler for the index page
(defn index
	"Index page handler"
	[req]
	;;; select all posts using Korma's select function, and then pass them on to
	;;; the homepage function; the result of the home-page template function - 
	;;; the HTML with posts populated - is passed to the Ring's response function

	(->> (select posts) home-page response)) ;;; sexy way of writing (reponse (home-page (select posts)))