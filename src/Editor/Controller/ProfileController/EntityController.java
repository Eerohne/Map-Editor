/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller.ProfileController;

import Editor.Model.Profile.MapProfile;
import Editor.View.Grid.Grid;
import Editor.View.Menu.Entity.ExistingEntityStage;
import Editor.View.Metadata.EntityContent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

/**
 *
 * @author A
 */
public class EntityController extends MetadataController{
    MapProfile map;
    Grid grid;
    Stage stage;
    
    public EntityController(EntityContent content, MapProfile map, Stage stage) {
        super(content);
        this.map = map;
        this.grid = map.getGridView();
        this.stage = stage;
        
        ((EntityContent)content).getOpenEditingButton().setOnAction(e -> {
            try {
                new ExistingEntityStage(stage);
            } catch (IOException ex) {
                Logger.getLogger(EntityController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(EntityController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

     
    //Actually this is the duplication event
    @Override
    protected void saveAction() {
        
    }

    @Override
    protected void deleteAction() {

    }
    
}
