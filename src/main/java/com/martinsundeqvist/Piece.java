package com.martinsundeqvist;

public class Piece {
    private int start = 0;
    private int length = 0;
    private Source source;

    public Piece(int start, int length, Source source) {
        this.start = start;
        this.length = length;
        this.source = source;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStart() {
        return this.start;
    }

    public void setLength(int length) {
        this.length = length;
    }
    
    public int getLength() {
        return this.length;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Source getSource() {
        return this.source;
    }
}
