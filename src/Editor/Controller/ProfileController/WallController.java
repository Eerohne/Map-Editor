/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller.ProfileController;

import Editor.Main.MapEditor;
import Editor.Model.Profile.MapProfile;
import Editor.Model.Profile.WallProfile;
import Editor.View.Grid.Cell;
import Editor.View.Grid.Grid;
import Editor.View.Inspector.WallContent;
import Editor.View.Hierarchy.WallHierarchy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Controller of the WallContent Class
 * @author A
 */
public class WallController extends InspectorController {
    Grid grid;
    MapProfile map;
    Image newImage;
    String imgName;
    Stage stage;
    //WallPane Component references
    
    private TextField nameField;
    private Rectangle txrPreview;
    private ComboBox flagCombo;
    
    public WallController(WallContent content, MapProfile map, Stage stage) {
        super(content);
        this.grid = map.getGridView();
        this.map = map;
        this.imgName = content.getWallProfile().getImageName();
        this.setupReferences((WallContent)content);
        this.stage = stage;
        if(((WallContent)content).getWallProfile().isDelete()){
            super.delete.setDisable(true);
            nameField.setDisable(true);
            flagCombo.setDisable(true);
        }
        else{
            super.delete.setDisable(false);
            nameField.setDisable(false);
            flagCombo.setDisable(false);
            
            nameField.setOnMouseClicked(e -> disableButtons(false));
            txrPreview.setOnMouseClicked(e -> {
                    disableButtons(false);
                    FileChooser fileChooser = new FileChooser();
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Textures", "*.png", "*.jpg");
                    fileChooser.getExtensionFilters().add(extFilter);
                    fileChooser.setTitle("Choose A Wall Texture");
                    fileChooser.setInitialDirectory(new File(MapEditor.getProject().getImageFolder()));
                    File textureFile = fileChooser.showOpenDialog(stage);
                try {
                    this.newImage = new Image(new FileInputStream(textureFile.getAbsoluteFile()), 100, 100, false, false);
                    this.imgName = textureFile.getName();
                    txrPreview.setFill(new ImagePattern(newImage));
                } catch (FileNotFoundException ex) {
                       System.out.println(ex);
                }
            });
            flagCombo.setOnAction(e -> {
                    disableButtons(false);
            });
        }
    }
    
    private void setupReferences(WallContent content){
        this.flagCombo = content.getFlagCombo();
        this.nameField = content.getNameField();
        this.txrPreview = content.getTxrPreview();
    }
    
    @Override
    protected void saveAction(){
        ((WallContent)content).getWallProfile().setFlag((String)flagCombo.getValue());
        ((WallContent)content).getWallProfile().setImg(imgName);
        ((WallContent)content).getWallProfile().setName(nameField.getText());
        for (Cell[] cells : grid.getCells()) {
            for (Cell cell : cells) {
                if(cell.getID() == ((WallContent)content).getWallProfile().getID())
                    this.map.getGc().setImg(cell, ((WallContent)content).getWallProfile().getImage());
            }
        }
        
        MapEditor.getWallHierarchy().refresh();
    }
    
    @Override
    protected void deleteAction() {
        this.map.getGc().setSelectedWallProfile(MapEditor.getProject().getSelectedMap().getDefaultWall());
        
        for (Cell[] cells : grid.getCells()) {
            for (Cell cell : cells) {
                if(cell.getID() == ((WallContent)content).getWallProfile().getID()){
                    System.out.println(cell.getID());
                    this.map.getGc().setImg(cell, this.map.getDefaultWall().getDeleteImage());
                    cell.setID(0);
                }
            }
        }
        WallContent wallContent = new WallContent(this.map.getGc().getSelectedWallProfile());
        WallController wc = new WallController(wallContent, map, stage);
        MapEditor.setDataView(wallContent);
        
        MapEditor.getProject().getSelectedMap().getWallMap().remove(((WallContent)content).getWallProfile().getID());
        MapEditor.getWallHierarchy().refresh();
    }
    
    public void refresh(){
        
    }
}
