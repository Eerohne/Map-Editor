/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller.ProfileController;

import Editor.View.Hierarchy.Hierarchy;
import Editor.View.Metadata.DataView;
import javafx.scene.control.Button;

/**
 *
 * @author A
 */
public abstract class MetadataController {
    protected DataView content;
    protected Hierarchy hierarchy;
    
    protected Button cancel;
    protected Button save;
    protected Button delete;

    public MetadataController(DataView content, Hierarchy hierarchy) {
        this.save = content.getSave();
        this.cancel = content.getCancel();
        this.delete = content.getDelete();
        
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
        
        this.content = content;
        this.hierarchy = hierarchy;
    }
    
    protected void disableButtons(boolean disable){
        save.setDisable(disable);
        cancel.setDisable(disable);
    }
    
    protected void cancelEvent(){
        disableButtons(true);
        content.reset();
    }
    
    private void saveEvent(){
        saveAction();
        hierarchy.refresh();
        cancelEvent();
    }
    
    protected abstract void saveAction();
    
    
}
