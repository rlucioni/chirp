(ns chirp.templates
	(:use net.cgrand.enlive-html))

;;; using the deftemplate macro to create a template function called home-page
;;; which takes the file home.html and transforms the file using the forms specified
(deftemplate home-page "home.html" [posts]
	;;; form in use - find the tag :title in home.html and replace its contents with
	;;; the specified string
	[:title] (content "Chirp - Clojure-Powered Microblogging")
	;;; "clone" the div with class post and replace content of divs with classes title
	;;; and content
	[:div.post] (clone-for [post posts]
				[:a.title] (do->
							(set-attr :href (str "/" (:id post)))
							(content (:title post)))
				[:b.time] (html-content (:created post))
				[:div.content] (html-content (:content post))))

;;; using the deftemplate macro to create a template function called post-page
;;; which takes the file post.html and transforms the file using the forms specified
(deftemplate post-page "post.html" [post]
	;;; form in use - find the tag :title in post.html and replace its contents with
	;;; the specified string
	[:title] (content (str "Chirp - " (:title post)))
	[:span.title] (content (:title post))
	[:b.time] (html-content (:created post))
	[:div.content] (html-content (:content post)))

;;; handler for the login page - check to see if any message is passed to the 
;;; template method
(deftemplate login-page "login.html" [& msg]
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
(deftemplate admin-page "admin.html" [])