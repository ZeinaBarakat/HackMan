import javax.swing.SwingUtilities;

import display.Hauptmenü;

/**
 * Die Hauptklasse, die den Einstiegspunkt in die Anwendung darstellt. Hier wird
 * das Hauptmenü gestartet.
 * 
 * @author Asus
 */
public class Main {
    /**
     * Die Hauptmethode, die beim Start der Anwendung aufgerufen wird.
     * 
     * @param args Die Befehlszeilenargumente (nicht verwendet in diesem Beispiel).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Hauptmenü());
    }
}
