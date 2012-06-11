(ns clog.templates
	(:use [net.cgrand.enlive-html]))

;;; using the deftemplate macro to create a template function called home-page
;;; which takes the file home.html and transforms the file using the forms specified
(deftemplate home-page "home.html" [posts]
	;;; form in use - find the tag :title in home.html and replace its contents with
	;;; the specified string
	[:title] (content "Clog - The Clojure-Powered Blogging Engine")
	;;; "clone" the div with class post and replace content of divs with classes title
	;;; and content
	[:div.post] (clone-for [post posts]
				[:span.title] (content (:title post))
				[:div.content] (content (:content post))))

;;; using the deftemplate macro to create a template function called post-page
;;; which takes the file post.html and transforms the file using the forms specified
(deftemplate post-page "post.html" [post]
	;;; form in use - find the tag :title in post.html and replace its contents with
	;;; the specified string
	[:title] (content (str "Clog - " (:title post)))
	[:span.title] (content (:title post))
	[:div.content] (html-content (:content post)))