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
	(->> (select posts (order :created :DESC)) home-page response)) ;;; sexy way of writing (response (home-page (select posts)))

;;; handler for the post page
(defn post
	"Post details page handler"
	[req id]
	(let [postId (Integer/parseInt id)]
		(->> (first (select posts (where {:id postId})))
			post-page response)))

;;; CRUDE handler for the login page - just checks if username = password
(defn login
	"Login handler"
	[req]
	(let [params (:params req)]
		;;; check if params are empty
		(if (empty? params)
			;;; if empty, render login page
			(response (login-page))
			;;; if not empty, check if username and password are blank
			(if (= "" (get params "username") (get params "password"))
				;;; if they're blank, render login page and complain
				(response (login-page "Invalid username or password."))
				;;; else, check if username and password match
				(if (= (get params "username") (get params "password"))
					;;; if match, redirect to admin page
				    (assoc (redirect "/admin") :session {:username (get params "username")})
				    ;;; no match, then render login page again and complain
				    (response (login-page "Invalid username or password.")))))))

;;; handler for logout
(defn logout
	"Logout handler"
	[req]
	(assoc (redirect "/") :session nil))

;;; handler for the admin page
(defn admin
	"Admin handler"
	[req]
	(let [username (:username (:session req))
		  params (:params req)]
		(if (nil? username)
			(redirect "/login")
			(do
				(if-not (empty? params)
					(let [id (inc (count (select posts)))
						  author-id (:id (first (select authors (fields :id) (where {:username username}))))]
					(insert posts (values (assoc params
											:id id
											:author author-id)))))
				(response (admin-page))))))