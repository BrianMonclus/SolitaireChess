package chessGame;
import java.util.ArrayList;
import java.util.HashSet;

/*
 * ${file_name}
 *
 * Version:
 * $$Id: Solver.java,v 1.3 2015/12/09 06:11:49 ju7847 Exp $$
 *
 * Revisions:
 * $$Log: Solver.java,v $
 * $Revision 1.3  2015/12/09 06:11:49  ju7847
 * $Finished implementing getNeighbors for all Pieces....
 * $
 * $Revision 1.2  2015/12/08 21:29:27  ju7847
 * $Implemented Pawn and some of Chess. Made getNeighbors got Pawn
 * $
 * $Revision 1.1  2015/12/06 22:40:53  ju7847
 * $Initial Commit with CVS bc GIT sucks.
 * $$
 */

/**
 * @author Jean Luis Urena ju7847
 *
 *         CS For Transfers Project3
 *
 *         This solver class contains method to solve Mobius puzzle.
 */

public class Solver<E> {
    /**
     * Solves a puzzle
     * 
     * @param puzzle
     *            - the puzzle to be solved
     * @return ArrayList holding solution
     */
    public ArrayList<E> solve( Puzzle<E> puzzle ) {
        HashSet<E> visited = new HashSet<E>();
        ArrayList<ArrayList<E>> queue = new ArrayList<ArrayList<E>>();
        ArrayList<E> startConfig = new ArrayList<E>();
        ArrayList<E> current = null;

        startConfig.add( puzzle.getStart() );
        boolean found = puzzle.isGoal( puzzle.getStart() );
        queue.add( startConfig );
        visited.add( puzzle.getStart() );

        while ( !queue.isEmpty() && !found ) {
            current = queue.remove( 0 );
            ArrayList<E> neighbors = puzzle
                    .getNeighbors( current.get( current.size() - 1 ) );

            for ( E neighbor : neighbors ) {
                if ( !visited.contains( neighbor ) ) {
                    ArrayList<E> nextConfig = new ArrayList<E>();
                    for ( E item : current ) {
                        nextConfig.add( item );
                    }
                    nextConfig.add( neighbor );
                    if ( puzzle.isGoal(
                            nextConfig.get( nextConfig.size() - 1 ) ) ) {
                        current = nextConfig;
                        found = true;
                        break;
                    } else {
                        queue.add( nextConfig );
                    }
                    visited.add( neighbor );
                }
            }
        }

        if ( found ) {
            return current;
        } else {
            return null;
        }
    }
}
