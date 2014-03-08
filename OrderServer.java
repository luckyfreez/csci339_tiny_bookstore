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

  static public void startOrderServer() {

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

  public boolean changeBookStockCount(int itemNum, int delta) {
    System.out.println("Request received: ChangeBookStockCount(" + itemNum +
                       ", " + delta + ")");

    XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
    XmlRpcClient client = null;
    try {
      config.setServerURL(new URL("http://localhost:8592"));
      client = new XmlRpcClient();
      client.setConfig(config);
    } catch (Exception e) { System.err.println("Problem! "+ e); }

    Object[] params = new Object[2];
    params[0] = itemNum;
    params[1] = delta;

    try {
      Boolean result = (Boolean) client.execute("catalogServer.changeBookStockCount", params);
      return result;
    } catch (Exception exception) { System.err.println("OrderServer Client: " + exception); }
    return false;
  }
}
