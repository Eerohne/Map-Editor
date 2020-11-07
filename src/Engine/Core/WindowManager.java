/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Core;

//Merouane Issad

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class WindowManager extends AnchorPane{
    private Stage stage;
    private Canvas renderCanvas; //first layer
    public GraphicsContext renderContext;
    private AnchorPane ingameDisplay; //second layer
    private AnchorPane pauseMenu; //third layer
        
    public WindowManager(Stage stage, int width, int height)
    {
        super();
        this.stage = stage;
        this.setWidth(width);
        this.setHeight(height);
        
        renderCanvas = new Canvas(this.getWidth(), this.getHeight());
        ingameDisplay = new AnchorPane();
        pauseMenu = new AnchorPane();
        
        this.getChildren().addAll(pauseMenu);
        
        this.setTopAnchor(renderCanvas, 0.0);
        this.setBottomAnchor(renderCanvas, 0.0);
        this.setRightAnchor(renderCanvas, 0.0);
        this.setLeftAnchor(renderCanvas, 0.0);
        
        this.setTopAnchor(ingameDisplay, 0.0);
        this.setBottomAnchor(ingameDisplay, 0.0);
        this.setRightAnchor(ingameDisplay, 0.0);
        this.setLeftAnchor(ingameDisplay, 0.0);
        
        this.setTopAnchor(pauseMenu, 0.0);
        this.setBottomAnchor(pauseMenu, 0.0);
        this.setRightAnchor(pauseMenu, 0.0);
        this.setLeftAnchor(pauseMenu, 0.0);
        
        initPauseMenu();
    }
    
    public void resizeWindow(int width, int height)
    {
        System.out.println(stage);
        this.stage.setWidth(width);
        this.stage.setHeight(height);
    }
    private void initRenderCanvas()
    {
        renderCanvas.widthProperty().bind(this.widthProperty());
        renderCanvas.heightProperty().bind(this.heightProperty());
        renderContext = renderCanvas.getGraphicsContext2D();
    }
    
    private void initPauseMenu()
    {
        //put pause menu elements inside
        Button button1 = new Button("resize");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Game.resizeWindow(400, 350);
            }
        });
        pauseMenu.getChildren().add(button1);
        this.setTopAnchor(button1, 0.0);
        this.setLeftAnchor(button1, 0.0);
    }

    public GraphicsContext getRenderContext()
    {
        return this.renderContext;
    }
    
    public AnchorPane getIngameDisplay() {
        return ingameDisplay;
    }
    
    public void clearIngameDiplay()
    {
        this.ingameDisplay.getChildren().clear();
    }

    public AnchorPane getPauseMenu() {
        return pauseMenu;
    }
    
    public void togglePauseMenu()
    {
        this.pauseMenu.setVisible(!this.pauseMenu.isVisible());
    }
    
    public void setPauseMenuVisibility(boolean visible)
    {
        this.pauseMenu.setVisible(visible);
    }
}
