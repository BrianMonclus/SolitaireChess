
package chessGame;

import java.util.ArrayList;
import java.util.Observable;
import chessPieces.Blank;
import chessPieces.Pieces;

/**
 * @author Jean Luis Urena
 * @author ID: ju7847
 *
 *         This class is the ChessGame controller of the GUI. It basically has
 *         all methods necessary for the game.
 */

public class ChessGame extends Observable {

    // Default values false.
    private boolean isSelected = false;
    private boolean invalidMove = false;
    private boolean noNextMove = false;

    private ChessBoard chessBoard;
    public int colLength;
    private int moveCount;
    private int[] pieceHeld; // This will hold row and col location of piece
                             // held
    public int rowLength;

    /**
     * Constructor takes a chess board and creates a copy for reset
     * 
     * @param board
     */
    public ChessGame( ChessBoard chessBoard ) {
        this.chessBoard = chessBoard;
        moveCount = 0;
        pieceHeld = new int[2]; // Will get value in methods
        rowLength = this.chessBoard.getRows();
        colLength = this.chessBoard.getCols();

    }

    // Getter of chess
    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public boolean getIsInvalidMove() {
        return invalidMove;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    // Getter of move count
    public int getMoveCount() {
        return moveCount;
    }

    public boolean getNoNextMove() {
        return noNextMove;
    }

    /**
     * This method will validate a move
     * 
     * @param secondRow
     *            - int index of row location of piece to move to
     * @param secondCol
     *            - int index of col location of piece to move to
     * @return boolean returns true if valid move else otherwise
     */
    public boolean isValid( Pieces piece, int secondRow, int secondCol ) {

        try {
            ArrayList<int[][]> solutions = piece.getPieceNeighbors();

            for ( int[][] iterate : solutions ) {

                int firstRow = iterate[0][0];
                int firstCol = iterate[0][1];
                if ( firstRow == secondRow && firstCol == secondCol )
                    return true;

            }
        } catch ( NullPointerException ex ) {

        }
        return false;

    }

    /**
     * This method will return a new board with the newly positioned pieces and
     * change the field board
     * 
     * @param firstRow
     *            - index row of piece to move
     * @param firstCol
     *            - index col of piece to move
     * @param secondRow
     *            - index row of piece to move to
     * @param secondCol
     *            - index col of piece to move to
     * @return char[][] - new board with updated positions
     */
    public void movePiece( int secondRow, int secondCol ) {

        // First piece selection positions
        int fRow = pieceHeld[0];
        int fCol = pieceHeld[1];

        // Selecting the first piece validation
        // Confirming not selecting empty space
        if ( chessBoard.getPieceOnBoard( fRow, fCol ) == null ) {
            invalidMove = true;

            setChanged();
            notifyObservers();
            invalidMove = false;

            isSelected = false;
            return;

        }

        Pieces selectedPiece = selectPiece( fRow, fCol );

        // Verifying piece to capture is a valid move
        if ( !isValid( selectedPiece, secondRow, secondCol ) ) {

            // If invalid toggle invalid move
            invalidMove = true;

            // Notify observers
            setChanged();
            notifyObservers();

            invalidMove = false;

            isSelected = false;

        } else {

            // Delete overtaken piece
            chessBoard.removePiece( secondRow, secondCol );

            // Assigning the piece a value on new board
            selectedPiece.setPiecePosition( secondRow, secondCol );

            // Remaking the board
            chessBoard.setBoardPiece( new Blank( chessBoard, fRow, fCol ), fRow,
                    fCol );
            chessBoard.setBoardPiece( selectedPiece, secondRow, secondCol );

            // increment move count
            moveCount++;

            // toggle is selcted
            isSelected = false;

            // Notify observers of change in board
            setChanged();
            notifyObservers();

        }

    }

    /**
     * Description: This method searches for the next best move and makes it.
     * 
     */
    public void nextBestMove() {

        try {
            // Setting the value of the chess to the current board for solver
            Solver<ChessBoard> solver = new Solver<ChessBoard>();
            ArrayList<ChessBoard> solutions = solver.solve( chessBoard );

            // Return the first step from solutions
            chessBoard = new ChessBoard( solutions.get( 1 ) );
            chessBoard.redrawBoard();

            // incrementing count
            moveCount++;

            setChanged();
            notifyObservers();
        } catch ( NullPointerException ex ) {
            noNextMove = true;
            setChanged();
            notifyObservers();
        }

    }

    /**
     * Description: This method will reset the board to the original board
     */
    public void reset() {

        // Reset board to original difficulty
        int difficulty = chessBoard.getDifficulty();
        chessBoard = new ChessBoard( difficulty );

        // Redraw entire board
        chessBoard.redrawBoard();

        // reseting counters
        moveCount = 0;
        invalidMove = false;
        isSelected = false;
        // Notify observers of change
        setChanged();
        notifyObservers();

    }

    /**
     * Returns a piece selected
     * 
     * @param row
     *            - row index of piece
     * @param col
     *            - col index of piece
     * @return Pieces - returns the pieces object
     */
    public Pieces selectPiece( int row, int col ) {

        // Setting piece held
        pieceHeld[0] = row;
        pieceHeld[1] = col;

        // toggle is selected
        isSelected = true;

        setChanged();
        notifyObservers();

        return chessBoard.getPieceOnBoard( row, col );

    }

    /**
     * Description: Returns whether the game has been won or not by checking if
     * only one more piece exists.
     * 
     * @return boolean @exception
     */
    public boolean wonGame() {

        int count = 0;
        for ( int i = 0; i < rowLength; i++ ) {
            for ( int j = 0; j < colLength; j++ ) {
                if ( !chessBoard.getPieceOnBoard( i, j ).isBlank() )
                    count++;

                if ( count > 1 )
                    return false;
            }
        }
        return true;
    }

}
