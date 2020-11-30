/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commons.SettingsManager;

import Engine.Util.RessourceManager.ResourceLoader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 *
 * @author child
 */
public class Settings {
    
    private static OrderedProperties properties;
    
    /**
     * Loads config.cfg
     */
    public static void init()
    {
        properties = ResourceLoader.loadConfigFile();
    }
    
    /**
     * 
     * @param name Name of the Key
     * @return String Value of the name property
     */
    public static String get(String name)
    {
        return properties.getProperty(name);
    }
    
    /**
     * 
     * @param name Name of the Key
     * @return Integer Value of the name property
     */
    public static int getInt(String name)
    {
        return Integer.valueOf(properties.getProperty(name));
    }
    
    /**
     * 
     * @param name Name of the Key
     * @return Float Value of the name property
     */
    public static float getFloat(String name)
    {
        return Float.valueOf(properties.getProperty(name));
    }
    
    /**
     * 
     * @param name Name of the Key
     * @return Double Value of the name property
     */
    public static double getDouble(String name)
    {
        return Double.valueOf(properties.getProperty(name));
    }
    
    /**
     * 
     * @param name Name of the Key
     * @return Boolean Value of the name property
     */
    public static boolean getBoolean(String name)
    {
        return Boolean.valueOf(properties.getProperty(name));
    }
    
    /**
     * Stores the given value under the given key
     * @param key
     * @param value 
     */
    public static void save(String key, Object value)
    {
        try {
            properties.setProperty(key, String.valueOf(value));
            OutputStream out = new FileOutputStream("config.cfg"); //the config file is always in the engine root folder
            properties.save(out, "e->engine, r->renderer, snd->sound, ed->editor");
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }
}
