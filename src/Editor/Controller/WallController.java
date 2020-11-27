/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;

import Editor.Model.WallProfile;
import Editor.View.Grid.Cell;
import Editor.View.Grid.Grid;
import Editor.View.Metadata.WallContent;
import Editor.View.Properties.WallHierarchy;
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
public class WallController {
    WallContent wallContent;
    Grid grid;
    WallHierarchy wallHierarchy;
    
    //WallPane Component references
    private Button cancel;
    private Button save;
    private Button delete;
    
    private TextField nameField;
    private Rectangle txrPreview;
    private ComboBox flagCombo;
    
    public WallController(WallContent pane, Grid grid, WallHierarchy wallHierarchy) {
        this.wallContent = pane;
        this.grid = grid;
        this.wallHierarchy = wallHierarchy;
        this.setupReferences();
        
        nameField.setOnMouseClicked(e -> disableButtons(false));
        txrPreview.setOnMouseClicked(e -> disableButtons(false));
        flagCombo.setOnAction(e -> disableButtons(false));
        
        cancel.setOnAction(e -> cancelEvent());
        save.setOnAction(e -> saveEvent());
        delete.setOnAction(e -> {
//            System.out.println(this.wallContent.getWallProfile().getID());
//            System.out.println(WallProfile.palette);
//            for (Map.Entry<Integer, Image> en : WallProfile.palette.entrySet()) {
//                System.out.println(en.getValue().toString());
//                
//            }
        });
    }
    
    private void setupReferences(){
        this.save = wallContent.getSave();
        this.cancel = wallContent.getCancel();
        this.delete = wallContent.getDelete();
        
        this.flagCombo = wallContent.getFlagCombo();
        this.nameField = wallContent.getNameField();
        this.txrPreview = wallContent.getTxrPreview();
    }
    
    private void disableButtons(boolean disable){
        save.setDisable(disable);
        cancel.setDisable(disable);
    }
    
    private void cancelEvent(){
        disableButtons(true);
        wallContent.reset();
    }
    
    private void saveEvent(){
        wallContent.getWallProfile().setFlag((String)flagCombo.getValue());
        wallContent.getWallProfile().setImg("grey_brick.png"); //DUMMY
        wallContent.getWallProfile().setName(nameField.getText());
        for (Cell[] cells : grid.getCells()) {
            for (Cell cell : cells) {
                if(cell.getID() == wallContent.getWallProfile().getID())
                    wallHierarchy.getMapModel().getGc().setImg(cell, wallContent.getWallProfile().getImage());
            }
        }
        wallHierarchy.refresh();
        cancelEvent();
    }
}
