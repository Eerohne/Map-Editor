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
    public static Entity constructEntity(HashMap<String, Object> propertyMap) //provide a map with all needed properties, outputs an entity
    {
        Entity entity = null;
        
        if(!verifyProperties(propertyMap, "classname", "name", "active", "position"))
            return null;
        String classname = (String)propertyMap.get("classname");
        
        try{
            //entity classname list
            switch(classname) //add any new entity to this list and point to a create_Entity_... method
            {
                case "classname_of_entity_all_lowercase": //example of how to setup a new entity
                    //if(verifyProperties(propertyMap, "rotation", "walkspeed", "runspeed"))
                        //entity = new Entity_Name_Of_Entity_Class(propertyMap);
                    break;
                case "environment":
                    if(verifyProperties(propertyMap, "foggy", "fogcolor", "fog_near_distance", "fog_far_distance", "has_sky", "skycolor", "wallheight"))
                        entity = new Entity_Environment(propertyMap);
                    break;
                case "game_leveload":
                    if(verifyProperties(propertyMap, "levelpath"))
                        entity = new Entity_Game_Levelload(propertyMap);
                    break;
                case "logic_counter":
                    if(verifyProperties(propertyMap, "minvalue", "maxvalue", "startingvalue"))
                        entity = new Entity_Logic_Counter(propertyMap);
                    break;
                 case "logic_gamemanager":
                    if(verifyProperties(propertyMap, "losemessage", "winmessage"))
                        entity = new Entity_Logic_GameManager(propertyMap);
                    break;
                case "logic_messenger":
                    entity = new Entity_Logic_Messenger(propertyMap);
                    break;
                case "logic_start":
                    entity = new Entity_Logic_Start(propertyMap);
                    break;
                case "logic_timer":
                    if(verifyProperties(propertyMap, "maxtime"))
                        entity = new Entity_Logic_Timer(propertyMap);
                    break;
                case "player_simple":
                    if(verifyProperties(propertyMap, "rotation", "walkspeed", "runspeed"))
                        entity = new Entity_Player_Simple(propertyMap);
                    break;
                case "player_idle":
                    if(verifyProperties(propertyMap, "rotation", "rotationspeed"))
                        entity = new Entity_Player_Idle(propertyMap);
                    break;
                case "sound_ambient":
                    if(verifyProperties(propertyMap, "audiopath", "channel", "volume", "onstart", "loop", "onlyonce"))
                        entity = new Entity_Sound_Ambient(propertyMap);
                    break;
                case "sound_point":
                    if(verifyProperties(propertyMap, "audiopath", "channel", "volume", "onstart", "loop", "onlyonce", "range"))
                        entity = new Entity_Sound_Point(propertyMap);
                    break;
                 case "object_collectible":
                    if(verifyProperties(propertyMap, "texture", "size", "radius"))
                        entity = new Entity_Object_Collectible(propertyMap);
                    break;
                case "object_decor":
                    if(verifyProperties(propertyMap, "texture", "size", "radius"))
                        entity = new Entity_Object_Decor(propertyMap);
                    break;
                case "object_trigger":
                    if(verifyProperties(propertyMap, "radius"))
                        entity = new Entity_Object_Trigger(propertyMap);
                    break;
                case "ui_text":
                    if(verifyProperties(propertyMap, "text"))
                        entity = new Entity_Text(propertyMap);
                    break;
                default:
                    System.out.println(new EntityCreationException("classname '"+classname+"' is not defined"));
                    entity = null;
            }
        }
        catch(EntityCreationException e)
        {
            System.out.println(e);
            return null;
        }
        return entity;
    }
    
    
    //verifies if all provided properties exist inside the map in case the entity data is incomplete or corrupted
    private static boolean verifyProperties(HashMap<String, Object> propertyMap, String... properties)
    {
        try {
            for(String property : properties)
            if(!propertyMap.containsKey(property)) {
                if(property.equals("classname"))
                    throw new EntityCreationException("Property '" + property + "' was not provided, entity type is unknown");
                else
                    throw new EntityCreationException("Missing requiered propertiy '"+property+"' when trying to create '" + propertyMap.get("classname")+"'");
            }
        }
        catch(EntityCreationException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
}
