package chessPieces;


import java.util.ArrayList;

import chessGame.Chess;

/*
 * Bishop.java
 *
 * Version:
 * $Id: Bishop.java,v 1.5 2015/12/11 03:39:25 ju7847 Exp $
 *
 * Revisions:
 * $Log: Bishop.java,v $
 * Revision 1.5  2015/12/11 03:39:25  ju7847
 * Finished model!!!! YAYYYYY
 *
 * Revision 1.4  2015/12/10 17:15:48  ju7847
 * Finished implementing neighbors for all pieces and getNeighbors, but found error where it mistakes one object piece with another
 *
 * Revision 1.3  2015/12/10 06:05:42  ju7847
 * Implemented getNeighbors... Kinda works
 *
 * Revision 1.2  2015/12/10 04:21:18  ju7847
 * Changed data structure to 2D array of chars instead of ArrayList<ArrayList...
 *
 * Revision 1.1  2015/12/09 06:11:50  ju7847
 * Finished implementing getNeighbors for all Pieces....
 *
 */

/**
 * @author Jean Luis Urena ID: ju7847
 *
 *         CS242
 */

public class Bishop extends Pieces {

    private final static char PIECECHAR = 'B';

    public Bishop( Chess board, int rowLocation, int colLocation ) {
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
            int colLength = super.colLength-1;

            // row length pawn can travel
            int rowLength = super.rowLength -1;

            /* BACK DIAGANOL GOING UP */
            if(rowLocation > 0 && colLocation > 0){

                for ( int i = 1, j = 1; i <= rowLength 
                        && j <= colLength; i++, j++ ) {
                    
                    //If it doesn't go out of bounds keep going
                    if(rowLocation - i >= 0 && colLocation - j >= 0){
                        
                    if(isOccupied(rowLocation-i, colLocation -j)){
                        int [][] pos = {{rowLocation -i, colLocation -j}};
                        solutions.add( pos );
                    }
                    }

                }
            }
            

            /* BACK DIAGANOL DOWN */
            if(rowLocation < rowLength && colLocation < colLength ){
                
                for (int i = 1, j = 1; i <= rowLength && j <= colLength; i++, j++){
                    
                    //If it doesn't go out of bounds
                    if((rowLocation + i <= rowLength && colLocation + j <= colLength)) {
                        if (isOccupied(rowLocation +i, colLocation +j)){
                        int [][] pos = {{rowLocation +i, colLocation +j}};
                        solutions.add( pos );
                        }
                    }
                }
            }
            

            /* FRONT DIAGANOL GOING UP */
            if(rowLocation > 0 && colLocation < colLength){
                
                for (int i = 1, j = 1; i <= rowLength && j <= colLength; i++, j++){
                    if(rowLocation - i >= 0 && colLocation + j <= colLength){
                    if(isOccupied(rowLocation -i, colLocation +j)){
                        int[][] pos = {{rowLocation -i, colLocation +j}};
                        solutions.add( pos );
                    }
                    }
                }
            }

            /* BACK DIAGANOL GOING DOWN */
            if(rowLocation < rowLength && colLocation > 0){
                
                for (int i = 1, j = 1; i <= rowLength && j <= colLength; i++, j++){
                    
                    if(rowLocation + i <= rowLength && colLocation - j >= 0){
                    if(isOccupied(rowLocation+i,colLocation-j )){
                        int[][] pos = {{rowLocation +i, colLocation -j}};
                        solutions.add( pos );
                    }
                    }
                }
            }
            
        }

        return solutions;
    }

}
