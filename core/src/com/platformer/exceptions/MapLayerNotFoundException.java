package com.platformer.exceptions;

public class MapLayerNotFoundException extends Exception{

    private final static String message = "Map does not contain necessary layer";

    public MapLayerNotFoundException() {
        super(message);
    }
}
