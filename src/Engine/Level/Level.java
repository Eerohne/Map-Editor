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

//Merouane Issad
public class Level {
    private int[][] levelData; //2d array with wall data
    private Palette palette; //Look-up table
    private Map<String, Entity> entities; //Entities stored by their name
    
    private Entity playerEntity;
    //flags
    private boolean isRunning = false;
    
    public Level(){
        entities = new HashMap<String, Entity>();
    }
    
    public int getCellValue(int x, int y)
    {
        return levelData[x][y];
    }
    
    public void setLevelData(int[][] data)
    {
        this.levelData = data;
    }
    
    public Palette getPalette()
    {
        return palette;
    }
    
    public void setPalette(Palette palette)
    {
        this.palette = palette;
    }
    
    public void addEntity(Entity entity)
    {
        entities.put(entity.getName(), entity);
        if(entity instanceof Entity_Player){
            playerEntity = entity;
            System.out.println("player");
        }
        else
        {
            System.out.println("not player");
        }
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
        if(!isRunning) //first update
        {
            Iterator<Entity> it = entities.values().iterator();
            while(it.hasNext())
            {
                it.next().start();
            }
            isRunning = true;
        }
        else //not first update
        {
            Iterator<Entity> it = entities.values().iterator();
            while(it.hasNext())
            {
                Entity entity = it.next();
                if(entity.getActive())
                    it.next().update();
            }
        }
    }
}
