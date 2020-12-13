/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Menu.Entity;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author linuo
 */
public class SignalView extends GridPane{
    public Label nameLabel = new Label("Name :");
    public Label targetNameLabel = new Label("Target name :");;
    public Label inputNameLabel = new Label("Input name :");
    public Label arguementLabel = new Label("arguements :");
    
    public TextField nameTf = new TextField();
    public TextField targetNameTf = new TextField();
    public TextField inputNameTf = new TextField();
    public TextField arguementTf = new TextField();
    
    public Button saveSignal = new Button("save");
    public Button clearSignal = new Button("clear");
    
    public VBox labelBox = new VBox();
    public VBox tfBox = new VBox();
    public VBox btnBox = new VBox();
    
    
    
    public SignalView() {
        labelBox.setPadding(new Insets(25));
        labelBox.setSpacing(25);
        tfBox.setPadding(new Insets(10));
        tfBox.setSpacing(10);
        btnBox.setPadding(new Insets(10));
        btnBox.setSpacing(10);
        
        saveSignal.setMaxWidth(Double.MAX_VALUE);
        clearSignal.setMaxWidth(Double.MAX_VALUE);
        
        labelBox.getChildren().addAll(nameLabel, targetNameLabel, inputNameLabel, arguementLabel);
        tfBox.getChildren().addAll(nameTf, targetNameTf, inputNameTf, arguementTf);
        btnBox.getChildren().addAll(saveSignal, clearSignal);
        
        this.add(labelBox, 0, 0);
        this.add(tfBox, 1, 0);
        this.add(btnBox, 2, 0);
    }
    
    
    
    

    
}
