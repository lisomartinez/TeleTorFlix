package com.teletorflix.app.exceptions;

public class EpisodeNotFoundException extends RuntimeException {
    public static final String DESCRIPTION = "Episode Not Found exception";
    private static final long serialVersionUID = 1907592976887146392L;

    public EpisodeNotFoundException() {
        super(DESCRIPTION);
    }

    public EpisodeNotFoundException(String message) {
        super(DESCRIPTION + ". " + message);
    }
}
