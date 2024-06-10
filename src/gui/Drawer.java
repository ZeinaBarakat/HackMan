package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.Arrays;
import javax.swing.JPanel;
import constantes.Constante;
import pacObject.PacMan;
import utilities.Direction;

/**
 * Die Drawer-Klasse ist für das Zeichnen des Spiels verantwortlich. Sie enthält
 * Methoden zum Zeichnen von PacMan, Geistern und dem Spielfeld.
 * 
 * @author Asus
 */
@SuppressWarnings("serial")
public class Drawer extends JPanel {
    Constante constante = new Constante();
    @SuppressWarnings("unused")
    private PacMan pac;
    private int[] ghostImageIndices;
    private final int s = Constante.BLOCK_SIZE;
    private Point pacmanPoint = new Point();
    private Point[] ghostPoint = new Point[Constante.NUMBER_OF_GHOST];
    private Image pacmanImage = Constante.PACMAN_NORMAL;
    @SuppressWarnings("unused")
    private Direction pacmanDirection;
    private int[][] blocks;
    private int[][] gomes;

    /**
     * Konstruktor für den Drawer.
     * 
     * @param b Spielfeld (Blocks)
     * @param g Spielfeld (Gome)
     */
    public Drawer(int[][] b, int[][] g) {
        this.setBackground(Color.black);
        updateMaps(b, g);

        ghostImageIndices = new int[Constante.NUMBER_OF_GHOST];
        Arrays.fill(ghostImageIndices, 0);
    }

    /**
     * Setzt das PacMan-Objekt.
     * 
     * @param pac PacMan-Objekt
     */
    public void setPacMan(PacMan pac) {
        this.pac = pac;
    }

    /**
     * Überschreibt die Methode paintComponent, um das Spielfeld, PacMan und die
     * Geister zu zeichnen.
     * 
     * @param g Graphics-Objekt zum Zeichnen
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Zeichne das Labyrinth
        drawMap(g, s);
        // Zeichne PacMan
        drawPacman(g);
        // Zeichne die Geister
        for (int i = 0; i < Constante.NUMBER_OF_GHOST; i++) {
            drawPacghost(g, i);
        }
    }

    /**
     * Zeichnet PacMan.
     * 
     * @param g Graphics-Objekt zum Zeichnen
     */
    public void drawPacman(Graphics g) {
        g.drawImage(pacmanImage, pacmanPoint.x, pacmanPoint.y, null);
    }

    /**
     * Setzt den Ghost-Image-Index für alle Geister.
     * 
     * @param index Index des Geisterbildes
     */
    public void setGhostImageIndexForAll(int index) {
        Arrays.fill(ghostImageIndices, index);
    }

    /**
     * Zeichnet einen Geist.
     * 
     * @param g Graphics-Objekt zum Zeichnen
     * @param i Index des Geistes
     */
    public void drawPacghost(Graphics g, int i) {

        if (i >= 0 && i < Constante.NUMBER_OF_GHOST) {
            Image ghostImage = Constante.GHOST_IMAGES[i];

            // Zeichne das Bild basierend auf der Position des Geistes
            g.drawImage(ghostImage, ghostPoint[i].x, ghostPoint[i].y, null);
            g.setColor(Color.black);
            g.fillOval(ghostPoint[i].x + 6 * s / 16, ghostPoint[i].y + s / 8, s / 4, s / 4);
        }
    }

    /**
     * Zeichnet das Spielfeld.
     * 
     * @param g    Graphics-Objekt zum Zeichnen
     * @param size Größe eines Spielfeldblocks
     */
    public void drawMap(Graphics g, int size) {
        double scale = 0.3;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                // Blocks
                if (blocks[i][j] == 1) {
                    g.setColor(Color.gray);
                    g.fill3DRect(j * size, i * size, size, size, true);
                } else if (blocks[i][j] == 3) {
                    g.setColor(Color.red);
                    g.drawLine(j * size, i * size, (j + 1) * size, i * size);
                }
                // Pacgomes
                int v = gomes[i][j];
                if (v != 0) {
                    scale = Constante.SCALES[v - 1];
                    g.setColor(Constante.GOMESCOLORS[v - 1]);
                    g.fillOval((int) ((j + 0.25) * size - scale), (int) ((i + 0.25) * size - scale),
                            (int) (size * scale), (int) (size * scale));
                }
            }
        }
    }

    /**
     * Aktualisiert die Spielfeldkarten (Blocks und Gome).
     * 
     * @param b Spielfeld (Blocks)
     * @param g Spielfeld (Gome)
     */
    public void updateMaps(int[][] b, int[][] g) {
        blocks = b;
        gomes = g;
    }

    /**
     * Gibt die Größe eines Spielfeldblocks zurück.
     * 
     * @return Größe eines Spielfeldblocks
     */
    public int getBlockSize() {
        return s;
    }

    /**
     * Setzt die Eigenschaften von PacMan im Zeichner.
     * 
     * @param pac PacMan-Objekt
     */
    public void setPacmanFeatures(PacMan pac) {
        pacmanPoint = pac.getPoint();
        pacmanDirection = pac.getMovement().getCurrent();
    }

    /**
     * Setzt die Position eines Geistes im Zeichner.
     * 
     * @param i Index des Geistes
     * @param p Position des Geistes
     */
    public void setPacghostPoint(int i, Point p) {
        ghostPoint[i] = p;

    }

}