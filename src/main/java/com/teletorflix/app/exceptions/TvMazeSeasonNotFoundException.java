package com.teletorflix.app.exceptions;

public class TvMazeSeasonNotFoundException extends RuntimeException {
    public static final String DESCRIPTION = "TVMaze season not found";
    private static final long serialVersionUID = 6830753467688565637L;

    public TvMazeSeasonNotFoundException() {
        super(DESCRIPTION);
    }

    public TvMazeSeasonNotFoundException(String message) {
        super(DESCRIPTION + ". " + message);
    }
}
