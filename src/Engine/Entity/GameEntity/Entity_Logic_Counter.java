/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import Engine.Entity.AbstractEntity.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Point2D;

/**
 *
 * @author child
 */
public class Entity_Logic_Counter extends Entity{
    
    private int minValue;
    private int maxValue;
    private int startingValue;
    private int value;
    
    private boolean canReset;
    private boolean canCount = true;
    
    public Entity_Logic_Counter(String name, Point2D position, int minValue, int maxValue, int startingValue, boolean canReset) {
        super(name, position);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.startingValue = startingValue;
        this.value = startingValue;
        this.canReset = canReset;
    }
    
    public Entity_Logic_Counter(HashMap<String, Object> propertyMap)
    {
        super(propertyMap);
        this.minValue = Integer.valueOf((String) propertyMap.get("minvalue"));
        this.maxValue = Integer.valueOf((String) propertyMap.get("maxvalue"));
        this.value = Integer.valueOf((String) propertyMap.get("startingvalue"));
        this.canReset = Boolean.valueOf((String) propertyMap.get("canreset"));
    }

    @Override
    public void start() {
    }

    @Override
    public void update() {
    }
    
    @Override
    public void handleSignal(String signalName, ArrayList<Object> arguments){
        switch(signalName)
        {
            case "increment":
                this.value++;
                verifyCount();
                System.out.println("value inside the counter is : "+this.value);
                System.out.println("the normal returned value is : "+(this.maxValue-this.value));
                this.fireSignal("OnValueChanged", (this.maxValue-this.value));
                break;
            case "decrement":
                this.value--;
                verifyCount();
                this.fireSignal("OnValueChanged", this.value-this.minValue);
                break;
            case "reset":
                this.value = startingValue;
                break;
            default:
                super.handleSignal(signalName, arguments);
                
        }
    }
    
    public void verifyCount()
    {
        if(canCount && (value >= maxValue | value <= minValue)){
            System.out.println("calling reached signal");
            if(canReset)
                reset();
            else
                canCount = false;
        }
    }
    
    public int getCount()
    {
        return this.value;
    }
    public void reset()
    {
        this.value = startingValue;
    }
    
}
