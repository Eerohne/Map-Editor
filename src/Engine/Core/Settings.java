/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Core;

import Engine.Util.RessourceManager.ResourceLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author child
 */
public class Settings {
    
    private static Properties properties;
    
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
}
