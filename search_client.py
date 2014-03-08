import xmlrpclib
catalog_server_url = 'http://localhost:8592'
order_server_url = 'http://localhost:8593'

# search
server = xmlrpclib.Server(catalog_server_url)
print "server connected"
result = server.catalogServer.search("Distributed Systems");
print result;
