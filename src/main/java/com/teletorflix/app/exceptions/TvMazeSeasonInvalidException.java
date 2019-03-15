package com.teletorflix.app.exceptions;

public class TvMazeSeasonInvalidException extends RuntimeException {
    public static final String DESCRIPTION = "TVMaze season id invalid";
    private static final long serialVersionUID = 3330753467338565633L;

    public TvMazeSeasonInvalidException() {
        super(DESCRIPTION);
    }

    public TvMazeSeasonInvalidException(String message) {
        super(DESCRIPTION + ". " + message);
    }
}
