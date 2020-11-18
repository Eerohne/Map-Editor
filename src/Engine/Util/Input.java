/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Util;

import Engine.Core.Game;
import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author child
 */
public class Input {
    private static HashMap<KeyCode, Boolean> keyMap = new HashMap<>();
    
    public static void init()
    {
        Game.scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent key) {
                keyMap.put(key.getCode(), true);
                if(key.getCode() == KeyCode.ESCAPE)
                    Game.togglepauseGame();
            }
        });
        
        Game.scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent key) {
                keyMap.put(key.getCode(), false);
            }
        });
    }
    public static boolean keyPressed(KeyCode keyName)
    {
        if(keyMap.containsKey(keyName))
        {
            return keyMap.get(keyName);
        }
        else
            return false;
    }
}
