/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author A
 */
public class MetaDataController{
    private static Pane content;
    private ScrollPane display;
    
    public MetaDataController(ScrollPane display) {
        this.display = display;
        display.setContent(content);
    }
    
    public static void setContent(Pane content, ContentController controller){
        MetaDataController.content = content;
    }
}
