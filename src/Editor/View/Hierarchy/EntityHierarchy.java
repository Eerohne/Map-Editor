/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Hierarchy;

import Editor.Model.EntityModel;
import Editor.Model.Profile.EntityProfile;
import Editor.View.Metadata.DataView;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author A
 */
public class EntityHierarchy extends Hierarchy{
    private EntityProfile profile;
    
    public EntityHierarchy() {
        super(null);
    }

    @Override
    public void refresh() {
        //TODO
    }

    public void setEntityProfile(EntityProfile profile) {
        this.profile = profile;
    }
    
}