package com.martinsundeqvist;

import java.util.ArrayList;
import java.util.List;

public class PieceTable {
    private String originalBuffer;
    private StringBuilder addBuffer;
    private List<Piece> pieces;

    public PieceTable(String original) {
        originalBuffer = original;
        addBuffer = new StringBuilder();
        pieces = new ArrayList<>();
        pieces.add(new Piece(0, original.length(), Source.ORIGINAL));
    }

    public String getBuffer(Source source) {
        if (source == Source.ORIGINAL) {
            return originalBuffer;
        } else {
            return addBuffer.toString();
        }
    }

    public void addToAddBuffer(String s) {
        addBuffer.append(s);
    }

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
     * 
     * @param deleteIndex index to delete
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
