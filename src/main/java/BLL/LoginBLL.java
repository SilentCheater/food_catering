package BLL;

import DataAccess.MyReader;
import Model.Administrator;
import Model.Client;
import Model.Person;

import java.util.HashSet;

public class LoginBLL {

    //TODO: singleton for DeliveryService
    private DeliveryService deliveryService = DeliveryService.getInstance();

    public int login(String username, String password){
        HashSet<Client> clients = deliveryService.clients;
        HashSet<Administrator> administrators = deliveryService.administrators;

        if(username.equals("employee"))
            return 3;
        for(Client client : clients){
            if(validLogin(username, password, client))
                return 1;
        }
        for(Administrator administrator : administrators){
            if(validLogin(username, password, administrator))
                return 2;
        }
        return -1;
    }

    private boolean validLogin(String username, String password, Person person){
        return person.getUsername().equals(username) && person.getPassword().equals(password);
    }
}
