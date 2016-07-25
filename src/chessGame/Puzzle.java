package chessGame;
import java.util.ArrayList;

/*
 * ${file_name}
 *
 * Version:
 * $$Id: Puzzle.java,v 1.3 2015/12/12 04:02:09 ju7847 Exp $$
 *
 * Revisions:
 * $$Log: Puzzle.java,v $
 * $Revision 1.3  2015/12/12 04:02:09  ju7847
 * $Finished .. .debugging some more
 * $
 * $Revision 1.2  2015/12/08 21:29:25  ju7847
 * $Implemented Pawn and some of Chess. Made getNeighbors got Pawn
 * $
 * $Revision 1.1  2015/12/06 22:40:50  ju7847
 * $Initial Commit with CVS bc GIT sucks.
 * $$
 */

/**
 * @author Jean Luis Urena ju7847
 * @author Jake Madlem jxm9019 CS For Transfers Project3
 *
 *         An interface puzzle class that contains stubs for a puzzle.
 */

public interface Puzzle<E> {

    // Will return neighbors in a puzzle
    public ArrayList<E> getNeighbors( E config );

    // Getter for starting configuration
    public E getStart();

    // Getter for goal in Puzzle
    public boolean isGoal( E config );
}