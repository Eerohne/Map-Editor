/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Menu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private Button mapShort;
    private Button runShort;
    private Button help;
    
    public ShortcutBar() {
        ImageView addWall;
        ImageView addEntity;
        ImageView addMap;
        try {
            addWall = new ImageView(new Image(new FileInputStream("resources/dev/brickicon.png"), 100, 100, true, true));
            addEntity = new ImageView(new Image(new FileInputStream("resources/dev/skeleton_icon.gif"), 100, 100, true, true));
            addMap = new ImageView(new Image(new FileInputStream("resources/dev/map.png"), 100, 100, true, true));

            wallShort = new Button();
            wallShort.setGraphic(addWall);
            mapShort = new Button();
            mapShort.setGraphic(addMap);
            entityShort = new Button();
            entityShort.setGraphic(addEntity);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
        
        
        runShort = new Button("Run");
        help = new Button("?");
    
        Insets insets = new Insets(5);
        
        this.setPadding(insets);
        
        Region space = new Region();
        HBox.setHgrow(space, Priority.ALWAYS);
        
        this.setSpacing(5);
        this.getChildren().addAll(wallShort, entityShort, mapShort, space, runShort, help);
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
