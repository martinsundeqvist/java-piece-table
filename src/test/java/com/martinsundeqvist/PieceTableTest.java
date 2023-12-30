package com.martinsundeqvist;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PieceTableTest {

    @Test
    public void testMultipleInserts() {
        String original = "Is this";
        String s1 = ", where I";
        String s2 = ", came in?";
        PieceTable pieceTable = new PieceTable(original);
        pieceTable.insert(s2, original.length());
        pieceTable.insert(s1, original.length());

        assertEquals("Is this, where I, came in?", pieceTable.toString());
    }

    @Test
    public void testInsertWithEmptyOriginal() {
        // Initialize PieceTable with an empty string
        PieceTable pieceTable = new PieceTable("");

        // Append a new string to the add buffer and add a piece for it
        String addString = "New content";
        pieceTable.insert(addString, 0);

        // Assert the content of the PieceTable matches the added string
        assertEquals("New content", pieceTable.toString());
    }

    @Test
    public void addPieceAtBeginning() {
        String originalString = "Start";
        PieceTable pieceTable = new PieceTable(originalString);
        String insertString = "Before ";
        pieceTable.insert(insertString, 0);

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
        pieceTable.insert(addString, afterFirstLineIndex);

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

    @Test
    public void testDeleteRangeAtBeginning() {
        PieceTable pieceTable = new PieceTable("Hello World");
        pieceTable.delete(0, 5); // Deleting "Hello"
        assertEquals(" World", pieceTable.toString());
    }

    @Test
    public void testDeleteRangeAtEnd() {
        PieceTable pieceTable = new PieceTable("Hello World");
        pieceTable.delete(6, 11); // Deleting "World"
        assertEquals("Hello ", pieceTable.toString());
    }

    @Test
    public void testDeleteRangeInTheMiddle() {
        PieceTable pieceTable = new PieceTable("Hello World");
        pieceTable.delete(4, 7); // Deleting "o W"
        assertEquals("Hellorld", pieceTable.toString());
    }

    @Test
    public void testDeleteEntireText() {
        PieceTable pieceTable = new PieceTable("Hello World");
        pieceTable.delete(0, 11); // Deleting everything
        assertEquals("", pieceTable.toString());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testNoOpDeletionRange() {
        PieceTable pieceTable = new PieceTable("Hello World");
        pieceTable.delete(5, 5); // No change
        assertEquals("Hello World", pieceTable.toString());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testDeleteRangeInvalidIndex() {
        PieceTable pieceTable = new PieceTable("Hello World");
        pieceTable.delete(-1, 12); // Invalid indices
    }

    @Test
    public void testDeleteAcrossMultiplePieces() {
        PieceTable pieceTable = new PieceTable("Hello World");
        pieceTable.insert(" beautiful", 5);
        // Text is now "Hello beautiful World"
        pieceTable.delete(5, 16); // Deleting " beautiful W"
        assertEquals("HelloWorld", pieceTable.toString());
    }

    @Test
    public void testDeleteThatSplitsAPiece() {
        PieceTable pieceTable = new PieceTable("Hello World");
        pieceTable.insert(" and wonderful", 11);
        // Text is now "Hello World and wonderful"
        pieceTable.delete(12, 16); // Deleting "and "
        assertEquals("Hello World wonderful", pieceTable.toString());
    }

}
