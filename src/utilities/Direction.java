package utilities;

/**
 * repräsentiert die vier Himmelsrichtungen: NONE, UP, DOWN, LEFT, RIGHT. Jede
 * Richtung hat eine zugeordnete Verschiebung in den x- und y-Koordinaten.
 * 
 * NONE: Keine Verschiebung (0, 0). UP: Verschiebung nach oben (-BLOCK_SIZE, 0).
 * DOWN: Verschiebung nach unten (BLOCK_SIZE, 0). LEFT: Verschiebung nach links
 * (0, -BLOCK_SIZE). RIGHT: Verschiebung nach rechts (0, BLOCK_SIZE).
 * 
 * @author Asus
 */
public enum Direction {

    NONE(0, 0), UP(0, -constantes.Constante.BLOCK_SIZE), DOWN(0, constantes.Constante.BLOCK_SIZE),
    LEFT(-constantes.Constante.BLOCK_SIZE, 0), RIGHT(constantes.Constante.BLOCK_SIZE, 0);

    private int x;
    private int y;

    /**
     * Konstruktor für die Richtung mit den zugehörigen Verschiebungen in den x- und
     * y-Koordinaten.
     * 
     * @param x Die Verschiebung in der x-Richtung.
     * @param y Die Verschiebung in der y-Richtung.
     */
    private Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * 
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * Gibt eine lesbare Zeichenfolge der Richtung zurück, die die Verschiebung
     * angibt.
     * 
     * @return Eine Zeichenfolge der Richtung.
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
  