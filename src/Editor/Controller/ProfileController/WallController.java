/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller.ProfileController;

import Editor.Model.Profile.WallProfile;
import Editor.View.Grid.Cell;
import Editor.View.Grid.Grid;
import Editor.View.Metadata.WallContent;
import Editor.View.Hierarchy.WallHierarchy;
import java.io.File;
import java.util.Map;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Controller of the WallContent Class
 * @author A
 */
public class WallController extends MetadataController {
    Grid grid;
    String newImage;
    
    //WallPane Component references
    
    private TextField nameField;
    private Rectangle txrPreview;
    private ComboBox flagCombo;
    
    public WallController(Stage stage, WallHierarchy hierarchy) {
        super(hierarchy.getWallContent(), hierarchy);
        this.grid = hierarchy.getMapModel().getGridView();
        this.setupReferences((WallContent)content);
        
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
                fileChooser.setInitialDirectory(new File(WallProfile.resourceFolder));
                File textureFile = fileChooser.showOpenDialog(stage);

                this.newImage = textureFile.getName();
            });
            flagCombo.setOnAction(e -> disableButtons(false));
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
        ((WallContent)content).getWallProfile().setImg(newImage); //DUMMY
        ((WallContent)content).getWallProfile().setName(nameField.getText());
        for (Cell[] cells : grid.getCells()) {
            for (Cell cell : cells) {
                if(cell.getID() == ((WallContent)content).getWallProfile().getID())
                    ((WallHierarchy)hierarchy).getMapModel().getGc().setImg(cell, ((WallContent)content).getWallProfile().getImage());
            }
        }
    }
}
