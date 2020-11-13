/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor;

import Editor.View.Grid.Grid;
import Editor.Controller.GridController;
import Editor.Controller.MenuController;
import Editor.View.Info;
import Editor.View.Menu.TopMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author A
 */
public class MapEditor extends Application {
    
    @Override
    public void start(Stage editorWindow) {
        TopMenu menu = new TopMenu();
        Info info = new Info();
        Grid gridRender = new Grid(60, 20, 10);
        setChildrenClipping(gridRender);
        
        
        //Create Entity Tab
        GridPane entityPane = new GridPane();
        Tab entities = new Tab("Entities", entityPane);
        
        //Create Wall Tab
        GridPane wallPane = new GridPane();
        Tab walls = new Tab("Walls", wallPane);
        
        TabPane properties = new TabPane(entities, walls);
        
        
        BorderPane gridDisplay = new BorderPane();
        gridDisplay.setCenter(gridRender);
        gridDisplay.setBottom(info);
        
        BorderPane layout = new BorderPane();
        layout.setCenter(gridDisplay);
        layout.setTop(menu);
        layout.setLeft(properties);
        
        
        Scene scene = new Scene(layout, 1920, 1080);
        
        GridController gc = new GridController(scene, gridRender, new Button(), new ColorPicker());
        MenuController mc = new MenuController(menu, editorWindow);
        
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
    
    public static void setChildrenClipping(Pane pane) {
        Rectangle clip = new Rectangle();
        pane.setClip(clip);    
        
        pane.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            clip.setWidth(newValue.getWidth());
            clip.setHeight(newValue.getHeight());
        });
    }
}
