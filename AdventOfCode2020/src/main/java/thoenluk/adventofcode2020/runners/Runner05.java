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

import java.util.ArrayList;
import thoenluk.adventofcode2020.Ut;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Runner05 {
    public static void runFirstChallenge(String input) {
        int highestID = Integer.MIN_VALUE;
        int currentID;
        for(String pass : input.split("\n")) {
            currentID = Ut.cachedParseInt(pass.replaceAll("[FL]", "0").replaceAll("[BR]", "1"), 2);
            highestID = currentID > highestID ? currentID : highestID;
        }
        System.out.println("The highest ID one can find is " + highestID);
    }
    
    public static void runSecondChallenge(String input) {
        ArrayList<Integer> ids = new ArrayList<>();
        int myID = -1;
        for(String pass : input.split("\n")) {
            ids.add(Ut.cachedParseInt(pass.replaceAll("[FL]", "0").replaceAll("[BR]", "1"), 2));
        }
        ids.sort(null);
        for(int i = 1; i < ids.size(); i++) {
            if(ids.get(i) - ids.get(i - 1) > 1) {
                myID = ids.get(i) - 1;
                break;
            }
        }
        System.out.println("My ID is presumably " + myID);
    }
}