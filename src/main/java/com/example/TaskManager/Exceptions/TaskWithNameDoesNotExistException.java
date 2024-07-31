package com.example.TaskManager.Exceptions;

/**
 * represents an exception that is meant to be displayed only when a task cannot be found as expected
 */
public class TaskWithNameDoesNotExistException extends RuntimeException{
    public TaskWithNameDoesNotExistException(String s) {
        super(s);
    }
}
