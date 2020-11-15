/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Window;

//Merouane Issad

import Engine.Window.Menu.MenuButton;
import Engine.Window.Menu.PauseMenu;
import Engine.Core.Game;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WindowManager extends AnchorPane{
    private Stage stage;
    private Canvas renderCanvas; //first layer, never initiated two times, handled by the renderer
    private AnchorPane ingameDisplay; //second layer, cleared and rebuilt at every level load, controlled by entities and game logic
    private AnchorPane pausePane; //third layer, built only once at game start, hidden and visible on user request (Example : button press)
    private PauseMenu pauseMenu; //menu controller
    
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
        pausePane.setVisible(true);
        
        this.getChildren().addAll(renderCanvas, ingameDisplay, pausePane);
        
        this.setTopAnchor(renderCanvas, 0.0);
        this.setBottomAnchor(renderCanvas, 0.0);
        this.setRightAnchor(renderCanvas, 0.0);
        this.setLeftAnchor(renderCanvas, 0.0);
        
        this.setTopAnchor(ingameDisplay, 0.0);
        this.setBottomAnchor(ingameDisplay, 0.0);
        this.setRightAnchor(ingameDisplay, 0.0);
        this.setLeftAnchor(ingameDisplay, 0.0);
        
        this.setTopAnchor(pausePane, 0.0);
        this.setBottomAnchor(pausePane, 0.0);
        this.setRightAnchor(pausePane, 0.0);
        this.setLeftAnchor(pausePane, 0.0);
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
        Label label2 = new Label("Rendering in testing phase");
        label2.setFont(Font.font("Cambria", 20));
        ingameDisplay.getChildren().addAll(label, label2);
        ingameDisplay.setBottomAnchor(label, 0.0);
        ingameDisplay.setLeftAnchor(label, 0.0);
        ingameDisplay.setTopAnchor(label2, 0.0);
        ingameDisplay.setRightAnchor(label2, 50.0);
    }
    
    public AnchorPane getIngameDisplay() {
        return ingameDisplay;
    }
    
    public void clearIngameDiplay()
    {
        this.ingameDisplay.getChildren().clear();
    }
    
    //pausePane
    private void buildPauseMenu()
    {
        pausePane = new AnchorPane();
        
        pauseMenu = new PauseMenu("main");
        
        //main pause screen
        VBox mainBox = new VBox();
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setSpacing(40);
        
        Button resumeButton = new MenuButton("resume game");
        resumeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Game.pauseGame(false);
            }
        });
        resumeButton.setMinWidth(300);
        resumeButton.setMinHeight(80);
        
        Button optionButton = new MenuButton("options");
        optionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                pauseMenu.transitionToScreen("option");
            }
        });
        
        optionButton.setMinWidth(300);
        optionButton.setMinHeight(80);
        
        Button quitButton = new MenuButton("quit game");
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Game.exit();
            }
        });
        quitButton.setMinWidth(300);
        quitButton.setMinHeight(80);
        
        mainBox.getChildren().addAll(resumeButton, optionButton, quitButton);
        pauseMenu.addScreen("main", mainBox);
        
        //option screen
        VBox optionBox = new VBox();
        optionBox.setAlignment(Pos.CENTER);
        optionBox.setSpacing(40);
        optionBox.setStyle("-fx-background-color: rgba(33, 35, 46, 0.8); -fx-background-radius: 10; -fx-padding: 20;");
        
        ComboBox screenSizeBox = new ComboBox();
        screenSizeBox.setPromptText((int)this.getWidth()+" x "+(int)this.getHeight());
        screenSizeBox.setValue((int)this.getWidth()+" x "+(int)this.getHeight());
        screenSizeBox.getItems().addAll(
            "800 x 600",
            "1280 x 800",
            "1600 x 900",
            "1920 x 1080"
        );
        
        CheckBox fullscreenCheckbox = new CheckBox("fullscreen");
        fullscreenCheckbox.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                screenSizeBox.setDisable(fullscreenCheckbox.isSelected());
            }
        });
        MenuButton applyButton = new MenuButton("Apply settings");
        applyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(fullscreenCheckbox.isSelected()){
                    Game.getWindowManager().setFullScreen(fullscreenCheckbox.isSelected());
                }else{
                    Game.getWindowManager().setFullScreen(false);
                    
                    if(screenSizeBox.getValue() == null){
                        applyButton.valid = false;
                        System.out.println("null screen size");
                    }
                    else
                    {
                        applyButton.valid = true;
                        if(screenSizeBox.getValue().equals("800 x 600"))
                           Game.getWindowManager().resizeWindow(800, 600);
                       else if(screenSizeBox.getValue().equals("1280 x 800"))
                           Game.getWindowManager().resizeWindow(1280, 800);
                       else if(screenSizeBox.getValue().equals("1600 x 900"))
                           Game.getWindowManager().resizeWindow(1600, 900);
                       else if(screenSizeBox.getValue().equals("1920 x 1080"))
                           Game.getWindowManager().resizeWindow(1920, 1080);
                       else{ //fail safe in case the screen resolution doesnt exist
                           Game.getWindowManager().resizeWindow(1280, 800);
                           screenSizeBox.setValue("1280 x 800");
                           }
                    }
                }
                    
            }
        });
        applyButton.setMinWidth(80);
        applyButton.setMinHeight(30);
        
        Button returnButton = new MenuButton("return");
        returnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                pauseMenu.transitionToScreen("main");
            }
        });
        returnButton.setMinWidth(80);
        returnButton.setMinHeight(30);
        
        HBox buttonBar = new HBox();
        buttonBar.setSpacing(30);
        buttonBar.getChildren().addAll(applyButton, returnButton);

        optionBox.getChildren().addAll(screenSizeBox, fullscreenCheckbox, buttonBar);
        optionBox.setVisible(false);
        optionBox.setManaged(false);
        
        pauseMenu.addScreen("option", optionBox);
        
        //root boxes
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(40);
        
        vbox.getChildren().addAll(mainBox, optionBox); //add here all pause screens
        hbox.getChildren().addAll(vbox);
        pausePane.getChildren().add(hbox);
        pausePane.setTopAnchor(hbox, 0.0);
        pausePane.setBottomAnchor(hbox, 0.0);
        pausePane.setRightAnchor(hbox, 0.0);
        pausePane.setLeftAnchor(hbox, 0.0);
    }
    
    public void clearPauseMenu()
    {
        this.pausePane.getChildren().clear();
    }
    
    public void togglePauseMenu()
    {
        this.pausePane.setVisible(!this.pausePane.isVisible());
    }
    
    public void setPauseMenuVisibility(boolean visible)
    {
        System.out.println("open menu -> "+visible);
        //this.pausePane.setVisible(visible);
        if(visible)
            this.pauseMenu.open();
        else
            this.pauseMenu.close();
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
}
