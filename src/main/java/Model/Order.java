package Model;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = 2744129137947734243L;
    private int id;
    private Client client;
    private ArrayList<MenuItem> items;
    private LocalDateTime orderDate;

    public Order(int id, Client client, ArrayList<MenuItem> items) {
        this.id = id;
        this.client = client;
        this.items = items;
        this.orderDate = LocalDateTime.now();
    }

    public int hashCode(){
        return new HashCodeBuilder(13,17).append(id).append(client).append(items).append(orderDate).toHashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return id == order.id && client.equals(order.client) && items.equals(order.items) && orderDate.equals(order.orderDate);
    }

    public ArrayList<MenuItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", client=" + client.toString() +
                ", items=" + items.toString() +
                ", orderDate=" + orderDate +
                '}';
    }

    public int getId(){
        return id;
    }

    public LocalDateTime getOrderDate(){
        return orderDate;
    }

    public Client getClient() {
        return client;
    }

    public int computePrice(){
        int sum = 0;
        for(MenuItem menuItem:items){
            sum+= menuItem.getPrice();
        }
        return sum;
    }
}
