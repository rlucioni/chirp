(ns chirp.db.config)

(def connection
  {:classname   "com.mysql.jdbc.Driver"
   :subprotocol "mysql"
   :subname     "//chirpdbinstance.cvnjt4wjg36t.us-east-1.rds.amazonaws.com:3306/chirpdb"
   :user        "chirpuser"
   :password    "lono1234"})
