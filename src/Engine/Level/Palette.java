/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Level;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;

//Merouane Issad

public class Palette<T> {
    Map<Integer, T> entries;
    
    Palette()
    {
        entries = new HashMap<Integer, T>();
    }
    
    
    public T getEntry(int index)
    {
        return entries.get(index);
    }
    public void setEntry(int index, T data)
    {
        entries.put(index, data);
    }
    
}
