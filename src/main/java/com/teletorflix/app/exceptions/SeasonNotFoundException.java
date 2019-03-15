package com.teletorflix.app.exceptions;

public class SeasonNotFoundException extends RuntimeException {
    public static final String DESCRIPTION = "Season not found exception";
    private static final long serialVersionUID = 1110756676887746372L;

    public SeasonNotFoundException() {
        super(DESCRIPTION);
    }

    public SeasonNotFoundException(String message) {
        super(DESCRIPTION + ". " + message);
    }
}
