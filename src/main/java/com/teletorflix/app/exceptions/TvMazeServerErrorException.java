package com.teletorflix.app.exceptions;

public class TvMazeServerErrorException extends RuntimeException {
    public static final String DESCRIPTION = "TvMazeServerErrorException";
    private static final long serialVersionUID = 6830756676887746373L;

    public TvMazeServerErrorException() {
        super(DESCRIPTION);
    }

    public TvMazeServerErrorException(String message) {
        super(DESCRIPTION + ". " + message);
    }
}
