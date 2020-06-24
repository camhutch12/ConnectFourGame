import java.util.InputMismatchException;
import java.util.Scanner;

public class ConnectFour {

    /*scanner is used to allow for a user to input a position on the board
      static cnt is used as a test varaible for my self to deal with the recursive call
      depth: is how deep we plan to traverse in the tree (in AI we call this ply)
     * rows: is how many rows are created in the game
     * columns: is the amount of columns made inside the game
     * board: this is the array that going to be used for playing the game as well as keeping track of different states
     * player is the user or the computer. which is going to allow them to choose a color
     * root: is the top of our tree like structure
     * x: is the x-coordinate
     * y: is the y- coordinate
      won: tells the user if they won or not
      notempty: is used to check if a spot is empty or not
      amountofplayers: is weather the game will be 2 players or 1 player*/

    private Scanner scanner = new Scanner(System.in);
    private static int cnt = 0;
    int depth = 0;
    final int rows = 6;
    final int columns = 7;
    private int[][] board;
    private int player;
    private Node root;
    boolean won;
    boolean notEmptySpot;
    int y;
    int amountOfPlayers;


    public ConnectFour() {
        this.board = new int[rows][columns];
        this.player = -10;
        root = new Node(board, player);


        playGame();


    }

    private void playGame() {

        System.out.println("Tree is build");
        System.out.println("Please press 1 for 2 players other wise your gonna play against a CPU  ");
        amountOfPlayers = scanner.nextInt();
        if (amountOfPlayers == 1) {
            playHuman();

        } else {
            playGameAI();
        }

    }

    public void playGameAI() {
        selectPlayer();
        while (!won) {

            if (player == 1) {


                do {
                    notEmptySpot = false;
                    while (true) {
                        try {
                            System.out.println("Player 1 please choose a number below");
                            System.out.println("1 2 3 4 5 6 7");

                            y = scanner.nextInt();
                            break;

                        } catch (InputMismatchException e) {
                            System.out.println("What you enter into the keyboard was invalid");
                            // need this to clear the buffer other wise you will still get the error
                            scanner.next();

                        }

                    }


                    if (spotNotEmpty(checkRowAbove(y - 1), y - 1)) {
                        board[checkRowAbove(y - 1)][y - 1] = 1;
                        notEmptySpot = true;
                    } else {
                        System.out.println(" The spot enter already has a tile placed please choose another location");

                    }

                } while (!notEmptySpot);


                printBoard(board);



                player = 2;
            } else {
                root.board = this.board;
                root = buildStateTree(3, player);
                board = root.board;
                System.out.println();
                printBoard(board);


                player = 1;

                won = winnerFound();

                if (won) {
                    System.out.println("congraduations" + player);
                    printBoard(board);
                    break;
                }
            }


        }

    }

    // this method returns if a spot has been previously chosen if it has
    // then we retun false, other we return true
    private boolean spotNotEmpty(int x, int y) {
        if (x > -1 && x < rows && y > -1 && y < columns) {
            if (board[x][y] == 2 || board[x][y] == 1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }


    }

    /*This allows for a user to select if they wish to be X or O and
    also allows for swapping and understanding*/
    private void selectPlayer() {
        int player2;
        if (player == -10) {
            System.out.println("player one please press 1 for X and 2 for O");
            player = scanner.nextInt();
            while (player != 1 && player != 2) {
                System.out.println("The number you entered is not correct");
                player = scanner.nextInt();
            }


            if (player == 1) {

                System.out.println("Player 1 is X and player 2 is O ");
            } else {

                System.out.println("Player 1 is O and player 2 is X ");

            }

        }
    }

    private Node buildStateTree(int depth, int player) {

        root.depth = 0;


        root = buildStateTree(root, columns, player, depth);
        Node first_child = root.child;
        int childNum = 1;
        while(first_child.nextSibbling != null){
            System.out.println("child# " +childNum +  "max value " + first_child.maxValue + "min value " + first_child.minValue);
            first_child = first_child.nextSibbling;
            childNum++;
        }
        System.out.println();

        printBoard(root.board);
        return root;

    }
    /*how this method works is first the parent node being the root to start is paced into
     * the method. then we check for all the parents children using a  depth limited search
     * where first we check the parent then the child of the parent then we check the parant grandChild IE the child-child
     * once we reach the bottom of the tree we go up one level and check if that child has any siblibings
     *  this is done using the for loop from there we create a child for every parent child sibling and if we are going deeper
     * then 1 level then we will create each child-child sibling for every child of the parent
     * level 0 = 1
     * level 1 = 7
     * level 2 = 42
     * and so on this is the amount of nodes  */


    private Node buildStateTree(Node parent, int children, int player, int depth) {
        Node child = null;
        Node parents_grandChild = null;
        // create a new node that is a child of its parent, as well the node also copies
        // the parents board

        // Base case


        if (parent.depth + 1 > depth) {
            return parent;
        }


        for (int i = 0; i < children; i++) {
            child = new Node(parent.board, player);
            child.depth = parent.depth + 1;
            //printBoard(child.board);

            move(child.board, i + 1, player);
//            if (cnt == 255) {
//                System.out.println();
//            }
            if (player == 1) {
                child.setMinValue();

                //System.out.println("min value is: " + child.getMinValue());
            } else {
                child.setMaxValue();
                //System.out.println("max value is: " + child.getMaxValue());

            }


            //printBoard(child.board);
            // System.out.println(cnt++);


            if (i == 0) {
                parent.child = buildStateTree(child, children - 1, player == 1 ? 2 : 1, depth);
                parents_grandChild = parent.child;
                parents_grandChild.depth = child.depth + 1;


            } else {
                parents_grandChild.nextSibbling = buildStateTree(child, children - 1, player == 1 ? 2 : 1, depth);
                parents_grandChild = parents_grandChild.nextSibbling;


            }



        }
        return child;
    }

    // allows for the movement on the board;
    /*how this works is a value is used representing a spot to check on the row
     * then from there the peice will be dropped into the row */
    public void move(int[][] board, int value, int player) {

        int i;
        int counter = 0;
        int testVar = 0;
        for (i = rows - 1; i > -1; i--) {
            for (int j = columns - 1; j > -1; j--) {
                // this position on the board is empty

                counter++;


                if (counter == value) {
                    // take out later on

                    i = checkRowAboveCpu(board, j, i);
                    if (spotNotEmpty(i, j)) {
                        board[i][j] = player;
                        return;

                    }
                }

            }
        }

    }

    /*This method check all the rows in the game board,
     * and check if there is a winner,
     * how this is done is first we loop though the board until we find a peice that has a mark.
     * then we check the next 3 peices near that initialze spot
     * if there 3 3 more peices then we have a winner and returns true */
    public boolean checkRows(int[][] board, int player) {
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i > -1 && i < rows) && j > -1 && j < columns) {
                    if (board[i][j] == player) {

                        for (int k = 1; k <= 3; k++) {
                            if (i < rows && j + k < columns && i > -1 && j + k > -1) {
                                if (board[i][j + k] == player) {
                                    count++;
                                }
                            }


                        }

                        if (count == 3) {
                            return true;
                        }


                    }
                    count = 0;

                }
            }
        }
        return false;

    }

    /*this method will check all the columns
    and check if there is a winner
    hows this is done is first we loop though board until we find a peice that has a mark
    then we check the 3peices vertical to that intiialze peice /
     */
    public boolean checkColumns(int[][] board, int player) {
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i > -1 && i < rows) && j > -1 && j < columns) {
                    if (board[i][j] == player) {

                        for (int k = 1; k <= 4; k++) {
                            if (i + k < columns - 1 && j + k < rows - 1 && j + k > -1 && i + k > -1) {
                                if (board[i + k][j] == player) {
                                    count++;
                                }
                            }


                        }
                        if (count == 3) {
                            return true;
                        }
                        count = 0;
                    }


                }
            }
        }
        return false;
    }

    /*check all diagnols on the board */
    public boolean forWardCheckDiagnol(int[][] board, int player) {
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (i < rows - 1 && j < columns - 1) {
                    if (board[i][j] == player) {
                        for (int k = 1; k <= 3; k++) {
                            if (i + k < rows && j + k < columns && i + k > -1 && j + k > -1) {
                                if (board[i + k][j + k] == player)
                                    count++;
                            }
                        }

                        if (count == 3) {
                            return true;
                        }
                        count = 0;
                    }
                }
            }
        }

        return false;
    }

    /*This method is used for checking if the user has one on a backwards diagnol example
     * 0000x
     * 000x0
     * 00x00
     * */
    public boolean backWardCheckDiagnol(int[][] board, int player) {
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i > -1 && i < rows - 1) && j > -1 && j < columns - 1) {
                    if (board[i][j] == player) {
                        for (int k = 1; k <= 4; k++) {
                            if ((i + k < rows && j - k < columns) && i + k > -1 && j - k > -1) {
                                if (board[i + k][j - k] == player) {
                                    System.out.println("i + k is " + (i + k) + " and j-k is " + (j - k));
                                    count++;
                                }
                            }

                        }
                        System.out.println("The count is " + count);
                        System.out.println();

                        if (count == 3) {
                            return true;
                        }
                        count = 0;


                    }
                }
            }
        }

        return false;
    }


    private boolean winnerFound() {
        if (checkRows(board, player)) {
            System.out.println("rows won");
            return true;
        } else if (checkColumns(board, player)) {
            System.out.println("columns");
            return true;
        } else if (forWardCheckDiagnol(board, player)) {
            System.out.println("forwarddia");
            return true;
        } else if (backWardCheckDiagnol(board, player)) {
            System.out.println("backward");
            return true;
        } else {
            return false;
        }


    }

    private int checkRowAbove(int columns) {
        for (int i = rows - 1; i > -1; i--) {
            for (int j = this.columns - 1; j > -1; j--) {
                if (board[i][columns] == 0) {
                    return i;
                }
            }


        }
        return -1;
    }

    private int checkRowAboveCpu(int[][] board, int columns, int rows) {
        if (rows > this.rows) {
            return 0;
        }

        while (true)
            if (rows > -1 && rows < this.rows && columns > -1 && columns < this.columns) {
                if (board[rows][columns] != 0) {
                    rows--;


                } else {
                    return rows;
                }
            }

    }

    /*This method is used to print out what the display of the code
     * at each player into the console*/
    private void printBoard(int[][] board) {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0) {
                    System.out.print(" " + "|");
                } else if (board[i][j] == 1) {
                    System.out.print("X" + "|");
                } else if (board[i][j] == 2) {
                    System.out.print("O" + "|");

                }


            }
            System.out.println();
        }
    }

    private void playHuman() {
        selectPlayer();
        while (!won) {

            if (player == 1) {


                do {
                    notEmptySpot = false;
                    while (true) {
                        try {
                            System.out.println("Player 1 please choose a number below");
                            System.out.println("1 2 3 4 5 6 7");

                            y = scanner.nextInt();
                            break;

                        } catch (InputMismatchException e) {
                            System.out.println("What you enter into the keyboard was invalid");
                            // need this to clear the buffer other wise you will still get the error
                            scanner.next();

                        }

                    }


                    if (spotNotEmpty(checkRowAbove(y - 1), y - 1)) {
                        board[checkRowAbove(y - 1)][y - 1] = 1;
                        notEmptySpot = true;
                    } else {
                        System.out.println(" The spot enter already has a tile placed please choose another location");

                    }

                } while (!notEmptySpot);


                printBoard(board);

                won = winnerFound();

                if (won) {
                    System.out.println("congraduations" + player);
                    printBoard(board);
                    break;
                }

                player = 2;
            } else {


                do {
                    notEmptySpot = false;

                    while (true) {
                        try {
                            System.out.println("Player 2 please choose a number below");
                            System.out.println("1 2 3 4 5 6 7");
                            y = scanner.nextInt();
                            break;

                        } catch (InputMismatchException e) {
                            System.out.println("What you enter into the keyboard was invalid");
                            // need this to clear the buffer other wise you will still get the error
                            scanner.next();

                        }

                    }

                    if (spotNotEmpty(checkRowAbove(y - 1), y - 1)) {
                        board[checkRowAbove(y - 1)][y - 1] = 2;
                        notEmptySpot = true;
                    } else {
                        System.out.println(" The spot enter already has a tile placed please choose another location");

                    }

                } while (!notEmptySpot);
                printBoard(board);
                won = winnerFound();
                if (won) {
                    System.out.println("congraduations" + player);
                    break;
                }
                player = 1;
            }


        }
    }


}
