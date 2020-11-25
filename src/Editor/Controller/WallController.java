/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;

import Editor.Model.WallProfile;
import Editor.View.Grid.Cell;
import Editor.View.Grid.Grid;
import Editor.View.Properties.WallPane;
import Editor.View.Properties.WallTab;
import java.util.Map;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author A
 */
public class WallController extends ContentController{
    WallPane wallPane;
    Grid grid;
    WallTab wallHierarchy;
    
    //WallPane Component references
    private Button cancel;
    private Button save;
    private Button delete;
    
    private TextField nameField;
    private Rectangle txrPreview;
    private ComboBox flagCombo;
    
    public WallController(WallPane pane, Grid grid, WallTab wallHierarchy) {
        this.wallPane = pane;
        this.grid = grid;
        this.setupReferences();
        
        nameField.setOnMouseClicked(e -> disableButtons(false));
        txrPreview.setOnMouseClicked(e -> disableButtons(false));
        flagCombo.setOnAction(e -> disableButtons(false));
        
        cancel.setOnAction(e -> cancelEvent());
        save.setOnAction(e -> saveEvent());
        delete.setOnAction(e -> {
            System.out.println(this.wallPane.getWallProfile().getID());
            System.out.println(WallProfile.palette);
            for (Map.Entry<Integer, Image> en : WallProfile.palette.entrySet()) {
                System.out.println(en.getValue().toString());
                
            }
        });
    }
    
    private void setupReferences(){
        this.save = wallPane.getSave();
        this.cancel = wallPane.getCancel();
        this.delete = wallPane.getDelete();
        
        this.flagCombo = wallPane.getFlagCombo();
        this.nameField = wallPane.getNameField();
        this.txrPreview = wallPane.getTxrPreview();
    }
    
    private void disableButtons(boolean disable){
        save.setDisable(disable);
        cancel.setDisable(disable);
    }
    
    private void cancelEvent(){
        disableButtons(true);
        wallPane.reset();
    }
    
    private void saveEvent(){
        wallPane.getWallProfile().setFlag((String)flagCombo.getValue());
        wallPane.getWallProfile().setImg("grey_brick.png"); //DUMMY
        wallPane.getWallProfile().setName(nameField.getText());
        for (Cell[] cells : grid.getCells()) {
            for (Cell cell : cells) {
                if(cell.getID() == wallPane.getWallProfile().getID())
                    cell.setImg(wallPane.getWallProfile().getImage());
            }
        }
        wallHierarchy.refresh();
        cancelEvent();
    }
}
