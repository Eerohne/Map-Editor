package Engine.Level;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;

//Merouane Issad
public class Palette<T> {
    private Map<Integer, T> entries;
    
    public Palette()
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
