# Chirp

A microblogging platform written in Clojure and backed by PostgreSQL. This branch is configured for deployment on Heroku.


## Configuration

Before you begin, make sure you have a Heroku account, have installed the Heroku toolbelt, and have logged in.

First, create the app on Heroku’s Cedar stack by running

	$ heroku create --stack cedar

(Formerly, this step was <pre>$ heroku create --stack cedar</pre>, but the Cedar stack has since become the default runtime stack on Heroku.)

Since Chirp is a database-backed application, you need to provision a database for it to use. Use the Heroku PostgreSQL database add-on to provision a remote database:

	$ heroku addons:add shared-database

This will add a DATABASE_URL variable to the Chirp app’s environment, which the app will use at run-time to connect to its remote database resource.

Now that the app’s database has been provisioned, deploy Chirp’s code. Since the app has been added to a Git repository, code can be pushed to Heroku, as follows:

	$ git push heroku master

With Chirp’s code up on Heroku, run the database migration against the app’s newly provisioned database and load the included fixtures to populate the database with some initial data:

	$ heroku run lein repl
	...
	user=> (use 'lobos.core 'lobos.connectivity 'lobos.migration 'lobos.migrations 'lobos.config)
	...
	user=> (open-global chirpdb)
	...
	user=> (migrate)
	...
	user=> (use 'korma.db 'korma.core 'chirp.models 'clj-yaml.core)
	...
	user=> (insert authors (values (:authors (parse-string (slurp "./resources/fixtures.yml")))))
	...
	user=> (insert posts (values (:posts (parse-string (slurp "./resources/fixtures.yml")))))
	...

With Chirp’s database prepared, scale web processes up. This is as easy as

	$ heroku ps:scale web=1

Chirp is now up and running on Heroku. Check the status of the app’s process formation to confirm that the web process is up with

	$ heroku ps

Finally, view the finished, live application in a browser by running

	$ heroku open


## License

Copyright (C) 2012 Renzo Lucioni

Distributed under the Eclipse Public License, the same as Clojure.