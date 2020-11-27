/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Metadata;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 *
 * @author A
 */
public class DataView extends Pane{
    //Data Interaction Controls
    protected Button save;
    protected Button cancel;
    protected Button delete;

    public DataView() {
        this.cancel = new Button("Cancel");
        this.save = new Button("Save Changes");
        this.delete = new Button("Delete");
        
        this.save.setDisable(true);
        this.cancel.setDisable(true);
    }
}
