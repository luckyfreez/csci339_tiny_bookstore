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
      if (stockCount >= delta) {
        System.out.println("old item num = " + stockCount);
        stockCount -= delta;
        System.out.println("new item num = " + stockCount);
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
