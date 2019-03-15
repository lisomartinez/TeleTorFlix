package com.teletorflix.app.exceptions;

//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ShowNotFoundException extends RuntimeException {
    public static final String DESCRIPTION = "Show not found exception";
    private static final long serialVersionUID = 1839756676887746372L;

    public ShowNotFoundException() {
        super(DESCRIPTION);
    }

    public ShowNotFoundException(String message) {
        super(DESCRIPTION + ". " + message);
    }
}
