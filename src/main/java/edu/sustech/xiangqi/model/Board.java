package edu.sustech.xiangqi.model;

// 保存棋盘数据，并统一检查和执行移动

public class Board {
    // 定义棋盘大小
    public static final int ROWS = 10;
    public static final int COLS = 9;

    // 存储棋子位置
    private final Piece[][] pieces = new Piece[ROWS][COLS];
    private boolean redTurn = true;
    public enum GameStatus { IN_PROGRESS, CHECK, RED_WINS, BLACK_WINS }

    private GameStatus status;

    public Board() {
        initializePieces();
        updateStatus();
    }

    public GameStatus getStatus() {
        return status;
    }

    private void initializePieces() {
        pieces[9][3] = new AdvisorPiece(true);
        pieces[9][4] = new GeneralPiece(true);
        pieces[9][5] = new AdvisorPiece(true);
        pieces[9][1] = new HorsePiece(true);
        pieces[9][7] = new HorsePiece(true);
        pieces[9][0] = new CarPiece(true);
        pieces[9][8] = new CarPiece(true);
        pieces[7][1] = new PaoPiece(true);
        pieces[7][7] = new PaoPiece(true);
        pieces[9][2] = new XiangPiece(true);
        pieces[9][6] = new XiangPiece(true);
        pieces[6][0] = new BingPiece(true);
        pieces[6][2] = new BingPiece(true);
        pieces[6][4] = new BingPiece(true);
        pieces[6][6] = new BingPiece(true);
        pieces[6][8] = new BingPiece(true);

        pieces[0][3] = new AdvisorPiece(false);
        pieces[0][4] = new GeneralPiece(false);
        pieces[0][5] = new AdvisorPiece(false);
        pieces[0][1] = new HorsePiece(false);
        pieces[0][7] = new HorsePiece(false);
        pieces[0][0] = new CarPiece(false);
        pieces[0][8] = new CarPiece(false);
        pieces[2][1] = new PaoPiece(false);
        pieces[2][7] = new PaoPiece(false);
        pieces[0][2] = new XiangPiece(false);
        pieces[0][6] = new XiangPiece(false);
        pieces[3][0] = new BingPiece(false);
        pieces[3][2] = new BingPiece(false);
        pieces[3][4] = new BingPiece(false);
        pieces[3][6] = new BingPiece(false);
        pieces[3][8] = new BingPiece(false);
    }

    public boolean isInside(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }

    public Piece getPieceAt(int row, int col) {
        if (!isInside(row, col)) {
            return null;
        }
        return pieces[row][col];
    }

    public boolean tryMove(int fromRow, int fromCol, int toRow, int toCol) {
        if (status == GameStatus.RED_WINS || status == GameStatus.BLACK_WINS) {
            return false;
        }
        if (!isInside(fromRow, fromCol) || !isInside(toRow, toCol)) {
            return false;
        }
        if (fromRow == toRow && fromCol == toCol) {
            return false;
        }

        Piece piece = getPieceAt(fromRow, fromCol);
        if (piece == null || piece.isRed() != redTurn) {
            return false;
        }

        if (!moveIfLegal(piece, fromRow, fromCol, toRow, toCol, true)) {
            return false;
        }
        redTurn = !redTurn;
        updateStatus();
        return true;
    }

    private boolean moveIfLegal(Piece piece, int fromRow, int fromCol,
                                int toRow, int toCol, boolean commit) {
        Piece target = pieces[toRow][toCol];
        if (target != null && target.isRed() == piece.isRed()) {
            return false;
        }

        if (!piece.canMove(this, fromRow, fromCol, toRow, toCol)) {
            return false;
        }

        pieces[fromRow][fromCol] = null;
        pieces[toRow][toCol] = piece;
        boolean legal = isGeneralSafe(redTurn);
        if (!legal || !commit) {
            pieces[fromRow][fromCol] = piece;
            pieces[toRow][toCol] = target;
        }
        return legal;
    }

    private void updateStatus() {
        if (!hasLegalMove()) {
            status = redTurn ? GameStatus.BLACK_WINS : GameStatus.RED_WINS;
        } else {
            status = isGeneralSafe(redTurn) ? GameStatus.IN_PROGRESS : GameStatus.CHECK;
        }
    }

    private boolean hasLegalMove() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Piece piece = pieces[row][col];
                if (piece == null || piece.isRed() != redTurn) {
                    continue;
                }
                for (int toRow = 0; toRow < ROWS; toRow++) {
                    for (int toCol = 0; toCol < COLS; toCol++) {
                        if (moveIfLegal(piece, row, col, toRow, toCol, false)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isGeneralSafe(boolean red) {
        int palaceStart = red ? 7 : 0;
        for (int row = palaceStart; row < palaceStart + 3; row++) {
            for (int col = 3; col <= 5; col++) {
                Piece piece = pieces[row][col];
                if (piece instanceof GeneralPiece && piece.isRed() == red) {
                    return !areGeneralsFacing(red, row, col) && !isGeneralAttacked(red, row, col);
                }
            }
        }
        return false;
    }

    private boolean areGeneralsFacing(boolean red, int generalRow, int generalCol) {
        int step = red ? -1 : 1;
        for (int row = generalRow + step; row >= 0 && row < ROWS; row += step) {
            Piece piece = pieces[row][generalCol];
            if (piece != null) {
                return piece instanceof GeneralPiece && piece.isRed() != red;
            }
        }
        return false;
    }

    private boolean isGeneralAttacked(boolean red, int generalRow, int generalCol) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Piece attacker = pieces[row][col];
                if (attacker == null || attacker.isRed() == red) {
                    continue;
                }
                if (attacker.canMove(this, row, col, generalRow, generalCol)) {
                    return true;
                }
            }
        }
        return false;
    }
}
