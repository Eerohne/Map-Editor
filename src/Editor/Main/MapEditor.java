/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Main;

import Commons.SettingsManager.Settings;
import Editor.Controller.MenuController;
import Editor.Controller.ShortcutController;
import Editor.Controller.ProfileController.WallController;
import Editor.Model.Profile.MapProfile;
import Editor.Model.Profile.ProjectProfile;
import Editor.View.Grid.Grid;
import Editor.View.Info;
import Editor.View.Menu.ShortcutBar;
import Editor.View.Menu.TopMenu;
import Editor.View.Metadata.DataView;
import Editor.View.Hierarchy.EntityHierarchy;
import Editor.View.Metadata.WallContent;
import Editor.View.Hierarchy.MapHierarchy;
import Editor.View.Hierarchy.WallHierarchy;
import Editor.View.Metadata.EntityContent;
import Editor.View.Metadata.MapContent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
    static Info info; //Displays some useful information about the interaction with the Editor
    
    static EntityHierarchy entityHierarchy; //Hierarchy of Entities
    static WallHierarchy wallHierarchy; // Hierarchy of Walls
    static MapHierarchy mapHierarchy; //Hierarchy of Maps
    
    static DataView metadataContent; //Content Wrapper to display in metadata tab
    WallContent wallContent; //Compatible with DataView and delivers WallProfile information
    MapContent mapContent;
    EntityContent entityContent;
    
    static Grid grid;
    static BorderPane gridDisplay;
    
    static TabPane properties;
    
    static ScrollPane dataPane;
    
    public static ProjectProfile project; //Base project
//    MapProfile currentMap = new MapProfile("Map", 10, 10); //Test Map -- To Be Removed
    
    /**
     * Core method of the Optik Editor. Starts the whole Editor GUI and events.
     * 
     * @param editorWindow
     * @throws MalformedURLException 
     */
    @Override
    public void start(Stage editorWindow) throws MalformedURLException {
        entityHierarchy = new EntityHierarchy(editorWindow);
        wallHierarchy = new WallHierarchy(editorWindow);
        mapHierarchy = new MapHierarchy();
        grid = new Grid();
        gridDisplay = new BorderPane();
        info = new Info();
        
        
        BorderPane view = setupView(metadataContent);
//        project = new ProjectProfile("Test", new MapProfile("map", 10, 10));
        initialize(editorWindow);
   
        Scene scene = new Scene(view, 1920, 1080);
        
        MenuController mc = new MenuController(menu, editorWindow);
        ShortcutController sc = new ShortcutController(shortcuts, editorWindow, wallHierarchy);
        
        String pathName = "dev/editor/style/style.css" ;
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
    
    private void initialize(Stage stage){
//        try {
//            Settings.init();
//            String projectName = Settings.get("ed_proj");//To Remove
//            System.out.println(projectName);
            
            if (ProjectProfile.openProject()) {
                wallHierarchy.setMapProfile(project.getSelectedMap());
                entityHierarchy.setMapProfile(project.getSelectedMap());
                mapHierarchy.setProject(project);
                grid = project.getSelectedMap().getGridView();
                gridDisplay.setCenter(grid);
                setDataView(new WallContent(project.getMainMap().getDefaultWall()));
                new WallController((WallContent)metadataContent, project.getSelectedMap(), stage);
                info.setupInfoBar(project.getSelectedMap().getGc());
                info.start();
                //EntityHierarchy
            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
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
        //Property Tabs Initialization
        Tab entityTab = new Tab("Entities", entityHierarchy);
        Tab wallTab = new Tab("Walls", wallHierarchy);
        Tab mapTab = new Tab("Maps", mapHierarchy);
        
        //Property Pane Setup
        properties = new TabPane(wallTab, entityTab, mapTab);
        properties.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        properties.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                if(newValue.getText().equals("Walls")){
                    project.getSelectedMap().getGc().setEditingMode(1);
                    project.getSelectedMap().getGridView().getSelectionCell().setStroke(Color.YELLOW);
                }
                else if(newValue.getText().equals("Entities")){
                    project.getSelectedMap().getGc().setEditingMode(2);
                    project.getSelectedMap().getGridView().getSelectionCell().setStroke(null);
                }
                else{
                    project.getSelectedMap().getGc().setEditingMode(0);
                    project.getSelectedMap().getGridView().getSelectionCell().setStroke(null);
                }
                
                 System.out.println(project.getSelectedMap().getGc().getEditingMode());
            }
        });
        
        VBox.setVgrow(properties, Priority.ALWAYS);
        
        return properties;
    }
    
    /**
     * 
     * 
     * @return <code></code>
     */
    private TabPane setupMetadata(DataView metadata){
        dataPane = new ScrollPane(metadata);
        Tab data = new Tab("Metadata", dataPane);
        
        TabPane dataTab =  new TabPane(data);
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
        gridDisplay.setCenter(grid);
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
    
    public static void refreshEditor(){
        grid = project.getSelectedMap().getGridView();
        gridDisplay.setCenter(grid);
        info.reload(project.getSelectedMap().getGc());
        wallHierarchy.setMapProfile(project.getSelectedMap());
        entityHierarchy.setMapProfile(project.getSelectedMap());
//        properties.getSelectionModel().selectedItemProperty().addListener(
//            new ChangeListener<Tab>() {
//            @Override
//            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
//                if(newValue.getText().equals("Walls")){
//                    project.getSelectedMap().getGc().setEditingMode(1);
//                    project.getSelectedMap().getGridView().getSelectionCell().setStroke(Color.YELLOW);
//                }
//                else if(newValue.getText().equals("Entities")){
//                    project.getSelectedMap().getGc().setEditingMode(2);
//                    project.getSelectedMap().getGridView().getSelectionCell().setStroke(null);
//                }
//                else{
//                    project.getSelectedMap().getGc().setEditingMode(0);
//                    project.getSelectedMap().getGridView().getSelectionCell().setStroke(null);
//                }
//                
//                 System.out.println(project.getSelectedMap().getGc().getEditingMode());
//            }
//        });
    }
    
    
    public static void setProject(ProjectProfile proj){
        project = proj;
        //Add refresh code
    }
    
    public static ProjectProfile getProject(){
        return project;
    }
    
    public static DataView getDataView(){
        return metadataContent;
    }
    
    public static void setDataView(DataView view){
        metadataContent = view;
        refreshDataView();
    }
    
    private static void refreshDataView(){
        dataPane.setContent(metadataContent);
        metadataContent.reset();
    }
    
    public static EntityHierarchy getEntityHierarchy(){
        return entityHierarchy;
    }

    public static WallHierarchy getWallHierarchy() {
        return wallHierarchy;
    }

    public static MapHierarchy getMapHierarchy() {
        return mapHierarchy;
    }
    
    public static MapProfile load(File pathFile){
        FileReader reader = null;
        MapProfile mapToLoad = null;
        try {
            JSONParser parser = new JSONParser();
            reader = new FileReader(pathFile);
            JSONObject savefile = (JSONObject) parser.parse(reader);
            JSONObject mapInfo = (JSONObject) savefile.get("grid");
            JSONArray entities = (JSONArray) savefile.get("entities");
            JSONArray gridData = new JSONArray();
            
            if(savefile.containsKey("gird")){
                // getting width and height of the grid
                String gridWidthStr =  (String) mapInfo.get("width");
                String gridHeightStr = (String) mapInfo.get("height");
                int gridWidth = Integer.parseInt(gridWidthStr);
                int gridHeight = Integer.parseInt(gridHeightStr);


                // getting the grid array
                int[][] gridArray = new int[gridHeight][gridWidth];
                gridData = (JSONArray) mapInfo.get("data");
                Iterator<JSONArray> rowIterator = gridData.iterator();
                int rowNumber = 0;

                while(rowIterator.hasNext()){
                    JSONArray columns = rowIterator.next();
                    Iterator<Long> colIterator = columns.iterator();
                    int colNumber = 0;
                    while(colIterator.hasNext()){
                        gridArray[rowNumber][colNumber] = colIterator.next().intValue();
                        colNumber++;
                    }
                    rowNumber++;
                }


                //getting grid palette
                JSONArray paletteArray = (JSONArray) mapInfo.get("palette");
                Iterator<Object> paletteIterator = paletteArray.iterator();
                mapToLoad = new MapProfile(pathFile.getName().replaceAll(".lvl", ""), gridWidth, gridHeight);

                while(paletteIterator.hasNext()){
                    JSONObject palette = (JSONObject) paletteIterator.next();
                    String idStr = (String) palette.get("id");
                    int id = Integer.parseInt(idStr); 
                    String imagePath = (String) palette.get("texture");
                    String flagStr = (String) palette.get("flag");
                    int flag = Integer.parseInt(flagStr); 
                    String name = (String) palette.get("name");
                    //System.out.println(id + " , " + imagePath + " , " + flag + " , " + name);

                    mapToLoad.loadWallProfile(name, imagePath.replaceAll("images/textures/", ""), flag, id);
                }

                mapToLoad.getGc().loadPalette(gridArray, mapToLoad);
            //getting entities 
            }
            if(savefile.containsKey("entities")){
                Iterator<Object> entitiesIterator = entities.iterator();
                double [] position = new double[2];
                float [] color = new float[3];
                String entityName = "";
                while(entitiesIterator.hasNext()){
                    JSONObject entity = (JSONObject) entitiesIterator.next();
                    if(entity.containsKey("position")){
                        JSONArray positionArray = (JSONArray) entity.get("position");
                        Iterator<Double> positionIterator = positionArray.iterator();
                        int positionCounter = 0;
                        while(positionIterator.hasNext()){
                            position[positionCounter] = positionIterator.next().doubleValue();
                            positionCounter++;
                        }
                    }
                    if(entity.containsKey("color")){
                        JSONArray colorArray = (JSONArray) entity.get("color");
                        Iterator<Double> colorIterator = colorArray.iterator();
                        int colorCounter = 0;
                        while(colorIterator.hasNext()){
                            color[colorCounter] = (float) colorIterator.next().doubleValue();
                            colorCounter++;
                        }

                    }
                    entityName = (String) entity.get("name");

                    //mapToLoad.getGc().
                    mapToLoad.loadEntityProfile(mapToLoad.getName(), entityName, color , position[0], position[1]);
                }
            }
            else{
                
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return mapToLoad;
    }
}