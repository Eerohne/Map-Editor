

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
    private Button saveShort;
    private Button help;
    
    public ShortcutBar() {
        ImageView addWall;
        ImageView addEntity;
        ImageView addMap;
        ImageView runImage;
        ImageView helpImage;
        ImageView saveImage;
        
        try {
            addWall = new ImageView(new Image(new FileInputStream("dev/editor/brickicon.png"), 32, 32, true, true));
            addEntity = new ImageView(new Image(new FileInputStream("dev/editor/skeleton_icon.gif"), 32, 32, true, true));
            addMap = new ImageView(new Image(new FileInputStream("dev/editor/map.png"), 32, 32, true, true));
            runImage = new ImageView(new Image(new FileInputStream("dev/editor/playicon.png"), 32, 32, true, true));
            helpImage = new ImageView(new Image(new FileInputStream("dev/editor/helpicon.png"), 32, 32, true, true));
            saveImage = new ImageView(new Image(new FileInputStream("dev/editor/saveicon.png"), 32, 32, true, true));
            
            wallShort = new Button("", addWall);
            mapShort = new Button("", addMap);
            entityShort = new Button("", addEntity);
            runShort = new Button("", runImage);
            help = new Button("", helpImage);
            saveShort = new Button("", saveImage);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
        Insets insets = new Insets(5);
        
        this.setPadding(insets);
        
        Region space = new Region();
        HBox.setHgrow(space, Priority.ALWAYS);
        
        this.setSpacing(5);
        this.getChildren().addAll(wallShort, entityShort, mapShort, space, saveShort, runShort, help);
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

    public Button getMapShort() {
        return mapShort;
    }

    public Button getSaveShort() {
        return saveShort;
    }
}
