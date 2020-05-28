# FINAL PROJECT OF SOFTWARE ENGINEERING 2019/2020

#### Implementation of Santorini© board game in Java language, which idea was conceived by Gordon Hamilton and was later published thanks to Kickstarter by Roxley Games.

#### Instructions on how to play the board game: https://cdn.1j1ju.com/medias/fc/ec/5d-santorini-rulebook.pdf

## GROUP DESCRIPTION
<pre><ul><li> Mangano Davide ------- https://github.com/Davismanghi , https://github.com/DavideMangano </li>
  <li> Messori Alessandro --- https://github.com/AlessandroMessori </li>
  <li> Nespoli Matteo ------- https://github.com/MattNes </li></pre></ul>
  
## LIST OF FUNCTIONALITIES

We decided to implement the __complete rules + CLI + GUI + Socket + 1 AF__, so to therorically reach 30 as maximum mark possible. The __advanced functionality__ we chose is the __multiple games on one server__.

## INSTRUCTIONS ON HOW TO CORRECTLY USE THE .jar FILE

### On Ubuntu/Debian/MacOS[1]:

__Step 1)__ *Checking if you already have java installed.* <br>
To work properly, the .jar needs to be opened in an OS with already a JRE and a JDK installed. Open the terminal with the CTRL+ALT+T combination, write down <code> java -version </code> and see the result. If the Java version is already 13 or more, you can jump directly to __Step 4__. Otherwise, go to the next step. <br><br>
__Step 2)__ *Installing Java*.<br>
Start by typing <code> sudo apt-get install default-jre</code> to download the JRE. After that, type <code>sudo apt_get install openjdk-13-jdk</code> to install Java JDK 13. <br><br>
__Step 3)__ *Confirmation*.<br>
Always on the terminal, now type again <code>java -version</code>. You should see Java 13, that means everything has gone correctly. Otherwise there could be some errors in the previous phases, but the terminal should explain you how to solve them.<br><br>
__Step 4)__ *Downloading the actual game*.<br>
Now you have to get the actual file to play with. First, open the <code>deliveries/final/jar</code> folder in this repo. From there, download either the CLI client or the GUI client, depending on you wanting to play a more vintage gameplay or a more modern, graphical one.
After you have decided, you now have the .jar file in your PC. But how to run it?<br><br>
__Step 5)__ *Open the game*. <br>
Open the terminal, reach the folder in your system containing the downloaded .jar file, and write <code>java SantoriniGUI.jar -jar</code>.<br><br>
__Step 6)__ *Congratulations!*<br>
Now you can play your Santorini.jar file without problems. 

### On Windows:

__Step 1)__ *Downloading Java.*<br>
If you already have Java installed, you can skip to __Step 4__. Otherwise, you have to download JRE and JDK 13 (or later). To do so, you can search the links here indicated to have all you need to play:<br>
__JRE__: https://www.java.com/it/download <br>
__JDK__: https://download.java.net/openjdk/jdk13/ri/openjdk-13+33_windows-x64_bin.zip.<br><br>
__Step 2)__ *Downloading the actual game*.<br>
When you're done with Java, you can see the __Step 4__ in the Ubuntu/Debian/MacOS guide.<br><br>
__Step 3)__ *How to run the .jar*<br>
To run the .jar without problems, Windows gets a bit tricky. Open the Terminal, and go to the folder where you saved your Santorini.jar file (with a series of <code>cd</code>). Now write the path to the folder where you saved the JDK previously downloaded together with the .jar name in this way:<br>
<code>/Example/Folder/WhereYouHaveJDK/bin/Java -jar Santorini.jar</code>.<br><br>
__Step 4)__ *Congratulations!*<br>
Now you can play your Santorini.jar file without problems.


#### Enjoy the game!

__[1] ATTENTION!__ On some MacOS devices (13" laptops) there could be some problems with the buttons and texts disposure. Be aware, and use a 15,6" display or an Ubuntu/Debian device!
