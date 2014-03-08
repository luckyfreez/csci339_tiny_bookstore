import xmlrpclib
catalog_server_url = 'http://localhost:8592'
order_server_url = 'http://localhost:8593'

# Buy
server = xmlrpclib.Server(order_server_url)
print "server connected"
answer = server.orderServer.changeBookStockCount(53477, 2)
print ("Succeeded" if answer else "Failed")
