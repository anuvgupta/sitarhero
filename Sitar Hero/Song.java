
//import java.util classes
import java.util.Scanner;
import java.util.ArrayList;
//import java.io File class
import java.io.File;
//import java.sound.sampled classes for playing audio
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;

//class Song is a container of song data and audio
public class Song { 

    //declare instance fields
    private String name; //full name of song
    private double bpm; //beats per minute of song
    private double npb; //notes per each beat of the song
    private ArrayList<String> keys; //list of the keys to be pressed in order in song
    private ArrayList<String> notes; //list of the notes to be played in order of notes
    private File infoFile; //text file with song info (full name, bpm, npb)
    private File keysFile; //text file containing song keys in order
    private File notesFile; //text file containing song notes in order
    private File audioFile; //wav file containing song audio
    private int totalNotes; //total amount of notes in a song
    private int k; //counter representing current key that song is on
    private int n; //counter representing current note that song is on
    private Clip audio; //audio clip player
    private boolean playing; //represents if audio is playing (helps prevent audio errors)

    public Song (String t) { //pass in title of song
        //initialize audio file and song data files using title
        audioFile = new File("./files/" + t + "/" + t + ".wav");
        infoFile = new File("./files/" + t + "/" + t + ".txt");
        notesFile = new File("./files/" + t + "/" + t + "_notes.txt");
        keysFile = new File("./files/" + t + "/" + t + "_keys.txt");

        //initialize lists for keys and notes
        keys = new ArrayList<String>();
        notes = new ArrayList<String>();
        //load song data from initialized files
        try { //try catch block to catch any thrown exceptions in file input
            //use scanner to load song info into variables
            Scanner s1 = new Scanner(infoFile);
            name = s1.nextLine();
            bpm = Double.parseDouble(s1.nextLine());
            npb = Double.parseDouble(s1.nextLine());
            s1.close();
            //use scanner to load keys into key list
            Scanner s2 = new Scanner(keysFile);
            while (s2.hasNextLine()) {
                String n = s2.nextLine(); //retrieve next key from file
                if (!n.equals("0")) totalNotes++; //if the key is not 0 (blank), add to total number of notes
                keys.add(n); //add key to list of keys
            }
            s2.close();
            //use scanner to load notes into note list
            Scanner s3 = new Scanner(notesFile);
            while (s3.hasNextLine()) notes.add(s3.nextLine());
            s3.close();
        } catch (Exception e) { //catch FileNotFoundExceptions and IOExceptions using the Exception superclass
            e.printStackTrace(); //print any caught exceptions
        }
        //set counters to -1, they increment immediately on event before any other code is run
        n = -1;
        k = -1;

        //initialize song for playing audio
        playing = false; //initialize playing boolean ot false, as the song is not playing currently
        try { //try catch block for catching any thrown exceptions in the song loading
            audio = AudioSystem.getClip(); //initialize audio clip player
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile); //load audio file data into audio input stream object 
            audio.open(audioStream); //load audio input stream into audio clip player
        } catch (Exception e) { //catch any LineUnavailableExceptions, UnsupportedAudioFileExceptions, or IOExceptions using the Exception superclass
            e.printStackTrace(); //print any caught exceptions
        }
    }

    //accessor method for bpm
    public double getBeatsPerMinute() {
        return bpm;
    }

    //accessor method for npb
    public double getNotesPerBeat() {
        return npb;
    }

    //accessor method for song's full name
    public String getName() {
        return name;
    }

    //accessor method for percentage correct
    public int getPercentage(int p) { //pass in points earned value
        return (int) (100 * p/totalNotes); //calculate a percentage using points earned parameter and total points possible, cast to int to truncate
    }

    //accessor method for the next key in the list of keys of the song
    public String nextKey() {
        k++; //increment key list counter
        if (k < keys.size()) return keys.get(k); //return current key
        else return "q"; //if out of keys, return q
    }

    //accessor method for the next note in the list of notes of the song
    public String nextNote() {
        n++; //increment note list counter
        if (n < notes.size()) return notes.get(n); //return current note
        else return "q"; //if out of notes, return q
    }

    //accessor method for play state of song
    public boolean isPlaying() {
        return playing; //return playing boolean
    }

    //method for playing audio
    public void playAudio() {
        if (!playing) { //only if the audio is not already playing
            audio.start(); //start the audio clip player
            playing = true; //mark the song as playing
        }
    }

    //method for stopping audio
    public void stopAudio() {
        if (playing) { //only if the audio is playing
            audio.stop(); //stop the audio clip player
            playing = false; //mark the song as not playing
        }
    }
}
