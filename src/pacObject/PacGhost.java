package pacObject;

import java.awt.Image;
import java.awt.Point;
import java.util.Random;
import constantes.Constante;
import utilities.Direction;
import utilities.State;
import utilities.Utils;

/**
 * Eine Klasse, die einen Geistcharakter im Spiel repräsentiert, abgeleitet von
 * AbstractCharacter.
 * 
 * Enthält spezifische Eigenschaften und Methoden für Geister, wie
 * Geschwindigkeitsänderungen, das Verwalten von Gefängniszeiten und das Bewegen
 * des Geistes auf der Karte.
 * 
 * @author Asus
 */
public class PacGhost extends AbstractCharacter {
    Constante constante = new Constante();
    int jailTime = 0;
    int baseImageIndex;
    int imageIndex;

    /**
     * Konstruktor für einen PacGhost.
     * 
     * @param p Der Startpunkt des Geistes.
     * @param i Der Index des Geisterbildes.
     */
    public PacGhost(Point p, int i) {
        START = Utils.clonePoint(p);
        point = Utils.clonePoint(p);
        movement.setCurrent(Direction.UP);
        state = State.NORMAL;

    }

    /**
     * Setzt den Startpunkt des Geistes.
     * 
     * @param p Der neue Startpunkt.
     */
    public void setStart(Point p) {
        START = p;
    }

    /**
     * Startet den Countdown für die Gefängniszeit des Geistes.
     */
    public void startJailTimeContDown() {
        jailTime = Constante.JAIL_TIME_UNIT;
    }

    /**
     * Gibt die Geschwindigkeit des Geistes zurück.
     * 
     * @return Die Geschwindigkeit des Geistes.
     */
    public int getVelocity() {
        return velocity;
    }

    /**
     * Verlangsamt die Geschwindigkeit des Geistes.
     */
    public void slowdown() {
        velocity = Constante.SLOW_VELOCITY;
        unit = Constante.UNIT;

    }

    /**
     * Gibt das Bild der Geister zurück.
     * 
     * @return Das Bild des Geistes.
     */
    public Image getGhostImage() {
        return Constante.GHOST_IMAGES[imageIndex];
    }

    /**
     * Setzt den Geist in den normalen Zustand zurück.
     */
    public void toNormal() {
        velocity = Constante.STD_VELOCITY;
    }

    /**
     * Verwaltet den Geist, überprüft die Zeiteffekte und die Rückkehr zum normalen
     * Zustand.
     */
    @Override
    public void manage() {
        if (unit >= 0)
            unit--;
        else
            toNormal();

        if (jailTime > 0) {
            jailTime--;
            back2Start();
        }
    }

    /**
     * Bewegt den Geist auf der Karte basierend auf seiner aktuellen Richtung.
     * Überprüft auch auf Kollisionen und ändert die Richtung, wenn eine Kollision
     * auftritt.
     * 
     * @param index Der Index des Geistes.
     */
    @Override
    public void move(int index) {
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

        if (collision(index, x, y)) {
            movement.setCurrent(getRandomDirection());
            move(index);
            return;
        }
        point.x = x;
        point.y = y;
    }

    /**
     * Korrigiert einen schlechten Zug des Geistes, indem er die Position anpasst.
     */
    public void correctBadMove() {
        if (getVelocity() == Constante.STD_VELOCITY) {
            int x = point.x, y = point.y;

            if ((x + y) % Constante.STD_VELOCITY == 0)
                return;

            setPoint(correctPoint(0, x, y));

        }
    }

    /**
     * Korrigiert die Position des Geistes, um Kollisionen zu vermeiden.
     * 
     * @param index Der Index des Geistes.
     * @param x     Die x-Koordinate des Geistes.
     * @param y     Die y-Koordinate des Geistes.
     * @return Die korrigierte Position als Punkt.
     */
    private Point correctPoint(int index, int x, int y) {

        if (x % Constante.STD_VELOCITY != 0) {
            if (checkBounds(x + Constante.SLOW_VELOCITY, y) && !collision(index, x + Constante.SLOW_VELOCITY, y)) {
                x += Constante.SLOW_VELOCITY;
            } else {
                x -= Constante.SLOW_VELOCITY;
            }
        }

        if (y % Constante.STD_VELOCITY != 0) {
            if (checkBounds(x, y + Constante.SLOW_VELOCITY) && !collision(index, x, y + Constante.SLOW_VELOCITY)) {
                y += Constante.SLOW_VELOCITY;
            } else {
                y -= Constante.SLOW_VELOCITY;
            }
        }

        return new Point(x, y);
    }

    /**
     * Setzt den Geist zurück an seinen Startpunkt und ändert die Richtung auf "UP".
     */
    @Override
    public void back2Start() {
        super.back2Start();
        movement.setCurrent(Direction.UP);
        toNormal(); // *************************
    }

    /**
     * Generiert eine zufällige Richtung für den Geist.
     * 
     * @return Eine zufällige Richtung für den Geist.
     */
    public Direction getRandomDirection() {
        Direction[] choice = { Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT };
        Random rand = new Random();
        int n = rand.nextInt(1000) % 4;
        // System.out.println("Random : "+ n);

        return choice[n];
    }

}