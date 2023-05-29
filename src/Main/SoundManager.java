package Main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class SoundManager
{
    private final File explosionMusic;
    private final Clip menuClip;
    private final Clip gameClip;

    public SoundManager ()
    {
        File menuMusic;
        File gameMusic;
        try
        {
            menuMusic = new File(ClassLoader.getSystemResource("MenuMusic.wav").toURI());
            gameMusic = new File(ClassLoader.getSystemResource("GameMusic.wav").toURI());
            explosionMusic = new File(ClassLoader.getSystemResource("Explosion.wav").toURI());
        }
        catch (URISyntaxException e)
        {
            throw new RuntimeException("Sound Files could not be loaded");
        }

        try
        {
            AudioInputStream explosionStream = AudioSystem.getAudioInputStream(explosionMusic);
            Clip explosionClip = AudioSystem.getClip();
            explosionClip.open(explosionStream);
            menuClip = AudioSystem.getClip();
            AudioInputStream menuStream = AudioSystem.getAudioInputStream(menuMusic);
            menuClip.open(menuStream);
            gameClip = AudioSystem.getClip();
            AudioInputStream gameStream = AudioSystem.getAudioInputStream(gameMusic);
            gameClip.open(gameStream);
        }
        catch (LineUnavailableException | UnsupportedAudioFileException | IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void playExplosionSound ()
    {
        new Thread(() -> {
            Clip clip;
            try {
                clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(explosionMusic));
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                throw new RuntimeException(e);
            }
            clip.start();
        }).start();
    }

    public void playMenuMusic ()
    {
        new Thread(menuClip::start).start();
    }

    public void stopMenuMusic ()
    {
        new Thread(menuClip::stop).start();
    }

    public void playGameMusic ()
    {
        new Thread(gameClip::start).start();
    }

    public void stopGameMusic ()
    {
        new Thread(gameClip::stop).start();
    }
}
