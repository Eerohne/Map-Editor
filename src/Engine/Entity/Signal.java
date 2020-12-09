/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity;

import java.util.ArrayList;

/**
 *
 * @author child
 */
public class Signal {
    public String name;
    public String targetName;
    public String inputname;
    public ArrayList<Object> arguments;
    
    public Signal(String name, String targetName, String inputname, ArrayList<Object> arguments)
    {
        this.name = name;
        this.targetName = targetName;
        this.inputname = inputname;
        this.arguments = new ArrayList<>();
        this.arguments = arguments;
    }
    
}
