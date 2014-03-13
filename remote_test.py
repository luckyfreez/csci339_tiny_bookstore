import buy_client
import search_client
import lookup_client
import threading
import getpass
import os
import pexpect
import client
import sys
import time

c = client.Client("rath")
lookup_results = {}
search_results = {}
buy_results = {}
f = open('sequential_times','w')

def main():
    """
    repeat_test("Search", c.search)
    repeat_test("Lookup", c.lookup)
    repeat_test("Buy", c.buy)
    """
    thread_test()
    raw_input()

def repeat_test(method_name = "search", method = c.search):
    f.write("Testing for " + method_name + "\n")
    times = [10,50,100,200,300,500,1000,5000]
    for repeat in times:
        start_time = time.time()
        sequential_test(repeat, method)
        f.write(str(repeat))
        f.write(" repetitions time: ")
        f.write(str(time.time() - start_time))
        f.write(" seconds \n")


def create_thread(target, repeat):
    t = threading.Thread(target=repeat_target, kwargs={'target': target, 'repeat': repeat})
    t.daemon = True
    t.start()

def repeat_target(target, repeat):
    for i in range(0, repeat):
        target()


def sequential_test(repeat = 500,method=c.search):
    for i in range(0, repeat):
        """
        c.lookup()
        c.search()
        c.buy()
        """
        method()

def thread_test(thread_num=5, repeat = 100, method=c.buy):
    for i in range(0, thread_num):
        #create_thread(c.lookup, repeat)
        #create_thread(c.search, repeat)
        create_thread(method, repeat)
        

if __name__ == "__main__":main()


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
