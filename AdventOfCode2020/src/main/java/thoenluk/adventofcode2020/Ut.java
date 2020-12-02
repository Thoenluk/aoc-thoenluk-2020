package thoenluk.adventofcode2020;


import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Ut {
    private static HashMap<String, Integer> numberCache = new HashMap<>();
    
    public static int cachedParseInt(String stringRepresentation) {
        if(!numberCache.containsKey(stringRepresentation)) {
            numberCache.put(stringRepresentation, Integer.parseInt(stringRepresentation));
        }
        return numberCache.get(stringRepresentation);
    }
}
