/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

/**
 *
 * @author A
 */
public class WallProfile {
    public static String resourceFolder = "resources/images/textures/";
    public static String[] flagArray = {"Empty", "Wall"};
    
    private String imgName;
    private String wallName;
    private Image img;
    private int flag; //0 : Empty, 1: Full
    private int id;
    
    //public Map<Integer,Image> palette;
    //public Map<Integer, WallProfile> wallMap;
    
    public WallProfile(int id, String name, String imageName, int flag){
        this.flag = flag;
        this.imgName = imageName;
        
        this.wallName = name;
        
        this.setImg(imageName);
    }

    public int getFlag() {
        return flag;
    }

    public Image getImage() {
        return this.img;
    }

    public String getName() {
        return wallName;
    }
    
    public int getID(){
        return id;
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

    public void setFlag(String flag) {
        this.flag = getWallFlag(flag);
    }

    public void setName(String name) {
        this.wallName = name;
    }

    private void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public void setImg(String img) {
        this.setImgName(imgName);
        try {
            this.img = new Image(new FileInputStream(resourceFolder + imgName), 100, 100, false, false);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }
}
