
package chessGame;

import java.util.ArrayList;
import chessPieces.*;

/**
 * 
 * Description: This class itself is the board. It builds the board based on the
 * given difficulty, and has all methods needed to interact with the board
 * itself.
 * 
 * @author Jean Luis Urena ID: ju7847
 *
 */

public class ChessBoard implements Puzzle<ChessBoard> {

    private int difficulty; // Numbered level of difficulty
    private Pieces[][] board; // Actual board
    protected int cols; // int to hold number of columns
    protected int rows; // int to hold number of rows

    /**
     * Constructor builds the board
     * 
     * @param file
     */
    public ChessBoard( int difficulty ) {

        this.difficulty = difficulty;
        buildBoard(); // Builds the board based on difficulty

    }

    /**
     * Description: Copy constructor. Creates a deep copy of a Chess object.
     * 
     * @param chess,
     *            Chess object to copy
     */
    public ChessBoard( ChessBoard chessBoard ) {

        // Copying all fields
        this.rows = chessBoard.rows;
        this.cols = chessBoard.cols;
        this.difficulty = chessBoard.difficulty;
        this.board = new Pieces[this.rows][this.cols];

        // Copying board
        for ( int i = 0; i < this.rows; i++ ) {
            for ( int j = 0; j < this.cols; j++ ) {

                // Retrieving Piece character identification.
                char pieceChar = chessBoard.getPieceOnBoard( i, j )
                        .getPieceChar();

                // Creating new Piece object based on Piece character
                // identification
                this.board[i][j] = newPieceObj( pieceChar, this, i, j );

            }
        }

    }

    /**
     * This method will build the board based on the difficulty number passed.
     * 
     * TODO catch illegalargument and non-number error
     */
    private void buildBoard() {

        switch ( difficulty ) {

        case 1:
            rows = 4;
            cols = 4;
            board = new Pieces [rows][cols];
            board[0][2] = new Knight( this, 0, 2 );
            board[2][1] = new Bishop( this, 2, 1 );
            board[3][2] = new Pawn( this, 3, 2 );

            for ( int i = 0; i < rows; i++ ) {
                for ( int j = 0; j < cols; j++ ) {
                    if ( board[i][j] == null )
                        board[i][j] = new Blank( this, i, j );
                }
            }

        }

    }

    /**
     * Description: Getter of columns.
     * 
     * @return int - number of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * Description: Getter of difficulty.
     * 
     * @return int - Level of difficulty
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * 
     * @param config
     *            - a chess game board containing
     * @return getNeighbors - an ArrayList<char[][]> a list of neighbors of
     *         config
     */
    @Override
    public ArrayList<ChessBoard> getNeighbors( ChessBoard chessBoard ) {
        ArrayList<ChessBoard> solutions = new ArrayList<>();

        int rowLength = chessBoard.rows; // Row length
        int colLength = chessBoard.cols; // Column length

        for ( int i = 0; i < rowLength; i++ ) {
            for ( int j = 0; j < colLength; j++ ) {

                // Get piece on config board
                Pieces piece = chessBoard.getPieceOnBoard( i, j );
                // If the cell is not empty
                if ( !piece.isBlank() ) {

                    // Get neighbors of config board in form of
                    // position integer values
                    ArrayList<int[][]> neighbors = new ArrayList<>(
                            piece.getPieceNeighbors() );

                    // For each neighbor in config board
                    for ( int[][] neighs : neighbors ) {

                        for ( int r = 0; r < neighs.length; r++ ) {

                            // rows will always be index 0
                            int rowNeighbor = neighs[r][0];
                            // columns will always be index 1
                            int colNeighbor = neighs[r][1];

                            // make a copy (copy constructor) of config to
                            // change and piece
                            ChessBoard temp = new ChessBoard( chessBoard );
                            temp.board[rowNeighbor][colNeighbor] = piece;

                            // Move the piece to the new position
                            piece.setPiecePosition( rowNeighbor, colNeighbor );

                            // Set old position to be blank
                            temp.board[i][j] = new Blank( temp, i, j );

                            solutions.add( temp );

                        }

                    }
                }

            }
        }
        return solutions;
    }

    /**
     * This method will return and assign and object Pieces to the board
     * 
     * @param board
     *            - char 2d array of boards
     * @param row
     *            - row location
     * @param col
     *            - int col location
     * @return Pieces - Object Pieces that is on board
     */
    public Pieces getPieceOnBoard( int row, int col ) {

        return board[row][col];

    }

    // Getter of rows length
    public int getRows() {
        return rows;
    }

    /**
     * This method just returns a beginning chess puzzle
     * 
     * @return char[][] - the begining config
     */
    @Override
    public ChessBoard getStart() {
        return this;
    }

    /**
     * Method to determine whether is goal or not. true if so, false if not.
     * 
     * @return isGoal - a boolean determining if its the goal
     */
    @Override
    public boolean isGoal( ChessBoard chessBoard ) {
        int count = 0;
        for ( int i = 0; i < chessBoard.rows; i++ ) {
            for ( int j = 0; j < chessBoard.cols; j++ ) {
                if ( !chessBoard.board[i][j].isBlank() )
                    count++;

                if ( count > 1 )
                    return false;
            }
        }
        return true;
    }

    public Pieces newPieceObj( char piece, ChessBoard chess, int row,
            int col ) {

        switch ( piece ) {
        case 'B':
            return new Bishop( chess, row, col );

        case 'K':
            return new King( this, row, col );

        case 'N':
            return new Knight( this, row, col );

        case 'P':
            return new Pawn( this, row, col );

        case 'Q':
            return new Queen( this, row, col );

        case 'R':
            return new Rook( this, row, col );

        case '.':
            return new Blank( this, row, col );

        default:
            System.out
                    .println( "ERROR on newPieceObj line 378, creating Piece" );
            return null;

        }

    }

    protected void redrawBoard() {

        ChessBoard tempBoard = new ChessBoard( this );
        for ( int i = 0; i < rows; i++ ) {
            for ( int j = 0; j < cols; j++ ) {

                // Redrawing the entire board
                Pieces piece = tempBoard.board[i][j];
                char pieceChar = piece.getPieceChar();
                int pos[] = piece.getPosition();
                int row = pos[0];
                int col = pos[1];
                board[row][col] = newPieceObj( pieceChar, this, row, col );

            }

        }

    }

    /**
     * Description: Removes a piece from the board by assigning it to null.
     * 
     * @param row
     *            - row of piece
     * @param col
     *            - column of piece
     */
    public void removePiece( int row, int col ) {

        board[row][col] = new Blank( this, row, col );

    }

    /**
     * Remakes the board array of pieces void @exception
     */
    protected void setBoardPiece( Pieces piece, int row, int col ) {

        board[row][col] = piece;

    }

}
