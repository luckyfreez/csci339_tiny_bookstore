all:
	javac -cp .:/usr/cs-local/339/apache-xmlrpc-3.1.2/lib/* OrderServer.java
	javac -cp .:/usr/cs-local/339/apache-xmlrpc-3.1.2/lib/* CatalogServer.java
clean:
	rm -f *.class
