# chirp

A microblogging platform backed by PostgreSQL and written in Clojure.


## Configuration

First, install Leiningen (http://bit.ly/euQG6L). Then, after cloning the repository, run <pre>$ lein deps</pre> to download required dependencies.

Next, install PostgreSQL for your platform (http://bit.ly/IRbjJ). Create a user with login 
chirp and your choice of password. Create a database named chirpdb and make sure that the user has permissions on the database. You can perform this set-up by using the psql command line tool and running the following:

	CREATE USER chirp WITH PASSWORD 'Your_Choice_Of_Password';
 
	CREATE DATABASE chirpdb;
 
	GRANT ALL PRIVILEGES ON DATABASE chirpdb TO chirp;

Finally, create the required schema by using Lobos. In a Clojure REPL, run

	(use 'lobos.core 'lobos.connectivity 'lobos.migration 'lobos.migrations)
	(open-global chirpdb)
	(migrate)

Launch the app by running the following in the REPL:

	(use 'chirp.core)
	(start 8080)

You can now access the app at port 8080.


## License

Copyright (C) 2012 Renzo Lucioni

Distributed under the Eclipse Public License, the same as Clojure.
