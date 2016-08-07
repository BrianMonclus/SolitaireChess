package chessPieces;


import java.util.ArrayList;

import chessGame.ChessBoard;

/*
 * Queen.java
 *
 * Version:
 * $Id: Queen.java,v 1.5 2015/12/11 03:39:24 ju7847 Exp $
 *
 * Revisions:
 * $Log: Queen.java,v $
 * Revision 1.5  2015/12/11 03:39:24  ju7847
 * Finished model!!!! YAYYYYY
 *
 * Revision 1.4  2015/12/10 17:15:47  ju7847
 * Finished implementing neighbors for all pieces and getNeighbors, but found error where it mistakes one object piece with another
 *
 * Revision 1.3  2015/12/10 06:05:40  ju7847
 * Implemented getNeighbors... Kinda works
 *
 * Revision 1.2  2015/12/10 04:21:17  ju7847
 * Changed data structure to 2D array of chars instead of ArrayList<ArrayList...
 *
 * Revision 1.1  2015/12/09 06:11:48  ju7847
 * Finished implementing getNeighbors for all Pieces....
 *
 */

/**
 * @author Jean Luis Urena ID: ju7847
 *
 *         CS242
 */

public class Queen extends Pieces {

    private static final char CHARPIECE = 'Q';

    public Queen( ChessBoard board, int rowLocation, int colLocation ) {
        super( board, CHARPIECE, rowLocation, colLocation );
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
            int rowLength = super.rowLength - 1;

            /* FRONT DIAGANOL GOING UP */
            if ( rowLocation > 0 && colLocation > 0 ) {

                for ( int i = 1, j = 1; i <= rowLength
                        && j <= colLength; i++, j++ ) {

                    // If it doesn't go out of bounds keep going
                    if ( rowLocation - i > 0 && colLocation - j > 0 ) {

                        if ( isOccupied( rowLocation - i, colLocation - j ) ) {
                            int[][] pos = {
                                    { rowLocation - i, colLocation - j } };
                            solutions.add( pos );
                        }
                    }

                }
            }

            /* FRONT DIAGANOL DOWN */
            if ( rowLocation < rowLength && colLocation < colLength ) {

                for ( int i = 1, j = 1; i <= rowLength
                        && j <= colLength; i++, j++ ) {

                    // If it doesn't go out of bounds
                    if ( (rowLocation + i < rowLocation
                            && colLocation + j < colLength) ) {
                        if ( isOccupied( rowLocation + i, colLocation + j ) ) {
                            int[][] pos = {
                                    { rowLocation + i, colLocation + j } };
                            solutions.add( pos );
                        }
                    }
                }
            }

            /* BACK DIAGANOL GOING UP */
            if ( rowLocation > 0 && colLocation < colLength ) {

                for ( int i = 1, j = 1; i <= rowLength
                        && j <= colLength; i++, j++ ) {
                    if ( rowLocation - i > 0 && colLocation + j < colLength ) {
                        if ( isOccupied( rowLocation - i, colLocation + j ) ) {
                            int[][] pos = {
                                    { rowLocation - i, colLocation + j } };
                            solutions.add( pos );
                        }
                    }
                }
            }

            /* BACK DIAGANOL GOING DOWN */
            if ( rowLocation < rowLength && colLocation > 0 ) {

                for ( int i = 1, j = 1; i <= rowLength
                        && j <= colLength; i++, j++ ) {

                    if ( rowLocation + i < rowLength && colLocation - j > 0 ) {
                        if ( isOccupied( rowLocation + i, colLocation - j ) ) {
                            int[][] pos = {
                                    { rowLocation + i, colLocation - j } };
                            solutions.add( pos );
                        }
                    }
                }
            }

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
