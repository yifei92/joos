package joos.exceptions;

import java.lang.Exception;

public class InvalidTransitionException extends Exception {

    public InvalidTransitionException(String message) {
        super(message);
    }

}