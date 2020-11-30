/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor;

import Editor.Controller.InfoController;
import Editor.Controller.MenuController;
import Editor.Controller.ShortcutController;
import Editor.Controller.ProfileController.WallController;
import Editor.Model.Profile.MapProfile;
import Editor.Model.Profile.Profile;
import Editor.Model.Profile.ProjectProfile;
import Editor.View.Info;
import Editor.View.Menu.ShortcutBar;
import Editor.View.Menu.TopMenu;
import Editor.View.Metadata.DataView;
import Editor.View.Hierarchy.EntityHierarchy;
import Editor.View.Metadata.WallContent;
import Editor.View.Hierarchy.MapHierarchy;
import Editor.View.Hierarchy.WallHierarchy;
import java.io.File;
import java.net.MalformedURLException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Main class of the Optik Engine Editor
 * 
 * @author A
 */
public class MapEditor extends Application {
    /**
     * Main Components of Optik Editor GUI.
     */
    TopMenu menu; //Classical Menu
    ShortcutBar shortcuts; //Shortcut Bar for some menu items
    Info info; //Displays some useful information about the interaction with the Editor
    
    EntityHierarchy entityHierarchy; //Hierarchy of Entities
    WallHierarchy wallHierarchy; // Hierarchy of Walls
    MapHierarchy mapHierarchy; //Hierarchy of Maps
    
    DataView metadataContent; //Content Wrapper to display in metadata tab
    WallContent wallContent; //Compatible with DataView and delivers WallProfile information
    
    static ProjectProfile project; //Base project
    MapProfile currentMap = new MapProfile("Map", 10, 10); //Test Map -- To Be Removed
    
    /**
     * Core method of the Optik Editor. Starts the whole Editor GUI and events.
     * 
     * @param editorWindow
     * @throws MalformedURLException 
     */
    @Override
    public void start(Stage editorWindow) throws MalformedURLException {
        this.metadataContent = wallContent;
        this.project = new ProjectProfile("Test", "C:/", currentMap);
        project.selectedMap = currentMap;
        
        Scene scene = new Scene(setupView(metadataContent), 1920, 1080);
        
//        WallController wc = new WallController(editorWindow, wallHierarchy);
        InfoController ic = new InfoController(info, currentMap);
        MenuController mc = new MenuController(menu, editorWindow);
        ShortcutController sc = new ShortcutController(shortcuts, editorWindow, wallHierarchy);
        //wallContent.setWallController(wc);
        
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
        
        new EditorSplashScreen(editorWindow);
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Sets up the top elements of the GUI.
     * 
     * @return A <code>BorderPane</code> containing the <code>TopMenu</code> and <code>ShortcutBar</code>
     */
    private BorderPane setupTopElements(){
        //Top Elements
        this.menu = new TopMenu();
        this.shortcuts = new ShortcutBar();
        
        return new BorderPane(shortcuts, menu, null, null, null);
    }
    
    /**
     * 
     * @return <code></code>
     */
    private TabPane setupProperties(){
        this.entityHierarchy = new EntityHierarchy();
        this.wallHierarchy = new WallHierarchy(wallContent, currentMap);
        this.mapHierarchy = new MapHierarchy(project, new DataView(project) {
            @Override
            protected VBox setupPane() {
                return null;
            }

            @Override
            public void reset() {
                System.out.println(1000);
            }
        });
        
        
        //Property Tabs Initialization
        Tab entityTab = new Tab("Entities", entityHierarchy);
        Tab wallTab = new Tab("Walls", wallHierarchy);
        Tab mapTab = new Tab("Maps", mapHierarchy);
        
        //Property Pane Setup
        TabPane properties = new TabPane(wallTab, entityTab, mapTab);
        properties.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
        VBox.setVgrow(properties, Priority.ALWAYS);
        
        return properties;
    }
    
    /**
     * 
     * 
     * @return <code></code>
     */
    private TabPane setupMetadata(DataView metadata){
        ScrollPane scrollDataPane = new ScrollPane(metadata);
        Tab data = new Tab("Metadata", scrollDataPane);
        
        TabPane  dataTab =  new TabPane(data);
        dataTab.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
        return dataTab;
    }
    
    /**
     * 
     * @return 
     */
    private VBox setupSideElements(DataView data){
        return new VBox(setupProperties(), setupMetadata(data));
    }
    
    /**
     * 
     * @return 
     */
    private BorderPane setupCenterElements(){
        this.info = new Info();
        
        BorderPane gridDisplay = new BorderPane();
        gridDisplay.setCenter(project.selectedMap.getGridView());
        gridDisplay.setBottom(info);
        
        return gridDisplay;
    }
    
    /**
     * 
     * @return 
     */
    private BorderPane setupView(DataView metadata){
        BorderPane layout = new BorderPane();
        layout.setCenter(setupCenterElements());
        layout.setTop(setupTopElements());
        layout.setLeft(setupSideElements(metadata));
        
        return layout;
    }
    
    public static void setProject(ProjectProfile proj){
        project = proj;
        //Add refresh code
    }
    
    public static ProjectProfile getProject(){
        return project;
    }
}
