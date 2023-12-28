package com.martinsundeqvist;

/**
 * The Source enum defines the possible sources of text pieces managed by the PieceTable.
 * It specifies whether a piece of text originates from the original content or from content
 * that has been added later.
 */
public enum Source {
    /** Indicates that the piece of text is from the original content. */
    ORIGINAL,

    /** Indicates that the piece of text has been added after the original content. */
    ADD 
}