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
public class NewWallProfile {
    private String filepath;
    
    
    public NewWallProfile(Stage parent, String filepath) {
        Stage newWallWindow = new Stage();
        newWallWindow.initOwner(parent);
        newWallWindow.initModality(Modality.APPLICATION_MODAL);
        
        
    }
}
