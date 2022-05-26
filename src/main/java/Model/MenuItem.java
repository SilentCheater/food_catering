package Model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class MenuItem implements Serializable {

    //private static final long serialVersionUID = 4369724031232305748L;
    private String title;
    private double rating;
    private int calories;
    private int fat;
    private int protein;
    private int sodium;
    private int price;

    public MenuItem(String title, double rating, int calories, int fat, int protein, int sodium, int price) {
        this.title = title;
        this.rating = rating;
        this.calories = calories;
        this.fat = fat;
        this.protein = protein;
        this.sodium = sodium;
        this.price = price;
    }

    public MenuItem() {
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return  "title='" + title + '\'' +
                ", rating=" + rating +
                ", calories=" + calories +
                ", fat=" + fat +
                ", protein=" + protein +
                ", sodium=" + sodium +
                ", price=" + price
                ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItem)) return false;
        MenuItem menuItem = (MenuItem) o;
        return Double.compare(menuItem.getRating(), getRating()) == 0 && getCalories() == menuItem.getCalories() && getFat() == menuItem.getFat() && getProtein() == menuItem.getProtein() && getSodium() == menuItem.getSodium() && getPrice() == menuItem.getPrice() && getTitle().equals(menuItem.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getRating(), getCalories(), getFat(), getProtein(), getSodium(), getPrice());
    }

}
