/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitycreation;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author linuo
 */
public class EntityModel {
    
    public SimpleStringProperty property;
    public SimpleStringProperty value;

    public EntityModel(String property, String value) {
        this.property = new SimpleStringProperty(property);
        this.value = new SimpleStringProperty(value);
    }

    public EntityModel() {
    }
    
    

    public String getProperty() {
        return property.get();
    }

    public String getValue() {
        return value.get();
    }

    public void setProperty(SimpleStringProperty property) {
        this.property = property;
    }

    public void setValue(SimpleStringProperty value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "EntityModel{" + "property=" + property + ", value=" + value + '}';
    }
    
    
    
}
