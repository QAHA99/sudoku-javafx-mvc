package se.kth.qusaiaha.sudoku.model;

import java.io.*;

public class SudokuIO {

    private SudokuIO(){

    }

    public static void SaveGame(File file, Boxes model) throws IOException {
        if (file != null) {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(model);
            out.close();
        }
    }

    public static Boxes LoadGame(File file) throws IOException, ClassNotFoundException {
        if (file != null) {
            Boxes model;
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            model = (Boxes) in.readObject();
            in.close();
            return model;
        }
        return null;   // Return null if the file is null}
    }
}
