/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Core;

//Merouane Issad

import Engine.RaycastRenderer.Renderer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WindowManager extends AnchorPane{
    private Stage stage;
    private Canvas renderCanvas; //first layer, never initiated two times, handled by the renderer
    private AnchorPane ingameDisplay; //second layer, cleared and rebuilt at every level load, controlled by entities and game logic
    private AnchorPane pauseMenu; //third layer, built only once at game start, hidden and visible on user request (Example : button press)
    
    private int oldWidth, oldHeight;
    private boolean isFullscreen;
        
    public WindowManager(Stage stage, int width, int height)
    {
        super();
        this.stage = stage;
        this.setWidth(width);
        this.setHeight(height);
        this.isFullscreen = false;
        
        initRenderCanvas();
        buildIngameDisplay();
        buildPauseMenu();
        pauseMenu.setVisible(false);
        
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
        this.resizeWindow(width, height);
    }
    
    //renderCanvas
    private void initRenderCanvas()
    {
        renderCanvas = new Canvas(this.getWidth(), this.getHeight());
    }
    
    public Canvas getRenderCanvas()
    {
        return renderCanvas;
    }
    
     //in-game Display
    private void buildIngameDisplay()
    {
        ingameDisplay = new AnchorPane();
        //only a setup test, this would normally be code inside some entities
        Label label = new Label("Press esc to toggle pause menu");
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
    private void buildPauseMenu()
    {
        //Game.getWindowManager().setFullScreen(!isFullscreen);
        System.out.println("pause build");
        pauseMenu = new AnchorPane();
        
        //main pause screen
        VBox pauseVbox = new VBox();
        pauseVbox.setAlignment(Pos.CENTER);
        pauseVbox.setSpacing(40);
        
        Button resumeButton = new Button("resume game");
        resumeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Game.togglepauseGame();
            }
        });
        resumeButton.setMinWidth(300);
        resumeButton.setMinHeight(80);
        
        Button optionButton = new Button("options");
        
        optionButton.setMinWidth(300);
        optionButton.setMinHeight(80);
        
        Button quitButton = new Button("quit game");
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Game.exit();
            }
        });
        quitButton.setMinWidth(300);
        quitButton.setMinHeight(80);

        pauseVbox.getChildren().addAll(resumeButton, optionButton, quitButton);
        
        //option screen
        VBox optionVbox = new VBox();
        optionVbox.setAlignment(Pos.CENTER);
        optionVbox.setSpacing(40);
        
        ComboBox screnSizeBox = new ComboBox();
        screnSizeBox.setPromptText((int)this.getWidth()+" x "+(int)this.getHeight());
        screnSizeBox.getItems().addAll(
            "800 x 600",
            "1280 x 800",
            "1600 x 900"
        );
        
        CheckBox fullscreenCheckbox = new CheckBox("fullscreen");
        fullscreenCheckbox.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                screnSizeBox.setDisable(fullscreenCheckbox.isSelected());
            }
        });
        Button applyButton = new Button("Apply settings");
        applyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(fullscreenCheckbox.isSelected()){
                    Game.getWindowManager().setFullScreen(fullscreenCheckbox.isSelected());
                }else{
                    Game.getWindowManager().setFullScreen(false);
                    if(screnSizeBox.getValue() == null)
                        Game.getWindowManager().resizeWindow((int)Game.getWindowManager().getWidth(), (int)Game.getWindowManager().getHeight());
                    else if(screnSizeBox.getValue().equals("800 x 600"))
                        Game.getWindowManager().resizeWindow(800, 600);
                    else if(screnSizeBox.getValue().equals("1280 x 800"))
                        Game.getWindowManager().resizeWindow(1280, 800);
                    else if(screnSizeBox.getValue().equals("1600 x 900"))
                        Game.getWindowManager().resizeWindow(1600, 900);
                }
                    
            }
        });
        applyButton.setMinWidth(80);
        applyButton.setMinHeight(30);
        
        Button returnButton = new Button("return");
        
        returnButton.setMinWidth(80);
        returnButton.setMinHeight(30);
        
        HBox buttonBar = new HBox();
        buttonBar.setSpacing(30);
        buttonBar.getChildren().addAll(applyButton, returnButton);

        optionVbox.getChildren().addAll(screnSizeBox, fullscreenCheckbox, buttonBar);
        optionVbox.setVisible(false);
        optionVbox.setManaged(false);
        
        optionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                optionVbox.setVisible(true);
                optionVbox.setManaged(true);
                pauseVbox.setVisible(false);
                pauseVbox.setManaged(false);
            }
        });
        
        returnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                optionVbox.setVisible(false);
                optionVbox.setManaged(false);
                pauseVbox.setVisible(true);
                pauseVbox.setManaged(true);
            }
        });
        
        //root boxes
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(40);
        
        vbox.getChildren().addAll(pauseVbox, optionVbox); //add here all pause screens
        hbox.getChildren().addAll(vbox);
        pauseMenu.getChildren().add(hbox);
        pauseMenu.setTopAnchor(hbox, 0.0);
        pauseMenu.setBottomAnchor(hbox, 0.0);
        pauseMenu.setRightAnchor(hbox, 0.0);
        pauseMenu.setLeftAnchor(hbox, 0.0);
    }
    
    public void clearPauseMenu()
    {
        this.pauseMenu.getChildren().clear();
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
        System.out.println("resizing to : "+width+", "+height);
        this.stage.setWidth(width);
        this.stage.setHeight(height);
        this.renderCanvas.setWidth(width);
        this.renderCanvas.setHeight(height);
        
        Renderer.resize();
    }
    
    public void setFullScreen (boolean fullscreen)
    {
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        
        this.isFullscreen = fullscreen;
        if(fullscreen){
            this.oldWidth = (int) this.getWidth();
            this.oldHeight = (int) this.getHeight();
            stage.setFullScreen(fullscreen);
            this.resizeWindow((int) this.getWidth(), (int) this.getHeight());
        }
        else
        {
            this.resizeWindow(oldWidth, oldHeight);
            stage.setFullScreen(fullscreen);
        }
    }
    
    
    /*private Pane getCentedBox(Control... nodes, String type)
    {
        if(type == "hbox")
        {
            Hbox
        }
    }*/
}
