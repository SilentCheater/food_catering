package BLL;

import DataAccess.FileWriter;
import Model.Administrator;
import Model.Client;
import Model.Person;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterBLL {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    HashSet<Person> people;
    HashSet<Administrator> administrators;
    DeliveryService deliveryService = DeliveryService.getInstance();

    public RegisterBLL(){
        HashSet<Person> people = new HashSet<>();
        if(deliveryService.clients!=null)
            people.addAll(deliveryService.clients);
        if(deliveryService.administrators!=null)
            people.addAll(deliveryService.administrators);
        this.people  = people;
        this.administrators = deliveryService.administrators;
    }

    public int uniqueUsername(String username){
        HashSet<String> usernames = new HashSet<>();
        for(Person person : people){
            usernames.add(person.getUsername());
        }
        if(!usernames.contains(username))
            return 2;
        return 0;
    }

    public int safePassword(String password){
        Matcher matcher = pattern.matcher(password);
        if(matcher.matches()){
            return 1;
        }
        else return 0;
    }

    public int validRegister(String username, String password){
        return uniqueUsername(username) + safePassword(password);
    }

    public void updateClients(Client client){
        deliveryService.clients.add(client);
        FileWriter.writeClients(deliveryService.clients);
    }

    public void updateAdmins(Administrator administrator){
        deliveryService.administrators.add(administrator);
        FileWriter.writeAdmins(deliveryService.administrators);
    }
}
