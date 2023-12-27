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
                // We split the existing piece in two, then add our new piece in between old pieces.
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
                // Case 3: There's no overlap so we move currentIndex to first index of next piece
                currentIndex += piece.getLength() + 1;
            }
        }
        // TODO: Currently assuming that if we reach end of pieces and didn't find an insertion point,
        // we need to simply append the new piece. However, 
        this.pieces.add(newPiece);
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Piece piece: this.pieces) {
            Source source = piece.getSource();
            String buffer = getBuffer(source);
            String spanOfText = buffer.substring(piece.getStart(), piece.getStart() + piece.getLength());
            output.append(spanOfText);
        }
        return output.toString();
    }
}
