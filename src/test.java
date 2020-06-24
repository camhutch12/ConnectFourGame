public class test {
    public test (){
        int[][] board = new int[6][7];
        //board[3][0] = 1;
        board[3][0] = 2;
        board[4][0] = 2;
        board[5][0] = 2;
        Node temp = new Node(board, 2);
        temp.board = board;
        System.out.println(temp.huristicColumns());

    }

}
