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

import thoenluk.adventofcode2020.CardinalDirection;
import thoenluk.adventofcode2020.Ut;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Runner12 {
    public static void runFirstChallenge(String input) {
        int latitude = 0, longitude = 0;
        char action;
        int value;
        CardinalDirection facing = CardinalDirection.EAST;
        
        for(String line : input.split("\n")) {
            action = line.charAt(0);
            value = Ut.cachedParseInt(line.substring(1));
            switch(action) {
                case 'N':
                    latitude += value;
                    break;
                case 'S':
                    latitude -= value;
                    break;
                case 'E':
                    longitude += value;
                    break;
                case 'W':
                    longitude -= value;
                    break;
                case 'L':
                    facing = CardinalDirection.get((4 + facing.index - value / 90) % 4);
                    break;
                case 'R':
                    facing = CardinalDirection.get((4 + facing.index + value / 90) % 4);
                    break;
                case 'F':
                    latitude += facing.latitude * value;
                    longitude += facing.longitude * value;
                    break;
            }
        }
        
        System.out.println("It appears our random circling has brought us to "
                + (latitude < 0 ? "S" : "N" ) + Math.abs(latitude)
                + (longitude < 0 ? " W" : " E") + Math.abs(longitude)
                + " which is " + (Math.abs(latitude) + Math.abs(longitude)) +
                " manhattan distance away.");
    }
    
    public static void runSecondChallenge(String input) {
        int latitude = 0, longitude = 0, tmp;
        int[] waypoint = new int[]{1, 10};
        char action;
        int value;
        
        for(String line : input.split("\n")) {
            action = line.charAt(0);
            value = Ut.cachedParseInt(line.substring(1));
            switch(action) {
                case 'N':
                    waypoint[0] += value;
                    break;
                case 'S':
                    waypoint[0] -= value;
                    break;
                case 'E':
                    waypoint[1] += value;
                    break;
                case 'W':
                    waypoint[1] -= value;
                    break;
                case 'L':
                    for(; value > 0; value -= 90) {
                        tmp = waypoint[1];
                        waypoint[1] = -waypoint[0];
                        waypoint[0] = tmp;
                    }
                    break;
                case 'R':
                    for(;value > 0; value -= 90) {
                        tmp = waypoint[0];
                        waypoint[0] = -waypoint[1];
                        waypoint[1] = tmp;
                    }
                    break;
                case 'F':
                    latitude += waypoint[0] * value;
                    longitude += waypoint[1] * value;
                    break;
            }
        }
        
        System.out.println("It appears our random circling has brought us to "
                + (latitude < 0 ? "S" : "N" ) + Math.abs(latitude)
                + (longitude < 0 ? " W" : " E") + Math.abs(longitude)
                + " which is " + (Math.abs(latitude) + Math.abs(longitude)) +
                " manhattan distance away.");
    }
}
