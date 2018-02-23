import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import javax.swing.JOptionPane;
/**
 * Write a description of class PlayerWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PlayerWorld extends World
{
    private int[][] board = new int[10][10];
    
    private boolean firstTurn = true;
    private boolean playerOneTurn = true;
    private boolean messageShown = false;
    
    private int shipCounter = 0;
    
    private boolean addedToGrid = false;
    
    private Ship toAdd;
    
    private int refreshCounter = 10;
    /**
     * PlayerWorld Constructor for objects of class PlayerWorld
     * 
     * @param There are no parameters
     * @return objects of type PlayerWorld
     */
    public PlayerWorld()
    {    
        // Create a new world with 720x720 cells with a cell size of 1x1 pixels.
        super(720, 720, 1); 
        
        drawLines();
        
        //0 means ship has not been placed
        for( int r = 0; r < board.length; r++ )
        {
            for( int c = 0; c < board[r].length; c++ )
            {
                board[r][c] = 0;
            }
        }
        
    }
    
    /**
     * drawLines draws the 10x10 grid that we can place our ships inside
     * 
     * @param There are no parameters
     * @return Nothing will be returned
     */
    private void drawLines()
    {
        getBackground().setColor( Color.BLACK );
        
        //720 / 10 = 72,, sqr root 100 = 10
        for( int i = 72; i < getWidth(); i += 72 )
        {
            //verticle line, x doesn't change
            getBackground().drawLine( i, 0, i, getHeight() );
            getBackground().drawLine( 0, i, getWidth(), i );
        }
    }
    
    /**
     * act is the code that is run on every iteration of the acy cycle
     * 
     * @param There are no parameters
     * @return Nothing will be returned
     */
    public void act()
    {
        Ship currentShip;
        GreenfootImage transparentShip;
        
        displayTurn();
        
        if( firstTurn == true )
        {
            addShips();
        }
        else
        {
           for( int i = 0; i < getObjects(Ship.class).size(); i++ )
           {
               currentShip = getObjects(Ship.class).get(i);
               // . = of
               transparentShip = new GreenfootImage( currentShip.getImage().getWidth(), currentShip.getImage().getHeight() );
               currentShip.setImage( transparentShip );
           }
           Greenfoot.stop();
        }
    }
    
    /**
     * displayTurn shows various messages for the different stages of the game
     * 
     * @param There are no parameters
     * @return Nothing is returned
     */
    private void displayTurn()
    {
        if( firstTurn == true )
        {
            if( messageShown == false )
            {
                JOptionPane.showMessageDialog( null, "Player One, please select where your ships should be placed", "Please Place Ships", JOptionPane.PLAIN_MESSAGE );
                
                messageShown = true;
            }
        }
        else
        {
            if( messageShown == false )
            {
                JOptionPane.showMessageDialog( null, "Player Two, please select a spot to attack on the board...", "Attack Ships", JOptionPane.PLAIN_MESSAGE );
                
                messageShown = true;
            }
        }
    }
    
    private void addShips()
    {
        
        if( shipCounter >= 5 )
        {
            firstTurn = false;
            messageShown = false;
            fillBoard();
            displayBoard();
        }
        else
        {
            if( addedToGrid == false )
            {
                toAdd = checkShip();
                
                //Adds the ship at a default location because the code in Ship makes the Ship follow the mouse
                addObject( toAdd, 0, 0 );
                
                addedToGrid = true;
            }
            
            checkKeyPress( toAdd );
            
            if( Greenfoot.mouseClicked(null) )
            {
                shipCounter++;
                
                addedToGrid = false;
            }
        }
    }
    
    /**
     * checkShip checks what ship is to be added to the grid next
     * 
     * @param There are no parameters
     * @return the next ship that will be added to the world
     */
    private Ship checkShip()
    {
        Ship current = new Ship();
        
        if( shipCounter == 0 )
        {
            current = new Carrier();
        }
        
        if( shipCounter == 1 )
        {
            current = new Destroyer();
        }
        
        if( shipCounter == 2 )
        {
            current = new Sub();
        }
        
        if( shipCounter == 3 )
        {
            current = new Battleship();
        }
        
        if( shipCounter == 4 )
        {
            current = new Patrol();
        }
        
        return current;
    }
    
    private void checkKeyPress( Ship current )
    {
        if( Greenfoot.isKeyDown("shift") && refreshCounter > 10 )
        {
            current.setRotation( current.getRotation() + 90 );
            refreshCounter = 0;
        }
        else
        {
            refreshCounter++;
        }
    }
    
    /**
     * fillBoard fills the numeric representation of the gameboarsd with
     * 1's where a ship is located
     * 
     * @param There are no parameters
     * @return Nothing is returned
     */
    private void fillBoard()
    {
        Ship toAdd;
        
        int currentX;
        int currentY;
        int rotation;
        
        for( int i = 0; i < getObjects(Ship.class).size(); i++ )
        {
            toAdd = getObjects(Ship.class).get(i);
            
            currentX = toAdd.getX();
            currentY = toAdd.getY();
            rotation = toAdd.getRotation();
            
            for( int j = 0; j < (toAdd.getSize() + 1) / 2; j++ )
            {
                //ship of length 3
                if( toAdd.getSize() % 2 == 1 )
                {
                    //horizontal
                    if( rotation == 0 || rotation == 180 )
                    {
                        //row, then coloum
                        //going down the rows, using y
                        board[ (currentY - 36) / 72 ] [ (currentX - 36) / 72 - j ] = 1;
                        board[ (currentY - 36) / 72 ] [ (currentX - 36) / 72 + j ] = 1;
                    }
                    //vertical
                    else
                    {
                        board[ (currentY - 36) / 72 - j] [ (currentX - 36) / 72 ] = 1;
                        board[ (currentY - 36) / 72 + j] [ (currentX - 36) / 72 ] = 1;
                    }
                }
                else
                {
                    if( rotation == 0 || rotation == 180 )
                    {
                        //row, then coloum
                        //going down the rows, using y
                        board[ (currentY - 36) / 72 ] [ (currentX - 36) / 72 - j ] = 1;
                        board[ (currentY - 36) / 72 ] [ (currentX - 36) / 72 + j ] = 1;
                        board[ (currentY - 36) / 72 ] [ (currentX - 36) / 72 + j + 1 ] = 1;
                    }
                    //vertical
                    else
                    {
                        board[ (currentY - 36) / 72 - j] [ (currentX - 36) / 72 ] = 1;
                        board[ (currentY - 36) / 72 + j] [ (currentX - 36) / 72 ] = 1;
                        board[ (currentY - 36) / 72 + j + 1] [ (currentX - 36) / 72 ] = 1;
                    }
                }
            }
        }
    }
    
    /**
     * displayBoard displays the numerical representation of the gameboard where 1's indicate a ship
     * at that location and 0's indicate no ship placed at that location
     * 
     * @param There are no parameters
     * @return Nothing is returned
     */
    private void displayBoard()
    {
        for( int r = 0; r < board.length; r ++ )
        {
            for( int c = 0; c < board[r].length; c ++ )
            {
                System.out.print( board[r][c] + "\t" );
            }
            
             System.out.println();
        }
        
         System.out.println();
    }
    
    
    
    
    
    
    

}