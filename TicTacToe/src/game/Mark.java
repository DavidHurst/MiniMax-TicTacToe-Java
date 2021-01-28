
package game;

/**
 * @author <a href="mailto:peter.vakulin@protonmail.com">Peter Vakulin</a>
 */

public enum Mark {
    X('X'),
    O('O'),
    BLANK(' ');

    private final char mark;

    Mark(char initMark) {
        this.mark = initMark;
    }

    public boolean isMarked() {
        return this != BLANK;
    }

    public char getMark() {
        return this.mark;
    }

    @Override
    public String toString() {
        return String.valueOf(mark);
    }
}
