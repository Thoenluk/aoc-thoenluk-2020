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
package thoenluk.adventofcode2020.runners;

import java.util.Arrays;

enum Direction {
    UPLEFT(-1, -1),
    UP(-1, 0),
    UPRIGHT(-1, 1),
    LEFT(0, -1),
    RIGHT(0, 1),
    DOWNLEFT(1, -1),
    DOWN(1, 0),
    DOWNRIGHT(1, 1);
    
    public final int y, x;

    private Direction(int y, int x) {
        this.y = y;
        this.x = x;
    }
}

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Runner11 {
    public static void runFirstChallenge(String input) {
        char[][] layout = parseAndPadInput(input);
        int y, x, surroundingPeople;
        // Translate strings into char matrix, and pad with . to make things easier.
        char[][] next = new char[layout.length][];
        // Create next-step matrix, remembering to deep clone (layout.clone wouldn't work.)
        for(y = 0; y < layout.length; y++) {
            next[y] = layout[y].clone();
        }
        char[][] tmp;
        boolean changed = true, seatFilled;
        while(changed) {
            changed = false;
            for(y = 1; y < layout.length - 1; y++) {
                for(x = 1; x < layout[y].length - 1; x++) {
                    if(layout[y][x] == 'L') {
                        next[y][x] = '#';
                        seatFilled = true;
                        for(Direction curr : Direction.values()) {
                            if(layout[y + curr.y][x + curr.x] == '#') {
                                next[y][x] = 'L';
                                seatFilled = false;
                                break;
                            }
                        }
                        changed |= seatFilled;
                    } else if (layout[y][x] == '#') {
                        surroundingPeople = 0;
                        next[y][x] = '#';
                        for(Direction curr : Direction.values()) {
                            if(layout[y + curr.y][x + curr.x] == '#') {
                                surroundingPeople++;
                                if(surroundingPeople >= 4) {
                                    next[y][x] = 'L';
                                    changed = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            tmp = layout;
            layout = next;
            next = tmp;
        }
        int occupied = 0;
        for(y = 1; y < layout.length - 1; y++) {
            for(x = 1; x < layout[y].length - 1; x++) {
                if(layout[y][x] == '#') {
                    occupied++;
                }
            }
        }
        System.out.println("There seem to be " + occupied + " occupied seats.");
    }
    
    public static void runSecondChallenge(String input) {
        char[][] layout = parseAndPadInput(input);
        int y, x, surroundingPeople, distance;
        // Translate strings into char matrix, and pad with . to make things easier.
        char[][] next = new char[layout.length][];
        // Create next-step matrix, remembering to deep clone (layout.clone wouldn't work.)
        for(y = 0; y < layout.length; y++) {
            next[y] = layout[y].clone();
        }
        char[][] tmp;
        boolean changed = true, seatFilled;
        while(changed) {
            changed = false;
            for(y = 1; y < layout.length - 1; y++) {
                for(x = 1; x < layout[y].length - 1; x++) {
                    if(layout[y][x] == 'L') {
                        next[y][x] = '#';
                        seatFilled = true;
                        for(Direction curr : Direction.values()) {
                            // Stop 1 position shy of running out of bounds, as at the
                            // edge the condition will check true, increment distance,
                            // and go out of bounds if we check for >= 0 etc.
                            // A sane person would use a while loop.
                            // but a bodyless for looks cooler even though it's smelly.
                            for(distance = 1; 
                                    y + distance * curr.y > 0 &&
                                    y + distance * curr.y < layout.length - 1 &&
                                    x + distance * curr.x > 0 &&
                                    x + distance * curr.x < layout[y].length - 1 &&
                                    layout[y + distance * curr.y][x + distance * curr.x] == '.';
                                    distance++);
                            if(layout[y + distance * curr.y][x + distance * curr.x] == '#') {
                                next[y][x] = 'L';
                                seatFilled = false;
                                break;
                            }
                        }
                        changed |= seatFilled;
                    } else if (layout[y][x] == '#') {
                        surroundingPeople = 0;
                        next[y][x] = '#';
                        for(Direction curr : Direction.values()) {
                            for(distance = 1;
                                    y + distance * curr.y > 0 &&
                                    y + distance * curr.y < layout.length - 1 &&
                                    x + distance * curr.x > 0 &&
                                    x + distance * curr.x < layout[y].length - 1 &&
                                    layout[y + distance * curr.y][x + distance * curr.x] == '.';
                                    distance++);
                            if(layout[y + distance * curr.y][x + distance * curr.x] == '#') {
                                surroundingPeople++;
                                if(surroundingPeople >= 5) {
                                    next[y][x] = 'L';
                                    changed = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            tmp = layout;
            layout = next;
            next = tmp;
        }
        int occupied = 0;
        for(y = 1; y < layout.length - 1; y++) {
            for(x = 1; x < layout[y].length - 1; x++) {
                if(layout[y][x] == '#') {
                    occupied++;
                }
            }
        }
        System.out.println("There seem to be " + occupied + " occupied seats.");
    }

    private static char[][] parseAndPadInput(String input) {
        String[] lines = input.split("\n");
        char[] lineArray;
        char[][] layout = new char[lines.length + 2][lines[0].length() + 2];
        Arrays.fill(layout[0], '.');
        Arrays.fill(layout[layout.length - 1], '.');
        for(int y = 0; y < lines.length; y++) {
            lineArray = lines[y].toCharArray();
            for(int x = 0; x < lineArray.length; x++) {
                layout[y + 1][x + 1] = lineArray[x];
            }
            layout[y + 1][0] = '.';
            layout[y + 1][lineArray.length + 1] = '.';
        }
        return layout;
    }
}
