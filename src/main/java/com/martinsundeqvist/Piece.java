package com.martinsundeqvist;

/**
 * The Piece class represents a contiguous span of text (a "piece") in the document. It holds
 * the start position, length, and the source of the text (original or added buffer).
 */
public class Piece {
    private int start = 0;
    private int length = 0;
    private Source source;

    /**
     * Constructs a new Piece with the specified start position, length, and source.
     *
     * @param start  The starting index of this piece in its source buffer.
     * @param length The number of characters in this piece.
     * @param source The source buffer of this piece, which can be the original or add buffer.
     */
    public Piece(int start, int length, Source source) {
        this.start = start;
        this.length = length;
        this.source = source;
    }

    /**
     * Sets the starting index of this piece.
     *
     * @param start The starting index to set for this piece.
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * Gets the starting index of this piece.
     *
     * @return The starting index of this piece.
     */
    public int getStart() {
        return this.start;
    }

    /**
     * Sets the length of this piece.
     *
     * @param length The length to set for this piece.
     */
    public void setLength(int length) {
        this.length = length;
    }
    
    /**
     * Gets the length of this piece.
     *
     * @return The length of this piece.
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Sets the source buffer of this piece.
     *
     * @param source The source to set for this piece, indicating where the text comes from.
     */
    public void setSource(Source source) {
        this.source = source;
    }

    /**
     * Gets the source buffer of this piece.
     *
     * @return The source of this piece, indicating where the text comes from.
     */
    public Source getSource() {
        return this.source;
    }
}
