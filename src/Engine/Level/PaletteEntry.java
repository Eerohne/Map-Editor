/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Level;

import javafx.scene.paint.Color;

/**
 *
 * @author child
 */
public class PaletteEntry {
    private Color color;
    private int flag;

    public PaletteEntry(Color color, int flag) {
        this.color = color;
        this.flag = flag;
    }

    public Color getColor() {
        return color;
    }

    public int getFlag() {
        return flag;
    }
}
