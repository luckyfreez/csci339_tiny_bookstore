// Import for client
import java.net.URL;
import org.apache.xmlrpc.*;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

// Import for server
import org.apache.xmlrpc.webserver.WebServer;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.XmlRpcException;

public class FrontEndServer {
    private static String catalogServerAddr = "";
    private static String orderServerAddr = "";

    // Starts the server
    public static void startFrontEndServer() {
	try {
	    PropertyHandlerMapping phm = new PropertyHandlerMapping();
	    XmlRpcServer xmlRpcServer;
	    WebServer server = new WebServer(8594);
	    xmlRpcServer = server.getXmlRpcServer();
	    phm.addHandler("frontendServer", FrontEndServer.class);
	    xmlRpcServer.setHandlerMapping(phm);
	    server.start();
	} catch (Exception exception) { System.err.println("FrontEndServer: " + exception); }
    }

    // Input: a topic string. Calls the Catalog server to search for topic
    public String querySearch(String topic) {
	XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
	XmlRpcClient client = null;
	try {
	    config.setServerURL(new URL(catalogServerAddr + ":8592"));
	    client = new XmlRpcClient();
	    client.setConfig(config);
	} catch (Exception e) { System.err.println("Problem! "+ e); }

	Object[] params = new Object[1];
	params[0] = topic;


	try {
	    String result = (String) client.execute("catalogServer.search", params);
	    return result;
	} catch (Exception exception) { System.err.println("FrontendServer Client search: " + exception); }
	return "Could not find";
    }

    // Input: item number int. Calls the catalog server to search for the item number
    public Object[] queryLookup(int itemNum) {
	XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
	XmlRpcClient client = null;
	try {
	    config.setServerURL(new URL(catalogServerAddr + ":8592"));
	    client = new XmlRpcClient();
	    client.setConfig(config);
	} catch (Exception e) { System.err.println("Problem! "+ e); }

	Object[] params = new Object[1];
	params[0] = itemNum;

	try {
	    Object[] result = (Object[]) client.execute("catalogServer.lookup", params);
	    if(result == null){
		System.err.println("FrontendServer Client lookup: Book with item Number: "
				   + itemNum + " is not found");
	    }
	    return result;
	} catch (Exception exception) { System.err.println("FrontendServer Client lookup: " + exception); }
	return null;
    }

    // Input: item number int. Calls the order server to buy a book. If book is out of stock, returns false, else true.
    public boolean buy(int itemNum) {
	XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
	XmlRpcClient client = null;
	try {
	    config.setServerURL(new URL(orderServerAddr + ":8593"));
	    client = new XmlRpcClient();
	    client.setConfig(config);
	} catch (Exception e) { System.err.println("Problem! "+ e); }

	Object[] params = new Object[1];
	params[0] = itemNum;

	try {
	    Boolean result = (Boolean) client.execute("orderServer.buy", params);
	    return result;
	} catch (Exception exception) { System.err.println("Frontend Client: " + exception); }
	return false;
    }

    // Inputs: CatalogServer hostname, OrderServer hostname. Defaults to local host.
    public static void main(String[] args) {
	catalogServerAddr = "http://" + ((args.length > 0) ? args[0] : "localhost");
	orderServerAddr = "http://" + ((args.length > 1) ? args[1] : "localhost");
    
	FrontEndServer.startFrontEndServer();
    }
}
