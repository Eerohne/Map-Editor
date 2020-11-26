/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Model;

import java.util.ArrayList;

/**
 *
 * @author A
 */
public class Project {
    ArrayList<Map> maps;

    public Project() {
        maps = new ArrayList<>();
    }
    
    public Project(Map... map){
        this();
        for (Map m : map) {
            maps.add(m);
        }
    }
}
