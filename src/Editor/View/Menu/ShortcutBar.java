/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Menu;

import java.io.ObjectOutputStream;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 *
 * @author A
 */
public class ShortcutBar extends HBox{
    private Button wallShort;
    private Button entityShort;
    private Button runShort;
    private Button help;
    
    public ShortcutBar() {
        wallShort = new Button("Add New Wall");
        //wallShort.setPrefSize(this.getHeight()-10, this.getHeight()-10);
        entityShort = new Button("Create New Entity");
        runShort = new Button("Run");
        help = new Button("Help");
    
        Insets insets = new Insets(5);
        
        this.setPadding(insets);
        
        Region space = new Region();
        HBox.setHgrow(space, Priority.ALWAYS);
        
        this.setSpacing(5);
        this.getChildren().addAll(wallShort, entityShort, space, runShort, help);
    }

    public Button getWallShort() {
        return wallShort;
    }

    public Button getEntityShort() {
        return entityShort;
    }
    public Button getRunShort() {
        return runShort;
    }

    public Button getHelp() {
        return help;
    }
        
    
}
