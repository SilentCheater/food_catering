import BLL.DeliveryService;
import DataAccess.MyReader;
import Model.Administrator;
import Model.Client;
import Model.Person;
import Presentation.LoginController;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args){
        LoginController loginController = new LoginController();
        MyReader productsReader = new MyReader();
        productsReader.read();
    }
}
