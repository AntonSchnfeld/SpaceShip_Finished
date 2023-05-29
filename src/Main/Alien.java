package Main;

import javax.swing.*;

public class Alien extends Entity
{
    public Alien (int x , int y)
    {
        super(x , y);
        spriteURL = ClassLoader.getSystemResource("Alien.png");
        sprite = new ImageIcon(spriteURL).getImage();
        width = sprite.getWidth(null);
        height = sprite.getHeight(null);
        SPEED = -3;
    }
}
