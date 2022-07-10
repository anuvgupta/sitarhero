
//  import javax.swing classes for UI
import javax.swing.JComponent;
import javax.swing.Timer;
// import java.awt classes for UI
import java.awt.Color;
import java.awt.Graphics;
// import java.awt.event classes for event management
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
// import java.lang exception for error handling
import java.lang.IllegalArgumentException;

// class ReactionRectangle is used to show flash and fade animations for the background and the note area of the game
// it "reacts" (flashes colors) in reaction to "hits" (any call of the method hit)
public class ReactionRectangle extends JComponent { // declare class ReactionRectangle as type of JComponent

    // declare instance field variables
    private Color c; // color of rectangle
    private int intensity; // intensity of color
    private int xPos; // x position of reactangle
    private int yPos; // y position of rectangle
    private int width; // width of rectangle
    private int height; // height of rectangle
    private int speed; // speed of change of color
    private int hit; // which color property to change [1 = green, 2 = red, 3 = rgb (white)]
    private Timer t; // timer for fade animations

    public ReactionRectangle(int x, int y, int w, int h, int s) { // constructor for ReactionRectangle takes in x position, y position, width, height, and speed of fade
        c = new Color(0, 0, 0, 140); // slightly translucent black
        intensity = 160; // color intensity of 160
        hit = 0; // initialize hit to 0, for no color change
        // initialize instance fields to constructor parameters
        xPos = x;
        yPos = y;
        width = w;
        height = h;
        speed = s;

        t = new Timer(speed/2, null); // initialize swing timer, null action listener added later to reference the timer within its instructions
        t.addActionListener(new ActionListener() { // add action listener to the timer
                public void actionPerformed(ActionEvent e) { // when the timer ticks
                    if (intensity > 0) { // if the the intensity is more than 0, meaning it has room to decrease
                        try { // try catch block to prevent errors; all below colors are translucent
                            if (hit == 1) c = new Color(0, intensity, 0, 140); // if hit represents green, set note color to a more intensely green color
                            else if (hit == 2) c = new Color(intensity, 0, 0, 140); // if hit represents red, set note color to a more intensely red color
                            else if (hit == 3) c = new Color(intensity, intensity, intensity, 140); // if hit represents white, set note color to a more intensely white color
                        }
                        catch (IllegalArgumentException iae) { // if error is caught, print details
                            System.out.println(intensity);
                            System.out.println(c.getGreen() + " " + c.getRed());
                            iae.printStackTrace();
                        }
                        intensity -= speed; // reduce the color intensity by the speed, for the next timer tick, so the color gets darker
                        repaint(); // repaint the rectangle component
                    } else { // if the intensity is 0 or less than 0, the rectangle has finished fading
                        c = new Color(0, 0, 0, 140); // reset the color to translucent black
                        intensity = 160; // reset the color intensity
                        hit = 0; // reset hit to 0, no color change
                        repaint(); // repaint the rectangle component
                        t.stop(); // stop the timer, the animation is over
                    }
                }
            });
        setBounds(0, 0, 400, 562); // set bounds so the component appears in absolute positioning
    }

    // declare paintComponent method with the parameter of a Graphics object; overrides method in JComponent
    public void paintComponent(Graphics g) {
        g.setColor(c); // set the color fo the graphics toolkit to the current color, however it has changed
        g.fillRect(xPos, yPos, width, height); // fill a rectangle at the correct area
    }

    // method that stops previous animations to start new ones
    public void hit(int b) { // take in an int b to choose the color
        t.stop(); // stop the timer if it is currently running
        hit = b; // set hit to b for the timer to know which color to change
        intensity = 160; // set the intensity to 160 to start with
        t.start(); // start the timer to start the animation
    }
}
