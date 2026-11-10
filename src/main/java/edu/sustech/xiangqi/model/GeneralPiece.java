package edu.sustech.xiangqi.model;

// 将 / 帅：每次横走或竖走一格，不能离开己方九宫

public class GeneralPiece extends Piece {
    public GeneralPiece(boolean isRed) {
        super(isRed ? "帅" : "将", isRed);
    }

    @Override
    public boolean canMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        // 两方九宫都在第 3～5 列
        if (toCol < 3 || toCol > 5) {
            return false;
        }

        // 红方九宫在下方，黑方九宫在上方
        if (isRed()) {
            if (toRow < 7 || toRow > 9) {
                return false;
            }
        } else {
            if (toRow < 0 || toRow > 2) {
                return false;
            }
        }
        // 行列距离之和为 1，表示横走或竖走一格
        return Math.abs(fromRow - toRow) + Math.abs(fromCol - toCol) == 1;
    }
}
