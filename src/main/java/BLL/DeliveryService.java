package BLL;

import DataAccess.Deserializer;
import DataAccess.MyReader;
import Model.*;
import Presentation.ClientGUI;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DeliveryService implements IDeliveryServiceProcessing {
    public HashSet<Client> clients;
    public HashSet<Administrator> administrators;
    public HashSet<Order> orders;
    public HashSet<MenuItem> menuItems;

    private static DeliveryService singleInstance = null;

    private DeliveryService(){

        clients = Deserializer.readClients();
        administrators = Deserializer.readAdmins();
        menuItems = MyReader.read();
        orders = Deserializer.readOrders();
        System.out.println(orders.size());
        if(clients == null)
            clients = new HashSet<>();
        if(administrators == null)
            administrators = new HashSet<>();
        administrators.add(new Administrator("admin", "admin", 30, "admin", "admin"));
    }

    public static DeliveryService getInstance(){
        if(singleInstance == null)
            singleInstance = new DeliveryService();
        return singleInstance;
    }

    @Override
    public HashSet<MenuItem> importProducts() {
        return MyReader.read();
    }

    /*
     * @invariant menuItem != null
     * @param menuItem is an item to be added to the menu
     */
    @Override
    public void addProduct(MenuItem menuItem) {
        assert menuItem != null : "item can't be null";
        menuItems.add(menuItem);
        MyReader.writeDataLineByLine(menuItems);

    }

    @Override
    public void editProduct(MenuItem menuItem, String title, double rating, int calories, int fat, int protein, int sodium, int price) {
        for(MenuItem menuItem1 : menuItems){
            if(menuItem.equals(menuItem1)){
                menuItem1.setSodium(sodium);
                menuItem1.setProtein(protein);
                menuItem1.setFat(fat);
                menuItem1.setRating(rating);
                menuItem1.setCalories(calories);
                menuItem1.setPrice(price);
                menuItem1.setTitle(title);
            }
        }
        MyReader.writeDataLineByLine(menuItems);
    }

    /*
     * @invariant menuItem != null
     * <pre>
     * assert menuItems.contains(menuItem) : "You are deleting a menu item that does not exist";
     * </pre>
     * @param menuItem is an item to be deleted from the menu
     * <post>
     * assert !menuItems.contains(menuItem) : "Sth went wrong with deleting the item";
     * </post>
     */
    @Override
    public void deleteProduct(MenuItem menuItem) {
        assert menuItems.contains(menuItem) : "You are deleting a menu item that does not exist";
        menuItems.remove(menuItem);
        MyReader.writeDataLineByLine(menuItems);
        assert !menuItems.contains(menuItem) : "Sth went wrong with deleting the item";
    }

    @Override
    public void generateBill(Order order) {
        try {
            File myObj = new File(System.getProperty("user.dir")+"\\src\\main\\resources\\orders\\" + "order"+ order.getId() + ".txt");
            if (myObj.createNewFile()) {
                FileWriter myWriter = new FileWriter(System.getProperty("user.dir")+"\\src\\main\\resources\\orders\\" + "order"+ order.getId() + ".txt");
                myWriter.write(order.toString());
                myWriter.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Override
    public void filterProducts(ClientGUI clientGUI) {

        assert clientGUI.getData() != null : "no data loaded";
        List<MenuItem> menuItemList = new ArrayList<>();
        for(int i = 0; i<clientGUI.getData().length;i++){
            menuItemList.add(new MenuItem(clientGUI.getData()[i][0].toString(), Double.parseDouble(clientGUI.getData()[i][1].toString()), Integer.parseInt(clientGUI.getData()[i][2].toString()), Integer.parseInt(clientGUI.getData()[i][3].toString()), Integer.parseInt(clientGUI.getData()[i][4].toString()), Integer.parseInt(clientGUI.getData()[i][5].toString()), Integer.parseInt(clientGUI.getData()[i][6].toString())));
        }

        assert menuItems != null : "failed to extract data";
        clientGUI.getTableModel().setRowCount(0);

        // if statements have the role to skip past the fields that don't need filtering
        if(clientGUI.getKeyword() != null)
            menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getTitle().contains(clientGUI.getKeyword())).collect(Collectors.toList());

        if(!Objects.equals(clientGUI.getRating().getText(), "")){
            try{
                String[] split = clientGUI.getRating().getText().split("\\s+");
                double ratingDouble = Double.parseDouble(split[1]);
                switch(split[0]){
                    case "=":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getRating()==ratingDouble).collect(Collectors.toList());
                        break;
                    case "<=":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getRating()<=ratingDouble).collect(Collectors.toList());
                        break;
                    case ">=":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getRating()>=ratingDouble).collect(Collectors.toList());
                        break;
                    case "<":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getRating()<ratingDouble).collect(Collectors.toList());
                        break;
                    case ">":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getRating()>ratingDouble).collect(Collectors.toList());
                        break;
                }
            }
            catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(null, "rating must be double type = x.y", "double", JOptionPane.ERROR_MESSAGE);
            }
        }

        if(!Objects.equals(clientGUI.getCalories().getText(), "")){
            try{
                String[] split = clientGUI.getCalories().getText().split("\\s+");
                int integerVar = Integer.parseInt(split[1]);
                switch(split[0]){
                    case "=":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getCalories()==integerVar).collect(Collectors.toList());
                        break;
                    case "<=":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getCalories()<=integerVar).collect(Collectors.toList());
                        break;
                    case ">=":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getCalories()>=integerVar).collect(Collectors.toList());
                        break;
                    case "<":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getCalories()<integerVar).collect(Collectors.toList());
                        break;
                    case ">":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getCalories()>integerVar).collect(Collectors.toList());
                        break;
                }
            }
            catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(null, "calories must be int type", "int", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(!Objects.equals(clientGUI.getFats().getText(), "")){
            try{
                String[] split = clientGUI.getFats().getText().split("\\s+");
                int integerVar = Integer.parseInt(split[1]);
                switch(split[0]){
                    case "=":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getFat()==integerVar).collect(Collectors.toList());
                        break;
                    case "<=":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getFat()<=integerVar).collect(Collectors.toList());
                        break;
                    case ">=":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getFat()>=integerVar).collect(Collectors.toList());
                        break;
                    case "<":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getFat()<integerVar).collect(Collectors.toList());
                        break;
                    case ">":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getFat()>integerVar).collect(Collectors.toList());
                        break;
                }
            }
            catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(null, "fats must be int type", "int", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(!Objects.equals(clientGUI.getProteins().getText(), "")){
            try{
                String[] split = clientGUI.getProteins().getText().split("\\s+");
                int integerVar = Integer.parseInt(split[1]);
                switch(split[0]){
                    case "=":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getProtein()==integerVar).collect(Collectors.toList());
                        break;
                    case "<=":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getProtein()<=integerVar).collect(Collectors.toList());
                        break;
                    case ">=":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getProtein()>=integerVar).collect(Collectors.toList());
                        break;
                    case "<":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getProtein()<integerVar).collect(Collectors.toList());
                        break;
                    case ">":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getProtein()>integerVar).collect(Collectors.toList());
                        break;
                }
            }
            catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(null, "proteins must be int type", "int", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(!Objects.equals(clientGUI.getSodium().getText(), "")){
            try{
                String[] split = clientGUI.getSodium().getText().split("\\s+");
                int integerVar = Integer.parseInt(split[1]);
                switch(split[0]){
                    case "=":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getSodium()==integerVar).collect(Collectors.toList());
                        break;
                    case "<=":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getSodium()<=integerVar).collect(Collectors.toList());
                        break;
                    case ">=":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getSodium()>=integerVar).collect(Collectors.toList());
                        break;
                    case "<":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getSodium()<integerVar).collect(Collectors.toList());
                        break;
                    case ">":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getSodium()>integerVar).collect(Collectors.toList());
                        break;
                }
            }
            catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(null, "sodium must be int type", "int", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(!Objects.equals(clientGUI.getPrice().getText(), "")){
            try{
                String[] split = clientGUI.getPrice().getText().split("\\s+");
                int integerVar = Integer.parseInt(split[1]);
                switch(split[0]){
                    case "=":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getPrice()==integerVar).collect(Collectors.toList());
                        break;
                    case "<=":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getPrice()<=integerVar).collect(Collectors.toList());
                        break;
                    case ">=":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getPrice()>=integerVar).collect(Collectors.toList());
                        break;
                    case "<":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getPrice()<integerVar).collect(Collectors.toList());
                        break;
                    case ">":
                        menuItemList = menuItemList.stream().filter(Objects::nonNull).filter(f-> f.getPrice()>integerVar).collect(Collectors.toList());
                        break;
                }
            }
            catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(null, "price must be int type", "int", JOptionPane.ERROR_MESSAGE);
            }
        }

        Object[] objects = menuItemList.toArray();
        Object[][] newData = new Object[objects.length][MenuItem.class.getDeclaredFields().length];
        for(int i = 0; i<objects.length;i++){
            for(int j= 0;j<MenuItem.class.getDeclaredFields().length;j++){
                Field field = MenuItem.class.getDeclaredFields()[j];
                field.setAccessible(true);
                try{
                    newData[i][j] = field.get(objects[i]);
                }catch (IllegalAccessException exception){
                    exception.printStackTrace();
                }
            }
            clientGUI.getTableModel().addRow(newData[i]);
            System.out.println(newData[i][0]);
        }
        clientGUI.getTableModel().fireTableDataChanged();
    }


    @Override
    public void placeOrder(int id, Client client, ArrayList<MenuItem> items) {
        this.orders.add(new Order(id, client, items));
        System.out.println("new size: "+orders.size());
        DataAccess.FileWriter.writeOrders(orders);
        generateBill(new Order(id, client, items));
    }

    @Override
    public void generateReports(){
        try {
            File myObj = new File(System.getProperty("user.dir")+"\\src\\main\\resources\\" + "reports" + ".txt");
            myObj.createNewFile();
                FileWriter myWriter = new FileWriter(System.getProperty("user.dir")+"\\src\\main\\resources\\" + "reports" + ".txt");

                // report 1
                List<Order> report1 = orders.stream().filter(o->o.getOrderDate().getHour()>=8 && o.getOrderDate().getHour()<=22).collect(Collectors.toList());

                //report 2
                Map<MenuItem, Integer > frequencyMap = new HashMap<>();

                StringBuilder report2 = new StringBuilder("REPORT\n Products ordered more than " + 2
                        + " times:\n");

                orders.stream()
                        .map(Order::getItems)
                        .forEach(array -> array.stream()
                                .forEach(arrayElement -> frequencyMap.put(arrayElement, 0)));

                orders.stream()
                        .map(Order::getItems)
                        .forEach(array -> array
                                .forEach(arrayElement -> frequencyMap.put(arrayElement, frequencyMap.get(arrayElement) + 1)));

                frequencyMap.entrySet().stream()
                        .filter(entry -> entry.getValue() > 2)
                        .forEach(secondEntry -> report2.append(secondEntry.getKey()).append("\n"));

                //report 3
                // Additional data structures
                Map<Client, Integer > frequencyMap3 = new HashMap<>();
                Map<Client, Double> priceMap = new HashMap<>();

                StringBuilder report3 = new StringBuilder("REPORT\n Clients that have ordered more than " + 2
                        + " times and the total value of the orders is bigger than " + 100 + ":\n");

                // Get all clients in this map with 0 count value
                orders.stream()
                        .map(Order::getClient)
                        .forEach(client -> frequencyMap3.put(client, 0));

                // Calculate times ordered for each client
                orders.stream()
                        .map(Order::getClient)
                        .forEach(client -> frequencyMap3.put(client, frequencyMap3.get(client) + 1));


                // Check if times ordered is big enough, in order to start step 2 of validation
                frequencyMap3.entrySet().stream()
                        .filter(entry -> entry.getValue() > 2)
                        .forEach(secondEntry -> priceMap.put( secondEntry.getKey(), (double) 0) );

                // Compute price for each of the entries in priceMap

                orders.stream()
                        .filter(mapIterator -> priceMap.containsKey(mapIterator.getClient()))
                        .forEach(secondIterator -> priceMap.put(
                                secondIterator.getClient(), // Client
                                priceMap.get(secondIterator.getClient()) +
                                        secondIterator.computePrice())
                        );

                // Finally, check if price and order count respect the conditions

                orders.stream()
                        .filter(mapIterator -> priceMap.containsKey(mapIterator.getClient()))
                        .filter(secondMapIterator -> priceMap.get(secondMapIterator.getClient()) > 100)
                        .forEach(finalIterator -> report3.append(finalIterator.getClient()).append("\n"));

                //report 4

                /* Additional data structures needed */
                Map<MenuItem, Integer > frequencyMap4 = new HashMap<>();

                int day =1;
                StringBuilder report4 = new StringBuilder("REPORT\n Products ordered in day "+ day
                        + " :\n");

                /* Initialise frequency map */

                orders.stream()
                        .filter(orderEntry -> orderEntry.getOrderDate().getDayOfWeek().getValue() == day)
                        .map(Order::getItems)
                        .forEach(array -> array.forEach(item -> frequencyMap.put(item, 0)));

                /* Populate frequency map */

                orders.stream()
                        .filter(orderEntry -> orderEntry.getOrderDate().getDayOfWeek().getValue() == day)
                        .map(Order::getItems)
                        .forEach(array -> array.forEach(item -> frequencyMap.put(item, frequencyMap.get(item) + 1)));

                /* Finalize report */

                frequencyMap.forEach((key, value) -> report4.append(key)
                        .append(" with ").append(value).append(" orders.").append("\n"));


                String finalString = report1.toString() + report2.toString() + report3.toString() + report4.toString();
                myWriter.write(finalString);
                myWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
