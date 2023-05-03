package main;

import javax.swing.*;

public class Star extends Entity
{
    Star ( int x , int y )
    {
        super( x , y );
        sprite = new ImageIcon("src/images/star.png").getImage();
        SPEED = -7;
    }
}
