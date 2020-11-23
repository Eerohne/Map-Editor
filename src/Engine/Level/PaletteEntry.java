/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Level;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 *
 * @author child
 */
public class PaletteEntry {
    private Color color;
    private Image texture;
    private int flag;

    public PaletteEntry(Color color, Image texture, int flag) {
        this.color = color;
        this.texture = texture;
        this.flag = flag;
    }
    
    public PaletteEntry(Image texture, int flag) {
        this.texture = texture;
        this.flag = flag;
    }

    public Color getColor() {
        return color;
    }
    
    public Image getTexture()
    {
        return texture;
    }

    public int getFlag() {
        return flag;
    }
}
