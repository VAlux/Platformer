package com.platformer.exceptions;

public class MapObjectNotFoundException extends Exception {

    private final static String message = "Map does not contain necessary object";

    public MapObjectNotFoundException(String objectName) {
        super(message + " :: " + objectName);
    }

    public MapObjectNotFoundException() {
        super(message);
    }
}
