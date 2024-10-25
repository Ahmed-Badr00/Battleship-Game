CSCE3102 - Programming in Java (Fall 2024)
Battleship Game Implementation
=========================================

Developed by:
- Ahmed Badr (900202868)
  Email: ahmedbadr00@aucegypt.edu
- Bemen Girgis (900213066)
  Email: bemen@aucegypt.edu

Project Overview
---------------
This project implements a single-player version of the classic Battleship game using Java Swing. The player attempts to sink five ships of different sizes hidden on a 10x10 grid within a limited number of attempts based on the chosen difficulty level.

Features
--------
1. Three difficulty levels:
   - Easy: 40 attempts
   - Medium: 30 attempts
   - Hard: 20 attempts

2. Five different ships:
   - Carrier (5 cells)
   - Battleship (4 cells)
   - Cruiser (3 cells)
   - Submarine (3 cells)
   - Destroyer (2 cells)

3. Game features:
   - Real-time attempt tracking
   - Ship sinking notifications
   - Game statistics display
   - Option to reveal all ships
   - Ships cannot be placed adjacent to each other
   - Visual feedback for hits and misses

File Structure
-------------
The game consists of three main Java classes:
1. BattleShip.java - Main game window and controls
2. Board.java - Game logic and grid management
3. Cell.java - Individual cell behavior and display

Required Resources
-----------------
The game requires three image files in a 'data' directory:
- data/tile-01.png (Empty cell)
- data/tile-02.png (Miss indicator)
- data/tile-03.png (Hit indicator)

Setup Instructions
-----------------
1. Ensure you have Java Development Kit (JDK) installed
2. Create a directory for the game
3. Place all three .java files in the directory
4. Create a 'data' subdirectory and place the required images
5. Compile the files:
   javac BattleShip.java Board.java Cell.java
6. Run the game:
   java BattleShip

How to Play
-----------
1. Select difficulty level from the dropdown menu
2. Click "New Game" to start
3. Click on grid cells to fire at that location
4. Blue indicates a miss, red indicates a hit
5. Messages will appear when ships are sunk
6. Game ends when all ships are sunk or attempts run out
7. View statistics or reveal ships using the buttons below the grid

Controls
--------
- New Game: Starts a new game with selected difficulty
- Reveal Ships: Shows locations of all ships
- Statistics: Displays current game statistics
- Difficulty Dropdown: Changes game difficulty

Implementation Details
--------------------
- The game uses Java Swing for the GUI
- Ships are randomly placed at game start
- Ships cannot overlap or touch each other
- Each difficulty level provides different number of attempts
- The game tracks statistics including hits, misses, and accuracy

Known Issues
-----------
None at present time.

Future Improvements
-----------------
Possible enhancements for future versions:
1. Add sound effects
2. Implement two-player mode
3. Add save/load game functionality
4. Add animation effects
5. Implement computer AI for strategic gameplay

Version Information
------------------
Version: 1.0
Last Updated: October 19, 2024

End of README
