/*
 * Copyright (C) 2021 Lukas Thöni lukas.thoeni@gmx.ch
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

import java.util.Collection;
import org.apache.commons.collections.map.MultiKeyMap;
import thoenluk.adventofcode2020.HexDirection;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Runner24 {
    public static void runFirstChallenge(String input) {
        MultiKeyMap floor = new MultiKeyMap();
        int x, y, i;
        int total = 0;
        HexDirection direction = null;
        for(String line : input.split("\n")) {
            x = 0;
            y = 0;
            for(i = 0; i < line.length(); i++) {
                switch(line.charAt(i)) {
                    case 'e':
                        direction = HexDirection.EAST;
                        break;
                    case 'w':
                        direction = HexDirection.WEST;
                        break;
                    case 'n':
                        i++;
                        if(line.charAt(i) == 'w') {
                            direction = HexDirection.NORTHWEST;
                        } else {
                            direction = HexDirection.NORTHEAST;
                        }
                        break;
                    case 's':
                        i++;
                        if(line.charAt(i) == 'w') {
                            direction = HexDirection.SOUTHWEST;
                        } else {
                            direction = HexDirection.SOUTHEAST;
                        }
                        break;
                }
                x += direction.x;
                y += direction.y;
            }
            if(!floor.containsKey(x, y)) {
                floor.put(x, y, false);
            }
            floor.put(x, y, !((Boolean) floor.get(x, y)));
        }
        for(Boolean current : ((Collection<Boolean>) floor.values())) {
            total = current ? total + 1 : total;
        }
        System.out.println("My unhatched chickens count to " + total + " which seems to be the answer.");
    }
    
    public static void runSecondChallenge(String input) {
        boolean[][] floor = new boolean[236][236];
        boolean[][] nextFloor;
        int x, y, i, r, c, neighbours;
        int total = 0;
        HexDirection direction = null;
        for(String line : input.split("\n")) {
            x = floor.length / 2;
            y = floor[0].length / 2;
            for(i = 0; i < line.length(); i++) {
                switch(line.charAt(i)) {
                    case 'e':
                        direction = HexDirection.EAST;
                        break;
                    case 'w':
                        direction = HexDirection.WEST;
                        break;
                    case 'n':
                        i++;
                        if(line.charAt(i) == 'w') {
                            direction = HexDirection.NORTHWEST;
                        } else {
                            direction = HexDirection.NORTHEAST;
                        }
                        break;
                    case 's':
                        i++;
                        if(line.charAt(i) == 'w') {
                            direction = HexDirection.SOUTHWEST;
                        } else {
                            direction = HexDirection.SOUTHEAST;
                        }
                        break;
                }
                x += direction.x;
                y += direction.y;
            }
            floor[y][x] = !floor[y][x];
        }
        for(i = 0; i < 100; i++) {
            nextFloor = new boolean[floor.length][floor[0].length];
            for(r = 1; r < floor.length - 1; r++) {
                for(c = 1; c < floor[r].length - 1; c++) {
                    neighbours = 0;
                    for(HexDirection current : HexDirection.values()) {
                        if(floor[r + current.y][c + current.x]) {
                            neighbours++;
                        }
                    }
                    switch (neighbours) {
                        case 1:
                            nextFloor[r][c] = floor[r][c];
                            break;
                        case 2:
                            nextFloor[r][c] = true;
                            break;
                        default:
                            nextFloor[r][c] = false;
                            break;
                    }
                    total += floor[r][c] ? 1 : 0;
                }
            }
            floor = nextFloor;
        }
        for(r = 0; r < floor.length; r++) {
            for(c = 0; c < floor[r].length; c++) {
                total += floor[r][c] ? 1 : 0;
            }
        }
        System.out.println("My unhatched chickens count to " + total + " which seems to be the answer.");
    }
}
