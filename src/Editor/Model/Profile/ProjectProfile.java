/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Model.Profile;

import Editor.Main.MapEditor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 *
 * @author A
 */
public class ProjectProfile extends Profile{
    private LinkedList<MapProfile> maps;
    private int mainMapID = 0;
    
    public MapProfile selectedMap;

    public ProjectProfile(String name) {
        super(name);
        this.maps = new LinkedList<>();
    }
    
    public ProjectProfile(String name, MapProfile map){
        this(name);
        
        map.save();
        maps.add(map);
        this.selectedMap = map;
        this.initializeProject();
    }

    public static boolean openProject(String projectName){
        File projFile = new File(projectName);
        
        if(projFile.exists()){
            ProjectProfile p = new ProjectProfile(projectName);
            p.loadProject(projectName);
            MapEditor.setProject(p);
            return true;
        } else
            return false;
    }
    
    public boolean loadProject(String projName){
        String mapFile = projName + "/resources/levels/";
        
        File mapDir = new File(mapFile);
        File[] mapList = mapDir.listFiles();
        
        for (File file : mapList) {
            this.loadMap(new MapProfile(file));
        }
        
        File projectConfig = new File(projName + "/project.proj");
        
        try {
            Scanner configReader = new Scanner(projectConfig);
            while (configReader.hasNext()) {
                String next = configReader.next();
                
                if(next.startsWith("last_map="))
                    this.selectedMap = this.getMapByName(next.replace("last_map=", ""));
            }
            
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("project.proj unreadable");
            return false;
        }
        
    }
    
    public boolean initializeProject(){
        File rootFile = new File(name);
        boolean success = rootFile.mkdir();
        
        if(success){
            return setupDirectories();
        } else{
            return false;
        }
    }
    
    private boolean setupDirectories(){
        boolean success;
        
        File textures = new File(name + "/resources/images");
        success = textures.mkdirs();

        File sprites = new File(name + "/resources/dev/sprites");
        success = sprites.mkdirs();

        File levels = new File(name + "/resources/levels");
        success = levels.mkdirs();

        File music = new File(name + "/resources/sounds");
        success = music.mkdirs();
        
        File ui = new File(name + "/resources/dev/ui");
        success = ui.mkdirs();
        
        try {
            File errorSprite = new File("dev/engine/error_sprite.png");
            
            Path src = Paths.get(errorSprite.getAbsolutePath());
            Path dst = Paths.get(sprites.getAbsolutePath() + "/error_sprite.png");
            Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
            
            File uiFile = new File("dev/engine/ui");
            
            copyFolder(Paths.get(uiFile.getAbsolutePath()), Paths.get(ui.getAbsolutePath()));
        } catch (FileNotFoundException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        }
        
        File projectConfig = new File(name + "/project.proj");
        try {
            projectConfig.createNewFile();
        } catch (IOException ex) {
            System.out.println(ex + "/n Unable to create project.proj");
            return false;
        }
        
        return success;
    }
    
    public LinkedList<MapProfile> getMaps() {
        return maps;
    }

    public void setMaps(LinkedList<MapProfile> maps) {
        this.maps = maps;
    }

    public void addMap(MapProfile map){
        this.maps.add(map);
    }
    
    public void loadMap(MapProfile map){
        this.addMap(map);
        if(map.isMainMap())
            this.setMainMap(map);
    }
    
    public MapProfile getMainMap() {
        return maps.get(mainMapID);
    }

    public void setMainMap(MapProfile map) {
        for (int i = 0; i < maps.size(); i++) {
            map.setMainMap(false);
            if(map.equals(maps.get(i))){
                this.mainMapID = i;
                map.setMainMap(true);
            }
        }
    }
    
    public MapProfile getSelectedMap(){
        return selectedMap;
    }

    public void setSelectedMap(MapProfile selectedMap) {
        this.selectedMap = selectedMap;
    }
    
    public MapProfile getMapByName(String name) throws FileNotFoundException{
        for (MapProfile map : maps) {
            if(map.getName().equals(name))
                return map;
        }
        
        throw new FileNotFoundException();
    }
    
    private void copyFolder(Path src, Path dest) {
        try (Stream<Path> stream = Files.walk(src)) {
            stream.forEach(source -> {
                try {
                    Files.copy(source, dest.resolve(src.relativize(source)), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            });
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
