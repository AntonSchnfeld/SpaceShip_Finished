package Main;

import javax.swing.*;

public class Explosion extends Entity
{
    private int duration;
    Explosion (int x, int y, int SPEED)
    {
        super (x, y);
        this.SPEED = SPEED;
        spriteURL = ClassLoader.getSystemResource("Explosion.gif");
        sprite = new ImageIcon(spriteURL).getImage();
        duration = 0;
    }

    @Override
    public void move ()
    {
        super.move();
        duration++;
        if(duration > 7)
        {
            visible = false;
        }
    }
}