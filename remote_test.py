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
threads_num = []

method_dict = {"search": c.search, "lookup": c.lookup, "buy": c.buy}

def main():
    """
    repeat_seq_test("Search", c.search)
    repeat_seq_test("Lookup", c.lookup)
    repeat_seq_test("Buy", c.buy)
    """
    global f
    if len(sys.argv) >= 2:
      test_type = sys.argv[1]
      method_name = sys.argv[2]
      method = method_dict[method_name]

      if test_type == 'thread' and len(sys.argv) >=3:
        thread_num = int(sys.argv[3])
        f = open("log/%s_multiple_test2_on_%s_with_%d_threads\n" % (test_type, method_name, thread_num), 'w')
        repeat_thread_test(thread_num, method_name, method)
      elif test_type == 'seq':
        f = open("log/%s_test2_on_%s" % (test_type, method_name), 'w')
        repeat_seq_test(method_name, method)
    f.write("\n\n")




def repeat_seq_test(method_name = "search", method = c.search):
    f.write("Testing for " + method_name + "using sequential requests\n")
    times = [10,50,100,200,300,500,1000,5000,10000]
    for repeat in times:
        f.write("Testing with %d repeats\n" % repeat)
        start_time = time.time()
        repeat_target(method, repeat)
        f.write(str(repeat))
        f.write(" repetitions time: ")
        f.write(str(time.time() - start_time))
        f.write(" seconds \n")

def repeat_thread_test(thread_num=10, method_name="search", method=c.search):
    f.write("Testing for " + method_name + " using parallel requests\n")
    times = [10,50,100,200,500,1000,2000,5000]
    for repeat in times:
        f.write("Testing with %d repeats\n" % repeat)
        global threads
        threads = []
        start_time = time.time()
        for i in range(0, thread_num):
            create_thread(method, repeat)

        # wait till all the threads finish
        while threads:
          #print ("\r%d threads still running with total repeat %d" % (len(threads), repeat)),
          for t in threads:
            if not t.is_alive():
              threads.remove(t)

        f.write(str(repeat))
        f.write(" repetitions time: ")
        f.write(str(time.time() - start_time))
        f.write(" seconds \n")
        print ("finishing %d repeats")

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
