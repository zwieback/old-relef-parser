package io.github.zwieback.relef.parsers.exceptions;

import org.jsoup.HttpStatusException;

public class UncheckedHttpStatusException extends RuntimeException {

    private static final long serialVersionUID = 7078281241827227588L;

    public UncheckedHttpStatusException(String message, HttpStatusException cause) {
        super(message, cause);
    }
}
