import java.util.ArrayList;

public class Player {

    ArrayList<Integer> squareOnBoard = new ArrayList<>();
    ArrayList<String> playerName = new ArrayList<>();
    ArrayList<Integer> playerDiceRolledAtBeginning = new ArrayList<>();
    ArrayList<Integer> playerOrder = new ArrayList<>();


    public void addPlayer(String player){
        playerName.add(player);
        squareOnBoard.add(0);
        playerDiceRolledAtBeginning.add(1);
        playerOrder.add(1);
    }
    public String getPlayerName(int index){
        return playerName.get(index);
    }
    public int getPlayerIndexFromName(String name){
        return playerName.indexOf(name);
    }
    public String getPlayerNameFromSquare(int square){
        int index = squareOnBoard.indexOf(square);
        return playerName.get(index);
    }
    public String getPlayerNameFromFirstDieRoll(int number){
        int index = playerDiceRolledAtBeginning.indexOf(number);
        return playerName.get(index);
    }
    public void setPlayerSquare(String player, int square){
        int index = playerName.indexOf(player);
        squareOnBoard.set(index, square);
    }
    public int getSquare(String player){
        int index = playerName.indexOf(player);
        return squareOnBoard.get(index);
    }
    public int getSquare(int playerIndex){
        return squareOnBoard.get(playerIndex);
    }
    public int getSquareFromName(String name){
        int index = playerName.indexOf(name);
        return squareOnBoard.get(index);
    }
    public void setPlayerDiceRolledAtBeginning(String name, int numberRolled){
        int index = playerName.indexOf(name);
        playerDiceRolledAtBeginning.set(index,numberRolled);
    }

    public void setPlayerOrder (String name, int order){
        int index = playerName.indexOf(name);
        playerOrder.set(index,order);
    }
    public int getPlayerOrder (String playerName){
        int index = playerName.indexOf(playerName);
        return playerOrder.get(index);
    }
    public String getPlayerNameFromOrder (int order){
        int index = playerOrder.indexOf(order);
        return playerName.get(index);
    }
}
