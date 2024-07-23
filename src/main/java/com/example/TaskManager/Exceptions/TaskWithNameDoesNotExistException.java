package com.example.TaskManager.Exceptions;

public class TaskWithNameDoesNotExistException extends RuntimeException{
    public TaskWithNameDoesNotExistException(String s) {
        super(s);
    }
}
