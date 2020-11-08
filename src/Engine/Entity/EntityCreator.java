/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity;

import Engine.Core.Exceptions.EntityCreationException;
import Engine.Entity.AbstractEntity.*;
import Engine.Entity.GameEntity.*;
import java.util.HashMap;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

//Merouane Issad
public class EntityCreator { //Entity creation is defined in this class

    //Entity classname listing 
    public static Entity constructEntity(HashMap<String, String> propertyMap) //provide a map with all needed properties, outputs an entity
    {
        Entity entity = null;
        
        try {
            verifyProperties(propertyMap, "classname", "name", "active", "pos_x", "pos_y");
        }
        catch(EntityCreationException e) {
            System.out.println(e);
            return null;
        }
        
        //basic entity properties
        String classname = propertyMap.get("classname");
        String name = propertyMap.get("name");
        boolean active = Boolean.parseBoolean(propertyMap.get("active"));
        Point2D position = new Point2D(Float.parseFloat(propertyMap.get("pos_x")), Float.parseFloat(propertyMap.get("pos_y")));
        
        //entity classname list
        switch(classname) //add any new entity to this list and point to a create_Entity_... method
        {
            case "item_coin":
                entity = create_Entity_Item_Coin(propertyMap);
                break;
            case "player_base":
                entity = create_Entity_Player_Base(propertyMap);
                break;
            default:
                entity = null;
        }
        
        //set the name and position if classname was valid
        if(entity != null) 
        {
            entity.setName(name);
            entity.setActive(active);
            entity.setPosition(position);
        }
        return entity;
    }
    //verifies if all provided properties exist inside the map in case the entity data is incomplete or corrupted
    private static boolean verifyProperties(HashMap<String, String> propertyMap, String... properties) throws EntityCreationException
    {
        for(String property : properties)
            if(!propertyMap.containsKey(property)) {
                if(property.equals("classname"))
                    throw new EntityCreationException("Property '" + property + "' was not provided, entity type is unknown");
                else
                    throw new EntityCreationException("Missing requiered properties when trying to create " + propertyMap.get("classname"));
            }
        return true;
    }
    
    //Entity construction definitions below
    
    //PLAYER_
    private static Entity create_Entity_Player_Base(HashMap<String, String> propertyMap)
    {
        try {
            verifyProperties(propertyMap, "speed");
        }
        catch(EntityCreationException e) {
            System.out.println(e);
            return null;
        }
        
        //SpriteEntity
        Color color = new Color(Float.parseFloat(propertyMap.get("col_r")), Float.parseFloat(propertyMap.get("col_g")), Float.parseFloat(propertyMap.get("col_b")), Float.parseFloat(propertyMap.get("col_a")));
        
        //CoinEntity
        int score = Integer.parseInt(propertyMap.get("scorepoint"));

        return new Entity_Item_Coin(null, null, color, score);
    }
    
    //ITEM_
    private static Entity create_Entity_Item_Coin(HashMap<String, String> propertyMap)
    {
        try {
            verifyProperties(propertyMap, "col_r", "col_g", "col_b", "scorepoint");
        }
        catch(EntityCreationException e) {
            System.out.println(e);
            return null;
        }
        
        //SpriteEntity
        Color color = new Color(Float.parseFloat(propertyMap.get("col_r")), Float.parseFloat(propertyMap.get("col_g")), Float.parseFloat(propertyMap.get("col_b")), Float.parseFloat(propertyMap.get("col_a")));
        
        //CoinEntity
        int score = Integer.parseInt(propertyMap.get("scorepoint"));

        return new Entity_Item_Coin(null, null, color, score);
    }
}
