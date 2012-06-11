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

;;; handler for the post page
(defn post
	"Post details page handler"
	[req id]
	(let [postId (Integer/parseInt id)]
		(->> (first (select posts (where {:id postId})))
			post-page response)))

;;; handler for the login page
(defn login
	"Login handler"
	[req]
	(let [params (:params req)]
		;;; check if params are empty
		(if (empty? params)
			;;; if empty, render login page
			(reponse (login-page))
			;;; if not empty, check if username and password are blank
			(if (= "" (get params "username") (get params "password"))
				;;; if they're blank, render login page and complain
				(reponse (login-page "Invalid username or password."))
				;;; else, check if username and password match
				(if (= (get params "username") (get params "password"))
					;;; if match, redirect to admin page
				    (redirect "/admin")
				    ;;; no match, then render login page again and complain
				    (reponse (login-page "Invalid username or password.")))))))