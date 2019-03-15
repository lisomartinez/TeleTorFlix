package com.teletorflix.app.exceptions;


public class InvalidScheduleDayException extends RuntimeException {
    public static final String DESCRIPTION = "Invalid ScheduleDay exception";
    private static final long serialVersionUID = 1107599976887746372L;

    public InvalidScheduleDayException() {
        super(DESCRIPTION);
    }

    public InvalidScheduleDayException(String message) {
        super(DESCRIPTION + ". " + message);
    }
}
