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
import java.util.Map;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * Controller of the WallContent Class
 * @author A
 */
public class WallController extends MetadataController {
    Grid grid;
    
    //WallPane Component references
    private Button cancel;
    private Button save;
    private Button delete;
    
    private TextField nameField;
    private Rectangle txrPreview;
    private ComboBox flagCombo;
    
    public WallController(WallContent content, Grid grid, WallHierarchy hierarchy) {
        super(content, hierarchy);
        this.grid = grid;
        this.setupReferences(content);
        
        nameField.setOnMouseClicked(e -> disableButtons(false));
        txrPreview.setOnMouseClicked(e -> disableButtons(false));
        flagCombo.setOnAction(e -> disableButtons(false));
    }
    
    private void setupReferences(WallContent content){
        this.flagCombo = content.getFlagCombo();
        this.nameField = content.getNameField();
        this.txrPreview = content.getTxrPreview();
    }
    
    @Override
    protected void saveAction(){
        ((WallContent)content).getWallProfile().setFlag((String)flagCombo.getValue());
        ((WallContent)content).getWallProfile().setImg("grey_brick.png"); //DUMMY
        ((WallContent)content).getWallProfile().setName(nameField.getText());
        for (Cell[] cells : grid.getCells()) {
            for (Cell cell : cells) {
                if(cell.getID() == ((WallContent)content).getWallProfile().getID())
                    ((WallHierarchy)hierarchy).getMapModel().getGc().setImg(cell, ((WallContent)content).getWallProfile().getImage());
            }
        }
    }
}
