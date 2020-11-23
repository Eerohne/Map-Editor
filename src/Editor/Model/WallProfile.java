/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 *
 * @author A
 */
public class WallProfile {
    public static int wallCounter = 1;
    public static String resourceFolder = "resources/images/textures/";
    
    private boolean isDefault;
    private int flag; //0 : Empty, 1: Full, 2 : Door
    private int paletteID;
    private String name;
    
    public static Map<Integer, Image> palette = new TreeMap<Integer, Image>();

    public WallProfile(String name, String imageName, int wallMode) {
        this(name, imageName, wallMode, false);
    }
    
    public WallProfile(String name, String imageName, int wallMode, boolean isDefault){
        this.flag = wallMode;
        this.paletteID = wallCounter++;
        this.isDefault = isDefault;
        
        this.name = name;
        
        try {
            palette.put(paletteID, new Image(new FileInputStream(resourceFolder + imageName), 100, 100, false, false));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
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
        final WallProfile other = (WallProfile) obj;
        if (this.flag != other.flag) {
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

    public int getFlag() {
        return flag;
    }

    public Image getImage() {
        return palette.get(paletteID);
    }

    public String getName() {
        return name;
    }
    
    public int getPaletteID(){
        return paletteID;
    }
    
    public static int getPaletteID(Image img){
        for (Map.Entry<Integer, Image> entry : palette.entrySet()) {
            if (entry.getValue().equals(img)) {
                return entry.getKey();
            }
        }
        return -1;
    }
}
