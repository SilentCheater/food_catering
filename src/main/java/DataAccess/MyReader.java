package DataAccess;

import Model.*;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class MyReader {

    public static HashSet<MenuItem> read(){
        HashSet<MenuItem> products = new HashSet<>();
        try {
            CSVReader csvReader = new CSVReaderBuilder(new FileReader(System.getProperty("user.dir")+"\\src\\main\\resources\\products.csv")).withSkipLines(1).build();
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                MenuItem menuItem = new MenuItem(values[0], Double.parseDouble(values[1]), Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]), Integer.parseInt(values[5]), Integer.parseInt(values[6]));
                products.add(menuItem);
            }
    } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }
        return products;

    }
    public static void writeDataLineByLine(HashSet<MenuItem> menuItems)
    {
        File file = new File(System.getProperty("user.dir")+"\\src\\main\\resources\\products.csv");
        try {
            java.io.FileWriter outputFile = new java.io.FileWriter(file);

            CSVWriter writer = new CSVWriter(outputFile);

            String[] header = { "Title","Rating","Calories","Protein","Fat","Sodium","Price" };
            writer.writeNext(header);

            // add data to csv
            for(MenuItem menuItem : menuItems){
                String[] data = {menuItem.getTitle(),
                        String.valueOf(menuItem.getRating()),
                        String.valueOf(menuItem.getCalories()),
                        String.valueOf(menuItem.getProtein()),
                        String.valueOf(menuItem.getFat()),
                        String.valueOf(menuItem.getSodium()),
                        String.valueOf(menuItem.getPrice()) };
                writer.writeNext(data);
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
