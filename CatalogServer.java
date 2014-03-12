import org.apache.xmlrpc.webserver.WebServer;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.XmlRpcException;
import java.util.Hashtable;
import java.util.HashSet;
public class CatalogServer {
    static public Hashtable<Integer, Book> books;
    static public Hashtable<String, HashSet<Book> > topics;

    static private void initBooks() {
	System.out.println("initing books");

	books = new Hashtable<Integer, Book>();
	topics = new Hashtable<String, HashSet<Book> >();

	Book book = new Book("Achieving Less Bugs with More Hugs in CSCI 339",  // name
			     480,                          // stockCount
			     4.99f,                      // cost
			     "Distributed Systems",      // topic
			     53477);                   // itemNum
	books.put(book.itemNum, book);
	if (topics.get(book.topic) == null) {
	    topics.put(book.topic, new HashSet<Book>());
	}
	topics.get(book.topic).add(book);

	book = new Book("Distributed Systems for Dummies",                   // name
			9,                          // stockCount
			19.99f,                     // cost
			"Distributed Systems",      // topic
			53573);                    // itemNum
	books.put(book.itemNum, book);
	if (topics.get(book.topic) == null) {
	    topics.put(book.topic, new HashSet<Book>());
	}
	topics.get(book.topic).add(book);

	book = new Book( "Surviving College",                   // name
			 8,                          // stockCount
			 99.99f,                     // cost
			 "College Life",             // topic
			 12365);                    // itemNum
	books.put(book.itemNum, book);
	if (topics.get(book.topic) == null) {
	    topics.put(book.topic, new HashSet<Book>());
	}
	topics.get(book.topic).add(book);

	book = new Book( "Cooking for the Impatient Undergraduate",                   // name
			 8,                          // stockCount
			 13.98f,                     // cost
			 "College Life",             // topic
			 12498);                    // itemNum
                                 
	books.put(book.itemNum, book);
	if (topics.get(book.topic) == null) {
	    topics.put(book.topic, new HashSet<Book>());
	}
	topics.get(book.topic).add(book);
    }

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

    public boolean changeBookStockCount(int itemNum, int delta) {
	System.out.println("Request received: ChangeBookStockCount(" + itemNum +
			   ", " + delta + ")");
	return books.get(itemNum).changeStockCount(delta);
    }

    public String search(String topic) {
	System.out.println("Request received: Search(" +
			   topic + ")");
	String result = "";
	for (Book book : topics.get(topic)) {
	    result += book.name + ", " + book.itemNum + "\n";
	}
	return result;
    }

    public Object[] lookup(int itemNum){
	System.out.println("Request received: Lookup(" + itemNum + ")");

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
	//System.out.println("main is running");
	//CatalogServer catalogServer = new CatalogServer();
    
	CatalogServer.startCatalogServer();
	//OrderServer orderServer = new OrderServer();
    }
}
