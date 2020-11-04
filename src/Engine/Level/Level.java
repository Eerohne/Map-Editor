/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Level;

import Engine.Entity.AbstractEntity.Entity;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//Merouane Issad
public class Level {
    private int[][] levelData; //2d array with wall data
    private Palette palette; //Look-up table
    private Map<String, Entity> entities; //Entities stored by their name
    
    //flags
    private boolean isRunning = false;
    
    public Level(){
        entities = new HashMap<String, Entity>();
    }
    
    public int getCellValue(int x, int y)
    {
        return levelData[x][y];
    }
    
    public Palette acessPalette()
    {
        return palette;
    }
    
    public void addEntity(Entity entity)
    {
        entities.put(entity.getName(), entity);
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
        if(!isRunning) //first time
        {
            Iterator<Entity> it = entities.values().iterator();
            while(it.hasNext())
            {
                it.next().start();
            }
            isRunning = true;
        }
        else //not first time
        {
            Iterator<Entity> it = entities.values().iterator();
            while(it.hasNext())
            {
                it.next().update();
            }
        }
    }
}
