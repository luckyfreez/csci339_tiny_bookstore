all:
	javac Book.java
	javac -cp .:/usr/cs-local/339/apache-xmlrpc-3.1.2/lib/* CatalogServer.java
	javac -cp .:/usr/cs-local/339/apache-xmlrpc-3.1.2/lib/* OrderServer.java
	javac -cp .:/usr/cs-local/339/apache-xmlrpc-3.1.2/lib/* FrontEndServer.java
run_catalog:
	java -cp .:/usr/cs-local/339/apache-xmlrpc-3.1.2/lib/* CatalogServer
run_order:
	java -cp .:/usr/cs-local/339/apache-xmlrpc-3.1.2/lib/* OrderServer
run_frontend:
	java -cp .:/usr/cs-local/339/apache-xmlrpc-3.1.2/lib/* FrontEndServer
clean:
	rm -f *.class
