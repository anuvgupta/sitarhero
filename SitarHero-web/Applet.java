import javax.swing.JApplet;

// import java.awt classes for UI
import java.awt.Color;
// import java.awt.event classes for event handling
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Applet extends JApplet {
    public void init() {
        SitarHero e = new SitarHero(); // construct new Engine
        JApplet f = this; // construct new JFrame
        // initialize JFrame size and properties
        //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // force program exit on frame close
        f.setSize(400, 562); // set size of frame to 400x562
        //f.setResizable(false); // force the frame to be rigid in size

        Game game = new Game(); // construct new Game (game management) JPanel object
        game.setLayout(null); // enable absolute positioning in game JPanel
        game.setBackground(Color.BLACK); // initialize Game JPanel bgcolor

        // introductory functionality
        f.addMouseListener(new MouseListener() { // construct and add new anonymous key listener to frame
                public void mousePressed(MouseEvent e) { }
                public void mouseReleased(MouseEvent me) {
                    if (!e.getStage()) {
                        e.setStage(true); // change engine's stage so this key listener is rendered useless
                        f.addKeyListener(game); // add game to frame as a key listener, so key events are logged from there, not here
                        f.setContentPane(game); // add game to frame as a JPanel by setting it as the content pane
                        f.setVisible(true); // have the frame appear again to show the changes
                        game.setSize(400, 562);
                        f.setSize(400, 562);
                        game.play(); // start the game
                    }
                }
                public void mouseEntered(MouseEvent e) { }
                public void mouseExited(MouseEvent e) { }
                public void mouseClicked(MouseEvent e) { }
            });
        f.setContentPane(e); // set the frame's content pane to the engine panel to begin the intro
        f.setVisible(true); // have the frame appear for the first time
        e.setSize(400, 562);
        f.setSize(400, 562);
    }
}
