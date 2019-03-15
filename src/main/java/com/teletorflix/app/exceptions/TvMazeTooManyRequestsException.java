package com.teletorflix.app.exceptions;

public class TvMazeTooManyRequestsException extends RuntimeException {
    public static final String DESCRIPTION = "TV Maze Too Many Request";
    private static final long serialVersionUID = 6830756676887746371L;

    public TvMazeTooManyRequestsException() {
        super(DESCRIPTION);
    }

    public TvMazeTooManyRequestsException(String message) {
        super(DESCRIPTION + ". " + message);
    }
}
