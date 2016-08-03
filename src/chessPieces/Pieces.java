
package chessPieces;

import java.util.ArrayList;

import chessGame.Chess;

/*
 * Pieces.java
 *
 * Version:
 * $Id: Pieces.java,v 1.7 2015/12/12 04:02:08 ju7847 Exp $
 *
 * Revisions:
 * $Log: Pieces.java,v $
 * Revision 1.7  2015/12/12 04:02:08  ju7847
 * Finished .. .debugging some more
 *
 * Revision 1.6  2015/12/11 03:39:22  ju7847
 * Finished model!!!! YAYYYYY
 *
 * Revision 1.5  2015/12/10 06:05:39  ju7847
 * Implemented getNeighbors... Kinda works
 *
 * Revision 1.4  2015/12/10 04:21:15  ju7847
 * Changed data structure to 2D array of chars instead of ArrayList<ArrayList...
 *
 * Revision 1.3  2015/12/09 06:11:47  ju7847
 * Finished implementing getNeighbors for all Pieces....
 *
 * Revision 1.2  2015/12/08 22:35:42  ju7847
 * Changed Pieces interface to Abstract class
 *
 * Revision 1.1  2015/12/08 21:29:23  ju7847
 * Implemented Pawn and some of Chess. Made getNeighbors got Pawn
 *
 */

/**
 * @author Jean Luis Urena ID: ju7847
 *
 *         CS242
 */

public abstract class Pieces {

    protected Chess board;
    protected int colLength;
    protected int colLocation;
    protected char pieceChar;
    protected int rowLength;
    protected int rowLocation;

    public Pieces( Chess board, char pieceChar, int rowLocation,
            int colLocation ) {
        this.board = board;
        this.pieceChar = pieceChar;
        this.colLength = board.getCols();
        this.rowLength = board.getRows();
        this.rowLocation = rowLocation;
        this.colLocation = colLocation;

    }

    // Getter of char piece
    public char getPieceChar() {
        return pieceChar;
    }

    public abstract ArrayList<int[][]> getPieceNeighbors();

    /**
     * This will return an array containing row and column
     * 
     * @return int[] position of piece @exception
     */
    // row = index 0
    // col = index 1
    public int[] getPosition() {
        int[] pos = { rowLocation, colLocation };

        return pos;

    }

    public boolean isInBoard() {

        for ( int i = 0; i < rowLength; i++ ) {
            for ( int j = 0; j < colLength; j++ ) {
                if ( board.getPieceOnBoard( i, j ).pieceChar == pieceChar )
                    return true;
            }
        }
        return false;

    }

    public boolean isOccupied( int row, int col ) {
        boolean occupied = true;

        if ( board.getPieceOnBoard( row, col ).isBlank() )
            occupied = false;

        return occupied;

    }

    public boolean isBlank() {

        return (pieceChar == '.') ? true : false;
    }

    public boolean equals( Pieces piece ) {

        if ( getPosition().equals( piece.getPosition() )
                && pieceChar == piece.pieceChar )
            return true;
        else
            return false;

    }

    // Setter of piece character
    public void setPieceChar( char piece ) {
        pieceChar = piece;
    }
    
    public void setPiecePosition( int row, int col) {
        
        this.rowLocation = row;
        this.colLocation = col;
        
    }


}
