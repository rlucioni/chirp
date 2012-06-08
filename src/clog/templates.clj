(ns clog.templates
	(:use [net.cgrand.enlive-html]))

;;; using the deftemplate macro to create a template function called home-page
;;; which takes the file home.html and transforms the file using the forms specified
(deftemplate home-page "home.html" [posts]
	;;; form in use - find the tag :title in home.html and replace its contents with
	;;; the specified string
	[:title] (content "Clog - The Clojure Blogging Engine")
	;;; "clone" the div with class post and replace content of divs with classes title
	;;; and content
	[:div.post] (clone-for [post posts]
				[:div.title] (content (:title post))
				[:div.content (content (:content post))]))