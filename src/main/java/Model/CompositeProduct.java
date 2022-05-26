package Model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class CompositeProduct extends MenuItem {
//
//    @Serial
//    private static final long serialVersionUID = 4047588015863499748L;
    private ArrayList<MenuItem> products;

    public CompositeProduct(String title, ArrayList<MenuItem> products){
        this.products = products;
        setTitle(title);
        setRating(computeRating());
        setCalories(computeCalories());
        setProtein(computeProtein());
        setFat(computeFat());
        setSodium(computeSodium());
        setPrice(computePrice());
    }

    public CompositeProduct(String title, double rating, int calories, int fat, int protein, int sodium, int price, ArrayList<MenuItem> products) {
        super(title, rating, calories, fat, protein, sodium, price);
        this.products = products;
    }

    public ArrayList<MenuItem> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<MenuItem> products) {
        this.products = products;
    }

    public void addMenuItem(BaseProduct item){
        products.add(item);
    }

    private int computePrice(){
        int sum = 0;
        for(MenuItem baseProduct : products){
           sum+=baseProduct.getPrice();
        }
        return sum;
    }
    private double computeRating(){
        double sum = 0;
        for(MenuItem baseProduct : products){
            sum+=baseProduct.getRating();
        }
        return sum/products.size();
    }
    private int computeCalories(){
        int sum = 0;
        for(MenuItem baseProduct : products){
            sum+=baseProduct.getCalories();
        }
        return sum;
    }
    private int computeProtein(){
        int sum = 0;
        for(MenuItem baseProduct : products){
            sum+=baseProduct.getProtein();
        }
        return sum;
    }
    private int computeFat(){
        int sum = 0;
        for(MenuItem baseProduct : products){
            sum+=baseProduct.getFat();
        }
        return sum;
    }
    private int computeSodium(){
        int sum = 0;
        for(MenuItem baseProduct : products){
            sum+=baseProduct.getSodium();
        }
        return sum;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for(MenuItem menuItem : products){
            if(menuItem.equals(products.get(0)))
                string.append(menuItem.toString());
            else
                string.append(", ").append(menuItem.toString());
        }
        return string.append(".\n").toString();
    }
}
