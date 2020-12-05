/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View;

import Editor.Controller.GridController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author A
 */
public class Info extends HBox{
    private Label mouseXLabel;
    private Label mouseYLabel;
    private Label zoomLabel;
    private Label modeLabel;
    private Label modeInfoLabel;
    private Rectangle colorViewer;
    
    public Button save;
    public Button load;
    
    private double mouseX;
    private double mouseY;
    private double zoom;
    
    public Info() {
    }
    
    public void setupInfoBar(GridController gc){
        this.mouseX = gc.getMouseX();
        this.mouseY = gc.getMouseY();
        this.zoom = gc.getZoom();
        
        mouseXLabel = new Label("X : " + String.format("%.2f", mouseX) + "");
        mouseYLabel = new Label("Y : " + String.format("%.2f", mouseY) + "");
        
        zoomLabel = new Label("Zoom : " + String.format("%.0f", zoom*100) + "%");
        modeLabel = new Label("Current Mode : Place Wall");
        modeInfoLabel = new Label("Selected Color : ");
        colorViewer = new Rectangle(20, 20);
        colorViewer.setFill(Color.BLACK);
        colorViewer.setStroke(Color.BLACK);
        
        save = new Button("Save");
        load = new Button("Load");
        
        Region spacing = new Region();
        HBox.setHgrow(spacing, Priority.ALWAYS);
        
        this.getChildren().addAll(mouseXLabel, mouseYLabel, zoomLabel, modeLabel, modeInfoLabel, colorViewer, spacing, save, load);
        this.setSpacing(10);
        
        Insets insets = new Insets(10);
        this.setPadding(insets);
    }

    public double getMouseX() {
        return mouseX;
    }

    public void setMouseX(double mouseX) {
        this.mouseX = mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public void setMouseY(double mouseY) {
        this.mouseY = mouseY;
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    public void reset() {
        this.mouseXLabel.setText("X : " + String.format("%.2f", mouseX) + "");
        this.mouseYLabel.setText("Y : " + String.format("%.2f", mouseY) + "");
        this.zoomLabel.setText("Zoom : " + String.format("%.0f", zoom*100) + "%");
    }
}
