package thoenluk.adventofcode2020.runners;

/*
 * Copyright (C) 2020 Lukas Thöni lukas.thoeni@gmx.ch
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


import java.util.TreeMap;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Runner17 {
    // When you write this kind of typedef, you know your life has gone horribly right.
    private static TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, Boolean>>>> space = new TreeMap<>();
    private static TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, Boolean>>>> next = new TreeMap<>();
    
    public static void runFirstChallenge(String input) {
        String[] lines = input.split("\n");
        int x, y, z, i, neighbours, active = 0;
        for(y = 0; y < lines.length; y++) {
            for(x = 0; x < lines[y].length(); x++) {
                setNext(x, y, 0, 0, lines[y].charAt(x) == '#');
            }
        }
        
        // As much as indices start at 0, in this case it makes the loop much easier.
        // The starting input is an 8x8 square at z=0. It cannot grow more than one
        // cube each round. Since looping over all actual possible points would be
        // very costly, abuse this property of the challenge instead.
        for(i = 1; i <= 6; i++) {
            active = 0;
            space = next;
            next = new TreeMap<>();
            for(x = -i; x <= 8 + i; x++){
                for(y = -i; y <= 8 + i; y++) {
                    for(z = -i; z <= i; z++) {
                        neighbours = countNeighbours(x, y, z, 0);
                        if(neighbours == 3 || (neighbours == 2 && getSpace(x, y, z, 0))) {
                            setNext(x, y, z, 0, true);
                            active++;
                        } else {
                            setNext(x, y, z, 0, false);
                        }
                    }
                }
            }
        }
        System.out.println("Oh bother. There appear to be " + active + " active cubes.");
    }
    
    public static void runSecondChallenge(String input) {
        String[] lines = input.split("\n");
        int x, y, z, w, i, neighbours, active = 0;
        for(y = 0; y < lines.length; y++) {
            for(x = 0; x < lines[y].length(); x++) {
                setNext(x, y, 0, 0, lines[y].charAt(x) == '#');
            }
        }
        
        for(i = 1; i <= 6; i++) {
            active = 0;
            space = next;
            next = new TreeMap<>();
            for(x = -i; x <= 8 + i; x++){
                for(y = -i; y <= 8 + i; y++) {
                    for(z = -i; z <= i; z++) {
                        for(w = -i; w <= i; w++) {
                            neighbours = countNeighbours(x, y, z, w);
                            if(neighbours == 3 || (neighbours == 2 && getSpace(x, y, z, w))) {
                                setNext(x, y, z, w, true);
                                active++;
                            } else {
                                setNext(x, y, z, w, false);
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Oh bother. There appear to be " + active + " active cubes.");
    }
    
    private static boolean getSpace(int x, int y, int z, int w) {
        if(!space.containsKey(x)) {
            space.put(x, new TreeMap<>());
        }
        if(!space.get(x).containsKey(y)) {
            space.get(x).put(y, new TreeMap<>());
        }
        if(!space.get(x).get(y).containsKey(z)) {
            space.get(x).get(y).put(z, new TreeMap<>());
        }
        // When I had to extend it for the fourth dimension and added this condition,
        // I pondered that a boolean array array array array would have been easier.
        // But this is theoretically infinite, albeit that that is unnecessary.
        if(!space.get(x).get(y).get(z).containsKey(w)) {
            space.get(x).get(y).get(z).put(w, Boolean.FALSE);
        }
        return space.get(x).get(y).get(z).get(w);
    }
    
    private static void setNext(int x, int y, int z, int w, boolean value) {
        if(!next.containsKey(x)) {
            next.put(x, new TreeMap<>());
        }
        if(!next.get(x).containsKey(y)) {
            next.get(x).put(y, new TreeMap<>());
        }
        if(!next.get(x).get(y).containsKey(z)) {
            next.get(x).get(y).put(z, new TreeMap<>());
        }
        next.get(x).get(y).get(z).put(w, value);
    }
    
    private static int countNeighbours(int x, int y, int z, int w) {
        // Counting only the spaces around the desired cube is clunky. We can simply
        // count all in the 3x3 cube and adjust the value to compensate if the cube is active.
        int neighbours = getSpace(x, y, z, w) ? -1 : 0;
        int i, j, k, m;
        for(i = x - 1; i <= x + 1; i++) {
            for(j = y - 1; j <= y + 1; j++) {
                for(k = z - 1; k <= z + 1; k++) {
                    for(m = w - 1; m <= w + 1; m++) {
                        if(getSpace(i, j, k, m)) {
                            neighbours++;
                            if(neighbours > 3) {
                                return neighbours;
                            }
                        }
                    }
                }
            }
        }
        return neighbours;
    }
}
