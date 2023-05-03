package main;

import javax.swing.*;

public class Missile extends Entity
{
    Missile ( int x , int y )
    {
        super( x , y );
        sprite = new ImageIcon( "src/Images/missile.png" ).getImage();
        SPEED = 8;
        width = sprite.getWidth( null );
        height = sprite.getHeight( null );
    }

    @Override
    public void move ()
    {
        directionX = SPEED;
        x += directionX;
        if( x > GameFrame.GAME_WIDTH )
        {
            setVisible( false );
        }
    }

    @Override
    public Explosion explode ()
    {
        SPEED *= -1;
        return super.explode();
    }
}
