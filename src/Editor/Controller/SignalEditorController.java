/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;

import Editor.Model.SignalModel;
import Editor.View.Menu.Entity.SignalEditorView;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
    JSONArray signals = new JSONArray();

    public SignalEditorController(SignalModel model, SignalEditorView view) throws IOException, FileNotFoundException, ParseException {
        
        this.model = model;
        this.view = view;
        
        allSignals();
        comboBoxHandler();
        
        
    }
    
//show all entites with signals in the second combobox, will then open signal editor window
    private void allSignals() throws FileNotFoundException, IOException, ParseException{
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader("entities.json");
        JSONArray entitiesArray = (JSONArray) parser.parse(reader);
        
        for(int i = 0; i < entitiesArray.size(); i++){
            JSONObject entity = (JSONObject) entitiesArray.get(i);
            
            for(Object keyStr : entity.keySet()){
                JSONObject signal = (JSONObject) entity.get(keyStr);
                if(signal.keySet().contains("signal")){
                    view.cb.getItems().add(entity.keySet().toString());
                }
            }
        
        }
    }
    
    private void comboBoxHandler(){
        view.cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                
                JSONParser parser = new JSONParser();
                try(FileReader reader = new FileReader("entities.json")){
            
                    JSONArray entitiesArray = (JSONArray) parser.parse(reader);
                    JSONArray signals = new JSONArray();
                    JSONObject signalEntity = new JSONObject();
                    for(int i = 1; i < entitiesArray.size(); i++){
                       
                        JSONObject entity = (JSONObject) entitiesArray.get(i);
                        for(Object keyStr : entity.keySet()){
                            signalEntity = (JSONObject) entity.get(keyStr);
                            signals = (JSONArray) signalEntity.get("signal");
                            view.signalSelector.getItems().clear();
                            for(int j = 0; j < signals.size(); j++){
                                view.signalSelector.getItems().add(j);
                            }
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
    
    private void signalSelectorHandler(){
        
    }
    private void combosBoxHandler(){
        view.cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                JSONParser parser = new JSONParser();
                try(FileReader reader = new FileReader("entities.json")){
            
                    JSONArray signalsArray = (JSONArray) parser.parse(reader);
                    
                    for(int i = 0; i < signalsArray.size(); i++){
                        JSONObject signal = (JSONObject) signalsArray.get(i);
                        
                        if(newValue.equals(i)){
                            view.nameTf.setText((String) signal.get("name"));
                            view.targetNameTf.setText((String) signal.get("targetname" ));
                            view.inputNameTf.setText((String) signal.get("inputname"));
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
                            view.arguementTf.setText(argumentStr);
                           
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
    private void clear(){
        view.clearSignal.setOnAction((event) -> {
            System.out.println(signals.size());
        });
    }
    
    
}
