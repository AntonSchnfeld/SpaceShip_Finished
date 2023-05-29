package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

// This class is where the game is displayed
public class Board extends JPanel implements ActionListener
{
    private SoundManager soundManager;
    private final GameFrame parent;
    private int kills = 0;
    private final JLabel highScore;
    private int frames = 0;
    private long lastCheck = 0;
    private SpaceShip spaceShip;
    private final java.util.List<Alien> aliens;
    private final java.util.List<Meteor> meteors;
    private final java.util.List<Star> stars;
    private final java.util.List<Explosion> explosions;
    private int spawnCount = 0;
    private boolean isRunning = true;
    private final Timer timer;

    public Board(GameFrame parent) {
        this.parent = parent;

        soundManager = new SoundManager();

        int DELAY = 1;
        timer = new Timer(DELAY, this);

        highScore = new JLabel();

        spaceShip = new SpaceShip();

        highScore.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        highScore.setForeground(Color.WHITE);

        setSize(GameFrame.GAME_WIDTH, GameFrame.GAME_HEIGHT);
        setFocusable(true);
        addKeyListener(new TAdapter());
        setPreferredSize(GameFrame.GAME_DIMENSION);
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        add(highScore, BorderLayout.PAGE_START);

        explosions = new CopyOnWriteArrayList<>();
        aliens = new CopyOnWriteArrayList<>();
        meteors = new CopyOnWriteArrayList<>();
        stars = new CopyOnWriteArrayList<>();
    }

    @Override
    public void paintComponent(Graphics g) {
        frames++;
        if (System.currentTimeMillis() - lastCheck >= 1000) {
            lastCheck = System.currentTimeMillis();
            System.out.println("FPS: " + frames);
            frames = 0;
        }
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }

    public boolean isRunning ()
    {
        return isRunning;
    }

    public void doDrawing(Graphics g) {
        if (isRunning) {
            Graphics2D graphics2D = (Graphics2D) g;

            highScore.setText(String.valueOf(kills));

            for (Star star : stars) {
                if (star.isVisible()) {
                    graphics2D.drawImage(star.getSprite(), star.getX(), star.getY(), this);
                }
            }

            if (spaceShip.isVisible()) {
                graphics2D.drawImage(spaceShip.getSprite(), spaceShip.getX(), spaceShip.getY(), this);
            }

            for (Alien alien : aliens) {
                if (alien.isVisible()) {
                    graphics2D.drawImage(alien.getSprite(), alien.getX(), alien.getY(), this);
                }

            }

            for (Missile missile : spaceShip.getMissiles()) {
                if (missile.isVisible()) {
                    graphics2D.drawImage(missile.getSprite(), missile.getX(), missile.getY(), this);
                }
            }

            for (Meteor meteor : meteors) {
                if (meteor.isVisible()) {
                    graphics2D.drawImage(meteor.getSprite(), meteor.getX(), meteor.getY(), this);
                }
            }

            for (Explosion explosion : explosions)
            {
                if (explosion.isVisible())
                {
                    graphics2D.drawImage(explosion.getSprite(), explosion.getX(), explosion.getY(), this);
                }
            }
        } else {
            gameLost();
        }
    }

    public void gameLost() {
        timer.stop();
        stop();
        parent.menuPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // This method is basically the game loop method
        if (isRunning) {
            spaceShip.move();
            java.util.List<Missile> missiles = spaceShip.getMissiles();

            for (Explosion explosion : explosions)
            {
                if (explosion.isVisible())
                {
                    explosion.move();
                }
                else
                {
                    explosions.remove(explosion);
                }
            }

            for (Missile missile : missiles) {
                if (missile.isVisible()) {
                    missile.move();
                } else {
                    spaceShip.getMissiles().remove(missile);
                }
            }

            for (Alien alien : aliens) {
                if (alien.isVisible()) {
                    alien.move();
                } else {
                    aliens.remove(alien);
                }
            }

            for (Meteor meteor : meteors) {
                if (meteor.isVisible()) {
                    meteor.move();
                } else {
                    meteors.remove(meteor);
                }
            }

            for (Star star : stars) {
                if (star.isVisible()) {
                    star.move();
                } else {
                    stars.remove(star);
                }
            }

            collisionCheck();

            if (spawnCount == 25)
            {
                spawnEntities();
            } else spawnCount++;
        }
    }

    public void spawnEntities() {
        Random ran = new Random();
        int ranInt = ran.nextInt(5);

        switch (ranInt) {
            case 0, 2, 3 -> {
                Meteor meteor = new Meteor(GameFrame.GAME_WIDTH, ran.nextInt(GameFrame.GAME_HEIGHT) + 1);
                meteors.add(meteor);
            }
            case 1, 4 -> {
                Alien newAlien = new Alien(GameFrame.GAME_WIDTH, ran.nextInt(GameFrame.GAME_HEIGHT) + 1);
                aliens.add(newAlien);
            }
        }

        for (int i = 0; i < ran.nextInt(3); i++) {
            stars.add(new Star(GameFrame.GAME_WIDTH, ran.nextInt(GameFrame.GAME_HEIGHT) + 1));
        }

        spawnCount = 0;
    }

    public int getHighScore ()
    {
        return kills;
    }

    private void collisionCheck()
    {
        // Spaceship collision logic
        for (Meteor meteor : meteors) {
            if (meteor.getCollision().intersects(spaceShip.getCollision()) && meteor.isVisible() && spaceShip.isVisible()) {
                explosions.add(spaceShip.explode());
                soundManager.playExplosionSound();
                isRunning = false;
            }
        }

        for (Alien alien : aliens) {
            if (alien.getCollision().intersects(spaceShip.getCollision()) && alien.isVisible() && spaceShip.isVisible()) {
                explosions.add(spaceShip.explode());
                explosions.add(alien.explode());
                soundManager.playExplosionSound();
                isRunning = false;
            }
        }

        // Main.Missile collision logic
        for (Missile missile : spaceShip.getMissiles()) {
            for (Alien alien : aliens) {
                // Check for collision and set colliding rockets and aliens to !visible
                if (missile.getCollision().intersects(alien.getCollision()) && alien.isVisible() && missile.isVisible()) {
                    explosions.add(aliens.get(aliens.indexOf(alien)).explode());
                    spaceShip.getMissiles().get(spaceShip.getMissiles().indexOf(missile)).explode();
                    soundManager.playExplosionSound();
                    kills++;
                }
            }

            for (Meteor meteor : meteors) {
                if (missile.getCollision().intersects(meteor.getCollision()) && meteor.isVisible() && missile.isVisible()) {
                    explosions.add(missile.explode());
                    soundManager.playExplosionSound();
                }
            }
        }
    }

    public Timer getTimer() {
        return timer;
    }

    public void stop() {
        timer.stop();
        for (int i = 0; i < stars.size(); i++) {
            stars.remove(i);
        }
        for (int i = 0; i < meteors.size(); i++) {
            meteors.remove(i);
        }
        for (int i = 0; i < aliens.size(); i++) {
            aliens.remove(i);
        }
        spaceShip = new SpaceShip();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            spaceShip.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            spaceShip.keyPressed(e);
        }
    }

}