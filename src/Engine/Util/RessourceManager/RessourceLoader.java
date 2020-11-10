/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Util.RessourceManager;

import Engine.Entity.AbstractEntity.Entity;
import Engine.Entity.EntityCreator;
import Engine.Level.Level;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import javafx.scene.image.Image;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sun.audio.AudioStream;
 
public class RessourceLoader
{
    //RessourceLoader.loadImage("images/brick.png");
    public static Image loadImage(String path)
    {
        try{
            FileInputStream inputstream = new FileInputStream("ressources/"+path);
            return new Image(inputstream);
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
        return null;
    }
    
    //AudioPlayer.player.start(RessourceLoader.loadAudio("sounds/musictest.wav"));
    public static AudioStream loadAudio(String path)
    {
        try{
            FileInputStream inputstream = new FileInputStream("ressources/"+path);
            return new AudioStream(inputstream);
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
        return null;
    }
    
    public static Level loadLevel(String path)
    {
        Level level = new Level();
        //level file here
        try{
            FileInputStream inputstream = new FileInputStream("ressources/"+path);
            //load level file (xml or json)
        }
        catch(IOException e)
        {
            System.out.println("Level file " + "ressources/"+path + "not found");
            return null;
        }
        //1) parse file for level data
        
        //2) parse file for palette entries
        
        //3) parse file for all entities and get their parameters
        /*for(all entities in the level file)
        {
            HashMap<String, String> entityProperties = levelfile.extractEntityPropertiesAndPutThemInTheMap;
            Entity entity = EntityCreator.constructEntity(entityProperties);
            if(entity != null)
                level.addEntity(entity);
        }*/
        
        return level;
    }
    
    public static Level loadLevel(Level level){
        
    }
    
    public static void main( String[] args )
    {
        
    }
}
