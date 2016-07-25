package chessPieces;


import java.util.ArrayList;

import chessGame.Chess;

/*
 * Pawn.java
 *
 * Version:
 * $Id: Pawn.java,v 1.7 2015/12/11 03:39:23 ju7847 Exp $
 *
 * Revisions:
 * $Log: Pawn.java,v $
 * Revision 1.7  2015/12/11 03:39:23  ju7847
 * Finished model!!!! YAYYYYY
 *
 * Revision 1.6  2015/12/10 17:15:47  ju7847
 * Finished implementing neighbors for all pieces and getNeighbors, but found error where it mistakes one object piece with another
 *
 * Revision 1.5  2015/12/10 04:21:16  ju7847
 * Changed data structure to 2D array of chars instead of ArrayList<ArrayList...
 *
 * Revision 1.4  2015/12/09 06:11:48  ju7847
 * Finished implementing getNeighbors for all Pieces....
 *
 * Revision 1.3  2015/12/08 22:35:44  ju7847
 * Changed Pieces interface to Abstract class
 *
 * Revision 1.2  2015/12/08 21:44:39  ju7847
 * Fixed Pawn get neighbors
 *
 * Revision 1.1  2015/12/08 21:29:24  ju7847
 * Implemented Pawn and some of Chess. Made getNeighbors got Pawn
 *
 */

/**
 * @author Jean Luis Urena ID: ju7847
 *
 *         CS242
 */

public class Pawn extends Pieces {

    private final static char PIECECHAR = 'P';

    public Pawn( Chess board, int rowLocation, int colLocation ) {
        super( board, PIECECHAR, rowLocation, colLocation );

    }

    @Override
    public ArrayList<int[][]> getPieceNeighbors() {
        ArrayList<int[][]> neighbors = new ArrayList<>();
        int colLength = super.colLength -1;

        if ( isInBoard() ) {
            // Making sure it can only go up
            if ( rowLocation > 0 ) {

                // If its not on the left most
                if ( colLocation - 1 >= 0 ) {
                    if ( isOccupied( rowLocation - 1, colLocation - 1 ) ) {
                        int[][] neighbor1 = {
                                { rowLocation - 1, colLocation - 1 } };
                        neighbors.add( neighbor1 );
                    }
                }
                // If its not right most
                if ( colLocation + 1 <= colLength ) {

                    if ( isOccupied( rowLocation - 1, colLocation + 1 ) ) {
                        int[][] neighbor2 = {
                                { rowLocation - 1, colLocation + 1 } };
                        neighbors.add( neighbor2 );
                    }
                }
            }
        }

        return neighbors;

    }

    // row = index 0
    // col = index 1

}
