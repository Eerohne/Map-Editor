/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Core;

//Merouane Issad

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WindowManager extends AnchorPane{
    private Stage stage;
    private Canvas renderCanvas; //first layer, never initiated two times, handled by the renderer
    public GraphicsContext renderContext; //given to the renderer once and never touched again
    private AnchorPane ingameDisplay; //second layer, cleared and rebuilt at every level load, controlled by entities and game logic
    private AnchorPane pauseMenu; //third layer, built only once at game start, hidden and visible on user request (Example : button press)
        
    public WindowManager(Stage stage, int width, int height)
    {
        super();
        this.stage = stage;
        this.setWidth(width);
        this.setHeight(height);
        
        initRenderCanvas();
        initIngameDisplay();
        initPauseMenu();
        
        this.getChildren().addAll(renderCanvas, ingameDisplay, pauseMenu);
        
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
    }
    
    //renderCanvas
    private void initRenderCanvas()
    {
        renderCanvas = new Canvas(this.getWidth(), this.getHeight());
        
        renderCanvas.widthProperty().bind(this.widthProperty()); //assures that canvas width is always the same as the master's width (this)
        renderCanvas.heightProperty().bind(this.heightProperty()); //same
        renderContext = renderCanvas.getGraphicsContext2D(); //create the renderContext
    }
    
    public GraphicsContext getRenderContext()
    {
        return this.renderContext;
    }
    
     //in-game Display
    private void initIngameDisplay()
    {
        ingameDisplay = new AnchorPane();
        //only a setup test, this would normally be code inside some entities
        Label label = new Label("abcdefghij");
        label.setFont(Font.font("Cambria", 32));
        ingameDisplay.getChildren().add(label);
        ingameDisplay.setBottomAnchor(label, 0.0);
        ingameDisplay.setLeftAnchor(label, 0.0);
    }
    
    public AnchorPane getIngameDisplay() {
        return ingameDisplay;
    }
    
    public void clearIngameDiplay()
    {
        this.ingameDisplay.getChildren().clear();
    }
    
    //pauseMenu
    private void initPauseMenu()
    {
        pauseMenu = new AnchorPane();
        
        //put pause menu elements inside
        Button button1 = new Button("resize");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                resizeWindow(1000, 800);
            }
        });
        pauseMenu.getChildren().add(button1);
        this.setBottomAnchor(button1, 0.0);
        this.setRightAnchor(button1, 0.0);
    }

    public AnchorPane getPauseMenu() { //temporary
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
    
    //window
    public void resizeWindow(int width, int height)
    {
        System.out.println(stage);
        this.stage.setWidth(width);
        this.stage.setHeight(height);
    }
}
