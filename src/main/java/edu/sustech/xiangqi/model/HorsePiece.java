package edu.sustech.xiangqi.model;

public class HorsePiece extends Piece {
    public HorsePiece(boolean isRed) {
        super(isRed ? "马" : "马", isRed);
    }

    @Override
    public boolean canMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        if(!((Math.abs(toCol - fromCol) == 2 && Math.abs(toRow - fromRow) == 1) || 
            (Math.abs(toCol - fromCol) == 1 && Math.abs(toRow - fromRow) == 2))){
            return false;
        }
        int to = -1;
        if(toCol - fromCol == 2){
            to = 0;//右
        }else if(toRow - fromRow == 2){
            to = 1;//上
        }else if(toCol - fromCol == -2){
            to = 2;//左
        }else{
            to = 3;//下
        }
        int[][] move = new int[4][2];
        move[0][0] = 0;move[0][1] = 1;
        move[1][0] = 1;move[1][1] = 0;
        move[2][0] = 0;move[2][1] = -1;
        move[3][0] = -1;move[3][1] = 0;
        if(board.getPieceAt(fromRow + move[to][0] , fromCol + move[to][1]) != null){
            return false;
        }
        return true;
    }
}
