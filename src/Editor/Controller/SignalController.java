/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;
import Editor.Controller.NewEntityController;
import Editor.Model.EntityModel;
import Editor.Model.SignalModel;
import Editor.View.Menu.Entity.NewEntity;
import Editor.View.Menu.Entity.SignalView;
import org.json.simple.JSONObject;

/**
 *
 * @author linuo
 */
public class SignalController {
    SignalModel model = new SignalModel();
    SignalView view = new SignalView();
    JSONObject signal = new JSONObject();
    NewEntityController nec = new NewEntityController(signal);
    
    String name;
    String targetname;
    String inputname;
    String [] arguments;
    //SignalModel signal = new SignalModel(name, targetname, inputname, arguments);

    public SignalController(SignalModel model, SignalView view) {
        this.model = model;
        this.view = view;
        saveSignal();
        clear();
    }
    
    public SignalController(JSONObject signal){
        this.signal = signal;
    }
    
    
    // save the signal to a file, then try to retrieve it from entity creation class
    public JSONObject saveSignal(){
        view.saveSignal.setOnAction((event) -> {
            model.setName(view.nameTf.getText());
            model.setTargetname(view.targetNameTf.getText());
            model.setInputname(view.inputNameTf.getText());
            model.setArguments(view.arguementTf.getText().split(","));
            
            signal.put("signal", model);
            nec.setSignalObj(signal);
            System.out.println(signal);
        });
        return signal;
    }
    
    public void clear(){
        view.clearSignal.setOnAction((event) -> {
            System.out.println(signal);
        });
    }

    public JSONObject getSignal() {
        return signal;
    }
    
    
    
    
}
