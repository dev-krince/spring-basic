package hello.core;

public class StatefulService {

    private int price;

    public void order(String name, int price) {
        System.out.println("name: " + name + ", price: " + price);
        this.price = price;
    }

    public int statelessOrder(String name, int price) {
        System.out.println("name: " + name + ", price: " + price);

        return price;
    }

    public int getPrice() {
        return price;
    }
}
