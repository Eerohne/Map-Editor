/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;

import Editor.View.Grid.Grid;
import Editor.View.Properties.WallPane;
import Editor.View.Properties.WallTab;

/**
 *
 * @author A
 */
public class WallPropertyPaneController {
    WallTab tab;
    Grid grid;
    
    public WallPropertyPaneController(WallTab tab, Grid grid) {
        for (WallPane wall : tab.getWalls()) {
            new WallController(wall, grid);
        }
    }
    
    
}
