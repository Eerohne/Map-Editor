/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View;

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
    private Label mouseX;
    private Label mouseY;
    private Label zoom;
    private Label mode;
    private Label color;
    private Rectangle colorViewer;
    
    private Button save;

    public Info() {
        mouseX = new Label("X : 0.00");
        mouseY = new Label("Y : 0.00");
        
        zoom = new Label("Zoom : 100%");
        mode = new Label("Current Mode : Place Wall");
        color = new Label("Selected Color : ");
        colorViewer = new Rectangle(20, 20);
        colorViewer.setFill(Color.BLACK);
        colorViewer.setStroke(Color.BLACK);
        
        save = new Button("Save");
        
        Region spacing = new Region();
        HBox.setHgrow(spacing, Priority.ALWAYS);
        
        this.getChildren().addAll(mouseX, mouseY, zoom, mode, color, colorViewer, spacing, save);
        this.setSpacing(10);
        
        Insets insets = new Insets(5);
        this.setPadding(insets);
    }
}
