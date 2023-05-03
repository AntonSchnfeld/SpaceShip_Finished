package main;

import java.awt.*;

public class Entity
{
    transient Image sprite;
    transient int x , y , directionX , directionY;
    transient int width , height;
    transient boolean visible;
    transient int SPEED;

    Entity ( int x , int y )
    {
        this.x = x;
        this.y = y;
        visible = true;
    }

    public Explosion explode ()
    {
        visible = false;
        return new Explosion(x, y, SPEED);
    }

    public Rectangle getCollision ()
    {
        return new Rectangle( x , y , width , height );
    }

    public void move ()
    {
        directionX = SPEED;
        x += directionX;

        if ( x < -100 )
        {
            setVisible(false);
        }
    }

    public int getX ()
    {
        return x;
    }

    public Image getSprite ()
    {
        return sprite;
    }

    public int getY ()
    {
        return y;
    }

    public boolean isVisible ()
    {
        return visible;
    }

    public void setVisible ( boolean visible )
    {
        this.visible = visible;
    }

}
