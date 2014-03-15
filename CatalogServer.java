import org.apache.xmlrpc.webserver.WebServer;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.XmlRpcException;
import java.util.Hashtable;
import java.util.HashSet;
public class CatalogServer {

    // We are storing two hash tables using itemNumber and topics as the keys.
    static public Hashtable<Integer, Book> books;
    static public Hashtable<String, HashSet<Book> > topics;

    // Initializes our books and its stock.
    static private void initBooks() {
	System.out.println("initing books");

	books = new Hashtable<Integer, Book>();
	topics = new Hashtable<String, HashSet<Book> >();

	Book book = new Book("Achieving Less Bugs with More Hugs in CSCI 339",  // name
			     995000,                                            // stockCount
			     4.99f,                                             // cost
			     "Distributed Systems",                             // topic
			     53477);                                            // itemNum
	books.put(book.itemNum, book);
	if (topics.get(book.topic) == null) {
	    topics.put(book.topic, new HashSet<Book>());
	}
	topics.get(book.topic).add(book);

	book = new Book("Distributed Systems for Dummies",                   // name
			9,                                                   // stockCount
			19.99f,                                              // cost
			"Distributed Systems",                               // topic
			53573);                                              // itemNum
	books.put(book.itemNum, book);
	if (topics.get(book.topic) == null) {
	    topics.put(book.topic, new HashSet<Book>());
	}
	topics.get(book.topic).add(book);

	book = new Book( "Surviving College",                                // name
			 8,                                                  // stockCount
			 99.99f,                                             // cost
			 "College Life",                                     // topic
			 12365);                                             // itemNum
	books.put(book.itemNum, book);
	if (topics.get(book.topic) == null) {
	    topics.put(book.topic, new HashSet<Book>());
	}
	topics.get(book.topic).add(book);

	book = new Book( "Cooking for the Impatient Undergraduate",          // name
			 8,                                                  // stockCount
			 13.98f,                                             // cost
			 "College Life",                                     // topic
			 12498);                                             // itemNum

	books.put(book.itemNum, book);
	if (topics.get(book.topic) == null) {
	    topics.put(book.topic, new HashSet<Book>());
	}
	topics.get(book.topic).add(book);
    }

    // Starts the catalog server.
    public static void startCatalogServer() {
	try {
	    PropertyHandlerMapping phm = new PropertyHandlerMapping();
	    XmlRpcServer xmlRpcServer;
	    WebServer server = new WebServer(8592);
	    xmlRpcServer = server.getXmlRpcServer();
	    phm.addHandler("catalogServer", CatalogServer.class);
	    xmlRpcServer.setHandlerMapping(phm);
	    server.start();
	} catch (Exception exception) { System.err.println("CatalogServer: " + exception); }
    }

    // Input: item number and delta. Finds the book, and changes the stock of the book by delta.
    // This operation locks within the book class.
    public boolean changeBookStockCount(int itemNum, int delta) {
	return books.get(itemNum).changeStockCount(delta);
    }

    public boolean addNewBook(String bookName, int stockCount, double cost,
			      String topic, int itemNum) {
	Book book = new Book(bookName, stockCount, (float)cost, topic, itemNum);        
	if (books.contains(book.itemNum)) {
	    return false;  // The book has already existed.
	}
	books.put(book.itemNum, book);
	if (topics.get(book.topic) == null) {
	    topics.put(book.topic, new HashSet<Book>());
	}
	topics.get(book.topic).add(book);
	return true;
    }

    // Input: string topic. Returns the books with the input topic. 
    public String search(String topic) {
	String result = "";
	for (Book book : topics.get(topic)) {
	    result += book.name + ", " + book.itemNum + "\n";
	}
	return result;
    }

    // Input: int item number. Returns a list of objects that describes the book
    // including the stock, item number, topic, and price
    public Object[] lookup(int itemNum){
	Book book = books.get(itemNum);
	if(book == null){
	    return null;
	}
	Object[] result = new Object[4];
	result[0] = book.name;
	result[1] = book.getStockCount();
	result[2] = book.cost + "";
	result[3] = book.topic;
	return result;	
    }


    public static void main(String[] args) {
	initBooks();

	CatalogServer.startCatalogServer();
    }
}
