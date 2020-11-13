/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor;

import Editor.Model.EntityModel;
import Editor.View.Menu.NewEntity;
import Editor.Controller.NewEntityController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author linuo
 */
public class NewEntityStage{

        public NewEntityStage(Stage parent)  {
        Stage newEntity = new Stage();
        newEntity.initOwner(parent);
        newEntity.initModality(Modality.NONE);
        
        
        NewEntity view = new NewEntity();
        NewEntityController nec = new NewEntityController(new EntityModel(), view);
        nec.setData();
        Scene scene = new Scene(view, 500, 300);
        newEntity.setTitle("Add New Entity");
        newEntity.setScene(scene);
        newEntity.show();
       
    }
}
