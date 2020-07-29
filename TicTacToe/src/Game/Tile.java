
package Game;

/**
 * @author 198780
 */
public class Tile {
    
    private char mark;
    
    public Tile(char initMark) {
        this.mark = initMark;
    }
    
    public boolean isMarked() {
        if (this.mark == 'X' || this.mark == 'O') {
            return true;
        }
        return false;
    }
    
    public void setMark(char newMark) {
        this.mark = newMark;
    }
    
    public char getMark() {
        return this.mark;
    }
}
