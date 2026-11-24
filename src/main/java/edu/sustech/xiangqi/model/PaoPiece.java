package edu.sustech.xiangqi.model;

public class PaoPiece extends Piece{
    public PaoPiece(boolean isRed) {
        super(isRed ? "炮" : "砲", isRed);
    }

    @Override
    public boolean canMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        if((toRow - fromRow) *(toCol - fromCol) != 0){
            return false;
        }
        int piece_in_way = 0;
        if(toRow - fromRow != 0){
            for(int i = fromRow + (toRow - fromRow) / Math.abs(toRow - fromRow); i != toRow ;
             i += (toRow - fromRow) / Math.abs(toRow - fromRow)){
                if(board.getPieceAt(i, toCol) != null){
                    piece_in_way += 1;
                }
            }
        }else{
            for(int i = fromCol + (toCol - fromCol) / Math.abs(toCol - fromCol); i != toCol ;
             i += (toCol - fromCol) / Math.abs(toCol - fromCol)){
                if(board.getPieceAt(toRow, i) != null){
                    piece_in_way += 1;
                }
            }
        }
        if(piece_in_way == 0 && board.getPieceAt(toRow, toCol) == null) return true;
        if(piece_in_way == 1 && board.getPieceAt(toRow, toCol).isRed() != this.isRed()) return true;
        return false;
    }
}
