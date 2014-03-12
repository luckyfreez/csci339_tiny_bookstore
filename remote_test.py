import buy_client
import search_client
import lookup_client
import threading
import getpass
import os
import pexpect
import client

def create_thread(target):
    t = threading.Thread(target=target)
    t.daemon = True
    t.start()

c = client.Client()
for i in range(0, 500):
  create_thread(c.lookup)
  create_thread(c.search)
  create_thread(c.buy)

#"beja"
#"tropicana"
#"chillingham"
#def create_remote(server_name, cmd):
#  hostname = server_name
#  username = "14zz2"
#  password = getpass.getpass('password: ')
#  child = pexpect.spawn("ssh " + username + "@" + hostname, timeout=4000)
#  child.expect (pexpect.EOF)
#  child.sendline(password)        
#  child.expect (pexpect.EOF)
#  child.sendline("~/339/1_web_server/./server -document_root ~/339/1_web_server/ -port 8897")
#  child.expect (pexpect.EOF)
#  print child.before
#  
#  #s.sendline()
#create_remote("beja", "pwd")
#raw_input()
