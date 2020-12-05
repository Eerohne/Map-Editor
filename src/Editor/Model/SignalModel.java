/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Model;

import java.util.Arrays;

/**
 *
 * @author linuo
 */
public class SignalModel {
    
    String name;
    String targetname;
    String inputname;
    String [] arguments;

    public SignalModel(String name, String targetname, String inputname, String[] arguments) {
        this.name = name;
        this.targetname = targetname;
        this.inputname = inputname;
        this.arguments = arguments;
    }

    public SignalModel() {
        
    }
    

    public String getName() {
        return name;
    }

    public String getTargetname() {
        return targetname;
    }

    public String getInputname() {
        return inputname;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTargetname(String targetname) {
        this.targetname = targetname;
    }

    public void setInputname(String inputname) {
        this.inputname = inputname;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return name + ", " + targetname + ", " + inputname + ", " + Arrays.deepToString(arguments);
    }
    
    
    
}
