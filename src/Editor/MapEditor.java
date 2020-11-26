/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor;

import Editor.View.Grid.Grid;
import Editor.Controller.GridController;
import Editor.Controller.InfoController;
import Editor.Controller.MenuController;
import Editor.Controller.ShortcutController;
import Editor.Controller.WallController;
import Editor.Model.WallProfile;
import Editor.View.Info;
import Editor.View.Menu.ShortcutBar;
import Editor.View.Menu.TopMenu;
import Editor.View.Properties.EntityTab;
import Editor.View.Properties.WallContent;
import Editor.View.Properties.WallHierarchy;
import java.io.File;
import java.net.MalformedURLException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author A
 */
public class MapEditor extends Application {
    //Default WallProfile
    WallProfile floor = GridController.selectedWallProfile;
    WallProfile t = new WallProfile("S K E L E T O N ?", "skeleton.gif", 1);
    
    @Override
    public void start(Stage editorWindow) throws MalformedURLException {
        floor = new WallProfile("Floor", "white.png", 1);
        
        //Top Elements
        TopMenu menu = new TopMenu();
        ShortcutBar shortcuts = new ShortcutBar();
        BorderPane tools = new BorderPane(shortcuts, menu, null, null, null);
        
        //Center Elements
        Grid grid = new Grid(50, 20, 20, floor);
        Info info = new Info();
        setChildrenClipping(grid);
        
        //Property Panels Setup
        //Create Entity Tab
        EntityTab entityTab = new EntityTab();
        Tab entities = new Tab("Entities", entityTab);
        
        
        
        
        
        //Wall Content Metadata 
        WallContent wallContent = new WallContent(floor);
        
        //Create Wall Tab
        WallHierarchy wallTab = new WallHierarchy(wallContent);//floor, new WallProfile("Black", "grey_brick_vines.png", 1)
        Tab walls = new Tab("Walls", wallTab);
        
        //Property pane
        TabPane properties = new TabPane(walls, entities);
        properties.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        properties.setPrefHeight(750);
        
        
        //Metadata pane setup
        WallController wc = new WallController(wallContent, grid, wallTab);
        
        
        
        ScrollPane scrollDataPane = new ScrollPane(wallContent);
        Tab data = new Tab("Metadata", scrollDataPane);
        
        TabPane metadata = new TabPane(data);
        metadata.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
        Region r = new Region();
        VBox.setVgrow(r, Priority.ALWAYS);
        
        VBox dataSide = new VBox(properties, r, metadata);
        
        //
        BorderPane gridDisplay = new BorderPane();
        gridDisplay.setCenter(grid);
        gridDisplay.setBottom(info);
        
        BorderPane layout = new BorderPane();
        layout.setCenter(gridDisplay);
        layout.setTop(tools);
        layout.setLeft(dataSide);
        
        
        Scene scene = new Scene(layout, 1920, 1080);
        
        GridController gc = new GridController(scene, grid);
        InfoController ic = new InfoController(info, grid, gc);
        MenuController mc = new MenuController(menu, editorWindow);
        ShortcutController sc = new ShortcutController(shortcuts, editorWindow);
        
        String pathName = "resources/style/style.css" ;
        File file = new File(pathName);
        if (file.exists()) {
            scene.getStylesheets().add(file.toURI().toURL().toExternalForm());
        } else {
           System.out.println("Could not find css file: "+pathName);
        }
        
        editorWindow.setTitle("Optik Editor");
        editorWindow.setScene(scene);
        editorWindow.setMaximized(true);
        editorWindow.show();
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private static void setChildrenClipping(Pane pane) {
        Rectangle clip = new Rectangle();
        pane.setClip(clip);    
        
        pane.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            clip.setWidth(newValue.getWidth());
            clip.setHeight(newValue.getHeight());
        });
    }
}
