package main;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SpaceShip extends Entity
{
    private final CopyOnWriteArrayList<Missile> missiles;
    SpaceShip ()
    {
        super( 60 , 40 );
        SPEED = 5;

        missiles = new CopyOnWriteArrayList<>();

        ImageIcon icon = new ImageIcon( "src/images/newplayer.gif" );
        sprite = icon.getImage();

        width = sprite.getWidth( null );
        height = sprite.getHeight( null );
    }

    public void move ()
    {
        if ( ( y + directionY ) < GameFrame.GAME_HEIGHT && ( y + directionY ) > 0 )
        {
            y += directionY;
        }
    }

    public void resetPosition ()
    {
        x = 60;
        y = 40;
    }

    public void shootRocket ()
    {
        missiles.add( new Missile( x + 47 , y + 15 ) );
    }

    public List<Missile> getMissiles ()
    {
        return  missiles;
    }

    public void keyPressed ( KeyEvent e )
    {
        int key = e.getKeyCode();

        if( key == KeyEvent.VK_W )
        {
            directionY = -SPEED;
        }

        if( key == KeyEvent.VK_S )
        {
            directionY = SPEED;
        }

        if( key == KeyEvent.VK_SPACE )
        {
            shootRocket();
        }
    }

    public void keyReleased ( KeyEvent e )
    {
        int key = e.getKeyCode();

        if( key == KeyEvent.VK_W )
        {
            directionY = 0;
        }

        if( key == KeyEvent.VK_S )
        {
            directionY = 0;
        }
    }
}
