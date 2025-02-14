package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Order;

public class ListOrderController implements Initializable {

    @FXML
    private TableView<Order> listOrderTable;

    @FXML
    private TableColumn<Order, String> customerColumn;

    @FXML
    private TableColumn<Order, String> phoneNumberColumn;

    @FXML
    private TableColumn<Order, String> addressColumn;

    @FXML
    private TableColumn<Order, String> totalPriceColumn;

    @FXML
    private Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPriceFormatted"));
        
        loadOrdersFromCSV();
    }

    private void loadOrdersFromCSV() {
        ObservableList<Order> orders = FXCollections.observableArrayList();
    
        URL FILE_PATH = getClass().getResource("../database/Order.csv");
    
        if (FILE_PATH == null) {
            System.out.println("Arquivo Order.csv não encontrado!");
            return;
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH.getPath()))) {
            String line;
            boolean firstLine = true;
    
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
            symbols.setDecimalSeparator(',');
            symbols.setGroupingSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);
    
            while ((line = br.readLine()) != null) {
                if (firstLine) { 
                    firstLine = false; 
                    continue;
                }
    
                String[] data = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                if (data.length == 5) {
                    String customer = data[0].replace("\"", "").trim();
                    String phoneNumber = data[1].replace("\"", "").trim();
                    String address = data[2].replace("\"", "").trim();
                    String products = data[3].replace("\"", "").trim(); 
    
                    double totalPrice = decimalFormat.parse(data[4].replace("\"", "").trim()).doubleValue();
    
                    orders.add(new Order(customer, phoneNumber, address, products, totalPrice));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Erro ao converter número: " + e.getMessage());
        }
    
        listOrderTable.setItems(orders);
    }

    @FXML
    void backPage(ActionEvent event) {
        try {
            URL orderPath = getClass().getResource("../view/MenuManagementView.fxml");
            FXMLLoader loader = new FXMLLoader(orderPath);
            Parent newRoot = loader.load();

            Node sourceNode = (Node) event.getSource();
            Scene currentScene = sourceNode.getScene();
            Stage stage = (Stage) currentScene.getWindow();

            stage.setScene(new Scene(newRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
