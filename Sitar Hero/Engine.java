//import javax.swing classes for UI
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
//import java.awt classes for UI
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
//import java.awt.event classes for event handling
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
//import exception class
import java.lang.InterruptedException;

//class Engine can be constructed to create an intro panel, and contains the static method to run the game
public class Engine extends JPanel { //declare Engine class as a type of JPanel

    //instance field stage: represents current stage of game
    private boolean stage; //false = intro stage, true = game stage

    public Engine() { //Engine constructor
        stage = false; //intro stage
        //initialize Engine JPanel content/layout
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        //initialize title content/layout
        JLabel title = new JLabel("<html><b>SITAR HERO</b></html>", SwingConstants.CENTER);
        title.setPreferredSize(new Dimension(400, 85));
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Helvetica", Font.PLAIN, 48));

        //initialize instruction content/layout
        JPanel infoPanel = new JPanel(null);
        infoPanel.setPreferredSize(new Dimension(400, 497));
        infoPanel.setLayout(new GridLayout(11, 1));
        infoPanel.setBackground(Color.BLACK);
        Font infoFont = new Font("Helvetica", Font.PLAIN, 19);
        //instructions to play game
        String[] infoText = new String[] {"<div style = 'font-size: 23'>Welcome to Sitar Hero!</div>",
                "<b>How to Play</b>",
                "1. ASDFGH correspond to the 6 strings",
                "2. Notes come down on the strings",
                "3. Hit a note by hitting the key of its string when it enters the translucent black box",
                "4. If you hit a note, you gain a point,<br/>and the sitar will play that note",
                "5. If you miss a note, you lose a life",
                "6. Hit a key with no notes, also lose a life",
                "7. If you lose all your lives, you die",
                "8. If you get through with 80% accuracy, you get to know the song's name and move on",
                "<div style = 'font-size: 24'><i>PRESS SPACE TO START</i></div>"};
        for (String s : infoText) {
            JLabel n = new JLabel("<html><center>" + s + "</center></html>", SwingConstants.CENTER);
            n.setFont(infoFont);
            n.setForeground(Color.WHITE);
            infoPanel.add(n);
        }

        //add title and instructions to Engine JPanel
        add(title, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
    }

    //mutator for state instance field of Engine
    public void setStage(boolean s) {
        stage = s;
    }

    //accessor for state instance field of Engine
    public boolean getStage() {
        return stage;
    }

    //static main method for program to run
    public static void main(String[] args) {
        Engine e = new Engine(); //construct new Engine
        JFrame f = new JFrame("Sitar Hero"); //construct new JFrame
        //initialize JFrame size and properties
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //force program exit on frame close
        f.setSize(400, 562); //set size of frame to 400x562
        f.setResizable(false); //force the frame to be rigid in size

        Game game = new Game(); //construct new Game (game management) JPanel object
        game.setLayout(null); //enable absolute positioning in game JPanel
        game.setBackground(Color.BLACK); //initialize Game JPanel bgcolor

        //introductory functionality
        f.addKeyListener(new KeyListener() { //construct and add new anonymous key listener to frame
                public void keyReleased(KeyEvent ke) { //when key is pressed
                    //if the key pressed was the spacebar, and the engine is at its introductory stage
                    if ((ke.getKeyCode() == KeyEvent.VK_SPACE) && (!e.getStage())) {
                        e.setStage(true); //change engine's stage so this key listener is rendered useless
                        f.addKeyListener(game); //add game to frame as a key listener, so key events are logged from there, not here
                        f.setContentPane(game); //add game to frame as a JPanel by setting it as the content pane
                        f.setVisible(true); //have the frame appear again to show the changes
                        game.play(); //start the game
                    }
                }
                //method needed to satisfy KeyListener interface
                public void keyPressed(KeyEvent e) { }
                //method needed to satisfy KeyListener interface
                public void keyTyped(KeyEvent e) { }
            });
        f.setContentPane(e); //set the frame's content pane to the engine panel to begin the intro
        f.setVisible(true); //have the frame appear for the first time
    }
}
