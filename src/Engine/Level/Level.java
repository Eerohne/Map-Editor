/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Level;

import Engine.Entity.AbstractEntity.Entity;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author child
 */
public class Level {
    private int[][] levelData;
    private Palette palette;
    private Map<String, Entity> entities;
    
    Level()
    {
        entities = new HashMap<String, Entity>();
    }
    
    public int getCellValue(int x, int y)
    {
        return levelData[x][y];
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
}
