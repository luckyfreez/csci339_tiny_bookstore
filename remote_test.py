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


#Inputs two arguments. The first is what type, seq or thread.
# The second is the number of threads we want to run for.
def main():
    global f
    if len(sys.argv) >= 2:
      test_type = sys.argv[1]
      method_name = sys.argv[2]
      method = method_dict[method_name]

      if test_type == 'thread' and len(sys.argv) >=3:
        thread_num = int(sys.argv[3])
        f = open("log/%s_multiple_test3_on_%s_with_%d_threads\n" % (test_type, method_name, thread_num), 'w')
        repeat_thread_test(thread_num, method_name, method)
      elif test_type == 'seq':
        f = open("log/%s_test3_on_%s" % (test_type, method_name), 'w')
        repeat_seq_test(method_name, method)
    f.write("\n\n")

# Repeats the requests sequentually ranging from 10 to 10000. 
# Inputs the name of the method we want, and the method
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

# Repeats the requests on n number of threads.
# Input 1 is the number of threads. Inputs 2 and 3 are the method name and method.
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
          for t in threads:
            if not t.is_alive():
              threads.remove(t)

        f.write(str(repeat))
        f.write(" repetitions time: ")
        f.write(str(time.time() - start_time))
        f.write(" seconds \n")
        print ("finishing %d repeats")

# Creates the thread that runs the method request.
def create_thread(method, repeat):
    t = threading.Thread(target=repeat_target, kwargs={'method': method, 'repeat': repeat})
    t.daemon = True
    threads.append(t)
    t.start()

# Repeats the method for repeat number of times. 
def repeat_target(method=c.search, repeat=500):
    for i in range(0, repeat):
        method()

if __name__ == "__main__":main()

