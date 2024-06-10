package display;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import constantes.Constante;
import gui.Maze;
import pacObject.PacGhost;
import pacObject.PacMan;
import sound.SoundManager;
import utilities.State;
import utilities.Utils;

/**
 * Die PacmanGame-Klasse steuert das Hauptspielgeschehen, einschließlich der
 * Interaktion mit dem Benutzer, der Aktualisierung des Spielzustands und der
 * Steuerung von Pacman und Geistern.
 * 
 * @author Asus
 */
public class PacmanGame {
    Constante constante = new Constante();
    int score;
    int mapIndex;

    private SoundManager soundManager;
    private PacMan pacman = new PacMan();
    private PacGhost[] ghosts = new PacGhost[Constante.NUMBER_OF_GHOST];
    private int[][] blocksMatrix;
    private int[][] gomesMatrix;
    Maze maze;

    /**
     * KeyListener zum Abfangen von Tastatureingaben für Pacman.
     */
    public KeyListener listener = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            pacman.getKey(key, mapIndex);
        }
    };

    /**
     * Konstruktor für die PacmanGame-Klasse.
     */
    public PacmanGame() {
        soundManager = new SoundManager();
        for (int i = 0; i < Constante.NUMBER_OF_GHOST; i++) {
            ghosts[i] = new PacGhost(Utils.clonePoint(Constante.GHOSTS_START[i]), i);
        }
        totalReset();
        maze = new Maze(Utils.clone2DMatrix(blocksMatrix), Utils.clone2DMatrix(gomesMatrix), listener, pacman, ghosts);
    }

    /**
     * Startet das Spiel und führt die Spielschleife aus.
     */
    public void play() {
        while (!noMoreGomes() && !noMoreLife()) {
            maze.setGhostsPoint(ghosts);
            maze.updateMaps(Utils.clone2DMatrix(blocksMatrix), Utils.clone2DMatrix(gomesMatrix));
            maze.show(pacman, score, pacman.getLife());
            pacman.manage();
            manageGhosts();
            pacman.move(mapIndex);
            updateAll();
            moveGhosts();
            updateAll();
        }
        if (noMoreGomes()) {
            soundManager.playGameWonSound();
            congrats("Du hast die Viren verjagt !!!!");

        } else {
            soundManager.playGameLostSound();
            congrats("Oh nein, dein Computer wurde gehackt !!!!");

        }

        ask2Play("");
    }

    /**
     * Wechselt zur Hauptmenüansicht und schließt die aktuelle Spielinstanz.
     */
    private void goToMainMenu() {
        SwingUtilities.invokeLater(() -> {

            new Hauptmenü();

            close();
        });
    }

    /**
     * Schließt das Fenster der aktuellen Spielinstanz.
     */
    private void close() {
        JFrame frame = maze.getFrame();
        if (frame != null) {
            frame.dispose();
        } else {
            System.err.println("Fehler beim Schließen des Fensters: JFrame nicht gefunden.");
        }
    }

    /**
     * Zeigt eine Benutzereingabeaufforderung an, ob der Benutzer erneut spielen
     * möchte.
     * 
     * @param msg Nachricht an den Benutzer
     */
    public void ask2Play(String msg) {
        String response = JOptionPane.showInputDialog(null, msg + "Möchtest du nochmal spielen?\n j: ja  n: nein");
        System.out.println("Antwort : " + response);
        if (response.equals("j")) {
            totalReset();
            play();
        } else if (response.equals("n")) {
            goToMainMenu();
        } else {
            ask2Play("Bitte nochmal eingeben.\n");
        }
    }

    /**
     * Zeigt eine Glückwunschmeldung an.
     * 
     * @param msg Glückwunschnachricht
     */
    public void congrats(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Infos", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Setzt das Spiel auf den Anfangszustand zurück.
     */
    public void totalReset() {
        mapIndex = 0;
        score = 0;
        pacman.setLife(Constante.PAC_START_LIFE);
        partialReset();
        resetMaps();
    }

    /**
     * Setzt die Positionen von Pacman und Geistern zurück.
     */
    public void partialReset() {
        pacman.back2Start();
        for (int i = 0; i < Constante.NUMBER_OF_GHOST; i++) {
            ghosts[i].back2Start();
        }
    }

    /**
     * Setzt die Karten (Maps) zurück.
     */
    private void resetMaps() {
        blocksMatrix = Utils.clone2DMatrix(Constante.blocksMaps[mapIndex]);
        gomesMatrix = Constante.buildGomeMap(mapIndex);
    }

    /**
     * Setzt die Karten (Maps) zurück und passt die Anzahl der Basis-Gomes an.
     * 
     * @param cnt   Anzahl der Basis-Gomes
     * @param index Index der aktuellen Map
     */
    private void resetMaps(int cnt, int index) {
        blocksMatrix = Utils.clone2DMatrix(Constante.blocksMaps[mapIndex]);
        gomesMatrix = Utils.makeGomesForNewMap(blocksMatrix, gomesMatrix, cnt, mapIndex, index);
    }

    /**
     * Überprüft, ob keine Gomes mehr im Spiel sind.
     * 
     * @return true, wenn keine Gomes mehr vorhanden sind; andernfalls false
     */
    public boolean noMoreGomes() {
        for (int i = 0; i < gomesMatrix.length; i++) {
            for (int j = 0; j < gomesMatrix[0].length; j++) {
                if (gomesMatrix[i][j] == 1)
                    return false;
            }
        }
        return true;
    }

    /**
     * Überprüft, ob keine Leben mehr für Pacman übrig sind.
     * 
     * @return true, wenn keine Leben mehr übrig sind; andernfalls false
     */
    public boolean noMoreLife() {
        return pacman.getLife() <= 0;
    }

    /**
     * 
     * @param i
     * @param j
     */
    public void updateGoneMatrix(int i, int j) {
        setGoneMatrixCell(i, j, 0);
    }

    /**
     * Aktualisiert den Spielzustand basierend auf der Position von Pacman und
     * seiner Wechselwirkung mit Gomes.
     */
    public void updateAll() {
        int i = pacman.getMatrixPosition()[0], j = pacman.getMatrixPosition()[1];
        int v = getGoneMatrixCell(i, j);
        if (v != 0) {
            computeScore(v);
            updateGoneMatrix(i, j);
        }
        applyRules(i, j);

    }

    /**
     * Überprüft und wendet die Regeln für die Wechselwirkung zwischen Pacman und
     * Geistern an.
     * 
     * @param i Zeilenindex
     * @param j Spaltenindex
     */
    public void applyRules(int i, int j) {
        if (pacman.getState() == State.INVISIBLE)
            return;

        for (int n = 0; n < Constante.NUMBER_OF_GHOST; n++) {
            int k = ghosts[n].getMatrixPosition()[0], m = ghosts[n].getMatrixPosition()[1];
            if (i == k && j == m) {
                System.out.println("Same pos for Pac and Ghost " + n);
                if (pacman.getState() == State.SUPER) {
                    ghosts[n].startJailTimeContDown();
                    System.out.println("Ghost " + n + " is in Jail");
                } else if (pacman.getState() == State.NORMAL) {
                    System.out.println("Ghost " + n + " caught Pacman");
                    pacman.loseLife();
                    if (pacman.getLife() > 0) {
                        soundManager.playPacmanDeathSound(); // Sound bei Lebensverlust abspielen
                    } else {
                        soundManager.playGameLostSound(); // Sound bei letztem Lebensverlust abspielen
                    }
                    sleep(300);
                    partialReset();
                }
            }
        }
    }

    /**
     * Berechnet die Punktzahl basierend auf dem Wert (Gome-Typ) und aktualisiert
     * die Punktzahl.
     * 
     * @param v Gome-Typ
     */
    public void computeScore(int v) {
        if (v == 1) {
            score += 100;
        } else if (v == 2) {
            score += 300;
            soundManager.playSuperStateSound();
            pacman.specialState(State.INVISIBLE);
            System.out.println("Invisible Pacman");
        } else if (v == 3) {
            score += 500;
            soundManager.playSuperStateSound();
            pacman.specialState(State.SUPER);
            slowdownGhosts();
            System.out.println("Super Pacman");
        } else if (v == 4) {
            score += 1000;
            int index = mapIndex;
            mapIndex++;
            mapIndex %= Constante.blocksMaps.length;
            sleep(100);
            partialReset();
            int cnt = Utils.howMuchBaseGomes(gomesMatrix);
            resetMaps(cnt, index);
        }

        if (score >= 5000)
            pacman.addLife();
    }

    /**
     * Gibt den Wert (Gome-Typ) an einer bestimmten Position in der Gome-Matrix
     * zurück.
     * 
     * @param i Zeilenindex
     * @param j Spaltenindex
     * @return Gome-Typ an der angegebenen Position
     */
    private int getGoneMatrixCell(int i, int j) {
        return gomesMatrix[j][i];
    }

    /**
     * Setzt den Wert (Gome-Typ) an einer bestimmten Position in der Gome-Matrix.
     * 
     * @param i Zeilenindex
     * @param j Spaltenindex
     * @param v Neuer Wert (Gome-Typ)
     */
    private void setGoneMatrixCell(int i, int j, int v) {
        gomesMatrix[j][i] = v;
    }

    /**
     * Bewegt die Geister im Spiel.
     */
    public void moveGhosts() {
        for (int i = 0; i < Constante.NUMBER_OF_GHOST; i++) {
            ghosts[i].move(mapIndex);
        }
    }

    /**
     * Verlangsamt die Geister im Spiel.
     */
    public void slowdownGhosts() {
        for (int i = 0; i < Constante.NUMBER_OF_GHOST; i++) {
            ghosts[i].slowdown();
        }
    }

    /**
     * Steuert das Verhalten der Geister im Spiel.
     */
    public void manageGhosts() {
        for (int i = 0; i < Constante.NUMBER_OF_GHOST; i++) {
            ghosts[i].manage();
        }
    }

    /**
     * Pausiert die Ausführung des aktuellen Threads für die angegebene Zeit in
     * Millisekunden.
     */
    public void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gibt die aktuelle Punktzahl aus.
     */
    public void printScore() {
        System.out.println("Your score : " + score);
    }

    /**
     * Beendet das Spiel und schließt das Fenster.
     */
    public void quit() {
        maze.close();
    }
}