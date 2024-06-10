package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import constantes.Constante;
import pacObject.PacGhost;
import pacObject.PacMan;
import utilities.Utils;

/**
 * Die Maze-Klasse repräsentiert das Hauptfenster des Spiels, das den
 * Spielfeldrahmen, den Statusbereich und den Zeichner enthält. Sie verwaltet
 * die Anzeige von Punkten, Leben und den Spielstatus.
 * 
 * @author Asus
 */
public class Maze {
    private int currentLife;
    private JFrame frame = new JFrame();
    private JLabel lifeLabel;
    private JLabel scoreLabel;
    private JPanel statusBar;
    private Dimension dimension = Utils.cloneDimension(Constante.DIMENSION, 0, 45);
    private Drawer drawer;

    /**
     * Konstruktor für das Maze-Objekt.
     * 
     * @param b        Spielfeld (Blocks)
     * @param g        Spielfeld (Gome)
     * @param listener KeyListener für die Steuerung
     * @param pac      PacMan-Objekt
     * @param ghosts   Array von PacGhost-Objekten
     */
    public Maze(int[][] b, int[][] g, KeyListener listener, PacMan pac, PacGhost[] ghosts) {
        JPanel container = new JPanel();

        lifeLabel = new JLabel("❤: " + currentLife);
        lifeLabel.setForeground(Color.RED.darker());
        scoreLabel = new JLabel("Score: 0");
        statusBar = createStatusBar();
        drawer = new Drawer(b, g);

        container.setLayout(new BorderLayout(0, 2));
        container.add(statusBar, BorderLayout.NORTH);
        container.add(drawer, BorderLayout.CENTER);

        frame.setTitle("HackMan");
        frame.setSize(dimension);
        frame.setLocation(300, 200);
        frame.setContentPane(container);
        frame.addKeyListener(listener);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(true);

        setPacmanFeatures(pac);
        setGhostsPoint(ghosts);
    }

    /**
     * Erstellt den Statusbereich (StatusBar) mit Leben (Hearts) und Punkten
     * (Score).
     * 
     * @return Erstellter StatusBar
     */
    private JPanel createStatusBar() {
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.add(lifeLabel, BorderLayout.WEST);
        statusBar.add(scoreLabel, BorderLayout.EAST);
        return statusBar;
    }

    /**
     * Setzt die Eigenschaften von PacMan im Zeichner.
     * 
     * @param pac PacMan-Objekt
     */
    public void setPacmanFeatures(PacMan pac) {
        drawer.setPacmanFeatures(pac);
    }

    /**
     * Zeigt den aktuellen Spielstatus mit Punkten, Leben und aktualisiertem
     * Zeichner an.
     * 
     * @param pac  PacMan-Objekt
     * @param sc   Punkte
     * @param life Leben
     */
    public void show(PacMan pac, int sc, int life) {
        setPacmanFeatures(pac);
        scoreLabel.setText("Score: " + sc);
        currentLife = life;
        lifeLabel.setText("❤: " + currentLife);
        statusBar.repaint();
        sleep(200);
        drawer.repaint();
    }

    /**
     * Gibt das JFrame-Objekt zurück.
     * 
     * @return JFrame-Objekt
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * Schließt das JFrame-Objekt.
     */
    public void close() {
        frame.dispose();
    }

    /**
     * Aktualisiert die Spielfeldkarten (Blocks und Gome) im Zeichner.
     * 
     * @param b Spielfeld (Blocks)
     * @param g Spielfeld (Gome)
     */
    public void updateMaps(int[][] b, int[][] g) {
        drawer.updateMaps(b, g);
    }

    /**
     * Setzt die Positionen der Geister (PacGhost) im Zeichner.
     * 
     * @param ghosts Array von PacGhost-Objekten
     */
    public void setGhostsPoint(PacGhost[] ghosts) {
        for (int i = 0; i < Constante.NUMBER_OF_GHOST; i++) {
            drawer.setPacghostPoint(i, ghosts[i].getPoint());
        }
    }

    /**
     * Pausiert die Ausführung für die angegebene Zeit.
     * 
     * @param ms Pausezeit in Millisekunden
     */
    public void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}