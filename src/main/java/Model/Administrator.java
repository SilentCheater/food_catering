package Model;

import java.io.Serializable;

public class Administrator extends Person implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;

    public Administrator(String firstName, String lastName, int age, String username, String password) {
        super(firstName, lastName, age, username, password);
    }
    public Administrator(Person p){
        super(p.getFirstName(), p.getLastName(), p.getAge(), p.getUsername(), p.getPassword());
    }
}
