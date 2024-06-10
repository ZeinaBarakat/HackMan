package utilities;

import java.awt.Dimension;
import java.awt.Point;

import constantes.Constante;

/**
 * 
 * @author Asus
 *
 */
public class Utils {

    /**
     * Erzeugt eine Kopie einer zweidimensionalen Matrix.
     * 
     * @param mat Die zu klonende Matrix.
     * @return Eine Kopie der übergebenen Matrix.
     */
    public static int[][] clone2DMatrix(int[][] mat) {
        int x = mat.length;
        int y = mat[0].length;
        int[][] clone = new int[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                clone[i][j] = mat[i][j];
            }
        }
        return clone;
    }

    /**
     * Erzeugt eine Kopie eines Point-Objekts.
     * 
     * @param p Der zu klonende Point.
     * @return Eine Kopie des übergebenen Points.
     */
    public static Point clonePoint(Point p) {
        return new Point(p.x, p.y);
    }

    /**
     * Erzeugt eine Dimension mit modifizierten Breite und Höhe.
     * 
     * @param d die ursprüngliche Dimension
     * @param w die Breitenänderung
     * @param h die Höhenänderung
     * @return eine modifizierte Dimension
     */
    public static Dimension cloneDimension(Dimension d, int w, int h) {
        return new Dimension(d.width + w, d.height + h);
    }

    /**
     * Zählt, wie viele Kugeln in der Matrix vorhanden sind.
     * 
     * @param mat Die Matrix, die analysiert wird.
     * @return Die Anzahl der Kugeln in der Matrix.
     */
    public static int howMuchBaseGomes(int[][] mat) {
        int x = mat.length;
        int y = mat[0].length;
        int cnt = 0;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (mat[i][j] == 1)
                    cnt++;
            }
        }

        return cnt;
    }

    /**
     * Erzeugt eine neue Matrix für eine neue Karte mit gegebenen Gomes/Kugeln.
     *
     * @param mat    Die ursprüngliche Matrix.
     * @param gom    Die Gomes für die neue Karte.
     * @param cnt    Die Anzahl der zu platzierenden Gomes.
     * @param index  Der Index für die x- und y-Koordinaten der neuen Gomes.
     * @param index1 Der Index für die x- und y-Koordinaten der vorhandenen Gomes.
     * @return Die neu generierte Matrix für die neue Karte.
     */
    public static int[][] makeGomesForNewMap(int[][] mat, int[][] gom, int cnt, int index, int index1) {
        int x = mat.length;
        int y = mat[0].length;
        int[][] clone = new int[x][y];

        for (int i = 0; i < x & cnt > 0; i++) {
            for (int j = 0; j < y & cnt > 0; j++) {

                if (mat[i][j] == 0) {
                    clone[i][j] = 1;
                    cnt--;
                }
            }
        }

        int[][] xs = Constante.xs, ys = Constante.ys, vs = Constante.vs;

        // Constante.addSpecialGomes(index, map);
        for (int i = 0; i < vs[0].length; i++) {
            clone[xs[index][i]][ys[index][i]] = gom[xs[index1][i]][ys[index1][i]];
            // Constante.addSpecialGomes(index, clone);
        }

        return clone;
    }

}