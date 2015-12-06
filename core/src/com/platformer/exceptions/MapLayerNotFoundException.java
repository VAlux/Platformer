package com.platformer.exceptions;

public class MapLayerNotFoundException extends Exception{

    private final static String message = "Map does not contain necessary layer";

    public MapLayerNotFoundException(String layerName) {
        super(message + " :: " + layerName);
    }

    public MapLayerNotFoundException() {
        super(message);
    }
}
