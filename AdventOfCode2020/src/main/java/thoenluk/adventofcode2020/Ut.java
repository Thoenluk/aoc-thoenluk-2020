package thoenluk.adventofcode2020;


import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lukas Th�ni lukas.thoeni@gmx.ch
 */
public class Ut {
    private static final HashMap<String, Integer> numberCache = new HashMap<>();
    
    public static int cachedParseInt(String stringRepresentation) throws NumberFormatException {
        if(!numberCache.containsKey(stringRepresentation)) {
            numberCache.put(stringRepresentation, Integer.parseInt(stringRepresentation));
        }
        return numberCache.get(stringRepresentation);
    }
    
    public static int cachedParseInt(String stringRepresentation, int radix) throws NumberFormatException {
        if(!numberCache.containsKey(stringRepresentation)) { // Does not distinguish radix. Probably fine in my use case.
            numberCache.put(stringRepresentation, Integer.parseInt(stringRepresentation, radix));
        }
        return numberCache.get(stringRepresentation);
    }
}
