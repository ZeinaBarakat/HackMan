package pacObject;

import java.awt.Point;
import java.awt.event.KeyEvent;

import constantes.Constante;
import utilities.Direction;
import utilities.State;
import utilities.Utils;

/**
 * Eine Klasse, die den Pac-Man-Charakter im Spiel repräsentiert, abgeleitet von
 * AbstractCharacter.
 * 
 * Enthält spezifische Eigenschaften und Methoden für Pac-Man, wie das Bewegen
 * auf der Karte, das Verwalten von Leben und speziellen Zuständen sowie die
 * Verarbeitung von Tastatureingaben.
 * 
 * @author Asus
 */
public class PacMan extends AbstractCharacter {
    private int life;
    boolean bonus;

    /**
     * Konstruktor für Pac-Man. Initialisiert den Startpunkt, die Bewegung, den
     * Zustand und das Leben.
     */
    public PacMan() {
        START = new Point(Constante.PAC_START.x, Constante.PAC_START.y);
        point = Utils.clonePoint(START);
        movement = new Movement();
        state = State.NORMAL;
        life = Constante.PAC_START_LIFE;
        bonus = false;
    }

    /**
     * Bewegt Pac-Man auf der Karte basierend auf seiner aktuellen Richtung.
     * Überprüft auch auf Kollisionen und ändert die Richtung, wenn eine Kollision
     * auftritt. Überprüft zusätzlich auf den Bildschirmrand und implementiert
     * Wraparound.
     * 
     * @param index Der Index von Pac-Man.
     */
    @Override
    public void move(int index) {
        this.checkToChangeDirection(index);

        int x = point.x;
        int y = point.y;

        if (movement.getCurrent() == Direction.UP)
            y -= velocity;
        else if (movement.getCurrent() == Direction.DOWN)
            y += velocity;
        else if (movement.getCurrent() == Direction.LEFT)
            x -= velocity;
        else if (movement.getCurrent() == Direction.RIGHT)
            x += velocity;

        if (checkBounds(x, y)) {
            if (collision(index, x, y))
                return;
            point.x = x;
            point.y = y;
        }

        if (wraparound(x, y))
            return;
    }

    /**
     * Verwaltet den Pac-Man, überprüft den Zustand, das Leben und den Bonuszustand.
     */
    public void manage() {
        if (unit > 0) {
            unit--;

        } else {
            this.changeState(State.NORMAL);

        }
    }

    /**
     * Setzt Pac-Man in einen speziellen Zustand und startet den Zeiteffekt.
     * 
     * @param s Der spezielle Zustand.
     */
    public void specialState(State s) {
        this.changeState(s);
        this.unit = Constante.UNIT;
    }

    /**
     * Setzt Pac-Man zurück an seinen Startpunkt und ändert die Richtung auf "NONE".
     */
    @Override
    public void back2Start() {
        super.back2Start();
        movement.setCurrent(Direction.NONE);
    }

    /**
     * Fügt Pac-Man ein weiteres Leben hinzu, wenn der Bonus nicht aktiv ist.
     */
    public void addLife() {
        if (bonus)
            return;
        life++;
        bonus = true;
    }

    /**
     * Verringert das Leben von Pac-Man.
     */
    public void loseLife() {
        life--;
    }

    /**
     * Ändert den Zustand von Pac-Man.
     * 
     * @param s Der neue Zustand.
     */
    private void changeState(State s) {
        this.state = s;
    }

    /**
     * Gibt die Anzahl der Leben von Pac-Man zurück.
     * 
     * @return Die Anzahl der Leben von Pac-Man.
     */
    public int getLife() {
        return life;
    }

    /**
     * Setzt die Anzahl der Leben von Pac-Man.
     * 
     * @param life Die neue Anzahl der Leben.
     */
    public void setLife(int life) {
        this.life = life;
    }

    /**
     * Verarbeitet die Tastatureingabe von Pac-Man und ändert die nächste Richtung
     * entsprechend.
     * 
     * @param key   Die Tasten-ID der gedrückten Taste.
     * @param index Der Index von Pac-Man.
     */
    public void getKey(int key, int index) {
        if (key == KeyEvent.VK_UP) {
            this.movement.setNext(Direction.UP);
        } else if (key == KeyEvent.VK_DOWN) {
            this.movement.setNext(Direction.DOWN);
        } else if (key == KeyEvent.VK_LEFT) {
            this.movement.setNext(Direction.LEFT);
        } else if (key == KeyEvent.VK_RIGHT) {
            this.movement.setNext(Direction.RIGHT);
        }

        this.checkToChangeDirection(index);
    }

    /**
     * Setzt den Bonuszustand von Pac-Man zurück.
     */
    public void resetBonus() {
        bonus = false;
    }

}