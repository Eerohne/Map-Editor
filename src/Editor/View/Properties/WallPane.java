/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Properties;

import Editor.Controller.WallController;
import Editor.Model.WallProfile;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author A
 */
public class WallPane extends TitledPane{
    //Labels Initialization
    private Label nameLbl = new Label("Name : ");
    private Label textureLbl = new Label("Texture : ");
    private Label flagLbl = new Label("Flag : ");
    
    //Interaction Elements Initialization
    private TextField nameField;
    private Rectangle txrPreview;
    private ComboBox flagCombo;

    ObservableList<String> flags = FXCollections.observableArrayList(Arrays.asList(WallProfile.flagArray));//Observable List of Flags
    
    //Buttons Initialization
    private Button select;
    private Button cancel;
    private Button save;
    private Button delete;
    
    //WallProfile Representation
    private WallProfile wallProfile;
    
    public WallPane(WallProfile wallProfile) {
        this.wallProfile = wallProfile;
        
        //Interactions 
        this.nameField = new TextField(wallProfile.getName());
        this.txrPreview = new Rectangle(30, 30);
        this.flagCombo = new ComboBox(flags);
        this.reset();
        
        //Buttons
        this.select = new Button("Select");
        this.cancel = new Button("Cancel");
        this.save = new Button("Save Changes");
        this.delete = new Button("Delete Profile");
        
        save.setDisable(true);
        cancel.setDisable(true);
        
        super.setText(wallProfile.getName());
        super.setContent(this.setupPane());
    }
    
    //Method that sets up the pane in a vertical Box
    private VBox setupPane(){
        Insets padding = new Insets(10); // Padding
        Region space = new Region(); //GUI Gap

        
        //Initialize the Name Section
        HBox nameBox = new HBox(nameLbl, nameField);
        nameBox.setPadding(padding);
        nameBox.setSpacing(10);
        
        //Initialize the Texture Section
        HBox txrBox = new HBox(textureLbl, txrPreview);
        txrBox.setPadding(padding);
        txrBox.setSpacing(10);
        
        //Initialize the Flag Section
        HBox flagBox = new HBox(flagLbl, flagCombo);
        flagBox.setPadding(padding);
        flagBox.setSpacing(10);
        
        //Initialize the Button Section
        HBox.setHgrow(space, Priority.ALWAYS);
        VBox.setVgrow(space, Priority.ALWAYS);
        HBox buttons = new HBox(save, cancel, space, select, delete);
        buttons.setPadding(padding);
        buttons.setSpacing(5);
        
        return new VBox(nameBox, txrBox, flagBox, space, buttons);
    }

    public final void reset(){
        this.nameField.setText(wallProfile.getName());
        this.txrPreview.setFill(new ImagePattern(wallProfile.getImage()));
        this.flagCombo.getSelectionModel().select(wallProfile.getFlag());
    }
    
    /*
    Getters And Setters
    */
    
    public Label getNameLbl() {
        return nameLbl;
    }

    public void setNameLbl(Label nameLbl) {
        this.nameLbl = nameLbl;
    }

    public Label getTextureLbl() {
        return textureLbl;
    }

    public void setTextureLbl(Label textureLbl) {
        this.textureLbl = textureLbl;
    }

    public Label getFlagLbl() {
        return flagLbl;
    }

    public void setFlagLbl(Label flagLbl) {
        this.flagLbl = flagLbl;
    }

    public TextField getNameField() {
        return nameField;
    }

    public void setNameField(TextField nameField) {
        this.nameField = nameField;
    }

    public Rectangle getTxrPreview() {
        return txrPreview;
    }

    public void setTxrPreview(Rectangle txrPreview) {
        this.txrPreview = txrPreview;
    }

    public ComboBox getFlagCombo() {
        return flagCombo;
    }

    public void setFlagCombo(ComboBox flagCombo) {
        this.flagCombo = flagCombo;
    }

    public ObservableList<String> getFlags() {
        return flags;
    }

    public void setFlags(ObservableList<String> flags) {
        this.flags = flags;
    }

    public Button getSelect() {
        return select;
    }

    public void setSelect(Button select) {
        this.select = select;
    }

    public Button getCancel() {
        return cancel;
    }

    public void setCancel(Button cancel) {
        this.cancel = cancel;
    }

    public Button getSave() {
        return save;
    }

    public void setSave(Button save) {
        this.save = save;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }

    public WallProfile getWallProfile() {
        return wallProfile;
    }

    public void setWallProfile(WallProfile wallProfile) {
        this.wallProfile = wallProfile;
    }
}
