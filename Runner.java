import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        
        //Creates both objects
        Scanner scanner = new Scanner(System.in);
        TicTacToe ticTacToe = new TicTacToe();

        //Asks for the game type they want to play.
        System.out.print("What type of game do you want to play? (1 for Single Player, 2 for multiplayer): ");
        int gameType = scanner.nextInt();

        //Controls each game type.
        if (gameType == 1) { // AI game
            ticTacToe.setUpBoard();

            while (ticTacToe.canNextTurn()) {
                System.out.print("Enter the row (0, 1, or 2): ");
                int row = scanner.nextInt();

                System.out.print("Enter the column (0, 1, or 2): ");
                int col = scanner.nextInt();

                if (ticTacToe.addSpot('x', row, col)) {
                    ticTacToe.NPCTurn();
                }
            }

        } else { // Multiplayer game
            int turn = 0;
            char currentPlayerChar = 'x';

            ticTacToe.setUpBoard();

            while (ticTacToe.canNextTurn()) 
            {
                turn++;

                if (turn % 2 == 0) 
                {
                    currentPlayerChar = 'o';
                } else 
                {
                    currentPlayerChar = 'x';
                }

                boolean spotOccupied = true;
              
                while (spotOccupied) 
                {
                    System.out.print("Player " + currentPlayerChar + ", enter the row (0, 1, or 2): ");
                    int row = scanner.nextInt();

                    System.out.print("Player " + currentPlayerChar + ", enter the column (0, 1, or 2): ");
                    int col = scanner.nextInt();

                    if (!ticTacToe.isOccupied(row, col)) 
                    {
                        ticTacToe.addSpot(currentPlayerChar, row, col);
                        spotOccupied = false;
                    } else 
                    {
                        System.out.println("Spot occupied, try another spot.");
                    }
                }

                System.out.println("========================");
            }

          String winner = "";
          
          if(ticTacToe.checkWhoWon() == 'x')
          {
            //Player one
            winner = "player One";
          } else 
          {
            //player two
            winner = "player Two";
          }

          System.out.println("The winner is " + winner + "great job... for a mere human");
        }

        //Ends the game
        scanner.close();
    }
}
