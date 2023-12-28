package com.martinsundeqvist;

import java.util.ArrayList;
import java.util.List;

/**
 * The PieceTable class is a data structure that manages text through a piece table,
 * allowing efficient insertions and deletions. It uses two buffers: the original buffer
 * contains the original text, and the add buffer stores additional text appended to the
 * original. The class maintains a list of pieces that reference spans within these two buffers.
 */
public class PieceTable {
    private String originalBuffer;
    private StringBuilder addBuffer;
    private List<Piece> pieces;

    /**
     * Constructs a new PieceTable with the specified original text.
     *
     * @param original The initial text to be managed by the piece table.
     */
    public PieceTable(String original) {
        originalBuffer = original;
        addBuffer = new StringBuilder();
        pieces = new ArrayList<>();
        pieces.add(new Piece(0, original.length(), Source.ORIGINAL));
    }

    /**
     * Retrieves the buffer based on the specified source.
     *
     * @param source The source of the buffer to retrieve; either the original or added text.
     * @return The text buffer corresponding to the specified source.
     */
    public String getBuffer(Source source) {
        if (source == Source.ORIGINAL) {
            return originalBuffer;
        } else {
            return addBuffer.toString();
        }
    }

    /**
     * Appends the given string to the add buffer.
     *
     * @param s The string to append to the add buffer.
     */
    public void addToAddBuffer(String s) {
        addBuffer.append(s);
    }

    /**
     * Inserts a new piece into the pieces list at the specified index.
     * 
     * @param newPiece    The new piece to insert.
     * @param insertIndex The index at which the new piece should be inserted.
     */
    public void addPiece(Piece newPiece, int insertIndex) {
        // TODO: Add check for insertIndex and how it spans
        // Use a current index to track what current index is
        int currentIndex = 0;
        // Go over all the pieces to figure out where new piece gets inserted
        for (int i = 0; i < this.pieces.size(); i++) {
            Piece piece = this.pieces.get(i);
            int diff = insertIndex - currentIndex;
            if (diff == 0) {
                // Case 1: We are at the start of current piece, i.e. we should insert new
                // piece before current piece
                this.pieces.add(i, newPiece);
                return;
            } else if (diff <= piece.getLength()) {
                // Case 2: The insertion needs to be made in the middle of this piece.
                // We split the existing piece in two, then add our new piece in between old
                // pieces.
                Piece secondHalf = new Piece(piece.getStart() + diff, piece.getLength() - diff, piece.getSource());
                if (i == this.pieces.size() - 1) {
                    this.pieces.add(secondHalf);
                } else {
                    this.pieces.add(i + 1, secondHalf);
                }
                this.pieces.add(i + 1, newPiece);
                piece.setLength(diff);
                return;
            } else {
                // Case 3: There's no overlap so we move currentIndex to first index of next
                // piece
                currentIndex += piece.getLength() + 1;
            }
        }
        // TODO: Currently assuming that if we reach end of pieces and didn't find an
        // insertion point,
        // we need to simply append the new piece. However,
        this.pieces.add(newPiece);
    }

    /**
     * Deletes a single character at the specified index.
     *
     * @param deleteIndex The index of the character to delete.
     * @throws IndexOutOfBoundsException If deleteIndex is out of the bounds of the current text.
     */
    public void delete(int deleteIndex) {
        if (deleteIndex < 0 || deleteIndex >= this.toString().length()) {
            throw new IndexOutOfBoundsException("Delete index is out of bounds");
        }

        int currentIndex = 0;
        for (int i = 0; i < this.pieces.size(); i++) {
            Piece piece = this.pieces.get(i);
            int start = currentIndex;
            int end = start + piece.getLength();

            if (deleteIndex >= start && deleteIndex < end) {
                // Delete from this piece
                if (piece.getLength() == 1) {
                    // If the piece is of length 1, remove it
                    this.pieces.remove(i);
                } else if (deleteIndex == start) {
                    // If deleting from the start of the piece, just adjust the start and length
                    piece.setStart(piece.getStart() + 1);
                    piece.setLength(piece.getLength() - 1);
                } else if (deleteIndex == end - 1) {
                    // If deleting from the end of the piece, just adjust the length
                    piece.setLength(piece.getLength() - 1);
                } else {
                    // Split the piece into two around the deletion point
                    Piece firstHalf = new Piece(piece.getStart(), deleteIndex - start, piece.getSource());
                    Piece secondHalf = new Piece(piece.getStart() + deleteIndex - start + 1,
                            piece.getLength() - (deleteIndex - start + 1), piece.getSource());
                    this.pieces.set(i, firstHalf);
                    this.pieces.add(i + 1, secondHalf);
                }
                return;
            }
            currentIndex += piece.getLength();
        }
    }

    /**
     * Returns the current text managed by the piece table.
     *
     * @return A string representing the current state of the text.
     */
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Piece piece : this.pieces) {
            Source source = piece.getSource();
            String buffer = getBuffer(source);
            String spanOfText = buffer.substring(piece.getStart(), piece.getStart() + piece.getLength());
            output.append(spanOfText);
        }
        return output.toString();
    }
}
