;; controller functions - connect views to requests
(ns chirp.controller
  (:use [chirp.templates :only (admin-page
                                home-page
                                login-admin-page
                                login-profile-page
                                post-page
                                profile-page
                                register-page)]
        [chirp.models :only (authors posts)]
        [ring.util.response :only (redirect response)]
        [korma.core :only (fields insert order select values where)]))

;; handler for the index page
(defn index
  "Index page handler"
  [req]
  (let [username (:username (:session req))
        params   (:params req)]
    (if (nil? username)
      ;; select all posts using Korma's select function, and then pass them on to
      ;; the home-page function along with a status message; the result of the home-page
      ;; template function - the HTML with posts populated - is passed to Ring's response function
      (response (home-page (select posts (order :created :DESC)) "Not logged in"))
      (response (home-page (select posts (order :created :DESC)) (str "Logged in as " username))))))

;; handler for the index page
(defn profile
  "Profile page handler"
  [req]
  (let [username (:username (:session req))
        params   (:params req)]
    (if (nil? username)
      (response (login-profile-page "Please log in to view your profile."))
      ;; select all posts belonging to the logged in user using Korma's select function, and then
      ;; pass them on to the profile page function; the result of the profile page function -
      ;; the HTML with posts populated - is passed to Ring's response function
      (response (profile-page (select posts (order :created :DESC) (where {:author username})) username)))))


;; handler for the post page
(defn post
  "Post details page handler"
  [req id]
  (let [postId   (Integer/parseInt id)
        username (:username (:session req))
        params   (:params req)]
    (if (nil? username)
      (response (post-page (first (select posts (where {:id postId}))) "Not logged in"))
      (response (post-page (first (select posts (where {:id postId}))) (str "Logged in as " username))))))

;; login handler - receieves logins redirecting to admin page
(defn login-admin
  "Login-admin handler"
  [req]
  (let [username (:username (:session req))
        params (:params req)]
    ;; make sure user isn't already logged in
    (if (nil? username)
      ;; if user is not logged in, check if params are empty
      (if (empty? params)
        ;; if empty, render login-admin page
        (response (login-admin-page))
        ;; if not empty, check if username and password are blank
        (if (= "" (get params "username") (get params "password"))
          ;; if they're blank, render login-admin page and complain
          (response (login-admin-page "Invalid username or password."))
          ;; else, check if username and password match
          (if (= (:password (first (select authors (fields :password) (where {:username (get params "username")})))) (get params "password"))
            ;; if match, redirect to admin page
            (assoc (redirect "/admin") :session {:username (get params "username")})
            ;; no match, then render login-admin page again and complain
            (response (login-admin-page "Invalid username or password.")))))
      ;; if user is already logged in, render login-admin page and complain
      (response (login-admin-page (str "You are already logged in as " username ". To log in as a different user, please log out of the current account by going to your Profile page."))))))

;; login handler - receieves logins redirecting to profile page
(defn login-profile
  "Login-profile handler"
  [req]
  (let [username (:username (:session req))
        params (:params req)]
    ;; make sure user isn't already logged in
    (if (nil? username)
      ;; if user is not logged in, check if params are empty
      (if (empty? params)
        ;; if empty, render login-profile page
        (response (login-profile-page))
        ;; if not empty, check if username and password are blank
        (if (= "" (get params "username") (get params "password"))
          ;; if they're blank, render login-profile page and complain
          (response (login-profile-page "Invalid username or password."))
          ;; else, check if username and password match
          (if (= (:password (first (select authors (fields :password) (where {:username (get params "username")})))) (get params "password"))
            ;; if match, redirect to admin page
            (assoc (redirect "/profile") :session {:username (get params "username")})
            ;; no match, then render login-profile page again and complain
            (response (login-profile-page "Invalid username or password.")))))
      ;; if user is already logged in, render login page and complain
      (response (login-profile-page (str "You are already logged in as " username ". To log in as a different user, please log out of the current account by going to your Profile page."))))))


;; utility function for checking if a sequence contains a given item
(defn seq-contains?
  "Determine whether a sequence contains a given item"
  [sequence item]
  (if (empty? sequence)
    false
    (reduce #(or %1 %2) (map #(= %1 item) sequence))))

;; registration handler
(defn register
  "Registration handler"
  [req]
  (let [params (:params req)]
    ;; check if params are empty
    (if (empty? params)
      ;; if empty, render regustration page
      (response (register-page))
      ;; if not empty, check if username is blank
      (if (= "" (get params "username"))
        ;; if it's blank, render register page and complain
        (response (register-page "Please enter a username."))
        ;; else, check if username is unique
        (if (seq-contains? (select authors (fields :username)) {:username (get params "username")})
          ;; if it's taken, render register page and complain
          (response (register-page "The username you entered is taken. Please try another."))
          ;; else, check if password is blank
          (if (= "" (get params "password"))
            ;; if it's blank, render register page and complain
            (response (register-page "Please enter a password."))
            ;; else, check if given passwords match
            (if (= (get params "password") (get params "password2"))
              ;; if they match, register new user and redirect to home page
              (do
                (insert authors (values {:id (inc (count (select authors))) :username (get params "username") :password (get params "password") :email (get params "email")}))
                (assoc (response (login-admin-page "Registration successful. Please log in.")) :session nil))
              ;; else, complain and render register page
              (response (register-page "The passwords you entered do not match. Please try again.")))))))))

;; handler for logout
(defn logout
  "Logout handler"
  [req]
  (assoc (redirect "/") :session nil))

;; handler for the admin page
(defn admin
  "Admin handler"
  [req]
  (let [username (:username (:session req))
        params   (:params req)]
    (if (nil? username)
      (response (login-admin-page "Please log in to post."))
      (do
        (if-not (empty? params)
          (let [id (inc (count (select posts)))]
            ;; author-id (:id (first (select authors (fields :id) (where {:username username}))))]
            (insert posts (values (assoc params
                                    :id id
                                    :author username)))))
        (response (admin-page (str "Logged in as " username)))))))