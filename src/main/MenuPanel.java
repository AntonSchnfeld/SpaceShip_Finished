package main;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel
{
    private final JButton startGame;
    private final Image img;

    MenuPanel ()
    {
        img = new ImageIcon("src/images/Menu.png").getImage();

        Font title = new Font(Font.DIALOG, Font.BOLD, 40);
        Font font = new Font(Font.DIALOG, Font.PLAIN, 30);

        startGame = new JButton ( "START GAME " );
        startGame.setAlignmentX( JButton.CENTER_ALIGNMENT );

        JLabel titleLabel = new JLabel("SPACESHIP");
        titleLabel.setAlignmentX( JLabel.CENTER_ALIGNMENT );

        titleLabel.setFont(title);
        startGame.setFont(font);

        startGame.setBackground(new Color(31, 5, 16));
        startGame.setForeground( Color.WHITE );
        titleLabel.setForeground( Color.WHITE );

        titleLabel.setPreferredSize( new Dimension ( ( int ) ( GameFrame.GAME_WIDTH * 0.75 ), 100 ) );
        startGame.setPreferredSize( new Dimension( ( int ) ( GameFrame.GAME_WIDTH * 0.65 ) , 50 ) );

        setLayout ( new BoxLayout ( this , BoxLayout.Y_AXIS ) );
        setPreferredSize(new Dimension(400, 400));
        setBackground( Color.BLACK );
        add (titleLabel);
        add ( startGame );
    }

    @Override
    public void paintComponent (Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }

    public JButton getStartGame ()
    {
        return startGame;
    }
}
