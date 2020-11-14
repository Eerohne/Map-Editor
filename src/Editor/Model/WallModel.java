/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Model;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import javafx.scene.paint.Color;

/**
 *
 * @author A
 */
public class WallModel {
    public static int wallCounter = 1;
    
    private int wallMode; //0 : Empty, 1: Full, 2 : Door
    private int paletteID;
    private String name;
    
    public static Map<Integer, Color> palette = new TreeMap<Integer, Color>();

    public WallModel(String name, Color color, int wallMode) {
        this.wallMode = wallMode;
        this.paletteID = wallCounter++;
        
        this.name = name;
        
        palette.put(paletteID, color);
    }

    @Override
    public int hashCode() {
        int hash = 5 + paletteID;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WallModel other = (WallModel) obj;
        if (this.wallMode != other.wallMode) {
            return false;
        }
        if (this.paletteID != other.paletteID) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    public int getWallMode() {
        return wallMode;
    }

    public Color getColor() {
        return palette.get(paletteID);
    }

    public String getName() {
        return name;
    }
}
