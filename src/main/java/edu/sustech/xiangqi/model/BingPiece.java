package edu.sustech.xiangqi.model;

// 士 / 仕：每次斜走一格，不能离开己方九宫

public class BingPiece extends Piece {
    public BingPiece(boolean isRed) {
        super(isRed ? "兵" : "卒", isRed);
    }

    @Override
    public boolean canMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        // 行和列必须都变化一格，不能直走、原地停留或跨越多格
        if(isRed()){
            if (fromRow >= 5) {
                if(toCol != fromCol) return false;
                if(toRow - fromRow != -1) return false;
                return true;
            }else{
                if((toRow - fromRow) * (toCol - fromCol) != 0) return false;
                if((toRow - fromRow == -1) || Math.abs(toCol - fromCol) == 1) return true;
                return false;
            }
        }else{
            if (fromRow <= 4) {
                if(toCol != fromCol) return false;
                if(toRow - fromRow != 1) return false;
                return true;
            }else{
                if((toRow - fromRow) * (toCol - fromCol) != 0) return false;
                if((toRow - fromRow == 1) || Math.abs(toCol - fromCol) == 1) return true;
                return false;
            }
        }
    }
}
