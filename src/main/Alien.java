package main;

import javax.swing.*;

public class Alien extends Entity
{
    public Alien (int x , int y)
    {
        super(x , y);
        sprite = new ImageIcon("src/images/alien.png").getImage();
        width = sprite.getWidth(null);
        height = sprite.getHeight(null);
        SPEED = -3;
    }
}
