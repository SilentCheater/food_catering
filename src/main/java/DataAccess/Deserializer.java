package DataAccess;

import Model.Administrator;
import Model.Client;
import Model.Order;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashSet;

public class Deserializer {
    public static HashSet<Administrator> readAdmins(){
        try {
            FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\resources\\admins.ser");
            ObjectInputStream ois=new ObjectInputStream(fis);
            HashSet<Administrator> administrators = (HashSet<Administrator>)ois.readObject();
            ois.close();
            fis.close();

            return administrators;
        } catch (EOFException exception){
            System.out.println("the file is empty, no worries");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HashSet<Client> readClients(){
        try {
            FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\resources\\clients.ser");
            ObjectInputStream ois=new ObjectInputStream(fis);
            HashSet<Client> clients = (HashSet<Client>)ois.readObject();
            ois.close();
            fis.close();

            return clients;
        }
         catch (EOFException exception){
            System.out.println("the file is empty, no worries");

         }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HashSet<Order> readOrders(){
        try {
            FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\resources\\orderz.ser");
            ObjectInputStream ois=new ObjectInputStream(fis);
            HashSet<Order> orders = (HashSet<Order>)ois.readObject();
            ois.close();
            fis.close();

            return orders;
        } catch (EOFException exception){
            System.out.println("the file is empty, no worries");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new HashSet<>();
    }
}
