package edu.sustech.xiangqi.model;

// 将 / 帅：每次横走或竖走一格，不能离开己方九宫

public class  CarPiece extends Piece {
    public CarPiece(boolean isRed) {
        super(isRed ? "车" : "車", isRed);
    }

    @Override
    public boolean canMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        if((toRow - fromRow) *(toCol - fromCol) != 0){
            return false;
        }
        if(toRow - fromRow != 0){
            for(int i = fromRow + (toRow - fromRow) / Math.abs(toRow - fromRow); i != toRow ;
             i += (toRow - fromRow) / Math.abs(toRow - fromRow)){
                if(board.getPieceAt(i, toCol) != null){
                    return false;
                }
            }
        }else{
            for(int i = fromCol + (toCol - fromCol) / Math.abs(toCol - fromCol); i != toCol ;
             i += (toCol - fromCol) / Math.abs(toCol - fromCol)){
                if(board.getPieceAt(toRow, i) != null){
                    return false;
                }
            }
        }
        return true;
    }
}
