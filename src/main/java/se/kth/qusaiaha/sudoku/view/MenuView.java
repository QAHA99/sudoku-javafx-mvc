package se.kth.qusaiaha.sudoku.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import se.kth.qusaiaha.sudoku.model.SudokuLevel;
import se.kth.qusaiaha.sudoku.controller.Controller;

import java.io.File;

/**
 * Represents the view for the menu bar in the Sudoku application.
 */
public class MenuView {
    private Menu fileMenu, gameMenu, helpMenu, newLevel;
    private MenuBar menuBar;
    private MenuItem newGame, easy, medium, hard;
    private MenuItem exit, saveGame, loadGame, info, check, clear;
    private Controller controller;

    /**
     * Constructs a new MenuView and initializes menu items.
     */
    public MenuView() {
        initializeMenuItems();
        addEventHandler();
    }

    /**
     * Gets the menu bar containing various menus and items.
     *
     * @return The MenuBar containing menus and items.
     */
    public MenuBar getMenuBar() {
        return menuBar;
    }

    /**
     * Sets the controller for handling menu actions.
     *
     * @param controller The controller to be set.
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Initializes menu items and constructs the menu bar.
     */
    private void initializeMenuItems() {
        this.menuBar = new MenuBar();

        this.fileMenu = createMenu("File");
        this.gameMenu = createMenu("Game");
        this.helpMenu = createMenu("Help");
        this.newLevel = createMenu("New Level");

        this.newGame = createMenuItem("New Game");
        this.easy = createMenuItem("Easy");
        this.medium = createMenuItem("Medium");
        this.hard = createMenuItem("Hard");
        this.exit = createMenuItem("Exit");
        this.saveGame = createMenuItem("Save Game");
        this.loadGame = createMenuItem("Load Game");
        this.info = createMenuItem("Info");
        this.check = createMenuItem("Check");
        this.clear = createMenuItem("Clear");

        this.fileMenu.getItems().addAll(this.loadGame, this.saveGame, this.exit);
        this.newLevel.getItems().addAll(this.easy, this.medium, this.hard);
        this.gameMenu.getItems().addAll(this.newGame, this.newLevel);
        this.helpMenu.getItems().addAll(this.info,this.check, this.clear);
        this.menuBar.getMenus().addAll(this.fileMenu, this.gameMenu, this.helpMenu);
    }

    /**
     * Opens a FileChooser dialog for selecting a file based on the specified title.
     * The method allows the user to either save or open a file, depending on the title.
     *
     * @param title The title of the FileChooser dialog, indicating the purpose (e.g., "Save Game" or "Open Game").
     * @param stage The Stage object associated with the current JavaFX application window.
     * @return A File object representing the selected file if the user confirms the action, or null if the action is canceled.
     */
    public File makeFileChooser(String title, Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Game Files", "*.sudoku"));
        if(title.equals("Save Game")){
            return fileChooser.showSaveDialog(stage);
        }else if(title.equals("Load Game")) {
            return fileChooser.showOpenDialog(stage);
        }
        return null;
    }

    /**
     * Creates and returns an information alert window with the specified header text and title.
     *
     * @param headerText The header text for the alert window.
     * @param title      The title of the alert window.
     * @return An information alert window.
     */
    public Alert alertWindow(String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(headerText);
        alert.setTitle(title);
        return alert;
    }

    /**
     * Creates a new menu with the given title.
     *
     * @param title The title of the menu.
     * @return The created Menu object.
     */
    private Menu createMenu(String title) {
        return new Menu(title);
    }

    /**
     * Creates a new menu item with the given title.
     *
     * @param title The title of the menu item.
     * @return The created MenuItem object.
     */
    private MenuItem createMenuItem(String title) {
        return new MenuItem(title);
    }

    /**
     * Adds event handlers for menu items to handle user actions.
     */
    private void addEventHandler() {
        EventHandler<ActionEvent> newGameHandler = new EventHandler<>() {

            @Override
            public void handle(ActionEvent actionEvent) {controller.handleNewGame();
            }
        };
        newGame.setOnAction(newGameHandler);


        EventHandler<ActionEvent> newGameEasyHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleNewGameNewLevel(SudokuLevel.EASY);
            }
        };
        easy.setOnAction(newGameHandler);


        EventHandler<ActionEvent> newGameMediumHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleNewGameNewLevel(SudokuLevel.MEDIUM);
            }
        };
        medium.setOnAction(newGameMediumHandler);


        EventHandler<ActionEvent> newGameHardHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleNewGameNewLevel(SudokuLevel.HARD);
            }
        };
        hard.setOnAction(newGameHardHandler);


        EventHandler<ActionEvent> infoHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleInfo();
            }
        };
        info.setOnAction(infoHandler);

        EventHandler<ActionEvent> checkHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleCheck();
            }
        };
        check.setOnAction(checkHandler);


        EventHandler<ActionEvent> clearAllHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleClearAll();
            }
        };
        clear.setOnAction(clearAllHandler);

        EventHandler<ActionEvent> saveGameHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleSaveGame();
            }
        };
        saveGame.setOnAction(saveGameHandler);

        EventHandler<ActionEvent> loadGameHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleLoadGame();
            }
        };
        loadGame.setOnAction(loadGameHandler);

        EventHandler<ActionEvent> exitHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleExit();
            }
        };
        exit.setOnAction(exitHandler);
    }


}
