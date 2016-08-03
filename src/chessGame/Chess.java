
package chessGame;

/*
 * Chess.java
 *
 * Version:
 * $Id: Chess.java,v 1.10 2015/12/12 04:02:09 ju7847 Exp $
 *
 * Revisions:
 * $Log: Chess.java,v $
 * Revision 1.10  2015/12/12 04:02:09  ju7847
 * Finished .. .debugging some more
 *
 * Revision 1.9  2015/12/11 21:25:20  ju7847
 * Almost done with GUI... i think
 *
 * Revision 1.8  2015/12/11 03:39:24  ju7847
 * Finished model!!!! YAYYYYY
 *
 * Revision 1.7  2015/12/10 17:15:48  ju7847
 * Finished implementing neighbors for all pieces and getNeighbors, but found error where it mistakes one object piece with another
 *
 * Revision 1.6  2015/12/10 06:05:42  ju7847
 * Implemented getNeighbors... Kinda works
 *
 * Revision 1.5  2015/12/10 04:21:18  ju7847
 * Changed data structure to 2D array of chars instead of ArrayList<ArrayList...
 *
 * Revision 1.4  2015/12/09 06:11:50  ju7847
 * Finished implementing getNeighbors for all Pieces....
 *
 * Revision 1.3  2015/12/08 22:35:46  ju7847
 * Changed Pieces interface to Abstract class
 *
 * Revision 1.2  2015/12/08 21:29:27  ju7847
 * Implemented Pawn and some of Chess. Made getNeighbors got Pawn
 *
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.ArrayList;

import chessPieces.Bishop;
import chessPieces.Blank;
import chessPieces.King;
import chessPieces.Knight;
import chessPieces.Pawn;
import chessPieces.Pieces;
import chessPieces.Queen;
import chessPieces.Rook;

/**
 * @author Jean Luis Urena ID: ju7847
 *
 *         CS242
 */

public class Chess implements Puzzle<Chess> {

    private Pieces[][] board; // board arraylist to hold
                              // pieces
    private int cols; // int to hold number of columns
    private File file; // File object to hold file
    private BufferedReader reader; // Buffered reader to keep track of reading

    private int rows; // int to hold number of rows

    /**
     * Constructor builds the board
     * 
     * @param file
     */
    public Chess( File file ) {
        // Initializing fields
        this.file = file;
        // Setting rows and cols
        setDimensions();
        board = new Pieces[rows][cols];
        setBoard();

    }

    /**
     * Description: Copy constructor.
     * 
     * @param chess,
     *            Chess object to copy
     */
    public Chess( Chess chess ) {

        this.rows = chess.rows;
        this.cols = chess.cols;

        this.board = new Pieces[this.rows][this.cols];
        /* FILE and BufferedReader can be ignored */

        // Copying board
        for ( int i = 0; i < this.rows; i++ ) {
            for ( int j = 0; j < this.cols; j++ ) {

                char pieceChar = chess.getPieceOnBoard( i, j ).getPieceChar();

                this.board[i][j] = newPieceObj( pieceChar, this, i, j );

            }
        }

    }

    // Getter of board
    public Pieces[][] getBoard() {
        return board;
    }

    /**
     * THis method will set the board pieces
     * 
     * @return void
     * @exception IOException,
     *                incase BufferedReader fails or File could not be found
     */

    // Getter of columns length
    public int getCols() {
        return cols;
    }

    /**
     * 
     * @param config
     *            - a chess game board containing
     * @return getNeighbors - an ArrayList<char[][]> a list of neighbors of
     *         config
     */
    @Override
    public ArrayList<Chess> getNeighbors( Chess config ) {
        ArrayList<Chess> solutions = new ArrayList<>();

        int rowLength = config.rows; // Row length
        int colLength = config.cols; // Column length

        for ( int i = 0; i < rowLength; i++ ) {
            for ( int j = 0; j < colLength; j++ ) {

                // Get piece on config board
                Pieces piece = config.getPieceOnBoard( i, j );
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
                            Chess temp = new Chess( config );
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
    public Chess getStart() {
        return this;
    }

    /**
     * Method to determine whether is goal or not. true if so, false if not.
     * 
     * @return isGoal - a boolean determining if its the goal
     */
    @Override
    public boolean isGoal( Chess config ) {
        int count = 0;
        for ( int i = 0; i < config.rows; i++ ) {
            for ( int j = 0; j < config.cols; j++ ) {
                if ( !config.board[i][j].isBlank() )
                    count++;

                if ( count > 1 )
                    return false;
            }
        }
        return true;
    }

    public Pieces newPieceObj( char piece, Chess chess, int row, int col ) {

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

    protected void redrawBoard() {

        Chess tempBoard = new Chess( this );
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
     * This method will set the pieces in the board void
     * 
     * @exception IOException,
     *                in case BufferedReader fails, or cannot close
     */
    private void setBoard() {
        int read; /*
                   * var to check for empty lines Then casted into a char and
                   * added to board
                   */
        int col = 0;
        ArrayDeque<Character> charReadArr = new ArrayDeque<>(); // Pieces read
        ArrayDeque<Integer> colPosArr = new ArrayDeque<>(); // Piece column
                                                            // Position

        // repeating for as many ROWS
        for ( int row = 0; row < rows; row++ ) {

            // While it doesn't break line
            try {
                // Reading columns
                while ( (read = reader.read()) != -1 && read != '\n' ) {

                    // While it is not blank, add the position
                    // denoted by col and character read
                    if ( read != ' ' ) {
                        charReadArr.add( (char) read );
                        colPosArr.add( col );
                        col++;

                    }

                }

                // add pieces array list to board
                while ( !colPosArr.isEmpty() ) {

                    // Re-assigning piece column position
                    col = colPosArr.remove();
                    char charRead = charReadArr.remove();

                    board[row][col] = newPieceObj( charRead, this, row, col );

                }

                col = 0; // Resetting counter.

            } catch ( IOException e ) {
                System.out.println( file.getName() + " not found." );
                System.exit( 0 );
            }
        }
        try {
            reader.close();
        } catch ( IOException e ) {
            System.out.println( file.getName() + " not found." );
            System.exit( 0 );
        }

    }

    /**
     * This method reads the first line of file to get row and col sizes
     * 
     * @exception IOException
     *                - incase file cannot be found
     */
    private void setDimensions() {
        // Variable to hold read characters
        int read;
        ArrayList<Character> dimensions = new ArrayList<>();
        try {

            // Initializing buffered reader
            reader = new BufferedReader( new InputStreamReader(
                    new FileInputStream( file ), Charset.forName( "UTF-8" ) ) );

            // While there exists a character and it doesn't break line
            while ( (read = reader.read()) != -1 && read != '\n' ) {

                // Skip spaces
                if ( read != (' ') )
                    dimensions.add( (char) read );

            }

        } catch ( IOException e ) {
            System.out.println( file.getName() + " not found." );
            System.exit( 0 );
        }

        rows = Character.getNumericValue( dimensions.get( 0 ) );
        cols = Character.getNumericValue( dimensions.get( 1 ) );

    }

}
