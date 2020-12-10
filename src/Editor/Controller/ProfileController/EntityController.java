/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller.ProfileController;

import Editor.Model.Profile.MapProfile;
import Editor.View.Metadata.EntityContent;

/**
 *
 * @author A
 */
public class EntityController extends MetadataController{
    MapProfile map;
    
    public EntityController(EntityContent content, MapProfile map) {
        super(content);
        this.map = map;
    }

    @Override
    protected void saveAction() {

    }

    @Override
    protected void deleteAction() {

    }
    
}
