import xmlrpclib
import sys

class Client:
  def __init__(self, front_end_url="localhost"):
    self.front_end_url = "http://" + front_end_url + ":8594"

  def buy(self,  item_num=53477):
    server = xmlrpclib.Server(self.front_end_url)
    result = server.frontendServer.buy(item_num)
    print ("Succeeded" if result else "Failed")
  
  def search(self, topic="Distributed Systems"):
    server = xmlrpclib.Server(self.front_end_url)
    result = server.frontendServer.querySearch(topic);
    print result;
  
  def lookup(self, item_num=12365):
    server = xmlrpclib.Server(self.front_end_url)
    result = server.frontendServer.queryLookup(item_num);
    print result;

