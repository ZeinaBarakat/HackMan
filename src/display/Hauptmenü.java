package display;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import constantes.Constante;
import sound.SoundManager;

/**
 * Die Hauptmenü-Klasse repräsentiert das Hauptmenü des Spiels, in dem der
 * Benutzer zwischen dem Spielstart und dem Anzeigen der Anleitung wählen kann.
 * 
 * @author Asus
 */
public class Hauptmenü extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    Constante constante = new Constante();
    private JButton myButton1;
    private JButton myButton2;
    private SoundManager soundManager;
    private Timer soundTimer;

    /**
     * Konstruktor für die Hauptmenü-Klasse.
     */
    public Hauptmenü() {
        soundManager = new SoundManager();
        soundManager.playAnfangsSound();

        int soundDelay = 5000;
        soundTimer = new Timer(soundDelay, e -> soundManager.playAnfangsSound());
        soundTimer.start();

        myButton1 = new JButton("Start");
        myButton2 = new JButton("Anleitung");
        myButton1.setBounds(410, 260, 200, 50);
        myButton1.setFocusable(false);
        myButton1.addActionListener(this);

        myButton2.setBounds(410, 370, 200, 50);
        myButton2.setFocusable(false);
        myButton2.addActionListener(this);

        JPanel panel = new JPanel() {

            private static final long serialVersionUID = 1L;

            /**
             * Zeichnet das Hintergrundbild des Hauptmenüs.
             */
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image image = Constante.MAINPAGE;
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        panel.setLayout(null);
        panel.add(myButton1);
        panel.add(myButton2);

        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1020, 630);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    /**
     * Reagiert auf Aktionen im Hauptmenü (z. B. Button-Klicks).
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == myButton1) {
            soundTimer.stop();
            soundManager.stopAnfangsSound();
            SwingUtilities.invokeLater(() -> {
                dispose();
                startPacmanGame();
            });
        }

        if (e.getSource() == myButton2) {
            soundTimer.stop();
            soundManager.stopAnfangsSound();

            SwingUtilities.invokeLater(() -> {
                dispose();
                @SuppressWarnings("unused")
                Anleitung anleitung = new Anleitung();
            });
        }
    }

    /**
     * Startet das Pacman-Spiel in einem separaten Thread.
     */
    private void startPacmanGame() {
        new Thread(() -> {
            PacmanGame game = new PacmanGame();
            game.play();
        }).start();
    }

    /**
     * Hauptmethode, um die Anwendung zu starten.
     * 
     * @param args Kommandozeilenargumente (nicht verwendet)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Hauptmenü());
    }

}