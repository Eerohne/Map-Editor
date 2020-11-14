/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Properties;

import Editor.Model.WallModel;
import java.util.ArrayList;
import javafx.scene.control.Accordion;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author A
 */
public class WallTab extends Accordion{
    //private ArrayList<TitledPane> entities = new ArrayList<>();

    public WallTab() {
        TitledPane floorPane = setupWallPane(new WallModel("Floor", Color.WHITE, 1));
        TitledPane blackWallPane = setupWallPane(new WallModel("Black", Color.BLACK, 1));
        this.getPanes().addAll(floorPane, blackWallPane);
    }
    
    private TitledPane setupWallPane(WallModel wall){
        Label name = new Label("Name : " + wall.getName());
        ColorPicker color = new ColorPicker(wall.getColor());
        Label mode = new Label("Wall Mode : " + wall.getWallMode());
        
        VBox floorPane = new VBox(name, color, mode);
        
        return new TitledPane(wall.getName(), floorPane);
    }
}
