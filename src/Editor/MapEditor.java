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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author A
 */
public class MapEditor extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        AnchorPane root = new AnchorPane();
        HBox menu = new HBox();
        
        Grid grid = new Grid(60, 10, 10);
        
        GridPane entityPane = new GridPane();
        Tab entities = new Tab("Entities", entityPane);
        
        GridPane wallPane = new GridPane();
        Tab walls = new Tab("Walls", wallPane);
        
        TabPane tabs = new TabPane(walls, entities);
        
        ColorPicker colorPicker = new ColorPicker(Color.BLACK);
        
        
        //menu.getChildren().addAll(toggle, colorPicker);
        //root.getChildren().addAll(menu, grid);
        
        Scene scene = new Scene(grid, 500, 525);
        
        GridController gc = new GridController(scene, grid, new Button(), colorPicker);

        
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
