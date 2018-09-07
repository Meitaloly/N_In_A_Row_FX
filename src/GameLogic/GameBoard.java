package GameLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        resetBoard();
    }

    public boolean isColFull(int col) {
        boolean res = false;
        if (board[0][col-1] != -1) {
            res = true;
        }
        return res;
    }
    public boolean isBoardFull()
    {
        boolean res = true;
        for(int i = 0;i<cols;i++) {
            if (board[0][i] == -1) {
                res = false;
                break;
            }
        }
        return res;
    }

    public boolean checkPlayerCanRemove(int sign)
    {
        boolean res = false;
        for(int i = 0;i<cols;i++) {
            if (board[(int)rows-1][i] == sign) {
                res = true;
                break;
            }
        }
        return res;
    }

    public boolean checkColEmpty(int col)
    {
        boolean res = false;
        if(board[(int)this.rows-1][col] == -1 )
        {
            res = true;
        }
        return res;
    }

    public boolean checkSignAndRemove(int col, int sign)
    {
        boolean res = false;
        if(board[(int)this.rows-1][col] == sign )
        {
            res = true;
            for(int i = (int)this.rows-1; i>0 ; i--)
            {
                board[i][col] = board[i-1][col];
            }
            board[0][col] = -1;
        }
        return res;
    }
    public void setEmptyBoard()
    {
        board = new int[Math.toIntExact(rows)][Math.toIntExact(cols)];
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
        resetBoard();
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
                if(board[i][j] != -1) {
                    System.out.print(" ");
                }
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

    public boolean checkPlayerWin(int col, String gameType, String playerType)
    {
//        if(playerType.toUpperCase().equals("COMPUTER"))
//        {
//            col--;
//        }
        boolean res = false;
        int i = 0 ;

        while (board[i][col-1] == -1){
            i++;
        }
        if(gameType.toUpperCase().equals("CIRCULAR")) {
            res = isDiagonal(i, col - 1) || isHorizontalCircular(i, col - 1) || isVerticalCircular(i, col - 1);
        }
        else
        {
            res = isDiagonal(i, col - 1) || isHorizontal(i, col - 1) || isVertical(i, col - 1);
        }
        return res;
    }


    public boolean isDiagonal(int row, int col)
    {
        boolean res = false;
        long len = target -1;
        int mySign = board[row][col];
        len -= checkLeftUp(row,col,mySign,len);
        if(len>0) {
            len -= checkRightDown(row, col, mySign, len);
            if (len > 0) {
                len = target - 1;
                len -= checkRightUp(row, col, mySign, len);
                if (len > 0) {
                    len -= checkLeftDown(row, col, mySign, len);
                    if(len == 0)
                    {
                        res = true;
                    }
                }
                else
                {
                    res =true;
                }
            }
            else
            {
                res = true;
            }
        }
        else
        {
            res =true;
        }

        if(res)
        {
            System.out.println("won in Diagonal");
        }
        return res;
    }


    private long checkRightUp(int row, int col, int mySign,long newTarget)
    {
        long len =0;
        boolean stop = false;
        int newRow = row-1;
        int newCol = col+1;

        while(newCol<=cols-1 && newRow<=rows-1 && !stop && len<=newTarget)
        {
            if(newCol<0 || newRow<0)
            {
                stop= true;
            }
            else {
                if (board[newRow][newCol] == mySign) {
                    newRow = newRow - 1;
                    newCol = newCol + 1;
                    len++;
                } else {
                    stop = true;
                }
            }
        }
        return len;
    }

    private long checkLeftDown(int row, int col, int mySign,long newTarget)
    {
        long len =0;
        boolean stop = false;
        int newRow = row+1;
        int newCol = col-1;

        while(newCol<=cols-1 && newRow<=rows-1 && !stop && len<=newTarget)
        {
            if(newCol<0 || newRow<0)
            {
                stop= true;
            }
            else {
                if (board[newRow][newCol] == mySign) {
                    newRow = newRow + 1;
                    newCol = newCol - 1;
                    len++;

                } else {
                    stop = true;
                }
            }
        }
        return len;
    }

    private long checkRightDown(int row, int col, int mySign,long newTarget)
    {
        long len =0;
        boolean stop = false;
        int newRow = row+1;
        int newCol = col +1;

        while(newCol<=cols-1 && newRow<=rows-1 && !stop && len<=newTarget)
        {
            if(newCol<0 || newRow<0)
            {
                stop= true;
            }
            else {
                if (board[newRow][newCol] == mySign) {
                    newRow = newRow + 1;
                    newCol = newCol + 1;
                    len++;

                } else {
                    stop = true;
                }
            }
        }

        return len;
    }

    private long checkLeftUp(int row, int col, int mySign,long newTarget)
    {
        long len =0;
        boolean stop = false;
        int newRow = row-1;
        int newCol = col -1;

        while(newCol<=cols-1  && newRow<=rows-1 && !stop && len<=newTarget)
        {
            if(newCol<0 || newRow<0)
            {
                stop= true;
            }
            else {
                if (board[newRow][newCol] == mySign) {
                    newRow = newRow - 1;
                    newCol = newCol - 1;
                    len++;
                } else {
                    stop = true;
                }
            }
        }

        return len;
    }
//
//    public boolean isDiagonal(int row,int col){
//        boolean res = false;
//        long  len = target-1;
//        int mySign = board[row][col];
//        int newCol = col +1, newRow = row+1;
//
//        while (newCol <= cols-1 && newRow <= rows-1 && !res && board[newRow][newCol] == mySign ){  //go down rigth
//            len--;
//            if (len > 0 ){
//                newCol++;
//                newRow++;
//            }
//            else if (len == 0){
//                res = true;
//            }
//        }
//        if (!res && len > 0 ){
//            newCol = col - 1;
//            newRow = row -1;
//            while (newCol>=1 && newRow >= 1 && !res && board[newRow][newCol] == mySign ){
//                len--;
//                if (len > 0 ){
//                    newCol--;
//                    newRow--;
//                }
//                else if (len ==0){
//                    res = true;
//                }
//            }
//        }
//        if (!res){
//            newCol = col + 1;
//            newRow = row - 1;
//
//            while (newCol <= cols-1 && newRow >= 1 && !res && board[newRow][newCol] == mySign ){  //go down rigth
//                len--;
//                if (len > 0 ){
//                    newCol++;
//                    newRow--;
//                }
//                else if (len == 0){
//                    res = true;
//                }
//            }
//            if (!res && len > 0 ){
//                newCol = col - 1;
//                newRow = row  + 1;
//                while (newCol >= 1 && newRow <= rows-1 && !res && board[newRow][newCol] == mySign ){
//                    len--;
//                    if (len > 0 ){
//                        newCol--;
//                        newRow++;
//                    }
//                    else if (len ==0){
//                        res = true;
//                    }
//                }
//            }
//
//        }
//
//        if (res)
//        {
//            System.out.println("won in Diagonal");
//        }
//        return res;
//    }


    public boolean isHorizontalCircular(int row, int col) {
        boolean res = false;
        long len = this.target - 1;
        int mySign = this.board[row][col];
        int newCol = (col + 1) % (int)this.cols;

        while((long)newCol != col && !res && this.board[row][newCol] == mySign) {
            --len;
            if (len > 0L) {
                ++newCol;
                newCol= newCol % (int)(this.cols);
            } else if (len == 0) {
                res = true;
            }
        }

        if (!res && len > 0) {
            newCol = col - 1;
            if(newCol<0 )
            {
                newCol = (int)this.cols-1;
            }
            while(newCol != col && !res && this.board[row][newCol] == mySign) {
                --len;
                if (len > 0) {
                    --newCol;
                    if(newCol<0) {
                        newCol = (int)(this.cols - 1);
                    }
                } else if (len == 0) {
                    res = true;
                }
            }
        }

        if (res)
        {
            System.out.println("won in H");
        }
        return res;
    }

    public boolean isVerticalCircular(int row, int col) {
        boolean res = false;
        long len = this.target - 1;
        int mySign = this.board[row][col];
        int newRow = (row + 1) % (int)this.rows;

        while((long)newRow != row && !res && this.board[newRow][col] == mySign) {
            --len;
            if (len > 0) {
                ++newRow;
                newRow = newRow  % (int)(this.rows);
            } else if (len == 0) {
                res = true;
            }
        }

        if (!res && len > 0) {
            newRow = row - 1;
            if (newRow < 0) {
                newRow = (int)this.rows-1;
            }
            while (newRow != row  && !res && this.board[newRow][col] == mySign) {
                --len;
                if (len > 0) {
                    --newRow;
                    if (newRow < 0) {
                        newRow = (int) (this.rows - 1);
                    }
                } else if (len == 0) {
                    res = true;
                }
            }
        }

        if (res)
        {
            System.out.println("won in V");
        }
        return res;
    }

    public boolean isHorizontal(int row,int col){
        boolean res = false;
        long  len = target-1;
        int mySign = board[row][col];
        int newCol = col + 1;

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


        if (res)
        {
            System.out.println("won in H");
        }
        return res;
    }


    public boolean isVertical(int row,int col){
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

        if (res)
        {
            System.out.println("won in V");
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


    public boolean checkAnyWinner(int col, List<Integer> winnersList)
    {
        boolean res = false;

        for(int i = (int)this.rows-1; i>=0; i--)
        {
            if(board[i][col]!=-1) {
                if (isDiagonal(i, col) || isHorizontal(i,col) || isVertical(i,col)) {
                    winnersList.add(board[i][col]);
                    res= true;
                }
            }
        }

        return res;
    }

    public List<Integer> checkAvaliableColForEnter()
    {
        List<Integer> res = new ArrayList<>();

        for(int i=0; i<cols; i++)
        {
            if(board[0][i] ==-1)
            {
                res.add(i);
            }
            else
            {
                int j=1;
            }
        }
        return res;
    }

    public List<Integer> checkAvaliableColForRemove(int sign)
    {
        List<Integer> res = new ArrayList<>();

        for(int i=0; i<cols; i++)
        {
            if(board[(int)this.rows-1][i] == sign)
            {
                res.add(i);
            }
        }
        return res;

    }

    public void removeAllDisksOfPlayer(Player p){
        int row;
        int col = 0;
        int signToRemove = p.getPlayerSign();
        for (col = 0 ; col < cols; col ++ ) {
            for (row = (int)rows-1; row >= 0 && board[row][col] != -1; row--) {
                if (row == 0){
                    board[row][col] = -1;
                }
                else {
                    while (board[row][col] == signToRemove) {
                        for (int row2 = row; row2 >= 0 && board[row2][col] != -1; row2--) {
                            if (row2 == 0) {
                                board[row2][col] = -1;
                            } else {
                                board[row2][col] = board[row2 - 1][col];
                            }
                        }
                    }
                }
            }
            if (row >= 0) {
                board[row][col] = -1;
            }
        }
        printBoard();
    }

}
