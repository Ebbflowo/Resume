import java.util.ArrayList;
import java.util.Scanner;
public class Main {

    // taken by Stack Overflow to cause console to pause:
    // https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);

        // initializing variables and objects
        boolean playerWin = false;
        boolean playerTrapped = false;
        boolean fireBall = true;
        boolean thunderWave = true;
        boolean spikeGrowth = true;

        ArrayList<Room> listOfRooms = new ArrayList<>();
        listOfRooms.add(new Room("01"));
        listOfRooms.add(new Room("02"));
        listOfRooms.add(new Room("03"));
        listOfRooms.add(new Room("04"));
        listOfRooms.add(new Room("05"));
        listOfRooms.add(new Room("06"));
        listOfRooms.add(new Room("07"));
        listOfRooms.add(new Room("08"));
        listOfRooms.add(new Room("09"));
        listOfRooms.add(new Room("10"));
        listOfRooms.add(new Room("11"));
        listOfRooms.add(new Room("12"));
        listOfRooms.add(new Room("13"));
        listOfRooms.add(new Room("14"));
        listOfRooms.add(new Room("15"));
        listOfRooms.add(new Room("16"));
        listOfRooms.add(new Room("17"));
        listOfRooms.add(new Room("18"));
        listOfRooms.add(new Room("19"));
        listOfRooms.add(new Room("20"));
        listOfRooms.add(new Room("21"));
        listOfRooms.add(new Room("22"));
        listOfRooms.add(new Room("23"));
        listOfRooms.add(new Room("24"));
        listOfRooms.add(new Room("25"));
        Board game = new Board(listOfRooms);

        game.showBoard();
        String[] spellList = {"Fireball", "Thunderwave", "Spike Growth"};

        // picks two spells for the reader to use
        int randomSpell1 = (int) (Math.random() * 3);
        int randomSpell2 = randomSpell1;
        while (randomSpell2 == randomSpell1){
            randomSpell1 = (int) (Math.random() * 3);
        }


        // Tiles are presented with symbols of order based on conditions:
        // "P" stands for player position
        // "O" stands for revealed tile
        // "X" stands for trapped tile
        // "?" stands for unrevealed tile
        // "!" if both thief and player are on the same tile.


         // Game starts
        System.out.println("You enter the room!");
        for (int turns = 15; turns > 0; turns--) {
            boolean resetTurn = false;

            if (playerTrapped){
                System.out.println("You lost your turn getting out of your trap");
                Thread.sleep(1000);
                System.out.println("Your trap is destroyed");

            } else {

                // player chooses action
                System.out.println("You have " + turns + " left. What do you do?: ");
                Thread.sleep(1000);
                System.out.println("1) Move \t 2) Cast Spell \t 3) Listen \t 4) Place trap");
                int userInput = scanner.nextInt();

                // outcome of player action

                // player takes move action
                if (userInput == 1) {

                    System.out.println("1) North \t 2) East \t 3) South \t 4) West \t 5) Cancel");
                    int moveInput = scanner.nextInt();

                    if (moveInput == 1 && game.findPlayerPlacement("row") != 0) {
                        game.movePlayer("north");
                    } else if (moveInput == 2 && game.findPlayerPlacement("col") != 4) {
                        game.movePlayer("east");
                    } else if (moveInput == 3 && game.findPlayerPlacement("row") != 4) {
                        game.movePlayer("south");
                    } else if (moveInput == 4 && game.findPlayerPlacement("col") != 0) {
                        game.movePlayer("west");
                    } else {
                        System.out.println("Move Invalid / Cancelled Move");
                        turns++;
                        resetTurn = true;
                    }


                } else if (userInput == 2) {

                    System.out.println("1) " + spellList[randomSpell1] + "\t 2) " + spellList[randomSpell2]);
                    String spellInput = scanner.next();

                    if (spellInput.startsWith("f") && fireBall){

                        System.out.println("1) North \t 2) East \t 3) South \t 4) West");
                        int fromWhere = scanner.nextInt();
                        fireBall = false;

                        System.out.println("You try to cast fireball!");
                        Thread.sleep(1000);

                        if (fromWhere == 1 && game.findPlayerPlacement("row") != 0)
                            game.fireBall(1);
                        else if (fromWhere == 2 && game.findPlayerPlacement("col") != 4)
                            game.fireBall(2);
                        else if (fromWhere == 3 && game.findPlayerPlacement("row") != 4)
                            game.fireBall(3);
                        else if (fromWhere == 4 && game.findPlayerPlacement("col") != 0)
                            game.fireBall(4);
                        else {
                            System.out.println("You cannot cast that direction!");
                            resetTurn = true;
                            fireBall = true;
                            turns++;
                        }
                        System.out.println("Light shines throughout the rooms!");

                    } else if (spellInput.startsWith("t") && thunderWave){
                        thunderWave = false;
                        if (game.findPlayerPlacement("row") != 0 && game.findPlayerPlacement("row") != 4 && game.findPlayerPlacement("col") != 0 && game.findPlayerPlacement("row") != 4){

                            System.out.println("You try to blast the ground!");
                            Thread.sleep(1000);

                            if (game.thunderWave()){
                                System.out.println("You hear a scream");
                                playerWin = true;
                                break;
                            } else
                                System.out.println("The room is silent...");

                        } else {
                            System.out.println("You cannot cast that right now!");
                            resetTurn = true;
                            thunderWave = true;
                            turns++;
                        }

                    } else if (spellInput.startsWith("s") && spikeGrowth){
                        spikeGrowth = false;
                        System.out.println("1) North \t 2) East \t 3) South \t 4) West");
                        int growth = scanner.nextInt();

                        System.out.println("You try and grow vines beside you!");
                        Thread.sleep(1000);

                        if (growth == 1 && game.findPlayerPlacement("row") != 0){
                            game.spikeGrowth(1);
                            System.out.println("Vines sprout from the ground!");

                        } else if (growth == 2 && game.findPlayerPlacement("col") != 4){
                            game.spikeGrowth(2);
                            System.out.println("Vines sprout from the ground!");

                        } else if (growth == 3 && game.findPlayerPlacement("row") != 4){
                            game.spikeGrowth(3);
                            System.out.println("Vines sprout from the ground!");

                        } else if (growth == 4 && game.findPlayerPlacement("col") != 0){
                            game.spikeGrowth(4);
                            System.out.println("Vines sprout from the ground!");

                        } else {
                            System.out.println("You cannot cast it here!");
                            resetTurn = true;
                            spikeGrowth = true;
                            turns++;
                        }

                    } else {
                        System.out.println("Error Input / Ran out of spell slots!");
                        turns++;
                        resetTurn = true;
                    }

                } else if (userInput == 3) {

                    // gives player general direction of the thief
                    String direction;

                    int playerThiefRow = game.findPlayerPlacement("row") - game.findThiefPlacement("row");
                    int playerThiefCol = game.findPlayerPlacement("col") - game.findThiefPlacement("col");

                    if (playerThiefRow == 0) {
                        if (playerThiefCol > 0)
                            direction = "west";
                        else
                            direction = "east";

                    } else if (playerThiefCol == 0){
                        if (playerThiefRow > 0)
                            direction = "north";
                        else
                            direction = "south";
                    } else {

                    if (Math.abs(playerThiefRow) > Math.abs(playerThiefCol)) {
                        // you hear the thief based on row
                        if (playerThiefRow > 0)
                            direction = "north";
                        else
                            direction = "south";


                    } else {
                        // you hear the thief based on col
                        if (playerThiefCol > 0)
                            direction = "west";
                        else
                            direction = "east";
                        }
                    }

                    Thread.sleep(1500);
                    System.out.println("You hear the floor creek down " + direction + "!");


                } else if (userInput == 4) {

                    // allows the user to place a trap in a spot beside them (any direction)
                    System.out.println("Place trap: 1) North \t 2) East \t 3) South \t 4) West \t 5) Cancel");
                    int trapInput = scanner.nextInt();

                    // ensures the place beside player is open and available
                    if (trapInput == 1 && (game.findPlayerPlacement("row") - 1) != -1) {
                        game.placeTrap("north");
                    } else if (trapInput == 2 && (game.findPlayerPlacement("col") + 1) != 5) {
                        game.placeTrap("east");
                    } else if (trapInput == 3 && (game.findPlayerPlacement("row") + 1) != 5) {
                        game.placeTrap("south");
                    } else if (trapInput == 4 && (game.findPlayerPlacement("col") - 1) != -1) {
                        game.placeTrap("west");
                    } else {
                        System.out.println("Input Invalid / Cancelled Action");
                        turns++;
                        resetTurn = true;
                    }

                } else {
                    System.out.println("That does not work. Please try again");
                    turns++;
                    resetTurn = true;
                }
            }


            if (!resetTurn) {
                playerTrapped = false;
                // checks any board changes after player moves

                if (game.victoryConditionMet()) {
                    playerWin = true;
                    game.showBoard();
                    break;
                }

                // checks to see if player stepped on their own trap. If so, they lose their next turn.
                if (game.playerStupid()){
                    playerTrapped = true;
                    System.out.println("You stepped in your own trap!");
                }


                // thief takes their action

                // if the thief is near a border, it will only move within the given axis
                if (game.findThiefPlacement("col") == 0){
                    game.moveThief("east");
                } else if (game.findThiefPlacement("col") == 4){
                    game.moveThief("west");
                } else if (game.findThiefPlacement("row") == 0){
                    game.moveThief("south");
                } else if (game.findThiefPlacement("row") == 4){
                    game.moveThief("north");
                } else {
                    // otherwise, the following occurs:
                    // calculates distance between thief and player in all directions
                    int thiefXVal = (game.findThiefPlacement("col") - game.findPlayerPlacement("col"));
                    int thiefYVal = (game.findThiefPlacement("row") - game.findPlayerPlacement("row"));

                    // checks to see if player is in the same column or row as player and moves away
                    if (thiefXVal == 0) {
                        int randomDirection = (int) (Math.random() * 2);
                        if (randomDirection == 0)
                            game.moveThief("east");
                        else
                            game.moveThief("west");

                    } else if (thiefYVal == 0) {
                        int randomDirection = (int) (Math.random() * 2);
                        if (randomDirection == 0)
                            game.moveThief("north");
                        else
                            game.moveThief("south");

                    } else if (Math.abs(thiefXVal) == Math.abs(thiefYVal)) {
                        boolean moveWell = true;
                        while (moveWell) {
                            int randomDirection = (int) (Math.random() * 4);
                            if (randomDirection == 0 && thiefYVal < 0) {
                                game.moveThief("north");
                                moveWell = false;
                            } else if (randomDirection == 1 && thiefXVal > 0) {
                                game.moveThief("east");
                                moveWell = false;
                            } else if (randomDirection == 2 && thiefYVal > 0) {
                                game.moveThief("south");
                                moveWell = false;
                            } else if (randomDirection == 3 && thiefXVal < 0) {
                                game.moveThief("west");
                                moveWell = false;
                            }
                        }

                        // if the player is closer in row, the thief will move on the row
                    } else if (Math.abs(thiefYVal) < Math.abs(thiefXVal)) {
                        if (thiefYVal < 0) {
                            game.moveThief("north");
                        } else {
                            game.moveThief("south");
                        }

                    } else {
                        // if the player is closer in row, the thief will move on the row
                        if (thiefXVal > 0) {
                            game.moveThief("east");
                        } else {
                            game.moveThief("south");
                        }
                    }
                }


                // checks any board changes after thief moves

                if (game.victoryConditionMet()) {
                    playerWin = true;
                    game.showBoard();
                    break;
                }

                Thread.sleep(2000);

                // lowers conditional status of rooms
                game.tickDown();

                game.showBoard();
            }
        }

        // checks if player has won
        if (playerWin){
            System.out.println("You've caught the thief!");
        } else {
            System.out.println("The thief escaped!!");
        }
    }
}
