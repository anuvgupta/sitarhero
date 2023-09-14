//import javax.swing classes for UI

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

// import java.util classes
// import java.io and java.imageio classes
// import java.awt classes for UI
// import java.awt.image class for bg image
// import java.awt.event classes for event handling

// class Game can be constructed to create main game panel and to collect key events
// contains main game loop and main game logic
public class Game extends JPanel implements KeyListener { // declare class game as a type of JPanel that is also a KeyListener

    // declare instance fields
    private int gameState; // state of the game: 0 = stopped, 1 = playing, 2 = died (out of lives), 3 = lost (not enough points), 4 = won
    private ArrayList<ArrayList<Note>> notes; // outer list contains a list for each string on the sitar. each individual list contains notes on that string
    private Song song; // song currently being played
    private Sitar sitar; // sitar (sound-making) object
    // UI elements
    private JLabel scoreL; // displays current score
    private JLabel livesL; // displays current lives
    private JLabel finishL1; // displays win/lose text
    private JLabel finishImg; // displays gif
    private JLabel finishL2; // displays win/lost text
    private JPanel finishP; // holds win/lose elements
    private JComponent sitarImg; // displays background image
    private ReactionRectangle reactRect; // box in which notes must be hit, reacts to hits and misses with color
    private ReactionRectangle bgRect; // background reaction box
    private int score; // contains score
    private int lives; // contains lives
    private int noteClick; // contains counter for times at which notes are added
    private int level; // contains current level
    private Timer gameTimer; // util timer that controls gameplay
    private int[] keyCodes; // contains key codes in order of strings
    private String[] songs; // contains titles of songs to be played
    private InputStream[] gifs; // contains gif files

    public Game() throws IOException { // constructor for Game
        level = 0; // level 0 is essentially level 1
        keyCodes = keyCodes = new int[]{KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_F, KeyEvent.VK_G, KeyEvent.VK_H}; // initialize key codes
        String[] names = new String[]{"shrek", "saxroll", "dancing", "rickroll"}; // initialize names of gifs
        gifs = new InputStream[names.length]; // initialize array of files for gifs
        for (int i = 0; i < names.length; i++)
            gifs[i] = Files.getInputStream("sitar/" + names[i] + ".gif"); // initialize files for gifs
        songs = new String[]{"levels", "saymyname", "rickroll"}; // initialize titles of songs
        setSong(new Song(songs[level])); // initialize current song data
        setComponents(); // initialize UI
    }

    // method use to set (initialize) and reset the current song or song to be played
    public void setSong(Song s) { // takes in song as parameter
        gameState = 0; // stop game
        song = s; // use parameter song
        sitar = new Sitar(); // create new sitar sound player
        gameTimer = new Timer(); // create new timer

        noteClick = 0; // set note counter to 0
        score = 0; // start with 0 for score
        lives = 25; // start with 25 lives
        notes = new ArrayList<ArrayList<Note>>(); // initialize outer note list list
        for (int i = 0; i < 6; i++)
            notes.add(new ArrayList<Note>()); // initialize 6 inner note lists (one for each string)
    }

    // method to set and reset UI
    public void setComponents() throws IOException {
        // create score and lives title labels
        Font titleFont = new Font("Helvetica", Font.PLAIN, 20);
        JLabel scoreTL = new JLabel("SCORE");
        scoreTL.setForeground(Color.WHITE);
        scoreTL.setBounds(37, -260, 400, 562);
        scoreTL.setFont(titleFont);
        JLabel livesTL = new JLabel("LIVES");
        livesTL.setForeground(Color.WHITE);
        livesTL.setBounds(290, -260, 400, 562);
        livesTL.setFont(titleFont);
        // create score and lives display labels
        Font valueFont = new Font("Helvetica", Font.PLAIN, 30);
        scoreL = new JLabel(Integer.toString(score));
        scoreL.setForeground(Color.WHITE);
        scoreL.setBounds(37, -210, 400, 562);
        scoreL.setFont(valueFont);
        livesL = new JLabel(Integer.toString(lives));
        livesL.setForeground(Color.WHITE);
        livesL.setBounds(290, -210, 400, 562);
        livesL.setFont(valueFont);
        // create key legend label
        JLabel keysL = new JLabel("A S D F G H");
        keysL.setForeground(Color.WHITE);
        keysL.setBounds(148, 250, 400, 562);
        keysL.setFont(new Font("Helvetica", Font.BOLD, 10));

        // create panel for game/round ends
        finishP = new JPanel(new BorderLayout());
        finishP.setBounds(0, 0, 400, 562);
        finishP.setBackground(new Color(0, 0, 0, 195));
        // create title game end label
        Font finishFont1 = new Font("Helvetica", Font.PLAIN, 20);
        finishL1 = new JLabel("YOU WIN", SwingConstants.CENTER);
        finishL1.setForeground(Color.WHITE);
        finishL1.setFont(finishFont1);
        // create panel for title game end label
        JPanel finishPTop = new JPanel(new GridLayout(1, 1));
        finishPTop.setPreferredSize(new Dimension(400, 100));
        finishPTop.setBackground(new Color(0, 0, 0, 0));
        finishPTop.add(finishL1);
        // create gif label
        finishImg = new JLabel((Icon) new ImageIcon(Files.toByteArray(gifs[0])));
        finishImg.setBackground(new Color(0, 0, 0, 0));
        // create secondary game end label
        Font finishFont2 = new Font("Helvetica", Font.PLAIN, 10);
        finishL2 = new JLabel("<html><center>PRESS SPACE<br/><br/>TO MOVE ON</center></html>", SwingConstants.CENTER);
        finishL2.setForeground(Color.WHITE);
        finishL2.setFont(finishFont2);
        // create panel for secondary game end label
        JPanel finishPBottom = new JPanel(new GridLayout(1, 1));
        finishPBottom.setPreferredSize(new Dimension(400, 260));
        finishPBottom.setBackground(new Color(0, 0, 0, 0));
        finishPBottom.add(finishL2);
        // add sections to game end panel
        finishP.add(finishPTop, BorderLayout.NORTH);
        finishP.add(finishImg, BorderLayout.CENTER);
        finishP.add(finishPBottom, BorderLayout.SOUTH);
        finishP.setVisible(false); // make game ned panel invisible (currently the beginning of a game or round)

        // create background image
        BufferedImage img = null; // initialize img to null to avoid compiler errors
        try { // try block for buffering images without crashing
            img = ImageIO.read(Files.getInputStream("sitar/sitar.png")); // use static read() method from ImageIO class to load an image into img
        } catch (IOException e) { // catch block to recieve potential errors thrown by image buffering
            e.printStackTrace(); // print errors, if caught
        }
        final BufferedImage imgFinal = img; // make variable final for inner class usage
        sitarImg = new JComponent() { // create new custom JComponent for bg image, draws sitar image
            public void paintComponent(Graphics g) { // declare paintComponent method with the parameter of a Graphics object. Overrides method in JComponent
                g.drawImage(imgFinal, 0, 0, null); // use graphics toolkit to draw the buffered image referenced by img onto the current Sitar component
            }
        };

        // set bounds of custom component for absolute positioning use
        sitarImg.setBounds(0, 0, 400, 562);
        // initialize reaction rectangles
        reactRect = new ReactionRectangle(130, 429, 130, 50, 10);
        bgRect = new ReactionRectangle(0, 0, 400, 562, 5);

        // add all components to this current Game panel in order
        add(finishP);
        add(reactRect);
        add(sitarImg);
        add(keysL);
        add(scoreTL);
        add(livesTL);
        add(scoreL);
        add(livesL);
        add(bgRect);
    }

    // method for clearing memory after round is over
    public void nullifyAll() {
        // nullify all object references
        notes = null;
        song = null;
        sitar = null;
        scoreL = null;
        livesL = null;
        finishL1 = null;
        finishL2 = null;
        finishP = null;
        sitarImg = null;
        reactRect = null;
        bgRect = null;
        gameTimer = null;
        // clear memory for efficiency
        System.gc(); // request JVM to clear all objects not referenced by an identifier or used at all
    }

    // method for starting game
    public void play() throws IOException {
        gameState = 1; // start game
        // calculate speed of timer in milliseconds using data from song
        double msPerBeat = 1000.0 / (song.getBeatsPerMinute() / 60);
        double notesPerBeat = song.getNotesPerBeat();
        double msPerNote = msPerBeat / notesPerBeat;
        final int constant = 50;
        long tDelay = (long) (msPerNote / constant);
        if (gameTimer != null) { // if the game timer is an object
            gameTimer.scheduleAtFixedRate(new TimerTask() { // schedule a new custom TimerTask for the timer; use scheduleAtFixedRate to reduce lag from swing and audio functions
                public void run() { // declare a run method for override
                    boolean empty = true; // the strings are currently empty, or they may not be; guilty until proven innocent
                    for (int i = 0; i < 6; i++) { // loop through list of lists of Notes
                        for (int j = 0; j < notes.get(i).size(); j++) { // loop through lists of Notes
                            Note currNote = notes.get(i).get(j); // get current note
                            currNote.move(); // move current note
                            currNote.repaint(); // repaint current note
                            if (currNote.isMissed()) { // if the current note was missed
                                sitar.kick(); // make kick sound with sitar soundmaker
                                reactRect.hit(2); // have the main reaction rectange flash red
                                lives--; // take away a life
                                livesL.setText(Integer.toString(lives)); // update lives label
                                if (currNote.getStage() == 0)
                                    currNote.shrink(); // if the missed note has not started shrinking, start shrinking it
                                if (lives <= 0) { // if the player is out of lives
                                    sitar.kick(); // make kick sound with sitar soundmaker
                                    // set gif to shrek losing gif
                                    byte [] bytes = new byte[0];
                                    try {
                                        bytes = Files.toByteArray(gifs[0]);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    };
                                    finishImg.setIcon((Icon) new ImageIcon(bytes));
                                    finishImg.setBackground(new Color(0, 0, 0, 0));
                                    gameState = 0; // game is stopped
                                    lives = 0; // reset lives to 0 in case the user went under 0 to the negatives
                                    livesL.setText(Integer.toString(lives)); // update lives label
                                    bgRect.hit(3); // make the background reaction rectangle flash white
                                    // set dying text
                                    finishL1.setText("YOU DIED");
                                    finishL2.setText("<html><center>LIVES: 0<br/><br/>PRESS SPACE TO TRY AGAIN</center></html>");
                                    finishP.setVisible(true); // make end panel visible
                                    gameState = 2; // game state: dead
                                    if (gameState > 1) { // this check is unneccessary but helpful in case of threading errors
                                        gameTimer.cancel(); // stop timer task
                                        gameTimer.purge(); // remove all tasks from timer
                                    }
                                }
                                break; // break out of the current for loop, meaning stop checking this string for notes
                            }
                            if (currNote.isOut()) { // if the current note is out of the frame
                                notes.get(i).remove(j); // remove it form the list of notes, so it is not unneccessarily checked or moved
                                break; // break out of the current for loop, meaning stop checking this string for notes
                            }
                        }
                        if (notes.get(i).size() > 0)
                            empty = false; // if even a single string has a note on it, empty will be set to false
                    }

                    noteClick++; // add to note counter
                    if (noteClick == constant * 1.2) { // only add note to the screen at certain intervals
                        noteClick = 0; // reset note counter
                        String n = song.nextNote(); // get next note from song
                        String k = song.nextKey(); // get next key from song
                        if (!n.equals("q") && !k.equals("q")) { // if the song is not over
                            for (int i = 0; i < 6; i++) { // loop through the array of sitar strings
                                if (Note.strings[i].equals(k)) { // find the index i of the string with the next note
                                    Note newNote = new Note(k, n); // create a new note with the correct key and note
                                    notes.get(i).add(newNote); // use the index found to add the new note to the correct string
                                    add(newNote); // add the new note to this Game panel
                                    add(sitarImg); // add the background image behind it
                                    add(bgRect); // add the background reaction rectangle behind that
                                    break; // break out of string checking for loop
                                }
                            }
                        } else if (empty) { // if the song is over, and all strings are empty
                            sitar.kick(); // make kicking sound
                            gameState = 0; // stop the game
                            bgRect.hit(3); // flash the background white
                            // check for win
                            if (song.getPercentage(score) >= 80) { // if the player got 80% of notes correct or more
                                song.playAudio(); // play the song
                                byte[] bytes = new byte[0];
                                try {
                                    bytes = Files.toByteArray(gifs[level + 1]);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                finishImg.setIcon((Icon) new ImageIcon(bytes)); // change the gif to gif corresponding to level
                                if (level == 2) { // if the player is on the last level, display endgame text
                                    finishL1.setText("YOU WON");
                                    finishL2.setText("<html><center>SONG - " + song.getName() + "<br/><br/>PRESS SPACE TO END GAME</center></html>");
                                } else { // if the player is not on the last level, display next level text
                                    finishL1.setText("LEVEL " + (level + 1));
                                    finishL2.setText("<html><center>SONG - " + song.getName() + "<br/><br/>PRESS SPACE TO MOVE ON</center></html>");
                                }
                                finishP.setVisible(true); // display the end round panel
                                gameState = 4; // set the state to win
                            } else { // if the player did not get 80% of notes correct, at least
                                try {
                                    finishImg.setIcon((Icon) new ImageIcon(Files.toByteArray(gifs[0]))); // set gif to shrek losing gif
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                // display lose level text
                                finishL1.setText("YOU LOST");
                                finishL2.setText("<html><center>ACCURACY: " + song.getPercentage(score) + "%<br/><br/>PRESS SPACE TO TRY AGAIN</center></html>");
                                finishP.setVisible(true); // display the end round panel
                                gameState = 3; // set the state to lose
                            }
                            finishImg.setBackground(new Color(0, 0, 0, 0)); // set background of gif to clear
                            if (gameState > 1) { // this check is unneccessary but helpful in case of threading errors
                                gameTimer.cancel(); // stop timer task
                                gameTimer.purge(); // remove all tasks from timer
                            }
                        }
                    }
                }
            }, tDelay, tDelay); // pass in delays
        }
    }

    public void keyPressed(KeyEvent e) { // when a key is pressed
        if (gameState == 1) { // if the game is running
            boolean miss = true; // guilty until proven innocent: the user has missed the note
            for (int i = 0; i < 6; i++) { // loop through list of lists of notes
                if (keyCodes[i] == e.getKeyCode()) { // if the key pressed corresponds to the current string
                    for (int j = 0; j < notes.get(i).size(); j++) { // loop through notes on each string
                        Note currNote = notes.get(i).get(j); // for each note, store it for convenience, and check if it has been hit
                        if (currNote.isIn()) { // if that note is in the reaction rectangle at the time of the key event
                            sitar.playNote(currNote.getNote()); // play the note belonging to the current note on the sitar
                            reactRect.hit(1); // flash green on the reaction rectangle for a hit
                            currNote.setYPos(700); // move the current note far outside of the frame so it is essentially invisible
                            currNote.repaint(); // repaint the note so it disappears from the frame
                            notes.get(i).remove(j); // remove the note from the array of notes so it isnt checked
                            score++; // increment score
                            scoreL.setText(Integer.toString(score)); // update score label
                            miss = false; // being in this code block means there was a hit, so the user is innocent: set miss to false
                            break; // break out of the current for loop to move on to the next string
                        }
                    }
                }
            }
            if (miss) { // if the above block was not executed, the player is guilty of hitting a key when there are no notes in the reaction rectangle on any string whatsoever
                reactRect.hit(2); // flash the reaction rectangle red
                lives--; // decrement lives
                livesL.setText(Integer.toString(lives)); // update lives label
            }
        } else if (gameState > 1) { // if the game is not running or stopped, but rather is in some end round stage
            // being in this block of code represents moving to a new song or round
            if (song.isPlaying()) song.stopAudio(); // stop any audio that the song may be playing
            removeAll();// remove all components in this current Game panel
            updateUI(); // update this current Game panel to make the components disappear
            nullifyAll(); // nullify all the components and other objects before reconstructing (to save memory)
            if (gameState == 4) { // if the game is in a win state
                if (level == 2) System.exit(0); // and if the player is on the last level, end the program
                else level++;
                ; // but if the player is jsut at the end  of a regular level, increment level before reconstructing
            }
            setSong(new Song(songs[level])); // initialize the game to the new song, utilizing the updated level
            try {
                setComponents(); // initialize the frame again, with the new song
                play(); // play the game once it has been initialized
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    // method needed to satisfy KeyListener interface
    public void keyTyped(KeyEvent e) {
    }

    // method needed to satisfy KeyListener interface
    public void keyReleased(KeyEvent e) {
    }
}
