package Engine.Entity.AbstractEntity;

import Engine.Core.Exceptions.EntityCreationException;
import Engine.Core.Game;
import Engine.Entity.Signal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import javafx.geometry.Point2D;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

//Merouane Issad
public abstract class Entity implements IEntity{ //An entity is any object that moves, has behavior or affects the game in any way. By default a simple entity cannot be seen,
                                                 //meaning that it is not take in consideration by the renderer what so ever.
    
    protected String name; //all entities have a name, the name can never change and no entity can have the same name
    protected boolean active; //if false, this entity will not be updated
    protected Point2D position; //all entities have a position in the world
    protected float height;
    
    protected ArrayList<Signal> signals = new ArrayList<>();

    public Entity(String name, Point2D position) {
        this.name = name;
        this.active = true;
        this.position = position;
    }
    
    public Entity(String name, boolean active, Point2D position) {
        this.name = name;
        this.active = active;
        this.position = position;
    }
    
    public Entity(HashMap<String, Object> propertyMap)
    {
        this.name = (String)propertyMap.get("name");
        if(propertyMap.containsKey("active"))
            this.active = Boolean.parseBoolean((String)propertyMap.get("active"));
        else
            this.active = true;
        JSONArray posArray = (JSONArray) propertyMap.get("position");
        this.position = new Point2D((double) posArray.get(0),(double) posArray.get(1));
        
        if(propertyMap.containsKey("height"))
            this.height = Float.parseFloat((String) propertyMap.get("height"));
        else
            this.height = 0;
        
        JSONArray signalArr = (JSONArray) propertyMap.get("signals");
        if(signalArr != null)
        {
            if(signalArr.size() > 0){
                for(Object o : signalArr)
                {
                    JSONObject jsonEntry = (JSONObject) o;
                    String name = (String) jsonEntry.get("name");
                    String targetName = (String) jsonEntry.get("targetname");
                    String inputName = (String) jsonEntry.get("inputname");
                    Object[] argumentsArr = ((JSONArray) jsonEntry.get("arguments")).toArray();
                    ArrayList<Object> arguments = new ArrayList<>();
                    for(Object arg : argumentsArr){
                        arguments.add(arg);
                    }

                    Signal signal = new Signal(name, targetName, inputName, arguments);
                    this.addSignal(signal);
                }
            }
        }
    }
    
    @Override
    public void destroy() { //remove from the current level Entity list by name

        Game.getCurrentLevel().removeEntity(this.name); //temporary code
    }
    
    public void handleSignal(String signalName, ArrayList<Object> arguments){
        
        switch(signalName)
        {
            case "enable":
                this.active = true;
                break;
            case "disable":
                this.active = false;
                break;
            default:
                System.out.println("signal '"+signalName+"' was not found in entity '"+this.name+"'");
                
        }
    }
    
    public void fireSignal(String signalName, Object... extraArgs){
        for(Signal signal : signals)
        {
            if(signal.name.equals(signalName))
            {
                Entity targetEntity = Game.getCurrentLevel().getEntity(signal.targetName);
                if(targetEntity != null)
                {
                    ArrayList<Object> modifiedArgs = new ArrayList<>();
                    modifiedArgs.addAll(signal.arguments);
                    if(extraArgs.length >0){
                        for(Object arg : extraArgs){
                            modifiedArgs.add(arg);
                        }
                        modifiedArgs.addAll(Arrays.asList(extraArgs));
                    }
                    targetEntity.handleSignal(signal.inputname, modifiedArgs);
                }
            }
        }
    }
    
    public String getName() { //if you can't understand this one then I'm sorry...
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean getActive()
    {
        return this.active;
    }
    
    public void setActive(boolean active)
    {
        this.active = active;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }
    
    public float getHeight()
    {
        return this.height;
    }
    
    public void setHeight(float height)
    {
        this.height = height;
    }
    
    public void addSignal(Signal signal)
    {
        signals.add(signal);
    }
    
}
