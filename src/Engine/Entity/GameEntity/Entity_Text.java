/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import Engine.Core.Game;
import Engine.Entity.AbstractEntity.Entity;
import Engine.Util.Time;
import java.util.ArrayList;
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
    private Label messagelabel = new Label("message");
    private Label timelabel = new Label("time : 1:00");
    private Label coinlabel = new Label("coin left : 2");
    
    private Pane p;
    
    public Entity_Text(HashMap<String, Object> propertyMap)
    {
        super(propertyMap);
    }
    
    @Override
    public void start() {
        //start logic here
        messagelabel.setWrapText(true);
        messagelabel.setMaxWidth(Game.getWindowManager().getWidth());
        messagelabel.setTextAlignment(TextAlignment.CENTER);
        messagelabel.setFont(Font.font("Cambria", 40));
        messagelabel.setStyle("-fx-text-fill: rgba(255, 0, 0, 255);");
        
        timelabel.setWrapText(true);
        timelabel.setMaxWidth(Game.getWindowManager().getWidth());
        timelabel.setTextAlignment(TextAlignment.CENTER);
        timelabel.setFont(Font.font("Cambria", 24));
        timelabel.setStyle("-fx-text-fill: rgba(255, 255, 255, 255);");
        
        coinlabel.setWrapText(true);
        coinlabel.setMaxWidth(Game.getWindowManager().getWidth());
        coinlabel.setTextAlignment(TextAlignment.CENTER);
        coinlabel.setFont(Font.font("Cambria", 24));
        coinlabel.setStyle("-fx-text-fill: rgba(255, 255, 255, 255);");
        
        HBox hb = new HBox();
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().add(messagelabel);
        
        p = new Pane();
        p.setStyle("-fx-background-color: rgba(0, 0, 0, 1.0);");
        p.setOpacity(0);
        
        Game.getWindowManager().getIngameDisplay().getChildren().addAll(timelabel, coinlabel, p, hb);
        
        //Game.getWindowManager().getIngameDisplay().set
        Game.getWindowManager().getIngameDisplay().setTopAnchor(hb, 0.0);
        Game.getWindowManager().getIngameDisplay().setBottomAnchor(hb, 0.0);
        Game.getWindowManager().getIngameDisplay().setRightAnchor(hb, 0.0);
        Game.getWindowManager().getIngameDisplay().setLeftAnchor(hb, 0.0);
        
        Game.getWindowManager().getIngameDisplay().setTopAnchor(p, 0.0);
        Game.getWindowManager().getIngameDisplay().setBottomAnchor(p, 0.0);
        Game.getWindowManager().getIngameDisplay().setRightAnchor(p, 0.0);
        Game.getWindowManager().getIngameDisplay().setLeftAnchor(p, 0.0);
        
        Game.getWindowManager().getIngameDisplay().setTopAnchor(timelabel, 0.0);
        Game.getWindowManager().getIngameDisplay().setLeftAnchor(timelabel, 0.0);
        
        Game.getWindowManager().getIngameDisplay().setTopAnchor(coinlabel, 0.0);
        Game.getWindowManager().getIngameDisplay().setRightAnchor(coinlabel, 0.0);
        
    }

    @Override
    public void update() {
        //update logic here
        //System.out.println(Time.timePassed);
        //p.setOpacity(Math.sin(Time.timePassed));
    }
    
    @Override
    public void handleSignal(String signalName, ArrayList<Object> arguments){
        switch(signalName) //new signals here
        {
            case "setCoinText":
                coinlabel.setText((String)arguments.get(0));
                coinlabel.setStyle((String)arguments.get(1));
                break;
            case "setTimeText":
                break;
            case "setmessageText":
                break;
            default:
                super.handleSignal(signalName, arguments);
                
        }
    }
}
