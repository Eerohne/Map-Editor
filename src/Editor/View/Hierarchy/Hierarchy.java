/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Hierarchy;

import Editor.View.Metadata.DataView;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author A
 */
public abstract class Hierarchy extends ScrollPane{
    protected DataView content;
    protected VBox list;
    protected static HBox selected = new HBox();

    public Hierarchy(DataView content) {
        this.content = content;
        
        list = new VBox(10);
        list.setMinWidth(427);
        
        this.setContent(list);
    }
    
    public abstract void refresh();
    
    public void select(HBox box){
        this.selected.setBackground(Background.EMPTY);
        this.selected = box;
        this.selected.setBackground(new Background(new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
