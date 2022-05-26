package Model;

import java.io.Serial;
import java.io.Serializable;

public class BaseProduct extends MenuItem {
//
//    @Serial
//    private static final long serialVersionUID = -6987128540584977970L;

    public BaseProduct(){}

    public BaseProduct(String title, double rating, int calories, int fat, int protein, int sodium, int price){
        super(title, rating, calories, fat, protein, sodium, price);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
