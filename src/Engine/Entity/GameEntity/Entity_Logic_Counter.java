/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import Engine.Entity.AbstractEntity.Entity;
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
    
    public Entity_Logic_Counter(HashMap<String, String> propertyMap)
    {
        super(propertyMap);
        this.minValue = Integer.valueOf(propertyMap.get("minvalue"));
        this.maxValue = Integer.valueOf(propertyMap.get("maxvalue"));
        this.value = Integer.valueOf(propertyMap.get("startingvalue"));
        this.canReset = Boolean.valueOf(propertyMap.get("canreset"));
    }

    @Override
    public void start() {
    }

    @Override
    public void update() {
    }
    
    @Override
    public void trigger(Object... properties)
    {
        if(properties == null)
            this.value += 1;
        else
            this.value += (int) properties[0];
        
        if(this.canCount == true){
            if(this.value >= maxValue | this.value <= minValue){
                System.out.println("counter triggered");
                if(this.canReset == true){
                    this.value = this.startingValue;
                }
                else
                    this.canCount = false;
            }
        }
    }
    
}
