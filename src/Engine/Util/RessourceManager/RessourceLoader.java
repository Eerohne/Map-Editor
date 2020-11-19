/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Util.RessourceManager;

import Engine.Entity.AbstractEntity.Entity;
import Engine.Entity.EntityCreator;
import Engine.Level.Level;
import Engine.Level.PaletteEntry;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 
public class RessourceLoader
{
    public static String ressourcePath = "ressources/";
    //RessourceLoader.loadImage("images/brick.png");
    public static Image loadImage(String path)
    {
        try{
            FileInputStream inputstream = new FileInputStream(ressourcePath + path);
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
        return new Media(new File(ressourcePath + path).toURI().toString());
    }
    
    public static String loadStyleFile(String path) throws MalformedURLException
    {
        String pathName = ressourcePath + path;
        File file = new File(pathName);
        if (file.exists()) {
            return file.toURI().toURL().toExternalForm();
        } else {
           System.out.println("Could not find css file: " + pathName);
        }
        return null;
    }
    
    public static Level loadLevel(String path) throws ParseException
    {
        Level level = new Level();
        
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(ressourcePath + path)) {
            
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONObject grid = (JSONObject) jsonObject.get("grid");
            
            //1) level grid
            int gridWidth = Integer.valueOf((String)grid.get("width"));
            int gridHeight = Integer.valueOf((String)grid.get("height"));
            int gridArray[][] = new int[gridWidth][gridHeight];
            
            JSONArray rowsData = (JSONArray) grid.get("data");
            Iterator<JSONArray> rowsIterator = rowsData.iterator();
            int row = 0;
            while (rowsIterator.hasNext()) {
                JSONArray collumsData = (JSONArray) rowsIterator.next();
                Iterator<Long> collumsIterator = collumsData.iterator();
                int col = 0;
                while (collumsIterator.hasNext()) {
                    //System.out.println(collumsIterator.next().intValue());
                    gridArray[row][col] = collumsIterator.next().intValue();
                    col++;
                }
                row++;
            }
            level.setLevelData(gridArray);

            //2) level palette
            JSONArray paletteArray = (JSONArray) grid.get("palette");
            Iterator<Object> paletteIterator = paletteArray.iterator();
            while (paletteIterator.hasNext()) {
                JSONObject jsonEntry = (JSONObject) paletteIterator.next();
                
                int id = Integer.valueOf((String)jsonEntry.get("id"));
                
                JSONArray colorArr = (JSONArray) jsonEntry.get("color");
                Color color = Color.color(
                        Float.valueOf((long)colorArr.get(0)),
                        Float.valueOf((long)colorArr.get(1)),
                        Float.valueOf((long)colorArr.get(2)));
                
                int flag = Integer.valueOf((String)jsonEntry.get("flag"));
                
                PaletteEntry entry = new PaletteEntry(color, flag);
                level.putPaletteEntry(id, entry);
            }
            
            //3) level entities
            JSONArray entityArray = (JSONArray) jsonObject.get("entities");
            Iterator<Object> entityIterator = entityArray.iterator();
            while (entityIterator.hasNext()) {
                JSONObject jsonEntry = (JSONObject) entityIterator.next();
                HashMap<String, String> properties = new HashMap<>();
                jsonEntry.forEach((k, v) -> {
                    properties.put((String)k, (String)v);
                });
                Entity entity = EntityCreator.constructEntity(properties);
                if(entity != null)
                    level.addEntity(entity);
            }
            
            

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        return level;
    }
    
    public static void setRessourcePath(String path)
    {
        ressourcePath = path+"/";
    }
    
    public static void main( String[] args )
    {
        try {
            loadLevel("levels/level1.lvl");
        } catch (ParseException ex) {
            Logger.getLogger(RessourceLoader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
