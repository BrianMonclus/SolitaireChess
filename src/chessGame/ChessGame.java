
package chessGame;

import java.util.ArrayList;
import java.util.Observable;

import chessPieces.Blank;
import chessPieces.Pieces;

/*
 * ChessGame.java
 *
 * Version:
 * $Id: ChessGame.java,v 1.4 2015/12/12 04:42:12 ju7847 Exp $
 *
 * Revisions:
 * $Log: ChessGame.java,v $
 * Revision 1.4  2015/12/12 04:42:12  ju7847
 * Silly, forgot to toggle isSelected
 *
 * Revision 1.3  2015/12/12 04:24:50  ju7847
 * Fixed another small mistake
 *
 * Revision 1.2  2015/12/12 04:02:10  ju7847
 * Finished .. .debugging some more
 *
 * Revision 1.1  2015/12/11 21:25:20  ju7847
 * Almost done with GUI... i think
 *
 */

/**
 * @author Jean Luis Urena
 * @author ID: ju7847
 *
 *
 *         This class will serve to run the Solitare Chess game CS242
 */

public class ChessGame extends Observable {

    private Chess chess;
    private int colLength;
    public boolean isSelected;
    private int moveCount;
    private int[] pieceHeld; // This will hold row and col location of piece
                             // held

    private Chess origBoard;
    private int rowLength;
    public boolean invalidMove = false;
    public boolean noNextMove = false;

    /**
     * Constructor takes a chess board and creates a new one
     * 
     * @param board
     */
    public ChessGame( Chess chess ) {
        this.chess = chess;
        isSelected = false;
        moveCount = 0;
        pieceHeld = new int[2]; // Will get value in methods
        rowLength = this.chess.getRows();
        colLength = this.chess.getCols();

        // Copying original board retaining original for reset
        origBoard = new Chess( chess );

    }

    // Getter of chess
    public Chess getChess() {
        return chess;
    }

    // Getter for column length
    public int getColLength() {
        return colLength;
    }

    // Getter of move count
    public int getMoveCount() {
        return moveCount;
    }

    // Getter for row length
    public int getRowLength() {
        return rowLength;
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
        if ( chess.getPieceOnBoard( fRow, fCol ) == null ) {
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
            chess.removePiece( secondRow, secondCol );

            // Assigning the piece a value on new board
            selectedPiece.setPiecePosition( secondRow, secondCol );

            // Remaking the board
            chess.setBoardPiece(new Blank(chess, fRow, fCol), fRow, fCol);
            chess.setBoardPiece(selectedPiece, secondRow, secondCol);

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
     * This method will reset the board to the original board void @exception
     */
    public void reset() {

        // Recopy old board
        chess = new Chess( origBoard );

        // Redraw entire board
        chess.redrawBoard();

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

        return chess.getPieceOnBoard( row, col );

    }

    public boolean wonGame() {

        int count = 0;
        for ( int i = 0; i < rowLength; i++ ) {
            for ( int j = 0; j < colLength; j++ ) {
                if ( !chess.getPieceOnBoard( i, j ).isBlank() )
                    count++;

                if ( count > 1 )
                    return false;
            }
        }
        return true;
    }

    /**
     * This method searches for the next best move
     * 
     */
    public void nextBestMove() {

        try {
            // Setting the value of the chess to the current board for solver
            Solver<Chess> solver = new Solver<Chess>();
            ArrayList<Chess> solutions = solver.solve( chess );

            
            // Return the first step from solutions
            chess = new Chess( solutions.get( 1 ) );
            chess.redrawBoard();

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

}
