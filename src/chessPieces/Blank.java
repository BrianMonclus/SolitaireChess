package chessPieces;


import java.util.ArrayList;

import chessGame.ChessBoard;

/*
 * Blank.java
 *
 * Version:
 * $Id: Blank.java,v 1.1 2015/12/11 21:25:19 ju7847 Exp $
 *
 * Revisions:
 * $Log: Blank.java,v $
 * Revision 1.1  2015/12/11 21:25:19  ju7847
 * Almost done with GUI... i think
 *
 */

/**
 * @author Jean Luis Urena
 * ID: ju7847
 *
 * CS242 
 */

public class Blank extends Pieces {
    
    private static final char PIECECHAR = '.';
    
    public Blank( ChessBoard board, int rowLocation, int colLocation ) {
        super( board, PIECECHAR, rowLocation, colLocation );
    }

    @Override
    public ArrayList<int[][]> getPieceNeighbors() {

        return null;
    }

}
