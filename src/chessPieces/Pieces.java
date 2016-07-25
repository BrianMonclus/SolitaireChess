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

    public Pieces( Chess board, char pieceChar, int rowLocation, int colLocation ) {
        this.board = board;
        this.pieceChar = pieceChar;
        this.colLength = this.board.getCols();
        this.rowLength = this.board.getRows();
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

    
    public boolean isInBoard(){
        
        for (int i = 0; i < rowLength; i++){
            for (int j = 0; j < colLength; j++){
                if (board.getBoard()[i][j] == pieceChar)
                    return true;
            }
        }
        return false;
        
        
    }
    
    public boolean isOccupied( int row, int col ) {
        boolean occupied = true;

        if ( board.getBoard()[row][col] == '.' )
            occupied = false;

        return occupied;

    }

    // Setter of piece character
    public void setPieceChar( char piece ) {
        pieceChar = piece;
    }
    
    // Setter of piece col
    public void setPieceColumn(int colLocation){
        this.colLocation = colLocation;
    }
    
    //Setter of piece row
    public void setPieceRow(int rowLocation){
        this.rowLocation = rowLocation;
    }

}
