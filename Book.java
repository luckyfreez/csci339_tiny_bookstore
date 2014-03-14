public class Book {
  public String name;
  private int stockCount;
  public float cost;
  public String topic;
  public int itemNum;

  private final Object stockCountLock = new Object();

  public Book(String name, int stockCount, float cost, String topic, int itemNum) {
    this.name = name;
    this.stockCount = stockCount;
    this.cost = cost;
    this.topic = topic;
    this.itemNum = itemNum;
  }

  public boolean changeStockCount(int delta) {
    synchronized (stockCountLock) {
      if (stockCount + delta >= 0) {
	  
        stockCount += delta;

        return true;
      } else {
        return false;
      }
    }
  }

  public int getStockCount() {
    return stockCount;
  }

  public static void main(String[] args) {
    Book b = new Book("haha", 1, 1.0f, "a", 2);
  }
}
