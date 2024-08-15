package com.igordsanches.controller;

public class UserNotFoundException extends RuntimeException {

    UserNotFoundException(String id) {
        super("Could not find employee " + id);
    }
}
