/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Level;

import Engine.Entity.AbstractEntity.Entity;
import Engine.Entity.GameEntity.Entity_Player;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javafx.scene.paint.Color;

//Merouane Issad
public class Level {
    private int[][] levelData; //2d array with wall data
    private Map<Integer, PaletteEntry> palette; //Look-up table
    private Map<String, Entity> entities; //Entities stored by their name
    
    private Entity playerEntity;
    //flags
    private boolean firstUpdate = true;
    
    public Level(){
        palette = new HashMap<Integer, PaletteEntry>();
        entities = new HashMap<String, Entity>();
    }
    
    public boolean isWall(int x, int y)
    {
        int index = getCellValue(x, y);
        return getPaletteEntry(index).getFlag() == 1;
    }
    
    public Color getCellColor(int x, int y)
    {
        int index = getCellValue(x, y);
        try{
        getPaletteEntry(index);
        return getPaletteEntry(index).getColor();
        }
        catch(NullPointerException e)
        {
            System.out.println("palette entry "+index+" does not exist");
        }
        return null;
    }
    
    //LevelData code
    public void setLevelData(int[][] data)
    {
        this.levelData = data;
    }
    
    public int getCellValue(int x, int y)
    {
        return levelData[x][y];
    }
    
    //Palette code
    public PaletteEntry getPaletteEntry(int index)
    {
        return palette.get(index);
    }
    public void putPaletteEntry(int index, PaletteEntry data)
    {
        palette.put(index, data);
    }
    
    //Entities code
    public void addEntity(Entity entity)
    {
        entities.put(entity.getName(), entity);
        if(entity instanceof Entity_Player) //store player in a global variable for easy access
            playerEntity = entity;
        if(!firstUpdate)
            entity.start();
    }
    
    public Entity getEntity(String entityName)
    {
        return entities.get(entityName);
    }
    
    public void removeEntity(String entityName)
    {
        entities.remove(entityName);
    }
    
    public void update() //update all entities. If this is the first update, call the entities start method instead to initialize them
    {
        Iterator<Entity> it = entities.values().iterator();
        while(it.hasNext())
        {
            Entity entity = it.next();
            if(entity.getActive()){
                if(firstUpdate)
                    entity.start();
                else
                    entity.update();
            }
        }
        firstUpdate = false;
    }
    
    public Entity getPlayer()
    {
        return playerEntity;
    }
}
