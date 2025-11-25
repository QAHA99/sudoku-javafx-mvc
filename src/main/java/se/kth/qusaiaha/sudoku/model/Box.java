package se.kth.qusaiaha.sudoku.model;

import java.io.Serializable;

/**
 * Representerar en ruta i Sudoku-brädet.
 */
public class Box implements Serializable {
    private int toEnter;
    private int entered;
    private StateOfBox stateOfbox;

    /**
     * Konstruerar en ny ruta.
     *
     * @param toEnter Värdet som ska fyllas i rutan.
     * @param entered Det inskrivna värdet i rutan.
     * @param stateOfbox Tillståndet för rutan.
     */
    public Box(int toEnter, int entered, StateOfBox stateOfbox){
        this.toEnter = toEnter;
        this.entered = entered;
        this.stateOfbox = stateOfbox;
    }

    /**
     * Hämtar värdet som ska fyllas i rutan.
     *
     * @return Värdet som ska fyllas i rutan.
     */
    public int getToEnter() {
        return toEnter;
    }

    /**
     * Ställer in värdet som ska fyllas i rutan.
     *
     * @param toEnter Värdet som ska fyllas i rutan.
     */
    public void setToEnter(int toEnter) {
        this.toEnter = toEnter;
    }

    /**
     * Hämtar det inskrivna värdet i rutan.
     *
     * @return Det inskrivna värdet i rutan.
     */
    public int getEntered() {
        return entered;
    }

    /**
     * Ställer in det inskrivna värdet i rutan.
     *
     * @param entered Det inskrivna värdet i rutan.
     */
    public void setEntered(int entered) {
        this.entered = entered;
    }

    /**
     * Hämtar tillståndet för rutan.
     *
     * @return Tillståndet för rutan.
     */
    public StateOfBox getStateOfbox() {
        return stateOfbox;
    }

    /**
     * Ställer in tillståndet för rutan.
     *
     * @param stateOfbox Tillståndet för rutan.
     */
    public void setStateOfbox(StateOfBox stateOfbox) {
        this.stateOfbox = stateOfbox;
    }

    /**
     * Kontrollerar om det inskrivna värdet är korrekt.
     *
     * @return Sant om det inskrivna värdet är korrekt, annars falskt.
     */
    public boolean isEnteredCorrect() {
        if(entered == toEnter) {
            if(!(this.getStateOfbox().equals(StateOfBox.KNOWN))){
                this.stateOfbox = StateOfBox.KNOWN;
            } else {
                this.stateOfbox = StateOfBox.RIGHT;
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "To Enter: " + toEnter;
    }
}