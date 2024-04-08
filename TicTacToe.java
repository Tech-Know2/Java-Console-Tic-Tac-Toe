import java.util.ArrayList;

public class TicTacToe {

    public char[][] grid = new char[3][3];

    // AI is 'o'
    // Player is 'x'

    //Assigns all of the '-' to the board showing where your pieces will be, and making sure the grid is properly setup.
    public void setUpBoard() {
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                grid[r][c] = '-';
            }
        }

        displayBoard();
    }

    //Prints everything out in a nice and visual manner
    public void displayBoard() {
        System.out.println("========================");

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                System.out.print(grid[r][c]);
                System.out.print("|");
            }

            System.out.println("");
        }
    }

    //Places the piece on the board, and makes sure that the spot is not already occupied and can actually be placed there.
    public boolean addSpot(char player, int row, int col) {
      if(grid[row][col] == '-')
      {
        grid[row][col] = player;
        displayBoard();

        return true;
        
      } else 
      {
        System.out.println("Spot Occupied, try another spot");

        return false;
      }
    }

    //It will check to see if anyone has one in the game yet and return true if so
    //It checks horizontal directions, vertical, and diagonals to see if anyone has one yet.
    public boolean checkWinStatus() {
        // Rows
        for (int r = 0; r < grid.length; r++) {
            char currentPlayerChar = grid[r][0];
            if (currentPlayerChar != '-' && currentPlayerChar == grid[r][1] && currentPlayerChar == grid[r][2]) {
                return true;
            }
        }

        // Columns
        for (int c = 0; c < grid.length; c++) {
            char currentPlayerChar = grid[0][c];
            if (currentPlayerChar != '-' && currentPlayerChar == grid[1][c] && currentPlayerChar == grid[2][c]) {
                return true;
            }
        }

        // Diagonals
        char currentPlayerChar = grid[0][0];
        if (currentPlayerChar != '-' && currentPlayerChar == grid[1][1] && currentPlayerChar == grid[2][2]) {
            return true;
        }

        currentPlayerChar = grid[2][0];
        if (currentPlayerChar != '-' && currentPlayerChar == grid[1][1] && currentPlayerChar == grid[0][2]) {
            return true;
        }

        return false;
    }

    //Will be used to see who actually one the game, and then return it so that I can tell the player if they won or lost in a visual manner
    public char checkWhoWon() {
        // Rows
        for (int r = 0; r < grid.length; r++) {
            char currentPlayerChar = grid[r][0];
            if (currentPlayerChar != '-' && currentPlayerChar == grid[r][1] && currentPlayerChar == grid[r][2]) {
                return currentPlayerChar;
            }
        }

        // Columns
        for (int c = 0; c < grid.length; c++) {
            char currentPlayerChar = grid[0][c];
            if (currentPlayerChar != '-' && currentPlayerChar == grid[1][c] && currentPlayerChar == grid[2][c]) {
                return currentPlayerChar;
            }
        }

        // Diagonals
        char currentPlayerChar = grid[0][0];
        if (currentPlayerChar != '-' && currentPlayerChar == grid[1][1] && currentPlayerChar == grid[2][2]) {
            return currentPlayerChar;
        }

        currentPlayerChar = grid[2][0];
        if (currentPlayerChar != '-' && currentPlayerChar == grid[1][1] && currentPlayerChar == grid[0][2]) {
            return currentPlayerChar;
        }

        return '-';
    }

    //Will check to see if the AI or the humans is able to actually play next turn, or if thats not possible.
    public boolean canNextTurn() {
        if (checkWinStatus() != true && isBoardFull() != true) {
            return true;
        }else 
        {
          if(checkWinStatus() == true)
          {
            if(checkWhoWon() == 'o')
            {
              System.out.println("Damn you lost to 120 lines of code written by a teenager in a single night. Skill issue.");
            } else 
            {
              System.out.println("Wow, you must feel super good about being able to beat 120 lines of code. This help you feel better about your self?");
            }
          } else 
          {
            System.out.println("Damn, you pretty much lost to a dumb AI programmed by a teenager :(");
          }
          
            return false;
        }
    }

    //Will check to see if the board is full or if the AI/humans can actually play anywhere
    public boolean isBoardFull()
    {  
      // Find any open space
      for (int r = 0; r < grid.length; r++) 
      {
          for (int c = 0; c < grid[r].length; c++) {
              if (grid[r][c] == '-') 
              {
                  return false;
              }
          }
      }

      return true;
    }

      //Will look one turn ahead to see if the human can win, then make a list of all possible wins 
      public ArrayList<int[]> checkNextWins() 
      {  // NPC checks for all player win positions
        ArrayList<int[]> playerWins = new ArrayList<>();
        char oppChar = 'x';

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                if (grid[r][c] == '-') {
                    grid[r][c] = oppChar;  // Simulate player's move
                    if (checkWinStatus()) {
                        playerWins.add(new int[]{r, c});
                    }
                    grid[r][c] = '-';  // Undo simulation
                }
            }
        }

        return playerWins;
    }

    //Will look one turn ahead to see if the AI can win, then make a list of all possible wins
    public ArrayList<int[]> AICanWinNextTurn() 
      {  // NPC checks for all player win positions
          ArrayList<int[]> aiWins = new ArrayList<>();
          char aiChar = 'o';
  
          for (int r = 0; r < grid.length; r++) {
              for (int c = 0; c < grid[r].length; c++) {
                  if (grid[r][c] == '-') {
                      grid[r][c] = aiChar;  // Simulate next potential move
                      if (checkWinStatus()) {
                          aiWins.add(new int[]{r, c});
                      }
                      grid[r][c] = '-';  // Undo simulation
                  }
              }
          }
  
          return aiWins;
      }

      //Is the decision maker behind how to play
      //It will first see if it can win, if it can it will play appropriately
      //Next it will see if the human can win, then will move to block the win
      //Lastly it will call the play random function and have a random, properly weighted move be done
      public boolean NPCPlacePiece(ArrayList<int[]> playerWins, ArrayList<int[]> aiWins)
      {
        if (aiWins.size() > 0) {
            for (int[] pos : aiWins) {
                PlaceForAlgo(pos[0], pos[1]); // AI has a winning move, take it
                return true;
            }
        } else if (playerWins.size() > 0) {
            for (int[] pos : playerWins) {
                PlaceForAlgo(pos[0], pos[1]); // Block player's winning move
                return true;
            }
        } else {
            PlaceRandom(aiWins); // If no immediate win or block, place randomly
            return true;
        }
        return false;
    }

    //Will handle placing peices for the AI. Checks to see if the spot is occupied, if so, it wont place, otherwise it will place.
    public void PlaceForAlgo(int row, int col)
    {
      if(isOccupied(row,col) == false)
      {
        grid[row][col] = 'o';
      } else 
      {
        System.out.println("AI played on occupied tile");

        //Trying again
      }
    }

    //Is the brain for the first turn moves,and any move where players are tied/not in winning condition
    //I have logic setup for the first move, I will describe them up here as to keep the code nice and clean.
    //For the first moves its as follows:
        //If the player plays in a corner, the AI will play an adjacent side edge piece
        //If the player plays a side edge piece, the AI will play an adjacent corner
        //If the player plays a center, the AI will play a corner
    public void PlaceRandom(ArrayList<int[]> aiWins) 
    {
      ArrayList<int[]> emptySpots = new ArrayList<>();
  
      // Find all empty spots
      for (int r = 0; r < grid.length; r++) 
      {
          for (int c = 0; c < grid[r].length; c++) {
              if (grid[r][c] == '-') {
                  emptySpots.add(new int[]{r, c});
              }
          }
      }
  
      // Check if the board is full
      if (emptySpots.isEmpty()) 
      {
          System.out.println("Board is full! No valid move for AI.");
          System.out.println("Game is a draw, well done, kinda...");
          return;
      }
  
      // Choose a random empty spot      
      if(aiWins.size() != 0)
      {
        for(int i = 0; i < aiWins.size(); i++)
        {
          int[] pos = aiWins.get(i);
  
          int row = pos[0];
          int col = pos[1];

          PlaceForAlgo(row, col);

          break;
        }
      } else 
      {     
        if((isOccupied(1,0) == true || isOccupied(0,1) == true) && isOccupied(0,0) == false) //If a player plays on any side, the AI will play in the adjacent corner.
        {
          PlaceForAlgo(0,0);
          
        } else if ((isOccupied(1,2) == true || isOccupied(2,1) == true) && isOccupied(0,2) == false)
        {
          PlaceForAlgo(0,2);
        
        } else if ((isOccupied(0,0) == true || isOccupied(2,0) == true) && isOccupied(1,1) == false) //If player places in the corners, AI will play on the adjacent edge piece.
        {
          PlaceForAlgo(1,1);
          
        }else if((isOccupied(0,2) == true || isOccupied(2,2) == true) && isOccupied(1,1) == false)
        {
          PlaceForAlgo(1,1);       
        } else
        {
          int randomIndex = (int) (Math.random() * emptySpots.size());
          int[] randomSpot = emptySpots.get(randomIndex);
          int row = randomSpot[0];
          int col = randomSpot[1];
      
          // Place AI's piece in the random spot
          PlaceForAlgo(row, col);
        }
      }
    }

    //A method to see if that location is occupied or not.
    public boolean isOccupied(int row, int col)
    {
      if(grid[row][col] == '-')
      {
        return false;
      } else 
      {
        return true;
      }
      
    }

    //Sets up and manages the turn for the NPC/AI
    public void NPCTurn() 
    {
        ArrayList<int[]> playerWins = checkNextWins();
        ArrayList<int[]> aiWins = AICanWinNextTurn();
    
        // Block player wins if any
        NPCPlacePiece(playerWins, aiWins);

        displayBoard();
    }
}
