all:
	javac Book.java
	javac -cp .:/usr/cs-local/339/xmlrpc-3.1.3/lib/* CatalogServer.java
	javac -cp .:/usr/cs-local/339/xmlrpc-3.1.3/lib/* OrderServer.java
	javac -cp .:/usr/cs-local/339/xmlrpc-3.1.3/lib/* FrontEndServer.java
run_catalog:
	java -cp .:/usr/cs-local/339/xmlrpc-3.1.3/lib/* CatalogServer
run_order:
	java -cp .:/usr/cs-local/339/xmlrpc-3.1.3/lib/* OrderServer
run_frontend:
	java -cp .:/usr/cs-local/339/xmlrpc-3.1.3/lib/* FrontEndServer

run_alllocal_print:
	java -cp .:/usr/cs-local/339/xmlrpc-3.1.3/lib/* CatalogServer &
	java -cp .:/usr/cs-local/339/xmlrpc-3.1.3/lib/* OrderServer &
	java -cp .:/usr/cs-local/339/xmlrpc-3.1.3/lib/* FrontEndServer &

run_alllocal:
	java -cp .:/usr/cs-local/339/xmlrpc-3.1.3/lib/* CatalogServer > /dev/null &
	java -cp .:/usr/cs-local/339/xmlrpc-3.1.3/lib/* OrderServer > /dev/null &
	java -cp .:/usr/cs-local/339/xmlrpc-3.1.3/lib/* FrontEndServer > /dev/null &

clean:
	rm -f *.class
