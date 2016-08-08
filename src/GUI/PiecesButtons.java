package GUI;
import java.awt.Font;

import javax.swing.JButton;

import chessPieces.Pieces;

/*
 * PiecesButtons.java
 *
 * Version:
 * $Id: PiecesButtons.java,v 1.3 2015/12/12 04:05:27 ju7847 Exp $
 *
 * Revisions:
 * $Log: PiecesButtons.java,v $
 * Revision 1.3  2015/12/12 04:05:27  ju7847
 * Fixed small display issue
 *
 * Revision 1.1  2015/12/11 21:25:18  ju7847
 * Almost done with GUI... i think
 *
 */

/**
 * @author Jean Luis Urena ID: ju7847
 *
 *         CS242
 */

@SuppressWarnings( "serial" )
public class PiecesButtons extends JButton {


    @SuppressWarnings( "unused" )
    private Pieces piece;
    private char character;

    public PiecesButtons( Pieces piece ) {
        this.piece = piece;
        character  = piece.getPieceChar();
        setSize( 100, 120 );
        setFont( new Font( "Arial", Font.PLAIN, 60 ) );
        // Insets ( top, left, bottom, right )
        setText( pieceIcon(this.character));

    }

    public char getCharacter(){
        return character;
    }
    
    public static String pieceIcon(char character) {
        String charGraphic = null;
        switch ( character ) {
        case 'B':
            charGraphic = "\u2657";
            break;
        case 'K':
            charGraphic = "\u2654";
            break;
        case 'N':
            charGraphic = "\u2658";
            break;
        case 'P':
            charGraphic = "\u2659";
            break;
        case 'Q':
            charGraphic = "\u2655";
            break;
        case 'R':
            charGraphic = "\u2656";
            break;
        case '.':
            charGraphic = "";
            break;

        }
        return charGraphic;

    }

}
