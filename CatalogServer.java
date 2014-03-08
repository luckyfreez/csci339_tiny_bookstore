import org.apache.xmlrpc.webserver.WebServer;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.XmlRpcException;
public class CatalogServer {
  public Integer[] SumAndDifference(int x, int y) {
    Integer[] array = new Integer[2];
    array[0] = new Integer(x+y);
    array[1] = new Integer(y-x);
    return array;
  }
  public static void main (String [] args) {
    try {
      PropertyHandlerMapping phm = new PropertyHandlerMapping();
      XmlRpcServer xmlRpcServer;
      WebServer server = new WebServer(8888);
      xmlRpcServer = server.getXmlRpcServer();
      phm.addHandler("sample", CatalogServer.class);
      xmlRpcServer.setHandlerMapping(phm);
      server.start();
    } catch (Exception exception) { System.err.println("Server: " + exception); }
  }
}
