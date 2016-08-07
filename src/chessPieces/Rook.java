package chessPieces;


import java.util.ArrayList;

import chessGame.ChessBoard;

/*
 * Rook.java
 *
 * Version:
 * $Id: Rook.java,v 1.5 2015/12/11 03:39:24 ju7847 Exp $
 *
 * Revisions:
 * $Log: Rook.java,v $
 * Revision 1.5  2015/12/11 03:39:24  ju7847
 * Finished model!!!! YAYYYYY
 *
 * Revision 1.4  2015/12/10 17:15:47  ju7847
 * Finished implementing neighbors for all pieces and getNeighbors, but found error where it mistakes one object piece with another
 *
 * Revision 1.3  2015/12/10 06:05:41  ju7847
 * Implemented getNeighbors... Kinda works
 *
 * Revision 1.2  2015/12/10 04:21:17  ju7847
 * Changed data structure to 2D array of chars instead of ArrayList<ArrayList...
 *
 * Revision 1.1  2015/12/09 06:11:49  ju7847
 * Finished implementing getNeighbors for all Pieces....
 *
 */

/**
 * @author Jean Luis Urena ID: ju7847
 *
 *         CS242
 */

public class Rook extends Pieces {

    private final static char PIECECHAR = 'R';

    public Rook( ChessBoard board, int rowLocation, int colLocation ) {
        super( board, PIECECHAR, rowLocation, colLocation );
    }

    /**
     * Returns array list of possible moves for Rook
     * 
     * @return ArrayList<int[][]> @exception
     */
    @Override
    public ArrayList<int[][]> getPieceNeighbors() {
        ArrayList<int[][]> solutions = new ArrayList<>();

        if ( isInBoard() ) {
            // column length pawn can travel
            int colLength = super.colLength -1;

            // row length pawn can travel
            int rowLength = super.rowLength -1;

            /* ROW NEIGHBORS UP */

            for ( int i = 1; i < rowLength; i++ ) {

                // Checking it doesn't go out of bounds
                if ( rowLocation - i >= 0 ) {
                    if ( isOccupied( rowLocation - i, colLocation ) ) {
                        int[][] pos = { { rowLocation - i, colLocation } };
                        solutions.add( pos );
                    }
                }

            }

            /* ROW NEIGHBORS DOWN */
            for ( int i = 1; i < rowLength; i++ ) {

                if ( rowLocation + i <= rowLength ) {
                    if ( isOccupied( rowLocation + i, colLocation ) ) {
                        int pos[][] = { { rowLocation + i, colLocation } };
                        solutions.add( pos );
                    }
                }
            }

            /* COLUMN NEIGHBORS RIGHT */
            for ( int i = 1; i < colLength; i++ ) {

                if ( colLocation + i <= colLength ) {
                    if ( isOccupied( rowLocation, colLocation + i ) ) {
                        int pos[][] = { { rowLocation, colLocation + i } };
                        solutions.add( pos );
                    }
                }
            }

            /* COLUMN NEIGHBORS LEFT */
            for ( int i = 1; i < colLength; i++ ) {

                if ( colLocation - i >= 0 ) {
                    if ( isOccupied( rowLocation, colLocation - i ) ) {
                        int pos[][] = { { rowLocation, colLocation - i } };
                        solutions.add( pos );
                    }
                }
            }
        }
        return solutions;
    }

}
