
package Game;

/**
 * @author DavidHurst
 */
public class Tile {
    
    private char mark;
    
    public Tile(char initMark) {
        this.mark = initMark;
    }
    
    public boolean isMarked() {
        return this.mark == 'X' || this.mark == 'O';
    }
    
    public void setMark(char newMark) {
        this.mark = newMark;
    }
    
    public char getMark() {
        return this.mark;
    }
}
