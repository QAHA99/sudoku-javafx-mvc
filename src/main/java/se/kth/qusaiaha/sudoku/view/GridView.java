package se.kth.qusaiaha.sudoku.view;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.input.MouseEvent;
import se.kth.qusaiaha.sudoku.model.Boxes;
import se.kth.qusaiaha.sudoku.controller.*;

import se.kth.qusaiaha.sudoku.model.StateOfBox;

public class GridView {
    private final int GRID_SIZE = 9;
    private final int SECTIONS_PER_ROW = 3;
    private final int SECTION_SIZE = 3;
    private Label[][] numberTiles; // the tiles/squares to show in the UI grid
    private GridPane numberPane;
    private Boxes model;
    private int clickedRow;
    private int clickedCol;

    private Controller controller;

    public GridView(Boxes model) {
        numberTiles = new Label[GRID_SIZE][GRID_SIZE];
        this.model = model;
        initNumberTiles();
        numberPane = makeNumberPane();
        this.clickedCol = -1;
        this.clickedRow = -1;
        addEventHandler();
    }

    private final void initNumberTiles() {
        Font font = Font.font("Monospaced", FontWeight.NORMAL, 20);
        int showNumber;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                showNumber = model.getBoxes()[row][col].getEntered();
                String showNumberString = (showNumber != 0) ? String.valueOf(showNumber) : " ";
                Label tile = new Label(showNumberString);
                tile.setPrefWidth(32);
                tile.setPrefHeight(32);
                tile.setFont(font);
                tile.setAlignment(Pos.CENTER);
                tile.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");
                tile.setOnMouseClicked(event -> {
                    int clickedRow = GridPane.getRowIndex(tile);
                    int clickedCol = GridPane.getColumnIndex(tile);
                    if (clickedRow != 0 && clickedCol != 0) {
                        this.clickedRow = clickedRow;
                        this.clickedCol = clickedCol;
                        controller.handleTheChosenSquare();
                        controller.handleGuess();
                        controller.result();
                        controller.handleClear();
                    }
                });

                numberTiles[row][col] = tile;
            }
        }
    }

    private final GridPane makeNumberPane() {
        // create the root grid pane
        GridPane root = new GridPane();
        root.setStyle(
                "-fx-border-color: black; -fx-border-width: 1.0px; -fx-background-color: white;");

        // create the 3*3 sections and add the number tiles
        for (int srow = 0; srow < SECTIONS_PER_ROW; srow++) {
            for (int scol = 0; scol < SECTIONS_PER_ROW; scol++) {
                TilePane section = new TilePane();
                section.setPrefColumns(SECTION_SIZE);
                section.setPrefRows(SECTION_SIZE);
                section.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");

                // add number tiles to this section
                for (int row = 0; row < SECTION_SIZE; row++) {
                    for (int col = 0; col < SECTION_SIZE; col++) {
                        // calculate which tile and add
                        section.getChildren().add(
                                numberTiles[srow * SECTION_SIZE + row][scol * SECTION_SIZE + col]);
                    }
                }

                // add the section to the root grid pane
                root.add(section, scol, srow);
            }
        }

        return root;
    }

    public void addEventHandler() {
        EventHandler<MouseEvent> CLickHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (int row = 0; row < GRID_SIZE; row++) {
                    for (int col = 0; col < GRID_SIZE; col++) {
                        if (event.getSource() == numberTiles[row][col]) {
                            clickedRow = row;
                            clickedCol = col;
                            // we got the row and column - now call the appropriate controller method, e.g.
                            controller.handleTheChosenSquare();
                            controller.handleGuess();
                            controller.result();
                            controller.handleClear();
                        }
                    }
                }
            }
        };
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                numberTiles[row][col].setOnMouseClicked(CLickHandler); // add your custom event handler
            }
        }
    }


    public void updateColor() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                String backgroundColor;
                if (model.getBoxes()[row][col].getStateOfbox().equals(StateOfBox.KNOWN)) {
                    backgroundColor = "#e7e7e7";
                } else if (row == clickedRow && col == clickedCol && !model.getBoxes()[row][col].getStateOfbox().equals(StateOfBox.KNOWN)) {
                    backgroundColor = "lightblue";
                } else {
                    backgroundColor = "white";
                }
                numberTiles[row][col].setStyle("-fx-border-color: black; -fx-border-width: 0.5px; -fx-background-color: " + backgroundColor + ";");
            }
        }
    }

    public boolean isValid() {
        return getClickedRow()<=8 && getClickedRow()>=0 && getClickedCol()<=8 && getClickedCol()>=0;
    }


    public void updateView() {
        if (model != null) {
            String displayValue;
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    if (model.getBoxes()[row][col].getEntered() != 0) {
                        displayValue = String.valueOf(model.getBoxes()[row][col].getEntered());
                    } else {
                        displayValue = "";
                    }
                    numberTiles[row][col].setText(displayValue);
                }
            }
            updateColor();
        }
    }

    public void setModel(Boxes model) {
        this.model = model;
    }

    public int getClickedRow() {
        return clickedRow;
    }

    public int getClickedCol() {
        return clickedCol;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public GridPane getNumberPane() {
        return numberPane;
    }
}
