package sound;

import javax.sound.sampled.*;
import javax.swing.Timer;

import java.io.IOException;
import java.net.URL;

/**
 * Verwaltet die Wiedergabe von verschiedenen Soundeffekten im Spiel.
 * 
 * Sounds: anfangs, gameWon, gameLost, pacmanDeath, superState.
 * 
 * Zuständig für das Laden, Abspielen und Stoppen der Soundeffekte. Enthält
 * Timer für den SuperState-Sound.
 * 
 * @author Asus
 */
public class SoundManager {
    private Clip anfangsClip;
    private Clip gameWonClip;
    private Clip gameLostClip;
    private Clip pacmanDeathClip;
    private Clip superStateClip;

    // Mute-Status für verschiedene Sounds
    boolean isAnfangMuted = false;
    boolean isGameWonMuted = false;
    boolean isGameLostMuted = false;
    boolean isPacmanDeathMuted = false;
    boolean isSuperStateMuted = false;

    private Timer superStateTimer;

    /**
     * Konstruktor für den SoundManager. Lädt die Sounds und initialisiert Timer.
     */
    public SoundManager() {
        loadSounds();
        initTimers();
    }

    /**
     * Lädt die Sounds aus den Ressourcen.
     */
    private void loadSounds() {
        anfangsClip = loadClip("/sound/pixelGroove.wav");
        gameWonClip = loadClip("/sound/gameWon.wav");
        gameLostClip = loadClip("/sound/gameLost.wav");
        pacmanDeathClip = loadClip("/sound/pacmanDeath.wav");
        superStateClip = loadClip("/sound/stateSuper.wav");
    }

    /**
     * Lädt einen Soundclip aus der Ressource.
     * 
     * @param soundFilename Der Dateipfad zur Soundressource.
     * @return Der geladene Clip oder null, wenn die Ressource nicht gefunden wurde.
     */
    private Clip loadClip(String soundFilename) {
        try {
            URL url = getClass().getResource(soundFilename);
            if (url == null) {
                System.err.println("Ressource not found: " + soundFilename);
                return null;
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Spielt den Anfangssound ab, wenn nicht stummgeschaltet.
     */
    public void playAnfangsSound() {
        if (!isAnfangMuted && anfangsClip != null) {
            playClip(anfangsClip);
        }
    }

    /**
     * Stoppt den Anfangssound, falls er gerade abgespielt wird.
     */
    public void stopAnfangsSound() {
        if (anfangsClip != null && anfangsClip.isRunning()) {
            anfangsClip.stop();
        }
    }

    /**
     * Spielt den "Game Won" Sound ab, wenn nicht stummgeschaltet.
     */
    public void playGameWonSound() {
        if (!isGameWonMuted && gameWonClip != null) {
            playClip(gameWonClip);
        }
    }

    /**
     * Spielt den "Game Lost" Sound ab, wenn nicht stummgeschaltet.
     */
    public void playGameLostSound() {
        if (!isGameLostMuted && gameLostClip != null) {
            playClip(gameLostClip);
        }
    }

    /**
     * Spielt den "Pacman Death" Sound ab, wenn nicht stummgeschaltet.
     */
    public void playPacmanDeathSound() {
        if (!isPacmanDeathMuted && pacmanDeathClip != null) {
            playClip(pacmanDeathClip);
        }
    }

    /**
     * Initialisiert Timer für den SuperState-Sound.
     */
    private void initTimers() {
        superStateTimer = new Timer(7000, e -> stopSuperStateSound());
    }

    /**
     * Spielt den SuperState-Sound ab, wenn nicht stummgeschaltet. Startet den
     * Timer, um den Sound nach einer festgelegten Zeit zu stoppen.
     */
    public void playSuperStateSound() {
        if (!isSuperStateMuted && superStateClip != null) {
            superStateClip.setFramePosition(0); // Startposition auf den Anfang setzen
            superStateClip.start();
            superStateTimer.restart(); // Hier habe ich einen Restart eingefügt, damit die Musik immer wenn eine neue Kugel aufgenommen wird, neustartet
        }
    }

    /**
     * Stoppt den SuperState-Sound, falls er gerade abgespielt wird. Stoppt auch den
     * Timer.
     */
    public void stopSuperStateSound() {
        if (superStateClip != null && superStateClip.isRunning()) {
            superStateClip.stop();
            superStateTimer.stop();
        }
    }

    /**
     * Spielt einen Clip ab, indem er zur Startposition zurückspult und ihn startet.
     * 
     * @param clip Der abzuspielende Clip.
     */
    private void playClip(Clip clip) {
        clip.setFramePosition(0); // Zurückspulen auf den Anfang
        clip.start();
    }
}