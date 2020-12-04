/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;

import Editor.Model.EntityModel;
import Editor.Model.SignalModel;
import Editor.View.Menu.Entity.SignalViewer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author linuo
 */
public class SignalViewerController {
    
    SignalViewer viewer = new SignalViewer();
    SignalModel model = new SignalModel();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public SignalViewerController(SignalViewer viewer, SignalModel model) throws ParseException {
        this.viewer = viewer;
        this.model = model;
        
        allSignals();
        comboBoxHandler();
        clear();
        saveEditedSignal();
    }
    
    private void allSignals() throws ParseException{
        JSONParser parser = new JSONParser();
        
        try(FileReader reader = new FileReader("signals.json")){
            
            JSONArray entitiesArray = (JSONArray) parser.parse(reader);
            for(int i = 0; i < entitiesArray.size(); i++){
                
                viewer.cb.getItems().add(i);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void comboBoxHandler(){
        viewer.cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                JSONParser parser = new JSONParser();
                try(FileReader reader = new FileReader("signals.json")){
            
                    JSONArray signalsArray = (JSONArray) parser.parse(reader);
                    
                    for(int i = 0; i < signalsArray.size(); i++){
                        JSONObject signal = (JSONObject) signalsArray.get(i);
                        
                        if(newValue.equals(i)){
                            viewer.nameTf.setText((String) signal.get("name"));
                            viewer.targetNameTf.setText((String) signal.get("targetname" ));
                            viewer.inputNameTf.setText((String) signal.get("inputname"));
                            List list = new ArrayList();
                            JSONArray tempArr = (JSONArray) signal.get("arguments");
                            
                            String [] arguments = new String[tempArr.size()];
                            for(int j = 0; j < tempArr.size(); j++){
                                
                                list.add(tempArr.get(j));
                                arguments [j] = (String) list.get(j);
                            }
                            System.out.println(Arrays.toString(arguments));
                            String str = Arrays.toString(arguments);
                            String argumentStr = str.substring(1, str.length() - 1);
                            viewer.arguementTf.setText(argumentStr);
                           
                        }
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SignalViewerController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(SignalViewerController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(SignalViewerController.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        });
    }
    
    public void saveEditedSignal(){
        viewer.saveSignal.setOnAction((event) -> {
        FileReader reader = null;
        try {
            int index = viewer.cb.getItems().indexOf(viewer.cb.getValue());
            JSONParser parser = new JSONParser();
            reader = new FileReader("signals.json");
            JSONArray signalArray = (JSONArray) parser.parse(reader);
            
            JSONObject currentSignal = (JSONObject) signalArray.get(index);
            currentSignal.replace("name", viewer.nameTf.getText());
            currentSignal.replace("targetname", viewer.targetNameTf.getText());
            currentSignal.replace("inputname", viewer.inputNameTf.getText());
            currentSignal.replace("arguments", viewer.arguementTf.getText().split(","));
            System.out.println(currentSignal);
            signalArray.add(index, currentSignal);
            signalArray.remove(index + 1);
            
            FileWriter writer = new FileWriter("signals.json");
            gson.toJson(signalArray, writer);
            writer.close();
            
                
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SignalViewerController.class.getName()).log(Level.SEVERE, null, ex);
        }   catch (IOException ex) {
                Logger.getLogger(SignalViewerController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(SignalViewerController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(SignalViewerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
        });
    }
    
    public void clear(){
        viewer.clearSignal.setOnAction((event) -> {
            viewer.nameTf.clear();
            viewer.targetNameTf.clear();
            viewer.arguementTf.clear();
            viewer.inputNameTf.clear();
        });
    }
    
    

   
    
    
}
