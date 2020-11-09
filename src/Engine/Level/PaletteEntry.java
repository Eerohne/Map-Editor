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
    private boolean hollow;

    public PaletteEntry(Color color, boolean hollow) {
        this.color = color;
        this.hollow = hollow;
    }

    public Color getColor() {
        return color;
    }

    public boolean isHollow() {
        return hollow;
    }
}
