public class Room {

    private String tile;
    private boolean playerOn;
    private boolean thiefOn;
    private boolean revealed;
    private int conditionLength;
    private boolean trapped;

    public Room(String givenName){
        tile = "?";
        revealed = false;
        conditionLength = 0;
        trapped = false;
        playerOn = false;
        thiefOn = false;
    }

    // used at end of turns. Lowers length of conditional status of rooms
    public void timeDown(){
        conditionLength --;
        if (conditionLength < 0){
            if (trapped){
                trapped = false;
            }

            if (revealed){
                revealed = false;
            }
        }
    }

    public void trapDestoryed(){
        trapped = false;
    }

    public void trapPlaced(){
        trapped = true;
        conditionLength = 12;
    }

    // remove/add player from tile
    public void playerSwitch(){
        playerOn = !playerOn;
    }

    // remove/add thief from tile
    public void thiefSwitch(){
        thiefOn = !thiefOn;
    }

    public void light(){
        revealed = true;
        conditionLength += 5;
    }

    // spike growth spell hits the room. Provides "trapped" condition
    public void spikeGrowth(){
        conditionLength += 3;
        trapped = true;
    }

    // reveals tile condition and updates tile
    public String returnTile(){
        // Tiles are presented with symbols of order based on conditions:
        // "P" stands for player position
        // "O" stands for revealed tile
        // "X" stands for trapped tile
        // "?" stands for unrevealed tile

        if (playerOn)
            tile = "P";
        else if (revealed && thiefOn)
            tile = "!";
        else if (revealed)
            tile = "O";
        else if (trapped && thiefOn)
            tile = "~";
        else if (trapped)
            tile = "X";
        else
            tile = "?";

        // used for testing purposes only
//        if (thiefOn)
//            tile = "!";

        return tile;
    }

    public boolean playerReturn(){
        return playerOn;
    }

    public boolean thiefReturn(){
        return thiefOn;
    }

    public boolean isTrapped(){
        return trapped;
    }
}
