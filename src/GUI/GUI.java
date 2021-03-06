
package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import chessGame.ChessBoard;
import chessGame.ChessGame;
import chessPieces.Blank;

/*
 * GUI.java
 *
 * Version:
 * $Id: GUI.java,v 1.5 2015/12/12 04:42:12 ju7847 Exp $
 *
 * Revisions:
 * $Log: GUI.java,v $
 * Revision 1.5  2015/12/12 04:42:12  ju7847
 * Silly, forgot to toggle isSelected
 *
 * Revision 1.4  2015/12/12 04:24:49  ju7847
 * Fixed another small mistake
 *
 * Revision 1.3  2015/12/12 04:05:27  ju7847
 * Fixed small display issue
 *
 * Revision 1.2  2015/12/12 04:02:08  ju7847
 * Finished .. .debugging some more
 *
 * Revision 1.1  2015/12/11 21:25:19  ju7847
 * Almost done with GUI... i think
 *
 */

/**
 * @author Jean Luis Urena
 * @author ID: ju7847
 *
 *         CS242
 */

@SuppressWarnings( "serial" )
public class GUI extends JFrame implements Observer {

    PiecesButtons[][] currentBoard;
    private ChessBoard chessBoard;
    private ChessGame chessGame;
    private int cols;
    JLabel label;

    private int rows;

    public GUI( ChessGame chessGame ) {
        this.chessBoard = chessGame.getChessBoard();
        this.chessGame = chessGame;
        this.chessGame.addObserver( this );

        rows = chessGame.rowLength;
        cols = chessGame.colLength;

        label = new JLabel( "Moves: 0" );
        currentBoard = new PiecesButtons[rows][cols];

        populatePieceButtons();
        setsFrame();

        add( boardPanel( currentBoard ), BorderLayout.CENTER );
        add( menuPanel(), BorderLayout.SOUTH );
        add( labelPanel(), BorderLayout.NORTH );
        // add(instructionPanel(), BorderLayout.NORTH);

        pack();

    }

    /********************** PANELS **********************************/
    private JPanel boardPanel( PiecesButtons[][] buttons ) {
        JPanel boardPanel = new JPanel( new GridLayout( rows, cols ) );

        for ( int i = 0; i < buttons.length; i++ ) {
            for ( int j = 0; j < buttons[0].length; j++ ) {

                // Needed for inner class
                int row = i;
                int col = j;

                // If its a blank do not give it action Listener
                buttons[i][j].addActionListener( new ActionListener() {

                    @Override
                    public void actionPerformed( ActionEvent e ) {

                        if ( chessGame.getIsSelected() ) {
                            chessGame.movePiece( row, col );

                        } else {
                            chessGame.selectPiece( row, col );
                        }

                    }
                } );

                if ( i % 2 == 0 && j % 2 == 0 )
                    buttons[i][j].setBackground( Color.blue );
                if ( i % 2 != 0 && j % 2 != 0 )
                    buttons[i][j].setBackground( Color.blue );

                boardPanel.add( buttons[i][j] );

            }
        }
        return boardPanel;
    }

    /**
     * Method to disable buttons void @exception
     */
    private void disableButtons( boolean toggle ) {

        for ( int i = 0; i < rows; i++ ) {
            for ( int j = 0; j < cols; j++ ) {
                currentBoard[i][j].setEnabled( !toggle );
            }
        }

    }

    /***************** BUTTONS ***************************/

    private JButton instructions() {
        JButton instructions = new JButton( "Instructions" );

        instructions.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {

                JOptionPane.showMessageDialog( null,
                        "1: Move the chess pieces according to the movement rules (http://www.chessvariants.com/d.chess/chess.html)."
                                + "\n    Each move MUST result in a caputred piece. Think ahead and plan your moves! "
                                + "\n2: If you are left with two or more pieces on the board, reset the challenge and try again. "
                                + "\n3: When there is only one piece left on the board, YOU WIN!" );
            }
        } );

        return instructions;

    }

    private JPanel labelPanel() {
        JPanel labelPanel = new JPanel();

        labelPanel.add( label );
        labelPanel
                .setLayout( new BoxLayout( labelPanel, BoxLayout.LINE_AXIS ) );
        labelPanel.add( Box.createHorizontalGlue() );
        labelPanel.add( instructions(), FlowLayout.RIGHT );

        return labelPanel;
    }

    private JPanel menuPanel() {

        JButton undo = undo();
        JButton reset = reset();
        JButton nextMove = nextMove();
        JButton quit = quit();

        JPanel menuPanel = new JPanel();
        menuPanel.add( undo );
        menuPanel.add( reset );
        menuPanel.add( nextMove );
        menuPanel.add( quit );

        menuPanel.setLayout( new FlowLayout( FlowLayout.RIGHT ) );

        return menuPanel;

    }

    private JButton nextMove() {
        JButton nextMove = new JButton( "Next Move" );

        nextMove.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {

                chessGame.nextBestMove();

            }

        } );

        return nextMove;

    }

    /**
     * This method will populate array list containing button pieces
     * void @exception
     */
    private void populatePieceButtons() {

        for ( int i = 0; i < rows; i++ ) {
            for ( int j = 0; j < cols; j++ ) {

                if ( !chessBoard.getPieceOnBoard( i, j ).isBlank() )
                    currentBoard[i][j] = (new PiecesButtons(
                            chessBoard.getPieceOnBoard( i, j ) ));
                else {
                    currentBoard[i][j] = (new PiecesButtons(
                            new Blank( chessBoard, i, j ) ));
                }

            }
        }

    }

    private JButton quit() {
        JButton quit = new JButton( "Quit" );

        quit.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {

                System.exit( 0 );

            }
        } );

        return quit;

    }

    private JButton reset() {
        JButton reset = new JButton( "Reset" );

        reset.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {

                chessGame.reset();
                disableButtons( false );

            }

        } );

        return reset;

    }

    private JButton undo() {

        JButton undo = new JButton( "Undo" );

        undo.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {

                if ( chessGame.undo() )
                    disableButtons( false );
                else
                    changeLabel( "No moves to undo." );

            }

        } );

        return undo;

    }

    /**
     * Private void method that sets the frame
     * 
     * @return void
     */
    private void setsFrame() {
        setTitle( "Chess - Jean Luis Urena - ju7847" ); // Setting title of
                                                        // frame
        setLayout( new BorderLayout() );
        setSize( 500, 600 );
        setDefaultCloseOperation( EXIT_ON_CLOSE ); // Makes it close when you
                                                   // hit close
        setVisible( true );

    }

    private void changeLabel( String message ) {

        label.setText( "Moves: " + Integer.toString( chessGame.getMoveCount() )
                + "   " + message );
    }

    /**
     * Description: This method is notified and re-draws the board
     */
    @Override
    public void update( Observable o, Object arg ) {

        // Checking for invalid moves
        if ( chessGame.getIsInvalidMove() ) {
            label.setText(
                    "Moves: " + Integer.toString( chessGame.getMoveCount() )
                            + "   Invalid move. Try again" );
            return;
        }

        // Reprint the board
        label.setText(
                "Moves: " + Integer.toString( chessGame.getMoveCount() ) );

        for ( int i = 0; i < rows; i++ ) {
            for ( int j = 0; j < cols; j++ ) {
                currentBoard[i][j].setText(
                        PiecesButtons.pieceIcon( chessGame.getChessBoard()
                                .getPieceOnBoard( i, j ).getPieceChar() ) );

            }
        }

        // After game has won disable buttons to prevent null pointer exception
        if ( chessGame.wonGame() ) {
            changeLabel( "Congratulations! You won!!" );
            disableButtons( true );
        }

    }

    @SuppressWarnings( "unused" )
    public static void main( String[] args ) {
        ChessBoard chess = new ChessBoard( 2 );
        ChessGame game = new ChessGame( chess );

        GUI gui = new GUI( game );

    }
}
