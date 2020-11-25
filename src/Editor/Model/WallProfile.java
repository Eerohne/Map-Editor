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
import javafx.scene.image.Image;

/**
 *
 * @author A
 */
public class WallProfile {
    private static int wallCounter = 0;
    public static String resourceFolder = "resources/images/textures/";
    public static String[] flagArray = {"Empty", "Wall"};
    
    private String imgName;
    private String wallName;
    private int flag; //0 : Empty, 1: Full
    private int id;
    
    public static Map<Integer,Image > palette = new TreeMap<Integer, Image>();
    public static Map<Integer, WallProfile> wallMap = new TreeMap<Integer, WallProfile>();
    
    public WallProfile(String name, String imageName, int wallMode){
        this.flag = wallMode;
        this.id = wallCounter++;
        this.imgName = imageName;
        
        this.wallName = name;
        
        try {
            palette.put(id, new Image(new FileInputStream(resourceFolder + imageName), 100, 100, false, false));
            wallMap.put(id, this);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int getFlag() {
        return flag;
    }

    public Image getImage() {
        return palette.get(id);
    }

    public String getName() {
        return wallName;
    }
    
    public int getID(){
        return id;
    }
    
    public static int getID(Image img){
        for (Map.Entry<Integer, Image> entry : palette.entrySet()) {
            if (entry.getValue().equals(img)) {
                return entry.getKey();
            }
        }
        return -1;
    }
    
    public String getImageName(){
        return this.imgName;
    }
    
    public static int getWallFlag(String flag){
        for (int i = 0; i < flagArray.length; i++) {
            if (flagArray[i].equals(flag)) {
                return i;
            }
        }
        
        return -1;
    }
    
    public static String getTxrURL(int id){
        for (Map.Entry<Integer, WallProfile> entry : wallMap.entrySet()) {
            if(entry.getKey() == id){
                return WallProfile.resourceFolder + entry.getValue().getImageName();
            }
        }
        
        return null;
    }

    public void setFlag(String flag) {
        this.flag = getWallFlag(flag);
    }

    public void setName(String name) {
        this.wallName = name;
    }
    
    public void setImg(String img){
        try {
            palette.put(id, new Image(new FileInputStream(resourceFolder + img), 100, 100, false, false));
            this.imgName = img;
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }
}
