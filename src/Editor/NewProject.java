/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor;

import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author A
 */
public class NewProject {
    
    public NewProject(Stage parent){
        Stage newProjectWindow = new Stage();
        newProjectWindow.initOwner(parent);
        newProjectWindow.initModality(Modality.APPLICATION_MODAL);
    }
}
