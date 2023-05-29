package Main;

import javax.swing.*;

public class Missile extends Entity
{
    Missile ( int x , int y )
    {
        super( x , y );
        spriteURL = ClassLoader.getSystemResource("Missile.png");
        sprite = new ImageIcon(spriteURL).getImage();
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
        visible = false;
        return new Explosion(x, (int) (y + height * -0.5), -SPEED);
    }
}
