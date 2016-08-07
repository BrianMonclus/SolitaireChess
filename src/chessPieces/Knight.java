package chessPieces;


import java.util.ArrayList;

import chessGame.ChessBoard;

/*
 * Knight.java
 *
 * Version:
 * $Id: Knight.java,v 1.4 2015/12/11 03:39:23 ju7847 Exp $
 *
 * Revisions:
 * $Log: Knight.java,v $
 * Revision 1.4  2015/12/11 03:39:23  ju7847
 * Finished model!!!! YAYYYYY
 *
 * Revision 1.3  2015/12/10 17:15:46  ju7847
 * Finished implementing neighbors for all pieces and getNeighbors, but found error where it mistakes one object piece with another
 *
 * Revision 1.2  2015/12/10 04:21:16  ju7847
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

public class Knight extends Pieces {

    private final static char PIECECHAR = 'N';

    public Knight( ChessBoard board, int rowLocation, int colLocation ) {
        super( board, PIECECHAR, rowLocation, colLocation );
    }

    @Override
    public ArrayList<int[][]> getPieceNeighbors() {

        ArrayList<int[][]> solutions = new ArrayList<>();

        if ( isInBoard() ) {
            
            int rowLength = super.rowLength -1;
            int colLength = super.colLength -1;


            if ( rowLocation - 1 >= 0 && colLocation + 2 <= colLength ) {
                if ( isOccupied( rowLocation - 1, colLocation + 2 ) ) {
                    
                    int[][] pos = { { rowLocation - 1, colLocation + 2 } };
                    solutions.add( pos );
                }
            }

            if ( rowLocation + 1 <= rowLength
                    && colLocation + 2 <= colLength ) {
                if ( isOccupied( rowLocation + 1, colLocation + 2 ) ) {
                    int[][] pos = { { rowLocation + 1, colLocation + 2 } };
                    solutions.add( pos );
                }
            }

            if ( rowLocation - 2 >= 0 && colLocation - 1 >= 0 ) {
                if ( isOccupied( rowLocation - 2, colLocation - 1 ) ) {
                    int[][] pos = { { rowLocation - 2, colLocation - 1 } };
                    solutions.add( pos );
                }
            }

            if ( rowLocation - 2 >= 0 && colLocation + 1 <= colLength ) {

                if ( isOccupied( rowLocation - 2, colLocation + 1 ) ) {
                    int[][] pos = { { rowLocation - 2, colLocation + 1 } };
                    solutions.add( pos );
                }
            }

            if ( rowLocation - 1 > 0 && colLocation - 2 >= 0 ) {

                if ( isOccupied( rowLocation - 1, colLocation - 2 ) ) {
                    int[][] pos = { { rowLocation - 1, colLocation - 2 } };
                    solutions.add( pos );
                }
            }

            if ( rowLocation + 1 <= rowLength && colLocation - 2 >= 0 ) {

                if ( isOccupied( rowLocation + 1, colLocation - 2 ) ) {
                    int[][] pos = { { rowLocation + 1, colLocation - 2 } };
                    solutions.add( pos );
                }
            }

            if ( rowLocation + 2 <= rowLength && colLocation - 1 >= 0 ) {

                if ( isOccupied( rowLocation + 2, colLocation - 1 ) ) {
                    int[][] pos = { { rowLocation + 2, colLocation - 1 } };
                    solutions.add( pos );
                }
            }

            if ( rowLocation + 2 <= rowLength && colLocation + 1 < colLength ) {

                if ( isOccupied( rowLocation + 2, colLocation + 1 ) ) {
                    int[][] pos = { { rowLocation + 2, colLocation + 1 } };
                    solutions.add( pos );
                }
            }
        }

        return solutions;
    }

}
