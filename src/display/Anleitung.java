package display;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Die Anleitung-Klasse repräsentiert das Fenster, das dem Benutzer die
 * Spielanleitung zeigt.
 * 
 * @author Asus
 */
public class Anleitung extends JFrame {
    private static final long serialVersionUID = 1L;

    /**
     * Konstruktor für die Anleitung-Klasse.
     */
    public Anleitung() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        // Text für die Spielanleitung
        String anleitungText = "Willkommen in unserem aufregenden Spiel! Begib dich auf das Abenteuer mit unserer Hauptfigur, dem legendären 'Hack Man'. Deine Mission ist es, durch ein komplexes Labyrinth zu navigieren und dabei kleine blaue Kugeln zu sammeln. Aber sei gewarnt – gefährliche Viren streifen durch das Labyrinth und versuchen, Hack Man zu fangen. Jedes Mal, wenn sie es schaffen, verliert er ein Leben!\n\n"
                + "Aber keine Sorge, es gibt magische Superkugeln! Wenn Hack Man eine gelbe Kugel einsammelt, hat er die Macht, die Geister für eine unbestimmte Zeit zurück ins Gefängnis zu schicken. Aber sei auf der Hut – die Viren kehren immer wieder aus ihrer Virus-Zone zurück!\n\n"
                + "Zusätzlich gibt es pinke Kugeln, die Hack Man kurzzeitig unsichtbar machen. Während dieser Zeit können ihn die Viren nicht erkennen. Es verbirgt sich auch noch eine besondere Kugel im Labyrinth! Entdecke selbst, welche Geheimtür sie öffnen wird.\n\n"
                + "Deine Mission ist klar: Sammle alle kleinen Kugeln ein, bevor die Viren Hack Man dreimal fangen. Wenn dir das gelingt, bist du der Held des Labyrinths! Du hast drei Leben, aber hier kommt ein Tipp: Verdien dir ein zusätzliches Leben, indem du mehr als 5000 Punkte sammelst. Nutze die Pfeiltasten auf deiner Tastatur, um Hack Man zu bewegen.\n\n"
                + "Viel Spaß beim Spielen, kleiner Hacker!  \nDeine Fähigkeiten werden auf die Probe gestellt, also sei bereit für die Herausforderung!";

        JTextArea anleitungTextArea = new JTextArea(anleitungText);
        anleitungTextArea.setWrapStyleWord(true);
        anleitungTextArea.setLineWrap(true);
        anleitungTextArea.setEditable(false);

        Font textFont = new Font("Arial", Font.PLAIN, 20);
        anleitungTextArea.setFont(textFont);

        // Scrollpane hinzufügen, damit Text scrollbar wird
        JScrollPane scrollPane = new JScrollPane(anleitungTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JButton backButton = new JButton("Zurück zum Hauptmenü");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Schließt das Anleitungsfenster
                @SuppressWarnings("unused")
                Hauptmenü hauptmenue = new Hauptmenü(); // Erzeugt ein neues Hauptmenüfenster
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        setContentPane(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}