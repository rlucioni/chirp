(ns chirp.templates
	(:use net.cgrand.enlive-html))

;;; using the deftemplate macro to create a template function called home-page
;;; which takes the file home.html and transforms the file using the forms specified
(deftemplate home-page "home.html" [posts & msg]
	;;; form in use - find the tag :title in home.html and replace its contents with
	;;; the specified string
	[:title] (content "Chirp - Clojure-Powered Microblogging")
	;;; show if the current user is logged in or not, and if so, as who
	[:b.status] (content msg)
	;;; "clone" the div with class post and replace content of divs with classes title
	;;; and content
	[:div.post] (clone-for [post posts]
				[:a.title] (do->
							(set-attr :href (str "/" (:id post)))
							(content (:title post)))
				[:b.author] (html-content (:author post))
				[:b.time] (html-content (:created post))
				[:div.content] (html-content (:content post))))

;;; using the deftemplate macro to create a template function called profile-page
;;; which takes the file profile.html and transforms the file using the forms specified
(deftemplate profile-page "profile.html" [posts & msg]
	;;; form in use - find the tag :title in profile.html and replace its contents with
	;;; the specified string
	[:title] (content msg)
	;;; show if the current user is logged in or not, and if so, as who
	[:b.username] (content msg)
	;;; "clone" the div with class post and replace content of divs with classes title
	;;; and content
	[:div.post] (clone-for [post posts]
				[:a.title] (do->
							(set-attr :href (str "/" (:id post)))
							(content (:title post)))
				[:b.author] (html-content (:author post))
				[:b.time] (html-content (:created post))
				[:div.content] (html-content (:content post))))

;;; using the deftemplate macro to create a template function called post-page
;;; which takes the file post.html and transforms the file using the forms specified
(deftemplate post-page "post.html" [post & msg]
	;;; form in use - find the tag :title in post.html and replace its contents with
	;;; the specified string
	[:title] (content (str "Chirp - " (:title post)))
	;;; show if the current user is logged in or not, and if so, as who
	[:b.status] (content msg)
	;;; replace other relevant portions of the page
	[:span.title] (content (:title post))
	[:b.author] (html-content (:author post))
	[:b.time] (html-content (:created post))
	[:div.content] (html-content (:content post)))

;;; handler for the login page in front of the admin page - check to see if any message 
;;; is passed to the template method
(deftemplate login-admin-page "login-admin.html" [& msg]
  [:div#error] (if (nil? msg)
                  (set-attr :style "display:none")
                  (do->
                    (remove-attr :style)
                    (content msg))))

;;; handler for the login page in front of the profile page - check to see if any message 
;;; is passed to the template method
(deftemplate login-profile-page "login-profile.html" [& msg]
  [:div#error] (if (nil? msg)
                  (set-attr :style "display:none")
                  (do->
                    (remove-attr :style)
                    (content msg))))

;;; handler for the registration page - check to see if any message is passed to the 
;;; template method
(deftemplate register-page "register.html" [& msg]
  [:div#error] (if (nil? msg)
                  (set-attr :style "display:none")
                  (do->
                    (remove-attr :style)
                    (content msg))))

;;; use the admin page
(deftemplate admin-page "admin.html" [& msg]
	;;; show if the current user is logged in or not, and if so, as who
	[:b.status] (content msg))