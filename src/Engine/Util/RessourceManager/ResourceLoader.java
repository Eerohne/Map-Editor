/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Util.RessourceManager;

import Engine.Core.Exceptions.LevelCreationException;
import Commons.SettingsManager.OrderedProperties;
import Engine.Entity.AbstractEntity.Entity;
import Engine.Entity.EntityCreator;
import Engine.Entity.GameEntity.Entity_Environment;
import Engine.Level.Level;
import Engine.Level.PaletteEntry;
import Engine.RaycastRenderer.Renderer;
import com.sun.scenario.Settings;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.paint.Color;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 
public class ResourceLoader
{
    public static String resourcePath = "";
    
    public static String error_imagePath = "";
    public static Image error_image;
    
    public static String error_texturePath = "";
    
    public static OrderedProperties loadConfigFile()
    {
        OrderedProperties prop = new OrderedProperties();

        try {
            InputStream in = new FileInputStream("config.cfg"); //the config file is always in the engine root folder
            prop.load(in);
            in.close();

            resourcePath = prop.getProperty("e_resourcepath"); //right here set the resourcePath
            error_imagePath = prop.getProperty("e_error_image");
            error_image = loadImage(error_imagePath);
            error_texturePath = prop.getProperty("e_error_texture");
        }
        catch(IOException e) {
            System.out.println("'config.cfg' file was not found in root folder");
        }
        return prop;
    }
    //RessourceLoader.loadImage("images/brick.png");
    public static Image loadImage(String path)
    {
        try{
            FileInputStream inputstream = new FileInputStream(resourcePath + path);
            return new Image(inputstream, 500, 500, true, false);
        }
        catch(IOException e)
        {
            System.out.println("image at path '"+resourcePath+path+"' not found");
            if(error_imagePath != null)
                return error_image;
            else
                System.out.println("error sprite is undefined or inexistant");
        }
        return null;
    }
    
    //AudioPlayer.player.start(ResourceLoader.loadMedia("sounds/musictest.wav"));
    public static Media loadMedia(String path)
    {
        try
        {
            return new Media(new File(resourcePath + path).toURI().toString());
        }
        catch(MediaException e)
        {
            System.out.println("media loading error");
            return null;
        }
        catch(Exception e)
        {
            System.out.println("media at path '"+resourcePath +path+"' not found ");
        }
        return null;
    }
    
    public static String loadStyleFile(String path)
    {
        try{
            String pathName = resourcePath + path;
            File file = new File(pathName);
            return file.toURI().toURL().toExternalForm();
        }
        catch(IOException e)
        {
            System.out.println("style at path '"+path+"' not found");
        }
        return null;
    }
    
    public static Level loadLevel(String path) throws LevelCreationException
    {
        System.out.println("works1");
        Level level = new Level();
        level.path = path;
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(resourcePath + path)) {
            
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONObject grid = (JSONObject) jsonObject.get("grid");
            
            //1) level grid
            int gridWidth = Integer.valueOf((String)grid.get("width"));
            int gridHeight = Integer.valueOf((String)grid.get("height"));
            int gridArray[][] = new int[gridHeight][gridWidth];
            
            JSONArray rowsData = (JSONArray) grid.get("data");
            Iterator<JSONArray> rowsIterator = rowsData.iterator();
            int row = 0;
            while (rowsIterator.hasNext()) {
                JSONArray collumsData = (JSONArray) rowsIterator.next();
                Iterator<Long> collumsIterator = collumsData.iterator();
                int col = 0;
                while (collumsIterator.hasNext()) {
                    gridArray[row][col] = collumsIterator.next().intValue();
                    col++;
                }
                row++;
            }
            level.setLevelData(gridArray);

            System.out.println("works2");
            //2) level palette
            JSONArray paletteArray = (JSONArray) grid.get("palette");
            Iterator<Object> paletteIterator = paletteArray.iterator();
            while (paletteIterator.hasNext()) {
                JSONObject jsonEntry = (JSONObject) paletteIterator.next();
                
                int id = Integer.valueOf((String)jsonEntry.get("id"));
                
                /*JSONArray colorArr = (JSONArray) jsonEntry.get("color");
                Color color = Color.color(
                        (double) colorArr.get(0),
                        (double) colorArr.get(1),
                        (double) colorArr.get(2));*/
                
                Image texture = loadImage((String)jsonEntry.get("texture"));
                int flag = Integer.valueOf((String)jsonEntry.get("flag"));
                
                PaletteEntry entry = new PaletteEntry(texture, flag);
                level.putPaletteEntry(id, entry);
            }
            
            //3) level entities
            Renderer.setEnvironment(new Entity_Environment());
            JSONArray entityArray = (JSONArray) jsonObject.get("entities");
            Iterator<Object> entityIterator = entityArray.iterator();
            while (entityIterator.hasNext()) {
                JSONObject jsonEntry = (JSONObject) entityIterator.next();
                HashMap<String, Object> properties = new HashMap<>();
                jsonEntry.forEach((k, v) -> {
                    properties.put((String)k, (Object)v);
                });
                Entity entity = EntityCreator.constructEntity(properties);
                if(entity != null)
                    level.addEntity(entity);
            }
            //verify if player entity exists
            if(level.getPlayer() == null)
                throw new LevelCreationException("No player entity inside the level");

        } catch (IOException e) {
            throw new LevelCreationException("invalid or inexistant level path '"+resourcePath + path+"'");
        } catch (ParseException e) {
            throw new LevelCreationException("level file '"+resourcePath + path+"' contains a json format error : \n"+ e.getMessage());
        } catch(NumberFormatException e) {
            throw new LevelCreationException("level file '"+resourcePath + path+"' contains a parse error : \n"+ e.getMessage());
        } catch(ClassCastException e) {
            throw new LevelCreationException("level file '"+resourcePath + path+"' contains a property error : \n"+ e.getMessage());
        } catch(NullPointerException e) {
            throw new LevelCreationException("level file '"+resourcePath + path+"' throws null error when loading : \n"+ e.getMessage());
        }
        
        return level;
    }
}
