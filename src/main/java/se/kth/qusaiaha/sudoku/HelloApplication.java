package se.kth.qusaiaha.sudoku;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import se.kth.qusaiaha.sudoku.controller.Controller;
import se.kth.qusaiaha.sudoku.model.Boxes;
import se.kth.qusaiaha.sudoku.model.SudokuLevel;
import se.kth.qusaiaha.sudoku.model.SudokuUtilities;
import se.kth.qusaiaha.sudoku.view.Buttons;
import se.kth.qusaiaha.sudoku.view.GridView;
import se.kth.qusaiaha.sudoku.view.MenuView;

public class HelloApplication extends Application{
    private GridView gridView;
    private Buttons buttons;
    private MenuView menuView;
    private BorderPane borderpane;
    private Controller controller;
    private Boxes model;
    public static void main(String[] arg) {

        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.model = new Boxes(SudokuLevel.EASY);
        initializeTheView();
        controller = new Controller(model,gridView,buttons,menuView,stage);

        Scene scene = new Scene(borderpane,662,572);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();
    }

    private void initializeTheView() {
        this.gridView = new GridView(model);
        this.buttons = new Buttons();
        this.menuView = new MenuView();
        this.borderpane = new BorderPane();

        borderpane.setLeft(buttons.getLeftVBox());
        borderpane.setRight(buttons.getRightVBox());
        borderpane.setCenter(gridView.getNumberPane());
        borderpane.setTop(menuView.getMenuBar());

    }

}
