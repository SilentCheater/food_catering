package BLL;

import Model.BaseProduct;
import Model.Client;
import Model.MenuItem;
import Model.Order;
import Presentation.ClientGUI;

import java.util.ArrayList;
import java.util.HashSet;

public interface IDeliveryServiceProcessing {

    HashSet<MenuItem> importProducts();
    void addProduct(MenuItem menuItem);
    void editProduct(MenuItem menuItem, String title, double rating, int calories, int fat, int protein, int sodium, int price);
    void deleteProduct(MenuItem menuItem);
    void generateBill(Order order);
    void filterProducts(ClientGUI clientGUI);
    void placeOrder(int id, Client client, ArrayList<MenuItem> items);
    void generateReports();

}
