import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * Write a description of class Ship here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ship extends Actor
{
    private boolean set;
    private int size;
   
    //defult constructer
    /**
     * Ship is the default constructor for objects of type ship
     * 
     * @param There are no parameters
     * @return an object of type Ship
     */
    public Ship()
    {
        GreenfootImage shipImage = new GreenfootImage( 72, 72 );
        shipImage.setColor(Color.GRAY);
        shipImage.fillRect( 0, 0, shipImage.getWidth(), shipImage.getHeight() );
        setImage( shipImage );
        
        set = false;
    }
    
    /**
     * Ship is the constructor for objects of type Ship
     * 
     * @param s is the size of the ship
     * @return an object of type ship
     */
    public Ship( int s )
    {
        size = s;
        
        GreenfootImage shipImage = new GreenfootImage( 72 * size, 72 );
        shipImage.setColor( Color.GRAY );
        shipImage.fillRect( 0, 0, shipImage.getWidth(), shipImage.getHeight() );
        setImage( shipImage );
        
        set = false;
    }
    
    /**
     * Act - do whatever the Ship wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        
        if( Greenfoot.mouseClicked( this ) )
        {
            set = true;
            
            checkSetting();
        }
        
        if( mouse != null && set == false )
        {
            setLocation( mouse.getX(), mouse.getY() );
        }
    }    
    
    /**
     * getSize will return the size of the ship
     * 
     * @param There are no parameters
     * @return an int representing the size of the ship
     */
    public int getSize()
    {
       return size; 
    }
    
    /**
     * checkSetting ensures the ships are placed in the correct location
     * due to the fact that the world uses cells 1x1 pixels
     * 
     * @param There are no parametres
     * @return Nothing will be returnes
     */
    private void checkSetting()
    {
        //ship is horizontal
        if( getRotation() == 0 || getRotation() == 180 )
        {
            //amount of blocks, divided by 2
            //even length
            if( ( getImage().getWidth() / 72) % 2 == 0 )
            {
                //146 / 2 = 2....,, 2 * 72 = 144
                //middle of ship on middle line
                setLocation( getX() / 72 * 72, getY() / 72 * 72 + 36);
            }
            //odd length
            else
            {
               setLocation( getX() / 72 * 72 + 36, getY() / 72 * 72 + 36); 
            }
        }
        //ship is vertical
        else
        {
            //even
            if( ( getImage().getWidth() / 72) % 2 == 0 )
            {
                setLocation( getX() / 72 * 72 + 36, getY() / 72 * 72);
            }
            //odd
            else
            {
               setLocation( getX() / 72 * 72 + 36, getY() / 72 * 72 + 36); 
            }
        }
    }
}
