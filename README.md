# Chirp

A microblogging platform written in Clojure and backed by PostgreSQL.


## Configuration

In order to run Chirp, you must have Clojure installed and be able to start a REPL. If you haven't done this, the following is a good starting point: http://clojure.org/getting_started

To begin, install Leiningen (http://bit.ly/euQG6L). Then, after cloning the repository, run <pre>$ lein deps</pre> to download the project's required dependencies.

Next, install PostgreSQL for your platform (http://bit.ly/IRbjJ). Create a user with login 
chirp and your choice of password. Create a database named chirpdb and make sure that the user has permissions on the database. You can perform this set-up by using the psql command line tool and running the following:

	postgres=# CREATE USER chirp WITH PASSWORD 'Your_Choice_Of_Password';
	postgres=# CREATE DATABASE chirpdb;
	postgres=# GRANT ALL PRIVILEGES ON DATABASE chirpdb TO chirp;

Modify models.clj and migrations.clj, entering your new password to the chirpdb database where appropriate.

Now, migrate the required database schema. In a Clojure REPL, run

	user=> (use 'lobos.core 'lobos.connectivity 'lobos.migration 'lobos.migrations)
	user=> (open-global chirpdb)
	user=> (migrate)

N.B. You can use the (rollback) command to rollback the schema by one migration. Running (migrate) again will bring the schema back to the most current version.

With the schema migrated, you can now load the included fixtures to populate the database with some initial data. In the REPL, run

	user=> (use 'clj-yaml.core)
	user=> (use 'korma.db 'korma.core 'chirp.models)
	user=> (insert authors (values (:authors (parse-string (slurp "./resources/fixtures.yml")))))
	user=> (insert posts (values (:posts (parse-string (slurp "./resources/fixtures.yml")))))

Launch the app by running the following in the REPL:

	user=> (use 'chirp.core)
	user=> (start 8080)

You can now access the app at port 8080.


## License

Copyright (C) 2012 Renzo Lucioni

Distributed under the Eclipse Public License, the same as Clojure.