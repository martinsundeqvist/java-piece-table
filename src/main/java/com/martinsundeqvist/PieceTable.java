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
     * Method for inserting a new string into the piece table. Adds the string to the add buffer
     * as well as a new piece into the piece table.
     *  
     * @param s             String to be inserted into added buffer
     * @param insertIndex   Where in the current file the string is to be inserted
     */
    public void insert(String s, int insertIndex) {
        int addBufferInsertIndex = addBuffer.length() != 0 ? addBuffer.length() : 0;
        addBuffer.append(s);
        Piece piece = new Piece(addBufferInsertIndex, s.length(), Source.ADD);
        addPiece(piece, insertIndex);
    }

    /**
     * Inserts a new piece into the pieces list at the specified index.
     * 
     * @param newPiece    The new piece to insert.
     * @param insertIndex The index at which the new piece should be inserted.
     */
    private void addPiece(Piece newPiece, int insertIndex) {
        int currentIndex = 0;
        for (int i = 0; i < this.pieces.size(); i++) {
            Piece piece = this.pieces.get(i);
            if (currentIndex == insertIndex) {
                // Insert new piece here, move entire old piece one step forward
                this.pieces.add(i, newPiece);
                return;
            } else if (insertIndex < currentIndex + piece.getLength()) {
                // This means insert index is somewhere inside the current piece
                // We split at the insert index.
                int diff = insertIndex - currentIndex;
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
                // insertIndex is not in current piece, so we jump to first index of next
                currentIndex += piece.getLength();
            }
        }
        this.pieces.add(newPiece);
    }

    /**
     * Deletes the sequence given by start- and end indices.
     * 
     * @param startIndex    Index where deletion starts (inclusive, i.e. character for index is deleted)
     * @param endIndex      Index where deletion ends (exclusive, i.e. character for index not deleted)
     */
    public void delete(int startIndex, int endIndex) {
        if (startIndex < 0 || endIndex > this.toString().length() || startIndex >= endIndex) {
            throw new IndexOutOfBoundsException("Invalid start or end index");
        }
        
        int currentIndex = 0;
        int pieceIndex = 0;
        while (pieceIndex < this.pieces.size()) {
            Piece piece = this.pieces.get(pieceIndex);
            int pieceStart = currentIndex;
            int pieceEnd = pieceStart + piece.getLength();
            currentIndex += piece.getLength();
            if (pieceEnd <= startIndex || pieceStart >= endIndex) {
                // Current piece is not in intersection range, so we will leave it
                pieceIndex += 1;
            } else if (pieceStart < startIndex || pieceStart >= endIndex) {
                // Deletion range contained in piece, we need to split it up
                
                // Shorten current piece
                piece.setLength(startIndex - pieceStart);
                
                // Add new piece
                Piece newPiece = new Piece(piece.getStart() + endIndex - pieceStart, pieceEnd - endIndex, piece.getSource());
                if (pieceIndex == this.pieces.size() - 1) {
                    this.pieces.add(newPiece);
                } else {
                    this.pieces.add(pieceIndex + 1, newPiece);
                }
                pieceIndex += 2;
            } else if (pieceStart >= startIndex && pieceEnd <= endIndex) {
                this.pieces.remove(pieceIndex);
            } else {
                // Partial intersection, adjust the piece
                if (pieceStart < startIndex) {
                    piece.setLength(startIndex - pieceStart);
                }
                if (pieceEnd > endIndex) {
                    piece.setStart(piece.getStart() + endIndex - pieceStart);
                    piece.setLength(pieceEnd - endIndex);
                }
                pieceIndex += 1;
            }
        }
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
