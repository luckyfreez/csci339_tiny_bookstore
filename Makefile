all:
	javac Book.java
	javac -cp .:/usr/cs-local/339/apache-xmlrpc-3.1.2/lib/* CatalogServer.java
	javac -cp .:/usr/cs-local/339/apache-xmlrpc-3.1.2/lib/* OrderServer.java
run:
	java -cp .:/usr/cs-local/339/apache-xmlrpc-3.1.2/lib/* CatalogServer
clean:
	rm -f *.class
