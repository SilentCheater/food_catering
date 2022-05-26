package Presentation;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RegisterView extends JFrame {

    //TODO: ctrl

    JLabel title = new JLabel("REGISTER");
    JLabel username = new JLabel("username");
    JLabel password = new JLabel("password");
    JLabel age = new JLabel("age");
    JLabel firstName = new JLabel("first name");
    JLabel lastName = new JLabel("last name");


    JTextField usernameTF = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JTextField ageTF = new JTextField();
    JTextField firstNameTF = new JTextField();
    JTextField lastNameTF = new JTextField();

    JCheckBox admin = new JCheckBox("Register as admin");

    JButton register = new JButton("REGISTER");
    JButton back = new JButton("BACK");

    public RegisterView() {

        title.setFont(new Font("Roboto", Font.PLAIN, 24));
        title.setHorizontalAlignment(JLabel.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(11, 1, 50, 5));

        panel.add(firstName);
        panel.add(firstNameTF);
        panel.add(lastName);
        panel.add(lastNameTF);
        panel.add(age);
        panel.add(ageTF);
        panel.add(username);
        panel.add(usernameTF);
        panel.add(password);
        panel.add(passwordField);
        panel.add(admin);

        JPanel bottom = new JPanel();
        bottom.setLayout(new FlowLayout());
        bottom.add(register);
        bottom.add(back);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(100, 10));
        this.setSize(800, 500);
        this.setVisible(true);
        this.add(title, BorderLayout.NORTH);
        this.add(bottom, BorderLayout.SOUTH);
        this.add(panel, BorderLayout.CENTER);
        this.pack();
    }

    public JTextField getUsernameTF(){
        return usernameTF;
    }

    public JTextField getAgeTF(){
        return ageTF;
    }

    public JTextField getWeightTF(){return firstNameTF;}

    public JTextField getLastNameTF() {
        return lastNameTF;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getRegister() {
        return register;
    }

    public JButton getBack() {
        return back;
    }

    public void reset(){
        usernameTF.setText("");
        passwordField.setText("");
        ageTF.setText("");
        firstNameTF.setText("");
        lastNameTF.setText("");
    }
}
