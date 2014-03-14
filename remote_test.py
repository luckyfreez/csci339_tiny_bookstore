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

c = client.Client()
lookup_results = {}
search_results = {}
buy_results = {}
threads = []

def main():
    """
    repeat_seq_test("Search", c.search)
    repeat_seq_test("Lookup", c.lookup)
    repeat_seq_test("Buy", c.buy)
    """
    #repeat_thread_test()
    repeat_seq_test()
    #c.add_new_book()
    #c.search()
    raw_input()

def repeat_seq_test(method_name = "search", method = c.search):
    f = open('sequential_times','w')
    f.write("Testing for " + method_name + "using sequential requests\n")
    times = [10,50,100,200,300,500,1000,5000]
    for repeat in times:
        start_time = time.time()
        repeat_target(method, repeat)
        f.write(str(repeat))
        f.write(" repetitions time: ")
        f.write(str(time.time() - start_time))
        f.write(" seconds \n")

def repeat_thread_test(thread_num=5, repeat=100, method_name="search", method=c.search):
    f = open('thread_times','w')
    f.write("Testing for " + method_name + "using parallel requests\n")
    times = [10,50,100,200,300,500,1000,5000]
    for repeat in times:
        threads = {}
        start_time = time.time()
        for i in range(0, thread_num):
            create_thread(method, repeat)

        # wait till all the 
        while threads:
          print ("%d threads still running" % len(threads)),
          for t in threads:
            if not t.is_alive():
              threads.remove(t)

        f.write(str(repeat))
        f.write(" repetitions time: ")
        f.write(str(time.time() - start_time))
        f.write(" seconds \n")

def create_thread(method, repeat):
    t = threading.Thread(target=repeat_target, kwargs={'method': method, 'repeat': repeat})
    t.daemon = True
    threads.append(t)
    t.start()

def repeat_target(method=c.search, repeat=500):
    for i in range(0, repeat):
        method()

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
