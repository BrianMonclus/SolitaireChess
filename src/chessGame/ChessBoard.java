
package chessGame;

import java.util.ArrayList; // ArrayList data structure
import chessPieces.*; // Chessboard pieces

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
     * Description: Constructor builds the board based on the difficulty passed.
     * 
     * @param difficulty
     *            - An integer representing difficulty level
     * 
     * @exception NumberFormatException
     *                - Catches non-numerical input errors.
     */
    public ChessBoard( int difficulty ) {

        try {

            this.difficulty = difficulty;
            buildBoard(); // Builds the board based on difficulty

        } catch ( NumberFormatException e ) {

            System.out
                    .println( "ERROR: Enter a number for difficulty level.\n" );
            e.printStackTrace();
        }

    }

    /**
     * Description: Copy constructor. Creates a deep copy of a Chess object.
     * 
     * @param chess
     *            - Chess object to copy
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
     * Description: This method will build the board based on the difficulty
     * number passed.
     * 
     */
    private void buildBoard() {

        switch ( difficulty ) {

        case 1:
            rows = 4;
            cols = 4;
            board = new Pieces[rows][cols];
            board[0][2] = new Knight( this, 0, 2 );
            board[2][1] = new Bishop( this, 2, 1 );
            board[3][2] = new Pawn( this, 3, 2 );

            fillBlanks();
            break;

        case 2:
            rows = 4;
            cols = 4;
            board = new Pieces[rows][cols];
            board[1][1] = new Bishop( this, 1, 1 );
            board[2][0] = new Rook( this, 2, 0 );
            board[2][1] = new Pawn( this, 2, 1 );
            board[3][3] = new Knight( this, 3, 3 );

            fillBlanks();
            break;

        }

    }

    private void fillBlanks() {

        for ( int i = 0; i < rows; i++ ) {
            for ( int j = 0; j < cols; j++ ) {
                if ( board[i][j] == null )
                    board[i][j] = new Blank( this, i, j );
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
     * Description: Method finds all possible moves "neighbors" of a chessboard
     * and returns it in an ArrayList.
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

        // Cycling through the board
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
     * Description: This method will return the Piece at specified row, col.
     * 
     * @param row
     *            - row location
     * @param col
     *            - int col location
     * @return Pieces - Object Pieces on specified row, col.
     * 
     * @exception -
     *                Throws exception if row and col are not numberical.
     *                Catches said exception.
     */
    public Pieces getPieceOnBoard( int row, int col ) {

        // Verifying row and col are numbers.
        try {

            if ( !(row == (int) row && col == (int) col) )
                throw new NumberFormatException(
                        "ERROR: row and col parameters must be numerical." );

        } catch ( NumberFormatException e ) {
            e.printStackTrace();
        } catch ( IndexOutOfBoundsException e ) {
            e.printStackTrace();
        }

        return board[row][col];

    }

    /**
     * Description: Getter of rows.
     * 
     * @return int - Number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Description: This method just returns the beginning puzzle. Itself.
     * 
     * @return ChessBoard - the begining chessboard
     */
    @Override
    public ChessBoard getStart() {
        return new ChessBoard( difficulty );
    }

    /**
     * Description: Method determines if game is won by counting the number of
     * pieces left on board. If there is more than one piece on the board left
     * false, else true.
     * 
     * @return boolean - a boolean determining if its the goal
     */
    @Override
    public boolean isGoal( ChessBoard chessBoard ) {

        int count = 0; // Count for pieces

        // Iterating over the board
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

    /**
     * Description: Creates a specific new Pieces object based on the character
     * piece representation, the chessboard it is in, the row and the column.
     * 
     * @param piece
     *            - A char representation of the Pieces object to create.
     * 
     * @param chess
     *            - The chessboard the Pieces object will be a part of.
     * 
     * @param row
     *            - The row location of the Pieces object.
     * 
     * @param col
     *            - The column location of the Pieces object.
     * 
     * @return Pieces - The new Pieces object created based on the parameters
     *         passed.
     * 
     * @exception Exception
     *                - Throws a new exception if illegal character piece
     *                representation is passed.
     */
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
            try {
                throw new Exception(
                        "ERROR: Invalid character piece representation: "
                                + piece );
            } catch ( Exception e ) {

                e.printStackTrace();
            }
            return null;

        }

    }

    /**
     * Description: Reassigns the Pieces to their correct index position on 2D
     * board Pieces array.
     */
    protected void redrawBoard() {

        // Copies the board to avoid accidental deletion of Pieces.
        ChessBoard tempBoard = new ChessBoard( this );
        for ( int i = 0; i < rows; i++ ) {
            for ( int j = 0; j < cols; j++ ) {

                // Redrawing the entire board
                Pieces piece = tempBoard.board[i][j];
                char pieceChar = piece.getPieceChar();
                int pos[] = piece.getPosition();
                int row = pos[0];
                int col = pos[1];
                // Creates new piece object with same characteristics on its
                // appropriate index
                board[row][col] = newPieceObj( pieceChar, this, row, col );

            }

        }

    }

    /**
     * Description: Removes a piece from the board by assigning it as a Blank
     * Pieces.
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
     * Description: Sets a board piece at a specific row and column.
     */
    protected void setBoardPiece( Pieces piece, int row, int col ) {

        board[row][col] = piece;

    }

}
