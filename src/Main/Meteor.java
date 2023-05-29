package Main;

import javax.swing.*;

public class Meteor extends Entity
{
    Meteor ( int x , int y )
    {
        super( x , y );
        spriteURL = ClassLoader.getSystemResource("Meteorite.png");
        sprite = new ImageIcon(spriteURL).getImage();
        SPEED = -5;
        width = sprite.getWidth(null);
        height = sprite.getHeight(null);
    }
}
