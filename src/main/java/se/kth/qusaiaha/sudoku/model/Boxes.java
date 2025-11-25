package se.kth.qusaiaha.sudoku.model;

import java.io.Serializable;
import java.util.Random;

import static se.kth.qusaiaha.sudoku.model.SudokuUtilities.GRID_SIZE;

/**
 * Representerar rutorna i Sudoku-brädet och dess logik.
 */
public class Boxes implements Serializable {
    private Box[][] boxes;
    private int boxesLeftToEnter;
    private SudokuLevel level;

    /**
     * Konstruerar ett nytt Boxes-objekt med en angiven svårighetsnivå.
     *
     * @param level Svårighetsnivån för det nya spelet.
     */
    public Boxes(SudokuLevel level){
        this.boxes = new Box[GRID_SIZE][GRID_SIZE];
        this.boxesLeftToEnter = 81;
        this.level = level;
        fillBoxes(this.level);
    }

    /**
     * Returnerar antalet kvarvarande rutor att fylla i.
     *
     * @return Antalet kvarvarande rutor att fylla i.
     */
    public int boxesLeftToEnter(){
        return boxesLeftToEnter;
    }

    /**
     * Returnerar en kopia av rutorna i brädet.
     *
     * @return En kopia av rutorna i brädet.
     */
    public Box[][] getBoxes() {
        Box[][] copy = new Box[GRID_SIZE][GRID_SIZE];
        for(int row = 0; row < GRID_SIZE; row++){
            for( int col = 0; col < GRID_SIZE; col++){
                copy[row][col] = new Box(boxes[row][col].getToEnter(), boxes[row][col].getEntered() ,boxes[row][col].getStateOfbox());
            }
        }
        return copy;
    }


    /**
     * Fyller i en ruta med ett givet nummer.
     *
     * @param rowNb Radnummer.
     * @param colNb Kolumnnummer.
     * @param entered Det inskrivna värdet i rutan.
     * @throws IllegalArgumentException Om det inskrivna värdet är ogiltigt.
     */
    public void enter(int rowNb, int colNb, int entered) throws IllegalArgumentException {
        if(entered <= 0 || entered > 9) throw new IllegalArgumentException("Ogiltigt värde!");
        if(boxes[rowNb][colNb].getStateOfbox().equals(StateOfBox.KNOWN)) return;
        if(boxes[rowNb][colNb].getEntered() != 0) {
            boxes[rowNb][colNb].setEntered(entered);
        } else {
            boxes[rowNb][colNb].setEntered(entered);
            boxesLeftToEnter--;
        }
    }

    /**
     * Kontrollerar om brädet är korrekt ifyllt.
     *
     * @return Sant om brädet är korrekt ifyllt, annars falskt.
     */
    public boolean Check() {
        for (int row=0; row<GRID_SIZE; row++) {
            for (int col=0; col<GRID_SIZE; col++) {
                if (!boxes[row][col].getStateOfbox().equals(StateOfBox.KNOWN) && boxes[row][col].getEntered() != 0) {
                    if (!boxes[row][col].isEnteredCorrect()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Rensar en ruta.
     *
     * @param rowNr Radnummer.
     * @param colNr Kolumnnummer.
     */
    public void clear(int rowNr, int colNr) {
        if(boxes[rowNr][colNr].getStateOfbox().equals(StateOfBox.KNOWN)) {
            return;
        } else if(boxes[rowNr][colNr].getToEnter() != 0) {
            boxes[rowNr][colNr].setEntered(0);
            boxesLeftToEnter++;
        }
    }

    /**
     * Rensar alla rutor.
     */
    public void clearAll() {
        for (int row=0; row<GRID_SIZE; row++) {
            for(int col=0; col<GRID_SIZE; col++) {
                if(!boxes[row][col].getStateOfbox().equals(StateOfBox.KNOWN)) {
                    boxes[row][col].setEntered(0);
                    this.boxesLeftToEnter++;
                }
            }
        }
    }

    /**
     * Kontrollerar om alla rutor är korrekt ifyllda.
     *
     * @return Sant om alla rutor är korrekt ifyllda, annars falskt.
     */
    public boolean result() {
        for (int row=0; row<GRID_SIZE; row++) {
            for (int col=0; col<GRID_SIZE; col++) {
                if(!boxes[row][col].isEnteredCorrect()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Kontrollerar om det är den sista rutan som ska fyllas i.
     *
     * @return Sant om det är den sista rutan som ska fyllas i, annars falskt.
     */
    public boolean isTheLastBox() {
        return boxesLeftToEnter == 0;
    }

    /**
     * Ger en ledtråd genom att fylla i en slumpmässig ruta.
     */
    public void hint() {
        if (!isTheLastBox()) {
            Random random = new Random();
            int rowRand;
            int colRand;
            do {
                rowRand = random.nextInt(9);
                colRand = random.nextInt(9);
            } while (boxes[rowRand][colRand].getEntered() != 0);
            boxes[rowRand][colRand].setEntered(boxes[rowRand][colRand].getToEnter());
            this.boxesLeftToEnter--;
        }
    }

    /**
     * Startar ett nytt spel med samma svårighetsnivå.
     */
    public void NewGame(){
        this.boxesLeftToEnter = 81;
        fillBoxes(this.level);
    }

    /**
     * Startar ett nytt spel med en angiven svårighetsnivå.
     *
     * @param level Svårighetsnivån för det nya spelet.
     */
    public void NewGameNewLevel(SudokuLevel level){
        this.boxesLeftToEnter = 81;
        fillBoxes(level);
    }

    /**
     * Ger information om hur man spelar spelet.
     *
     * @return Information om hur man spelar spelet.
     */
    public String gameInfo() {
        StringBuilder info = new StringBuilder();
        info.append("1. Välj en ruta.").append("\n")
                .append("2. Välj ett nummer.").append("\n")
                .append("För att rensa en ruta: Välj en ruta och tryck 'C'.").append("\n")
                .append("Tryck på 'Hint' för att automatiskt fylla i en korrekt siffra i en ruta.").append("\n")
                .append("Tryck på 'Check' för att verifiera din framsteg.").append("\n")
                .append("Du vinner när varje ruta har rätt nummer!").append("\n\n")
                .append("Spelregler:").append("\n")
                .append("- Varje rad, kolumn och block (3x3) måste innehålla siffror 1 till 9 utan upprepningar.").append("\n")
                .append("- Summan av varje rad, kolumn och block måste vara 45.").append("\n");
        return info.toString();
    }



    /**
     * Fyller rutorna med nummer från en genererad Sudoku-matris och blandar sedan rutorna.
     *
     * @param level Svårighetsnivån för det genererade spelet.
     */
    private void fillBoxes(SudokuLevel level) {
        int[][][] sudokuMatrix = SudokuUtilities.generateSudokuMatrix(level);
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (sudokuMatrix[row][col][0] != 0) {
                    boxes[row][col] = new Box(sudokuMatrix[row][col][1], sudokuMatrix[row][col][0], StateOfBox.KNOWN);
                    this.boxesLeftToEnter--;
                } else {
                    boxes[row][col] = new Box(sudokuMatrix[row][col][1], sudokuMatrix[row][col][0], StateOfBox.TO_ENTER);
                }
            }
        }
        blendBoxes();
    }

    /**
     * Blandar rutorna genom att byta positioner på två olika nummer i rutorna.
     */
    private void blendBoxes() {
        Random random = new Random();
        int firstNumber, secondNumber;
        do {
            firstNumber = random.nextInt(9) + 1;
            secondNumber = random.nextInt(9) + 1;
        } while (firstNumber == secondNumber);

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (boxes[row][col].getToEnter() == firstNumber) {
                    for (; row < GRID_SIZE; ) {
                        for (int newCol = 0; newCol < GRID_SIZE; newCol++) {
                            if (boxes[row][newCol].getToEnter() == secondNumber) {
                                Box tmp = boxes[row][col];
                                boxes[row][col] = boxes[row][newCol];
                                boxes[row][newCol] = tmp;
                                break;
                            }
                        }
                        break;
                    }
                    break;
                }
            }
        }
    }



    @Override
    public String toString() {
        StringBuilder info = new StringBuilder();
        info.append("[");
        for (int row = 0; row < GRID_SIZE; row++) {
            info.append("{");
            for (int col = 0; col < GRID_SIZE; col++) {
                info.append(boxes[row][col].getEntered()).append(", ");
            }
            info.append("}").append("\n");
        }
        info.append("]");
        return info.toString();
    }
}
