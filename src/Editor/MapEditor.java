/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor;

import Editor.View.Grid.Grid;
import Editor.Controller.GridController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author A
 */
public class MapEditor extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        AnchorPane root = new AnchorPane();
        
        //Creating top menu pane and information viewer pane
        HBox menu = new HBox();
        HBox info = new HBox();
        
        //Create the Grid Pane
        Grid grid = new Grid(60, 10, 10);
        
        //Create Entity Tab
        GridPane entityPane = new GridPane();
        Tab entities = new Tab("Entities", entityPane);
        
        //Create Wall Tab
        GridPane wallPane = new GridPane();
        Tab walls = new Tab("Walls", wallPane);
        
        //Instantiationg a Tab Pane with the two tabs created
        TabPane tabs = new TabPane(walls, entities);
        
        Button b = new Button("TEST B");
        menu.getChildren().add(b);
        
        AnchorPane.setLeftAnchor(menu, 10d);
        AnchorPane.setRightAnchor(grid, 10d);
        root.getChildren().addAll(menu, grid);
        
        Scene scene = new Scene(root, 500, 525);
        
        //Controller Initialization
        GridController gc = new GridController(scene, grid, new Button(), new ColorPicker());

        
        primaryStage.setTitle("Optik Editor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
