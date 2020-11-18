/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import Editor.View.Info;
import Editor.View.Grid.Grid;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
/**
 *
 * @author linuo
 */
public class InfoController {

    Info info = new Info();
    Grid grid = new Grid();
    String testStr = "testing testing testing";
    
    public InfoController(Info info) {
        
        info.save.setOnAction(e -> {
            try {
                save();
            } catch (IOException ex) {
                Logger.getLogger(GridController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        info.load.setOnAction(e ->{
            try {
                load();
            } catch (IOException ex) {
                Logger.getLogger(InfoController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InfoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    
    
    public void save() throws FileNotFoundException, IOException{
       
        FileOutputStream savefile = new FileOutputStream("savefile.sav");
        ObjectOutputStream savedObj = new ObjectOutputStream(savefile);
        savedObj.writeObject(testStr);
        savedObj.close();
        
        System.out.println("saved");
       
    }
    
    public void load() throws FileNotFoundException, IOException, ClassNotFoundException{
        
        FileInputStream savefile = new FileInputStream("savefile.sav");
        ObjectInputStream loadfile = new ObjectInputStream(savefile);
        Object obj = (String) loadfile.readObject();
        String str = obj.toString();
        System.out.println(str);
        loadfile.close();
    }
}
