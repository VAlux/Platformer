package com.platformer.utils;

import com.badlogic.gdx.utils.Array;

/**
 * Created by alexander on 20.11.15.
 * Some generic tools for different tasks.
 */
public class GenericTools {

    /**
     * Simple way of printing the array as a matrix.
     * @param array array to print.
     * @param size matrix size.
     */
    public static void printArrayAsMatrix(Array<?> array, int size) {
        for (int i = 0; i < array.size; i++) {
            System.out.print(array.get(i) + " ");
            if(i % size == 0) {
                System.out.print(System.lineSeparator());
            }
        }
    }
}
