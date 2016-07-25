package chessPieces;


import java.util.ArrayList;

import chessGame.Chess;

/*
 * King.java
 *
 * Version:
 * $Id: King.java,v 1.6 2015/12/11 03:39:22 ju7847 Exp $
 *
 * Revisions:
 * $Log: King.java,v $
 * Revision 1.6  2015/12/11 03:39:22  ju7847
 * Finished model!!!! YAYYYYY
 *
 * Revision 1.5  2015/12/10 17:15:46  ju7847
 * Finished implementing neighbors for all pieces and getNeighbors, but found error where it mistakes one object piece with another
 *
 * Revision 1.4  2015/12/10 06:05:40  ju7847
 * Implemented getNeighbors... Kinda works
 *
 * Revision 1.3  2015/12/10 04:21:15  ju7847
 * Changed data structure to 2D array of chars instead of ArrayList<ArrayList...
 *
 * Revision 1.2  2015/12/09 06:11:47  ju7847
 * Finished implementing getNeighbors for all Pieces....
 *
 */

/**
 * @author Jean Luis Urena ID: ju7847
 *
 *         CS242
 */

public class King extends Pieces {

    private final static char PIECECHAR = 'K';

    public King( Chess board, int rowLocation, int colLocation ) {
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

            /* FRONT DIAGANOL GOING UP */

            {
                if ( (rowLocation > 0 && colLocation > 0)
                        && isOccupied( rowLocation - 1, colLocation - 1 ) ) {
                    int[][] pos = { { rowLocation - 1, colLocation - 1 } };
                    solutions.add( pos );
                }
            }

            /* FRONT DIAGANOL DOWN */
            {
                if ( (rowLocation < rowLength && colLocation < colLength)
                        && isOccupied( rowLocation + 1, colLocation + 1 ) ) {
                    int pos[][] = { { rowLocation + 1, colLocation + 1 } };
                    solutions.add( pos );
                }
            }

            /* BACK DIAGANOL GOING UP */
            {
                if ( (rowLocation > 0 && colLocation < colLength)
                        && isOccupied( rowLocation - 1, colLocation + 1 ) ) {
                    int pos[][] = { { rowLocation - 1, colLocation + 1 } };
                    solutions.add( pos );
                }
            }

            /* BACK DIAGANOL GOING DOWN */
            {
                if ( (rowLocation < rowLength && colLocation > 0)
                        && isOccupied( rowLocation + 1, colLocation - 1 ) ) {
                    int pos[][] = { { rowLocation + 1, colLocation - 1 } };
                    solutions.add( pos );
                }
            }

            /* ROWS NEIGHBORS DOWN */
            if ( rowLocation < rowLength
                    && isOccupied( rowLocation + 1, colLocation ) ) {
                int[][] solutionArr = { { rowLocation + 1, colLocation } };

                solutions.add( solutionArr );
            }

            /* COLUMN NEIGHBORS RIGHT */
            if ( colLocation < colLength
                    && isOccupied( rowLocation, colLocation + 1 ) ) {
                int[][] solutionArr = { { rowLocation, colLocation + 1 } };
                solutions.add( solutionArr );
            }

            /* ROWS NEIGHBORS UP */
            if ( rowLocation > 0
                    && isOccupied( rowLocation - 1, colLocation ) ) {
                int[][] solutionArr = { { rowLocation - 1, colLocation } };
                solutions.add( solutionArr );
            }

            /* COLUMN NEIGHBORS LEFT */
            if ( colLocation > 0
                    && isOccupied( rowLocation, colLocation - 1 ) ) {
                int[][] solutionArr = { { rowLocation, colLocation - 1 } };

                if ( isOccupied( rowLocation, colLocation - 1 ) )
                    solutions.add( solutionArr );
            }
        }

        return solutions;
    }

}
