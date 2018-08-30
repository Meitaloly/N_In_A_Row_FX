package GameLogic;

import java.util.Arrays;

public class GameBoard {
    long rows;
    long cols;
    long target;
    int[][] board;
    int numOfFreePlaces;

    public GameBoard(){}

    public void setGameBoard(long i_rows, long i_cols, long i_target)
    {
        rows = i_rows;
        cols = i_cols;
        target = i_target;
        numOfFreePlaces = (int)(rows) * (int)(cols);
        board = new int[Math.toIntExact(rows)][Math.toIntExact(cols)];
        buildTheBoard();
    }

    public boolean isColFull(int col) {
        boolean res = false;
        if (board[0][col-1] != -1) {
            res = true;
        }
        return res;
    }

    public void setEmptyBoard()
    {
        board = new int[Math.toIntExact(rows)][Math.toIntExact(cols)];
    }

    public void buildTheBoard() {

//        for (int i = 0; i < cols; i++) {
//            board[0][i] = i;
//        }

        for (int i = 0; i < rows; i++) {
            Arrays.fill(board[i], -1);//            board[i][0] = i;

        }
    }

    public void resetBoard(){
        for (int i = 0; i < rows; i++) {
            Arrays.fill(board[i], -1);
            //board[i][0] = i;
        }
    }
    public void setTarget(long target) {
        this.target = target;
    }

    public void reset() {
        buildTheBoard();
        numOfFreePlaces = (int) (rows - 1) * (int) (cols - 1);
    }


    public void resetNumOfFreePlaces() {
        numOfFreePlaces = (int) (rows - 1) * (int) (cols - 1);
    }

    public void setCols(long cols) {
        this.cols = cols;
    }

    public void setRows(long rows) {
        this.rows = rows;
    }

    public void setCubeInBoard(int row,int col, int value)
    {
        this.board[row][col] = value;
    }

    public long getCols() {
        return cols;
    }

    public int[][] getBoard() {
        return board;
    }

    public long getRows() {
        return rows;
    }

    public long getTarget() {
        return target;
    }

    public void setSignOnBoard(int columToPutIn , Player player)
    {
        for(int i = (int)rows-1; i>=0;i--)
        {
            if(board[i][columToPutIn] == -1)
            {
                board[i][columToPutIn] = player.getPlayerSign();
                numOfFreePlaces--;
                int j=0 ; // to check what the value of mumOfFreePlaces
                break;
            }
        }
    }

    public void printBoard()
    {
        System.out.println("curr game board:");
        for(int i=0; i<rows ; i++)
        {
            for(int j=0; j<cols; j++)
            {
                System.out.print(board[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
    public int getNumOfFreePlaces() {
        return numOfFreePlaces;
    }

    public boolean checkIfAvaliable(int col)
    {
        boolean res= false;
        if(board[1][col] == -1)
        {
            res = true;
        }
        return res;
    }

    public boolean checkPlayerWin(int col)
    {
        boolean res = false;
        int i = 1 ;
        while (board[i][col-1] == -1){
            i++;
        }
        res = isDiagonal(i,col-1) || isHorizontal(i,col-1) || isvertical(i,col-1);
        // check if won
        return res;
    }

    public boolean isDiagonal(int row,int col){
        boolean res = false;
        long  len = target-1;
        int mySign = board[row][col];
        int newCol = col +1, newRow = row+1;

        while (newCol <= cols-1 && newRow <= rows-1 && !res && board[newRow][newCol] == mySign ){  //go down rigth
            len--;
            if (len > 0 ){
                newCol++;
                newRow++;
            }
            else if (len == 0){
                res = true;
            }
        }
        if (!res && len > 0 ){
            newCol = col - 1;
            newRow = row -1;
            while (newCol>=1 && newRow >= 1 && !res && board[newRow][newCol] == mySign ){
                len--;
                if (len > 0 ){
                    newCol--;
                    newRow--;
                }
                else if (len ==0){
                    res = true;
                }
            }
        }
        if (!res){
            newCol = col + 1;
            newRow = row - 1;

            while (newCol <= cols-1 && newRow >= 1 && !res && board[newRow][newCol] == mySign ){  //go down rigth
                len--;
                if (len > 0 ){
                    newCol++;
                    newRow--;
                }
                else if (len == 0){
                    res = true;
                }
            }
            if (!res && len > 0 ){
                newCol = col - 1;
                newRow = row  + 1;
                while (newCol >= 1 && newRow <= rows-1 && !res && board[newRow][newCol] == mySign ){
                    len--;
                    if (len > 0 ){
                        newCol--;
                        newRow++;
                    }
                    else if (len ==0){
                        res = true;
                    }
                }
            }

        }

        return res;
    }

    public boolean isHorizontal(int row,int col){
        boolean res = false;
        long  len = target-1;
        int mySign = board[row][col];
        int newCol = col +1;

        while (newCol <= cols-1 && !res && board[row][newCol] == mySign  ){
            len--;
            if (len > 0 ){
                newCol++;
            }
            else if (len == 0){
                res = true;
            }
        }

        if (!res && len > 0 ){
            newCol = col - 1;
            while (newCol>=1 && !res && board[row][newCol] == mySign ){
                len--;
                if (len > 0 ){
                    newCol--;
                }
                else if (len ==0){
                    res = true;
                }
            }
        }
        return res;
    }


    public boolean isvertical(int row,int col){
        boolean res = false;
        long  len = target-1;
        int mySign = board[row][col];
        int newRow = row +1;

        while (newRow <= rows-1 && !res && board[newRow][col] == mySign ){
            len--;
            if (len > 0 ){
                newRow++;
            }
            else if (len == 0){
                res = true;
            }
        }

        if (!res && len > 0 ){
            newRow = row - 1;
            while (newRow >= 1  && !res && board[newRow][col] == mySign ){
                len--;
                if (len > 0 ){
                    newRow--;
                }
                else if (len ==0){
                    res = true;
                }
            }
        }
        return res;
    }

    public int gameUndo(int col){
        int i = 1 ;
        int res;
        while (board[i][col] == 0){
            i++;
        }
        if (board[i][col] == 35){
            res = 2;
        }
        else{
            res = 1;
        }
        board[i][col] = 0;
        numOfFreePlaces --;
        return res;
    }
}
