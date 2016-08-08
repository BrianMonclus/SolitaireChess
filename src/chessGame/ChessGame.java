
package chessGame;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Stack;

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

    // Default values are false.
    private boolean isSelected = false;
    private boolean invalidMove = false;
    private boolean noNextMove = false;

    private ChessBoard chessBoard; // Reference to the chessboard
    private Stack<ChessBoard> undo; // Stack for move undo
    public int colLength; // Column length for easier access
    private int moveCount; // Move count
    private int[] pieceHeld; // This will hold row and col location of piece
                             // held
    public int rowLength; // Row length for easier access

    /**
     * Constructor takes a chessboard, references it on a field value,
     * references row length, col length, creates an array for pieceHeld of [2],
     * instantiates a Stack of moves and pushes the initial board.
     * 
     * @param chessBoard
     *            - A ChessBoard to play in.
     */
    public ChessGame( ChessBoard chessBoard ) {
        this.chessBoard = chessBoard; // Reference

        undo = new Stack<>();
        moveCount = 0; // Initializing
        pieceHeld = new int[2]; // Will get value in methods
        rowLength = this.chessBoard.getRows(); // Referencing
        colLength = this.chessBoard.getCols(); // Referencing

    }

    // Getter of chess
    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    // Getter of invalidMove field
    public boolean getIsInvalidMove() {
        return invalidMove;
    }

    // Getter of isSelected field
    public boolean getIsSelected() {
        return isSelected;
    }

    // Getter of move count
    public int getMoveCount() {
        return moveCount;
    }

    // Getter of noNextMove field
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
            // Getting all solutions
            ArrayList<int[][]> solutions = piece.getPieceNeighbors();

            // Iterating through all possible solutions
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
     * change the field board. Essentially make a move.
     * 
     * @param secondRow
     *            - index row of piece to move to
     * @param secondCol
     *            - index col of piece to move to
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

            // Pushing old chessboard into undo move stack.
            undo.push( new ChessBoard( chessBoard ) );

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

        // Setting the value of the chess to the current board for solver
        Solver<ChessBoard> solver = new Solver<ChessBoard>();
        ArrayList<ChessBoard> solutions = solver.solve( chessBoard );

        // If solution is null, undo move is the best solution
        if ( solutions == null ) {
            undo();
            return;
        }

        // Return the first step from solutions
        chessBoard = new ChessBoard( solutions.get( 1 ) );
        chessBoard.redrawBoard();

        // Pushing new board into undo stack
        undo.push( chessBoard );

        // incrementing count
        moveCount++;

        setChanged();
        notifyObservers();

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
        undo.clear(); // Clearing undo move
        moveCount = 0;
        invalidMove = false;
        isSelected = false;
        // Notify observers of change
        setChanged();
        notifyObservers();

    }

    /**
     * Description: Undo's a move. Decrements move count as well. Returns true
     * if move can be undone otherwise false.
     * 
     * @return boolean - True if move can be undone, otherwise false.
     */
    public boolean undo() {

        // Check if undo stack is empty.
        // Return false if it is.
        if ( undo.isEmpty() )
            return false;

        // Decrement move count
        if ( moveCount > 0 )
            moveCount--;

        // Resetting values
        invalidMove = false;
        isSelected = false;

        // Then copy it into chessBoard.
        // Finally redraw the board
        ChessBoard undoBoard = undo.pop();
        chessBoard = new ChessBoard( undoBoard );
        chessBoard.redrawBoard();

        setChanged();
        notifyObservers();
        return true;

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
     * only one more piece exists. Calls isGoal.
     * 
     * @return boolean - True if won, false otherwise.
     */
    public boolean wonGame() {

        return chessBoard.isGoal( chessBoard );
    }

}
