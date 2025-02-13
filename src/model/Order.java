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
    private double totalPrice;

    public Order(String customer, String phoneNumber, String address, List<Product> products, double totalPrice) {
        this.customer = customer;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    public void saveToCSV(String filePath) {
        File file = new File(filePath);
        boolean isNewFile = !file.exists() || file.length() == 0;

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            if (isNewFile) {
                writer.println("COSTUMER,PHONE_NUMBER,ADDRESS,PRODUCTS,TOTAL_PRICE");
            }

            String formattedAddress = "\"" + address + "\"";

            StringBuilder productDetails = new StringBuilder();
            for (Product product : products) {
                productDetails.append(product.nameProperty().get())
                              .append(" (x").append(product.desiredQuantityProperty().get()).append(") - ")
                              .append("R$ ").append(String.format("%.2f", product.valueProperty().get() * product.desiredQuantityProperty().get()))
                              .append("; ");
            }

            String formattedTotalPrice = "\"" + String.format("%.2f", totalPrice) + "\"";

            writer.println(customer + "," + phoneNumber + "," + formattedAddress + ",\"" + productDetails.toString().trim() + "\"," + formattedTotalPrice);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
