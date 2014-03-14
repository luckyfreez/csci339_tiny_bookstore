import xmlrpclib
import sys
import random

class Client:
  def __init__(self, front_end_url="localhost", catalog_url="localhost"):
    self.front_end_url = "http://" + front_end_url + ":8594"
    self.catalog_url = "http://" + catalog_url + ":8592"

  def buy(self,  item_num=53477):
    server = xmlrpclib.Server(self.front_end_url)
    result = server.frontendServer.buy(item_num)
    print "Buy" ("Succeeded" if result else "Failed")
  
  def search(self, topic="Distributed Systems"):
    server = xmlrpclib.Server(self.front_end_url)
    result = server.frontendServer.querySearch(topic);
    print result;
  
  def lookup(self, item_num=12365):
    server = xmlrpclib.Server(self.front_end_url)
    result = server.frontendServer.queryLookup(item_num);
    print result;

  def update_stock_num(self, item_num=53477, delta=1):
    server = xmlrpclib.Server(self.catalog_url)
    result = server.catalogServer.changeBookStockCount(item_num, delta)
    print "Update Stock Num", ("Succeeded" if result else "Failed")

  def add_new_book(self):
    server = xmlrpclib.Server(self.catalog_url)
    new_book = [ 'Book #%06x' % random.randrange(16**6),  # book name (rand string) \
                 random.randrange(500),  # stock count (rand number) \
                 random.randrange(20000) / 100.0,  # cost (random number) \
                 "Distributed Systems" if random.randrange(2) else "College Life",  # topic \
                 random.randrange(10000,100000) ]  # item num
    result = server.catalogServer.addNewBook(*new_book)
    print "Added New Book", new_book[0], ("Succeeded" if result else "Failed")
