package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderController implements Initializable { 

    @FXML
    private ComboBox<String> categoryComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCategories();
    }

    private void loadCategories() {
        try {
            URL pathDatabase = getClass().getResource("../database/Category.csv");
    
            if (pathDatabase == null) {
                System.out.println("Arquivo Category.csv não encontrado! Verifique o caminho.");
                return;
            }
    
            BufferedReader br = new BufferedReader(new FileReader(pathDatabase.toURI().getPath()));
    
            br.readLine(); // Pular o cabeçalho
    
            String line;
            while ((line = br.readLine()) != null) { 
                String[] values = line.split(",");
                if (values.length > 1) {
                    categoryComboBox.getItems().add(values[1]);
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
