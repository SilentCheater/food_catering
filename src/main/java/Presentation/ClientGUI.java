package Presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import BLL.DeliveryService;
import Model.BaseProduct;
import Model.Client;
import Model.Order;

import java.awt.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ClientGUI extends JFrame {
    JLabel helloMessage;
    JButton addMenuItem = new JButton("ADD ITEM");
    JTable table;
    JButton placeOrder = new JButton("PLACE ORDER");
    JLabel info = new JLabel("delete the strings in the text areas that you do not use for filtering");
    JTextField keyword = new JTextField("keyword for search");
    JTextField rating = new JTextField("rating");
    JTextField calories = new JTextField("calories");
    JTextField proteins = new JTextField("proteins");
    JTextField fats = new JTextField("fats");
    JTextField sodium = new JTextField("sodium");
    JTextField price = new JTextField("price");
    JButton filter = new JButton("FILTER");
    JTable cart = new JTable();
    DefaultTableModel tm;
    DefaultTableModel tableModel;
    JLabel total = new JLabel("Total: 0");
    int totalPrice = 0;
    Object[][] data;

    private static final ArrayList<EmployeeGUI> observers = new ArrayList<>();

    public ClientGUI(Object[][] data, String[] columns, String username){

        this.data = data;
        DeliveryService deliveryService = DeliveryService.getInstance();
        tableModel = new DefaultTableModel(data, columns);
        table = new JTable();
        table.setModel(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 50));
        table.setFillsViewportHeight(true);
        table.setDefaultEditor(Object.class, null);
        JScrollPane tableScrollPane = new JScrollPane(table);

        tm = new DefaultTableModel(columns, 0);
        cart.setModel(tm);
        cart.setPreferredScrollableViewportSize(new Dimension(500, 50));
        cart.setFillsViewportHeight(true);
        cart.setDefaultEditor(Object.class, null);
        JScrollPane cartScrollPane = new JScrollPane(cart);

        addMenuItem.addActionListener(
                e -> {
                    tm.addRow(new Object[]{table.getValueAt(table.getSelectedRow(), 0),
                                           table.getValueAt(table.getSelectedRow(), 1),
                                           table.getValueAt(table.getSelectedRow(), 2),
                                           table.getValueAt(table.getSelectedRow(), 3),
                                           table.getValueAt(table.getSelectedRow(), 4),
                                           table.getValueAt(table.getSelectedRow(), 5),
                                           table.getValueAt(table.getSelectedRow(), 6)
                                        }
                                );
                    totalPrice += (Integer)table.getValueAt(table.getSelectedRow(), 6);
                    total.setText("Total: " + totalPrice);
                    tm.fireTableDataChanged();
                }
        );

        filter.addActionListener(
                e ->{
                    deliveryService.filterProducts(this);
                }

        );

        placeOrder.addActionListener(
                e -> {
                    Client client = null;
                    for(Client client1 : deliveryService.clients){
                        if(client1.getUsername().equals(username)) {
                            client = client1;
                            break;
                        }
                    }

                    ArrayList<Model.MenuItem> menuItemList = new ArrayList<>();
                    for(int i = 0; i<cart.getRowCount();i++){
                        menuItemList.add(new Model.MenuItem(cart.getValueAt(i,0).toString(),
                                                      Double.parseDouble(cart.getValueAt(i,1).toString()),
                                                      Integer.parseInt(cart.getValueAt(i,2).toString()),
                                                      Integer.parseInt(cart.getValueAt(i,3).toString()),
                                                      Integer.parseInt(cart.getValueAt(i,4).toString()),
                                                      Integer.parseInt(cart.getValueAt(i,5).toString()),
                                                      Integer.parseInt(cart.getValueAt(i,6).toString())
                                                      )
                                        );
                    }
                    int id = ThreadLocalRandom.current().nextInt(1, 500000);
                    deliveryService.placeOrder(id, client, menuItemList);

                    tm.setRowCount(0);
                    total.setText("Total: ");
                    notifyObservers(new Order(id, client, menuItemList));
                }
        );

        helloMessage = new JLabel("Hello, " + username);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(1,2));
        JPanel left = new JPanel(new GridLayout(11, 1));
        left.add(helloMessage);
        left.add(info);
        left.add(keyword);
        left.add(rating);
        left.add(calories);
        left.add(fats);
        left.add(proteins);
        left.add(sodium);
        left.add(price);
        left.add(filter);
        left.add(addMenuItem);

        add(left);

        JPanel right = new JPanel(new GridLayout(3, 1));
        right.add(tableScrollPane);
        right.add(cartScrollPane);

        JPanel split = new JPanel(new GridLayout(1,2));
        split.add(placeOrder);
        split.add(total);

        right.add(split);
        add(right);
        setVisible(true);
        pack();
    }

    public Object[][] getData(){
        return data;
    }

    public DefaultTableModel getTableModel(){
        return tableModel;
    }

    public String getKeyword(){
        return keyword.getText();
    }

    public JTextField getRating(){
        return rating;
    }

    public JTextField getCalories() {
        return calories;
    }

    public JTextField getFats() {
        return fats;
    }

    public JTextField getPrice() {
        return price;
    }

    public JTextField getProteins() {
        return proteins;
    }

    public JTextField getSodium() {
        return sodium;
    }

    public interface Observer{
        void updateEmployee(Order order);
    }

    public static void addObserver(EmployeeGUI employeeGUI){
        observers.add(employeeGUI);
    }

    private void notifyObservers(Order order){
        observers.forEach(observer -> observer.updateEmployee(order));
    }
}
