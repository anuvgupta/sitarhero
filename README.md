# sitarhero
&nbsp;Music/video game using Java and MIDI Sitar  

## idea
I was inspired to design **Sitar Hero** in fourth grade, after my friends and I managed to come up with a plethora of multicultural puns. Sitar Hero being the one I found most compelling, I vowed to one day make a game similar to [Guitar Hero](https://www.guitarhero.com/game), but with the popular Indian instrument, the sitar, instead of the classic Gibson SG modeled guitar. For this project, that is what I originally intended to create; however, synchronizing an audio stream with AWT and JavaX Swing components is in fact quite an unwieldy task. Thus, I decided to make the project more authentic to the concept of *"Sitar Hero"* and have the user feel like he/she is playing a sitar.
 - The player must hit notes on the keyboard as they reach the bottom of the screen
    - If a note is missed, the reaction box will flash red and a kick drum sound will play
    - If a note is hit, the reaction box will flash green and the correct sitar note will play
 - If the player completes a song with 80% notes hit
    - Song name and artist are revealed
    - Edited/shortened version of song is played
    - Player proceeds to next song
 - If a player runs out of lives or completes with 20% notes missed
    - Player must retry song that was failed
 - After three songs, game won!

## instructions
 1. Install [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
 2. Clone/download this repository
 3. Use command line Java (recommended) or BlueJ
    - Command Line
        - Open a terminal or command prompt
        - Navigate to *this_repository/*`SitarHero/`
        - Run the following command: `java SitarHero`
    - BlueJ IDE
        - Download and install [BlueJ IDE](http://www.bluej.org/)
        - Open BlueJ and select Project->Open Project from the menu
        - Navigate to *this_repository/* and open the folder `SitarHero` (as a BlueJ project)
        - Right click on class `SitarHero` and run the static `main()` method with no arguments
 4. Ignore any messages that show up in the console/terminal window about `AudioComponent.h`
    - This problem is common using native Java audio on macOS
 5. Play sitar hero!

## credits
 - Sitar image `SitarHero/files/sitar/sitar.png` [premierguitar.com](http://www.premierguitar.com/articles/23392-danelectro-baby-sitar-review)
 - Sitar MIDI SoundFont `SitarHero/files/sitar/saz.SF2` Marck Heusschen of [hammersound.net](http://www.hammersound.com/cgi-bin/soundlink.pl?action=view_category&category=Ethnic)
 - Gifs in `SitarHero/files/sitar`
    - `dancing.gif` [onedio.com](https://onedio.com/haber/cirkin-goruntusu-disinda-sineklerden-nefret-etmek-icin-hakli-sebepler-385982)
    - `rickroll.gif` [medium.com](https://medium.com/@listenonrepeat/rickroll-the-world-but-why-c68e236c9f12)
    - `saxroll.gif` [giphy.com](http://giphy.com/gifs/W6Tdbnqo7rFrW)
    - `shrek.gif` [gifsoup.com](http://gifsoup.com/view/5164615/it-will-allbe-ogre-soon.html)
 - Wav audio files
    - All `.wav` files in
        - `SitarHero/files/levels`
        - `SitarHero/files/rickroll`
        - `SitarHero/files/saymyname`
    - All edited by me, courtesy of SoundCloud and YouTube

View full credits in [`SitarHero/README.TXT`](https://github.com/anuvgupta/sitarhero/blob/master/SitarHero/README.TXT)

## disclaimer
*Anuv Gupta and Sitar Hero take no credit for and have no association with any entities or property belonging or related to [Guitar Hero](https://www.guitarhero.com/) and [ActiVision](https://www.activision.com/). Any and all similarities or resemblances to the aforementioned entities are completely unintentional and by chance. For any issues, please contact (me@anuv.me)[mailto:me@anuv.me] by email.*
