package Main;

import javax.swing.*;

public class Star extends Entity
{
    Star ( int x , int y )
    {
        super( x , y );
        spriteURL = ClassLoader.getSystemResource("star.png");
        sprite = new ImageIcon(spriteURL).getImage();
        SPEED = -7;
    }
}

