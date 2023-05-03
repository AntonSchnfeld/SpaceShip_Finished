package main;

import javax.swing.*;

public class Meteor extends Entity
{
    Meteor ( int x , int y )
    {
        super( x , y );
        sprite = new ImageIcon("src/images/Meteorite.png").getImage();
        SPEED = -5;
        width = sprite.getWidth(null);
        height = sprite.getHeight(null);
    }
}
