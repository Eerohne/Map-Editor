/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Properties;

import Editor.Model.WallProfile;
import java.util.ArrayList;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;

/**
 *
 * @author A
 */
public class WallTab extends Accordion{
    private ArrayList<WallPane> walls = new ArrayList<>();

    public WallTab() {
        WallPane floorPane = new WallPane(new WallProfile("Floor", "brick.png", 1, true));
        WallPane mossPane = new WallPane(new WallProfile("Black", "grey_brick_vines.png", 1));
        
        walls.add(floorPane);
        walls.add(mossPane);
        
        this.getPanes().addAll(floorPane, mossPane);
    }

    public ArrayList<WallPane> getWalls() {
        return walls;
    }
}
