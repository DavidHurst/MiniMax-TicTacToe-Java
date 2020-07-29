
package Game;

/**
 *
 * @author 198780
 */
public class Tile {
    
    private char mark;
    
    public Tile(char initMark) {
        this.mark = initMark;
    }
    
    public boolean isMarked() {
        return this.mark == ' ';
    }
    
    public void markTile(char newMark) {
        this.mark = newMark;
    }
    
    public char getMark() {
        return this.mark;
    }
}
