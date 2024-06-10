package constantes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import javax.imageio.ImageIO;
import utilities.Utils;

/**
 * Die Constante-Klasse enthält Konstanten und Einstellungen für das Spiel.
 * Hierzu gehören Größen, Farben, Startpositionen und Bilder.
 * 
 * @author Asus
 */
public class Constante {

    public static final int BLOCK_SIZE = 30;
    public static final Dimension DIMENSION = new Dimension(BLOCK_SIZE * 20, BLOCK_SIZE * 17);
    public static final int UNIT = 30;
    public static final int JAIL_TIME_UNIT = UNIT / 3;
    public static final int STD_VELOCITY = BLOCK_SIZE;
    public static final int SLOW_VELOCITY = STD_VELOCITY;
    public static final Color[] GOMESCOLORS = { Color.blue, Color.magenta, Color.orange, Color.orange.darker() }; // Ich habe hier die letzte Farbe von grün auf Dunkelorange geändert
    public static final double[] SCALES = { 0.3, 0.5, 0.5, 0.5 };
    public static Point[] STATIC_WRAPAROUND = { new Point(-BLOCK_SIZE, 8 * BLOCK_SIZE),
            new Point(19 * BLOCK_SIZE + BLOCK_SIZE, 8 * BLOCK_SIZE) };
    public static Point[] DYNAMIC_WRAPAROUND = { new Point(0, 8 * BLOCK_SIZE),
            new Point(19 * BLOCK_SIZE, 8 * BLOCK_SIZE) };
    public static Point PAC_START = new Point(16 * BLOCK_SIZE, 15 * BLOCK_SIZE);
    public static Point[] GHOSTS_START = new Point[] { new Point(9 * BLOCK_SIZE, 7 * BLOCK_SIZE),
            new Point(10 * BLOCK_SIZE, 7 * BLOCK_SIZE), new Point(9 * BLOCK_SIZE, 8 * BLOCK_SIZE),
            new Point(10 * BLOCK_SIZE, 8 * BLOCK_SIZE) };

    public static final int NUMBER_OF_GHOST = 4;
    public static final int PAC_START_LIFE = 3;

    public static Image[] GHOST_IMAGES;
    public static Image MAINPAGE;
    public static Image PACMAN_SUPER;;
    public static Image PACMAN_NORMAL;
    public static Image GHOST_IMAGE_1;
    public static Image GHOST_IMAGE_2;
    public static Image GHOST_IMAGE_3;
    public static Image GHOST_IMAGE_4;

    /**
     * Konstruktor für die Constante-Klasse. Lädt die Bilder für das Spiel.
     */
    public Constante() {
        try {

            MAINPAGE = ImageIO.read(Constante.class.getClassLoader().getResource("pics/Hauptmenue.png"));
            PACMAN_SUPER = ImageIO.read(Constante.class.getClassLoader().getResource("pics/SuperHackMan.png"));
            PACMAN_NORMAL = ImageIO.read(Constante.class.getClassLoader().getResource("pics/HackMan.png"));

            GHOST_IMAGES = new Image[] {
                    GHOST_IMAGE_1 = ImageIO.read(Constante.class.getClassLoader().getResource("pics/Virus.png")),
                    GHOST_IMAGE_2 = ImageIO.read(Constante.class.getClassLoader().getResource("pics/Virus2.png")),
                    GHOST_IMAGE_3 = ImageIO.read(Constante.class.getClassLoader().getResource("pics/Virus3.png")),
                    GHOST_IMAGE_4 = ImageIO.read(Constante.class.getClassLoader().getResource("pics/Virus4.png")) };

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Arrays für die X-Koordinaten der speziellen Gome-Positionen.
     */
    public static int[][] xs = { { 16, 16, 8, 0, 0, 2, 4, 10 }, { 15, 15, 8, 1, 1, 5, 6, 9 } };

    /**
     * Arrays für die Y-Koordinaten der speziellen Gome-Positionen.
     */
    public static int[][] ys = { { 5, 7, 4, 1, 19, 6, 17, 18 }, { 5, 7, 2, 1, 18, 9, 14, 18 } };

    /**
     * Arrays für die Gome-Werte an den speziellen Positionen.
     */
    public static int[][] vs = { { 3, 4, 3, 3, 2, 3, 2, 3 }, { 3, 4, 3, 3, 2, 3, 2, 3 } };

    /**
     * 2D-Array für die Grundkarte des Spielfelds.
     */
    public static int[][] blocksMap = { { 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0 },
            { 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0 },
            { 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0 },
            { 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0 },
            { 1, 0, 0, 0, 0, 0, 0, 1, 1, 3, 3, 1, 1, 0, 0, 0, 1, 0, 0, 1 },
            { 1, 1, 0, 0, 0, 0, 0, 1, -1, -1, -1, -1, 1, 0, 0, 0, 0, 0, 1, 1 },
            { 0, 0, 0, 1, 0, 1, 0, 1, -1, -1, -1, -1, 1, 0, 1, 1, 1, 0, 0, 0 },
            { 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1 },
            { 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0 },
            { 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0 },
            { 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, -1, 0, 1, 0 },
            { 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 }

    };

    /**
     * 2D-Array für die geklonte Grundkarte des Spielfelds.
     */
    public static int[][] blocksMap0 = { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1 },
            { 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
            { 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1 },
            { 1, 0, 0, 0, 1, 0, 0, 1, 1, 3, 3, 1, 1, 0, 0, 0, 1, 0, 0, 1 },
            { 1, 0, 1, 0, 0, 0, 0, 1, -1, -1, -1, -1, 1, 0, 1, 0, 0, 0, 0, 1 },
            { 0, 0, 0, 0, 1, 1, 0, 1, -1, -1, -1, -1, 1, 0, 1, 1, 0, 0, 0, 0 },
            { 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
            { 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, -1, 0, 0, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }

    };

    /**
     * 
     */
    public static int[][][] blocksMaps = { blocksMap, blocksMap0 };

    /**
     * Erzeugt eine spezielle Gome-Map basierend auf dem angegebenen Index.
     * 
     * @param index Der Index der gewünschten Map.
     * @return Eine zweidimensionale Matrix, die die Gome-Map darstellt.
     */
    public static int[][] buildGomeMap(int index) {
        int[][] map = Utils.clone2DMatrix(blocksMaps[index]);

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = (map[i][j] + 1) % 2;
            }
        }
        // Add specials gomes
        addSpecialGomes(index, map);

        return map;
    }

    /**
     * Fügt spezielle Gomes basierend auf dem angegebenen Index in die Map ein.
     * 
     * @param index Der Index der speziellen Gomes.
     * @param map   Die zu bearbeitende Map.
     */
    public static void addSpecialGomes(int index, int[][] map) {
        for (int i = 0; i < vs[0].length; i++) {
            map[xs[index][i]][ys[index][i]] = vs[index][i];
        }
    }

}