package pacObject;

import java.awt.Point;
import constantes.Constante;
import utilities.Direction;
import utilities.State;
import utilities.Utils;

/**
 * Eine abstrakte Klasse, die die gemeinsamen Eigenschaften und Methoden von
 * Charakteren im Spiel definiert. Diese Klasse wird von spezifischen
 * Charakterklassen wie Pacman und Ghosts erweitert.
 * 
 * Enthält Position, Bewegung, Zustand, Geschwindigkeit, Startpunkt und
 * andere Eigenschaften eines Charakters. Definiert Methoden für Bewegung,
 * Zustandsverwaltung, Kollisionsprüfung und mehr.
 * 
 * @author Asus
 */
public abstract class AbstractCharacter {
    protected Point point; // Position auf der Karte
    protected Movement movement = new Movement(); // Enthält aktuelle und nächste Position
    protected State state; // Zustand des Objekts (normal, super, schwach)
    protected int velocity = Constante.STD_VELOCITY; // Geschwindigkeit des Charakters
    protected Point START; // Startpunkt auf der Karte

    int unit = 0; // Definiert die Dauer eines Effekts

    /**
     * Abstrakte Methode für die Bewegung eines Charakters.
     * 
     * @param index Der Index des Charakters.
     */
    public abstract void move(int index);

    /**
     * Abstrakte Methode für die Verwaltung eines Charakterzustands.
     */
    public abstract void manage();

    /**
     * Überprüft, ob die Richtung des Charakters geändert werden kann.
     * 
     * @param index Der Index des Charakters.
     */
    public void checkToChangeDirection(int index) {
        if (movement.getNext() != Direction.NONE) {
            int x = point.x;
            int y = point.y;
            Direction d = movement.getNext();

            if (!collision(index, x + d.getX(), y + d.getY())) {
                movement.setCurrent(d);
                movement.setNext(Direction.NONE);
            }

        }
    }

    /**
     * Weist den Charakter an, die Richtung zu ändern.
     * 
     * @param d Die neue Richtung.
     */
    public void askToChangeDirection(Direction d) {
        movement.setNext(d);
    }

    /**
     * Überprüft, ob die übergebenen Koordinaten innerhalb der Spielfeldgrenzen
     * liegen.
     * 
     * @param x Die x-Koordinate.
     * @param y Die y-Koordinate.
     * @return true, wenn die Koordinaten innerhalb der Spielfeldgrenzen liegen,
     *         sonst false.
     */
    public boolean checkBounds(int x, int y) {
        return (x >= 0) && (y >= 0) && (x < Constante.DIMENSION.width) && (y < Constante.DIMENSION.height);
    }

    /**
     * Überprüft, ob der Charakter mit einem Hindernis kollidiert.
     * 
     * @param index Der Index des Charakters.
     * @param x     Die x-Koordinate des Charakters.
     * @param y     Die y-Koordinate des Charakters.
     * @return true, wenn eine Kollision vorliegt, sonst false.
     */
    public boolean collision(int index, int x, int y) {
        if (!checkBounds(x, y))
            return true;

        int i = x / Constante.BLOCK_SIZE;
        int j = y / Constante.BLOCK_SIZE;

        if (Constante.blocksMaps[index][j][i] == 0
                || (Constante.blocksMaps[index][j][i] != 1 && movement.getCurrent() == Direction.UP)) {
            return false;
        }

        return true;
    }

    /**
     * Überprüft, ob der Charakter an den gegenüberliegenden Rand teleportieren
     * kann.
     * 
     * @param x Die x-Koordinate des Charakters.
     * @param y Die y-Koordinate des Charakters.
     * @return true, wenn der Charakter teleportieren kann, sonst false.
     */
    public boolean wraparound(int x, int y) {
        for (int i = 0; i < 2; i++) {
            if (x == Constante.STATIC_WRAPAROUND[i].x && y == Constante.STATIC_WRAPAROUND[i].y) {
                point = Utils.clonePoint(Constante.DYNAMIC_WRAPAROUND[(i + 1) % 2]);
                return true;
            }
        }
        return false;
    }

    /**
     * Gibt die Matrixposition des Charakters zurück.
     * 
     * @return Ein Array mit der x- und y-Position des Charakters in der Matrix.
     */
    public int[] getMatrixPosition() {
        return new int[] { point.x / Constante.BLOCK_SIZE, point.y / Constante.BLOCK_SIZE };
    }

    /**
     * Gibt den Punkt des Charakters zurück.
     * 
     * @return Der Punkt des Charakters.
     */
    public Point getPoint() {
        return point;
    }

    /**
     * Setzt den Charakter zurück auf seinen Startpunkt.
     */
    public void back2Start() {
        point = Utils.clonePoint(START);
    }

    /**
     * Gibt die Bewegungsinformationen des Charakters zurück.
     * 
     * @return Das Movement-Objekt mit aktuellen und nächsten Richtungen.
     */
    public Movement getMovement() {
        return movement;
    }

    /**
     * Gibt den Zustand des Charakters zurück.
     * 
     * @return Der aktuelle Zustand des Charakters (normal, super, schwach).
     */
    public State getState() {
        return state;
    }

    /**
     * Setzt den Punkt des Charakters.
     * 
     * @param p Der neue Punkt des Charakters.
     */
    public void setPoint(Point p) {
        this.point = p;
    }

    /**
     * Setzt die Bewegungsinformationen des Charakters.
     * 
     * @param m Das Movement-Objekt mit aktuellen und nächsten Richtungen.
     */
    public void setMovement(Movement m) {
        this.movement = m;
    }

    /**
     * Setzt den Zustand des Charakters.
     * 
     * @param s Der neue Zustand des Charakters.
     */
    public void setState(State s) {
        this.state = s;
    }

    /**
     * Eine innere Klasse, die die Bewegungsinformationen eines Charakters
     * speichert. Enthält aktuelle und nächste Richtungen.
     * 
     * @author Asus
     */
    public class Movement {
        private Direction current;
        private Direction next;

        /**
         * Konstruktor für das Movement-Objekt. Setzt aktuelle und nächste Richtungen
         * auf NONE.
         */
        public Movement() {
            current = Direction.NONE;
            next = Direction.NONE;
        }

        /**
         * Gibt die aktuelle Richtung zurück.
         * 
         * @return Die aktuelle Richtung.
         */
        public Direction getCurrent() {
            return current;
        }

        /**
         * Gibt die nächste Richtung zurück.
         * 
         * @return Die nächste Richtung.
         */
        public Direction getNext() {
            return next;
        }

        /**
         * Setzt die aktuelle Richtung.
         * 
         * @param c Die neue aktuelle Richtung.
         */
        public void setCurrent(Direction c) {
            this.current = c;
        }

        /**
         * Setzt die nächste Richtung.
         * 
         * @param n Die neue nächste Richtung.
         */
        public void setNext(Direction n) {
            this.next = n;
        }
    }
}