/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller.ProfileController;

import Editor.View.Hierarchy.Hierarchy;
import Editor.View.Inspector.InspectorView;
import javafx.scene.control.Button;

/**
 *
 * @author A
 */
public abstract class InspectorController {
    protected InspectorView content;
    
    protected Button cancel;
    protected Button save;
    protected Button delete;

    public InspectorController(InspectorView content) {
        this.save = content.getSave();
        this.cancel = content.getCancel();
        this.delete = content.getDelete();
        
        cancel.setOnAction(e -> cancelEvent());
        save.setOnAction(e -> saveEvent());
        delete.setOnAction(e -> {deleteAction();});
        
        this.content = content;
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
        cancelEvent();
    }
    
    private void deleteEvent(){
        deleteAction();
    }
    
    protected abstract void saveAction();
    
    protected abstract void deleteAction();
}
