/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Hierarchy;

import Editor.Main.MapEditor;
import Editor.View.Inspector.InspectorView;
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
    protected InspectorView content;
    protected VBox list;
    protected static HBox selected = new HBox();
    
    public Hierarchy(InspectorView content) {
        this.content = content;
        
        list = new VBox(10);
        list.setMinWidth(427);
        
        this.setContent(list);
    }
    
    public abstract void refresh();
    
    public void select(HBox box){
        selected.setBackground(Background.EMPTY);
        selected = box;
        selected.setBackground(new Background(new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
//        MapEditor.project.getSelectedMap().getGc().setEditingMode(mode);
        //System.out.println(mode);
    }
}
