package edu.sustech.xiangqi.model;

// 将 / 帅：每次横走或竖走一格，不能离开己方九宫

public class  CarPiece extends Piece {
    public CarPiece(boolean isRed) {
        super(isRed ? "车" : "車", isRed);
    }

    @Override
    public boolean canMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        // 两方九宫都在第 3～5 列
        if((toRow - fromRow) *(toCol - fromCol) != 0){
            return false;
        }
        if(toRow - fromRow != 0){
            id(toRow - fromRow > 0){
                for(int i = fromCol ; i <= toRow;i++)
            }
        }
        // 行列距离之和为 1，表示横走或竖走一格
        return Math.abs(fromRow - toRow) + Math.abs(fromCol - toCol) == 1;
    }
}
