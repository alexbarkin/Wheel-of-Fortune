/*
Alex Barkin
Ms. Dyke
WheelOfFortune V.3
1/13/2015
This program will allow two users to play a complete game of Wheel Of Fortune. In the main menu, the user will have 4 options; play, highscores,
instructions, or exit. If the user selects highscores, they will be shown the top ten highscores. If the user selects instructions, they will
get thorough instructions on how to play the game. And if they select exit, the program will say goodbye, and then the window will close.
If the user selects play, then the game will start. First two players must enter their names. The wheel will then spin and player one will
get to guess a consonant. If the consonant is correct they will recieve the spun value of the wheel times the number of occurences of the
letter. Once they recieve their money, if they have over 250 dollars then they will be given the option to buy a vowel. If the letter is wrong
then it will change to the other person's turn. This will continue until all the letters in the word are guessed, a ',' is selected to go back
to the main menu, or a player selects a '.' and then guesses the correct word. Once this round is done, whoever wins the round will recieve a
$10,000 bonus. Then they will continue to the final round. The final round is a one player round. The player spins the wheel and is given an
envelope with a hidden value. The user is then given the letters 'r', 's', 'l', 'n', 't', and 'e'. They will then get to select three consonants
and one vowel. Once they guess these letters a message will pop up. Once they click ok on the message a 10 second timer will begin. The user has
until the timer finishes to guess the word. If the user guesses the phrase in the given amount of time, they will recieve the amount of money
which was in the envelope. If they do not, they will see the value but not get it. If the user recieves a new high score (top ten players) then
they will be added to the high score board.
*/

import java.io.*;
import hsa.*;
import java.awt.*;
import javax.swing.*;
/*Variables
------------------------|---------------------------|-------------------------------------------------------------------------------------
Name                    |Type                       |Purpose
------------------------|---------------------------|-------------------------------------------------------------------------------------
choice                  |String                     |To recieve the userinput for whichever option the user would like in the main menu.
			|                           |This will later be used in the main menu to control the flow of the program as well as
			|                           |many other temporary instances later in the game.
picture                 |static Image               |To temperarily store a picture in until the next picture is created.
PICTURE_PATH_ENVELOPE   |private static final String|To store the name of the file used for the picture of the envelope. It is final so it can be
			|                           |change once at the top of the program.
PICTURE_PATH_VORTEX     |private static final String|To store the name of the file used for the picture of the vortex background. It is final
			|                           |so it can be change once at the top of the program.
PICTURE_PATH_BACK       |private static final String|To store the name of the file used for the picture of the main menu background. It is final
			|                           |so it can be change once at the top of the program.
PICTURE_PATH_WHEEL      |private static final String|To store the name of the file used for the picture of the wheel that is used in the main menu.
			|                           |It is final so it can be change once at the top of the program.
PICTURE_PATH_HIGHSCORE  |private static final String|To store the name of the file used for the picture of the medal rewarded to anyonee who gets a
			|                           |new highscore. It is final so it can be change once at the top of the program.
MAX_SCORES              |private static final int   |To set the maximum number of highscores which can be stored at one time.
PHRASES                 |array - static final String|Stores all the possible phrases and words that are related to computers for use in the game.
c                       |reference                  |It allows the program to use the built in methods in the console class for the main window.
d                       |reference                  |It allows the program to use the built in methods in the console class for the input window.
alpha                   |private String             |Used to store the letters of the alphabet so the program can show and check what has and
			|                           |hasn't been used
playerOne               |private String             |Used to store player one's name (must be less than 15 characters), and later the winner's name.
			|                           |It is then added to the high scores file if they recieve a new highscore.
playerTwo               |private String             |Used to store player two's name (must be less than 15 characters).
guesses                 |private String             |Used to store all the correct guesses from the user so the will display in drawResponse()
playerOneMoney          |private int                |Used to store the amount of money that player one wins, and later used for holding the winner
			|                           |of round one's amount of money.
playerTwoMoney          |private int                |Used to store the amount of money that player two wins.
envelope                |private int                |Used to store the amount of money which is in the envelope in the final round.
scores                  |array - private int        |Used to store the scores from the high scores file.
names                   |array - private String     |Used to store the names from the high scores file.
input                   |private char               |Used to store user input from askData() and sent into occurancesOf() in game() to find the
			|                           |amount of occurences of each guess
oneTurn                 |private boolean            |Used to determine who's turn it is in game() and who guessed the word in results().
hasAdded                |private boolean            |Used to determine whether or not the user gets a new high score
times                   |private boolean            |Used to determine whether or not the highscores file has been read and depending on that the
			|                           |program will read the file or not. This is to ensure it only reads from the file once.
*/
public class WheelOfFortune
{
    String choice = "";
    static Image picture;
    private static final String PICTURE_PATH_ENVELOPE = "WOF_ENVELOPE.png";
    private static final String PICTURE_PATH_VORTEX = "TIME_VORTEX.png";
    private static final String PICTURE_PATH_BACK = "WOF_BACK.png";
    private static final String PICTURE_PATH_WHEEL = "WOF_WHEEL_SELECT.png";
    private static final String PICTURE_PATH_HIGHSCORE = "WOF_HIGHSCORE.png";
    private static final int MAX_SCORES = 10;
    static final String[] PHRASES = {"read only memory", "random access memory", "dot matrix", "operating system", "there's an app for that", "central processing unit", "social networking", "domain name system", "trojan horse", "user interface"};
    static Console d;
    static Console c;
    private String alpha = "abcdefghijklmnopqrstuvwxyz", playerOne = "", playerTwo = "", guesses = "";
    private int playerOneMoney = 0, playerTwoMoney = 0, envelope = 0, x = 0, j = 0;
    private int[] scores = new int [MAX_SCORES];
    private String[] names = new String [MAX_SCORES];
    private char input;
    private boolean oneTurn = true, hasAdded = false, times = true;

    /*This is my constructor which creates the console window and allows access to said window*/
    public WheelOfFortune ()
    {
	c = new Console ("Wheel Of Fortune");
    }


    /*this method clears the screen and draws the title at the top, center of the main screen*/
    private void title ()
    {
	c.setTextBackgroundColour (new Color (6, 105, 242));
	c.clear ();
	c.setTextColor (Color.white);
	c.println ("\n\n\n");
	c.setColour (new Color (20, 55, 242));
	c.fillRect (0, 0, 640, 80);
	c.setFont (new Font ("Magneto", 0, 40));
	c.setColour (new Color (246, 15, 0));
	c.drawString ("Wheel Of Fortune", 135, 45);
	c.setColour (new Color (242, 242, 242));
	c.drawString ("Wheel Of Fortune", 133, 43);
	c.setColour (Color.black);
	c.setFont (new Font ("Magneto", 0, 20));
	c.drawString ("Computers Edition", 220, 73);
    }


    /*this method clears the screen and draws the title at the top, center of the input screen*/
    private void dTitle ()
    {
	d.setTextBackgroundColour (new Color (6, 105, 242));
	d.clear ();
	d.setTextColor (Color.white);
	d.println ("\n");
	d.setColour (new Color (20, 55, 242));
	d.fillRect (0, 0, 320, 40);
	d.setFont (new Font ("Magneto", 0, 22));
	d.setColour (new Color (246, 15, 0));
	d.drawString ("Wheel Of Fortune Input", 20, 25);
	d.setColour (new Color (242, 242, 242));
	d.drawString ("Wheel Of Fortune Input", 18, 23);
    }


    /*This method is used to recieve yes no input from the user. The message and the "yes" option will vary depending on what the programmer sends
    into the method.
    Variables
    ------------------------|---------------------------|-------------------------------------------------------------------------------------------
    Name                    |Type                       |Purpose
    ------------------------|---------------------------|-------------------------------------------------------------------------------------------
    opt                     |String                     |To decide which option to display in the "yes" slot of the yes, no JOptionPane window.
    msg                     |String                     |To decide which message to display on the yes, no JOptionPane window.
    n                       |int                        |used to store the value of the JOptionPane selection and returns it to where it is called
    */
    private int yesNoOption (String opt, String msg)
    {
	Object[] options = {opt, "No"};
	int n = JOptionPane.showOptionDialog (null, msg, "Choices", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options [0]);
	return n;
    }


    /*This method is used to load and draw images, given the image path name and it's desired location.
    Variables
    ------------------------|---------------------------|-------------------------------------------------------------------------------------------
    Name                    |Type                       |Purpose
    ------------------------|---------------------------|-------------------------------------------------------------------------------------------
    tracker                 |MediaTracker               |Holds the list of images to be tracked.
    pictureName             |String                     |Holds the path name of the desired picture sent in by the programmer.
    locX                    |int                        |Holds the desired x location of the desired picture sent in by the programmer.
    locY                    |int                        |Holds the desired y location of the desired picture sent in by the programmer.
    e                       |InteruptedException var    |Used to catch the interupted exception thrown by tracker
    try catch - Used to catch an interupted exception which can be thrown if it occurs. Although it is unlikely
    if statement - Used if any errors occur, the user will be notified and they can continue to wherever they left off, without the image.
    */
    private void loadImage (String pictureName, int locX, int locY)
    {
	picture = Toolkit.getDefaultToolkit ().getImage (pictureName);
	MediaTracker tracker = new MediaTracker (new Frame ());
	tracker.addImage (picture, 0);
	try
	{
	    tracker.waitForAll ();
	    if (tracker.isErrorAny ())
	    {
		JOptionPane.showMessageDialog (null, "Error: An image loading error has occured.");
		return;
	    }
	}
	catch (InterruptedException e)
	{
	}
	c.drawImage (picture, locX, locY, null);
    }


    /*This method is used to return the amount of times the user's guess is in the phrase.
     Variables
     ------------------------|---------------------------|-------------------------------------------------------------------------------------------
     Name                    |Type                       |Purpose
     ------------------------|---------------------------|-------------------------------------------------------------------------------------------
     s                       |String                     |Holds the phrase which is being guessed by the user so the program can check the number of
			     |                           |times the char is in the phrase
     c                       |char                       |Holds the character guessed by the user so it can check how many times it occurs in the phrase
     count                   |int                        |Tracks the number of occurences so it can be returned.
     x                       |int                        |used to control the for loops.
     for loop - used to run for the length of the phrase, so every character of the phrase is checked for the inputted character.
     if statement in the for loop - Used to add 1 to the count of occurences when the character in the specific spot of the phrase is equal to that guessed by the user.
     */
    private int occurencesOf (String s, char c)
    {
	int count = 0;
	for (int x = 0 ; x < s.length () ; x++)
	{
	    if (s.charAt (x) == c)
		count++;
	}
	return count;
    }


    /* This method is used to pause the program so the user can view screens which do not require user input. */
    private void pauseProgram ()
    {
	c.print ("\nPress any key to continue...");
	c.getChar ();
    }


    /* This method is used to draw the spinning wheel and return the value of the wheel. The value of the wheel
    will be between 100 and 1000 if it is round one and q is equal to 1 or 32000 and 1000000 if it is round two
    and q is equal to two.
    Variables
     ------------------------|---------------------------|-------------------------------------------------------------------------------------------
     Name                    |Type                       |Purpose
     ------------------------|---------------------------|-------------------------------------------------------------------------------------------
     q                       |int                        |Used to differentiate between wether it is round one or two. It will hold different values
			     |                           |and colors accoringly.
     i                       |int                        |Tracks how many degrees the wheel will spin for, it will be two less than loopCount.
     loopCount               |int                        |Tracks how long the wheel will spin for (randomly generated). Must be between 360 and 720.
     xLoc                    |array - int                |Used to store the x locations for the triangle at the top of the wheel to be used in fill polygon
     yLoc                    |array - int                |Used to store the y locations for the triangle at the top of the wheel to be used in fill polygon
     x                       |Exception var              |Used to catch the general exception of a Thread.sleep
     do while loop - Used to find a random number until it is between 360 and 720.
     do while loop - Used to spin the wheel one pixel at a time until i is larger than or equal to loopCount minus 2.
     Ternery operators - used to change the color of the wheel depending on if it is round 1 or 2.
		       - used to return different values depending on if it is round 1 or 2.
     if statement - Used to check between which bounds the top of the wheel is (90 degree mark).
     try catch - Used to catch an exception for the Thread.sleep to delay the program.
    */
    private int wheel (int q)
    {
	int i = 0, loopCount;
	do
	{
	    loopCount = (int) (1090000 * Math.random () * 90 / Math.random () / Math.random () * Math.random ());
	}
	while (loopCount > 720 || loopCount < 360);
	int[] xLoc = {315, 325, 335};
	int[] yLoc = {395, 425, 395};
	do
	{
	    i++;
	    c.setColour ((q == 1) ? Color.yellow:
	    new Color (10, 10, 10));
	    c.fillArc (200, 400, 250, 250, loopCount + 0 + i, 15);
	    c.setColour ((q == 1) ? Color.cyan:
	    new Color (20, 20, 20));
	    c.fillArc (200, 400, 250, 250, loopCount + 15 + i, 15);
	    c.setColour ((q == 1) ? Color.red:
	    new Color (30, 30, 30));
	    c.fillArc (200, 400, 250, 250, loopCount + 30 + i, 15);
	    c.setColour ((q == 1) ? Color.orange:
	    new Color (40, 40, 40));
	    c.fillArc (200, 400, 250, 250, loopCount + 45 + i, 15);
	    c.setColour ((q == 1) ? Color.cyan:
	    new Color (50, 50, 50));
	    c.fillArc (200, 400, 250, 250, loopCount + 60 + i, 15);
	    c.setColour ((q == 1) ? Color.red:
	    new Color (60, 60, 60));
	    c.fillArc (200, 400, 250, 250, loopCount + 75 + i, 15);
	    c.setColour ((q == 1) ? Color.yellow:
	    new Color (70, 70, 70));
	    c.fillArc (200, 400, 250, 250, loopCount + 90 + i, 15);
	    c.setColour ((q == 1) ? Color.orange:
	    new Color (80, 80, 80));
	    c.fillArc (200, 400, 250, 250, loopCount + 105 + i, 15);
	    c.setColour ((q == 1) ? Color.black:
	    new Color (90, 90, 90));
	    c.fillArc (200, 400, 250, 250, loopCount + 120 + i, 15);
	    c.setColour ((q == 1) ? Color.yellow:
	    new Color (100, 100, 100));
	    c.fillArc (200, 400, 250, 250, loopCount + 135 + i, 15);
	    c.setColour ((q == 1) ? Color.orange:
	    new Color (110, 110, 110));
	    c.fillArc (200, 400, 250, 250, loopCount + 150 + i, 15);
	    c.setColour ((q == 1) ? Color.red:
	    new Color (120, 120, 120));
	    c.fillArc (200, 400, 250, 250, loopCount + 165 + i, 15);
	    c.setColour ((q == 1) ? Color.yellow:
	    new Color (130, 130, 130));
	    c.fillArc (200, 400, 250, 250, loopCount + 180 + i, 15);
	    c.setColour ((q == 1) ? Color.orange:
	    new Color (140, 140, 140));
	    c.fillArc (200, 400, 250, 250, loopCount + 195 + i, 15);
	    c.setColour ((q == 1) ? Color.cyan:
	    new Color (150, 150, 150));
	    c.fillArc (200, 400, 250, 250, loopCount + 210 + i, 15);
	    c.setColour ((q == 1) ? Color.red:
	    new Color (160, 160, 160));
	    c.fillArc (200, 400, 250, 250, loopCount + 225 + i, 15);
	    c.setColour ((q == 1) ? Color.white:
	    new Color (170, 170, 170));
	    c.fillArc (200, 400, 250, 250, loopCount + 240 + i, 15);
	    c.setColour ((q == 1) ? Color.red:
	    new Color (180, 180, 180));
	    c.fillArc (200, 400, 250, 250, loopCount + 255 + i, 15);
	    c.setColour ((q == 1) ? Color.orange:
	    new Color (190, 190, 190));
	    c.fillArc (200, 400, 250, 250, loopCount + 270 + i, 15);
	    c.setColour ((q == 1) ? Color.red:
	    new Color (200, 200, 200));
	    c.fillArc (200, 400, 250, 250, loopCount + 285 + i, 15);
	    c.setColour ((q == 1) ? Color.yellow:
	    new Color (210, 210, 210));
	    c.fillArc (200, 400, 250, 250, loopCount + 300 + i, 15);
	    c.setColour ((q == 1) ? Color.cyan:
	    new Color (220, 220, 220));
	    c.fillArc (200, 400, 250, 250, loopCount + 315 + i, 15);
	    c.setColour ((q == 1) ? Color.red:
	    new Color (230, 230, 230));
	    c.fillArc (200, 400, 250, 250, loopCount + 330 + i, 15);
	    c.setColour ((q == 1) ? Color.orange:
	    new Color (240, 240, 240));
	    c.fillArc (200, 400, 250, 250, loopCount + 345 + i, 15);
	    c.setColor (Color.green);
	    c.fillOval (275, 470, 100, 100);
	    c.setColor (Color.gray);
	    c.fillPolygon (xLoc, yLoc, 3);
	    try
	    {
		Thread.sleep (i / 120);
	    }
	    catch (Exception x)
	    {
	    }
	}
	while (i < loopCount - 2);
	if (2 * loopCount + 344 >= 795 && 2 * loopCount + 344 <= 810 || 2 * loopCount + 344 >= 1155 && 2 * loopCount + 344 <= 1170 || 2 * loopCount + 344 >= 1515 && 2 * loopCount + 344 <= 1530 ||
		2 * loopCount + 269 >= 795 && 2 * loopCount + 269 <= 810 || 2 * loopCount + 269 >= 1155 && 2 * loopCount + 269 <= 1170 || 2 * loopCount + 269 >= 1515 && 2 * loopCount + 269 <= 1530 ||
		2 * loopCount + 194 >= 795 && 2 * loopCount + 194 <= 810 || 2 * loopCount + 194 >= 1155 && 2 * loopCount + 194 <= 1170 || 2 * loopCount + 194 >= 1515 && 2 * loopCount + 194 <= 1530 ||
		2 * loopCount + 149 >= 795 && 2 * loopCount + 149 <= 810 || 2 * loopCount + 149 >= 1155 && 2 * loopCount + 149 <= 1170 || 2 * loopCount + 149 >= 1515 && 2 * loopCount + 149 <= 1530 ||
		2 * loopCount + 104 >= 435 && 2 * loopCount + 104 <= 450 || 2 * loopCount + 104 >= 795 && 2 * loopCount + 104 <= 810 || 2 * loopCount + 104 >= 1155 && 2 * loopCount + 104 <= 1170 ||
		2 * loopCount + 104 >= 1515 && 2 * loopCount + 104 <= 1530 || 2 * loopCount + 44 >= 435 && 2 * loopCount + 44 <= 450 || 2 * loopCount + 44 >= 795 && 2 * loopCount + 44 <= 810 ||
		2 * loopCount + 44 >= 1155 && 2 * loopCount + 44 <= 1170)
	    return (q == 1) ? 100:
	    32000;
	else if (2 * loopCount + 329 >= 795 && 2 * loopCount + 329 <= 810 || 2 * loopCount + 329 >= 1155 && 2 * loopCount + 329 <= 1170 || 2 * loopCount + 329 >= 1515 && 2 * loopCount + 329 <= 1530 ||
		2 * loopCount + 284 >= 795 && 2 * loopCount + 284 <= 810 || 2 * loopCount + 284 >= 1155 && 2 * loopCount + 284 <= 1170 || 2 * loopCount + 284 >= 1515 && 2 * loopCount + 284 <= 1530 ||
		2 * loopCount + 254 >= 795 && 2 * loopCount + 254 <= 810 || 2 * loopCount + 254 >= 1155 && 2 * loopCount + 254 <= 1170 || 2 * loopCount + 254 >= 1515 && 2 * loopCount + 254 <= 1530 ||
		2 * loopCount + 224 >= 795 && 2 * loopCount + 224 <= 810 || 2 * loopCount + 224 >= 1155 && 2 * loopCount + 224 <= 1170 || 2 * loopCount + 224 >= 1515 && 2 * loopCount + 224 <= 1530 ||
		2 * loopCount + 164 >= 795 && 2 * loopCount + 164 <= 810 || 2 * loopCount + 164 >= 1155 && 2 * loopCount + 164 <= 1170 || 2 * loopCount + 164 >= 1515 && 2 * loopCount + 164 <= 1530 ||
		2 * loopCount + 74 >= 435 && 2 * loopCount + 74 <= 450 || 2 * loopCount + 74 >= 795 && 2 * loopCount + 74 <= 810 || 2 * loopCount + 74 >= 1155 && 2 * loopCount + 74 <= 1170 ||
		2 * loopCount + 29 >= 435 && 2 * loopCount + 29 <= 450 || 2 * loopCount + 29 >= 795 && 2 * loopCount + 29 <= 810 || 2 * loopCount + 29 >= 1155 && 2 * loopCount + 29 <= 1170)
	    return (q == 1) ? 250:
	    40000;
	else if (2 * loopCount + 359 >= 795 && 2 * loopCount + 359 <= 810 || 2 * loopCount + 359 >= 1155 && 2 * loopCount + 359 <= 1170 || 2 * loopCount + 359 >= 1515 && 2 * loopCount + 359 <= 1530 ||
		2 * loopCount + 299 >= 795 && 2 * loopCount + 299 <= 810 || 2 * loopCount + 299 >= 1155 && 2 * loopCount + 299 <= 1170 || 2 * loopCount + 299 >= 1515 && 2 * loopCount + 299 <= 1530 ||
		2 * loopCount + 179 >= 795 && 2 * loopCount + 179 <= 810 || 2 * loopCount + 179 >= 1155 && 2 * loopCount + 179 <= 1170 || 2 * loopCount + 179 >= 1515 && 2 * loopCount + 179 <= 1530 ||
		2 * loopCount + 134 >= 795 && 2 * loopCount + 134 <= 810 || 2 * loopCount + 134 >= 1155 && 2 * loopCount + 134 <= 1170 || 2 * loopCount + 134 >= 1515 && 2 * loopCount + 134 <= 1530 ||
		2 * loopCount + 89 >= 435 && 2 * loopCount + 89 <= 450 || 2 * loopCount + 89 >= 795 && 2 * loopCount + 89 <= 810 || 2 * loopCount + 89 >= 1155 && 2 * loopCount + 89 <= 1170 ||
		2 * loopCount + 89 >= 1515 && 2 * loopCount + 89 <= 1530)
	    return (q == 1) ? 500:
	    45000;
	else if (2 * loopCount + 314 >= 795 && 2 * loopCount + 314 <= 810 || 2 * loopCount + 314 >= 1155 && 2 * loopCount + 314 <= 1170 || 2 * loopCount + 314 >= 1515 && 2 * loopCount + 314 <= 1530 ||
		2 * loopCount + 209 >= 795 && 2 * loopCount + 209 <= 810 || 2 * loopCount + 209 >= 1155 && 2 * loopCount + 209 <= 1170 || 2 * loopCount + 209 >= 1515 && 2 * loopCount + 209 <= 1530 ||
		2 * loopCount + 59 >= 435 && 2 * loopCount + 59 <= 450 || 2 * loopCount + 59 >= 795 && 2 * loopCount + 59 <= 810 || 2 * loopCount + 59 >= 1155 && 2 * loopCount + 59 <= 1170 ||
		2 * loopCount + 14 >= 435 && 2 * loopCount + 14 <= 450 || 2 * loopCount + 14 >= 795 && 2 * loopCount + 14 <= 810 || 2 * loopCount + 14 >= 1155 && 2 * loopCount + 14 <= 1170)
	    return (q == 1) ? 1000:
	    50000;
	else
	{
	    if (2 * loopCount + 239 >= 795 && 2 * loopCount + 239 <= 810 || 2 * loopCount + 239 >= 1155 && 2 * loopCount + 239 <= 1170 || 2 * loopCount + 239 >= 1515 && 2 * loopCount + 239 <= 1530)
		return (q == 1) ? -2:
		1000000;
	}
	return (q == 1) ? -1:
	500000;
    }


    /* This method is used to draw the board and white out a specific amount of tiles depending on the length of the word (will not white out spaces).
     Variables
     ------------------------|---------------------------|-------------------------------------------------------------------------------------------
     Name                    |Type                       |Purpose
     ------------------------|---------------------------|-------------------------------------------------------------------------------------------
     blocks                  |String                     |Holds the phrase so the method knows what should be whited out and what should be left green
     x                       |int                        |used to control the for loop.
     j                       |int                        |used to control the for loop in the other for loop.
     for loop - Runs until the counter is equal to the length of the phrase. This is so a white block can be drawn if the character is a letter or
	nothing happens if the character is a space.
     if statement in the for loop - Used to determine which blocks to white out and which to leave green.
     for loop - runs three times to make the three rows of tiles on the board.
     for loop - runs 23 times to make the 23 columns of tiles on the board.
     */
    private void board (String blocks)
    {
	c.setCursor (7, 1);
	c.setColor (Color.green);
	c.fillRect (10, 100, 575, 60);
	c.setColor (Color.white);
	for (int x = 0 ; x < blocks.length () ; x++)
	{
	    if (blocks.charAt (x) != ' ')
		c.fillRect (10 + x * 25, 120, 25, 20);
	}
	c.setColor (Color.black);
	for (int x = 0 ; x < 3 ; x++)
	{
	    for (int j = 0 ; j < 23 ; j++)
		c.drawRect (10 + j * 25, 100 + x * 20, 25, 20);
	}
    }


    /* This method is used to draw the remaining letters which can be selected by the user.
     Variables
     ------------------------|---------------------------|-------------------------------------------------------------------------------------------
     Name                    |Type                       |Purpose
     ------------------------|---------------------------|-------------------------------------------------------------------------------------------
     highlight               |String                     |Stores all of the letters entered by the user in one string.
     select                  |boolean                    |Stores if the user is selecting a vowel or not and greys out all not accepted user input.
     x                       |int                        |used to control the for loops.
     for loop - runs 26 times, once to check every letter in the alphabet.
    if statement - Used to determine which letters to highlight (consonants or vowels)
    ternary operators - Used to set the colors depending on which letters should be highlighted (consonants or vowels), the color for each letter is set
			seperately depending on if it matches a vowel or not.
    */
    private void characterSelect (String highlight, boolean select)
    {
	c.setColor (new Color (6, 145, 242));
	c.fillRect (15, 184, 550, 30);
	c.setFont (new Font ("Times New Roman", 0, 20));
	c.setColor (Color.black);
	for (int x = 0 ; x < 26 ; x++)
	{
	    if (select)
	    {
		c.setColor ((highlight.charAt (x) + "").matches ("[a|e|i|o|u]") ? (Color.gray):
		(Color.black));
	    }
	    else
	    {
		c.setColor ((highlight.charAt (x) + "").matches ("[a|e|i|o|u]") ? (Color.black):
		(Color.gray));
	    }
	    c.drawString (highlight.charAt (x) + " ", 35 + x * 20, 204);
	}
    }


    /* This method is used to draw the correct user input on the board.
     Variables
     ------------------------|---------------------------|-------------------------------------------------------------------------------------------
     Name                    |Type                       |Purpose
     ------------------------|---------------------------|-------------------------------------------------------------------------------------------
     arr                     |array - char               |Stores the correct user input in the location of that letter.
     s                       |String                     |Stores the phrase which the user is trying to guess.
     guesses                 |String                     |Stores the correct user input to be put on the board.
     x                       |int                        |used to control the for loops.
     j                       |int                        |used to control the for loop within the for loop.
     for loop - declares all the values of the array as a ' '.
     for loop - runs as many times as the length of the phrase so each character in the phrase is checked.
     for loop in the for loop - runs as many times as the amount of guesses so each guess is checked against each character.
     if statement in both for loops - Used to determine whether or not any of the guesses are in the phrase at the location decided by the for loop
	(checks each letter of guesses to each letter of the phrase.
     for loop - used to draw each character of the array on the board, runs for the length of the phrase.
    */
    private void drawResponse (String guesses, String s)
    {
	char[] arr = new char [s.length ()];
	for (int x = 0 ; x < arr.length ; x++)
	    arr [x] = ' ';
	for (int x = 0 ; x < s.length () ; x++)
	{
	    for (int j = 0 ; j < guesses.length () ; j++)
	    {
		if (guesses.charAt (j) == s.charAt (x))
		    arr [x] = guesses.charAt (j);
	    }
	}
	c.setColor (Color.black);
	c.setFont (new Font ("Times New Roman", 0, 12));
	for (int x = 0 ; x < arr.length ; x++)
	{
	    c.drawString (arr [x] + " ", 22 + x * 25, 133);
	}
    }


    /* This method draws the amount of money that each player has and the value spun on the wheel (this will vary depending on what the wheel lands on)
       It also highlights which player's turn it is in yellow. It also does the processing for how much money to give each player after their turn.
    Variables
     ------------------------|---------------------------|-------------------------------------------------------------------------------------------
     Name                    |Type                       |Purpose
     ------------------------|---------------------------|-------------------------------------------------------------------------------------------
     onesTurn                |boolean                    |Holds whether or not it is player one's turn so that it knows which one to highlight.
     spunMoney               |int                        |Stores the value of the wheel so it can add that times the number of occurences of the user's
			     |                           |input.
     times                   |int                        |Stores the occurences of the user's guess to multiply the spun value by
    1st if statement - Used to determine what color to make the spun value so it matches the color on the wheel.
    2nd if statement - Used to draw the value of the wheel or special wheel spins (i.e. bankrupt).
    3rd if statement - Used to either highlight or not highlight player one's money area including their name, aswell as adding to player one and player
		       two's bank. The if statement within this if statement will either make player one or player two's money 0 if they land on bankrupt
		       or give them their money if they dont.
    4th if statement - Used to either highlight or not highlight player two's money area and their name.
    */
    private void money (boolean onesTurn, int spunMoney, int times)
    {
	c.setColor (new Color (6, 155, 242));
	c.fillRect (18, 270, 200, 100);
	c.fillRect (418, 270, 200, 100);
	c.setColor (new Color (6, 185, 242));
	c.fillRect (218, 270, 200, 100);
	c.setFont (new Font ("Cambria", 0, 32));
	if (spunMoney == 100)
	    c.setColor (Color.orange);
	else if (spunMoney == 250)
	    c.setColor (Color.red);
	else if (spunMoney == 500)
	    c.setColor (Color.yellow);
	else
	    c.setColor (Color.cyan);
	if (spunMoney == -1)
	{
	    c.setColor (Color.black);
	    c.drawString ("Bankrupt", 230, 350);
	}
	else if (spunMoney == -2)
	{
	    c.setColor (Color.white);
	    c.drawString ("Skip a Turn", 230, 350);
	}
	else
	    c.drawString ("$ " + spunMoney, 230, 350);
	c.setColor (Color.black);
	c.drawString ("Spun value", 230, 310);
	if (onesTurn)
	{
	    c.setColor (Color.yellow);
	    c.setFont (new Font ("Times New Roman", 0, 30));
	    if (spunMoney == -1)
		playerOneMoney = 0;
	    else
		playerOneMoney += times * spunMoney;
	}
	else
	{
	    if (spunMoney == -1)
		playerTwoMoney = 0;
	    else
		playerTwoMoney += times * spunMoney;
	    c.setColor (Color.black);
	    c.setFont (new Font ("Times New Roman", 0, 20));
	}
	c.drawString (playerOne + "", 20, 310);
	c.drawRect (20, 320, 100, 40);
	c.drawString ("$ " + playerOneMoney, 20, 350);
	if (!onesTurn)
	{
	    c.setColor (Color.yellow);
	    c.setFont (new Font ("Times New Roman", 0, 32));
	}
	else
	{
	    c.setColor (Color.black);
	    c.setFont (new Font ("Times New Roman", 0, 22));
	}
	c.drawString (playerTwo + "", 420, 310);
	c.drawRect (420, 320, 100, 40);
	c.drawString ("$ " + playerTwoMoney, 420, 350);

    }


    /* This method draws the amount of money that each player has and the value spun on the wheel (this will vary depending on what the wheel lands on)
       It also highlights which player's turn it is in yellow. It also does the processing for how much money to give each player after their turn.
       Variables
       ------------------------|---------------------------|-------------------------------------------------------------------------------------------
       Name                    |Type                       |Purpose
       ------------------------|---------------------------|-------------------------------------------------------------------------------------------
       randPhrase              |int                        |Stores a random value between 0 and 10 to decide which of the 10 phrases to use.
       tempStr                 |String                     |Stores and returns the randomly selected phrase.
       do while loop - finds a random number between 0 and 9 until that location in the array does not equal "hahaha" which is declared once the phrase
	    is used.
       */
    private String phrases ()
    {
	int randPhrase = -1;
	String tempStr;
	do
	{
	    randPhrase = (int) (Math.random () * 10);
	}
	while (PHRASES [randPhrase].equals ("hahaha"));
	tempStr = PHRASES [randPhrase];
	PHRASES [randPhrase] = "hahaha";
	return tempStr;
    }


    /* This method recieve all in game user input from the user and stores it in a global variable to be used in the game() method
       Variables
       ------------------------|---------------------------|-------------------------------------------------------------------------------------------
       Name                    |Type                       |Purpose
       ------------------------|---------------------------|-------------------------------------------------------------------------------------------
       tempStr                 |String                     |temporarily stores user input as a string before it is cast to a character. This is for
			       |                           |errortrapping purposes.
       inNum                   |int                        |Used to store the ascii value of the character minus 97 to give it it's location in the
			       |                           |alphabet (a=0)
       while loop - Used to recieve user input until the user enters a valid selection. Will always start, and will stop when correct input is recieved.
       1st if statement - Used to only get character user input if asker doesn't equal 4, otherwise it will ask for the entire phrase.
       if statements within first if statement - Changes the question posed to the user depending on what the accepted user input is.
       2nd if statement - Used to ask for the entire phrase if input is a '.' (either entered by the user or if it is the final round set by the programmer)
			  If the user enters a ',' then it returns to the main.
       3rd if statement - Used to display different error messages depending on what the user inputs and what is acceptable input.
			  it will break the loop if the user enters proper input.
       */
    private void askData (int asker)
    {
	String tempStr = "";
	choice = "";
	while (true)
	{
	    if (asker != 4)
	    {
		dTitle ();
		if (asker == 1)
		    d.println ("Please enter a lower case consonant, a  ',' to return to the main menu, or a '.'to guess the answer.");
		if (asker == 3)
		    d.println ("Please enter a lower case consonant");
		else
		{
		    if (asker == 2)
			d.println ("Please enter a lower case vowel");
		}
		tempStr = (d.readString ());
		input = tempStr.charAt (0);
	    }
	    if (input == '.')
	    {
		choice = JOptionPane.showInputDialog ("What is your guess?", "Guess!");
		if (choice == null)
		    choice = "[-v-]";
		break;
	    }
	    else
	    {
		if (input == ',')
		    return;
	    }
	    int inNum = input - 97;
	    if (input != '.' && inNum < 0 || tempStr.length () > 1)
		JOptionPane.showMessageDialog (null, "Please guess a valid lower case letter.");
	    else if (asker == 1 && (input + "").matches ("[a|e|i|o|u]"))
	    {
		JOptionPane.showMessageDialog (null, "Please select only a consonant.");
	    }
	    else if (asker == 2 && !(input + "").matches ("[a|e|i|o|u]"))
	    {
		JOptionPane.showMessageDialog (null, "Please select only a vowel.");
	    }
	    else if (alpha.charAt (inNum) == ' ')
		JOptionPane.showMessageDialog (null, "Please guess a letter that has not already been selected.");
	    else
	    {
		alpha = alphab (inNum);
		break;
	    }
	}

    }


    /* This method remakes the alphabet without the character selected by the user as well as all other characters selected by the user.
       Variables
       ------------------------|---------------------------|-------------------------------------------------------------------------------------------
       Name                    |Type                       |Purpose
       ------------------------|---------------------------|-------------------------------------------------------------------------------------------
       alphab                  |String                     |Stores and returns the new alphabet without the already guessed characters.
       letter                  |int                        |Stores the most recent user input so that it can be removed from the alphabet.
       x                       |int                        |used to control the for loops.
       for loop - Used so that each letter can be decided to add to the alphabet or not (runs 26 times once for every letter of the alphabet).
       if statement - Decides to add each letter to the alphabet or add a space in it's place.
       */
    private String alphab (int letter)
    {
	String alphab = "";
	for (int x = 0 ; x < 26 ; x++)
	{
	    if (letter == x)
		alphab += " ";
	    else
		alphab += alpha.charAt (x);
	}
	return alphab;
    }



    /* This method makes a box telling the user which letters have already been guessed for them.*/
    private void envelopeChar ()
    {
	c.clearRect (20, 230, 300, 100);
	c.setFont (new Font ("Arial", 0, 22));
	c.setColor (Color.black);
	c.drawString ("The letters given to you are:", 30, 250);
	c.drawString ("'r', 's', 't', 'l', 'n', and 'e'.", 30, 280);
    }


    /* This method calls the title method to clear the screen as well as draws the wheel of fortune board and reveals the word "wheel of fortune". The
       wheel will spin and a second wheel will bounce back and forth across the screen until the phrase is finished being revealed.
       ------------------------|---------------------------|-------------------------------------------------------------------------------------------
       Name                    |Type                       |Purpose
       ------------------------|---------------------------|-------------------------------------------------------------------------------------------
       titlePhrase             |String                     |Stores the value of the word which the program will slowly guess. This will always be
			       |                           |"Wheel of Fortune".
       word                    |String                     |Stores the guesses from the computer so they output 1 by 1 on the board.
       a                       |reference                  |It allows the program to use the methods in the Animations class.
       e                       |Exception var              |Used to catch the general exception of a Thread.sleep
       l                       |int                        |used to control the for loops.
       for loop - Runs 19 times so the program can "guess" each letter of the word and reveal the letters 1 by 1.
       try catch - Used to catch an exception for the Thread.sleep to delay the program.
       */
    public void splashScreen ()
    {
	String titlePhrase = "   Wheel of Fortune", word = "";
	title ();
	board (titlePhrase);
	characterSelect (alpha, true);
	Animation a = new Animation (c);
	a.start ();
	wheel (1);
	for (int l = 0 ; l < 19 ; l++)
	{
	    word += titlePhrase.charAt (l);
	    c.setColor (Color.black);
	    drawResponse (word, titlePhrase);
	    try
	    {
		Thread.sleep (100);
	    }
	    catch (Exception e)
	    {
	    }
	}
	a.stop ();
    }


    /* This method calls title and it recieves user input saying which option the user would like to do. In this main menu, options are selected by
    scrolling to the option using the 's' key to go down and the 'w' key to move up. Once the image of the wheel id beside the desired option, the 'd'
    key should be pressed this will select that option.
    Variables
    ------------------------|---------------------------|-------------------------------------------------------------------------------------------
    Name                    |Type                       |Purpose
    ------------------------|---------------------------|-------------------------------------------------------------------------------------------
    y                       |int                        |Used to set the location of the image (changes depending on user input).
    select                  |char                       |Stores the user input and either moves the image up or down, or selects an option.
    1st if statement - Makes it so the instructions on how to use the menu only shows if choice does not have a value.
    while loop - Used so user input is recieved until the user selects 'd'.
    if statement in the while loop - adds or subtracts 80 to the y location of the image depending on what the user inputs.
    2nd if statement - redeclares choice depending on what the image is beside so that the main knows which method(s) to run.
    */
    public void mainMenu ()
    {
	int y = 150;
	char select = ' ';
	if (choice == "")
	    JOptionPane.showMessageDialog (null, "To navigate through the main menu use 'w' to move upwards, 's' to move downwards, and 'd' to select the option that the wheel is beside.");
	while (select != 'd')
	{
	    title ();
	    loadImage (PICTURE_PATH_BACK, 0, 80);
	    c.setColor (Color.cyan);
	    c.setFont (new Font ("Harlow Solid Italic", 0, 52));
	    c.drawString ("The choices are:", 0, 130);
	    c.drawString ("Play", 120, 200);
	    c.drawString ("High Scores", 120, 280);
	    c.drawString ("Instructions", 120, 360);
	    c.drawString ("Exit", 120, 440);
	    loadImage (PICTURE_PATH_WHEEL, 10, y);
	    select = c.getChar ();
	    if (select == 'w' && y > 150)
		y -= 80;
	    else if (select == 's' && y < 390)
		y += 80;
	}
	if (y == 150)
	    choice = "1";
	else if (y == 230)
	    choice = "2";
	else if (y == 310)
	    choice = "3";
	else
	{
	    if (y == 390)
		choice = "4";
	}
    }


    /* This method clears the screen, gives the instructions to the user, and waits for the user to click any key to continue.*/
    public void instructions ()
    {
	title ();
	c.println ("\tThis program will allow two users to play a complete game of Wheel Of");
	c.println ("Fortune. First two players must enter their names. The wheel will then spin and");
	c.println ("player one will get to guess a consonant. If the consonant is correct they will");
	c.println ("recieve the spun value of the wheel times the number of occurences of the");
	c.println ("letter. Once they recieve their money, if they have over 250 dollars then they");
	c.println ("will be given the option to buy a vowel. If the letter is wrong then it will");
	c.println ("change to the other person's turn. This will continue until all the letters in");
	c.println ("the word are guessed or a player selects a '.' and then guesses the correct");
	c.println ("word. Once this round is done, whoever wins the round will recieve a $10,000");
	c.println ("bonus. Then they will continue to the final round. The final round is a one");
	c.println ("player round. The player spins the wheel and is given an envelope with a hidden");
	c.println ("value. The user is then given the letters 'r', 's', 'l', 'n', 't', and 'e'.");
	c.println ("They will then get to select three consonants and one vowel. Once they guess");
	c.println ("these letters a message will pop up. Once they click ok on the message a 10");
	c.println ("second timer will begin. The user has until the timer finishes to guess the");
	c.println ("word. If the user guesses the phrase in the given amount of time, they will");
	c.println ("recieve the amount of money which was in the envelope. If they do not, they");
	c.println ("will see the value but not get it. If the user recieves a new high score");
	c.println ("(top ten players) then they will be added to the high score board. Good luck!");
	pauseProgram ();
    }


    /* This method sorts the high scores by amount of money won, and will edit the highscore file if the user gets a new highscore. It will only read
       the file once, and then the values will be stored in a global array.
    Variables
    ------------------------|---------------------------|-------------------------------------------------------------------------------------------
    Name                    |Type                       |Purpose
    ------------------------|---------------------------|-------------------------------------------------------------------------------------------
    draw                    |boolean                    |This boolean makes sure that the file is only rewritten if the method is accessed after a
			    |                           |game is won.
    fileFound               |boolean                    |This boolean is used to check if the file is found. If it is not, the program will ask if
			    |                           |the user would like to create a new one.
    in                      |reference - BufferedReader |The variable is used to open and access the highscores file. It will read past highscores
			    |                           |from the file oonce, the first time it is accessed.
    out                     |reference - PrintWriter    |The variable is used to open and access the highscores file. It will re-write the file
			    |                           |using the global arrays which store the highscores everytime a new highscore is recieved.
    n                       |int                        |used to store the value of the JOptionPane selection and returns it to where it is called
    e                       |NumberFormatException var  |Used to catch when the Exception has been thrown, and used if the input is not of a number value.
    e                       |IOException var            |Used when a file input output exception has occured.
    e                       |FileNotFoundException var  |Used when the file is not found to catch the exception.
    x                       |int                        |used to control the for loops.
    do while loop - runs until the file is read (if it is called from the highscores method), the user selects no when asked if they would like to
	over-write a found but corrupted file, if the highscores file is successfully updated, if the user selects to not make a new file when none
	are found, or when the file cannot be written due to not enough permissions.
    try catch - used to errortrap against file writing issues, file reading issues, and currupt files (scores are not integers).
    1st if statement - used so that the highscores file is only read once (times is equal to true) and to make sure the file that is being read from
	is found.
    if statement in if statement - Used to ensure the program does not write over any important files (makes sure the file was created by this program,
	and if it wasnt gives them an option to overwrite it.
    for loop in if statement - reads from the file a maximum of ten (MAX_SCORES) times. If the name line is blank, the for loop will break.
    if statement in if statement -  breaks the loop if the method is being accessed from the main menu and the file has been successfully read.
    2nd if statement - runs the writing segment of code if the winner's name and bank are not empty, and this method is being accessed after a game
	is played
    for loop in 2nd if statement - runs 10 (MAX_SCORES) times to write the new highscores to the file in order.
    if statement in for loop - Used to output the playerName and score if the name is null and the file was blank from the start. Otherwise, if the
	name is null and the name was never added then it will output the recent non-stored information, if it isnt, the for loop will be broken.
    if statement in for loop- Used to verify if the score that the player currently played is higher than the score in the array, and if so then the next score
	will be outputted to the file.
    if statement in for loop- Used to verify if the name is null (because it may have not been caught previously), and then output the array values.
    if statement in catch - Used to exit the method if the user selects not to make a new file when one is not found.
    */
    public void setHighScores (boolean draw)
    {
	boolean fileFound = true;
	int n;
	do
	{
	    try
	    {
		if (fileFound == true && times)
		{
		    BufferedReader in = new BufferedReader (new FileReader ("highScores.wof"));
		    if (!in.readLine ().equals ("Wheel of Fortune by Alex Barkin"))
		    {
			n = yesNoOption ("Replace", "The high scores file is not made with this program. Would you like to replace it?");
			if (n == 1)
			    return;
		    }
		    for (int x = 0 ; x < MAX_SCORES ; x++)
		    {
			names [x] = in.readLine ();
			if (names [x] == null)
			    break;
			scores [x] = Integer.parseInt (in.readLine ());
		    }
		    times = false;
		    if (draw)
			break;
		}
		if (playerOne != "" && playerOneMoney != 0 && !draw)
		{
		    PrintWriter out = new PrintWriter (new FileWriter ("highScores.wof"));
		    out.println ("Wheel of Fortune by Alex Barkin");
		    for (int x = 0 ; x < MAX_SCORES ; x++)
		    {
			if (names [x] == null && x == 0 && hasAdded == false)
			{
			    names [x] = playerOne;
			    scores [x] = playerOneMoney;
			    hasAdded = true;
			}
			else
			    if (names [x] == null)
			    {
				if (x != 0 && playerOneMoney <= scores [x - 1] && hasAdded == false)
				{
				    names [x] = playerOne;
				    scores [x] = playerOneMoney;
				    out.close ();
				}
				hasAdded = true;
				break;
			    }
			if (scores [x] <= playerOneMoney && names [x] != null && hasAdded == false)
			{
			    hasAdded = true;
			    names [x] = playerOne;
			    scores [x] = playerOneMoney;
			}
			if (names [x] != null)
			{
			    out.println (names [x]);
			    out.println (scores [x]);
			}
		    }
		    out.close ();
		    break;
		}
	    }
	    catch (NumberFormatException e)
	    {
	    }
	    catch (FileNotFoundException e)
	    {
		n = yesNoOption ("New", "The high scores file cannot be found. Would you like to make a new one?");
		if (n != 0)
		    return;
		fileFound = false;
	    }
	    catch (IOException e)
	    {
		JOptionPane.showMessageDialog (null, "File cannot be saved.");
		return;
	    }
	}
	while (true);
    }


    /* This method calls title and it displays all of the high scores to the user. It will go back to the main menu when any key is selected.
    Variables
    ------------------------|---------------------------|-------------------------------------------------------------------------------------------
    Name                    |Type                       |Purpose
    ------------------------|---------------------------|-------------------------------------------------------------------------------------------
    x                       |int                        |used to control the for loops.
    1st if statement - Used to only call setHighScores and read from the file if it has not been done previously. This will ensure that the
		       file is only read from once.
    for loop - Used to display each high score line by line.
    if statement in the for loop - Used to only draw the highscores if they aren't null.
    */
    public void highScores ()
    {
	title ();
	if (times == true)
	    setHighScores (true);
	c.setFont (new Font ("TimesNewRoman", Font.BOLD, 12));
	for (int x = 0 ; x < 10 ; x++)
	{
	    if (names [x] != null)
		c.drawString (x + 1 + "): " + names [x] + " got a score of: $" + scores [x], 100, 200 + x * 20);
	}
	pauseProgram ();
    }


    /* This method makes the first round of the wheel of fortune game. The users can enter a ',' to go back to the main menu or click cancel when
	they are asked to enter their names. The users will guess each letter until they are all done or the can enter a '.' to guess the phrase.
    Variables
    ------------------------|---------------------------|-------------------------------------------------------------------------------------------
    Name                    |Type                       |Purpose
    ------------------------|---------------------------|-------------------------------------------------------------------------------------------
    phrase                  |String                     |Used to store the phrase that is randomly generated for the user to guess.
    guesses                 |String                     |Stores all of the user input so that the correct letters can be shown on the board.
    guessLength             |int                        |Stores the amount of correct user input (used to compare to wordLength).
    n                       |int                        |used to store the value of the JOptionPane selection and returns it to where it is called.
    vowelNum                |int                        |Stores the number of vowels in the phrase.
    moneyWon                |int                        |Stores the current value on the wheel or the price of a vowel(-$250).
    i                       |int                        |Used to decide whether or not to switch the player's turn if i is greater than or equal to 1.
    wordLength              |int                        |Stores the length of the phrase without spaces.
    skipTurn                |boolean                    |Stores whether or not to skip a players turn. Only true if they land on skip a turn.
    vowels                  |boolean                    |Stores whether there are any more vowels or not.
    vowQuestion             |boolean                    |This is used to ask the user if they would like to buy a vowel or not. That is decided on
			    |                           |wether there are enough vowels, if they just got a letter correct, and the user has enough money.
    x                       |Exception var              |Used to catch the general exception of a Thread.sleep.
    j                       |int                        |used to control the for loops.
    for loop - used to check every letter of the phrase and count it's length without spaces and contractions.
    if statement in for loop - if the character in the for loop does not equal a space or a contraction, 1 is added to the variable which stores the
	length of the word.
    if statement - used so that user input is only recieved if either user has not already entered a name.
    while loop in if statement - used to recieve player one's user name until they enter a value or click cancel.
    if statement in while loop - used to check if user 1's input is allowed or if they clicked cancel it returns to main.
    2nd while loop in if statement -  used to recieve player two's user name until they enter a value or click cancel.
    2nd if statement in while loop - used to check if user 2's input is allowed or if they clicked cancel it returns to main.
    for loop - finds the number of vowels in the word and stores it in the variable choice.
    do while loop - used to run the game until the user selects a comma, guesses the word after clicking a '.', or guesses each letter in the word.
    if statement in do while - used to declare the vowels variable as eithere true or false depending on whether or not there are any vowels that
	have not been guessed. It will also tell the user once when there are no more vowels.
    2nd if statement in do while - adds the letter guessed by the user to a variable which holds all correct letters guessed by the user.
    3rd if statement in do while - Used to only change who's turn it is if the wheel is not on skip a turn, if the user doesn't click cancel after
	selecting that they want to guess the word, or if the wheel is equal to bankrupt.
    4th if statement in do while - Used to delay the running of the game so that the amount on the wheel (if it is bankrupt or skip a turn stays
	there long enough for the user to read.
    try catch in the 4th if statement - used to catch the exception thrown by thread.sleep.
    5th if statement in do while - used to only update the user's bank value if they do not select a vowel (this makes it so they dont gain money
	for buying a vowel.
    6th if statement - used to either ask the user if they would like to buy a vowel (if they have enough money, just guessed a correct letter,
	and there are still vowels to be guessed). Otherwise it will ask for a consonant.
    if statement in the 6th if statement - used to either go to guessing a vowel if the user selects yes to would you like to buy a vowel, or
	makes sure that the user gets to guess another consonant if the user selects no to would you like to buy a vowel.
    2nd if statement in the 6th if statement - used to only spin the wheel and ask for a consonant when the user clicks cancel after asking to
	guess the word
    3rd if statement in the 6th if statement - Only asks for a consonant if the user does not land on bankrupt or skip a turn.
    4th if statement in the 6th if statement - Breaks the loop if the correct word is guessed or all the letters in the word have been guessed.
    5th if statement in the 6th if statement - Displays that the user has not guessed the correct phrase if the do not guess the correct phrase.
    */
    public void game ()
    {
	String phrase = phrases (), guesses = "'";
	int guessLength = 0, n = 290, count = 0, vowelNum = 0, moneyWon = 0, i = -1, wordLength = 0;
	boolean spin = true, skipTurn = false, vowels = false, vowQuestion = true;
	for (int j = 0 ; j < phrase.length () ; j++)
	{
	    if (phrase.charAt (j) != ' ' && !((phrase.charAt (j) + "").equals ("'")))
		wordLength++;
	}
	playerOneMoney = 0;
	playerTwoMoney = 0;
	alpha = "abcdefghijklmnopqrstuvwxyz";
	title ();
	loadImage (PICTURE_PATH_VORTEX, -20, 80);
	if (playerOne == "" || playerTwo == "" || playerOne == null || playerTwo == null)
	{
	    while (true)
	    {
		playerOne = JOptionPane.showInputDialog ("Player one, what is your username? (less than 15 characters)", "Player One");
		if (playerOne == null)
		    return;
		else
		{
		    if (playerOne.length () < 15 && playerOne.length () != 0)
			break;
		}
		JOptionPane.showMessageDialog (null, "Player one's name must be less than 15 characters, and not be blank.");
	    }
	    while (true)
	    {
		playerTwo = JOptionPane.showInputDialog ("Player two, what is your username? (less than 15 characters)", "Player Two");
		if (playerTwo == null)
		    return;
		else
		{
		    if (playerTwo.length () < 15 && playerTwo.length () != 0)
			break;
		}
		JOptionPane.showMessageDialog (null, "Player two's name must be less than 15 characters, and not be blank.");
	    }
	}
	d = new Console (10, 40, "Wheel Of Fortune Input");
	dTitle ();
	count = 0;
	for (int j = 0 ; j < phrase.length () ; j++)
	{
	    if ((phrase.charAt (j) + "").matches ("[a|e|i|o|u]"))
		count++;
	}
	do
	{
	    if ((count - vowelNum) > 0)
		vowels = true;
	    else
	    {
		if (vowQuestion)
		{
		    JOptionPane.showMessageDialog (null, "Sorry, there are no more vowels.");
		    vowQuestion = false;
		}
		vowels = false;
	    }
	    i++;
	    board (phrase);
	    if (occurencesOf (phrase, input) != 0)
	    {
		guesses += input;
	    }
	    else
	    {
		if (!skipTurn && choice != "[-v-]" || moneyWon == -1)
		{
		    if (i >= 1)
		    {
			if (oneTurn == true)
			    oneTurn = false;
			else
			    oneTurn = true;
		    }
		}
		else
		{
		    if (oneTurn == true)
			oneTurn = true;
		    else
			oneTurn = false;
		    skipTurn = true;
		}
	    }
	    if (moneyWon == -2)
	    {
		try
		{
		    Thread.sleep (1000);
		}
		catch (Exception x)
		{
		}
		skipTurn = true;
		moneyWon = 0;
	    }
	    else
	    {
		if (moneyWon == -1)
		{
		    try
		    {
			Thread.sleep (1500);
		    }
		    catch (Exception x)
		    {
		    }
		}
	    }
	    if (moneyWon != -250)
		money (oneTurn, moneyWon, occurencesOf (phrase, input));
	    characterSelect (alpha, true);
	    drawResponse (guesses, phrase);
	    if ((i >= 1 && occurencesOf (phrase, input) != 0 && ((oneTurn && !(playerOneMoney < 250)) || (!oneTurn && !(playerTwoMoney < 250)))) & vowQuestion)
	    {
		n = yesNoOption ("Yes", "Would you like to buy a vowel for $250");
		if (n == JOptionPane.YES_OPTION)
		{
		    moneyWon = -250;
		    money (oneTurn, moneyWon, 1);
		    characterSelect (alpha, false);
		    askData (2);
		    guessLength += occurencesOf (phrase, input);
		    vowelNum += occurencesOf (phrase, input);
		    if (wordLength == guessLength)
			break;
		}
		if (n == JOptionPane.NO_OPTION)
		{
		    i = -1;
		    input = '(';
		}

	    }
	    else
	    {
		if (input != ' ' && choice != "[-v-]")
		{
		    if (n == JOptionPane.YES_OPTION)
		    {
			moneyWon = 0;
			n = 290;
		    }
		    money (oneTurn, moneyWon, 0);
		    moneyWon = wheel (1);
		    money (oneTurn, moneyWon, 0);
		}
		if (moneyWon != -1 && moneyWon != -2)
		    askData (1);
		guessLength += occurencesOf (phrase, input);
		if (wordLength == guessLength || choice.equalsIgnoreCase (phrase) || input == ',')
		    break;
		if (input == '.' && choice != "[-v-]")
		{
		    i = 1;
		    JOptionPane.showMessageDialog (null, "Sorry, you did not guess the correct answer.");
		}
	    }
	}
	while (wordLength != guessLength || !choice.equalsIgnoreCase (phrase));
	d.close ();
    }


    /* This method calls title to clear the screen and displays the winner of round one. It also decides the winner, and adds 10000 dollars to their bank.
       It will wait for any key to be pressed before continuing to the final round.
       if statement - Used to redeclare player one's name and score as player two's if player two wins.
    */
    public void results ()
    {
	title ();
	if (!(playerOneMoney > playerTwoMoney) && !oneTurn)
	{
	    playerOne = playerTwo;
	    playerOneMoney = playerTwoMoney;
	}
	playerOneMoney += 10000;
	c.setColor (Color.cyan);
	c.fillRect (0, 120, 40, 220);
	c.fillRect (600, 120, 40, 220);
	c.fillRect (0, 300, 640, 40);
	c.fillRect (0, 80, 640, 40);
	c.setColor (Color.green);
	c.setFont (new Font ("Arial", 0, 22));
	c.drawString ("Congratulations " + playerOne + "!", 170, 160);
	c.drawString ("You get $10000 as a bonus for solving the problem", 70, 200);
	c.drawString ("Your current amount of money won is " + playerOneMoney + "!", 90, 240);
	c.setCursor (19, 29);
	c.println ("to the final round!");
	c.setCursor (18, 1);
	pauseProgram ();
    }


    /* This method calls title and it makes the second round of the game. It creates a second window for user input.
    Variables
    ------------------------|---------------------------|-------------------------------------------------------------------------------------------
    Name                    |Type                       |Purpose
    ------------------------|---------------------------|-------------------------------------------------------------------------------------------
    phrase                  |String                     |Stores the phrase which the user is trying to guess.
    guesses                 |String                     |Stores the user input to be put on the board.
    timer                   |Boolean                    |Stores whether or not the user guesses in time to win the money in the envelope.
    time                    |long                       |Stores the start time of the timer to see if the user guesses the phrase in the allotted 10 seconds
    j                       |int                        |used to control the for loops.
    for loop - Runs 10 times to recieve the 10 characters (6 pre-selected, 4 selected by the user).
    if statement in the for loop - Used to either declare input as a predetermined value, or recieve different types of user input, depending on the
				   value of the for loop.
    2nd if statement in the for loop - Changes the selectable letters, only called if the user selects the letter.
    while loop - Used to get the users guess until it is correct or the 10 second timer is complete.
    if statement in the while loop - redeclares the timer variable if the 10 second timer is complete and the user does not guess the answer.
    if statement - Used to either give the player the envelope money, if they win, or tell them that they lost if they lose.
    */
    public void finalRound ()
    {
	alpha = "abcdefghijklmnopqrstuvwxyz";
	String phrase = phrases (), guesses = "'";
	boolean timer = true;
	title ();
	d = new Console (12, 40, "Wheel Of Fortune Input");
	dTitle ();
	envelopeChar ();
	board (phrase);
	characterSelect (alpha, true);
	envelope = wheel (2);
	loadImage (PICTURE_PATH_ENVELOPE, 420, 230);
	for (int j = 0 ; j < 10 ; j++)
	{
	    if (j == 9)
	    {
		characterSelect (alpha, false);
		askData (2);
	    }
	    else if (j == 0)
		input = 'r';
	    else if (j == 1)
		input = 's';
	    else if (j == 2)
		input = 't';
	    else if (j == 3)
		input = 'l';
	    else if (j == 4)
		input = 'n';
	    else if (j == 5)
		input = 'e';
	    else
	    {
		characterSelect (alpha, true);
		askData (3);
	    }
	    if (j < 6)
	    {
		alpha = alphab (input -= 97);
		input += 97;
	    }
	    guesses += input;
	}
	d.close ();
	input = '.';
	JOptionPane.showMessageDialog (null, "You now have 10 seconds to guess the answer!");
	long time = System.currentTimeMillis ();
	drawResponse (guesses, phrase);
	while (!choice.equalsIgnoreCase (phrase) && timer)
	{
	    if (System.currentTimeMillis () - time >= 10000)
		timer = false;
	    askData (4);
	}
	if (timer == false)
	    JOptionPane.showMessageDialog (null, "Sorry, that took longer than 10 seconds and is therefore invalid");
	else
	{
	    playerOneMoney += envelope;
	    choice = "<||>";
	}
    }


    /* This method calls title to clear the screen and displays the amount on the envelope, the players final bank value, and if they got a highscore.
       It will wait for any key to be pressed before continuing to the final round.
       if statement - Used to show the ribbon if the user recieves a new highscore.
    */
    public void finalResults ()
    {
	title ();
	loadImage (PICTURE_PATH_ENVELOPE, 50, 150);
	c.setColor (new Color (236, 192, 154));
	c.fillRect (85, 279, 130, 50);
	if (hasAdded)
	    loadImage (PICTURE_PATH_HIGHSCORE, 300, 100);
	c.setFont (new Font ("Arial", 0, 27));
	c.setColor (Color.black);
	c.drawString ("$" + envelope, 85, 314);
	c.setFont (new Font ("Arial", 0, 37));
	c.setColor (Color.red);
	c.drawString ("You won $" + playerOneMoney + "!", 10, 410);
	c.setCursor (24, 1);
	pauseProgram ();
    }


    /* This method calls the title and says goodbye to the user as well as closes the program.*/
    public void goodbye ()
    {
	title ();
	c.println ("Thank you for viewing this program. I hope you enjoyed it!");
	c.println (" - Alex Barkin");
	pauseProgram ();
	c.close ();
    }


    /* This is my main method which calls all the methods in the proper order
       Name          |type                |purpose
       --------------|--------------------|---------------------------------------
       wof           |reference           |To call upon methods in this class in
		     |                    |the main method
       if statement - in this method is used to control program flow by either
	   running the game if the user enters 1, showing the highscores if the
	   user enters 2, show the instructions if the user selects 3, or calling
	   goodbye if the user enters 3.
       if statement in the if statement - if the user selects cancel on either of
	   the name inputs or enters a comma in round one then it will continue the
	   loop from the beggining (returning to main menu).
       While loop - runs the program until the user selects 3. It will always return to the main menu
		    and never re-run the splash screen. */
    public static void main (String[] args)
    {
	WheelOfFortune wof = new WheelOfFortune ();
	wof.splashScreen ();
	do
	{
	    wof.mainMenu ();
	    if (wof.choice.equals ("1"))
	    {
		wof.game ();
		if (wof.playerOne == null || wof.playerTwo == null || wof.input == ',')
		    continue;
		wof.results ();
		wof.finalRound ();
		wof.setHighScores (false);
		wof.finalResults ();
	    }
	    else if (wof.choice.equals ("2"))
		wof.highScores ();
	    else
	    {
		if (wof.choice.equals ("3"))
		    wof.instructions ();
	    }
	}
	while (!wof.choice.equals ("4"));
	wof.goodbye ();
    }
}


/*This is my animation class which is used as a thread in the splash screen method, of the WheelOfFortune class.
Variables
------------------------|---------------------------|-------------------------------------------------------------------------------------
Name                    |Type                       |Purpose
------------------------|---------------------------|-------------------------------------------------------------------------------------
c                       |reference                  |It allows the program to use the built in methods in the console class for the main window.
*/
class Animation extends Thread
{
    Console c;

    /*This is my constructor which accesses the console window from the WheelOfFortune class.
    Variables
    ------------------------|---------------------------|-------------------------------------------------------------------------------------
    Name                    |Type                       |Purpose
    ------------------------|---------------------------|-------------------------------------------------------------------------------------
    con                     |reference                  |Passes the console from WheelOfFortune class into the Animations class constructor to
			    |                           |allow that class access.
    */
    public Animation (Console con)
    {
	c = con;
    }


    /*This public method calls the private animations method so it can be accessed in the WheelOfFortune class. */
    public void run ()
    {
	animations ();
    }


    /* This method draws a wheel spinning back and forth across the screen. It is run in the splash screen.
	Variables
	------------------------|---------------------------|-------------------------------------------------------------------------------------------
	Name                    |Type                       |Purpose
	------------------------|---------------------------|-------------------------------------------------------------------------------------------
	xLoc                    |int                        |Used to track and make the x location of the wheel.
	left                    |boolean                    |Used in an if statement to decide if the wheel should go left or right. This is changed
				|                           |if the x location reaches either side of the screen
	ex                      |Exception var              |Used to catch the general exception of a Thread.sleep
	do while loop - Used so the wheel goes back and forth until the thread is stopped.
	if statement in the while loop - Used so if the x location hits an end the boolean which sends it left or right is redeclared as the opposite of
	    what it currently is.
	2nd if statement in the while loop - redeclares the x location either larger or smaller depending on if the boolean says it should go left or right.
	try catch - Used to catch an exception for the Thread.sleep to delay the program.
	*/
    private void animations ()
    {
	int xLoc = 0;
	boolean left = false;
	do
	{
	    if (xLoc <= 0)
		left = false;
	    else
	    {
		if (xLoc >= 540)
		    left = true;
	    }
	    if (left)
		xLoc--;
	    else
		xLoc++;
	    c.setColor (new Color (6, 105, 242));
	    c.fillOval (xLoc - 2, 268, 104, 104);
	    c.setColor (Color.red);
	    c.fillArc (xLoc, 270, 100, 100, 0 + xLoc, 45);
	    c.setColor (Color.cyan);
	    c.fillArc (xLoc, 270, 100, 100, 45 + xLoc, 45);
	    c.setColor (Color.orange);
	    c.fillArc (xLoc, 270, 100, 100, 90 + xLoc, 45);
	    c.setColor (Color.yellow);
	    c.fillArc (xLoc, 270, 100, 100, 135 + xLoc, 45);
	    c.setColor (Color.blue);
	    c.fillArc (xLoc, 270, 100, 100, 180 + xLoc, 45);
	    c.setColor (Color.red);
	    c.fillArc (xLoc, 270, 100, 100, 225 + xLoc, 45);
	    c.setColor (Color.cyan);
	    c.fillArc (xLoc, 270, 100, 100, 270 + xLoc, 45);
	    c.setColor (Color.orange);
	    c.fillArc (xLoc, 270, 100, 100, 315 + xLoc, 45);
	    c.setColor (Color.yellow);
	    c.fillArc (xLoc, 270, 100, 100, 360 + xLoc, 45);
	    try
	    {
		Thread.sleep (3);
	    }
	    catch (Exception ex)
	    {
	    }
	}
	while (true)
	    ;
    }
}


