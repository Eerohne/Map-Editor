/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Metadata;

import Editor.Model.Profile.Profile;
import javafx.scene.layout.VBox;

/**
 *
 * @author A
 */
public class EntityContent extends DataView{

    public EntityContent(Profile profile) {
        super(profile);
    }

    @Override
    protected VBox setupPane() {
        return null;
    }

    @Override
    public void reset() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
