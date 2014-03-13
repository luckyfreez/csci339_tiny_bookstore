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

public class OrderServer {
  
  private static String catalogServerAddr = "";

  public static void startOrderServer() {

    try {
      PropertyHandlerMapping phm = new PropertyHandlerMapping();
      XmlRpcServer xmlRpcServer;
      WebServer server = new WebServer(8593);
      xmlRpcServer = server.getXmlRpcServer();
      phm.addHandler("orderServer", OrderServer.class);
      xmlRpcServer.setHandlerMapping(phm);
      server.start();
    } catch (Exception exception) { System.err.println("OrderServer: " + exception); }
  }

  public boolean buy(int itemNum) {
      System.out.println("Request received: buy(" + itemNum + ")");

    XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
    XmlRpcClient client = null;
    try {
      config.setServerURL(new URL(catalogServerAddr + ":8592"));
      client = new XmlRpcClient();
      client.setConfig(config);
    } catch (Exception e) { System.err.println("Problem! "+ e); }

    Object[] params = new Object[2];
    params[0] = itemNum;
    params[1] = 1;

    try {
      Boolean result = (Boolean) client.execute("catalogServer.changeBookStockCount", params);
      return result;
    } catch (Exception exception) { System.err.println("OrderServer Client: " + exception); }
    return false;
  }
  public static void main(String[] args) {
      catalogServerAddr = "http://" + ((args.length > 0) ? args[0] : "localhost");
    
      OrderServer.startOrderServer();
  }
}
