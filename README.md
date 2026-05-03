# The Tower 

## Getting Started

You've wound up in a mysterious casino with 500 chips on your person.
Make your way to the top of the High Rollers list!
Or end up with the lost souls who went broke trying....
It's Blackjack time!

## Controls

**H to hit**
**S to Stand**

## What does The Tower solve? 

The Tower is first and foremost, meant to entertain or kill boredom. 
The replayable nature and high score system ensures replayablility and fun, even for the gambling-averse.
The game also teaches about probability and risk managment by educating players on whether they should hit or stand on a hand.
It is also a demonstration of object oriented design, file I/O and text-based user interface.

## Program Structure

The program is structured across six classes, each with their own responsibility.

**App.java** is the program's entry point and handles the main loop of the game, the new game/load menu, file saving and the broke detection logic.

**Card.java** represents a single playing card that is assigned a suit, name and value. It also contains a toString method for display purposes.

**Deck.java** represents a 52 card deck and is responsible for building the deck with a card of each value and suit. It also shuffles the cards every round.

**Hand.java** represents the card that the player and dealer are holding. It is responsible for taking cards from the deck on a hit, calculating the hand total and the Ace logic, deciding whether the card is a 1 or an 11. 

**Player.java** represents a player at the table, storing their name, chips, current hand and current bet.

**Game.java** is the main gameplay class. It holds the logic for betting, dealing, the player's hit/stand loop, the dealer's automated play and the winner of each round. 

## How To Run 
- Make sure Java is installed on your machine
- Download and unzip the project folder
- Open a terminal and navigate to the project folder 
- Compile the program with `javac src/*.java -d out`
- Run the program with `java -cp out App`
- Follow the on screen prompts to play! 
