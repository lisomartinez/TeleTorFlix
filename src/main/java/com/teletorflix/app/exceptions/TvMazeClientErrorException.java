package com.teletorflix.app.exceptions;

public class TvMazeClientErrorException extends RuntimeException {
    public static final String DESCRIPTION = "TV Maze API Exception";
    private static final long serialVersionUID = 6830756676887746372L;

    public TvMazeClientErrorException() {
        super(DESCRIPTION);
    }

    public TvMazeClientErrorException(String message) {
        super(DESCRIPTION + ". " + message);
    }
}

