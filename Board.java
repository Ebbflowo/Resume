import java.util.ArrayList;
import java.util.Objects;

public class Board {
    private Room[][] castle;

    // creates the board (5 by 5) and puts character on each board
    public Board(ArrayList<Room> roomList){
        castle = new Room[5][5];
        int currentRoom = 0;
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                castle[i][j] = roomList.get(currentRoom);
                currentRoom++;
            }
        }

        // puts player at top left corner
        castle[0][0].playerSwitch();

        // puts thief anywhere within the 4th/5th row or column
        int thiefRow = (int)(Math.random() * 5);
        int thiefColumn;
        if (thiefRow < 3)
            thiefColumn = (int)(Math.random() * 2 + 3);
        else
            thiefColumn = (int)(Math.random() * 5);
        castle[thiefRow][thiefColumn].thiefSwitch();
    }

    public void fireBall(int direction){
        if (direction == 1){
            int col = findPlayerPlacement("col");
            for (int i = 0; i < findPlayerPlacement("row"); i++){
                castle[i][col].light();
            }
        } else if (direction == 2){
            int row = findPlayerPlacement("row");
            for (int j = findPlayerPlacement("col") + 1; j < 5; j++){
                castle[row][j].light();
            }
        } else if (direction == 3) {
            int col = findPlayerPlacement("col");
            for (int i = findPlayerPlacement("row") + 1; i < 5; i++) {
                castle[i][col].light();
            }
        } else {
            int row = findPlayerPlacement("row");
            for (int j = 0; j < findPlayerPlacement("col"); j++){
                castle[row][j].light();
            }
        }
    }

    public boolean thunderWave(){
        for (int i = findPlayerPlacement("row") - 1; i <= findPlayerPlacement("row") + 1; i++){
            for (int j = findPlayerPlacement("col") - 1; j <= findPlayerPlacement("col") + 1; j++){
                if (castle[i][j].thiefReturn())
                    return true;
            }
        }
        return false;
    }

    public void spikeGrowth(int direction){
        if (direction == 1){
            int row = findPlayerPlacement("row") - 1;
            for (int j = 0; j < 5; j++){
                castle[row][j].spikeGrowth();
            }
        } else if (direction == 2){
            int col = findPlayerPlacement("col") + 1;
            for (int i = 0; i < 5; i++){
                castle[i][col].spikeGrowth();
            }
        } else if (direction == 3) {
            int row = findPlayerPlacement("row") + 1;
            for (int j = 0; j < 5; j++) {
                castle[row][j].spikeGrowth();
            }
        } else {
            int col = findPlayerPlacement("col") - 1;
            for (int i = 0; i < findPlayerPlacement("col"); i++){
                castle[i][col].spikeGrowth();
            }
        }
    }

    public boolean playerStupid(){
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++) {
                if (castle[i][j].playerReturn() && castle[i][j].isTrapped()){
                    castle[i][j].trapDestoryed();
                    return true;
                }
            }
        }
        return false;
    }

    public void placeTrap(String val){
        int trapRow = 0;
        int trapCol = 0;
        if (val.equals("north")){
             trapRow = findPlayerPlacement("row") - 1;
             trapCol = findPlayerPlacement("col");
        } else if (val.equals("east")){
             trapRow = findPlayerPlacement("row");
             trapCol = findPlayerPlacement("col") + 1;
        } else if (val.equals("south")){
             trapRow = findPlayerPlacement("row") + 1;
             trapCol = findPlayerPlacement("col");
        } else if (val.equals("west")){
             trapRow = findPlayerPlacement("row");
             trapCol = findPlayerPlacement("col") - 1;
        }

        castle[trapRow][trapCol].trapPlaced();
    }

    public void tickDown(){
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                castle[i][j].timeDown();
            }
        }
    }

    public int findPlayerPlacement(String val){
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                if (castle[i][j].playerReturn())
                    if (val.equals("row"))
                        return i;
                    else
                        return j;
            }
        }
        return 0;
    }

    public int findThiefPlacement(String val){
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                if (castle[i][j].thiefReturn())
                    if (val.equals("row"))
                        return i;
                    else
                        return j;
            }
        }
        return 0;
    }

    public void movePlayer(String direction) {
        boolean found = false;
        for (int i = 0; i < 5; i++) {
            if (!found) {
                for (int j = 0; j < 5; j++) {
                    if (!found) {
                        if (castle[i][j].playerReturn()) {
                            castle[i][j].playerSwitch();
                            if (direction.equals("north")) {
                                castle[i - 1][j].playerSwitch();
                                found = true;
                            } else if (direction.equals("east")) {
                                castle[i][j + 1].playerSwitch();
                                found = true;
                            } else if (direction.equals("south")) {
                                castle[i + 1][j].playerSwitch();
                                found = true;
                            } else if (direction.equals("west")) {
                                castle[i][j - 1].playerSwitch();
                                found = true;
                            }
                        }
                    }
                }
            }
        }
    }

    public void moveThief(String direction){
        boolean found = false;
        for (int i = 0; i < 5; i++) {
            if (!found) {
                for (int j = 0; j < 5; j++) {
                    if (!found) {
                        if (castle[i][j].thiefReturn()) {
                            castle[i][j].thiefSwitch();
                            if (direction.equals("north")) {
                                castle[i - 1][j].thiefSwitch();
                                found = true;
                            } else if (direction.equals("east")) {
                                castle[i][j + 1].thiefSwitch();
                                found = true;
                            } else if (direction.equals("south")) {
                                castle[i + 1][j].thiefSwitch();
                                found = true;
                            } else if (direction.equals("west")) {
                                castle[i][j - 1].thiefSwitch();
                                found = true;
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean victoryConditionMet(){
        // checks if thief is on same tile as a trap or player. If so, game ends and player wins
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (castle[i][j].thiefReturn() && ((castle[i][j]).isTrapped() || castle[i][j].playerReturn())){
                    return true;
                }
            }
        }
        return false;
    }

    // presents board to player;
    public void showBoard(){
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                System.out.print(castle[i][j].returnTile() + " ");
            }
            System.out.println();
        }
    }
}
