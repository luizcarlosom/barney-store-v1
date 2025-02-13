package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product {

    private SimpleStringProperty name;
    private SimpleStringProperty category;
    private SimpleIntegerProperty quantity;
    private SimpleDoubleProperty value;
    private IntegerProperty desiredQuantity;

    public Product(String name, String category, int quantity, double value) {
        this.name = new SimpleStringProperty(name);
        this.category = new SimpleStringProperty(category);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.value = new SimpleDoubleProperty(value);
        this.desiredQuantity = new SimpleIntegerProperty(0);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public DoubleProperty valueProperty() {
        return value;
    }

    public IntegerProperty desiredQuantityProperty() {
        return desiredQuantity;
    }

    public void setName(SimpleStringProperty name) {
        this.name = name;
    }

    public void setCategory(SimpleStringProperty category) {
        this.category = category;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public void setValue(SimpleDoubleProperty value) {
        this.value = value;
    }

    public void setDesiredQuantity(int desiredQuantity) {
        this.desiredQuantity.set(desiredQuantity);
    }
    
    @Override
    public String toString() {
        return name.get() + "," + category.get() + "," + quantity.get() + "," + value.get();
    }
}
