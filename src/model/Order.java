package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Order {
    private String customer;
    private String phoneNumber;
    private String address;
    private List<Product> products;
    private String productsString;
    private double totalPrice;

    public Order(String customer, String phoneNumber, String address, List<Product> products, double totalPrice) {
        this.customer = customer;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    public Order(String customer, String phoneNumber, String address, String productsString, double totalPrice) {
        this.customer = customer;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.productsString = productsString;
        this.totalPrice = totalPrice;
    }
    
    public void saveToCSV(String filePath) {
        File file = new File(filePath);
        boolean isNewFile = !file.exists() || file.length() == 0;
    
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            if (isNewFile) {
                writer.println("COSTUMER,PHONE_NUMBER,ADDRESS,PRODUCTS,TOTAL_PRICE");
            }
    
            String sanitizedCustomer = customer.replace(",", "-");
            String sanitizedPhoneNumber = phoneNumber.replace(",", "-");
            String sanitizedAddress = address.replace(",", "-");
            String sanitizedTotalPrice = String.format("%.2f", totalPrice).replace(",", "-");
    
            StringBuilder productDetails = new StringBuilder();
            for (Product product : products) {
                productDetails.append(product.nameProperty().get().replace(",", ";"))
                              .append(" (x").append(product.desiredQuantityProperty().get()).append(") - ")
                              .append("R$ ").append(String.format("%.2f", product.valueProperty().get() * product.desiredQuantityProperty().get()).replace(",", "."))
                              .append("; ");
            }
    
            writer.println(sanitizedCustomer + "," + sanitizedPhoneNumber + "," + 
                           "\"" + sanitizedAddress + "\"," + 
                           "\"" + productDetails.toString().trim() + "\"," + 
                           "\"" + sanitizedTotalPrice + "\"");
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    

    public String getCustomer() {
        return customer;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getProductsString() {
        return productsString;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getTotalPriceFormatted() {
        return String.format("R$ %.2f", totalPrice);
    }
    
}
