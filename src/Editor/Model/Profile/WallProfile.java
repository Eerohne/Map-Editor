/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Model.Profile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;

/**
 *
 * @author A
 */
public class WallProfile extends Profile{
    public static String resourceFolder = "resources/images/textures/";//To displace
    public static String[] flagArray = {"Empty", "Wall"};
    
    private String imgName;
    private Image img;
    private int flag; //0 : Empty, 1: Full
    private int id;
    
    public WallProfile(int id, String name, String imageName, int flag){
        super(name);
        this.flag = flag;
        this.imgName = imageName;
        
        this.setImg(imageName);
    }

    public int getFlag() {
        return flag;
    }

    public Image getImage() {
        return this.img;
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
