(ns clog.templates
	(:use [net.cgrand.enlive-html]))

;;; using the deftemplate macro to create a template function called home-page
;;; which takes the file home.html and transforms the file using the forms specified
(deftemplate home-page "home.html" []
	;;; form in use - find the tag :title in home.html and replace its contents with
	;;; the specified string
	[:title] (content "Clog - The Clojure Blogging Engine"))