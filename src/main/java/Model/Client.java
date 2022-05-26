package Model;

import java.io.Serial;
import java.io.Serializable;

public class Client extends Person implements Serializable {
    @Serial
    private static final long serialVersionUID = 1113799434508676095L;

    public Client(String firstName, String lastName, int age, String username, String password) {
        super(firstName, lastName, age, username, password);
    }
    public Client(Person p){
        super(p.getFirstName(), p.getLastName(), p.getAge(), p.getUsername(), p.getPassword());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
