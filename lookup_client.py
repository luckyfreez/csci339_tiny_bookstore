import xmlrpclib
catalog_server_url = 'http://localhost:8592'
order_server_url = 'http://localhost:8593'
front_end_url = 'http://localhost:8594'

def run():
  # lookup
  server = xmlrpclib.Server(front_end_url)
  print "server connected"
  result = server.frontendServer.queryLookup(12365);
  print result;

def main():
  run()

if  __name__ =='__main__':main()

