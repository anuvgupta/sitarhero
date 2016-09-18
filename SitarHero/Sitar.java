/* NOTE: SoundFont courtesy of "www.hammersound.net": Sitar SoundFont "saz.SF2" by Marck Heusschen */

// import java.io class
import java.io.File;
// import java.util class
import java.util.ArrayList;
// import javax.sound.midi classes to utilize midi in sound generation
import javax.sound.midi.Soundbank;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiUnavailableException;

// class Sitar uses midi and a custom SoundBank containing sitar sounds to generate sitar sounds based on note names
public class Sitar {

    // declare instance field variables
    private Synthesizer synth; // synthesizer for loading instruments and generating sounds
    private Soundbank sitarSB; // custom soundbank form which to load instruments
    private final MidiChannel[] mc; // midi channels for playing midi notes through synthsizer
    private final String[] notes; // array of note names
    private final int[] values; // array of corresponding midi note values

    public Sitar() {
        // initialize array of note names (note names based on note names in song_note.txt files in song folders)
        notes = new String[] {"b2", "b3", "c#3", "c#4", "d#3", "e3", "f#3", "g#3", "a3", "d4", "f#4", "e4", "f3", "c4", "a#3", "g3", "d#4"};
        // initialize array of midi note values corresponding to note names in previous array
        values = new int[] {59, 71, 61, 73, 63, 64, 66, 68, 69, 74, 78, 76, 65, 72, 70, 67, 75};
        try { // try catch for preventing midi errors
            synth = MidiSystem.getSynthesizer(); // load synthesizer from the midi system
            synth.open(); // open synthesizer to play audio
        } catch (MidiUnavailableException e) { // if exception is caught
            e.printStackTrace(); // print error
        }
        File file = Files.getFile(this, "files/sitar/saz.SF2"); // load soundfont into file
        try {
            sitarSB = MidiSystem.getSoundbank(file); // load soundbank from soundfont file into custom soundbank file
        } catch (Exception e) { // if any exception is caught
            e.printStackTrace(); // print error
        }

        mc = synth.getChannels(); // link output channels of synthesizer to midi output channels
        Instrument[] i = sitarSB.getInstruments(); // load all custom soundbank instruments into array of instruments
        synth.loadInstrument(i[0]); // load first instrument in custom soundbank (sitar instrument) to synthesizer
    }

    // method for playing note sound based on note name
    public void playNote(String n) { // take in the note name
        if(!n.equals("0")) { // if the note name does not represent a blank note
            for (int i = 0; i < notes.length; i++) { // loop through the array of note names
                if(n.equals(notes[i])) { // if the same note name is found
                    // use the second midi channel (sitar) to turn on the note with a midi value (corresponding to the note name) and a velocity of 120
                    mc[1].noteOn(values[i], 120);
                }
            }
        }
    }

    // method for playing acoustic kick drum sound
    public void kick() {
        mc[9].noteOn(35, 120); // use the ninth midi channel (percussion) to turn on the note with midi value 35 (acoustic kick drum) and a velocity of 120
    }
}
