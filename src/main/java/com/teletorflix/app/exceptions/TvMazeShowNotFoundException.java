package com.teletorflix.app.exceptions;

public class TvMazeShowNotFoundException extends RuntimeException {
    public static final String DESCRIPTION = "TvMaze Show Not Found Exception";
    private static final long serialVersionUID = 6830756676887746370L;

    public TvMazeShowNotFoundException() {
        super(DESCRIPTION);
    }

    public TvMazeShowNotFoundException(String message) {
        super(DESCRIPTION + ". " + message);
    }
}
