module se.kth.qusaiaha.sudoku {
    requires javafx.controls;
    requires javafx.fxml;


    opens se.kth.qusaiaha.sudoku to javafx.fxml;
    exports se.kth.qusaiaha.sudoku;
}