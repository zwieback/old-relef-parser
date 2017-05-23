package io.github.zwieback.relef.web.services.exceptions;

public class AuthException extends RuntimeException {

    private static final long serialVersionUID = 1708016696671057201L;

    public AuthException() {
        super("Can not authorize with such credentials. Check the login and password");
    }
}
