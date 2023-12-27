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

    @Test
    public void testDeleteFromBeginning() {
        PieceTable pieceTable = new PieceTable("Hello World");
        pieceTable.delete(0); // Deleting 'H'
        assertEquals("ello World", pieceTable.toString());
    }

    @Test
    public void testDeleteFromMiddle() {
        PieceTable pieceTable = new PieceTable("Hello World");
        pieceTable.delete(5); // Deleting space
        assertEquals("HelloWorld", pieceTable.toString());
    }

    @Test
    public void testDeleteFromEnd() {
        PieceTable pieceTable = new PieceTable("Hello World");
        pieceTable.delete(10); // Deleting 'd'
        assertEquals("Hello Worl", pieceTable.toString());
    }

    @Test
    public void testDeleteSingleCharacter() {
        PieceTable pieceTable = new PieceTable("A");
        pieceTable.delete(0);
        assertEquals("", pieceTable.toString());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testDeleteInvalidIndex() {
        PieceTable pieceTable = new PieceTable("Hello World");
        pieceTable.delete(-1); // Invalid index
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testDeleteFromEmptyTable() {
        PieceTable pieceTable = new PieceTable("");
        pieceTable.delete(0); // No character to delete
    }

}
