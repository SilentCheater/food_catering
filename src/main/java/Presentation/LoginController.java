package Presentation;

import BLL.DeliveryService;
import BLL.LoginBLL;
import Model.Administrator;
import Model.MenuItem;
import Model.Person;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class LoginController {

    private LoginView loginView;
    private LoginBLL loginBLL = new LoginBLL();
    private DeliveryService deliveryService = DeliveryService.getInstance();

    public LoginController(){
        loginView = new LoginView();

        loginView.login.addActionListener(
                e -> {
                    String username = loginView.usernameTF.getText();
                    String password = new String(loginView.passwordField.getPassword());

                    int login = loginBLL.login(username, password);

                    Object[] objects = deliveryService.menuItems.toArray();
                    Object[][] data = new Object[objects.length][MenuItem.class.getDeclaredFields().length];
                    for(int i = 0; i<objects.length;i++){
                        for(int j= 0;j<MenuItem.class.getDeclaredFields().length;j++){
                            Field field = MenuItem.class.getDeclaredFields()[j];
                            field.setAccessible(true);
                            try{
                                data[i][j] = field.get(objects[i]);
                            }catch (IllegalAccessException exception){
                                exception.printStackTrace();
                            }
                        }
                    }
                    String[] colNames = new String[MenuItem.class.getDeclaredFields().length];
                    for(int i=0;i<MenuItem.class.getDeclaredFields().length;i++){
                        colNames[i]=MenuItem.class.getDeclaredFields()[i].getName();
                    }

                    switch(login){
                        case 1: {//TODO: Client GUI
                            JOptionPane.showMessageDialog(null, "Client good", "", JOptionPane.INFORMATION_MESSAGE);

                            new ClientGUI(data, colNames, username);
                            break;
                        }
                        case 2: {//TODO: Admin GUI
                            JOptionPane.showMessageDialog(null, "Admin good", "", JOptionPane.INFORMATION_MESSAGE);
                            new AdminGUI(data, colNames, username);
                            break;
                        }
                        case 3:{
                            new EmployeeGUI();
                        }
                        case -1:
                            JOptionPane.showMessageDialog(null, "Failed login! Credentials incorrect", "Try again", JOptionPane.ERROR_MESSAGE);
                        default:
                            System.out.println("login error");
                    }

                }
        );
        loginView.register.addActionListener(
                e -> {
                    RegisterController registerController = new RegisterController();
                }
        );
    }

}
