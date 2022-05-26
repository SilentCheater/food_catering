package DataAccess;

import Model.Administrator;
import Model.Client;
import Model.Order;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class FileWriter {

    public static void writeAdmins(HashSet<Administrator>people){
        try {
            FileOutputStream fop=new FileOutputStream(System.getProperty("user.dir")+"\\src\\main\\resources\\admins.ser");
            ObjectOutputStream oos=new ObjectOutputStream(fop);
            oos.writeObject(people);
            oos.close();
            fop.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeClients(HashSet<Client>people){
        try {
            FileOutputStream fop=new FileOutputStream(System.getProperty("user.dir")+"\\src\\main\\resources\\clients.ser");
            ObjectOutputStream oos=new ObjectOutputStream(fop);
            oos.writeObject((Object) people);
            oos.close();
            fop.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeOrders(HashSet<Order> orders){
        try {
            FileOutputStream fop=new FileOutputStream(System.getProperty("user.dir")+"\\src\\main\\resources\\orderz.ser");
            ObjectOutputStream oos=new ObjectOutputStream(fop);
            oos.writeObject(orders);
            oos.close();
            fop.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
