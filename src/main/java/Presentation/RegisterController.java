package Presentation;

import BLL.RegisterBLL;
import Model.Administrator;
import Model.Client;
import Model.Person;

import javax.swing.*;

public class RegisterController {

    private final RegisterView registerView;
    private Person person;
    private boolean admin;

    public RegisterController(){
        registerView = new RegisterView();

        registerView.register.addActionListener(
                e -> {
                    RegisterBLL registerBLL = new RegisterBLL();
                    // if the registration is valid
                    int register = registerBLL.validRegister( registerView.usernameTF.getText(),
                            new String( registerView.passwordField.getPassword() ) );
                    if (register == 3) {
                        try {
                            // create a new person but see if the age is a number
                            person = new Person(registerView.firstNameTF.getText(), registerView.lastNameTF.getText(),
                                    Integer.parseInt(registerView.ageTF.getText()), registerView.usernameTF.getText(),
                                    new String(registerView.passwordField.getPassword()));

                            // see if it was registered as an admin
                            if(registerView.admin.isSelected()){
                                registerBLL.updateAdmins(new Administrator(person));
                                JOptionPane.showMessageDialog(null, "new admin", "new", JOptionPane.INFORMATION_MESSAGE);
                            }
                            else{
                                registerBLL.updateClients(new Client(person));
                                JOptionPane.showMessageDialog(null, "new client", "new", JOptionPane.INFORMATION_MESSAGE);

                            }
                        } catch (NumberFormatException exception){
                            exception.printStackTrace();
                        }
                    }
                    else if(register == 2){
                        JOptionPane.showMessageDialog(null, "Password not safe", "try again", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(register == 1){
                        JOptionPane.showMessageDialog(null, "username already taken", "try again", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(register == 0){
                        JOptionPane.showMessageDialog(null, "Username already taken and password is not strong enough", "try again", JOptionPane.ERROR_MESSAGE);
                    }
                }
        );
    }

    public Person getPerson() {
        return person;
    }

    public boolean isAdmin() {
        return admin;
    }
}
