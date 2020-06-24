

// this is the board peices in the array
// we are intaillze going to set the board to empty
// 0: empty
// 1: red peice
// 2: yellow peice;
public class Node {
    final int rows = 6;
    final int columns = 7;
    int player;
    int depth;
    double minValue;
    double maxValue;
    Node child;
    Node nextSibbling;
    int[][] board;


    public Node(int[][] board, int player) {
        this.board = new int[6][7];
        this.depth = 0;
        this.player = player;

        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                this.board[i][j] = board[i][j];
            }
        }
    }


    public double getMinValue() {
        return minValue;
    }

    public void setMinValue() {
        this.minValue = bestMove() * -1;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue() {
        this.maxValue = bestMove();
    }


    /*This works im pretty sure*/
    public int huristicRows() {
        int max = Integer.MIN_VALUE;
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i > -1 && i < rows) && j > -1 && j < columns) {
                    if (board[i][j] == player) {

                        for (int k = 0; k <= 4; k++) {
                            if (i < rows && j + k < columns && i > -1 && j + k > -1) {
                                if (board[i][j + k] == player) {


                                    count++;
                                }

                                if (count == 4) {
                                    break;
                                }

                                if (board[i][j + k] != player) {

                                    break;
                                }
                            }


                        }

                        switch (count) {
                            case 4:
                                //System.out.println("There are 4 points");
                                count = count * 1000;
                                return count;


                            case 3:
                                // System.out.println("There are 3 points");
                                count = count * 100;
                                break;

                            case 2:
                                // System.out.println("There are 2 points");
                                count = count * 10;
                                break;

                            case 1:
                                //  System.out.println("There are 1 points");
                                count = count * 1;
                                break;

                            default:
                                count = 0;

                        }

                        if (max < count) {
                            max = count;
                        }
                        count = 0;

                    }
                }
            }

        }
        return max;
    }

    /*This works as well i think*/
    public int huristicColumns() {
        int totalScore = 0;
        int lastJ = 7;
        int lastI = 6;
        int j = 0;
        while (j < lastJ) {
            for (int i = 0; i < lastI; i++) {
                //System.out.println("i and j " + i + ", " + j);
                if (this.board[i][j] == 0 || i == 0){
                    continue;
                }else{
                    if (this.board[i][j] == this.board[i-1][j] && this.board[i][j] != 0){
                        totalScore++;
                    }else if (this.board[i-1][j] != 0){
                        break;
                    }
                }
            }
            j++;
        }
        return totalScore == 0 ? 0 : totalScore + 1;

        /*int max = Integer.MIN_VALUE;
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i > -1 && i < rows) && j > -1 && j < columns) {
                    if (board[i][j] == player) {

                        for (int k = 0; k <= 4; k++) {
                            if (i + k < rows && j < columns && i + j > -1 && j > -1) {
                                if (board[i + k][j] == player) {


                                    count++;
                                }

                                if (board[i + k][j] != player) {

                                    break;
                                }
                            }


                        }

                        switch (count) {
                            case 4:
                                // System.out.println("There are 4 points");
                                count = count * 1000;
                                return count;

                            case 3:
                                // System.out.println("There are 3 points");
                                count = count * 100;
                                break;

                            case 2:
                                // System.out.println("There are 2 points");
                                count = count * 10;
                                break;

                            case 1:
                                //  System.out.println("There are 1 points");
                                count = count * 1;
                                break;

                            default:
                                count = 0;

                        }

                        if (max < count) {
                            max = count;
                        }
                        count = 0;

                    }
                }
            }

        }
        return max;*/
    }

    public int huristicForwardDiagnol() {
        int max = Integer.MIN_VALUE;
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i > -1 && i < rows) && j > -1 && j < columns) {
                    if (board[i][j] == player) {

                        for (int k = 0; k <= 4; k++) {
                            if (i + k < rows && j + k < columns && i + k > -1 && j + k > -1) {
                                if (board[i + k][j + k] == player) {


                                    count++;
                                }

                                if (board[i + k][j + k] != player) {

                                    break;
                                }
                            }


                        }

                        switch (count) {
                            case 4:
                                //System.out.println("There are 4 points");
                                count = count * 1000;
                                return count;

                            case 3:
                                // System.out.println("There are 3 points");
                                count = count * 100;
                                break;

                            case 2:
                                // System.out.println("There are 2 points");
                                count = count * 10;
                                break;

                            case 1:
                                //  System.out.println("There are 1 points");
                                count = count * 1;
                                break;

                            default:
                                count = 0;

                        }

                        if (max < count) {
                            max = count;
                        }
                        count = 0;

                    }
                }
            }

        }
        return max;
    }

    public int huristicBackWardDiagnol() {
        int max = Integer.MIN_VALUE;
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i > -1 && i < rows) && j > -1 && j < columns) {
                    if (board[i][j] == player) {

                        for (int k = 0; k <= 4; k++) {
                            if (i + k < rows && j - k < columns && i + k > -1 && j - k > -1) {
                                if (board[i + k][j - k] == player) {


                                    count++;
                                }

                                if (board[i + k][j - k] != player) {

                                    break;
                                }
                            }


                        }

                        switch (count) {
                            case 4:
                                //System.out.println("There are 4 points");
                                count = count * 1000;
                                return count;

                            case 3:
                                // System.out.println("There are 3 points");
                                count = count * 100;
                                break;

                            case 2:
                                //  System.out.println("There are 2 points");
                                count = count * 10;
                                break;

                            case 1:
                                //  System.out.println("There are 1 points");
                                count = count * 1;
                                break;

                            default:
                                count = 0;

                        }

                        if (max < count) {
                            max = count;
                        }
                        count = 0;

                    }
                }
            }

        }
        return max;
    }


    public int bestMove() {
        int max = 0;

        if (max < huristicRows()) {
            max = huristicRows();
        }

        if (max < huristicColumns()) {
            max = huristicColumns();
        }

        if (max < huristicForwardDiagnol()) {
            max = huristicForwardDiagnol();
        }

        if (max < huristicBackWardDiagnol()) {
            max = huristicBackWardDiagnol();
        }

        return max;
    }
}
