
//import javax.swing classes for UI
import javax.swing.JComponent;
import javax.swing.Timer;
//import java.awt classes for UI
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
//import java.awt.geom shape
import java.awt.geom.Ellipse2D;
//import java.awt.event classes for event management
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//class Note is used to create, manage and draw notes onto a panel with a sitar image
//it interacts indirectly with the song class and reaction rectangle class to create a realistic series
public class Note extends JComponent { //declare Note class as a type of JComponent
    
    //declare instance fields
    private int yPos; //y position of note
    private int xPos; //x position of note (does not change except for animations)
    private int dy; //speed of note
    private int string; //string that the note is on
    private Color color; //color of note
    private Ellipse2D.Double e2d; //ellipse defining shape of note
    private int size; //size of note's ellipse
    private int shrinkStage; //which animation state note is at (0 = stable/fully grown, 1 = shrinking/growing, 2 = fully shrunken)
    private int count; //counter used to change x twice as slowly for animations
    private String note; //letter of note (ex: f#3) to be played by sitar
    public static final int[] xs = new int[] {151, 166, 180, 195, 209, 225}; //array of x positions based on string
    public static final String[] strings = new String[] {"a", "s", "d", "f", "g", "h"}; //array of key names of strings
    public static final Color[] colors = new Color[] {new Color(255, 102, 0), new Color(75, 75, 255), Color.RED, Color.ORANGE, Color.YELLOW, new Color(15, 204, 15)}; //array of colors for each string
    
    public Note(String k, String n) { //constructor for Note, pass in key and note of Note
        yPos = -20; //start note out of frame
        size = 1; //start note at small size
        dy = 1; //move note at one unit at a time
        note = n; //initialize note for sitar to play later
        if (k == "0") string = 6; //if the note is a blank note, set the string to 6 (out of bounds) so it is not drawn
        else { //if the note is note blank
            for (int i = 0; i < 6; i++) { //loop through strings
                if (strings[i].equals(k)) { //if the string key is the same as the parameter
                    string = i; //mark that as the correct string using the index
                    break; //leave this loop
                }
            }
        }
        setBounds(0, 0, 400, 562); //set bounds of the note so it shows up in absolute positioning of game frame
        color = colors[string]; //obtain color of note based on string
        xPos = xs[string] + 4; //obtain x position of note based on string, add 4 for size issues (growing)
        e2d = new Ellipse2D.Double(xPos, yPos, size, size); //initialize ellipse
        
        //grow note into position
        shrinkStage = 1; //start growing note
        final Timer t = new Timer(30, null); //create swing timer, null ActionListener to be able to control it within its own method
        t.addActionListener(new ActionListener() { //add new action listener to timer
            public void actionPerformed(ActionEvent e) { //when timer ticks
                size += 2; //increment size of note by 2
                count++; //increment x position counter
                if (count == 2) { //every two timer ticks
                    xPos--; //decrement x position to recenter it as it shrinks
                    count = 0; //reset counter
                }
                if (size >= 13) { //if the size is 13 or has grown too large, it should stop growing
                    size = 13; //reset its size to 13
                    xPos = xs[string]; //reset its x position
                    shrinkStage = 0; //stop shrinking
                    count = 0; //stop counting
                    t.stop(); //stop the timer, so note stops growing
                }
            }
        });
        t.start(); //start the timer, so this note grows in on constructions
    }

    public void paintComponent(Graphics g) { //paintComponent method overrides method in JComponent
        if(string < 6) { //if the note is not a blank note
            Graphics2D g2 = (Graphics2D) g; //simplify graphics context to two dimensions
            e2d.setFrame(xPos, yPos, size, size); //reframe/reposition outer ellipse with updated coordinates
            g2.setColor(color); //set color of graphics context to correct current color for outside of note
            if(yPos >= 485) g2.setColor(color.darker().darker().darker()); //if the note has already been missed, darken color a lot
            g2.fill(e2d); //fill the outer ellipse using the graphics context
            e2d.setFrame(xPos + 2, yPos + 2, size - 4, size - 4); //reframe/reposition inner ellipse with updated coordinates
            g2.setColor(color.darker()); //set color of graphics context to correct current color for inside of note
            if(yPos >= 485) g2.setColor(color.darker().darker().darker().darker()); //if the note has already been missed, darken color a lot
            g2.fill(e2d); //fill the inner ellipse using the graphics context
        }
    }

    //mutator for changing y position by speed of note
    public void move() {
        yPos += dy;
    }
    
    //accessor that detects if note has been missed
    public boolean isMissed() {
        return (yPos == 485);
    }
    
    //accessor that detects if note is out of frame
    public boolean isOut() {
        return ((yPos > 565) && (yPos < 600));
    }
    
    //accessor that detects if note is in the reaction rectangle
    public boolean isIn() {
        return ((yPos >= 429) && (yPos <= 479));
    }
    
    //mutator for setting y position of note
    public void setYPos(int y) {
        yPos = y;
    }
    
    //accessor for note to play on sitar
    public String getNote() {
        return note;
    }
    
    //accessor for shrinking stage of note
    public int getStage() {
        return shrinkStage;
    }
    
    //method to shrink note
    public void shrink() {
        //utilize similar method to method used in constructor to shrink note
        shrinkStage = 1;
        count = 0;
        final Timer t = new Timer(25, null);
        t.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                size -= 2;
                count++;
                if (count == 2) {
                    xPos++;
                    count = 0;
                }
                if (size <= 0) {
                    shrinkStage = 2;
                    t.stop();
                }
            }
        });
        t.start();
    }
    
}
