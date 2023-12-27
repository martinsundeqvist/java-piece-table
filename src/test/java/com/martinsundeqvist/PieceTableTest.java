package com.martinsundeqvist;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PieceTableTest {

    @Test
    public void testInsertWithEmptyOriginal() {
        // Initialize PieceTable with an empty string
        PieceTable pieceTable = new PieceTable("");

        // Append a new string to the add buffer and add a piece for it
        String addString = "New content";
        pieceTable.addToAddBuffer(addString);
        pieceTable.addPiece(new Piece(0, addString.length(), Source.ADD), 0);

        // Assert the content of the PieceTable matches the added string
        assertEquals("New content", pieceTable.toString());
    }

    @Test
    public void addPieceAtBeginning() {
        String originalString = "Start";
        PieceTable pieceTable = new PieceTable(originalString);
        String insertString = "Before ";
        pieceTable.addToAddBuffer(insertString);
        pieceTable.addPiece(new Piece(0, insertString.length(), Source.ADD), 0);

        assertEquals("Before Start", pieceTable.toString());
    }

    @Test
    public void addPieceAtEnd() {
        // Setup the initial state of the PieceTable with some original content
        String originalString = "Original content ";
        PieceTable pieceTable = new PieceTable(originalString);

        // Append a new string to the add buffer and then add a new piece at the end
        String appendString = "appended.";
        pieceTable.addToAddBuffer(appendString);

        // Calculate the insert index which is the end of the original content
        int insertIndex = originalString.length();
        pieceTable.addPiece(new Piece(0, appendString.length(), Source.ADD), insertIndex);

        // Assert that the final content of the PieceTable is as expected
        String expectedString = "Original content appended.";
        assertEquals(expectedString, pieceTable.toString());
    }

    @Test
    public void addPieceWithSplit() {
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

        assertEquals("the quick brown fox\nwent to the park and\njumped over the lazy dog", pieceTable.toString());
    }

}
