(defproject chirp-application "0.0.1-SNAPSHOT"
  :description "Chirp: a microblogging engine written in Clojure"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [ring "1.1.1"]
                 [net.cgrand/moustache "1.1.0"]
                 [lobos "1.0.0-SNAPSHOT"]
                 [korma "0.2.1"]
                 [enlive "1.0.0"]
                 [org.clojure/java.jdbc "0.0.6"]
                 [mysql/mysql-connector-java "5.1.6"]]
  :plugins [[lein-beanstalk "0.2.2"]]
  :ring {:handler chirp.core/routes})
