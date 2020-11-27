/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Core.SettingsManager;

import Engine.Util.RessourceManager.ResourceLoader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author child
 */
public class Settings {
    
    private static OrderedProperties properties;
    
    public static void init()
    {
        properties = ResourceLoader.loadConfigFile();
    }
    
    public static String get(String name)
    {
        return properties.getProperty(name);
    }
    
    public static int getInt(String name)
    {
        return Integer.valueOf(properties.getProperty(name));
    }
    
    public static float getFloat(String name)
    {
        return Float.valueOf(properties.getProperty(name));
    }
    
    public static double getDouble(String name)
    {
        return Double.valueOf(properties.getProperty(name));
    }
    
    public static boolean getBoolean(String name)
    {
        return Boolean.valueOf(properties.getProperty(name));
    }
    
    public static void save(String key, Object value)
    {
        try {
            properties.setProperty(key, String.valueOf(value));
            OutputStream out = new FileOutputStream("config.cfg"); //the config file is always in the engine root folder
            properties.save(out, "e->engine, r->renderer, snd->sound");
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }
}
