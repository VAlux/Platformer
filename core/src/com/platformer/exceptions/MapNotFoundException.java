package com.platformer.exceptions;

/**
 * Created by Alvo on 06.12.2015.
 * Exception thrown, if we have not map found in the maps pool.
 */
public class MapNotFoundException extends Exception {
    private final static String message = "Map does not exist.";

    public MapNotFoundException(String mapName) {
        super(message + " Map Name: " + mapName);
    }

    public MapNotFoundException() {
        super(message);
    }
}
