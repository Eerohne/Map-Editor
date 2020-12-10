/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import Engine.Core.Game;
import Engine.Core.Sound.SoundManager;
import Engine.Entity.AbstractEntity.Entity;
import Engine.Util.RessourceManager.ResourceLoader;
import static Engine.Util.RessourceManager.ResourceLoader.resourcePath;
import Engine.Util.Time;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author child
 */
public class Entity_Text extends Entity{
    
    //variables here
    private Label messagelabel = new Label("a");
    private Label timelabel = new Label("b");
    private Label coinlabel = new Label("c");
    
    private Pane p;
    
    private boolean writingMessage = false;
    private String messageText = "";
    private String labelText = "";
    private int charCounter = 0;
    private float rate = 0.01f;
    private float timeCounter = 0;
    private float textFadeTime = 4;
    
    private MediaPlayer mediaPlayer;
    private static String dialoguePath = "sounds/fx/dialogue.wav";
    public static Media soundMedia;
    
    public Entity_Text(HashMap<String, Object> propertyMap)
    {
        super(propertyMap);
        
        //dialoguePath = (String) propertyMap.get("dialogueSoundPath");
        soundMedia= ResourceLoader.loadAudio(dialoguePath);
        mediaPlayer = SoundManager.createPlayer(soundMedia, "game", true, false);
    }
    
    @Override
    public void start() {
        //start logic here
        messagelabel.setWrapText(true);
        messagelabel.setMaxWidth(Game.getWindowManager().getWidth());
        messagelabel.setTextAlignment(TextAlignment.CENTER);
        messagelabel.setFont(Font.font("Cambria", 40));
        //messagelabel.setStyle("-fx-text-fill: rgba(255, 0, 0, 255);");
        
        timelabel.setWrapText(true);
        timelabel.setMaxWidth(Game.getWindowManager().getWidth());
        timelabel.setTextAlignment(TextAlignment.CENTER);
        timelabel.setFont(Font.font("Cambria", 30));
        //timelabel.setStyle("-fx-text-fill: rgba(255, 255, 255, 255);");
        
        coinlabel.setWrapText(true);
        coinlabel.setMaxWidth(Game.getWindowManager().getWidth());
        coinlabel.setTextAlignment(TextAlignment.CENTER);
        coinlabel.setFont(Font.font("Cambria", 30));
        //coinlabel.setStyle("-fx-text-fill: rgba(255, 255, 255, 255);");
        
        HBox hb = new HBox();
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().add(messagelabel);
        
        p = new Pane();
        p.setStyle("-fx-background-color: rgba(0, 0, 0, 1.0);");
        p.setOpacity(0);
        
        /*Media video = new Media(new File("c:/Users/child/Desktop/srender/render3.mp4").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(video); 
        MediaView mediaView = new MediaView(mediaPlayer);  
        mediaView.fitWidthProperty().set(Game.getWindowManager().getWidth());
        mediaView.fitHeightProperty().set(Game.getWindowManager().getHeight());
        mediaView.setPreserveRatio(false);
        mediaPlayer.setAutoPlay(true);*/
        
        Game.getWindowManager().getIngameDisplay().getChildren().addAll(timelabel, coinlabel, p, hb);
        
        //Game.getWindowManager().getIngameDisplay().set
        //Game.getWindowManager().getIngameDisplay().setTopAnchor(hb, 0.0);
        Game.getWindowManager().getIngameDisplay().setBottomAnchor(hb, 200.0);
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
        
        //splash screen
        /*Game.getWindowManager().getIngameDisplay().setTopAnchor(mediaView, 0.0);
        Game.getWindowManager().getIngameDisplay().setBottomAnchor(mediaView, 0.0);
        Game.getWindowManager().getIngameDisplay().setRightAnchor(mediaView, 0.0);
        Game.getWindowManager().getIngameDisplay().setLeftAnchor(mediaView, 0.0);*/
        
    }

    @Override
    public void update() {
        //System.out.println("update");
        if(this.writingMessage)
        {
            this.timeCounter+= Time.deltaTime;
            if((timeCounter) >= (rate))
            {
                labelText += messageText.charAt(charCounter);
                messagelabel.setText(labelText);
                this.charCounter++;
                this.timeCounter = 0;
                
                if(this.charCounter >= this.messageText.length())
                {
                    mediaPlayer.pause();
                    this.writingMessage = false;
                }
            }
        }
        else if(!labelText.equals(""))
        {
            this.timeCounter+= Time.deltaTime;
            if(this.timeCounter >= textFadeTime){
                labelText="";
                messagelabel.setText("");
                this.timeCounter = 0;
            }
        }
    }
    
    private void writeMessage(String message, float rate)
    {
        mediaPlayer.play();
        this.labelText = "";
        this.messageText = message;
        this.writingMessage = true;
        this.charCounter = 0;
        this.rate = rate;
        
    }
    
    @Override
    public void handleSignal(String signalName, ArrayList<Object> arguments){
        switch(signalName) //new signals here
        {
            case "setCoinText":
                coinlabel.setText((String)arguments.get(0));
                if(arguments.size() >1)
                    coinlabel.setStyle((String)arguments.get(1));
                else
                    coinlabel.setStyle("-fx-text-fill: rgba(255, 255, 255, 255);");
                break;
            case "setTimeText":
                timelabel.setText((String)arguments.get(0));
                if(arguments.size() >1)
                    timelabel.setStyle((String)arguments.get(1));
                else
                    timelabel.setStyle("-fx-text-fill: rgba(255, 255, 255, 255);");
                break;
            case "setmessageText":
                //messagelabel.setText((String)arguments.get(0));
                System.out.println("signal sent");
                if(arguments.size() >1)
                    messagelabel.setStyle((String)arguments.get(1));
                else
                    messagelabel.setStyle("-fx-text-fill: rgba(255, 255, 255, 255);");
                writeMessage((String)arguments.get(0), 0.005f);
                break;
            default:
                super.handleSignal(signalName, arguments);
                
        }
    }
}
