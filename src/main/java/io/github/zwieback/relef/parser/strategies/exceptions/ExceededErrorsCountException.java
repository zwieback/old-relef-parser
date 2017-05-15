package io.github.zwieback.relef.parser.strategies.exceptions;

public class ExceededErrorsCountException extends RuntimeException {

    private static final long serialVersionUID = 8612621262251475252L;

    public ExceededErrorsCountException() {
        super("Exceeded the threshold number of errors");
    }
}
