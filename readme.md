Implemented using java 8 and Spring MVC

The project comes with a pom.xml file to build using Maven.

The source is in three parts 
- main which contains the implementation of the controller and its dependencies,
- test which contains the unit test implementation and 
- integrationtest which contains the integration tests used by Maven. 
The integration testing uses Jetty as the container.

Run 
    mvn verify
to compile, unittest, package and integration test the service.

Run
    mvn jetty:run
to run the examples manually

The war file generated in the package process can be deployed to Tomcat or other containers.
Note: if it is not deployed as the ROOT webapp in Tomcat you will have to include the war name in the url e.g
    http://localhost:8080/primes/primes/10.json

Two algorithms for generating prime numbers have been implemented
- the division algorithm
- the sieve of Eratosthenes algorithm.
The division algorithm is used by default.

Implementations of basic java 8 parallel stream and caching examples are also included.

The response will be by default in json but will also be in XML format when requested.

The standard request
	http://localhost:8080/primes/10
will use the division algorithm and return the values in json.

Most browsers by default ask for the response in XML format so to get json use e.g.
	http://localhost:8080/primes/10.json

To get XML use e.g.
	http://localhost:8080/primes/10.xml


To use the sieve of Eratosthenes algorithm add the optional variable opt=S e.g.
	http://localhost:8080/primes/10?opt=S


To execute the java 8 parallel stream implementation use opt=DS e.g.
	http://localhost:8080/primes/10000?opt=DS



A second implementation has been included using Guava Cache from Google.
To access this implementation replace primes with primesCache in the URL e.g.
	http://localhost:8080/primesCache/1002.json

This implementation supports options D (the default) and S e.g.
	http://localhost:8080/primesCache/1002.json?opt=D
	http://localhost:8080/primesCache/1002.json?opt=S

There is a service to get the stats of the caches. Use
	localhost:8080/primesCache/cacheStats?opt=D
	localhost:8080/primesCache/cacheStats?opt=S



