package com.martinsundeqvist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PieceTableTest {

    @Test
    public void testAppendingToAddBuffer() {
        // Setup a piece table with an original string
        String originalString = "the quick brown fox\njumped over the lazy dog";
        PieceTable pieceTable = new PieceTable(originalString);

        // Append something to the add buffer, by means of the assoicated "piece"
        // we state it should be placed after the first line in the original string
        // and that it should be for 
        String addString = "went to the park and\n";
        int afterFirstLineIndex = originalString.indexOf('\n') + 1;
        pieceTable.addToAddBuffer(addString);
        pieceTable.addPiece(new Piece(0, addString.length(), Source.ADD), afterFirstLineIndex);

        assertEquals( "the quick brown fox\nwent to the park and\njumped over the lazy dog", pieceTable.toString());
    }

}
