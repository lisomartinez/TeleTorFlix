package com.teletorflix.app.exceptions;

public class TvMazeResourceNotFoundException extends RuntimeException {
    public static final String DESCRIPTION = "TVMaze Resource not found";
    private static final long serialVersionUID = 6163307534678565169L;

    public TvMazeResourceNotFoundException() {
        super(DESCRIPTION);
    }

    public TvMazeResourceNotFoundException(String message) {
        super(DESCRIPTION + ". " + message);
    }
}
