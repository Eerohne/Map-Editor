
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Inspector;

import Editor.Model.Profile.Profile;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author A
 */
public abstract class InspectorView extends Pane{
    //Data Interaction Controls
    protected Button save;
    protected Button cancel;
    protected Button delete;

    protected Profile profile;
    
    public InspectorView(Profile profile) {
        this.profile = profile;
        
        this.cancel = new Button("Cancel");
        this.save = new Button("Save Changes");
        this.delete = new Button("Delete");
        
        this.save.setDisable(true);
        this.cancel.setDisable(true);
    }
    
    protected abstract VBox setupPane();
    
    public void changeContent(Profile content){
        this.profile = content;
        this.reset();
    }

    public abstract void reset();
    
    protected HBox getButtonBox(Insets padding){
        HBox buttons = new HBox(save, cancel, delete);
        buttons.setPadding(padding);
        buttons.setSpacing(5);
        
        return buttons;
    }
    
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    
     public Button getCancel() {
        return cancel;
    }

    public void setCancel(Button cancel) {
        this.cancel = cancel;
    }

    public Button getSave() {
        return save;
    }

    public void setSave(Button save) {
        this.save = save;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }
}
