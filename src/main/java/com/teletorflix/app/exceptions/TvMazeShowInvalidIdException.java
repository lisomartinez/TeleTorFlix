package com.teletorflix.app.exceptions;

public class TvMazeShowInvalidIdException extends RuntimeException {
    public static final String DESCRIPTION = "TVMaze invalid Show id";
    private static final long serialVersionUID = 6830753467688565637L;

    public TvMazeShowInvalidIdException() {
        super(DESCRIPTION);
    }

    public TvMazeShowInvalidIdException(String message) {
        super(DESCRIPTION + ". " + message);
    }
}
