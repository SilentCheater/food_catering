package Presentation;

import BLL.DeliveryService;
import Model.BaseProduct;
import Model.Client;
import Model.CompositeProduct;
import Model.MenuItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AdminGUI extends JFrame {
    JLabel helloMessage;
    JButton addMenuItem = new JButton("ADD NEW ITEM");
    JButton editMenuItem = new JButton("EDIT ITEM");
    JButton deleteMenuItem = new JButton("DELETE ITEM");
    JButton addToComposite = new JButton("ADD ITEM TO COMPOSITE");
    JButton reports = new JButton("REPORTS");
    JTable table;
    JButton addComposite = new JButton("ADD COMPOSITE");
    JLabel info = new JLabel("the order is the following: String title, double rating, int calories, int fat, int protein, int sodium, int price");
    JTextField title = new JTextField("");
    JTextField rating = new JTextField("");
    JTextField calories = new JTextField("");
    JTextField proteins = new JTextField("");
    JTextField fats = new JTextField("");
    JTextField sodium = new JTextField("");
    JTextField price = new JTextField("");
    JTable cart = new JTable();
    DefaultTableModel tm;
    DefaultTableModel tableModel;
    JTextField compositeTF = new JTextField("");
    Object[][] data;

    public AdminGUI(Object[][] data, String[] columns, String username){

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

        addToComposite.addActionListener(
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
                    tm.fireTableDataChanged();
                }
        );

        addComposite.addActionListener(
                e->{
                    //get items from cart
                    ArrayList<MenuItem> menuItemList = new ArrayList<>();
                    for(int i = 0; i<cart.getRowCount();i++){
                        menuItemList.add(
                                new MenuItem(
                                        cart.getValueAt(i,0).toString(),
                                        Double.parseDouble(cart.getValueAt(i,1).toString()),
                                        Integer.parseInt(cart.getValueAt(i,2).toString()),
                                        Integer.parseInt(cart.getValueAt(i,3).toString()),
                                        Integer.parseInt(cart.getValueAt(i,4).toString()),
                                        Integer.parseInt(cart.getValueAt(i,5).toString()),
                                        Integer.parseInt(cart.getValueAt(i,6).toString())
                                )
                        );
                    }
                    MenuItem newComposite = new CompositeProduct(compositeTF.getText(), menuItemList);

                    deliveryService.addProduct(newComposite);

                    tm.setRowCount(0);
                    compositeTF.setText("");
                    tableModel.fireTableDataChanged();
                    tm.fireTableDataChanged();

                }
        );

        addMenuItem.addActionListener(
                e ->{
                    deliveryService.addProduct(new BaseProduct(title.getText(),
                                            Double.parseDouble(rating.getText()),
                                            Integer.parseInt(calories.getText()),
                                            Integer.parseInt(fats.getText()),
                                            Integer.parseInt(proteins.getText()),
                                            Integer.parseInt(sodium.getText()),
                                            Integer.parseInt(price.getText())));
                    tableModel.fireTableDataChanged();
                    tm.fireTableDataChanged();

                }
        );

        editMenuItem.addActionListener(
                e->{
                    deliveryService.editProduct(
                            new MenuItem(
                                               table.getValueAt(table.getSelectedRow(), 0).toString(),
                            Double.parseDouble(table.getValueAt(table.getSelectedRow(), 1).toString()),
                            Integer.parseInt  (table.getValueAt(table.getSelectedRow(), 2).toString()),
                            Integer.parseInt  (table.getValueAt(table.getSelectedRow(), 3).toString()),
                            Integer.parseInt  (table.getValueAt(table.getSelectedRow(), 4).toString()),
                            Integer.parseInt  (table.getValueAt(table.getSelectedRow(), 5).toString()),
                            Integer.parseInt  (table.getValueAt(table.getSelectedRow(), 6).toString())
                            ),
                                               title.getText(),
                            Double.parseDouble(rating.getText()),
                            Integer.parseInt  (calories.getText()),
                            Integer.parseInt  (fats.getText()),
                            Integer.parseInt  (proteins.getText()),
                            Integer.parseInt  (sodium.getText()),
                            Integer.parseInt  (price.getText())
                    );
                    table.setValueAt(title.getText(), table.getSelectedRow(), 0);
                    table.setValueAt(rating.getText(), table.getSelectedRow(), 1);
                    table.setValueAt(calories.getText(), table.getSelectedRow(), 2);
                    table.setValueAt(fats.getText(), table.getSelectedRow(), 3);
                    table.setValueAt(proteins.getText(), table.getSelectedRow(), 4);
                    table.setValueAt(sodium.getText(), table.getSelectedRow(), 5);
                    table.setValueAt(price.getText(), table.getSelectedRow(), 6);


                    tm.fireTableDataChanged();
                    tableModel.fireTableDataChanged();
                }
        );

        deleteMenuItem.addActionListener(
                e->{
                    deliveryService.deleteProduct(new MenuItem(
                            table.getValueAt(table.getSelectedRow(), 0).toString(),
                            Double.parseDouble(table.getValueAt(table.getSelectedRow(), 1).toString()),
                            Integer.parseInt  (table.getValueAt(table.getSelectedRow(), 2).toString()),
                            Integer.parseInt  (table.getValueAt(table.getSelectedRow(), 3).toString()),
                            Integer.parseInt  (table.getValueAt(table.getSelectedRow(), 4).toString()),
                            Integer.parseInt  (table.getValueAt(table.getSelectedRow(), 5).toString()),
                            Integer.parseInt  (table.getValueAt(table.getSelectedRow(), 6).toString())
                    ));
                    tm.fireTableDataChanged();
                    tableModel.fireTableDataChanged();
                }
        );

        reports.addActionListener(
                e -> {
                    deliveryService.generateReports();
                    JOptionPane.showMessageDialog(null, "Reports generated", "", JOptionPane.INFORMATION_MESSAGE);
                }
        );



        helloMessage = new JLabel("Hello, " + username);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(1,2));
        JPanel left = new JPanel(new GridLayout(13, 1));
        left.add(helloMessage);
        left.add(info);
        left.add(title);
        left.add(rating);
        left.add(calories);
        left.add(fats);
        left.add(proteins);
        left.add(sodium);
        left.add(price);
        left.add(editMenuItem);
        left.add(addMenuItem);
        left.add(deleteMenuItem);
        left.add(reports);

        add(left);

        JPanel right = new JPanel(new GridLayout(4, 1));
        right.add(tableScrollPane);
        right.add(cartScrollPane);
        right.add(addToComposite);

        JPanel split = new JPanel(new GridLayout(1,2));
        split.add(compositeTF);
        split.add(addComposite);

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
}
