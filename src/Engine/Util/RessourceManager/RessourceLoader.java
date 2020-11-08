/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Util.RessourceManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import javafx.scene.image.Image;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sun.audio.AudioStream;
 
public class RessourceLoader
{
    public static Image loadImage(String path)
    {
        try{
        FileInputStream inputstream = new FileInputStream("ressources/"+path);
        return new Image(inputstream);
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
        return null;
    }
    
    public static AudioStream loadAudio(String path)
    {
        try{
        FileInputStream inputstream = new FileInputStream("ressources/"+path);
        return new AudioStream(inputstream);
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
        return null;
    }
    
    public static void main( String[] args )
    {
        
    }
}
