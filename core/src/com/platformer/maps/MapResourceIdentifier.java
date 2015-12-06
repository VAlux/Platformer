package com.platformer.maps;

/**
 * Created by Alvo on 06.12.2015.
 * Contains all of the meta-info about the map.
 * It's identification name and path to tmx file.
 */
public final class MapResourceIdentifier {

    /**
     * Map name.
     */
    private String name;

    /**
     * Path to the tmx map file.
     */
    private String pathToFile;

    public MapResourceIdentifier(String name, String pathToFile) {
        this.name = name;
        this.pathToFile = pathToFile;
    }

    public String getName() {
        return name;
    }

    public String getPathToFile() {
        return pathToFile;
    }
}