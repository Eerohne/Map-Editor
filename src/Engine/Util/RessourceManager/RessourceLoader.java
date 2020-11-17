/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Util.RessourceManager;

import static Engine.Core.Game.scene;
import Engine.Entity.AbstractEntity.Entity;
import Engine.Entity.EntityCreator;
import Engine.Level.Level;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
 
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
    public static Media loadAudio(String path)
    {
        return new Media(new File("ressources/"+path).toURI().toString());
    }
    
    public static String loadStyleFile(String path) throws MalformedURLException
    {
        String pathName = "ressources/"+path;
        File file = new File(pathName);
        if (file.exists()) {
            return file.toURI().toURL().toExternalForm();
        } else {
           System.out.println("Could not find css file: " + pathName);
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
    
    public static void main( String[] args )
    {
        
    }
}
