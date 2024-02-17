import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class SnakesAndLadders {
    Player player = new Player();
    String[][] table = new String[10][11];
    boolean gameWon = false;
    int numberOfPlayers = 0;


    ArrayList<Integer> squaresWithPlayers = new ArrayList<>();
    ArrayList<Integer> squaresWithLadders = new ArrayList<>(Arrays.asList(1, 4, 9, 21, 36, 51, 71, 80));
    ArrayList<Integer> laddersLeadTo = new ArrayList<>(Arrays.asList(38, 14, 31, 42, 44, 67, 91, 100));
    ArrayList<Integer> squaresWithSnakes = new ArrayList<>(Arrays.asList(16, 48, 64, 79, 93, 95, 97, 98));
    ArrayList<Integer> snakesLeadTo = new ArrayList<>(Arrays.asList(6, 30, 60, 19, 68, 24, 76, 78));

    //runs through each spot on the board and numbers them 1-100.
    public void setBoard(){

        Integer count = 1;
        for (int row = 0; row < table.length; row++) {
            for (int column = 0; column < table.length; column++) {
                String number = count.toString();
                table[row][column] = number;
                count++;
            }
        }
    }
    //chooses a random number between 1-6 inclusively
    public int flipDice(){
        Random ran = new Random();
        int number = ran.nextInt(6) + 1;
        return number;
    }
    //initiate core engine to start game
    public void play(){
        System.out.print("Number of Players: ");
        Scanner scan = new Scanner(System.in);
        int numberInput = scan.nextInt();
        if (numberInput > 2){
            System.out.println("Initialization was attempted for " + numberInput + " member of players;" +
                    "however, this is only expected for an extended version of the game." +
                    "Value will be set to 2." );
            numberOfPlayers = 2;
        }
        else if(numberInput == 2){
            numberOfPlayers = 2;
        }
        else{
            System.out.println("Error: Cannot execute the game with less than 2 players! Will exit.");
            System.exit(0);
        }
//creates the player object depending on how many players there are
        for (int i = 1; i <= numberOfPlayers; i++){
            System.out.print("Player " + i + ", please enter name: ");
            System.out.println();
            String name = scan.next();
            player.addPlayer(name);
            squaresWithPlayers.add(0);
        }
        System.out.printf("Alright, now to choose who starts! %n" +
                "Take turns rolling the die. %n" +
                "Who ever has the highest number starts. %n" +
                "The second person to play will be the second highest, and so forth.%n");

        int[] numberHolder = new int[numberOfPlayers];

        for (int i = 0; i < numberOfPlayers; i++){
            System.out.print("Player " + (i+1) + ", " + player.getPlayerName(i) + ", please press any key to roll the die: ");
            String w = scan.next();
            int numberRolled = flipDice();
            String name = player.getPlayerName(i);
            player.setPlayerDiceRolledAtBeginning(name, numberRolled);
            System.out.println(name + " rolled a " + numberRolled);
            numberHolder[i] = numberRolled;
        }
        Arrays.sort(numberHolder);
        //saving the order of player to player index.
        for(int i = 0; i < numberHolder.length; i++){
            int a = numberHolder.length - 1 - i;
            int numberRolled = numberHolder[a];
            String name = player.getPlayerNameFromFirstDieRoll(numberRolled);
            player.setPlayerOrder(name, i+1);
        }
        //display the results
        for (int i = 1; i <= numberOfPlayers; i++){
            String name = player.getPlayerNameFromOrder(i);
            String place ="";
            if (i == 1){
                place = "st";
            }
            else if (i == 2){
                place = "nd";
            }
            else if (i == 3){
                place = "rd";
            }
            else{
                place = "th";
            }
            System.out.printf("%s will play %d%s.%n", name, i, place);
        }
        System.out.println("Let's begin!");
        setBoard();
        printBoard();
        while(gameWon == false){

            for (int i = 1; i <= numberOfPlayers; i++){
                String name = player.getPlayerNameFromOrder(i);
                System.out.print(name + " press any key to roll the die: ");
                String w = scan.next();
                int numberRolled = flipDice();
                int currentSquare = player.getSquare(name);
                makeMove(name,numberRolled,currentSquare);
                updateBoard(numberOfPlayers);
                if(gameWon == true){
                    break;
                }
            }
        }
        System.out.println("Game Won!");
    }
    public int[] getCoordinatesOfSquare(int square){
        int row;
        int column;
        int[] position = new int[2];



        if (square == 0){
            row = 9;
            column = 10;
        }
        else if(square%10 == 0){
            row = (square/10) - 1;
            column = 9;
        }
        else{
            row = square/10;
            column = (square%10)-1;
        }
        position[0] = row;
        position[1] = column;
        return position;
    }
    //Updates the board with players on their spots.
    public void updateBoard(int numberOfPlayers){
        setBoard();
        //for loop that runs through the list of position and set those to 0;
        for(int i = 1; i <= numberOfPlayers; i++){
            String name = player.getPlayerNameFromOrder(i);
            int[] position = getCoordinatesOfSquare(player.getSquare(name));
            int a = position[0];
            int b = position[1];
            String firstLetter = name.substring(0,1);
            String square = firstLetter+firstLetter+firstLetter;
            table[a][b] = square;
        }
        printBoard();
    }
    //prints the board in a Snakes and Ladders way.
    public void printBoard(){
        System.out.println();
        for (int row = 9; row >= 0; row--) {
            for(int column = 0; column < table.length; column++) {
                if(row%2 == 0){
                    if (column == 0) {
                        System.out.format("         |%4s |", table[row][column]);
                    }
                    else {
                        System.out.format("%4s |", table[row][column]);
                    }
                }
                else {
                    if (column == 0){
                        System.out.format("         |%4s |", table[row][9-column]);
                    }
                    else {
                        System.out.format("%4s |", table[row][9-column]);
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    //player moves depending on what number they got on die.
    public void makeMove(String playerName, int die, int currentSquare){
        int newSquare = currentSquare + die;
        if(squaresWithLadders.contains(newSquare)){
            System.out.printf("%s has rolled %d, so they move from %d to %d.%n", playerName, die, currentSquare, newSquare);
            System.out.print("There seems to be a ladder at " + newSquare);
            int index = squaresWithLadders.indexOf(newSquare);
            newSquare = laddersLeadTo.get(index);
            System.out.printf(", so %s will go up to %d!%n", playerName, newSquare);
        }
        else if(squaresWithSnakes.contains(newSquare)){
            System.out.printf("%s has rolled %d, so they move from %d to %d.%n", playerName, die, currentSquare, newSquare);
            System.out.print("Oh no, there's a snake at " + newSquare);
            int index = squaresWithSnakes.indexOf(newSquare);
            newSquare = snakesLeadTo.get(index);
            System.out.printf(", so %s will slide down to %d!%n", playerName, newSquare);
        }
        else if (newSquare>100){
            int stepForward = die - (100 - currentSquare);
            int stepBack = die - stepForward;
            newSquare = 100 - stepBack;
        }

        else{
            System.out.printf("%s has rolled %d, so they move from %d to %d.%n", playerName, die, currentSquare, newSquare);
        }

        if(squaresWithPlayers.contains(newSquare)){
            String name = player.getPlayerNameFromSquare(newSquare);
            player.setPlayerSquare(name, 0);
            int index = player.getPlayerIndexFromName(name);
            squaresWithPlayers.set(index, 0);
            System.out.printf("And it seems like %s landed on the same square as %s.%n" +
                    "%s will get kicked down to square 0!%n", playerName, name, name);

        }
        if (newSquare == 100){
            winner(playerName);
        }
        player.setPlayerSquare(playerName, newSquare);
        int index = player.getPlayerIndexFromName(playerName);
        squaresWithPlayers.set(index, newSquare);
    }
    public void winner(String player) {
        System.out.println("Congratulations! " + player + " has won the game!");
        gameWon = true;
    }
    public String toString(){
        return "Snakes and Ladders game.";
    }
}
