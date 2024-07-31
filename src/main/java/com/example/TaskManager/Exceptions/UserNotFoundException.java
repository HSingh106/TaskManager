package com.example.TaskManager.Exceptions;

/**
 * represents an exception to be thrown only when a user cannot be found
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String s) {
        super(s);
    }
}
