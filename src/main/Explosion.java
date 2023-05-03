package main;

import javax.swing.*;

public class Explosion extends Entity
{
    private int duration;
    Explosion (int x, int y, int SPEED)
    {
        super (x, y);
        this.SPEED = SPEED;
        sprite = new ImageIcon("src/images/Explosion.gif").getImage();
        duration = 0;
    }

    @Override
    public void move ()
    {
        super.move();
        duration++;
        if(duration > 3)
        {
            visible = false;
        }
    }
}
