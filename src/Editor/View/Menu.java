/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View;

import Editor.Controller.MenuController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 *
 * @author A
 */
public class Menu extends HBox{
    private Button newWall;
    private Button newEntity;
    private Button placePlayer;
    private Button help;

    public Menu() {
        this.newWall = new Button("Add New Wall");
        this.newEntity = new Button("Add New Entity");
        this.placePlayer = new Button("Place Player");
        this.help = new Button("Help");
        
        Insets padding = new Insets(5);
        
//        newWall.setPadding(padding);
//        newEntity.setPadding(padding);
//        placePlayer.setPadding(padding);
//        help.setPadding(padding);
        
        this.setSpacing(5);
        this.setPadding(padding);
        
        Region test = new Region();
        
        HBox.setHgrow(test, Priority.ALWAYS);
        
        this.getChildren().addAll(newWall, newEntity, placePlayer, test, help);
        
        
    }
    
    public Button getNewWall() {
        return newWall;
    }

    public Button getNewEntity() {
        return newEntity;
    }

    public Button getPlacePlayer() {
        return placePlayer;
    }

    public Button getHelp() {
        return help;
    }
}
