/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View;

import Editor.Controller.GridController;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Duration;

/**
 *
 * @author A
 */
public class Info extends HBox{
    private GridController gc;
    
    private Label coordinateLabel;
    private Label zoomLabel;
    private Label modeLabel;
    private Label hoverEntityLabel;
    
    public Button save;
    public Button load;
    
    private double gridX;
    private double gridY;
    private double zoom;
    private int editingMode;
    private String hoverEntity;
    
    private AnimationTimer eventLoop;
    
    public Info() {
        this.coordinateLabel = new Label();
        this.zoomLabel = new Label();
        this.modeLabel = new Label();
        this.hoverEntityLabel = new Label();
    }
    
    public void setupInfoBar(GridController gc){
        this.gc = gc;
        
        this.refresh();
        
        save = new Button("Save");
        load = new Button("Load");
        
        Region spacing = new Region();
        HBox.setHgrow(spacing, Priority.ALWAYS);
        
        this.getChildren().addAll(coordinateLabel, zoomLabel, modeLabel, hoverEntityLabel, spacing, save, load);
        this.setSpacing(10);
        
        Insets insets = new Insets(10);
        this.setPadding(insets);
    }

    public void start(){
        eventLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                refresh();
            }
        };
        
        eventLoop.start();
    }
    
    public double getGridX() {
        return gridX;
    }

    public void setGridX(double mouseX) {
        this.gridX = mouseX;
    }

    public double getGridY() {
        return gridY;
    }

    public void setGridY(double mouseY) {
        this.gridY = mouseY;
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    public void refresh() {
        this.gridX = gc.getGridX();
        this.gridY = gc.getGridY();
        this.zoom = gc.getZoom();
        this.editingMode = gc.getEditingMode();
        this.hoverEntity = gc.getHoverEntity();
        
        this.coordinateLabel.setText("Coordinates : (" + String.format("%.2f", gridX) + ", " + String.format("%.2f", gridY) + ") ");
        this.zoomLabel.setText("| Zoom : " + String.format("%.0f", zoom*100) + "% | ");
        this.hoverEntityLabel.setText("| Hovered Entity : " + hoverEntity + " ");
        
        switch(editingMode){
            case 0:
                modeLabel.setText("Editing Mode : Editing Disabled ");
                break;
            case 1:
                modeLabel.setText("Editing Mode : Wall Editing ");
                break;
            case 2: 
                modeLabel.setText("Editing Mode : Entity Editing ");
                break;
            default:
                modeLabel.setText("Editing Mode : Error ");
                break;
        }
    }
    
    public void reload(GridController gc){
        eventLoop.stop();
        this.gridX = gc.getGridX();
        this.gridY = gc.getGridY();
        this.zoom = gc.getZoom();
        this.editingMode = gc.getEditingMode();
        
        eventLoop.start();
    }
}
