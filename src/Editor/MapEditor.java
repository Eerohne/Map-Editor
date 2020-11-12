/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor;

import Editor.View.Grid.Grid;
import Editor.Controller.GridController;
import Editor.View.Menu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author A
 */
public class MapEditor extends Application {
    
    @Override
    public void start(Stage editorWindow) {
        Menu menu = new Menu();
        HBox info = new HBox();
        Grid gridRender = new Grid(60, 20, 10);
        TabPane properties = new TabPane();
        
        //Create Entity Tab
        GridPane entityPane = new GridPane();
        Tab entities = new Tab("Entities", entityPane);
        
        //Create Wall Tab
        GridPane wallPane = new GridPane();
        Tab walls = new Tab("Walls", wallPane);
        
        AnchorPane gridDisplay = new AnchorPane();
        gridDisplay.getChildren().addAll(gridRender, info);
        
        BorderPane layout = new BorderPane(gridRender, menu, null, null, null);
        
        Scene scene = new Scene(layout, 500, 500);
        
        GridController gc = new GridController(scene, gridRender, new Button(), new ColorPicker());
        
        editorWindow.setTitle("Optik Editor");
        editorWindow.setScene(scene);
        editorWindow.show();
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
