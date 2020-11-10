/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor;

import Editor.Grid.Grid;
import Editor.Controller.GridController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author A
 */
public class MapEditor extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        //VBox root = new VBox();
        //HBox menu = new HBox();
        Grid grid = new Grid(50, 100, 100);
        
        Button toggle = new Button("Mode: Place WALL");
        ColorPicker colorPicker = new ColorPicker(Color.BLACK);
       
        //menu.getChildren().addAll(toggle, colorPicker);
        //root.getChildren().addAll(menu, grid);
        
        Scene scene = new Scene(grid, 500, 525);
        
        GridController gc = new GridController(scene, grid, toggle, colorPicker);

        
        primaryStage.setTitle("Map Editor");
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
