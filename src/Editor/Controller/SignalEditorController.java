/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;

import Editor.Main.MapEditor;
import Editor.Model.SignalModel;
import Editor.View.Menu.Entity.SignalEditorView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author linuo
 */
public class SignalEditorController {
    SignalModel model = new SignalModel();
    SignalEditorView view = new SignalEditorView();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public SignalEditorController(SignalModel model, SignalEditorView view) throws IOException, FileNotFoundException, ParseException {
        
        this.model = model;
        this.view = view;
        
        allEntities();
        comboBoxHandler();
        signalSelectorHandler();
        saveChanges();
        clearBtn();
        deleteSignal();
        addSignal();
        
    }
    
     private void allEntities() throws ParseException{
        JSONParser parser = new JSONParser();
        
        try(FileReader reader = new FileReader(MapEditor.getProject().getSelectedMapPath())){
            
            JSONObject allEntity = (JSONObject) parser.parse(reader);
            JSONArray entitiesArray = (JSONArray) allEntity.get("entities");
            for(int i = 0; i < entitiesArray.size(); i++){
                JSONObject entity = new JSONObject();
                entity = (JSONObject) entitiesArray.get(i);
                String str = (String) entity.get("name");
                view.cb.getItems().add(str);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
////show all entites with signals in the second combobox, will then open signal editor window
//    private void allSignals() throws FileNotFoundException, IOException, ParseException{
//        JSONParser parser = new JSONParser();
//        FileReader reader = new FileReader(MapEditor.getProject().getSelectedMapPath());
//        JSONObject allEntities = (JSONObject) parser.parse(reader);
//        JSONArray entitiesArray = (JSONArray) allEntities.get("entities");
//        JSONArray signalEntiteis = new JSONArray();
//        
//        for(int i = 0; i < entitiesArray.size(); i++){
//            JSONObject entity = (JSONObject) entitiesArray.get(i);
//            if(entity.keySet().contains("signal")){
//                signalEntiteis = (JSONArray) entity.get("signal");
//                if(signalEntiteis.size() != 0){
//                     view.cb.getItems().add(entity.get("name").toString());
//                }
//            }
//        }
//    }
    
    // getting the entities that have signals
    private void comboBoxHandler(){
        view.cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                
                FileReader reader = null;
                try {
                    JSONParser parser = new JSONParser();
                    reader = new FileReader(MapEditor.getProject().getSelectedMapPath());
                    JSONObject allEntities = (JSONObject) parser.parse(reader);
                    JSONArray entitiesArray = (JSONArray) allEntities.get("entities");
                    String name = (String) view.cb.getValue();
                    JSONArray signals = new JSONArray();
                    
                    // getting the entity that has signal 
                    for(int i = 0; i < entitiesArray.size(); i++){
                        JSONObject signalEntity = (JSONObject) entitiesArray.get(i);
                        if(signalEntity.values().contains(name) && signalEntity.containsKey("signal")){
                            signals = (JSONArray) signalEntity.get("signal");
                        }
                    }
                    
                    
                    // adding entries to the signal selector
                    view.signalSelector.getItems().clear();
                    for(int j = 0; j < signals.size(); j++){
                        JSONObject signal = (JSONObject) signals.get(j);
                        String signalName = (String) signal.get("name");
                        view.signalSelector.getItems().add(signalName);
                   
                    }
                    signals.clear();
                    
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SignalEditorController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(SignalEditorController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(SignalEditorController.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        reader.close();
                    } catch (IOException ex) {
                        Logger.getLogger(SignalEditorController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
    
    // display selected signal to the UI
    private void signalSelectorHandler(){
        view.signalSelector.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                FileReader reader = null;
                try {
                    String name = (String) view.cb.getValue();
                    String signalName = (String) view.signalSelector.getValue();
                    int index = view.signalSelector.getItems().indexOf(signalName);
                    reader = new FileReader(MapEditor.getProject().getSelectedMapPath());
                    JSONParser parser = new JSONParser();
                    JSONObject allEntities = (JSONObject) parser.parse(reader);
                    JSONArray entitiesArray = (JSONArray) allEntities.get("entities");
                    JSONArray signals = new JSONArray();
                    
                    //getting the entitiy that has signals
                    for(int i = 0; i < entitiesArray.size(); i++){
                        JSONObject signalEntity = (JSONObject) entitiesArray.get(i);
                        
                        // getting the signal json array
                        if(signalEntity.values().contains(name)){
                            signals = (JSONArray) signalEntity.get("signal");
                        }
                    }
                    
                    // getting the one of the signal from the signal array and display it on the UI 
                    if(index >= 0){
                        JSONObject signal = (JSONObject) signals.get(index);
                        view.nameTf.setText(signal.get("name").toString());
                        view.targetNameTf.setText(signal.get("targetname").toString());
                        view.inputNameTf.setText(signal.get("inputname").toString());

                        List list = new ArrayList();
                        JSONArray tempArr = (JSONArray) signal.get("arguments");
                        String [] argumentArr = new String[tempArr.size()];

                        for(int j = 0; j < tempArr.size(); j++){
                            list.add(tempArr.get(j));
                            argumentArr[j] = (String) list.get(j);
                        }

                        String str = Arrays.toString(argumentArr);
                        String argumentStr = str.substring(1, str.length() - 1);
                        view.arguementTf.setText(argumentStr);
                    }
                    
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SignalEditorController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(SignalEditorController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(SignalEditorController.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        reader.close();
                    } catch (IOException ex) {
                        Logger.getLogger(SignalEditorController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
    
    private void saveChanges(){
        view.saveSignal.setOnAction((event) -> {
            
            FileReader reader = null;
            try {
                String name = (String) view.cb.getValue();
                JSONParser parser = new JSONParser();
                reader = new FileReader(MapEditor.getProject().getSelectedMapPath());
                JSONObject savefile = (JSONObject) parser.parse(reader);
                JSONArray entitiesArray = (JSONArray) savefile.get("entities");
                int index = view.cb.getItems().indexOf(view.cb.getValue());
                int signalIndex = view.signalSelector.getItems().indexOf(view.signalSelector.getValue());
                JSONArray signals = new JSONArray();
                int arrayPosition = 0;
                
                //getting the entitiy that has signals
                for(int i = 0; i < entitiesArray.size(); i++){
                    JSONObject signalEntity = (JSONObject) entitiesArray.get(i);
                    
                        
                    // getting the signal json array
                    if(signalEntity.values().contains(name)){
                        arrayPosition = entitiesArray.indexOf(signalEntity);
                        signals = (JSONArray) signalEntity.get("signal");
                    }
                }
                
                // replacing the values of signals and put it back into the signals array 
                
                JSONObject signal = (JSONObject) signals.get(signalIndex);
                signal.replace("name", view.nameTf.getText());
                signal.replace("targetname", view.targetNameTf.getText());
                signal.replace("inputname", view.inputNameTf.getText());
                signal.replace("arguments", view.arguementTf.getText().split(","));
                
                signals.set(signalIndex, signal);
                
                // puuting the signals array back into the json object, json object back into the array
                for(int j = 0; j < entitiesArray.size(); j++){
                    JSONObject signalEntity = (JSONObject) entitiesArray.get(j);
                    
                    if(signalEntity.values().contains(name)){
                        signalEntity.put("signal", signals);
                        entitiesArray.set(arrayPosition, signalEntity);
                        savefile.put("entities", entitiesArray);
                    }
                }
                
                FileWriter writer = new FileWriter(MapEditor.getProject().getSelectedMapPath());
                gson.toJson(savefile, writer);
                writer.close();
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SignalEditorController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SignalEditorController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(SignalEditorController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(SignalEditorController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private void addSignal(){
        view.add.setOnAction((event) -> {
            FileReader reader = null;
            try {
                JSONParser parser = new JSONParser();
                reader = new FileReader(MapEditor.getProject().getSelectedMapPath());
                JSONObject savefile = (JSONObject) parser.parse(reader); // the whole savefile 
                JSONArray entities = (JSONArray) savefile.get("entities"); // the entities array 
                JSONObject signalEntity = new JSONObject(); // getting the entity that has signals and currently working on
                JSONArray signals = new JSONArray(); // signal array of the current entity 
                JSONObject newSignal = new JSONObject(); // signal to be added
                String name = (String) view.cb.getValue();
                
                for(int i = 0; i < entities.size(); i++){
                    JSONObject tempObj = (JSONObject) entities.get(i);
                    if(tempObj.values().contains(name) && tempObj.containsKey("signal")){
                        signalEntity = tempObj;
                        signals = (JSONArray) signalEntity.get("signal");
                
                        newSignal.put("name", view.nameTf.getText());
                        newSignal.put("targetname", view.targetNameTf.getText());
                        newSignal.put("inputname", view.inputNameTf.getText());
                        newSignal.put("arguments", view.arguementTf.getText().split(","));

                        signals.add(newSignal);
                        signalEntity.replace("signal", signals);

                        for(int j = 0; j < entities.size(); j++){
                            JSONObject tempObj1 = (JSONObject) entities.get(j);
                            if(tempObj1.values().contains(name)){
                                entities.set(j, signalEntity);
                            }
                        }
                    }else if(tempObj.values().contains(name) && !tempObj.containsKey("signal")){
                        tempObj.put("signal", new JSONArray());
                        signalEntity = tempObj;
                        //System.out.println(signalEntity);
                        signals = (JSONArray) signalEntity.get("signal");
                
                        newSignal.put("name", view.nameTf.getText());
                        newSignal.put("targetname", view.targetNameTf.getText());
                        newSignal.put("inputname", view.inputNameTf.getText());
                        newSignal.put("arguments", view.arguementTf.getText().split(","));

                        signals.add(newSignal);
                        signalEntity.replace("signal", signals);

                        for(int j = 0; j < entities.size(); j++){
                            JSONObject tempObj1 = (JSONObject) entities.get(j);
                            if(tempObj1.values().contains(name)){
                                entities.set(j, signalEntity);
                            }
                        }
                        
                    }
                }
                
                savefile.replace("entities", entities);
                
                FileWriter writer = new FileWriter(MapEditor.getProject().getSelectedMapPath());
                gson.toJson(savefile, writer);
                writer.close();
                
                
                
                
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SignalEditorController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SignalEditorController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(SignalEditorController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(SignalEditorController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
    }
    
    private void deleteSignal(){
        view.delete.setOnAction((event) -> {
            FileReader reader = null;
            try {
                String name = (String) view.cb.getValue();
                String key = name.substring(1, name.length()-1);
                JSONParser parser = new JSONParser();
                reader = new FileReader(MapEditor.getProject().getSelectedMapPath());
                JSONObject savefile = (JSONObject) parser.parse(reader);
                JSONArray entitiesArray = (JSONArray) savefile.get("entities");
                int index = view.cb.getItems().indexOf(view.cb.getValue());
                int signalIndex = view.signalSelector.getItems().indexOf(view.signalSelector.getValue());
                JSONArray signals = new JSONArray();
                int arrayPostion = 0;
                
                //getting the entitiy that has signals
                for(int i = 0; i < entitiesArray.size(); i++){
                    JSONObject signalEntity = (JSONObject) entitiesArray.get(i);
                        
                    // getting the signal json array and remove the selected signal from the array 
                    if(signalEntity.values().contains(name)){
                        arrayPostion = entitiesArray.indexOf(signalEntity);
                        signals = (JSONArray) signalEntity.get("signal");
                    }
                }
                signals.remove(signalIndex);
                
                // putting the updated signals array back into the json object, then json object back into json array
                for(int j = 0; j < entitiesArray.size(); j++){
                    JSONObject signalEntity = (JSONObject) entitiesArray.get(j);
                    
                    if(signalEntity.values().contains(name)){
                        
                        signalEntity.replace("signal", signals);
                        entitiesArray.set(arrayPostion, signalEntity);
                        savefile.put("entities", entitiesArray);
                    }
                }
                
                FileWriter writer = new FileWriter(MapEditor.getProject().getSelectedMapPath());
                gson.toJson(savefile, writer);
                writer.close();
                
                view.signalSelector.getItems().remove(signalIndex);
                clear();
                
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SignalEditorController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SignalEditorController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(SignalEditorController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(SignalEditorController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
   
    private void clearBtn(){
        view.clearSignal.setOnAction((event) -> {
            clear();
        });
    }
    
    private void clear(){
        view.nameTf.clear();
        view.targetNameTf.clear();
        view.arguementTf.clear();
        view.inputNameTf.clear();
    }
    
    
}
