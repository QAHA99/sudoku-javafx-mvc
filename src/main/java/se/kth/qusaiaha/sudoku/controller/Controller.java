package se.kth.qusaiaha.sudoku.controller;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import se.kth.qusaiaha.sudoku.model.Boxes;
import se.kth.qusaiaha.sudoku.model.SudokuIO;
import se.kth.qusaiaha.sudoku.model.SudokuLevel;
import se.kth.qusaiaha.sudoku.view.Buttons;
import se.kth.qusaiaha.sudoku.view.GridView;
import se.kth.qusaiaha.sudoku.view.MenuView;

import java.io.*;

/**
 * Representerar kontrollern för Sudoku-applikationen, hanterar interaktioner mellan modellen och vyer.
 */
public class Controller {
    private Boxes model;
    private GridView gridView;
    private Buttons buttons;
    private MenuView menuView;
    private Stage stage;

    /**
     * Konstruerar en ny Controller-objekt.
     *
     * @param model Boxes-modellen som håller spelets tillstånd.
     * @param gridView GridView-objektet som ansvarar för att visa spelrutnätet.
     * @param buttons Buttons-objektet som representerar spelkontrollknapparna.
     * @param menuView MenuView-objektet som hanterar spelets meny.
     * @param stage JavaFX-scenen där spelgränssnittet visas.
     */
    public Controller(Boxes model, GridView gridView, Buttons buttons, MenuView menuView, Stage stage) {
        this.model = model;
        this.gridView = gridView;
        this.buttons = buttons;
        this.menuView = menuView;
        this.stage = stage;

        gridView.setController(this);
        buttons.setController(this);
        menuView.setController(this);
    }


    /**
     * Hanterar det valda rutnummeret.
     */
    public void handleTheChosenSquare() {
        gridView.updateView();
    }

    /**
     * Hanterar händelsen när en gissning görs, uppdaterar modellen och grid vyn.
     */
    public void handleGuess() {
        if(buttons.isValid()) {
            model.enter(gridView.getClickedRow(), gridView.getClickedCol(), buttons.getSelectedButton());
            gridView.updateView();
            System.out.println(model.boxesLeftToEnter());
        }
    }

    /**
     * Hanterar händelsen när användaren kontrollerar det aktuella tillståndet för Sudoku-brädet.
     * Visar en varning med resultatet.
     */
    public void handleCheck() {
        Alert alert = menuView.alertWindow("Resultat hitills", "information:");
        if (model.Check()){
            alert.setContentText("Korrekt!");
        } else {
            alert.setContentText("Du har gjort några misstag...");
        }
        alert.show();
        gridView.updateView();
    }

    /**
     * Hanterar händelsen när användaren rensar en gissning i en ruta, uppdaterar modellen och grid vyn.
     */
    public void handleClear() {
        if (!buttons.isValid()) {
            model.clear(gridView.getClickedRow(), gridView.getClickedCol());
            gridView.updateView();
            System.out.println(model.boxesLeftToEnter());
        }
    }

    /**
     * Hanterar händelsen när användaren begär en ledtråd, uppdaterar modellen och grid vyn.
     */
    public void handleHint() {
        model.hint();
        gridView.updateView();
    }

    /**
     * Hanterar händelsen när användaren startar ett nytt spel, återställer modellen och uppdaterar grid vyn.
     */
    public void handleNewGame() {
        model.NewGame();
        gridView.updateView();
    }

    /**
     * Hanterar händelsen när användaren startar ett nytt spel med en angiven nivå, återställer modellen och uppdaterar grid vyn.
     *
     * @param level Svårighetsnivån för det nya spelet.
     */
    public void handleNewGameNewLevel(SudokuLevel level) {
        model.NewGameNewLevel(level);
        gridView.updateView();
    }

    /**
     * Hanterar åtgärden att spara det aktuella tillståndet för Sudoku-spelet till en fil.
     * Öppnar en FileChooser-dialog där användaren kan ange filen där spelets tillstånd kommer att sparas.
     * Om en giltig fil väljs sparas det aktuella Sudoku-spelets tillstånd till den filen.
     */
    public void handleSaveGame() {
        File file = menuView.makeFileChooser("Save Game", stage);
        if(file!=null) {
            try {
                SudokuIO.SaveGame(file, model);
            } catch (IOException e) {
                Alert alert = menuView.alertWindow("Exception!", "Exception!");
                alert.setContentText("Exception line 126 while saving game!");
                alert.show();
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Hanterar åtgärden att ladda ett Sudoku-spel från en fil.
     * Öppnar en FileChooser-dialog där användaren kan välja en fil att ladda spelets tillstånd från.
     * Om en giltig fil väljs laddas Sudoku-spelets tillstånd och vyn uppdateras.
     */
    public void handleLoadGame() {
        File file = menuView.makeFileChooser("Load Game", stage);
        Boxes modelFromFiles = null;
        try {
            modelFromFiles = SudokuIO.LoadGame(file);
        } catch (IOException | ClassNotFoundException e) {
            Alert alert = menuView.alertWindow("Exception!", "Exception!");
            alert.setContentText("Exception line 144 while Loading game!");
            alert.show();
            throw new RuntimeException(e);
        }
        if (modelFromFiles != null) {
            model = modelFromFiles;
            gridView.setModel(model);
            gridView.updateView();
        }
    }

    /**
     * Hanterar händelsen när användaren begär information om hur man spelar spelet.
     * Visar en varning med spelets information.
     */
    public void handleInfo() {
        Alert alert = menuView.alertWindow("Spelregler!", "Information");
        alert.setContentText(model.gameInfo());
        alert.show();
    }

    /**
     * Hanterar händelsen när användaren rensar alla gissningar på brädet.
     * Uppdaterar modellen och grid vyn.
     */
    public void handleClearAll() {
        model.clearAll();
        gridView.updateView();
    }

    /**
     * Kontrollerar spelets resultat och visar en gratulerande eller informativ varning om spelet är vunnet eller förlorat.
     */
    public void result() {
        if(model.boxesLeftToEnter() == 0) {
            Alert alert = menuView.alertWindow("Resultat", "Information");
            if(model.result()) {
                alert.setContentText("Grattis, du vann!");
            }else {
                alert.setContentText("Några fel, men bra försök!");
            }
            alert.show();
        }
    }

    /**
     * Hanterar händelsen när användaren begär att avsluta applikationen.
     * Stänger applikations scenen.
     */
    public void handleExit() {
        stage.close();
    }
}
