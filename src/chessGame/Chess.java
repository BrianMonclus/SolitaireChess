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
import java.util.ArrayList;

import chessPieces.Bishop;
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

public class Chess implements Puzzle<char[][]>{

    private char[][] board; // board arraylist to hold
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
        board = new char[rows][cols];
        setBoard();

    }

    // Getter of board
    public char[][] getBoard() {
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
    public ArrayList<char[][]> getNeighbors( char[][] config ) {
        ArrayList<char[][]> solutions = new ArrayList<>();

        int rowLength = rows;
        int colLength = cols;

        for ( int i = 0; i < rowLength; i++ ) {
            for ( int j = 0; j < colLength; j++ ) {

                if ( config[i][j] != '.' ) {

                    Pieces piece = getPieceOnPos( config, i, j );
                    ArrayList<int[][]> neighbors = new ArrayList<>(
                            piece.getPieceNeighbors() );

                    for ( int[][] neighs : neighbors ) {

                        for ( int r = 0; r < neighs.length; r++ ) {

                            // rows will always be index 0
                            int row = neighs[r][0];
                            // columns will always be index 1
                            int col = neighs[r][1];

                            // make a copy of config to change
                            char[][] temp = new char[rowLength][colLength];

                            for ( int h = 0; h < rowLength; h++ ) {
                                for ( int d = 0; d < colLength; d++ ) {
                                    temp[h][d] = config[h][d];
                                }
                            }

                            // move the piece value
                            char pieceToMove = piece.getPieceChar();
                            temp[row][col] = pieceToMove;

                            // Changing the row/col of the new piece
                            piece.setPieceRow( row );
                            piece.setPieceColumn( col );

                            //Making old position a '.'
                            temp[i][j] = '.';

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
    public Pieces getPieceOnPos( char[][] board, int row, int col ) {

        if ( board[row][col] == 'B' )
            return new Bishop( this, row, col );
        else if ( board[row][col] == 'K' )
            return new King( this, row, col );
        else if ( board[row][col] == 'N' )
            return new Knight( this, row, col );
        else if ( board[row][col] == 'P' )
            return new Pawn( this, row, col );
        else if ( board[row][col] == 'Q' )
            return new Queen( this, row, col );
        else if ( board[row][col] == 'R' )
            return new Rook( this, row, col );
        else
            return null;

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
    public char[][] getStart() {
        return board;
    }

    /**
     * Method to determine whether is goal or not. true if so, false if not.
     * 
     * @return isGoal - a boolean determining if its the goal
     */
    @Override
    public boolean isGoal( char[][] config ) {
        int count = 0;
        for ( int i = 0; i < config.length; i++ ) {
            for ( int j = 0; j < config[0].length; j++ ) {
                if ( config[i][j] != '.' )
                    count++;

                if ( count > 1 )
                    return false;
            }
        }
        return true;
    }

    /**
     * This method will set the pieces in the board void
     * 
     * @exception IOException,
     *                in case BufferedReader fails, or cannot close
     */
    private void setBoard() {
        int read;
        ArrayList<Character> pieces;

        // repeating for as many rows
        for ( int i = 0; i < rows; i++ ) {

            // initializing pieces
            pieces = new ArrayList<>();

            // While it doesn't break line
            try {
                while ( (read = reader.read()) != -1 && read != '\n' ) {
                    // Skip spaces
                    if ( read != ' ' )
                        pieces.add( (char) read );

                }

                // add pieces array list to board
                for ( int m = 0; m < pieces.size(); m++ ) {
                    board[i][m] = pieces.get( m );
                }
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
    
    /**
     * Changes the board
     * 
     * @param newBoard - board to change to
     * @exception
     */
    public void setBoardValues(char[][] newBoard){

        for ( int i = 0; i < rows; i++ ) {
            for ( int j = 0; j < cols; j++ ) {
                board[i][j] = newBoard[i][j];
            }
        }
        
        
    }

}
