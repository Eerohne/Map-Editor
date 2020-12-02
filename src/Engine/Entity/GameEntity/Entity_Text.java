/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import Engine.Core.Game;
import Engine.Entity.AbstractEntity.Entity;
import Engine.Util.Time;
import java.util.HashMap;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author child
 */
public class Entity_Text extends Entity{
    
    //variables here
    private String text;
    
    private Pane p;
    
    public Entity_Text(String name, Point2D position, String text)
    {
        super(name, position);
        this.text = text;
    }
    
    public Entity_Text(HashMap<String, Object> propertyMap)
    {
        super(propertyMap);
        
        //parse property map here
        this.text = (String) propertyMap.get("text");
    }
    
    @Override
    public void start() {
        //start logic here
        Label label = new Label(this.text);
        label.setFont(Font.font("Cambria", 64));
        //label.setTextAlignment(TextAlignment.CENTER);
        HBox hb = new HBox();
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().add(label);
        
        p = new Pane();
        //p.setStyle("-fx-background-color: rgba(0, 0, 0, 1.0);");
        Game.getWindowManager().getIngameDisplay().getChildren().addAll(hb, p);
        
        //Game.getWindowManager().getIngameDisplay().set
        Game.getWindowManager().getIngameDisplay().setTopAnchor(hb, 0.0);
        Game.getWindowManager().getIngameDisplay().setBottomAnchor(hb, 0.0);
        Game.getWindowManager().getIngameDisplay().setRightAnchor(hb, 0.0);
        Game.getWindowManager().getIngameDisplay().setLeftAnchor(hb, 0.0);
        
        Game.getWindowManager().getIngameDisplay().setTopAnchor(p, 0.0);
        Game.getWindowManager().getIngameDisplay().setBottomAnchor(p, 0.0);
        Game.getWindowManager().getIngameDisplay().setRightAnchor(p, 0.0);
        Game.getWindowManager().getIngameDisplay().setLeftAnchor(p, 0.0);
        
    }

    @Override
    public void update() {
        //update logic here
        //System.out.println(Time.timePassed);
        p.setOpacity(Math.sin(Time.timePassed));
    }
    
    @Override
    public void handleSignal(String signalName, Object[] arguments){
        switch(signalName) //new signals here
        {
            case "signalexmaple":
                //signal code here
                break;
            default:
                super.handleSignal(signalName, arguments);
                
        }
    }
}
