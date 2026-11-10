package edu.sustech.xiangqi.model;

// 士 / 仕：每次斜走一格，不能离开己方九宫

public class AdvisorPiece extends Piece {
    public AdvisorPiece(boolean isRed) {
        super(isRed ? "仕" : "士", isRed);
    }

    @Override
    public boolean canMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        // 行和列必须都变化一格，不能直走、原地停留或跨越多格
        if (Math.abs(toRow - fromRow) != 1 || Math.abs(toCol - fromCol) != 1) {
            return false;
        }

        // 列判断
        if (toCol < 3 || toCol > 5) {
            return false;
        }

        // 红方九宫在下方，黑方九宫在上方
        if (isRed()) {
            return toRow >= 7 && toRow <= 9;
        } else {
            return toRow >= 0 && toRow <= 2;
        }
    }
}
