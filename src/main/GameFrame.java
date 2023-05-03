package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements Runnable, ActionListener
{
    private Thread thread;
    public static final int GAME_WIDTH = 900;
    public static final int GAME_HEIGHT = 600;
    public static final Dimension GAME_DIMENSION = new Dimension(GAME_WIDTH , GAME_HEIGHT);
    private Board board;
    private final MenuPanel menuPanel;

    // Make Frame
    public GameFrame ()
    {
        board = new Board(this);
        menuPanel = new MenuPanel();
        thread = new Thread(this);

        setTitle("main.SpaceShip");
        ImageIcon icon = new ImageIcon("src/images/alien.png");
        setIconImage(icon.getImage());
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        menuPanel();
        setVisible(true);

        menuPanel.getStartGame().addActionListener(this);
    }

    public void menuPanel ()
    {
        board.stop();
        remove(board);
        board = new Board(this);
        add(menuPanel);
        pack();
        setBackground(Color.BLACK);
        setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()*0.35 ),
                (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()*0.25));
    }

    public void startGame ()
    {
        board.stop();
        remove(menuPanel);
        board = new Board(this);
        board.getTimer().start();
        add(board);

        thread = new Thread(this);
        thread.start();
        board.requestFocus();
        pack();
        setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()*0.2),
                (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()*0.1));
    }

    @Override
    public void run ()
    {
        board.requestFocus();
        int FPS_SET = 1_000_000;
        double timePerFrame = (double) 1_000_000_000 / FPS_SET;
        long lastFrame = System.nanoTime();
        while (board.isRunning()) {
            long now = System.nanoTime();
            if (System.nanoTime() - lastFrame >= timePerFrame) {
                board.repaint();
                lastFrame = now;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ( e.getSource() == menuPanel.getStartGame() )
        {
            startGame();
        }
    }
}
