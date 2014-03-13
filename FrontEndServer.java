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

    public String querySearch(String topic) {
	System.out.println("Request received: Query search(" + topic +  ")");

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


    public Object[] queryLookup(int itemNum) {
	System.out.println("Request received: Query lookup(" + itemNum +  ")");

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

  public boolean buy(int itemNum) {
      System.out.println("Request received: buy(" + itemNum + ")");

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
  public static void main(String[] args) {
    catalogServerAddr = "http://" + ((args.length > 0) ? args[0] : "localhost");
    orderServerAddr = "http://" + ((args.length > 1) ? args[1] : "localhost");

    System.out.println("orderServerAddr = " +  orderServerAddr);
    System.out.println("catalogServerAddr = " +  catalogServerAddr);
    
    FrontEndServer.startFrontEndServer();
  }
}
