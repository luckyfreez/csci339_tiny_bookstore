import buy_client
import search_client
import lookup_client
import threading

def create_thread(target):
    t = threading.Thread(target=target)
    t.daemon = True
    t.start()

for i in range(0, 500):
  #create_thread(search_client.run)
  #create_thread(lookup_client.run)
  create_thread(buy_client.run)


raw_input()
