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
import Commons.SettingsManager.Settings;
import Engine.Core.Sound.SoundManager;
import Engine.RaycastRenderer.Renderer;
import java.awt.Color;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Slider;
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
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class WindowManager extends AnchorPane{
    private Stage stage;
    private Canvas renderCanvas; //first layer, never initiated two times, handled by the renderer
    private AnchorPane ingameDisplay; //second layer, cleared and rebuilt at every level load, controlled by entities and game logic
    private AnchorPane pausePane; //third layer, built only once at game start, hidden and visible on user request (Example : button press)
    private PauseMenu pauseMenu; //menu controller
    
    private int oldWidth, oldHeight; //only exists to reset the screen resolution when exiting fullscreen
    private boolean isFullscreen;
    
    private AnchorPane errorScreen;
        
    public WindowManager(Stage stage, int width, int height, boolean fullscreen)
    {
        super();
        this.stage = stage;
        this.setWidth(width);
        this.setHeight(height);
        this.oldWidth = width;
        this.oldHeight = height;
        this.isFullscreen = fullscreen;
        
        initRenderCanvas();
        buildIngameDisplay();
        buildPauseMenu();
        pausePane.setVisible(true);
        
        this.getChildren().addAll(renderCanvas, ingameDisplay, pausePane);
        
        //anchor all panels to force them to fit on the whole window
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
        
        //give the render canvas to the renderer
        Renderer.setCanvas(renderCanvas);
        
        if(fullscreen){ //special case when game loads on fullscreen because the render canvas wants to be a little baby and not resize correctly
            this.setFullScreen(fullscreen); //setting fullscreen at the end when everything is initialised
            resizeWindow((int) Screen.getPrimary().getBounds().getWidth(), (int) Screen.getPrimary().getBounds().getHeight());
        }
        
    }
    
    public void reloadWindow()
    {
        clearIngameDiplay();
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
            mainBox.setStyle("-fx-background-color: rgba(33, 35, 46, 0.8); -fx-background-radius: 10; -fx-padding: 20;");

            Button resumeButton = new MenuButton("resume game");
            resumeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    Game.togglePause();
                }
            });
            resumeButton.setMinWidth(300);
            resumeButton.setMinHeight(80);

            Button optionButton = new MenuButton("options");
            optionButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    pauseMenu.transitionToScreen("options");
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
        
        //option selector screen
            VBox optionSelectBox = new VBox();
            optionSelectBox.setAlignment(Pos.CENTER);
            optionSelectBox.setSpacing(40);
            optionSelectBox.setStyle("-fx-background-color: rgba(33, 35, 46, 0.8); -fx-background-radius: 10; -fx-padding: 20;");

            Button videoSettingsButton = new MenuButton("video settings");
            videoSettingsButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    pauseMenu.transitionToScreen("video");
                }
            });
            videoSettingsButton.setMinWidth(300);
            videoSettingsButton.setMinHeight(60);
            
            //ANY NEW BUTTONS TO AN OPTION SCREENS COMES HERE!!!
            Button soundSettingsButton = new MenuButton("sound settings");
            soundSettingsButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    pauseMenu.transitionToScreen("sound");
                }
            });
            soundSettingsButton.setMinWidth(300);
            soundSettingsButton.setMinHeight(60);
            
            Button returnToMainButton = new MenuButton("return");
            returnToMainButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    pauseMenu.transitionToScreen("main");
                }
            });
            returnToMainButton.setMinWidth(100);
            returnToMainButton.setMinHeight(20);
            
            optionSelectBox.getChildren().addAll(videoSettingsButton, soundSettingsButton, returnToMainButton);
            
            pauseMenu.addScreen("options", optionSelectBox);
        
        //video option screen
            VBox videoOptionBox = new VBox();
            videoOptionBox.setAlignment(Pos.CENTER);
            videoOptionBox.setSpacing(40);
            videoOptionBox.setStyle("-fx-background-color: rgba(33, 35, 46, 0.8); -fx-background-radius: 10; -fx-padding: 20;");

            MenuButton applyVideo = new MenuButton("Apply settings"); //defined on top here to allow access from other elements
            applyVideo.setDisable(true);

            ComboBox screenSizeBox = new ComboBox();
            screenSizeBox.setPromptText((int)this.getWidth()+" x "+(int)this.getHeight());
            screenSizeBox.setValue((int)this.getWidth()+" x "+(int)this.getHeight());
            screenSizeBox.setDisable(isFullscreen);//when created, set disabled based on if the game runs fullscreen
            screenSizeBox.getItems().addAll(
                "800 x 600",
                "1280 x 800",
                "1600 x 900",
                "1920 x 1080"
            );
            screenSizeBox.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    applyVideo.setDisable(false);
                }
            });

            CheckBox fullscreenCheckbox = new CheckBox("fullscreen");
            fullscreenCheckbox.setSelected(isFullscreen);
            fullscreenCheckbox.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    screenSizeBox.setDisable(fullscreenCheckbox.isSelected());
                    applyVideo.setDisable(false);
                }
            });

            //define what happens when we apply the settings
            applyVideo.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    int newWidth=0, newHeight=0;
                    if(fullscreenCheckbox.isSelected()){
                        Game.getWindowManager().setFullScreen(true);
                        newWidth=(int) Game.getWindowManager().getWidth(); 
                        newHeight=(int) Game.getWindowManager().getHeight();
                    }else{
                        Game.getWindowManager().setFullScreen(false);

                        if(screenSizeBox.getValue() == null){
                            applyVideo.valid = false;
                            System.out.println("null screen size");
                        }
                        else
                        {
                            applyVideo.valid = true;
                            if(screenSizeBox.getValue().equals("800 x 600")) {
                                newWidth=800; newHeight=600;
                            }
                           else if(screenSizeBox.getValue().equals("1280 x 800")) {
                               newWidth=1280; newHeight=800;
                           }
                           else if(screenSizeBox.getValue().equals("1600 x 900")) {
                               newWidth=1600; newHeight=900;
                           }
                           else if(screenSizeBox.getValue().equals("1920 x 1080")) {
                               newWidth=1920; newHeight=1080;
                           }
                           else{ //fail safe in case the screen resolution doesnt exist
                               newWidth=1280; newHeight=800;
                               screenSizeBox.setValue("1280 x 800");
                               }
                            Game.getWindowManager().resizeWindow(newWidth, newHeight);
                        }
                    }
                    applyVideo.setDisable(true); //disable the apply button when we save the changes

                    //now save the settings inside the config file
                    Settings.save("r_window_width", (int)newWidth);
                    Settings.save("r_window_height", (int)newHeight);
                    Settings.save("r_window_fullscreen", fullscreenCheckbox.isSelected());

                }
            });
            applyVideo.setMinWidth(80);
            applyVideo.setMinHeight(30);

            Button returnButton = new MenuButton("return");
            returnButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    pauseMenu.transitionToScreen("options");
                }
            });
            returnButton.setMinWidth(80);
            returnButton.setMinHeight(30);

            HBox buttonBar = new HBox();
            buttonBar.setSpacing(30);
            buttonBar.getChildren().addAll(applyVideo, returnButton);

            videoOptionBox.getChildren().addAll(screenSizeBox, fullscreenCheckbox, buttonBar);
            videoOptionBox.setVisible(false);
            videoOptionBox.setManaged(false);

            pauseMenu.addScreen("video", videoOptionBox);
        //sound option screen
            VBox soundOptionBox = new VBox();
            soundOptionBox.setAlignment(Pos.CENTER);
            soundOptionBox.setSpacing(40);
            soundOptionBox.setStyle("-fx-background-color: rgba(33, 35, 46, 0.8); -fx-background-radius: 10; -fx-padding: 20;");

            //master slider
            Label masterSoundLabel = new Label("master volume : " + Settings.getFloat("snd_master"));
            Slider masterSoundSlider = new Slider(0, 1, Settings.getFloat("snd_master"));
            masterSoundSlider.setMajorTickUnit(0.1f);
            masterSoundSlider.setBlockIncrement(0.1f);
            masterSoundSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    masterSoundLabel.setText("master volume : "+String.format("%.1f", new_val));
                    Settings.save("snd_master", String.format("%.1f", masterSoundSlider.getValue()));
                    SoundManager.changeChannelVolume("master", Settings.getDouble("snd_master"));
            }
            });
            HBox masterSoundBox = new HBox();
            masterSoundBox.getChildren().addAll(masterSoundLabel, masterSoundSlider);
            
            //game slider
            Label gameSoundLabel = new Label("game volume : " + Settings.getFloat("snd_game"));
            Slider gameSoundSlider = new Slider(0, 1, Settings.getFloat("snd_game"));
            gameSoundSlider.setMajorTickUnit(0.1f);
            gameSoundSlider.setBlockIncrement(0.1f);
            gameSoundSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    gameSoundLabel.setText("game volume : "+String.format("%.1f", new_val));
                    Settings.save("snd_game", String.format("%.1f", gameSoundSlider.getValue()));
                    SoundManager.changeChannelVolume("game", Settings.getDouble("snd_game"));
            }
            });
            HBox gameSoundBox = new HBox();
            gameSoundBox.getChildren().addAll(gameSoundLabel, gameSoundSlider);
            
            //music slider
            Label musicSoundLabel = new Label("music volume : " + Settings.getFloat("snd_music"));
            Slider musicSoundSlider = new Slider(0, 1, Settings.getFloat("snd_music"));
            musicSoundSlider.setMajorTickUnit(0.1f);
            musicSoundSlider.setBlockIncrement(0.1f);
            musicSoundSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    musicSoundLabel.setText("music volume : "+String.format("%.1f", new_val));
                    Settings.save("snd_music", String.format("%.1f", musicSoundSlider.getValue()));
                     SoundManager.changeChannelVolume("music", Settings.getDouble("snd_music"));
            }
            });
            HBox musicSoundBox = new HBox();
            musicSoundBox.getChildren().addAll(musicSoundLabel, musicSoundSlider);
            
            //menu slider
            Label menuSoundLabel = new Label("menu volume : " + Settings.getFloat("snd_menu"));
            Slider menuSoundSlider = new Slider(0, 1, Settings.getFloat("snd_menu"));
            menuSoundSlider.setMajorTickUnit(0.1f);
            menuSoundSlider.setBlockIncrement(0.1f);
            menuSoundSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    menuSoundLabel.setText("menu volume : "+String.format("%.1f", new_val));
                    Settings.save("snd_menu", String.format("%.1f", menuSoundSlider.getValue()));
                    SoundManager.changeChannelVolume("menu", Settings.getDouble("snd_menu"));
            }
            });
            HBox menuSoundBox = new HBox();
            menuSoundBox.getChildren().addAll(menuSoundLabel, menuSoundSlider);

            Button returnSoundToOptionsButton = new MenuButton("return");
            returnSoundToOptionsButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    pauseMenu.transitionToScreen("options");
                }
            });
            returnSoundToOptionsButton.setMinWidth(80);
            returnSoundToOptionsButton.setMinHeight(30);

            HBox soundButtonBar = new HBox();
            soundButtonBar.setAlignment(Pos.CENTER);
            soundButtonBar.setSpacing(30);
            soundButtonBar.getChildren().addAll(returnSoundToOptionsButton);

            soundOptionBox.getChildren().addAll(masterSoundBox, gameSoundBox, musicSoundBox, menuSoundBox, soundButtonBar);
            soundOptionBox.setVisible(false);
            soundOptionBox.setManaged(false);

            pauseMenu.addScreen("sound", soundOptionBox);
            
        
        //root boxes
        HBox rootMenuHBox = new HBox();
        rootMenuHBox.setAlignment(Pos.CENTER);
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(40);
        
        vbox.getChildren().addAll(pauseMenu.getScreens()); //add here all pause screens
        rootMenuHBox.getChildren().addAll(vbox);
        
    //no level found screen
        errorScreen = new AnchorPane();
        errorScreen.setVisible(false);
        //levelNotFoundScreen.setAlignment(Pos.CENTER);
        errorScreen.setMouseTransparent(true);
        
        HBox labelBox = new HBox();
        labelBox.setAlignment(Pos.CENTER);
        
        Label errorLabel = new Label("-Warning : Engine level Error!-");
        errorLabel.setWrapText(true);
        errorLabel.setMaxWidth(this.getWidth());
        errorLabel.setTextAlignment(TextAlignment.CENTER);
        Game.errorMessage.addListener((observer, oldValue, newValue) -> errorLabel.setText(newValue));
        errorLabel.setFont(Font.font("Cambria", 30));
        errorLabel.setStyle("-fx-text-fill: rgba(255, 0, 0, 255);");
        
        labelBox.getChildren().add(errorLabel);
        
        Pane blackPane = new Pane();
        blackPane.setStyle("-fx-background-color: rgba(0, 0, 0, 1.0);");
        
        errorScreen.getChildren().addAll(blackPane, labelBox);
        
        errorScreen.setTopAnchor(blackPane, 0.0);
        errorScreen.setBottomAnchor(blackPane, 0.0);
        errorScreen.setRightAnchor(blackPane, 0.0);
        errorScreen.setLeftAnchor(blackPane, 0.0);
        
        errorScreen.setTopAnchor(labelBox, 0.0);
        errorScreen.setBottomAnchor(labelBox, 0.0);
        errorScreen.setRightAnchor(labelBox, 0.0);
        errorScreen.setLeftAnchor(labelBox, 0.0);
                
        pausePane.getChildren().addAll(errorScreen, rootMenuHBox);
        pausePane.setTopAnchor(rootMenuHBox, 0.0);
        pausePane.setBottomAnchor(rootMenuHBox, 0.0);
        pausePane.setRightAnchor(rootMenuHBox, 0.0);
        pausePane.setLeftAnchor(rootMenuHBox, 0.0);
        
        pausePane.setTopAnchor(errorScreen, 0.0);
        pausePane.setBottomAnchor(errorScreen, 0.0);
        pausePane.setRightAnchor(errorScreen, 0.0);
        pausePane.setLeftAnchor(errorScreen, 0.0);
    }
    
    public void clearPauseMenu()
    {
        this.pausePane.getChildren().clear();
    }
    
    public void setErrorMessageVisibility(boolean visible)
    {
        errorScreen.setVisible(visible);
    }
    
    public void setPauseMenuVisibility(boolean visible)
    {
        if(visible)
            this.pauseMenu.open();
        else
            this.pauseMenu.close();
    }
    
    public void togglePauseMenuVisibility()
    {
        this.pauseMenu.toggle();
    }
    
    public boolean getPauseMenuVisibility()
    {
        return this.pauseMenu.open;
    }
    
    //window
    public void resizeWindow(int width, int height)
    {
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
            stage.setFullScreen(true);
            this.resizeWindow((int) this.getWidth(), (int) this.getHeight());
        }
        else
        {
            this.resizeWindow(oldWidth, oldHeight);
            stage.setFullScreen(false);
        }
    }
}
