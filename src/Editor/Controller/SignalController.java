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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author linuo
 */
public class SignalController {
    SignalModel model = new SignalModel();
    SignalView view = new SignalView();
    JSONObject signal = new JSONObject();
    NewEntityController nec = new NewEntityController(signal);
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
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
    
    public void saveSignal(){
        view.saveSignal.setOnAction((event) -> {
            JSONArray signalArray = new JSONArray();
            JSONParser parser = new JSONParser();
            try {
                FileReader reader =  new FileReader("signals.json");
                File file = new File("signals.json");
                
                
                if(file.length() == 0){
                    model.setName(view.nameTf.getText());
                    model.setTargetname(view.targetNameTf.getText());
                    model.setInputname(view.inputNameTf.getText());
                    model.setArguments(view.arguementTf.getText().split(","));

                    signalArray.add(model);
                    FileWriter writer = new FileWriter("signals.json", true);
                    gson.toJson(signalArray, writer);
                    writer.close();
                }
                else{
                    JSONArray existingSignals = (JSONArray) parser.parse(reader);
                    
                    FileWriter writer1 = new FileWriter("signals.json");
                    model.setName(view.nameTf.getText());
                    model.setTargetname(view.targetNameTf.getText());
                    model.setInputname(view.inputNameTf.getText());
                    model.setArguments(view.arguementTf.getText().split(","));

                    signalArray.add(model);
                    existingSignals.addAll(signalArray);
                    FileWriter writer = new FileWriter("signals.json");
                    gson.toJson(existingSignals, writer);
                    writer.close();   
                }
                
            } catch (IOException ex) {
                Logger.getLogger(SignalController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(SignalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    public void clear(){
        view.clearSignal.setOnAction((event) -> {
            view.nameTf.clear();
            view.targetNameTf.clear();
            view.arguementTf.clear();
            view.inputNameTf.clear();
        });
    }
    
    
    
}
